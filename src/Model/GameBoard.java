package Model;

import java.util.ArrayList;

public class GameBoard {

    // Data Fields:
    private ArrayList<Player> inGamePlayers;
    private Deck deck;
    private boolean inPlayDirection;
    private Card previousCard;
    private int positionOfCurrentPlayer;

    // Constructors
    GameBoard(){
        inGamePlayers = new ArrayList<>();
        deck = new Deck();
        inPlayDirection = true;
        previousCard = null;
        positionOfCurrentPlayer = 0; // the first player of the list will play first
    }


    // Skip methods:
    public void skip (){

        if (inPlayDirection){

            // If the direction is true, then it skips the next person
             positionOfCurrentPlayer = ( positionOfCurrentPlayer + 2 ) % inGamePlayers.size();

        } else {

            // If the direction is false, then it skips the next person which means -2 to get to that new player
            positionOfCurrentPlayer = ( positionOfCurrentPlayer - 2 ) % inGamePlayers.size();
        }

    }

    // Reverse tbe direction
    public void reverse () {

        inPlayDirection = !inPlayDirection;

    }

    // If the card is played, then update the turn
    public void updatePositionOfCurrentPlayer (){

        if (inPlayDirection){
            // if the direction is true, then move to the next current on the right side
            positionOfCurrentPlayer = ( positionOfCurrentPlayer + 1 ) % inGamePlayers.size();
        } else

            // if the direction is false, then move to the next current on the left side
            positionOfCurrentPlayer = ( positionOfCurrentPlayer - 1 ) % inGamePlayers.size();
    }

        //Check if the Card is empty or not.
    public boolean checkDeckIsEmpty(){
        if (deck.getSize() <= 4)
            return true;
        else
            return false;
    }

    // Player's card return to deck number 2
    public void resetDeck() {
        if (IsDeckEmpty()) {
           deck.getCards() = playedCards; // Change the first deck as second deck if first deck is empty
           playedCards.size() = deck.getSize();
        }
    }

}
