package Controller;

import Model.AccountList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMain implements Initializable {

    @FXML public Pane logInScene;
    @FXML public VBox mainMenu;
    @FXML public VBox instruction;

    @FXML public VBox leaderboard;
    @FXML public VBox settingBoard;
    @FXML private Pane chooseModeScene;
    @FXML private Pane setNameScene;

    @FXML
    private TextField txtName;
    @FXML
    private PasswordField passwordField;


    private String name;
    private String password;
    private AccountList accountList;


    public void displaySignUpMessage(ActionEvent actionEvent) {

        name = txtName.getText();
        password = passwordField.getText();

        if (AccountList.signUp(name, password)) {
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
        name = txtName.getText();
        password = passwordField.getText();

        if (!AccountList.signIn(name, password)) {
            Alert signUpBox = new Alert(Alert.AlertType.INFORMATION);
            signUpBox.setContentText("You may enter the wrong information\nOr the account does not exist");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
        } else {
            Alert signUpBox = new Alert(Alert.AlertType.CONFIRMATION);
            signUpBox.setContentText("Successfully sign in");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
            logInScene.setVisible(false);
            mainMenu.setVisible(true);
        }

    }

    public void getName(ActionEvent actionEvent) {
        name = txtName.getText();
    }

    public void getPassword(ActionEvent actionEvent) {
        password = passwordField.getText();
    }

    public void playGame(ActionEvent actionEvent) {
        /** Will go to the gameBoard or the selection scene **/


    }

    public void showLeaderBoard(ActionEvent actionEvent) {
        mainMenu.setVisible(false);
        leaderboard.setVisible(true);
    }

    public void showInstruction(ActionEvent actionEvent) {
        mainMenu.setVisible(false);
        instruction.setVisible(true);

    }

    public void showSettings(ActionEvent actionEvent) {
        mainMenu.setVisible(false);
        settingBoard.setVisible(true);
    }

    public void quit(ActionEvent actionEvent) {
    }

    public void backMainMenuFromInstruction(MouseEvent mouseEvent) {
        instruction.setVisible(false);
        mainMenu.setVisible(true);
    }

    public void backMainMenuFromSetting(MouseEvent mouseEvent) {
        settingBoard.setVisible(false);
        mainMenu.setVisible(true);
    }

    public void backMainMenuFromLeaderboard(MouseEvent mouseEvent) {
        leaderboard.setVisible(false);
        mainMenu.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void goToOnlineScene(ActionEvent actionEvent) {
        chooseModeScene.setVisible(false);
        logInScene.setVisible(true);
    }

    public void goToSetNameScene(ActionEvent actionEvent) {
        chooseModeScene.setVisible(false);
        setNameScene.setVisible(true);
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        setNameScene.setVisible(false);
        mainMenu.setVisible(true);
    }

    public void backToModeScene(ActionEvent actionEvent) {
        logInScene.setVisible(false);
        setNameScene.setVisible(false);
        chooseModeScene.setVisible(true);
    }


}
