/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020B
  Assessment: Final Project
 Team members:
        1. Nguyen Dang Huynh Chau - s3777214
        2. Nguyen Hoang Khang - s3802040
        3. Nguyen Dinh Dang Nguyen - s3759957
        4. Bui Thanh Huy - s3740934
        5. Nguyen Phuoc Nhu Phuc - s3819660
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package Controller;

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
        File file = new File("\\src\\settings.config");
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

    // Save the data from settings
    public void save() throws Exception {
        FileOutputStream fileOut = new FileOutputStream("src\\settings.config");
        ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(this);
        out.close();
        fileOut.close();
    }

    // Load the data from the settings
    public void load() throws Exception {
        FileInputStream fileIn = new FileInputStream("src\\settings.config");
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
