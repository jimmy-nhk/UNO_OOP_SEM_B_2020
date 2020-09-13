package Controller;

import Model.*;
import javafx.animation.FadeTransition;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static Model.Values.*;

public class GameBoard {
    public Pane playerFour;
    public Pane playerTwo;
    public Pane playerOne;
    public Pane playerThree;
    public Pane dropZone;
    public Button withDrawButton;
    public Button playButton;
    public Button homeButton;

    private ArrayList<Player> inGamePlayers = AccountList.getplayers();
    private ArrayList<Card> playedCards = new ArrayList<>();
    private Deck deck = new Deck();
    private int directionOfPlay = 1;
    private Card previousCard = null;
    private int positionOfCurrentPlayer = 0;
    private Card selectedCard = null;



    public void withdrawCardButton(ActionEvent actionEvent) {
        drawCard();
        updateTurn();
    }

    public void playCardButton(ActionEvent actionEvent) {
        playCard(selectedCard);
        isWinner(inGamePlayers.get(positionOfCurrentPlayer));
        resetDeck();
        updateTurn();
    }

    public void goBackHome(ActionEvent actionEvent) {}

    public Properties showColorScene (Properties color){
        return color;
    }

    public void reformat(ArrayList<Card> cardList) {
        if (cardList.size() <= 11) {
            for (int i = 0; i < cardList.size(); i++) {
                for (int j = 0; j < i; j++) {
                    cardList.get(j).setLayoutX(cardList.get(j).getLayoutX() - (120 / 4));
                }

                // Create transition path
                PathTransition pt = new PathTransition();
                pt.setDuration(Duration.millis(1000));
                Path path = new Path();
                path.getElements().add(new MoveTo(500, 75));
                path.getElements().add(new LineTo((360) + i * (120 / 4), 75));
                pt.setPath(path);
                pt.setNode(cardList.get(i));
                pt.setOrientation(PathTransition.OrientationType.NONE);
                //        Fade transition
                FadeTransition ft = new FadeTransition();
                ft.setDuration(Duration.millis(1000));
                ft.setFromValue(0.1);
                ft.setToValue(1);
                ft.setNode(cardList.get(i));
                ft.play();
                pt.play();

// MOve up
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(1000));
                Path pathTo = new Path();
                pathTo.getElements().add(new MoveTo((360) + i * (120 / 4), 75));
                pathTo.getElements().add(new LineTo((360) + i * (120 / 4), 30));
                pathTransition.setPath(pathTo);
                pathTransition.setNode(cardList.get(i));
                pathTransition.setOrientation(PathTransition.OrientationType.NONE);
// MOve down
                PathTransition pathTransitions = new PathTransition();
                pathTransitions.setDuration(Duration.millis(1000));
                Path pathTos = new Path();
                pathTos.getElements().add(new MoveTo((360) + i * (120 / 4), 30));
                pathTos.getElements().add(new LineTo((360) + i * (120 / 4), 75));
                pathTransitions.setPath(pathTos);
                pathTransitions.setNode(cardList.get(i));
                pathTransitions.setOrientation(PathTransition.OrientationType.NONE);

                /***set click***/
                AtomicInteger a = new AtomicInteger();
                AtomicBoolean b = new AtomicBoolean();
                a.addAndGet(i);
                cardList.get(i).setOnMouseClicked(e -> {
/****check playable***/
                    if (selectedCard.isCardPlayable(previousCard)) {
                        playButton.setDisable(true);
                    } else playButton.setDisable(false);
                    /****check playable***/
                    if (!b.get()) {
                        pathTransition.play();
                        setSelectedCard(cardList.get(a.get()));
                        for (int k = 0; k < inGamePlayers.get(0).getCardList().size(); k++) {
                            if (!cardList.get(k).equals(cardList.get(a.get()))) {
                                cardList.get(k).setDisable(true);
                            }
                        }
                        b.getAndSet(true);
                    } else {
                        pathTransitions.play();
                        setSelectedCard(null);
                        for (int k = 0; k < inGamePlayers.get(0).getCardList().size(); k++) {
                            if (!cardList.get(k).equals(cardList.get(a.get()))) {
                                cardList.get(k).setDisable(false);
                            }
                        }
                        b.getAndSet(false);
                    }
                });
            }
        } else {
            for (int i = 0; i < cardList.size(); i++) {
                TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000));
                inGamePlayers.get(0).getCardList().get(i).setTranslateX(i*((double)(720-120) / (double)(inGamePlayers.get(0).getCardList().size())));
                translateTransition.play();
                // Create transition path
                PathTransition pt = new PathTransition();
                pt.setDuration(Duration.millis(1000));
                Path path = new Path();
                path.getElements().add(new MoveTo(500, 75));
                path.getElements().add(new LineTo(360 + 300, 75));
                pt.setPath(path);
                pt.setNode(cardList.get(i));
                pt.setOrientation(PathTransition.OrientationType.NONE);
                //        Fade transition
                FadeTransition ft = new FadeTransition();
                ft.setDuration(Duration.millis(1000));
                ft.setFromValue(0.1);
                ft.setToValue(1);
                ft.setNode(cardList.get(i));
                ft.play();
                pt.play();

// MOve up
                PathTransition pathTransition = new PathTransition();
                pathTransition.setDuration(Duration.millis(1000));
                Path pathTo = new Path();
                pathTo.getElements().add(new MoveTo(360 +300, 75));
                pathTo.getElements().add(new LineTo(360 +300, 30));

                pathTransition.setPath(pathTo);
                pathTransition.setNode(cardList.get(i));
                pathTransition.setOrientation(PathTransition.OrientationType.NONE);
// MOve down
                PathTransition pathTransitions = new PathTransition();
                pathTransitions.setDuration(Duration.millis(1000));
                Path pathTos = new Path();
                pathTos.getElements().add(new MoveTo(360 +300, 30));
                pathTos.getElements().add(new LineTo(360 +300, 75));
                pathTransitions.setPath(pathTos);
                pathTransitions.setNode(cardList.get(i));
                pathTransitions.setOrientation(PathTransition.OrientationType.NONE);

                /***set click***/
                AtomicInteger a = new AtomicInteger();
                AtomicBoolean b = new AtomicBoolean();
                a.addAndGet(i);
                cardList.get(i).setOnMouseClicked(e -> {
                    /****check playable***/
                    if (selectedCard.isCardPlayable(previousCard)) {
                        playButton.setDisable(true);
                    } else playButton.setDisable(false);
                    /***check playable***/
                    if (!b.get()) {
                        pathTransition.play();
                        setSelectedCard(cardList.get(a.get()));
                        for (int k = 0; k < inGamePlayers.get(0).getCardList().size(); k++) {
                            if (!cardList.get(k).equals(cardList.get(a.get()))) {
                                cardList.get(k).setDisable(true);
                            }
                        }
                        b.getAndSet(true);
                    } else {
                        pathTransitions.play();
                        setSelectedCard(null);
                        for (int k = 0; k < inGamePlayers.get(0).getCardList().size(); k++) {
                            if (!cardList.get(k).equals(cardList.get(a.get()))) {
                                cardList.get(k).setDisable(false);
                            }
                        }
                        b.getAndSet(false);
                    }
                });
            }
        }
    }

        public Card getSelectedCard () {
            return selectedCard;
        }

        public void setSelectedCard (Card selectedCard){
            this.selectedCard = selectedCard;
        }

        // Reverse tbe direction
        public void reverse () {
            directionOfPlay *= -1;
        }

        // If the card is played, then update the turn
        public void updateTurn () {

            // If the direction is in the right, the next player will plus 1 , otherwise - 1.
            positionOfCurrentPlayer = (positionOfCurrentPlayer + directionOfPlay) % inGamePlayers.size();

        }

        //choose color
        //need for choose-color scene
        public void chooseColor () {
            Properties color = null;
            previousCard.setProperty(showColorScene(color));
        }

        //  +2
        public void plusTwo () {
            for (int i = 0; i < 2; i++) {
                inGamePlayers.get(positionOfCurrentPlayer + 1).drawCard(deck.drawTopCard());
            }
            updateTurn();
        }

        //   +4
        //   need for choose-color scene
        public void plusFour () {
            for (int i = 0; i < 4; i++) {
                inGamePlayers.get(positionOfCurrentPlayer + 1).drawCard(deck.drawTopCard());
            }
            updateTurn();
            //  right here...
            chooseColor();
        }

        // Player's card return to deck number 2
        public void resetDeck () {
            if (deck.getSize() < 4) {
                deck.getCards().addAll(playedCards); // Change the first deck as second deck if first deck is empty
                deck.shuffleDeck(); // shuffle the deck again
            }
        }


        public void drawCard () {
            inGamePlayers.get(positionOfCurrentPlayer).drawCard(deck.drawTopCard());
        }

        public void playCard (Card selectedCard){
//            if (!selectedCard.equals(null)) {
//                playedCards.add(inGamePlayers.get(positionOfCurrentPlayer).playCard(selectedCard));
//            } 
            switch (selectedCard.getValue()) {
                case SKIP:
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
        public boolean isWinner (Player player){
            if (player.getCardList().isEmpty()) {
                for (int i = 0; i < 4; i++) {
                    if (inGamePlayers.get(i).equals(player)) {
                        player.getAccount().setWin(player.getAccount().getWin() + 1);
                    } else {
                        player.getAccount().setLoss(player.getAccount().getLoss() + 1);
                    }
                }
                return true;
            }
            return false;
        }

        public void setPreviousCard (Card card){
            playedCards.add(card);
            previousCard = card;
        }

    /** Start the game with distribution cards to players **/
    /** First, to distribute 7 cards for each player
     And to put 1 card in the deck onto the table
     And choose 1 random player to start
     **/
    public void startGame () {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < inGamePlayers.size(); j++) {
                inGamePlayers.get(j).drawCard(deck.drawTopCard());
            }
        }
        for (int i = 0; i < 7; i++) {
            reformat(inGamePlayers.get(i).getCardList());
        }
        setPreviousCard(deck.drawTopCard());
        positionOfCurrentPlayer = (int) (Math.random() * (inGamePlayers.size()));
    }
}
