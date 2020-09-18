import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javax.swing.text.html.ImageView;
import java.io.IOException;

public class Test extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("resources/view/GameBoard.fxml"));


        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();

        // adding decorations to all scenes
        scene.getStylesheets().add("resources/css/gameboarddecor.css");
        scene.getStylesheets().add("resources/css/instructions_decor.css");
        scene.getStylesheets().add("resources/css/log_in.css");
        scene.getStylesheets().add("resources/css/mainMenu.css");
        scene.getStylesheets().add("resources/css/leaderboard.css");
        scene.getStylesheets().add("resources/css/setting.css");
        scene.getStylesheets().add("resources/css/mainMain.css");
    }

}
