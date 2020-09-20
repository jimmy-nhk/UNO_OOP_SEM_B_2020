package Controller;

import tools.PathUtils;

import java.io.*;
import java.util.Locale;

public class Settings implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numberOfBots;
    private int numberOfStartingCards;
    private int aiSpeed;
    private boolean allowChallengePlusTwo;
    private boolean allowChallengePlusFourWithTwo;
    private boolean allowChallengePlusFourWithFour;
    private int volume;
    private Locale locale;


    public Settings(int numberOfBots, int numberOfStartingCards, int aiSpeed,
                    boolean allowChallengePlusTwo, boolean allowChallengePlusFourWithTwo,
                    boolean allowChallengePlusFourWithFour, Locale locale, int volume) {
        this.numberOfBots = numberOfBots;
        this.numberOfStartingCards = numberOfStartingCards;
        this.aiSpeed = aiSpeed;
        this.allowChallengePlusTwo = allowChallengePlusTwo;
        this.allowChallengePlusFourWithTwo = allowChallengePlusFourWithTwo;
        this.allowChallengePlusFourWithFour = allowChallengePlusFourWithFour;
        this.volume = volume;
        this.locale = locale;
    }

    public Settings() {
        PathUtils.checkFolder(new File(PathUtils.getOSindependentPath() + "/OOP/UNO/"));
        File file = new File(PathUtils.getOSindependentPath() + "/OOP/UNO/settings.config");
        if (!file.exists()) {
            createStandardValues();
            try {
                save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void createStandardValues() {
        numberOfBots = 1;
        numberOfStartingCards = 7;
        aiSpeed = 2;
        allowChallengePlusTwo = false;
        allowChallengePlusFourWithTwo = false;
        allowChallengePlusFourWithFour = false;
    }

    // Save the data
    public void save() throws Exception {
        FileOutputStream fileOut = new FileOutputStream(PathUtils.getOSindependentPath() + "/OOP/UNO/settings.config");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();
    }

    // Load the data from the file
    public void load() throws Exception {
        FileInputStream fileIn = new FileInputStream(PathUtils.getOSindependentPath() + "/OOP/UNO/settings.config");
        ObjectInputStream in = new ObjectInputStream(fileIn);
        Settings loaded = (Settings) in.readObject();
        in.close();
        fileIn.close();

        this.numberOfBots = loaded.getNumberOfBots();
        this.numberOfStartingCards = loaded.getNumberOfStartingCards();
        this.aiSpeed = loaded.getBotSpeed();
        this.allowChallengePlusTwo = loaded.isAllowChallengePlusTwo();
        this.allowChallengePlusFourWithTwo = loaded.isAllowChallengePlusFourWithTwo();
        this.allowChallengePlusFourWithFour = loaded.isAllowChallengePlusFourWithFour();
        this.locale = loaded.getLocale();
        this.volume = loaded.getVolume();
    }

    public int getVolume() {
        return volume;
    }

    public Locale getLocale() {
        return locale;
    }

    public int getNumberOfBots() {
        return numberOfBots;
    }

    public int getNumberOfStartingCards() {
        return numberOfStartingCards;
    }

    public int getBotSpeed() {
        return aiSpeed;
    }

    public boolean isAllowChallengePlusTwo() {
        return allowChallengePlusTwo;
    }

    public boolean isAllowChallengePlusFourWithTwo() {
        return allowChallengePlusFourWithTwo;
    }

    public boolean isAllowChallengePlusFourWithFour() {
        return allowChallengePlusFourWithFour;
    }
}
