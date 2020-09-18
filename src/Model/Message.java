package Model;

import java.io.Serializable;

public class Message implements Serializable {
    //Data fields: includes sender, data, option.
    private int sender;
    int playerIndex;
    private String typeOfAction;
    private Object information;
    private int numOfCard;
    private Card card;
    private Deck deck;
    private int total = 0;
    private String name;

    // assign player index
    public Message(String typeOfAction, int playerIndex) {
        typeOfAction = "assign";
        this.playerIndex = playerIndex;
    }

    //initialize
    public Message(String typeOfAction, Deck deck) {
        this.typeOfAction = typeOfAction;
        this.deck = deck;
    }

    //  draw
    public Message(int sender, String typeOfAction, int numOfCard) {
        this.sender = sender;
        this.typeOfAction = typeOfAction;
        this.numOfCard = numOfCard;
    }

    //  play
    public Message(int sender, String typeOfAction, Card card) {
        this.sender = sender;
        this.typeOfAction = typeOfAction;
        this.card = card;
    }

//  leave & start
    public Message(String typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

//  login
    public Message(String typeOfAction,String name, String password) {
        this.typeOfAction = typeOfAction;
    }



    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }
    public Card getCard() {
        return card;
    }

    public int getNumOfCard() {
        return numOfCard;
    }

    public Deck getDeck() {
        return deck;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getPlayerIndex() {
        return playerIndex;
    }

    public String getTypeOfAction() {
        return typeOfAction;
    }

    public void setTypeOfAction(String typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

    public Object getInformation() {
        return information;
    }

    public void setInformation(Object information) {
        this.information = information;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumOfCard(int numOfCard) {
        this.numOfCard = numOfCard;
    }
}

