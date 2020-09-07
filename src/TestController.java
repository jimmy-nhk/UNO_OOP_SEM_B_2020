import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class TestController {

    public TextField txtName;
    public PasswordField passwordField;
    public Label getName;
    public Label getPass;

    private String name ;
    private String password;

    public void displaySignUpMessage(ActionEvent actionEvent) {

        name = txtName.getText();
        password = passwordField.getText();

        getName.setText(name);
        getPass.setText(password);

            Alert signUpBox = new Alert(Alert.AlertType.ERROR);
            signUpBox.setContentText("This account is already created");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();


    }

    public void displaySignInMessage(ActionEvent actionEvent) {
        name = txtName.getText();
        password = passwordField.getText();

        getName.setText(name);
        getPass.setText(password);

            Alert signUpBox = new Alert(Alert.AlertType.CONFIRMATION);
            signUpBox.setContentText("Successfully sign in");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();


    }

    public void getName(ActionEvent actionEvent) {
        name = txtName.getText();
    }

    public void getPassword(ActionEvent actionEvent) {
        password = passwordField.getText();
    }
}
