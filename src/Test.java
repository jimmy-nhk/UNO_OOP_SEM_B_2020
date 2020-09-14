import Controller.SettingController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.io.IOException;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("resources/view/mainMain.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        // adding decoration to all scenes
        scene.getStylesheets().add("resources/css/gameboarddecor.css");
        scene.getStylesheets().add("resources/css/instructions_decor.css");
        scene.getStylesheets().add("resources/css/log_in.css");
        scene.getStylesheets().add("resources/css/mainMenu.css");
        scene.getStylesheets().add("resources/css/leaderboard.css");
        scene.getStylesheets().add("resources/css/setting.css");
        scene.getStylesheets().add("resources/css/mainMain.css");

    }
}
