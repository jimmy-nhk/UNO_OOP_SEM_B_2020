package Controller;

import Model.AccountList;
import Model.Sound;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMain implements Initializable {

    @FXML public Pane logInScene;
    @FXML public VBox mainMenu;
    @FXML public VBox instruction;

    @FXML public VBox leaderboard;
    @FXML public VBox settingBoard;
    @FXML public Button volume;
    @FXML public Button language;
    @FXML public Button backButton3;
    @FXML public Button theme;
    @FXML public Button quit;
    public Button goOnlineScene;
    @FXML private Pane chooseModeScene;
    @FXML private Pane setNameScene;
    @FXML public static Sound backGroundSound = new Sound("src/resources/sound/background.mp3");

    @FXML
    private TextField txtName;
    @FXML
    private PasswordField passwordField;


    private String name;
    private String password;
    private AccountList accountList;


    public void displaySignUpMessage(ActionEvent actionEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        name = txtName.getText();
        password = passwordField.getText();

        if (AccountList.signUp(name, password)) {
            Alert signUpBox = new Alert(Alert.AlertType.CONFIRMATION);
            signUpBox.setContentText("Successfully created\nPlease sign in the previous scene");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        } else {
            Alert signUpBox = new Alert(Alert.AlertType.ERROR);
            signUpBox.setContentText("This account is already created");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        }

    }

    public void displaySignInMessage(ActionEvent actionEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
//        backGroundSound = new Sound("src/resources/sound/background.mp3");
        name = txtName.getText();
        password = passwordField.getText();

        if (!AccountList.signIn(name, password)) {
            Alert signUpBox = new Alert(Alert.AlertType.INFORMATION);
            signUpBox.setContentText("You may enter the wrong information\nOr the account does not exist");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        } else {
            Alert signUpBox = new Alert(Alert.AlertType.CONFIRMATION);
            signUpBox.setContentText("Successfully sign in");
            signUpBox.setResult(ButtonType.OK);
            signUpBox.showAndWait();
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

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

    public void playGame(ActionEvent actionEvent) throws IOException {
        /** Will go to the gameBoard or the selection scene **/
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("resources/view/GameBoard.fxml"));
        Scene scene = new Scene(view2);

        Stage newWindow = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        newWindow.setScene(scene);
        newWindow.show();


    }

    public void showLeaderBoard(ActionEvent actionEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        mainMenu.setVisible(false);
        leaderboard.setVisible(true);
    }

    public void showInstruction(ActionEvent actionEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        mainMenu.setVisible(false);
        instruction.setVisible(true);

    }

    public void showSettings(ActionEvent actionEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        mainMenu.setVisible(false);
        settingBoard.setVisible(true);

        setButtonActionForVolume();
        setButtonActionForLanguage();
        setButtonActionForTheme();
        this.backButton3.setOnMouseClicked(this::backMainMenuFromSetting);
    }

    private void setButtonActionForVolume() {
        this.volume.setOnMouseClicked((MouseEvent e) -> {
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
            volume.setText("VOLUME: OFF");
            backGroundSound.stop();
            this.volume.setOnMouseClicked((MouseEvent e1) -> {
                Sound buttonSound2 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
                volume.setText("VOLUME: ON");
                backGroundSound.play();
                setButtonActionForVolume();
            });
        });
    }

    private void setButtonActionForLanguage() {
        this.language.setOnMouseClicked((MouseEvent e) -> {
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
            language.setText("NGÔN NGỮ: VIỆT NAM");

            this.language.setOnMouseClicked((MouseEvent e1) -> {
                Sound buttonSound2 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
                language.setText("LANGUAGE: ENGLISH");
                setButtonActionForLanguage();
            });
        });
    }

    /**************** SETUP BINDING TEXT **************/
    private void setButtonBindingText(){
//    jnjhjh
    }


    private void setButtonActionForTheme() {
        this.theme.setOnMouseClicked((MouseEvent e) -> {
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
            theme.setText("THEME: 1");
            this.theme.setOnMouseClicked((MouseEvent e1) -> {
                Sound buttonSound2 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
                theme.setText("THEME: 2");
                setButtonActionForTheme();
            });
        });
    }

    public void quit(ActionEvent actionEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
        setButtonForQuitButton();
    }

    public void backMainMenuFromInstruction(MouseEvent mouseEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        instruction.setVisible(false);
        mainMenu.setVisible(true);
    }

    public void backMainMenuFromSetting(MouseEvent mouseEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        settingBoard.setVisible(false);
        mainMenu.setVisible(true);
    }

    public void backMainMenuFromLeaderboard(MouseEvent mouseEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        leaderboard.setVisible(false);
        mainMenu.setVisible(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    public void goToOnlineScene(ActionEvent actionEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        chooseModeScene.setVisible(false);
        logInScene.setVisible(true);
    }

    public void goToSetNameScene(ActionEvent actionEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        chooseModeScene.setVisible(false);
        setNameScene.setVisible(true);
    }

    public void goToMainMenu(ActionEvent actionEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        setNameScene.setVisible(false);
        mainMenu.setVisible(true);
    }

    public void backToModeScene(ActionEvent actionEvent) {
        logInScene.setVisible(false);
        setNameScene.setVisible(false);
        chooseModeScene.setVisible(true);
    }

    public void setButtonForQuitButton() {
        this.quit.setOnMouseClicked(e -> System.exit(1));
    }

}
