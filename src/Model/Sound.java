package Model;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

public class Sound {
    // resource urls
    private static final String BACKGROUND_MUSIC_URL = "../resources/sounds/background.mp3";
    private static final String BUTTON_CLICK_URL = "../resources/sounds/sound_button_click.wav";
    private static final String CARD_DEALING_URL = "../resources/sounds/Card_Dealing.mov";
    private static final String CARD_SHUFFLE_URL = "../resources/sound/ShuffleCardSound.mov";
    private static final String CARD_APPEAR_URL = "../resources/sound/sound_appear.wav";
    private static final String GAME_LAUNCH_SOUND_URL = "../resources/sound/startgamesound.ogg";

    // fields
    public enum Type {
        BACKGROUND_MUSIC,
        BUTTON_CLICK_SOUND,
        CARD_DEALING_SOUND,
        CARD_SHUFFLE_SOUND,
        CARD_APPEAR_SOUND,
        GAME_LAUNCH_SOUND,
    }
    private MediaPlayer sound;

    // constructor
    public Sound(Type soundType) {
        try {
            this.sound = new MediaPlayer(new Media(getResourceURL(soundType)));
        } catch (Exception e) {
            System.out.println("Cannot load sound.");
        }
        if (soundType == Type.BACKGROUND_MUSIC) {
            this.sound.setCycleCount(MediaPlayer.INDEFINITE);
        }
    }

    // get resource url from sound type
    private String getResourceURL(Type soundType) {
        URL resource;
        switch (soundType) {
            case BACKGROUND_MUSIC: {
                resource = getClass().getResource(BACKGROUND_MUSIC_URL);
                break;
            }
            case BUTTON_CLICK_SOUND: {
                resource = getClass().getResource(BUTTON_CLICK_URL);
                break;
            }
            case CARD_APPEAR_SOUND: {
                resource = getClass().getResource(CARD_APPEAR_URL);
                break;
            }
            case CARD_DEALING_SOUND: {
                resource = getClass().getResource(CARD_DEALING_URL);
                break;
            }
            case CARD_SHUFFLE_SOUND: {
                resource = getClass().getResource(CARD_SHUFFLE_URL);
                break;
            }
            case GAME_LAUNCH_SOUND: {
                resource = getClass().getResource(GAME_LAUNCH_SOUND_URL);
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + soundType);
        }
        return resource.toString();
    }

    // play audio
    public void play() {
        this.sound.seek(Duration.ZERO);
        this.sound.play();
    }

    // pause audio
    public void pause() {
        this.sound.pause();
    }

    // stop audio
    public void stop() {
        this.sound.stop();
    }
}

