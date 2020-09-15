package Controller;

import Model.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Instruction implements Initializable {
    @FXML public Button backButton;
    @FXML
    private VBox instruction;
    private MainMenu mainMenu;

    public void backMainMenu(ActionEvent actionEvent) {
        mainMenu.getMainMenu().setVisible(true);
        instruction.setVisible(false);
    }
    public void setSoundBtnEventHandler() {
        this.backButton.setOnMouseClicked((MouseEvent e) -> {
            Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setSoundBtnEventHandler();
    }

    public VBox getInstruction() {
        return instruction;
    }
}
