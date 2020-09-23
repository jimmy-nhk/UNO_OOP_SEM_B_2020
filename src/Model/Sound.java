/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020B
  Assessment: Final Project
  Created date: dd/mm/yyyy (e.g. 31/03/2019)
  By: Your name (Your studen id)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Your name (Your studen id)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package Model;

import javafx.beans.property.DoubleProperty;
import javafx.scene.control.Slider;
import javafx.scene.layout.Region;
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

    //adjust music volume:
    public void adjustMusicVolume(DoubleProperty volumeValue) {
        mediaPlayer.volumeProperty().bind(volumeValue);
    }

    // stop audio
    public void stop() {
        this.mediaPlayer.stop();
    }

}