package Controller;

import Model.Sound;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.util.Locale;

public class SettingsController
{
	@FXML private Slider sliderNumberOfBots;
	@FXML private Slider sliderNumberOfStartingCards;
	@FXML private Slider sliderAISpeed;
	@FXML private CheckBox checkBoxRule1;
	@FXML private CheckBox checkBoxRule2;
	@FXML private CheckBox checkBoxRule3;
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
		sliderAISpeed.setLabelFormatter(new StringConverter<Double>()
		{
			@Override
			public String toString(Double n)
			{
				if(n < 1.5)
					return "Slow";
				if(n < 2.5)
					return "Medium";
				if(n < 3.5)
					return "Fast";

				return "Very fast";
			}

			@Override
			public Double fromString(String s)
			{
				switch(s)
				{
					case "Slow":
						return 1d;
					case "Medium":
						return 2d;
					case "Fast":
						return 3d;					

					default:
						return 4d;
				}
			}
		});
		
		Settings settings = mainController.settings;

		sliderNumberOfBots.setValue((double)settings.getNumberOfBots());
		sliderNumberOfStartingCards.setValue((double)settings.getNumberOfStartingCards());
		sliderAISpeed.setValue((double)settings.getBotSpeed());
		checkBoxRule1.setSelected(settings.isAllowChallengePlusTwo());
		checkBoxRule2.setSelected(settings.isAllowChallengePlusFourWithTwo());
		checkBoxRule3.setSelected(settings.isAllowChallengePlusFourWithFour());		
		
		
		// stage.setOnCloseRequest(new EventHandler<WindowEvent>()
		// {
		// @Override
		// public void handle(WindowEvent event)
		// {
		// event.consume();
		// }
		// });
	}

	public void save()
	{
		int numberOfAIs = (int)sliderNumberOfBots.getValue();
		int numberOfStartingCards = (int)sliderNumberOfStartingCards.getValue();
		int aiSpeed = (int)sliderAISpeed.getValue();
		
		boolean allowChallengePlusTwo = checkBoxRule1.isSelected();
		boolean allowChallengePlusFourWithTwo = checkBoxRule2.isSelected();
		boolean allowChallengePlusFourWithFour = checkBoxRule3.isSelected();

		volumeSlider.setOnMouseClicked(e -> setActionForVolumeSlider());
		DoubleProperty volume = volumeSlider.valueProperty();
		int volumeAmount = volumeSlider.valueProperty().intValue();

		vietnameseButton.setOnAction(e -> locale = new Locale("vi", "VN"));
		englishButton.setOnAction(e -> locale = new Locale("en", "US"));

        MainController.backgroundMusic.adjustMusicVolume(volume);
		
		mainController.settings = new Settings(numberOfAIs, numberOfStartingCards, aiSpeed, allowChallengePlusTwo,
				                           allowChallengePlusFourWithTwo, allowChallengePlusFourWithFour, locale, volumeAmount);
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