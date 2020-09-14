package Controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class SettingController {
    @FXML
    public Button theme;
    @FXML
    private Scene scene;
    private VBox settingBoard;
    private MainMenu mainMenu;


    //    public Test
    public VBox getSettingBoard() {
        return settingBoard;
    }

    public void backMainMenu(MouseEvent mouseEvent) {
        settingBoard.setVisible(false);
        mainMenu.getMainMenu().setVisible(true);
    }

    public void changeTheme(ActionEvent actionEvent){
        // bug
        scene.getStylesheets().clear();
        System.out.println("HELLO");
    }

}
