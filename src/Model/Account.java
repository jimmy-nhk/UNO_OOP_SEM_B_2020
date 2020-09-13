package Model;

import java.io.Serializable;

public class Account implements Comparable<Account>, Serializable {
    private String name;
    private String password;
    private int win;
    private int loss;

    //  Default Constructor
    public Account(){
        name = null;
        password = null;
        win = 0;
        loss = 0;

    }
    //  Constructor
    public Account(String name, String password, int win, int loss) {
        this.name = name;
        this.password = password;
        this.win = win;
        this.loss = loss;
    }
    //  Accessor
    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getWin() {
        return win;
    }

    public int getLoss() {
        return loss;
    }
    //  Mutator
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWin(int win) {
        this.win = win;
    }

    public void setLoss(int loss) {
        this.loss = loss;
    }
    
        @Override
    public String toString() {
        return getName() + "    " + getWin() + "    " +getLoss();
    }
    
    //  used in Leaderboard
    public double calculateWinRate(int win, int loss) {
        return ((double) win) / (double) (win + loss);
    }

    //  Overriding compareTo() from interface Comparable
    @Override
    public int compareTo(Account account) {
        if (calculateWinRate(getWin(),getLoss())== account.calculateWinRate(getWin(),getLoss())) {
            return 0;
        } else if (calculateWinRate(getWin(),getLoss())> account.calculateWinRate(getWin(),getLoss())) {
            return 1;
        } else return -1;
    }

}
