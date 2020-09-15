package Controller;

import Model.Sound;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingController implements Initializable {
    public Button Volume;
    public Button Language;
    public Button Theme;
    public Button backButton;
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



    public void setSoundBtnEventHandler() {
        this.Volume.setOnMouseClicked((MouseEvent e) -> {
            Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
        });
        this.Language.setOnMouseClicked((MouseEvent e) -> {
            Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
        });
        this.Theme.setOnMouseClicked((MouseEvent e) -> {
            Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
        });
        this.backButton.setOnMouseClicked((MouseEvent e) -> {
            Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSoundBtnEventHandler();
    }
}
