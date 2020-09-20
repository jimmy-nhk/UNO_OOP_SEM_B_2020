package Controller;

import tools.PathUtils;

import java.io.*;
import java.util.Locale;

public class Settings implements Serializable {
    private static final long serialVersionUID = 1L;
    private int numberOfBots;
    private int numberOfStartingCards;
    private int volume;
    private Locale locale;


    public Settings(int numberOfBots, int numberOfStartingCards, Locale locale, int volume) {
        this.numberOfBots = numberOfBots;
        this.numberOfStartingCards = numberOfStartingCards;
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
}
