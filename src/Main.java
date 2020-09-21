import Controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application
{
	@Override
	public void start(Stage stage)
	{
		try
		{
			FXMLLoader loader = new FXMLLoader(getClass().getResource("view/MainGUI.fxml"));
			Parent root = (Parent)loader.load();

			Scene scene = new Scene(root, 800, 650);

			scene.getStylesheets().add(getClass().getResource("resources/css/ColorChooser.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("resources/css/MainGUI.css").toExternalForm());
			scene.getStylesheets().add(getClass().getResource("resources/css/Settings.css").toExternalForm());

			stage.setResizable(true);
			stage.setTitle("UNO TIME!!");

			stage.setScene(scene);		

			stage.setResizable(false);

			MainController mainController = (MainController)loader.getController();
			mainController.setStage(stage);
			mainController.init();


			stage.getIcons().add(new Image("images/icon.png"));
			stage.show();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}
}