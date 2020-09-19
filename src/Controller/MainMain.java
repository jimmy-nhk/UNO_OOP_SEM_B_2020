package Controller;

import Model.AccountList;
import Model.Message;
import Model.Sound;
import Model.Timer;
import Service.GameBoardService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainMain implements Initializable {

    @FXML public Pane logInScene;
    @FXML public VBox mainMenu;
    @FXML public VBox instruction;

    @FXML public VBox leaderboard;
    @FXML public VBox settingBoard;
    public Button startButton;
    public Button leaderBoard;
    public Button instructions;
    public Button settings;
    public Button quit;
    public Label welcome;
    public Label leaderBoardLabel;
    public Button backButton1;
    public Label InstructionLabel;
    public Button backButton2;
    public Label SettingLabel;
    public Button buttonVolume;
    public Button buttonLanguage;
    public Button buttonTheme;
    public Button backButton3;
    @FXML private Pane chooseModeScene;
    @FXML private Pane setNameScene;
    @FXML private AnchorPane readyScene;
    @FXML
    private TextField txtName;
    @FXML
    private PasswordField passwordField;

    private int total;
    private String name;
    private String password;
    private AccountList accountList;
    private Message message = new Message("start");
    public static Locale locale;
    public static Sound backGroundSound = new Sound("src/resources/sound/background.mp3");



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

    public void displaySignInMessage(ActionEvent actionEvent) throws IOException {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
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
            Message message = new Message("login",name,password);
            GameBoard.clientController.writeMessage(message);
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
        total ++;
        mainMenu.setVisible(false);
        readyScene.setVisible(true);
        if (getTotal()==4) {
//            start scene
        }
//        Parent view2 = FXMLLoader.load(getClass().getClassLoader().getResource("resources/view/GameBoard.fxml"));
//        Scene scene = new Scene(view2);
//
//        Stage newWindow = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
//        newWindow.setScene(scene);
//        newWindow.show();

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
        this.buttonVolume.setOnMouseClicked((MouseEvent e) -> {
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
            backGroundSound.stop();
            this.buttonVolume.setOnMouseClicked((MouseEvent e1) -> {
                Sound buttonSound2 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
                backGroundSound.play();
                setButtonActionForVolume();
            });
        });
    }

    private void setButtonActionForLanguage() {
        this.buttonLanguage.setOnMouseClicked((MouseEvent e) -> {
            setButtonBindingText();
            setLabelBindingText();

            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
            buttonLanguage.setText("Ngôn Ngữ");
            LanguageController.switchLanguage(LanguageController.getLanguageLocale(LanguageController.Language.VIETNAMESE));
            locale = LanguageController.getLocale();

            this.buttonLanguage.setOnMouseClicked((MouseEvent e1) -> {
                Sound buttonSound2 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
                buttonLanguage.setText("Language");
                LanguageController.switchLanguage(LanguageController.getLanguageLocale(LanguageController.Language.ENGLISH));
                locale = LanguageController.getLocale();

                setButtonActionForLanguage();
            });
        });
    }
    private void setButtonActionForTheme() {
        this.buttonTheme.setOnMouseClicked((MouseEvent e) -> {
            Sound buttonSound1 = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
        });
    }

    public void quit(ActionEvent actionEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
        System.exit(1);
    }


    /**************** SETUP BINDING TEXT **************/
    private void setButtonBindingText() {
        LanguageController.setUpButtonText(startButton, "menu.start");
        LanguageController.setUpButtonText(leaderBoard, "menu.leaderBoard");
        LanguageController.setUpButtonText(instructions, "menu.instruction");
        LanguageController.setUpButtonText(settings, "menu.setting");
        LanguageController.setUpButtonText(quit, "menu.quit");
        LanguageController.setUpButtonText(buttonTheme, "setting.Theme");
        LanguageController.setUpButtonText(backButton3, "setting.Back");
        LanguageController.setUpButtonText(backButton2, "setting.Back");
        LanguageController.setUpButtonText(backButton1, "leaderBoard.back");
        LanguageController.setUpButtonText(backButton2, "setting.BackButton");
        LanguageController.setUpButtonText(buttonVolume, "setting.Volume");
    }

    /**************** SETUP LABEL TEXT **************/
    private void setLabelBindingText() {
        LanguageController.setUpLabelText(welcome, "menu.welcome");
        LanguageController.setUpLabelText(leaderBoardLabel, "leaderBoard.LeaderBoard");
        LanguageController.setUpLabelText(InstructionLabel, "instruction.InstructionLabel");
        LanguageController.setUpLabelText(SettingLabel, "setting.SettingLabel");

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
        Timer timer = new Timer();
        timer.countTime();
        readyScene.getChildren().add(timer);
    }


    public void goToOnlineScene(ActionEvent actionEvent) {
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked
        GameBoardService.mode = true;
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
        Sound buttonSound = new Sound("src/resources/sound/sound_button_click.mp3");     //Make "button sound" when clicked

        logInScene.setVisible(false);
        setNameScene.setVisible(false);
        chooseModeScene.setVisible(true);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    //    start game message
    public void proccessMessageStart(Message message){
        this.message.setTotal(message.getTotal()+1);
        if (this.getTotal() == 4) {
//          start scene Main Game
        }
    }

    public void proccessMessageLogIn(Message message) {
        //
    }
}
