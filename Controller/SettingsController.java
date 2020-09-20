package Controller;

import Model.Sound;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.util.Locale;

public class SettingsController
{
	@FXML private Slider sliderNumberOfBots;
	@FXML private Slider sliderNumberOfStartingCards;
	@FXML public RadioButton englishButton;
	@FXML public RadioButton vietnameseButton;
	@FXML public Label languageLabel;
	@FXML public Label volumeLabel;
	@FXML public Slider volumeSlider = new Slider();

	private Stage stage;
	private MainController mainController;
	private Locale locale = new Locale("en", "US");

	public void init(Stage stage, MainController mainController)
	{
		Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

		this.stage = stage;
		this.stage.setMinHeight(600);
		this.mainController = mainController;
		
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
}