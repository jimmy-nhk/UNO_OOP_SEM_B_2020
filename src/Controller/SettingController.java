package Controller;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class SettingController {
    @FXML
    private VBox settingBoard;
    private MainMenu mainMenu;

    public VBox getSettingBoard() {
        return settingBoard;
    }

    public void backMainMenu(MouseEvent mouseEvent) {
        settingBoard.setVisible(false);
        mainMenu.getMainMenu().setVisible(true);
    }
}
