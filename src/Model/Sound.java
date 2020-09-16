package Model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.nio.file.Paths;

public class Sound {
    private MediaPlayer mediaPlayer;
    private Media media;

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

}