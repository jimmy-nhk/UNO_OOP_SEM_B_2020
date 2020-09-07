package Controller;

import Model.AccountList;
import Model.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;


public class LogInController {


    @FXML
    private TextField txtName;
    @FXML
    private PasswordField passwordField;

    @FXML
    private Pane logInController;

    private MainMenu mainMenu ;

    private String name ;
    private String password;
    private AccountList accountList;

//    LogInController(){
//        name = null;
//        password = null;
//        accountList = new AccountList(); // Load the account list from
//    }

    public void displaySignUpMessage(ActionEvent actionEvent) {

        if (accountList.signUp(name , password)){
            Alert signUpBox = new Alert(Alert.AlertType.CONFIRMATION);
            signUpBox.setContentText("Successfully created\nPlease sign in the previous scene");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
        } else {
            Alert signUpBox = new Alert(Alert.AlertType.ERROR);
            signUpBox.setContentText("This account is already created");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
        }

    }

    public void displaySignInMessage(ActionEvent actionEvent) {
        if (accountList.getAccountIndex(name,password) < 0) {
            Alert signUpBox = new Alert(Alert.AlertType.INFORMATION);
            signUpBox.setContentText("You may enter the wrong information\nOr the account does not exist");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
        } else {
            Alert signUpBox = new Alert(Alert.AlertType.CONFIRMATION);
            signUpBox.setContentText("Successfully sign in");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
            logInController.setVisible(false);
            mainMenu.getMainMenu().setVisible(true);
        }

    }

    public void getName(ActionEvent actionEvent) {
        name = txtName.getText();
    }

    public void getPassword(ActionEvent actionEvent) {
        password = passwordField.getText();
    }
}
