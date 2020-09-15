import Controller.SoundController;
import Model.Sound;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.nio.file.Paths;

public class MediaDemo extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    private static final String MEDIA_URL = "src/resources/sound/MissYou.mp3";

    @Override
    public void start(Stage primaryStage) {

        SoundController.playAlarmSound();
//        Button playButton = new Button();
//        playButton.setOnAction(e -> {
//            if (playButton.getText().equals(">")) {
//                mediaPlayer.play();
//                playButton.setText("||");
//            } else {
//                mediaPlayer.pause();
//                playButton.setText(">");
//            }
//        });
//
//        Button rewindButton = new Button("<<");
//
//        Slider slVolume = new Slider();
//        slVolume.setPrefWidth(150);
//        slVolume.setMaxWidth(Region.USE_PREF_SIZE);
//        slVolume.setMinWidth(30);
//        slVolume.setValue(50);
//        HBox paneForButton = new HBox(10);
//        paneForButton.setAlignment(Pos.CENTER);
//        paneForButton.getChildren().addAll(playButton, rewindButton, new Label("Volume"), slVolume);

        BorderPane pane = new BorderPane();


        Scene scene = new Scene(pane, 650, 500);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Media Demo");
        primaryStage.show();

    }

    private MediaPlayer playMusic() {
        Media media = new Media(Paths.get(MEDIA_URL).toUri().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        return mediaPlayer;
    }
}