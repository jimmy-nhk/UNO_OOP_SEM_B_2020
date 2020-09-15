package Controller;

import Model.AccountList;
import Model.Player;
import Model.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;


public class LogInController {

    @FXML public Button btSignIn;
    @FXML public Button btSignUp;
    public Pane logInScene;
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
            Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

            signUpBox.setContentText("Successfully created\nPlease sign in the previous scene");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        } else {
            Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

            Alert signUpBox = new Alert(Alert.AlertType.ERROR);
            signUpBox.setContentText("This account is already created");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        }

    }

    public void displaySignInMessage(ActionEvent actionEvent) {
        if (accountList.getAccountIndex(name) < 0) {
            Alert signUpBox = new Alert(Alert.AlertType.INFORMATION);
            Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked


            signUpBox.setContentText("You may enter the wrong information\nOr the account does not exist");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        } else {
            Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

            Alert signUpBox = new Alert(Alert.AlertType.CONFIRMATION);
            signUpBox.setContentText("Successfully sign in");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked


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
