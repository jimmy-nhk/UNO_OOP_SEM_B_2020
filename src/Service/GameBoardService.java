package Service;

import Model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.Optional;

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


    public Card getSelectedCard() {
        return selectedCard;
    }

    public void setSelectedCard(Card selectedCard) {
        this.selectedCard = selectedCard;
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



    private void setPreviousCard(Card selectedCard) {
        this.previousCard = selectedCard;
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
}
