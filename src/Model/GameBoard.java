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
    private Card selectedCard;

    // Constructors
    GameBoard(){
        inGamePlayers = new ArrayList<>();
        playedCards = new ArrayList<>();
        deck = new Deck();
        directionOfPlay = 1;
        previousCard = null;
        positionOfCurrentPlayer = 0; // the first player of the list will play first
    }
    
//     Getter and Setter
     public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
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
//         TO form a loop
        positionOfCurrentPlayer = ( positionOfCurrentPlayer + directionOfPlay ) % inGamePlayers.size() ;
    }
    
    //choose color   
    //need for choose-color scene
    public void chooseColor() {
        Properties color = null;
//        right here...
        previousCard.setProperty(color);
    }
    
//  +2   
    public void addTwo() {
        for (int i=0; i <2; i++) {
            inGamePlayers.get(positionOfCurrentPlayer + 1).drawcard();
        }
        skip();
    }

//   +4
    //   need for choose-color scene
    public void addFour() {
        for (int i=0; i <4; i++) {
            inGamePlayers.get(positionOfCurrentPlayer + 1).drawcard();
        }
        skip();
    //  right here...
        chooseColor();
    }

    //Check if the Card is empty or not.
    public boolean isEmptyDeck(){
        if (deck.getSize() < 4)
            return true;
        else
            return false;
    }

    // Player's card return to deck number 2
    public void resetDeck() {
        if (isEmptyDeck()){
            deck.getCards().addAll(playedCards) ; // Change the first deck as second deck if first deck is empty
            deck.shuffleDeck(); // shuffle the deck again
        }
    }
    
    public void drawCard() {
        inGamePlayers.get(positionOfCurrentPlayer).drawCard(deck.drawTopCard());
        //*  isEmptyDeck();
        //* resetDeck(); // if the hand-out deck is empty, merge the shuffle the played cards and reuse them
        //*       updateTurn(); // update the position of the next player
    }

    public void playCard(Card selectedCard) {
        playedCards.add(inGamePlayers.get(positionOfCurrentPlayer).playCard());
        setPreviousCard(selectedCard);
        //* updateTurn();
        //* isWinner(inGamePlayers.get(positionOfCurrentPlayer));
        //* isEmptyDeck();
        //* resetDeck();
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
        previousCard = card;
    }
}
