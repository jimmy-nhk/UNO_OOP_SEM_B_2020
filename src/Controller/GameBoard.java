package Controller;

import Model.*;
import javafx.animation.RotateTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.Shadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameBoard implements Initializable {


    @FXML private Button btDraw;
    @FXML private Pane pane;

    private int mainPlayer ;
    private ArrayList<Player> inGamePlayers ;
    private ArrayList<Card> playedCards ;
    private Deck deck ;
    private int directionOfPlay ;
    private Card previousCard ;
    private int positionOfCurrentPlayer;
    private Card selectedCard ;


    public GameBoard(){
        mainPlayer = 1 ;
        inGamePlayers = new ArrayList<>();
        Player player = new Player();
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        inGamePlayers.add(player);
        inGamePlayers.add(player1);
        inGamePlayers.add(player2);
        inGamePlayers.add(player3);
        playedCards = new ArrayList<>();
        deck = new Deck();
        directionOfPlay = 1 ;
        previousCard = null;
        selectedCard = null;
        Random random = new Random();
        positionOfCurrentPlayer = 1 ;
    }

    public GameBoard( int mainPlayer) {
        this.mainPlayer = mainPlayer ;
        inGamePlayers = new ArrayList<>();
        Player player = new Player();
        Player player1 = new Player();
        Player player2 = new Player();
        Player player3 = new Player();
        inGamePlayers.add(player);
        inGamePlayers.add(player1);
        inGamePlayers.add(player2);
        inGamePlayers.add(player3);
        playedCards = new ArrayList<>();
        deck = new Deck();
        directionOfPlay = 1 ;
        previousCard = null;
        selectedCard = null;
        Random random = new Random();
        positionOfCurrentPlayer = random.nextInt(3) ;
    }

    /** Create animation for cards in the board **/

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setCardInDeck(); // Set up deck for drawing
        startGame(); // distribute cards at the beginning



        /** Set the location as well as the animation for the cards of the main player **/
        arrangeCardsForMainPlayer(mainPlayer);
        setAnimationForSelectedCard();

        /** Set the location  for the cards of the upper player **/
        arrangeCardsForUpperPlayer((mainPlayer + 2) % inGamePlayers.size());

        arrangeCardsForLeftPlayer((mainPlayer + 3) % inGamePlayers.size());

        arrangeCardsForRightPlayer((mainPlayer + 1) % inGamePlayers.size());
    }

    /** GUI CARDS for left player **/
    public void arrangeCardsForLeftPlayer (int playerTh) {

        double step =  150 / inGamePlayers.get(playerTh).getCardListSize();
        for (int i = 0 ; i < inGamePlayers.get(playerTh).getCardListSize(); i ++) {

            setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
            inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(200 + i * step);
            inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(400 - i * step);
            inGamePlayers.get(playerTh).getCardList().get(i).setRotate(-15);
            inGamePlayers.get(playerTh).getCardList().get(i).toBack();
        }
    }

    /** GUI CARDS for right player **/
    public void arrangeCardsForRightPlayer (int playerTh) {

        double step =  150 / inGamePlayers.get(playerTh).getCardListSize();
        for (int i = 0 ; i < inGamePlayers.get(playerTh).getCardListSize(); i ++) {

            setNotMainCards(inGamePlayers.get(playerTh).getCardList().get(i));
            inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(1250 - i * step);
            inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(400 - i * step);
            inGamePlayers.get(playerTh).getCardList().get(i).setRotate(15);
            inGamePlayers.get(playerTh).getCardList().get(i).toBack();
        }
    }

    public void setNotMainCards (Card card) {
        card.setFitWidth(80);
        card.setFitHeight(100);
        card.setSmooth(true);
    }

    /** GUI cards for main player **/
    double step;
    // Set up the location as well as arrange Cards
    public void arrangeCardsForMainPlayer (  int playerTh) {
        //arrangeCardsForMainPlayer(true,mainPlayer,720, 120,150,400,660);

        // Set up players in horizontal
        step =  720 / inGamePlayers.get(playerTh).getCardListSize();
        for (int i = 0 ; i < inGamePlayers.get(playerTh).getCardListSize() ; i ++){

            inGamePlayers.get(playerTh).getCardList().get(i).setFrontImage();
            inGamePlayers.get(playerTh).getCardList().get(i).setFitWidth(120);
            inGamePlayers.get(playerTh).getCardList().get(i).setFitHeight(150);
            inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(400 + i * step);
            inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(660);
            inGamePlayers.get(playerTh).getCardList().get(i).setRotate(0);
            inGamePlayers.get(playerTh).getCardList().get(i).toFront();

            if (inGamePlayers.get(playerTh).getCardListSize() < 5) {
                inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(400 + 100 + i * (step - 150) );
            }
        }
    }

    /**GUI cards for Upper Player**/

    public void arrangeCardsForUpperPlayer (int playerTh) {

        double step =  400 / inGamePlayers.get(playerTh).getCardListSize();
        for (int i = 0 ; i < inGamePlayers.get(playerTh).getCardListSize() ; i ++){

            inGamePlayers.get(playerTh).getCardList().get(i).setFitWidth(80);
            inGamePlayers.get(playerTh).getCardList().get(i).setFitHeight(100);
            inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(600 + i * step);
            inGamePlayers.get(playerTh).getCardList().get(i).setTranslateY(50);
            inGamePlayers.get(playerTh).getCardList().get(i).setRotate(0);
            inGamePlayers.get(playerTh).getCardList().get(i).toFront();

            if (inGamePlayers.get(playerTh).getCardListSize() < 5) {
                inGamePlayers.get(playerTh).getCardList().get(i).setTranslateX(600 + 100 + i * (step - 150) );
            }
        }
    }

    /** This method only applies to main player which sets the selected animation **/
    public void setAnimationForSelectedCard () {

        for (int i = 0 ; i < inGamePlayers.get(mainPlayer).getCardListSize(); i ++) {
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), inGamePlayers.get(mainPlayer).getCardList().get(i));
            AtomicBoolean check = new AtomicBoolean(false);
            int finalI = i;
            inGamePlayers.get(mainPlayer).getCardList().get(i).setOnMouseClicked(event -> {
                if (!check.get()) {
                    translateTransition.setByY(-40);
                    translateTransition.play();
                    check.set(true);
                    selectedCard = inGamePlayers.get(mainPlayer).getCardList().get(finalI);
                    for (int j = 0 ; j <  inGamePlayers.get(mainPlayer).getCardListSize(); j ++){
                        if (inGamePlayers.get(mainPlayer).getCardList().get(j).equals(selectedCard)){
                            continue;
                        } else
                            inGamePlayers.get(mainPlayer).getCardList().get(j).setDisable(true);
                    }
                } else {
                    translateTransition.setByY(40);
                    translateTransition.play();
                    selectedCard = null;
                    check.set(false);
                    for (int j = 0 ; j <  inGamePlayers.get(mainPlayer).getCardListSize(); j ++){
                        inGamePlayers.get(mainPlayer).getCardList().get(j).setDisable(false);
                    }
                }

            });
        }

    }

    // Set the animation for cards which are in deck
    public void setCardInDeck () {
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


    public void setDrawCardAnimation (Card current , Card lastCard ) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(current);
        current.toFront();
        current.setFrontImage();
        translate.setToX(lastCard.getTranslateX()  + step );
        translate.setToY(lastCard.getTranslateY());

        // Set the actual width and height of the card
        current.setFitHeight(lastCard.getFitHeight());
        current.setFitWidth(lastCard.getFitWidth());
        current.setRotate(lastCard.getRotate()); // Rotate back to the right side
        translate.play();
    }

    /** Event handler for buttons  **/

    public void drawAction(ActionEvent actionEvent) {
        if (selectedCard == null){
            Card lastCard = inGamePlayers.get(positionOfCurrentPlayer).getCardList().get(inGamePlayers.get(positionOfCurrentPlayer).getCardListSize() - 2);
            Card currentCard = deck.getCards().get(deck.getSize() - 1);
            currentCard.setFrontImage();
            inGamePlayers.get(positionOfCurrentPlayer).getCardList().add(currentCard);
            setDrawCardAnimation(currentCard,lastCard );
            deck.drawTopCard();
            arrangeCardsForMainPlayer(mainPlayer);
            setAnimationForSelectedCard();
        }
    }


    /** Methods deal with model **/

    public void withdrawCard(ActionEvent actionEvent) {
        drawCard();
        updateTurn();
    }

    public void playCard(ActionEvent actionEvent) {
        playCard(selectedCard);
        isWinner(inGamePlayers.get(positionOfCurrentPlayer));
        resetDeck();
        updateTurn();
    }



    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    /** Start the game with distribution cards to players **/
    /** First, to distribute 7 cards for each player
        And to put 1 card in the deck onto the table
        And choose 1 random player to start
    **/
    public void startGame (){
        for (int i = 0 ; i < inGamePlayers.size() ; i ++){
            for (int j = 0 ; j < 7 ; j ++){
                inGamePlayers.get(i).drawCard(deck.drawTopCard());
            }
        }
    }

    // Reverse tbe direction
    public void reverse () {
        directionOfPlay *= -1 ;
    }

    // If the card is played, then update the turn
    public void updateTurn (){

        // If the direction is in the right, the next player will plus 1 , otherwise - 1.
        positionOfCurrentPlayer = (positionOfCurrentPlayer + directionOfPlay) % inGamePlayers.size();

    }

    //choose color
    //need for choose-color scene
    public void chooseColor() {
        Properties color = null;
//        right here...
        previousCard.setProperty(color);
    }

    //  +2
    public void plusTwo() {
        for (int i=0; i <2; i++) {
            inGamePlayers.get(positionOfCurrentPlayer + 1).drawCard(deck.drawTopCard());
        }
        updateTurn();
    }

    //   +4
    //   need for choose-color scene
    public void plusFour() {
        for (int i=0; i <4; i++) {
            inGamePlayers.get(positionOfCurrentPlayer + 1).drawCard(deck.drawTopCard());
        }
        updateTurn();
        //  right here...
        chooseColor();
    }

    // Player's card return to deck number 2
    public void resetDeck() {
        if (deck.getSize() < 4){
            deck.getCards().addAll(playedCards) ; // Change the first deck as second deck if first deck is empty
            deck.shuffleDeck(); // shuffle the deck again
        }
    }


    public void drawCard() {
        inGamePlayers.get(positionOfCurrentPlayer).drawCard(deck.drawTopCard());
    }

    public void playCard(Card selectedCard) {
        playedCards.add(inGamePlayers.get(positionOfCurrentPlayer).playCard(selectedCard));
        switch (selectedCard.getValue()) {
            case  SKIP:
                updateTurn();
                break;
            case REVERSE:
                reverse();
                break;
            case PLUS_ZERO:
                chooseColor();
                break;
            case PLUS_TWO:
                plusTwo();
                break;
            case PLUS_FOUR:
                plusFour();
                break;
        }
        setPreviousCard(selectedCard);
    }

    //     set winner + update win & loss
    public boolean isWinner(Player player) {
        if (player.getCardList().isEmpty()) {
            for (int i = 0; i < 4; i ++) {
                if (inGamePlayers.get(i).equals(player)) {
                    player.getAccount().setWin(player.getAccount().getWin()+1);
                } else {
                    player.getAccount().setLoss(player.getAccount().getLoss()+1);
                }
            }
            return true;
        }
        return false;
    }

    public void setPreviousCard(Card card) {
        playedCards.add(card);
        previousCard = card;
    }





}
