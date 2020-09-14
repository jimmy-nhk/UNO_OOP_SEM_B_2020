package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class Instruction {
    public Button backButton3;
    @FXML
    private VBox instruction;
    private MainMenu mainMenu;

    public void backMainMenu(ActionEvent actionEvent) {
        mainMenu.getMainMenu().setVisible(true);
        instruction.setVisible(false);
    }

    public VBox getInstruction() {
        return instruction;
    }
}
