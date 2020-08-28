package Model;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> cardList;

    //  Default Constructor
    public Player() {
        name = null;
        cardList = new ArrayList<Card>();
    }

    //  Constructor
    public Player(String name, ArrayList<Card> cardList) {
        this.name = name;
        this.cardList = cardList;
    }

    //  Accessor
    public String getName() {
        return name;
    }

    //  Mutator
    public void setName(String name) {
        this.name = name;
    }


    public void drawCard() {
    }

    public void playCard() {
    }
}
