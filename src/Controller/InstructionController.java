package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class InstructionController {
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
