package Model;

import java.io.*;
import java.util.ArrayList;

public class AccountList {

    // deserialize accountList
    public ArrayList<Account> loadFromFile() {
        try
        {
            FileInputStream fis = new FileInputStream("data.uno");
            ObjectInputStream ois = new ObjectInputStream(fis);

            ArrayList<Account> accountList = (ArrayList) ois.readObject();

            ois.close();
            fis.close();

            return accountList;
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return null;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return null;
        }
    }

    // serialize accountList
    public void saveToFile(ArrayList<Account> accountList) {
        try
        {
            FileOutputStream fos = new FileOutputStream("data.uno");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(accountList);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }

    public String displayLeaderboard() {
        return this.toString();
    }

    public void setRanking() {

    }

    public boolean checkAccountExist(ArrayList<Account> accountList, Account account) {
        return accountList.contains(account);
    }
}
