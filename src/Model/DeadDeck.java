package Model;

import java.util.ArrayList;

/**
 * discarded deck
 */
public class DeadDeck {
    // field
    private ArrayList<Card> cards;

    // constructor
    public DeadDeck() {
        cards = new ArrayList<Card>();
    }

    /**
     * add dealt card in dead deck
     */
    public void add(Card card) {
        cards.add(card);
    }

    // accessor
    public ArrayList<Card> getCards() {
        return cards;
    }
}