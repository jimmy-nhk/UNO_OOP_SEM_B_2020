package Controller;

import Model.*;
import Model.Timer;
import Service.GameBoardService;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.Bloom;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class GameBoard implements Initializable {


    public static ClientController clientController = new ClientController("127.0.0.1", 8080);
    public Button buttonHome;
    /**
     * GUI cards for main player
     **/
    double step;
    /**
     * This method only applies to main player which sets the selected animation
     **/
    @FXML
    private Button btPlay;
    @FXML
    private Button btDraw;
    @FXML
    private Pane pane;
    private MainMenu mainMenu;
    private AnchorPane gameBoard;
    private GameBoardService gameBoardService = GameBoardService.getInstance();
    private Deck deck = gameBoardService.getDeck();
    private int mainPlayer = gameBoardService.getMainPlayerIndex();
    private ArrayList<Player> inGamePlayers = gameBoardService.getInGamePlayers();
    private Timer timer = new Timer();
    @FXML private ImageView volumeOnImageView;
    @FXML private ImageView volumeOffImageView;
    @FXML private Text firstPlayer;
    @FXML private Text secondPlayer;
    @FXML private Text thirdPlayer;
    @FXML private Text fourthPlayer;
    /**
     * Create animation for cards in the board
     **/

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gameBoardService = new GameBoardService();
        Bloom bloom = new Bloom();
        firstPlayer.setEffect(bloom);
        secondPlayer.setEffect(bloom);
        thirdPlayer.setEffect(bloom);
        fourthPlayer.setEffect(bloom);
        try {
            clientController.writeMessage(new Message("initialize", gameBoardService.getDeck()));
            setCardInDeck(); // Set up deck for drawing

            createAnimationDistribute7Cards(); // Distribute cards for all players
            startGame(); // Begin the game by open a card.
            Sound launchGameSound = new Sound("resources/sound/sound_launch.mp3");
            timer.countTime();

            gameBoard.getChildren().add(timer);
            timer.setLayoutX(710);
            timer.setLayoutY(546);

            setActionForVolumeOnImage();
            setButtonBindingText();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setButtonBindingText(){
        LanguageController.setUpButtonText(buttonHome,"gameBoard.homeButton" );
        LanguageController.setUpButtonText(btPlay, "gameBoard.playButton");
        LanguageController.setUpButtonText(btDraw, "gameBoard.drawButton");
    }


    private void setActionForVolumeOnImage() {

        File soundOnFile = new File("src/resources/Image/sound_on.jpg");
        File soundOffFile = new File("src/resources/Image/sound_off.jpg");
        Image soundOn = new Image(soundOnFile.toURI().toString());
        Image soundOff = new Image(soundOffFile.toURI().toString());

        volumeOnImageView.setImage(soundOn);
        volumeOnImageView.setVisible(true);


        volumeOffImageView.setImage(soundOff);
        volumeOffImageView.setVisible(false);

        volumeOnImageView.setOnMouseClicked(e -> {
            Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
            MainMain.backGroundSound.stop();
            volumeOnImageView.setVisible(false);
            volumeOffImageView.setVisible(true);
        });
        volumeOffImageView.setOnMouseClicked(e -> {
            Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
            volumeOnImageView.setVisible(true);
            volumeOffImageView.setVisible(false);
            MainMain.backGroundSound.play();
        });
    }


    /** Set up the first card for first player to play card */
    public void startGame (){

        // Take the card from the deck of the gameBoard service
        gameBoardService.setPreviousCard(gameBoardService.getDeck().drawTopCard());
        System.out.println(gameBoardService.getPreviousCard());

        // Set the previous card by taking the deck
        Card startCard = deck.drawTopCard();
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), startCard );
        translateTransition.setToX(680);
        translateTransition.setByY(230);

        translateTransition.setDelay(Duration.millis(8000));

        translateTransition.setOnFinished(actionEvent -> {
            // Create another arraylist to store the played card.
            ArrayList<Card> playedCard = gameBoardService.getPlayedCards();
            startCard.setFitWidth(120);
            startCard.setFitHeight(150);
            startCard.setRotate(0);
            startCard.setFrontImage();
            playedCard.add(startCard);
            gameBoardService.setPlayedCards(playedCard);

        });
        translateTransition.play();
    }

    /**
     * Add all the cards to the main board
     **/
    public void setCardInDeck() {
        for (int i = 0; i < deck.getSize(); i++) {

            deck.getCards().get(i).setRotate(-80);
            deck.getCards().get(i).setTranslateX(250 + i * 0.5);
            deck.getCards().get(i).setTranslateY(80 - i * 0.5);
            deck.getCards().get(i).setFitWidth(80);
            deck.getCards().get(i).setFitHeight(100);
            deck.getCards().get(i).setBackImage();

            pane.getChildren().add(deck.getCards().get(i));
        }

    }

    /**
     * Set animation for distributing cards for main player
     **/
    public void distributeCardsForMainPlayer(int i) {
        Card currentCard = deck.getCards().get(deck.getSize() - 1);
        step = 720.0 / (i + 1);
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000));
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000));
        rotateTransition.setCycleCount(7);
        rotateTransition.setToAngle(360);

        rotateTransition.setInterpolator(Interpolator.LINEAR);
        currentCard.setRotate(-80);
        rotateTransition.setFromAngle(0);
        rotateTransition.setNode(currentCard);
        rotateTransition.play();

        translate.setNode(currentCard);
        translate.setDelay(Duration.millis(1000 * i + 250));
        rotateTransition.setDelay(Duration.millis(1000 * i + 250));

        translate.setToX(400 + i * step);
        translate.setToY(660);

        // Set the actual width and height of the card
        translate.setOnFinished(event -> {
            currentCard.setFitHeight(150);
            currentCard.setFitWidth(120);
            currentCard.setFrontImage();
            currentCard.toFront();
            currentCard.setRotate(0);
            inGamePlayers.get(mainPlayer).getCardList().add(currentCard);
            arrangeCardsForMainPlayer(mainPlayer);
            setAnimationForSelectedCard();
            rotateTransition.stop();
        });

        translate.play();
        deck.drawTopCard();
    }

    /**
     * Set animation for distributing cards for left player
     **/
    public void distributeCardsForRightPlayer(int i) {

        Card currentCard = deck.drawTopCard();
        double step = 150 / (i + 1);
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000));
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000));
        rotateTransition.setCycleCount(7);
        rotateTransition.setToAngle(360);

        rotateTransition.setInterpolator(Interpolator.LINEAR);
        currentCard.setRotate(-80);
        rotateTransition.setFromAngle(0);
        rotateTransition.setNode(currentCard);
        rotateTransition.play();

        translate.setNode(currentCard);
        translate.setDelay(Duration.millis(1000 * i + 500));
        rotateTransition.setDelay(Duration.millis(1000 * i + 500));

        translate.setToX(1250 - i * step);
        translate.setToY(400 - i * step);

        // Set the actual width and height of the card
        translate.setOnFinished(event -> {
            currentCard.setFitHeight(100);
            currentCard.setFitWidth(80);
            currentCard.toBack();
            currentCard.setRotate(0);
            inGamePlayers.get((mainPlayer + 1) % inGamePlayers.size()).getCardList().add(currentCard);
            arrangeCardsForRightPlayer((mainPlayer + 1) % inGamePlayers.size());
            rotateTransition.stop();
        });

        translate.play();
    }

    /**
     * Set animation for distributing cards for left player
     **/
    public void distributeCardsForLeftPlayer(int i) {

        Card currentCard = deck.drawTopCard();
        double step = 150 / (i + 1);
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000));
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000));
        rotateTransition.setCycleCount(7);
        rotateTransition.setToAngle(360);

        rotateTransition.setInterpolator(Interpolator.LINEAR);
        currentCard.setRotate(-80);
        rotateTransition.setFromAngle(0);
        rotateTransition.setNode(currentCard);
        rotateTransition.play();

        translate.setNode(currentCard);
        translate.setDelay(Duration.millis(1000 * i + 1000));
        rotateTransition.setDelay(Duration.millis(1000 * i + 1000));

        translate.setToX(160 + i * step);
        translate.setToY(400 - i * step);

        // Set the actual width and height of the card
        translate.setOnFinished(event -> {
            currentCard.setFitHeight(100);
            currentCard.setFitWidth(80);
            currentCard.toBack();
            currentCard.setRotate(0);
            inGamePlayers.get((mainPlayer + 3) % inGamePlayers.size()).getCardList().add(currentCard);
            arrangeCardsForLeftPlayer((mainPlayer + 3) % inGamePlayers.size());
            rotateTransition.stop();
        });

        translate.play();
    }


    /**
     * Set animation for distributing cards for upper player
     **/
    public void distributeCardsForUpperPlayer(int i) {
        Card currentCard = deck.drawTopCard();
        double step = 400 / (i + 1);
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000));
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000));
        rotateTransition.setCycleCount(7);
        rotateTransition.setToAngle(360);

        rotateTransition.setInterpolator(Interpolator.LINEAR);
        currentCard.setRotate(-80);
        rotateTransition.setFromAngle(0);
        rotateTransition.setNode(currentCard);
        rotateTransition.play();

        translate.setNode(currentCard);
        translate.setDelay(Duration.millis(1000 * i + 1000 * 3 / 4));
        rotateTransition.setDelay(Duration.millis(1000 * i + 750));

        translate.setToX(600 + i * step);
        translate.setToY(50);

        // Set the actual width and height of the card
        translate.setOnFinished(event -> {
            currentCard.setFitHeight(100);
            currentCard.setFitWidth(80);
            currentCard.toBack();
            currentCard.setRotate(0);
            inGamePlayers.get((mainPlayer + 2) % inGamePlayers.size()).getCardList().add(currentCard);
            arrangeCardsForUpperPlayer((mainPlayer + 2) % inGamePlayers.size());
            rotateTransition.stop();
        });

        translate.play();
    }

    /**
     * Animation for distribute 7 cards at the beginning
     **/
    public void createAnimationDistribute7Cards() {


        for (int i = 0; i < 7; i++) {

            distributeCardsForMainPlayer(i);
            distributeCardsForRightPlayer(i);
            distributeCardsForLeftPlayer(i);
            distributeCardsForUpperPlayer(i);

        }

    }


    /**
     * Create animation for cards going into the game board
     **/

    public void createAnimationGoToBoardForAllPlayer(int playerTh) {
        for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {
            createAnimationGoToBoardForAllCard(i, playerTh);

            if (playerTh == getUpperPlayer()) {
                arrangeCardsForUpperPlayer(playerTh);
            } else if (playerTh == getLeftPlayer()) {
                arrangeCardsForLeftPlayer(playerTh);
            } else
                arrangeCardsForRightPlayer(playerTh);
        }
    }

    public int getLeftPlayer() {
        int i = (mainPlayer + 3) % inGamePlayers.size();
        if (i < 0) {
            i += inGamePlayers.size();
        }
        return i;
    }

    public int getRightPlayer() {
        int i = (mainPlayer + 1) % inGamePlayers.size();
        if (i < 0) {
            i += inGamePlayers.size();
        }
        return i;
    }

    public int getUpperPlayer() {
        int i = (mainPlayer + 2) % inGamePlayers.size();
        if (i < 0) {
            i += inGamePlayers.size();
        }
        return i;
    }

    /**
     * Create animation for cards going into the game board
     **/

    public void createAnimationGoToBoardForAllCard(int i, int playerTh) {
        Random random = new Random();

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(700), inGamePlayers.get(playerTh).getCardList().get(i));

        RotateTransition rotator = new RotateTransition(Duration.millis(500), inGamePlayers.get(playerTh).getCardList().get(i));
        inGamePlayers.get(playerTh).getCardList().get(i).setFrontImage();

        inGamePlayers.get(playerTh).getCardList().get(i).toFront();

        if (playerTh == getRightPlayer()) {
            translateTransition.setToX(700 + random.nextInt(50));
            translateTransition.setToY(250 + random.nextInt(100));
        } else if (playerTh == getUpperPlayer()) {
            translateTransition.setToX(680 + random.nextInt(50));
            translateTransition.setToY(200 + random.nextInt(50));
        } else {
            translateTransition.setToX(600 + random.nextInt(50));
            translateTransition.setToY(250 + random.nextInt(100));
        }

        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);

        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(1);

        rotator.play();
        translateTransition.play();
        gameBoardService.setPreviousCard(inGamePlayers.get(playerTh).getCardList().get(i));

        translateTransition.setOnFinished(event -> {
//            inGamePlayers.get(playerTh).getCardList().get(i).setOnMouseClicked(null);
            inGamePlayers.get(playerTh).getCardList().get(i).setFitHeight(150);
            inGamePlayers.get(playerTh).getCardList().get(i).setFitWidth(120);
            inGamePlayers.get(playerTh).getCardList().remove(inGamePlayers.get(playerTh).getCardList().get(i));
            ArrayList<Card> temp = gameBoardService.getPlayedCards();
            temp.add(inGamePlayers.get(playerTh).getCardList().get(i));
            gameBoardService.setPlayedCards(temp);
        });
    }

    /**
     * GUI CARDS for left player
     **/
    public void arrangeCardsForLeftPlayer(int playerTh) {


        try {
            if (inGamePlayers.get(playerTh).getCardListSize() < 5) {

                for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {
                    setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(286 - i * 50);
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(274 + i * 50);
                    inGamePlayers.get(playerTh).getCardList().get(i).setRotate(-15);
                    inGamePlayers.get(playerTh).getCardList().get(i).toFront();

                }
            } else {
                double step = 150 / inGamePlayers.get(playerTh).getCardListSize();
                for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {

                    setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(286 - i * step);
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(274 + i * step);
                    inGamePlayers.get(playerTh).getCardList().get(i).setRotate(-15);
                    inGamePlayers.get(playerTh).getCardList().get(i).toFront();

                }
            }
        } catch (Exception e) {
        }

    }

    /**
     * GUI CARDS for right player
     **/
    public void arrangeCardsForRightPlayer(int playerTh) {


        try {
            if (inGamePlayers.get(playerTh).getCardListSize() < 5) {

                for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {
                    setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(1124 + i * 50);
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(274 + i * 50);
                    inGamePlayers.get(playerTh).getCardList().get(i).setRotate(15);
                    inGamePlayers.get(playerTh).getCardList().get(i).toFront();
                }
            } else {
                double step = 150 / inGamePlayers.get(playerTh).getCardListSize();
                for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {

                    setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(1124 + i * step);
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(274 + i * step);
                    inGamePlayers.get(playerTh).getCardList().get(i).setRotate(15);
                    inGamePlayers.get(playerTh).getCardList().get(i).toFront();
                }
            }
        } catch (Exception e) {
        }

    }

    /**
     * Gui for cards of main player
     **/

    public void arrangeCardsForMainPlayer(int playerTh) {
        //arrangeCardsForMainPlayer(true,mainPlayer,720, 120,150,400,660);

        // Set up main player

        try {
            step = 720.0 / inGamePlayers.get(playerTh).getCardListSize();

            if (inGamePlayers.get(playerTh).getCardListSize() < 3) {
                for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(400 + 100 + i * (step - 100));
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(660);
                    inGamePlayers.get(playerTh).getCardList().get(i).setFrontImage();
                    inGamePlayers.get(playerTh).getCardList().get(i).setFitWidth(120);
                    inGamePlayers.get(playerTh).getCardList().get(i).setFitHeight(150);
                    inGamePlayers.get(playerTh).getCardList().get(i).setSmooth(true);
                    inGamePlayers.get(playerTh).getCardList().get(i).setRotate(0);
                    inGamePlayers.get(playerTh).getCardList().get(i).toFront();
                }
            }
            for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {

                inGamePlayers.get(playerTh).getCardList().get(i).setFrontImage();
                inGamePlayers.get(playerTh).getCardList().get(i).setFitWidth(120);
                inGamePlayers.get(playerTh).getCardList().get(i).setFitHeight(150);
                inGamePlayers.get(playerTh).getCardList().get(i).setSmooth(true);
                inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(400 + i * step);
                inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(660);
                inGamePlayers.get(playerTh).getCardList().get(i).setRotate(0);
                inGamePlayers.get(playerTh).getCardList().get(i).toFront();


            }
        } catch (Exception e) {
        }

    }

    /**
     * GUI cards for Upper Player
     **/

    public void arrangeCardsForUpperPlayer(int playerTh) {

        try {
            double step = 400 / inGamePlayers.get(playerTh).getCardListSize();
            if (inGamePlayers.get(playerTh).getCardListSize() < 5) {
                for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {
                    setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(600 + 100 + i * (step - 150));
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(50);
                    inGamePlayers.get(playerTh).getCardList().get(i).setRotate(0);
                    inGamePlayers.get(playerTh).getCardList().get(i).toFront();
                }
            } else {

                for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {

                    setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(600 + i * step);
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(50);
                    inGamePlayers.get(playerTh).getCardList().get(i).setRotate(0);
                    inGamePlayers.get(playerTh).getCardList().get(i).toFront();


                }
            }

        } catch (Exception e) {
        }

    }

    /**
     * Set the properties for cards which is not main player
     **/
    public void setNotMainCards(Card card) {
        card.setFitWidth(80);
        card.setFitHeight(100);
        card.setSmooth(true);
    }





    public void setDrawCardAnimation(Card current, Card lastCard) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(current);
        current.toFront();
        current.setFrontImage();
        translate.setToX(lastCard.getTranslateX() + step);
        translate.setToY(lastCard.getTranslateY());

        // Set the actual width and height of the card
        current.setFitHeight(lastCard.getFitHeight());
        current.setFitWidth(lastCard.getFitWidth());
        current.setRotate(lastCard.getRotate()); // Rotate back to the right side
        translate.play();
    }

    /**
     * Display win and lose message to all players
     **/


    /**
     * Set animation for each selected card
     */
    public void setAnimation(int i) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), inGamePlayers.get(mainPlayer).getCardList().get(i));

        inGamePlayers.get(mainPlayer).getCardList().get(i).setOnMouseClicked(event -> {

            if (!inGamePlayers.get(mainPlayer).getCardList().get(i).getIfSelected()) {
                translateTransition.setByY(-40);
                translateTransition.play();
                inGamePlayers.get(mainPlayer).getCardList().get(i).setIfSelected(true);

                for (int j = 0; j < inGamePlayers.get(mainPlayer).getCardListSize(); j++) {

                    if (inGamePlayers.get(mainPlayer).getCardList().get(j).getIfSelected()) {
                        gameBoardService.setSelectedCard(inGamePlayers.get(mainPlayer).getCardList().get(j));
                        continue;
                    } else
                        inGamePlayers.get(mainPlayer).getCardList().get(j).setDisable(true);
                }
            } else {
                translateTransition.setByY(40);
                translateTransition.play();
                inGamePlayers.get(mainPlayer).getCardList().get(i).setIfSelected(false);
                gameBoardService.setSelectedCard(null);
                for (int j = 0; j < inGamePlayers.get(mainPlayer).getCardListSize(); j++) {
                    inGamePlayers.get(mainPlayer).getCardList().get(j).setDisable(false);
                }
            }
        });

    }

    /**
     * Network for remaining players
     **/
//    public void updateAnimationFromNetwork(int playerTh, int cardListSize, Card networkCard) {
//
//        // If cardList size is smaller than the the card list size in this scene, this player in this scene has already played a card
//        if (cardListSize < inGamePlayers.get(playerTh).getCardListSize()) {
//
//            // Check the location of the played card in the card list of that player
//            for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {
//
//                // If found, it will go into the board
//                if (networkCard.equals(inGamePlayers.get(playerTh).getCardList().get(i))) {
//                    playCard(networkCard);
//                    gameBoardService.isWinner(inGamePlayers.get(playerTh));
//                    gameBoardService.resetDeck();
//                    createAnimationGoToBoardForAllCard(i, playerTh);
//                    gameBoardService.updateTurn(); // Update next turn
//
//                    break;
//                }
//            }
//
//            // If cardListSize is bigger , then it must have drawn cards
//        } else if (cardListSize > inGamePlayers.get(playerTh).getCardListSize()) {
//
//            animationDrawCardForAllPlayer(cardListSize, playerTh); // Draw a card from a deck
//
//
//            // Arrange the card list again if it is drawn
//            if (playerTh == getLeftPlayer()) {
//
//                arrangeCardsForLeftPlayer(playerTh);
//
//            } else if (playerTh == getRightPlayer()) {
//
//                arrangeCardsForRightPlayer(playerTh);
//
//            } else {
//
//                arrangeCardsForUpperPlayer(playerTh);
//            }
//
//            updateTurn();
//        }
//    }
    public void animationDrawCardForAllPlayer(int i, int playerTh) {

        double step = 0;
        Card currentCard = deck.getCards().get(deck.getSize() - 1);
        TranslateTransition translate = new TranslateTransition(Duration.millis(1000));
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000));
        rotateTransition.setCycleCount(3);
        rotateTransition.setToAngle(360);

        rotateTransition.setInterpolator(Interpolator.LINEAR);
        currentCard.setRotate(-80);
        rotateTransition.setFromAngle(0);
        rotateTransition.setNode(currentCard);
        rotateTransition.play();

        translate.setNode(currentCard);

        if (playerTh == getLeftPlayer()) {

            step = 150 / (i + 1);
            translate.setToX(1250 - i * step);
            translate.setToY(400 - i * step);

        } else if (playerTh == getRightPlayer()) {

            step = 150 / (i + 1);
            translate.setToX(160 + i * step);
            translate.setToY(400 - i * step);

        } else {
            // For upper player to draw card
            step = 400 / (i + 1);
            translate.setToX(600 + i * step);
            translate.setToY(50);
        }


        // Set the actual width and height of the card
        translate.setOnFinished(event -> {
            currentCard.setFitHeight(100);
            currentCard.setFitWidth(80);
            currentCard.toFront();
            currentCard.setRotate(0);
            inGamePlayers.get(playerTh).getCardList().add(currentCard);
//            arrangeCardsForUpperPlayer(playerTh);
            rotateTransition.stop();
        });

        translate.play();
        deck.drawTopCard();
    }

    /**This method for the animation drawing cards in the GUI */
    public void animationForDrawingTwoOrFourCards ( int playerTH , int numberOfCards){
        for (int i = 0 ; i < numberOfCards; i ++){
            if (playerTH == getLeftPlayer()){
                distributeCardsForLeftPlayer(i);
            } else if (playerTH == getRightPlayer()){
                distributeCardsForRightPlayer(i);
            } else if (playerTH == getUpperPlayer()){
                distributeCardsForUpperPlayer(i);
            } else
                distributeCardsForMainPlayer(i);
        }
    }



    /**
     * Set animation for cards in main
     **/
    public void setAnimationForSelectedCard() {

        for (int i = 0; i < inGamePlayers.get(mainPlayer).getCardListSize(); i++) {
            setAnimation(i);
        }

    }

    /**
     * draw button
     **/
    public void drawAction(ActionEvent actionEvent) throws IOException {

        if (gameBoardService.getPositionOfCurrentPlayer() == mainPlayer) {
            if (gameBoardService.getSelectedCard() == null) {
                Card lastCard;
                if (gameBoardService.getInGamePlayers().get(mainPlayer).getCardListSize() == 1) {
                    lastCard = gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().get(gameBoardService.getInGamePlayers().get(mainPlayer).getCardListSize() - 1);

                } else {
                    lastCard = gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().get(gameBoardService.getInGamePlayers().get(mainPlayer).getCardListSize() - 2);

                }

                Card currentCard = gameBoardService.getDeck().drawTopCard();
                currentCard.setFrontImage();

                gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().add(currentCard);

                // This is where the animation appears
                setDrawCardAnimation(currentCard, lastCard);

                arrangeCardsForMainPlayer(mainPlayer);

                setAnimationForSelectedCard();
                gameBoardService.updateTurn(); // Update turn for next player
                gameBoardService.resetDeck();
                Message message = new Message("reset", gameBoardService.getDeck());
                clientController.writeMessage(message);
            }
        }
        Message message = new Message(clientController.getReplica(), "draw", 1);
        clientController.writeMessage(message);
    }

    /**
     * Event handler for the play button
     **/
    public void playAction(ActionEvent actionEvent) throws IOException {

        // This button is only used by main player
        if (gameBoardService.getPositionOfCurrentPlayer() == mainPlayer) {
            Random random = new Random();

            int location = 0;

            if (gameBoardService.getSelectedCard() != null) {

                for (int i = 0; i < gameBoardService.getInGamePlayers().get(mainPlayer).getCardListSize(); i++) {
                    if (gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().get(i).getIfSelected()) {
                        location = i;
                        break;
                    }
                }

                if (gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().get(location).isCardPlayable(gameBoardService.getPreviousCard())) {
                    // Set animation going into the board for main player's cards
                    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().get(location));
                    translateTransition.setToX(630 + random.nextInt(50));
                    translateTransition.setToY(350 + random.nextInt(50));
                    translateTransition.play();

                    gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().get(location).toFront();
                    ArrayList<Card> playedCards = gameBoardService.getPlayedCards();
                    playedCards.add(gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().get(location));
                    gameBoardService.setPlayedCards(playedCards);

                    // Set the previous card for the player
                    gameBoardService.setPreviousCard(gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().get(location));

                    gameBoardService.playCard(gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().get(location));

                    /** This is where the value of the card checked **/
                    if (gameBoardService.getPreviousCard().getValue() == Values.PLUS_TWO) {
                        Message message = new Message(clientController.getReplica() + gameBoardService.getDirectionOfPlay(), "draw", 2);
                        clientController.writeMessage(message);
                    } else if (gameBoardService.getPreviousCard().getValue() == Values.PLUS_FOUR) {
                        Message message = new Message(clientController.getReplica() + gameBoardService.getDirectionOfPlay(), "draw", 4);
                        clientController.writeMessage(message);
                    } else if (gameBoardService.getPreviousCard().getValue() == Values.REVERSE) {

                    }


                    gameBoardService.setSelectedCard(null);
                    gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().get(location).setOnMouseClicked(null);
                    gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().remove(location);

                    for (int j = 0; j < gameBoardService.getInGamePlayers().get(mainPlayer).getCardListSize(); j++) {
                        gameBoardService.getInGamePlayers().get(mainPlayer).getCardList().get(j).setDisable(false);
                    }

                    arrangeCardsForMainPlayer(mainPlayer);
                    setAnimationForSelectedCard();

                    if (gameBoardService.isWinner(gameBoardService.getInGamePlayers().get(mainPlayer))) {
                        displayWinnerMessage();

                    } else {

                        gameBoardService.updateTurn();
                        System.out.println("Direction: " + gameBoardService.getDirectionOfPlay());
                        System.out.println("Turn: " + gameBoardService.getPositionOfCurrentPlayer());
                    }

//                    gameBoardService.updateTurn(); // Update turn for next player
                    System.out.println("Direction: " + gameBoardService.getDirectionOfPlay());
                    System.out.println("Turns: " + gameBoardService.getPositionOfCurrentPlayer());
                }


            }
        }


        Message message = new Message(clientController.getReplica(), "play", gameBoardService.getPreviousCard());
        clientController.writeMessage(message);

        //        bot
        for (int i = 0; i < 3; i++) {
//            Card card = gameBoardService.getBots().get(i).draw(gameBoardService.getBots().get(i).play());
//            if ( card == null) {
//                gameBoardService.drawCard();
//            } else {
//                gameBoardService.playCard(card);
//            }
//            gameBoardService.isBotWinner(gameBoardService.getBots().get(i));
        }
    }


    public void animationDrawCards(int count, int playerTh) {
        for (int i = 0; i < count; i++) {
//            animationDrawCardForAllPlayer();
        }
    }

    /**
     * Network for update after 1 turn
     **/
    public void processMessage(Message message) {
        switch (message.getTypeOfAction()) {
            case "assign":
                processMessageAssign(message);
                break;
            case "initialize":
                processMessageInitialize(message);
                break;
            case "play":
                processMessageEachTurn(message);
                break;
            case "draw":
                processMessageEachTurn(message);
                break;
            case "reset":
                processMessageInitialize(message);
                break;
//            case "start":
//                mainMenu.setTotal(mainMenu.getTotal() + 1);
//                mainMenu.proccessMessageStart(message);
//                break;

//            case "login":
//                logInController.proccessMessageLogIn(message);
//                break;
        }
    }


    public void processMessageAssign(Message message) {
//        this.inGamePlayers.add(new Player(message.getPlayerIndex(), message.getName()));
    }

    public void processMessageEachTurn(Message message) {
        // If cardList size is smaller than the the card list size in this scene, this player in this scene has already played a card
        if (message.getNumOfCard() < gameBoardService.getInGamePlayers().get(message.getSender()).getCardListSize()) {

            // Check the location of the played card in the card list of that player
            for (int i = 0; i < gameBoardService.getInGamePlayers().get(message.getSender()).getCardListSize(); i++) {

                // If found, it will go into the board
                if (message.getCard().equals(gameBoardService.getInGamePlayers().get(message.getSender()).getCardList().get(i))) {
                    gameBoardService.playCard(message.getCard());

                    gameBoardService.isWinner(gameBoardService.getInGamePlayers().get(message.getSender()));
                    gameBoardService.resetDeck();
                    createAnimationGoToBoardForAllCard(i, message.getSender());
                    gameBoardService.updateTurn(); // Update next turn
                    break;
                }
            }

            // If cardListSize is bigger , then it must have drawn cards
        } else if (message.getNumOfCard() > gameBoardService.getInGamePlayers().get(message.getSender()).getCardListSize()) {
            for (int i = 0; i < message.getNumOfCard(); i++) {
                animationDrawCardForAllPlayer(message.getNumOfCard(), message.getSender()); // Draw a card from a gameBoardService.getDeck()
            }

            // Arrange the card list again if it is drawn
            if (message.getSender() == getLeftPlayer()) {

                arrangeCardsForLeftPlayer(message.getSender());

            } else if (message.getSender() == getRightPlayer()) {

                arrangeCardsForRightPlayer(message.getSender());

            } else {

                arrangeCardsForUpperPlayer(message.getSender());
            }
            gameBoardService.updateTurn();
        }
    }

    /**
     * Network for update initialize
     **/
    public void processMessageInitialize(Message message) {
        gameBoardService.setDeck(message.getDeck());
    }

    /**
     * Network for update initialize
     **/
    public void processMessageLeave(Message message) {
//        showMainMenu();
    }

    public void goBackHome(ActionEvent actionEvent) throws IOException {
        Message message = new Message("leave");
        clientController.writeMessage(message);

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("src/resources/view/GameBoard.fxml"));
        Scene scene = new Scene(view2);

        Stage newWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        newWindow.setScene(scene);
        newWindow.show();
    }

    // Display the result

    public void displayWinnerMessage() throws IOException {
    /** ingamePlayers.get(0) is not appropriate */
        if (gameBoardService.isWinner(inGamePlayers.get(0))) {
            Alert signInBox = new Alert(Alert.AlertType.INFORMATION);
            signInBox.setContentText("WINNER!!!!");
            signInBox.setResult(ButtonType.OK);
            signInBox.showAndWait();


        } else {
            Alert signInBox = new Alert(Alert.AlertType.INFORMATION);
            signInBox.setContentText("DEFEATED!!!!");
            signInBox.setResult(ButtonType.OK);
            signInBox.showAndWait();

        }

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("src/resources/view/GameBoard.fxml"));
        Scene scene = new Scene(view2);

        EventObject actionEvent = null;
        Stage newWindow = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        newWindow.setScene(scene);
        newWindow.show();
    }
}

