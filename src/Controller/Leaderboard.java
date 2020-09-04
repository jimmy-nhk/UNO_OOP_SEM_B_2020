package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class Leaderboard {
    public Button backButton;
    @FXML
    private VBox leaderBoard;
    private MainMenu mainMenu;

    public void backMainMenu(MouseEvent actionEvent) {
        mainMenu.getMainMenu().setVisible(true);
    }

    public VBox getLeaderBoard() {
        return leaderBoard;
    }
}
