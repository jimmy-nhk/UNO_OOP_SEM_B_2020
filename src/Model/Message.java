package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    //Data fields: includes sender, data, option.
    private String sender;
    private String playerName;
    private String action;
    private int numOfCard;
    private ArrayList<Card> cardList;
    private Card previousCard;

    public Message() {
    }

    /**
     * initial message at beginning of game, server send to players
     */
    public Message(ArrayList<Card> cardList) {
        this.action = "initialize";
        this.cardList = cardList;
    }

    /**
     * play and draw message
     */
    public Message(String sender, int numOfCard, Card previousCard) {
        this.sender = sender;
        this.action = "play";
        this.numOfCard = numOfCard;
        this.previousCard = previousCard;
    }

    /**
     * mutators and accessors
     */
    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int getNumOfCard() {
        return numOfCard;
    }

    public void setNumOfCard(int numOfCard) {
        this.numOfCard = numOfCard;
    }

    public Card getPreviousCard() {
        return previousCard;
    }

    public void setPreviousCard(Card previousCard) {
        this.previousCard = previousCard;
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }
}