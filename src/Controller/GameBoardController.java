package Controller;

import Model.Card;
import Model.GameBoard;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class GameBoardController {
    public Pane playerFour;
    public Pane playerTwo;
    public Pane playerOne;
    public Pane playerThree;
    public Pane playedCards;
    public Button withDrawButton;
    public Button playButton;
    public Button homeButton;

    private GameBoard gameBoard;
    private Card selectedCard;

    public void withdrawCard(MouseEvent mouseEvent) {

    }

    public void playCard(MouseEvent mouseEvent) {
        selectedCard.isCardPlayable(selectedCard);
        gameBoard.playCard(selectedCard);
    }

    public void goBackHome(MouseEvent mouseEvent) {

    }
}
