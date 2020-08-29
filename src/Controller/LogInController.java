package Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;


public class LogInController {


    public TextField txtName;
    public TextField txtPassword;

    public void displaySignUpMessage(ActionEvent actionEvent) {

        Alert signUpBox = new Alert(Alert.AlertType.INFORMATION);
        signUpBox.setContentText("Successfully created");
        signUpBox.setResult(ButtonType.OK);
        signUpBox.showAndWait();
    }

    public void displaySignInMessage(ActionEvent actionEvent) {
        Alert signInBox = new Alert(Alert.AlertType.INFORMATION);
        signInBox.setContentText("Successfully created");
        signInBox.setResult(ButtonType.OK);
        signInBox.showAndWait();

    }
}
