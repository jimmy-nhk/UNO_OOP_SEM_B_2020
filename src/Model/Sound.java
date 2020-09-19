package Model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import java.nio.file.Paths;

public class Sound {
    private MediaPlayer mediaPlayer;
    private Media media;
    public Slider volumeSlider = new Slider();

    // constructor
    public Sound(String soundURL) {
        try {
           this.mediaPlayer = playMusic(soundURL);


        } catch (Exception e) {
            System.out.println("Cannot load sound.");
        }
    }

    // play audio
    public void play() {
        this.mediaPlayer.seek(Duration.ZERO);
        this.mediaPlayer.play();
    }

    private MediaPlayer playMusic(String soundURL) {
        media = new Media(Paths.get(soundURL).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
        mediaPlayer.setAutoPlay(true);
        return mediaPlayer;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    // pause audio
    public void pause() {
        this.mediaPlayer.pause();
    }

    // stop audio
    public void stop() {
        this.mediaPlayer.stop();
    }

//    Slider slVolume = new Slider();
//        slVolume.setPrefWidth(150);
//        slVolume.setMaxWidth(Region.USE_PREF_SIZE);
//        slVolume.setMinWidth(30);
//        slVolume.setValue(50);
//        mediaPlayer.volumeProperty().bind(slVolume.valueProperty().divide(100))

     public void adjustVolume(DoubleProperty volume) {
         mediaPlayer.volumeProperty().bind(volume);
     }


}