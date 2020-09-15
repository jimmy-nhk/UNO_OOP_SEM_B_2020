package Service;

import Model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class GameBoardService {
    private boolean isGameFinished;
    private int directionOfGame;
    private ArrayList<Player> players;
    private int positionOfCurrentPlayer;


    private ArrayList<Player> inGamePlayers;
    private ArrayList<Card> playedCards;
    private Deck deck;
    private int directionOfPlay;
    private Card previousCard;
    private Card selectedCard;


    public GameBoardService() {
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
        directionOfPlay = 1;
        previousCard = null;
        selectedCard = null;
        positionOfCurrentPlayer = 1;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public void playCard(Card selectedCard) {
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


    // Reverse tbe direction
    public void reverse() {
        directionOfPlay *= -1;
    }

    // If the card is played, then update the turn
    public void updateTurn() {

        // If the direction is in the right, the next player will plus 1 , otherwise - 1.
        positionOfCurrentPlayer = (positionOfCurrentPlayer + directionOfPlay) % inGamePlayers.size();

        if (positionOfCurrentPlayer < 0) {
            positionOfCurrentPlayer += inGamePlayers.size();
        }
    }

    //choose color
    //need for choose-color scene
    public void chooseColor() {
        Properties color = null;

        Alert colorBox = new Alert(Alert.AlertType.CONFIRMATION);
        colorBox.setTitle("COLOR SELECTION");
        colorBox.setHeaderText("You have chosen the Wild card !!!");
        colorBox.setContentText("Please choose the color for the next turn: ");
        ButtonType redButton = new ButtonType("RED");
        ButtonType blueButton = new ButtonType("BLUE");
        ButtonType greenButton = new ButtonType("GREEN");
        ButtonType yellowButton = new ButtonType("YELLOW");

        colorBox.getButtonTypes().clear();
        colorBox.getButtonTypes().addAll(redButton, blueButton, greenButton, yellowButton);

        // option != null.
        Optional<ButtonType> option = colorBox.showAndWait();

        if (option.get() == yellowButton) {
            color = Properties.YELLOW;
        } else if (option.get() == redButton) {
            color = Properties.RED;
        } else if (option.get() == blueButton) {
            color = Properties.BLUE;
        } else if (option.get() == greenButton) {
            color = Properties.GREEN;
        }

        previousCard.setProperty(color);

    }


    public void drawCard() {
        inGamePlayers.get(positionOfCurrentPlayer).drawCard(deck.drawTopCard());
        resetDeck();
    }


    //     set winner + update win & loss
    public boolean isWinner(Player player) {
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

    public void setPreviousCard(Card card) {
        playedCards.add(card);
        previousCard = card;
    }

    //  +2
    public void plusTwo() {
        for (int i = 0; i < 2; i++) {
            inGamePlayers.get(positionOfCurrentPlayer + 1).drawCard(deck.drawTopCard());
        }
        updateTurn();
    }

    //   +4
    //   need for choose-color scene
    public void plusFour() {
        for (int i = 0; i < 4; i++) {
            inGamePlayers.get(positionOfCurrentPlayer + 1).drawCard(deck.drawTopCard());
        }
        updateTurn();
        chooseColor();
    }

    // Player's card return to deck number 2
    public void resetDeck() {
        if (deck.getSize() < 4) {
            deck.getCards().addAll(playedCards); // Change the first deck as second deck if first deck is empty
            deck.shuffleDeck(); // shuffle the deck again
        }

        // Return all the cards back to not being selected
        for (int i = 0; i < deck.getSize(); i++) {
            deck.getCards().get(i).setIfSelected(false);
        }
    }

    public boolean isGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(boolean gameFinished) {
        isGameFinished = gameFinished;
    }

    public int getDirectionOfGame() {
        return directionOfGame;
    }

    public void setDirectionOfGame(int directionOfGame) {
        this.directionOfGame = directionOfGame;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public int getPositionOfCurrentPlayer() {
        return positionOfCurrentPlayer;
    }

    public void setPositionOfCurrentPlayer(int positionOfCurrentPlayer) {
        this.positionOfCurrentPlayer = positionOfCurrentPlayer;
    }

    public ArrayList<Player> getInGamePlayers() {
        return inGamePlayers;
    }

    public void setInGamePlayers(ArrayList<Player> inGamePlayers) {
        this.inGamePlayers = inGamePlayers;
    }

    public ArrayList<Card> getPlayedCards() {
        return playedCards;
    }

    public void setPlayedCards(ArrayList<Card> playedCards) {
        this.playedCards = playedCards;
    }

    public int getDirectionOfPlay() {
        return directionOfPlay;
    }

    public void setDirectionOfPlay(int directionOfPlay) {
        this.directionOfPlay = directionOfPlay;
    }

    public Card getPreviousCard() {
        return previousCard;
    }
}
