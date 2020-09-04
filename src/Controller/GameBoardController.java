package Controller;

import Model.Card;
import Model.GameBoard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class GameBoardController implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
