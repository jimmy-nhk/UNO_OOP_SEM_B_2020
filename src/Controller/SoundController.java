package Controller;

import Model.Sound;
import javafx.scene.image.Image;
import javafx.scene.layout.*;

public class SoundController {
    private static final String SOUND_ON_IMG_URL = "file:src/resources/images/sound_on.jpg";
    private static final String SOUND_OFF_IMG_URL = "file:src/resources/images/sound_off.jpg";

    //Variable check if the sound of the system is on, sound is ON by default
    private static boolean soundOn = true;
    //Image object for loading "sound on" image from resources
    private static final Image soundOnImg = new Image(SOUND_ON_IMG_URL);
    //Image object for loading "sound off" image from resources
    private static final Image soundOffImg = new Image(SOUND_OFF_IMG_URL);
    //Background object for setting "sound on" image as soundEnabledBtn background image
    public static Background bgSoundOnImg = generateSoundButtonBackground(soundOnImg);
    //Background object for setting "sound off" image as soundEnabledBtn background image
    public static Background bgSoundOffImg = generateSoundButtonBackground(soundOffImg);
    //Sound object for playing background music
    private static Sound backgroundMusic = new Sound(Sound.Type.BACKGROUND_MUSIC);
    //Button sound object, for making sound when pressing the sound enabled button
    private static Sound buttonClickingSound = new Sound(Sound.Type.BUTTON_CLICK_SOUND);
    private static Sound cardDealingSound = new Sound(Sound.Type.CARD_DEALING_SOUND);
    private static Sound cardShuffleSound = new Sound(Sound.Type.CARD_SHUFFLE_SOUND);
    private static Sound cardAppearSound = new Sound(Sound.Type.CARD_APPEAR_SOUND);
    private static Sound gameLaunchSound = new Sound(Sound.Type.GAME_LAUNCH_SOUND);


    // generate sound button background
    private static Background generateSoundButtonBackground(Image img) {
        return new Background(new BackgroundImage(img,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100,
                        true, true, true, false)));
    }

    // check if sound is enabled
    public static boolean isSoundEnabled() {
        return soundOn;
    }

    // toggle sound on or off
    public static void toggleSound() {
        if (isSoundEnabled()) {
            soundOn = false;
            muteSound();
        } else {
            soundOn = true;
            unmuteSound();
        }
    }

    // mute sound
    private static void muteSound() {
        backgroundMusic.pause();
        cardDealingSound.stop();
        cardShuffleSound.stop();
        cardAppearSound.stop();
        buttonClickingSound.stop();
        gameLaunchSound.stop();
    }

    // unmute sound
    private static void unmuteSound() {
        backgroundMusic.play();
    }

    // play an audio
    private static void playSound(Sound sound) {
        if (isSoundEnabled()) sound.play();
    }

    // play background sound
    public static void playBackgroundSound() {
        playSound(backgroundMusic);
    }

    // play card dealing sound:
    public static void playCardDealingSound() {
        playSound(cardAppearSound);
    }

    // stop card dealing sound:
    public static void stopCardDealingSound() {
        cardAppearSound.stop();
    }

    // play card shuffle sound:
    public static void playCardShuffleSound() {
        playSound(cardShuffleSound);
    }

    // stop card shuffle sound:
    public static void stopCardShuffleSound() {
        cardShuffleSound.stop();
    }

    // play card appear sound:
    public static void playCardAppearSound() {
        playSound(cardAppearSound);
    }

    // stop card appear sound:
    public static void stopCardAppearSound() {
        cardShuffleSound.stop();
    }

    // play button click sound
    public static void playButtonClickSound() {
        playSound(buttonClickingSound);
    }

    // play button click sound
    public static void stopButtonClickSound() {
        buttonClickingSound.stop();
    }

    // play game launch sound
    public static void playGameLaunchSound() {
        playSound(gameLaunchSound);
    }
}