package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class PlayerList {
    ArrayList<Account> playerList = new ArrayList<>();

    public void loadFromFile() {
        try
        {
            FileInputStream fis = new FileInputStream("employeeData");
            ObjectInputStream ois = new ObjectInputStream(fis);

            playerList = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }

    public void saveToFile() {
        try
        {
            FileOutputStream fos = new FileOutputStream("employeeData");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(playerList);
            oos.close();
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void displayLeaderboard() {

    }

    public void sortRanking() {
        Collections.sort(playerList);
    }
}
