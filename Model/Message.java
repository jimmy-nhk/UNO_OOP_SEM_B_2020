package Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    //Data fields: includes sender, data, option.

    private ArrayList<String> playerName;
    private ArrayList<Integer> win;
    private FileOutputStream fileOutputStream;

    /**
     * initial message at beginning of game
     */
    public Message(ArrayList<String> playerName, ArrayList<Integer> win) {
        this.playerName = playerName;
        this.win = win;
    }

    public ArrayList<String> getPlayerName() {
        return playerName;
    }

    public void setPlayerName(ArrayList<String> playerName) {
        this.playerName = playerName;
    }

    public ArrayList<Integer> getWin() {
        return win;
    }

    public void setWin(ArrayList<Integer> win) {
        this.win = win;
    }

    public FileOutputStream getFileOutputStream() {
        return fileOutputStream;
    }

    public void setFileOutputStream(FileOutputStream fileOutputStream) {
        this.fileOutputStream = fileOutputStream;
    }

    public Message(FileOutputStream fileInputStream) {
        this.fileOutputStream = fileOutputStream;
    }


    /**
     * mutators and accessors
     */
}
