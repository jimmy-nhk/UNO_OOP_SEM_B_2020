package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class AccountList {
    private static ArrayList<Account> accountList = new ArrayList<>();
    private static ArrayList<Player> players = new ArrayList<>();

    // Constructor

    public AccountList(){
        loadFromFile(); // Whenever create the class AccountList, load the accounts to the accountList
    }

    public static void loadFromFile() {
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
    public static void saveToFile() {
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
    public static void setRanking() {
        Collections.sort(accountList);
    }

    /**
     * Check if account already exists by searching for matching name and password, return -1 if account does not exist
     */
    public static int getAccountIndex(String name) {
        for (Account a: accountList) {
            if (name.equals(a.getName()) )
                return accountList.indexOf(a);
        }
        return -1;
    }
    
    
     public static ArrayList<Player> getPlayers() {
        return players;
    }
    
   /**
     * return a player when account exists, null when account does not exist
     */
    public static boolean signIn(String name, String password) {
        loadFromFile();

        if (password.equals(accountList.get(getAccountIndex(name)).getPassword())) {
            players.add(new Player(accountList.get(getAccountIndex(name))));
            return true;
        }

        return false;
    }

    /**
     * Sign up account, return true if sign up successfully, false if account already exists
     */
    public static boolean signUp(String name, String password) {
        loadFromFile();
        if (getAccountIndex(name) >= 0) {
            return false;
        }
        Account account = new Account(name, password, 0, 0);
        accountList.add(account);
        saveToFile();
        return true;
    }
}
