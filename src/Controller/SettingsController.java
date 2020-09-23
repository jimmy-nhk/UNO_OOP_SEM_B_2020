/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020B
  Assessment: Final Project
  Created date: dd/mm/yyyy (e.g. 31/03/2019)
  By: Your name (Your studen id)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Your name (Your studen id)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package Controller;

import Model.Sound;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.util.Locale;

public class SettingsController
{
	@FXML private Slider sliderNumberOfBots;
	@FXML private Slider sliderNumberOfStartingCards;
	@FXML public RadioButton englishButton;
	@FXML public RadioButton vietnameseButton;
	@FXML public Button saveButton;
	@FXML public Label languageLabel;
	@FXML public Label volumeLabel;
	@FXML public Label settingLabel;
	@FXML public Label opponentLabel;
	@FXML public Label startingCardLabel;
	@FXML public Slider volumeSlider = new Slider();

	private Stage stage;
	private MainController mainController;
	public static Locale locale = new Locale("en", "US");

	public void init(Stage stage, MainController mainController)
	{
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

	public void save()
	{
		int numberOfAIs = (int)sliderNumberOfBots.getValue();
		int numberOfStartingCards = (int)sliderNumberOfStartingCards.getValue();

		volumeSlider.setOnMouseClicked(e -> setActionForVolumeSlider());
		DoubleProperty volume = volumeSlider.valueProperty();
		int volumeAmount = volumeSlider.valueProperty().intValue();


		vietnameseButton.setOnAction(e -> locale = new Locale("vi", "VN"));
		englishButton.setOnAction(e -> locale = new Locale("en", "US"));

        MainController.backgroundMusic.adjustMusicVolume(volume);
		
		mainController.settings = new Settings(numberOfAIs, numberOfStartingCards, locale, volumeAmount);
		try
		{
			mainController.settings.save();
			mainController.settings.load();
		}
		catch(Exception e)
		{			
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