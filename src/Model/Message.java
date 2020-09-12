package Model;

import java.io.Serializable;

public class Message implements Serializable {
    //Data fields: includes sender, data, option.
    private String sender;
    private String playerName;
    private Deck deck;
    private String action = "play"; // play card is the default action, there are play, draw, start, win
    private int numOfCard;
    private Card previousCard;

    public Message() {

    }

    /**
     * initial message at beginning of game
     */
    public Message(String sender, String playerName, Deck deck) {
        this.sender = sender;
        this.playerName = playerName;
        this.deck = deck;
    }

    /**
     * play and draw message
     */
    public Message(String sender, String action, int numOfCard, Card previousCard) {
        this.sender = sender;
        this.action = action;
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

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
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
}