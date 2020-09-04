package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class AccountList {
    static ArrayList<Account> accountList = new ArrayList<>();

    // Constructor
    public AccountList(){
        loadFromFile(); // Whenever create the class AccountList, load the accounts to the accountList
    }

    public void loadFromFile() {
        /**
         * Deserialize data from file
         */
        try
        {
            FileInputStream fis = new FileInputStream("data.uno");
            ObjectInputStream ois = new ObjectInputStream(fis);

            accountList = (ArrayList) ois.readObject();

            ois.close();
            fis.close();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Serialize data from file
     */
    public void saveToFile() {
        try
        {
            FileOutputStream fos = new FileOutputStream("data.uno");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(accountList);
            oos.close();
            fos.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Display players on leaderboard
     */
    public String displayLeaderboard() {
        return accountList.toString();
    }

    /**
     * Sorting players by points
     */
    public void setRanking() {
        Collections.sort(accountList);
    }

    /**
     * Check if account already exists by searching for matching name and password, return -1 if account does not exist
     */
    public int getAccountIndex(String name, String password) {
        for (Account a: accountList) {
            if (name.equals(a.getName()) && password.equals(a.getPassword()))
                return accountList.indexOf(a);
        }
        return -1;
    }

    /**
     * return a player when account exists, null when account does not exist
     */
    public Player signIn(String name, String password) {
        loadFromFile();
        if (getAccountIndex(name, password) >= 0) {
            return new Player(accountList.get(getAccountIndex(name, password)), null);
        }
        return null;
    }

    /**
     * Sign up account, return true if sign up successfully, false if account already exists
     */
    public boolean signUp(String name, String password) {
        if (getAccountIndex(name, password) >= 0) {
            return false;
        }
        Account account = new Account(name, password, 0, 0);
        accountList.add(account);
        saveToFile();
        return true;
    }
}
