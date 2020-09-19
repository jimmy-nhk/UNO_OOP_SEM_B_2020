package Controller;

import Model.Sound;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import java.util.Locale;

public class SettingsController
{


	@FXML public Slider volumeSlider;
	@FXML private Slider sliderNumberOfAIs;
	@FXML private Slider sliderNumberOfStartingCards;
	@FXML private Slider sliderAISpeed;
	@FXML private CheckBox checkBoxRule1;
	@FXML private CheckBox checkBoxRule2;
	@FXML private CheckBox checkBoxRule3;
	@FXML public RadioButton EnglishButton;
	@FXML public RadioButton VietnameseButton;

	private Stage stage;
	private Controller controller;
	private Locale locale;


	public void init(Stage stage, Controller controller)
	{
		Sound buttonSound = new Sound("src/resources/sounds/sound_button_click.mp3");     //Make "button sound" when clicked

		this.stage = stage;
		stage.setMinHeight(600);
		this.controller = controller;
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

		ToggleGroup group = new ToggleGroup();
		EnglishButton.setToggleGroup(group);
		VietnameseButton.setToggleGroup(group);

		Settings settings = controller.settings;

		sliderNumberOfAIs.setValue(settings.getNumberOfAIs());
		sliderNumberOfStartingCards.setValue(settings.getNumberOfStartingCards());
		sliderAISpeed.setValue(settings.getAiSpeed());
		volumeSlider.setValue(50);
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
		int numberOfAIs = (int)sliderNumberOfAIs.getValue();
		int numberOfStartingCards = (int)sliderNumberOfStartingCards.getValue();
		int aiSpeed = (int)sliderAISpeed.getValue();
		int volume = (int) volumeSlider.getValue();
		
		boolean allowChallengePlusTwo = checkBoxRule1.isSelected();
		boolean allowChallengePlusFourWithTwo = checkBoxRule2.isSelected();
		boolean allowChallengePlusFourWithFour = checkBoxRule3.isSelected();

		Controller.backGroundSound.adjustVolume(volumeSlider.valueProperty());

		volumeSlider.setPrefWidth(150);
		volumeSlider.setMaxWidth(Region.USE_PREF_SIZE);
		volumeSlider.setMinWidth(30);

		var ref = new Object() {
			Sound buttonSound;
		};
		EnglishButton.setOnAction(e -> ref.buttonSound = new Sound("src/resources/sounds/sound_button_click.mp3"));
		VietnameseButton.setOnAction(e -> ref.buttonSound = new Sound("src/resources/sounds/sound_button_click.mp3"));

		if(VietnameseButton.isSelected())
			locale = new Locale("vi", "VN");
		else
			locale = new Locale("en", "US");

		controller.settings = new Settings(numberOfAIs, numberOfStartingCards, aiSpeed, allowChallengePlusTwo,
				                           allowChallengePlusFourWithTwo, allowChallengePlusFourWithFour, locale, volume);
		try
		{
			controller.settings.save();
			controller.settings.load();		
		}
		catch(Exception e)
		{			
			e.printStackTrace();
		}
		stage.close();
		Sound buttonSound = new Sound("src/resources/sounds/sound_button_click.mp3");     //Make "button sound" when clicked

	}

	public void setBindingButtonText() {

	}
}