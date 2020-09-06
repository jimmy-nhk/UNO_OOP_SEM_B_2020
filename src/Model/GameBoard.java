package Model;

import Model.Card;
import Model.GameBoard;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

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

    private ArrayList<Player> inGamePlayers = AccountList.players;
    private ArrayList<Card> playedCards = new ArrayList<>();
    private Deck deck = new Deck();
    private int directionOfPlay = 1;
    private Card previousCard = null;
    private int positionOfCurrentPlayer = 0;
    private Card selectedCard = null;



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

    public void chooseCard(MouseEvent mouseEvent) {
        setSelectedCard();
    }

    public void goBackHome(ActionEvent actionEvent) { }

    public void chooseGreen(MouseEvent mouseEvent) {}
    public void chooseYellow(MouseEvent mouseEvent) {}
    public void chooseRed(MouseEvent mouseEvent) {}
    public void chooseBlue(MouseEvent mouseEvent) {}

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
        for (int i = 0 ; i < 7 ; i ++){
            for (int j = 0 ; j < inGamePlayers.size() ; j ++){
                inGamePlayers.get(i).drawCard(deck.drawTopCard());
            }
        }
        setPreviousCard(deck.drawTopCard());
        positionOfCurrentPlayer = (int) (Math.random() * (inGamePlayers.size()));
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
