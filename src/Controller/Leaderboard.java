package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class Leaderboard {

    @FXML
    private VBox leaderBoardScene;
    private MainMenu mainMenu;

    public void backMainMenu(ActionEvent actionEvent) {
        mainMenu.getMainMenu().setVisible(true);
    }

    public VBox getLeaderBoard() {
        return leaderBoardScene;
    }
}
