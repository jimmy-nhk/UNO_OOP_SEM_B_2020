/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020B
  Assessment: Final Project
 Team members:
        1. Nguyen Dang Huynh Chau - s3777214
        2. Nguyen Hoang Khang - s3802040
        3. Nguyen Dinh Dang Nguyen - s3759957
        4. Bui Thanh Huy - s3740934
        5. Nguyen Phuoc Nhu Phuc - s3819660
  Acknowledgement:
https://www.geeksforgeeks.org/stack-class-in-java/ - Stack Class in Java
https://www.javatpoint.com/enum-in-java#:~:text=Java%20Enums%20can%20be%20thought,own%20data%20type%20like%20classes. - Java Enums
https://www.geeksforgeeks.org/collections-shuffle-java-examples/ - Collections.shuffle() in Java with Examples
https://www.javatpoint.com/java-map - Java Map Interface
https://www.geeksforgeeks.org/serialization-in-java/ - Serialization and Deserialization in Java with Example
https://www.baeldung.com/a-guide-to-java-sockets - A Guide to Java Sockets
https://www.educba.com/javafx-alert/ - Introduction to JavaFX Alert
https://www.tutorialspoint.com/java/util/java_util_locale.htm - Java.util.Locale Class
https://stackoverflow.com/questions/22490064/how-to-use-javafx-mediaplayer-correctly - How to use JavaFX MediaPlayer correctly?
https://www.geeksforgeeks.org/javafx-duration-class/ - JavaFX | Duration Class
*/

package Controller;

import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Locale;

public class SettingsController {
    @FXML
    private Slider sliderNumberOfBots;
    @FXML
    private Slider sliderNumberOfStartingCards;
    @FXML
    public RadioButton englishButton;
    @FXML
    public RadioButton vietnameseButton;
    @FXML
    public Button saveButton;
    @FXML
    public Label languageLabel;
    @FXML
    public Label volumeLabel;
    @FXML
    public Label settingLabel;
    @FXML
    public Label opponentLabel;
    @FXML
    public Label startingCardLabel;
    @FXML
    public Slider volumeSlider = new Slider();

    private Stage stage;
    private MainController mainController;
    public static Locale locale = new Locale("en", "US");

    public void init(Stage stage, MainController mainController) {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        this.stage = stage;
        this.stage.setMinHeight(600);
        this.mainController = mainController;

        ToggleGroup group = new ToggleGroup();
        vietnameseButton.setToggleGroup(group);
        englishButton.setToggleGroup(group);


        Settings settings = mainController.settings;

        sliderNumberOfBots.setValue(settings.getNumberOfBots());
        sliderNumberOfStartingCards.setValue(settings.getNumberOfStartingCards());
    }

    public void save() {
        int numberOfAIs = (int) sliderNumberOfBots.getValue();
        int numberOfStartingCards = (int) sliderNumberOfStartingCards.getValue();

        volumeSlider.setOnMouseClicked(e -> setActionForVolumeSlider());
        DoubleProperty volume = volumeSlider.valueProperty();
        int volumeAmount = volumeSlider.valueProperty().intValue();


        vietnameseButton.setOnAction(e -> locale = new Locale("vi", "VN"));
        englishButton.setOnAction(e -> locale = new Locale("en", "US"));

        MainController.backgroundMusic.adjustMusicVolume(volume);

        mainController.settings = new Settings(numberOfAIs, numberOfStartingCards, locale, volumeAmount);
        try {
            mainController.settings.save();
            mainController.settings.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.close();
    }

    @FXML
    private void setActionForVolumeSlider() {
        volumeSlider.setValue(volumeSlider.getValue());
    }

    @FXML
    public void changeLanguageToVietnamese(ActionEvent actionEvent) {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");
        LanguageController.switchLanguage(LanguageController.getLanguageLocale(LanguageController.Language.VIETNAMESE));
        locale = LanguageController.getLocale();
        setButtonBindingText();
        setLabelBindingText();
    }

    @FXML
    public void changeLanguageToEnglish(ActionEvent actionEvent) {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");
        LanguageController.switchLanguage(LanguageController.getLanguageLocale(LanguageController.Language.ENGLISH));
        locale = LanguageController.getLocale();
        setButtonBindingText();
        setLabelBindingText();

    }

    private void setLabelBindingText() {
        LanguageController.setUpLabelText(settingLabel, "setting.settingLabel");
        LanguageController.setUpLabelText(opponentLabel, "setting.opponent");
        LanguageController.setUpLabelText(startingCardLabel, "setting.startingCards");
        LanguageController.setUpLabelText(volumeLabel, "setting.volume");
        LanguageController.setUpLabelText(languageLabel, "setting.language");


    }

    private void setButtonBindingText() {
        LanguageController.setUpRadioButtonText(englishButton, "setting.english");
        LanguageController.setUpRadioButtonText(vietnameseButton, "setting.vietnamese");
        LanguageController.setUpButtonText(saveButton, "setting.save");

    }
}