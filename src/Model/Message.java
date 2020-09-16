package Model;

import java.io.Serializable;
import java.util.ArrayList;

public class Message implements Serializable {
    //Data fields: includes sender, data, option.
    private int sender;
    private String typeOfAction;
    private Object information;
    private int numOfCard;
    private Card card;
    private Deck deck;
//initialize
    public Message(String typeOfAction,Deck deck) {
        this.deck = deck;
        this.typeOfAction = typeOfAction;
    }
//    draw
    public Message(int sender, String typeOfAction) {
        this.sender = sender;
        this.typeOfAction = typeOfAction;
    }
//  play
    public Message(int sender, String typeOfAction,  Card card) {
        this.sender = sender;
        this.typeOfAction = typeOfAction;
        this.card = card;
    }
    //  start
    public Message(String typeOfAction,int total) {
        this.typeOfAction = typeOfAction;
        this.total ++;
}
    //  leave
    public Message(String typeOfAction) {
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
}
