package Model;

import java.util.ArrayList;

public class GameBoard {

    // Data Fields:
    private ArrayList<Player> inGamePlayers;
    private ArrayList<Card> playedCards ;
    private Deck deck;
    private int directionOfPlay;
    private Card previousCard;
    private int positionOfCurrentPlayer;

    // Constructors
    GameBoard(){
        inGamePlayers = new ArrayList<>();
        playedCards = new ArrayList<>();
        deck = new Deck();
        directionOfPlay = 1;
        previousCard = null;
        positionOfCurrentPlayer = 0; // the first player of the list will play first
    }


    // Skip methods:
    public void skip (){

        // It is needed to update position twice since the 2 next player is allowed to play
        updateTurn();
        updateTurn();
        
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
    //   right here...
        setPreviousCard();
    }
    
//  +2   
    public void addTwo() {
        for (int i=0; i <2; i++) {
            inGamePlayers.get(positionOfCurrentPlayer + 1).drawcard;
        }
        skip();
    }

//   +4
    //   need for choose-color scene
    public void addFour() {
        for (int i=0; i <4; i++) {
            inGamePlayers.get(positionOfCurrentPlayer + 1).drawcard;
        }
        skip();
    //  right here...
        chooseColor();
    }
    
//    isCardPlayable
    public boolean isCardPlayable(Card card) {
        if (card.getValue().equals(previousCard.getValue()) || card.getProperty().equals(previousCard.getProperty())) {
            return true;
        } else if (card.getProperty().equals(Properties.WILD)) {
            return true;
        } else return false;
    }
}
