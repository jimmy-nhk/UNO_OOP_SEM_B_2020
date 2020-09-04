package Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class InstructionController {
    public Button backButton;
    public TextArea instructionTextArea;
    @FXML
    private VBox instruction;
    private MainMenu mainMenu;

    public void backMainMenu(MouseEvent actionEvent) {
        mainMenu.getMainMenu().setVisible(true);
        instruction.setVisible(false);
    }

    public VBox getInstruction() {
        return instruction;
    }
}
