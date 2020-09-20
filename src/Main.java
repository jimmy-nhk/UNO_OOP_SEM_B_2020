import Controller.MainController;
import achievements.Achievement.Status;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

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
			
//			stage.setOnCloseRequest(new EventHandler<WindowEvent>()
//			{
//				@Override
//				public void handle(WindowEvent event)
//				{
//					try
//					{
//						if(mainController.handler.getAchievements().get(3).getStatus().equals(Status.LOCKED))
//						{
//							mainController.handler.resetAchievement(3);
//						}
//						if(mainController.handler.getAchievements().get(4).getStatus().equals(Status.LOCKED))
//						{
//							mainController.handler.resetAchievement(4);
//						}
//						mainController.handler.saveAndLoad();
//					}
//					catch(Exception e)
//					{
//						System.out.println(e.toString());
//					}
//				}
//			});

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