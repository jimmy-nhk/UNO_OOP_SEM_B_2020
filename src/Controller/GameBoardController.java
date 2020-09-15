package Controller;

import Model.*;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {


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

    /**
     * Create animation for cards in the board
     **/

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setCardInDeck(); // Set up deck for drawing
        createAnimationDistribute7Cards();
    }

    /**
     * Set animation for distributing cards for main player
     **/
    public void distributeCardsForMainPlayer(int i) {

        Card currentCard = deck.getCards().get(deck.getSize() - 1);
        step = ( 720.0 - 120 ) / i;
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

        Card currentCard = deck.getCards().get(deck.getSize() - 1);
        double step = 150 / (i + 1);
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
        translate.setDelay(Duration.millis(1000 * i + 500));
        rotateTransition.setDelay(Duration.millis(1000 * i + 500));

        translate.setToX(1250 - i * step);
        translate.setToY(400 - i * step);

        // Set the actual width and height of the card
        translate.setOnFinished(event -> {
            currentCard.setFitHeight(100);
            currentCard.setFitWidth(80);
            currentCard.toFront();
            currentCard.setRotate(0);
            inGamePlayers.get((mainPlayer + 1) % inGamePlayers.size()).getCardList().add(currentCard);
            arrangeCardsForRightPlayer((mainPlayer + 1) % inGamePlayers.size());
            createAnimationGoToBoardForAllPlayer(getRightPlayer());
            rotateTransition.stop();
        });

        translate.play();
        deck.drawTopCard();
    }

    /**
     * Set animation for distributing cards for left player
     **/
    public void distributeCardsForLeftPlayer(int i) {

        Card currentCard = deck.getCards().get(deck.getSize() - 1);
        double step = 150 / (i + 1);
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
        translate.setDelay(Duration.millis(1000 * i + 1000));
        rotateTransition.setDelay(Duration.millis(1000 * i + 1000));

        translate.setToX(160 + i * step);
        translate.setToY(400 - i * step);

        // Set the actual width and height of the card
        translate.setOnFinished(event -> {
            currentCard.setFitHeight(100);
            currentCard.setFitWidth(80);
            currentCard.toFront();
            currentCard.setRotate(0);
            inGamePlayers.get((mainPlayer + 3) % inGamePlayers.size()).getCardList().add(currentCard);
            arrangeCardsForLeftPlayer((mainPlayer + 3) % inGamePlayers.size());
            createAnimationGoToBoardForAllPlayer((mainPlayer + 3) % inGamePlayers.size());
            rotateTransition.stop();
        });

        translate.play();
        deck.drawTopCard();
    }

    /**
     * Set animation for distributing cards for upper player
     **/
    public void distributeCardsForUpperPlayer(int i) {

        Card currentCard = deck.getCards().get(deck.getSize() - 1);
        double step = 400 / (i + 1);
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
        translate.setDelay(Duration.millis(1000 * i + 1000 * 3 / 4));
        rotateTransition.setDelay(Duration.millis(1000 * i + 750));

        translate.setToX(600 + i * step);
        translate.setToY(50);

        // Set the actual width and height of the card
        translate.setOnFinished(event -> {
            currentCard.setFitHeight(100);
            currentCard.setFitWidth(80);
            currentCard.toFront();
            currentCard.setRotate(0);
            inGamePlayers.get((mainPlayer + 2) % inGamePlayers.size()).getCardList().add(currentCard);
            arrangeCardsForUpperPlayer((mainPlayer + 2) % inGamePlayers.size());
            createAnimationGoToBoardForAllPlayer(getUpperPlayer());
            rotateTransition.stop();
        });

        translate.play();
        deck.drawTopCard();
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
//    public void createAnimationGoToBoardForLeftPlayer(int playerTh) {
//        for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {
//            createAnimationGoToBoardForAllCard(i,playerTh);
//            arrangeCardsForLeftPlayer(getLeftPlayer());
//
//        }
//    }
//
//    public void createAnimationGoToBoardForRightPlayer(int playerTh){
//        for (int i = 0 ; i <inGamePlayers.get(playerTh).getCardListSize(); i ++ ){
//            createAnimationGoToBoardForAllCard(i,playerTh);
//            arrangeCardsForRightPlayer(getRightPlayer());
//        }
//
//    }
//
//    public void createAnimationGoToBoardForUpperPlayer(int playerTh){
//        for (int i = 0 ; i <inGamePlayers.get(playerTh).getCardListSize(); i ++ ){
//            createAnimationGoToBoardForAllCard(i,playerTh);
//            arrangeCardsForUpperPlayer(getUpperPlayer());
//        }
//    }
    public void createAnimationGoToBoardForAllPlayer(int playerTh) {
        for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {
            createAnimationGoToBoardForAllCardTest(i, playerTh);

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
    public void createAnimationGoToBoardForAllCardTest(int i, int playerTh) {
        Random random = new Random();

        inGamePlayers.get(playerTh).getCardList().get(i).setOnMouseClicked(event -> {
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


            Reflection reflection = new Reflection();
            reflection.setFraction(0);
            inGamePlayers.get(playerTh).getCardList().get(i).setEffect(reflection);


            rotator.setAxis(Rotate.Y_AXIS);
            rotator.setFromAngle(0);
            rotator.setToAngle(360);


            rotator.setInterpolator(Interpolator.LINEAR);
            rotator.setCycleCount(1);

            rotator.play();
            translateTransition.play();
            previousCard = inGamePlayers.get(playerTh).getCardList().get(i);

            translateTransition.setOnFinished(event1 -> {
                inGamePlayers.get(playerTh).getCardList().get(i).setOnMouseClicked(null);
                inGamePlayers.get(playerTh).getCardList().get(i).setFitHeight(150);
                inGamePlayers.get(playerTh).getCardList().get(i).setFitWidth(120);
                inGamePlayers.get(playerTh).getCardList().remove(inGamePlayers.get(playerTh).getCardList().get(i));
                playedCards.add(inGamePlayers.get(playerTh).getCardList().get(i));
            });

        });
    }

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


        Reflection reflection = new Reflection();
        reflection.setFraction(0);
        inGamePlayers.get(playerTh).getCardList().get(i).setEffect(reflection);


        rotator.setAxis(Rotate.Y_AXIS);
        rotator.setFromAngle(0);
        rotator.setToAngle(360);


        rotator.setInterpolator(Interpolator.LINEAR);
        rotator.setCycleCount(1);

        rotator.play();
        translateTransition.play();
        previousCard = inGamePlayers.get(playerTh).getCardList().get(i);

        translateTransition.setOnFinished(event -> {
            inGamePlayers.get(playerTh).getCardList().get(i).setOnMouseClicked(null);
            inGamePlayers.get(playerTh).getCardList().get(i).setFitHeight(150);
            inGamePlayers.get(playerTh).getCardList().get(i).setFitWidth(120);
            inGamePlayers.get(playerTh).getCardList().remove(inGamePlayers.get(playerTh).getCardList().get(i));
            playedCards.add(inGamePlayers.get(playerTh).getCardList().get(i));
        });
    }

    /**
     * GUI CARDS for left player
     **/
    public void arrangeCardsForLeftPlayer(int playerTh) {
        Reflection reflection = new Reflection();
        reflection.setFraction(0.4);


        try {
            if (inGamePlayers.get(playerTh).getCardListSize() < 5) {

                for (int i = 0 ; i < inGamePlayers.get(playerTh).getCardListSize(); i ++) {
                    setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(160 + i * 50);
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(400 - i * 50);
                    inGamePlayers.get(playerTh).getCardList().get(i).setRotate(-15);
                    inGamePlayers.get(playerTh).getCardList().get(i).toBack();
                    inGamePlayers.get(playerTh).getCardList().get(i).setEffect(reflection);
                }
            } else {
                double step = 150 / inGamePlayers.get(playerTh).getCardListSize();
                for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {

                    setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(160 + i * step);
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(400 - i * step);
                    inGamePlayers.get(playerTh).getCardList().get(i).setRotate(-15);
                    inGamePlayers.get(playerTh).getCardList().get(i).toBack();
                    inGamePlayers.get(playerTh).getCardList().get(i).setEffect(reflection);

                }
            }
        } catch (Exception e) {
        }

    }

    /**
     * GUI CARDS for right player
     **/
    public void arrangeCardsForRightPlayer(int playerTh) {
        Reflection reflection = new Reflection();
        reflection.setFraction(0.4);

        try {
            if (inGamePlayers.get(playerTh).getCardListSize() < 5) {

                for (int i = 0 ; i < inGamePlayers.get(playerTh).getCardListSize(); i ++) {
                    setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(1250 - i * 50);
                    inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(400 - i * 50);
                    inGamePlayers.get(playerTh).getCardList().get(i).setRotate(15);
                    inGamePlayers.get(playerTh).getCardList().get(i).toBack();
                    inGamePlayers.get(playerTh).getCardList().get(i).setEffect(reflection);
                }
            } else {
                double step = 150 / inGamePlayers.get(playerTh).getCardListSize();
                for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {

                setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
                inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(1250 - i * step);
                inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(400 - i * step);
                inGamePlayers.get(playerTh).getCardList().get(i).setRotate(15);
                inGamePlayers.get(playerTh).getCardList().get(i).toBack();
                inGamePlayers.get(playerTh).getCardList().get(i).setEffect(reflection);
            }
        } } catch (Exception e) {
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
     * Event handler for buttons
     **/

    public void drawAction(ActionEvent actionEvent) {

        if (positionOfCurrentPlayer == mainPlayer) {
            if (selectedCard == null) {
                Card lastCard;
                if (inGamePlayers.get(mainPlayer).getCardListSize() == 1) {
                    lastCard = inGamePlayers.get(mainPlayer).getCardList().get(inGamePlayers.get(mainPlayer).getCardListSize() - 1);

                } else {
                    lastCard = inGamePlayers.get(mainPlayer).getCardList().get(inGamePlayers.get(mainPlayer).getCardListSize() - 2);

                }
                Card currentCard = deck.getCards().get(deck.getSize() - 1);
                currentCard.setFrontImage();
                inGamePlayers.get(mainPlayer).getCardList().add(currentCard);
                setDrawCardAnimation(currentCard, lastCard);
                deck.drawTopCard();
                arrangeCardsForMainPlayer(mainPlayer);
                setAnimationForSelectedCard();
                updateTurn(); // Update turn for next player
                
            }
        }
    }

    /**
     * Display win and lose message to all players
     **/
    private void displayResult() {
    }

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
                        selectedCard = inGamePlayers.get(mainPlayer).getCardList().get(j);
                        continue;
                    } else
                        inGamePlayers.get(mainPlayer).getCardList().get(j).setDisable(true);
                }
            } else {
                translateTransition.setByY(40);
                translateTransition.play();
                inGamePlayers.get(mainPlayer).getCardList().get(i).setIfSelected(false);
                selectedCard = null;
                for (int j = 0; j < inGamePlayers.get(mainPlayer).getCardListSize(); j++) {
                    inGamePlayers.get(mainPlayer).getCardList().get(j).setDisable(false);
                }
            }
        });

    }

    /**
     * Network for remaining players
     **/
    public void updateAnimationFromNetwork(int playerTh, int cardListSize, Card networkCard) {

        // If cardList size is smaller than the the card list size in this scene, this player in this scene has already played a card
        if (cardListSize < inGamePlayers.get(playerTh).getCardListSize()) {

            // Check the location of the played card in the card list of that player
            for (int i = 0; i < inGamePlayers.get(playerTh).getCardListSize(); i++) {

                // If found, it will go into the board
                if (networkCard.equals(inGamePlayers.get(playerTh).getCardList().get(i))) {
                    playCard(networkCard);
                    isWinner(inGamePlayers.get(playerTh));
                    resetDeck();
                    createAnimationGoToBoardForAllCard(i, playerTh);
                    updateTurn(); // Update next turn

                    break;
                }
            }

            // If cardListSize is bigger , then it must have drawn cards
        } else if (cardListSize > inGamePlayers.get(playerTh).getCardListSize()) {
            
                animationDrawCardForAllPlayer(cardListSize,playerTh); // Draw a card from a deck
       

            // Arrange the card list again if it is drawn
            if (playerTh == getLeftPlayer()) {

                arrangeCardsForLeftPlayer(playerTh);

            } else if (playerTh == getRightPlayer()) {

                arrangeCardsForRightPlayer(playerTh);

            } else {

                arrangeCardsForUpperPlayer(playerTh);
            }

            updateTurn();
        }
    }

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
            createAnimationGoToBoardForAllPlayer(getUpperPlayer());
            rotateTransition.stop();
        });

        translate.play();
        deck.drawTopCard();
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
     * Event handler for the play button
     **/
    public void playAction(ActionEvent actionEvent) {

        // This button is only used by main player
        if (positionOfCurrentPlayer == mainPlayer) {
            Random random = new Random();

            int location = 0;

            if (selectedCard != null) {

                for (int i = 0; i < inGamePlayers.get(mainPlayer).getCardListSize(); i++) {
                    if (inGamePlayers.get(mainPlayer).getCardList().get(i).getIfSelected()) {
                        location = i;
                        break;
                    }
                }

                if (inGamePlayers.get(mainPlayer).getCardList().get(location).isCardPlayable(previousCard)) {
                    // Set animation going into the board for main player's cards
                    TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), inGamePlayers.get(mainPlayer).getCardList().get(location));
                    translateTransition.setToX(630 + random.nextInt(50));
                    translateTransition.setToY(350 + random.nextInt(50));
                    translateTransition.play();

                    inGamePlayers.get(mainPlayer).getCardList().get(location).toFront();
                    playedCards.add(inGamePlayers.get(mainPlayer).getCardList().get(location));

                    // Set the previous card for the player
                    previousCard = (inGamePlayers.get(mainPlayer).getCardList().get(location));

                    playCard(inGamePlayers.get(mainPlayer).getCardList().get(location));
                    selectedCard = null;
                    inGamePlayers.get(mainPlayer).getCardList().get(location).setOnMouseClicked(null);
                    inGamePlayers.get(mainPlayer).getCardList().remove(location);
                    for (int j = 0; j < inGamePlayers.get(mainPlayer).getCardListSize(); j++) {
                        inGamePlayers.get(mainPlayer).getCardList().get(j).setDisable(false);
                    }
                    arrangeCardsForMainPlayer(mainPlayer);
                    setAnimationForSelectedCard();
                    
                    if (isWinner(inGamePlayers.get(mainPlayer))) {
                    displayResult();
                } else {
                    updateTurn();
                    System.out.println("Direction: " + directionOfPlay);
                    System.out.println("Turn: " + positionOfCurrentPlayer);
                }
                    updateTurn(); // Update turn for next player
                    System.out.println("Direction: " + directionOfPlay);
                    System.out.println("Turns: " + positionOfCurrentPlayer);
                }


            }
        }
    }


    public void processMessage(Message message) {
        switch (message.getAction()) {
            case "initialize":
                initializePlayer(message.getCardList());
                System.out.println("initialize player");
            case "play":
                System.out.println("play card");
        }
    }


    /***
     public void goBackHome(ActionEvent actionEvent) {
     //        network
     showMainMenu();
     }

     private MainMenu mainMenu;
     private AnchorPane gameBoard;

     public void showMainMenu() {
     mainMenu.getMainMenu().setVisible(true);
     gameBoard.setVisible(false);
     }

     public void displayWinnerMessage(ActionEvent actionEvent) {
     if (isWinner(inGamePlayers.get(0))) {
     Alert signInBox = new Alert(Alert.AlertType.INFORMATION);
     signInBox.setContentText("WINNER!!!!");
     signInBox.setResult(ButtonType.OK);
     Optional<ButtonType> result = signInBox.showAndWait();
     if(!result.isPresent()) {
     showMainMenu();
     } else if(result.get() == ButtonType.OK)
     showMainMenu();
     } else {
     Alert signInBox = new Alert(Alert.AlertType.INFORMATION);
     signInBox.setContentText("DEFEATED!!!!");
     signInBox.setResult(ButtonType.OK);
     Optional<ButtonType> result = signInBox.showAndWait();
     if(!result.isPresent()) {
     showMainMenu();
     } else if(result.get() == ButtonType.OK)
     showMainMenu();
     }
     }
     ***/


}