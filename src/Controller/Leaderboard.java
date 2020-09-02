package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class Leaderboard {
    @FXML
    private VBox leaderBoard;

    public void backMainMenu(ActionEvent actionEvent) {

    }

    public VBox getLeaderBoard() {
        return leaderBoard;
    }
}
