package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    // field
    private Stack<Card> cards; // stack card to automatically get the last card first

    // constructor
    public Deck() {
        cards = new Stack<Card>();

        for (Color currentColor : Color.values()) {
            if (currentColor != Color.BLACK && currentColor != Color.ALL) {
                cards.add(new Card(CardType.ZERO, currentColor, 0));

                for (int i = 0; i < 2; i++) {
                    cards.add(new Card(CardType.ONE, currentColor, 0));
                    cards.add(new Card(CardType.TWO, currentColor, 0));
                    cards.add(new Card(CardType.THREE, currentColor, 0));
                    cards.add(new Card(CardType.FOUR, currentColor, 0));
                    cards.add(new Card(CardType.FIVE, currentColor, 0));
                    cards.add(new Card(CardType.SIX, currentColor, 0));
                    cards.add(new Card(CardType.SEVEN, currentColor, 0));
                    cards.add(new Card(CardType.EIGHT, currentColor, 0));
                    cards.add(new Card(CardType.NINE, currentColor, 0));

                    cards.add(new Card(CardType.REVERSE, currentColor, 1));
                    cards.add(new Card(CardType.SKIP, currentColor, 1));
                    cards.add(new Card(CardType.DRAW_TWO, currentColor, 2));
                }
            }
        }

        cards.add(new Card(CardType.DRAW_FOUR, Color.BLACK, 10));
        cards.add(new Card(CardType.DRAW_FOUR, Color.BLACK, 10));
        cards.add(new Card(CardType.DRAW_FOUR, Color.BLACK, 10));
        cards.add(new Card(CardType.DRAW_FOUR, Color.BLACK, 10));

        cards.add(new Card(CardType.WILD, Color.BLACK, 5));
        cards.add(new Card(CardType.WILD, Color.BLACK, 5));
        cards.add(new Card(CardType.WILD, Color.BLACK, 5));
        cards.add(new Card(CardType.WILD, Color.BLACK, 5));
    }

    // accessor
    public Stack<Card> getCards() {
        return cards;
    }

    /**
     * shuffle deck
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * draw 1 card
     */
    public Card drawCard(DeadDeck deadDeck) {
        if (cards.size() > 0) {
            return cards.pop(); // if deck is not empty, draw the top card
        } else {
            refill(deadDeck);
            return cards.pop(); // if deck is empty, refill it and take the top card
        }
    }

    /**
     * draw more than 1 cards
     */
    public ArrayList<Card> drawCards(int numberOfCards, DeadDeck deadDeck) {
        ArrayList<Card> drawedCards = new ArrayList<Card>();
        for (int i = 0; i < numberOfCards; i++) {
            drawedCards.add(drawCard(deadDeck));
        }

        return drawedCards;
    }

    /**
     * if deck is empty, shuffle the discarded deck and reuse it
     */
    public void refill(DeadDeck deadDeck) {
        for (Card currentCard : deadDeck.getCards()) {
            cards.add(currentCard);
        }
        shuffle();
    }

}