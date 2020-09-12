package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class Deck implements Serializable {

    // The deck consists of 108 cards: four each of "Wild" and "Wild Draw Four,"
    // and 25 each of four different Properties (red, yellow, green, blue).
    // Each Properties consists of one zero, two each of 1 through 9,
    // and two each of "Skip," "Draw Two," and "Reverse." These last three types are known as "action cards."
    

    // Data fields
    private ArrayList<Card> cards;

    // Methods:

    public Deck(){
        cards = new ArrayList<>();
        loadCards(); // Load cards from the source to it
        shuffleDeck(); // Shuffle the deck before playing

    }

    // Load the cards inside the deck
    public void loadCards(){
        // Load the Properties cards inside the deck first
        for (Properties p : Properties.values()){

            // Check if the Properties is not WILD
            if (!p.equals(Properties.WILD)){

                // add the zero card first
                cards.add(new Card("ZERO-" + p +"-card-png-3806009.png", Values.ZERO ,p ) );

                // there are two each of digit 1 through 9
                // and two each of action cards: skip , draw too , reverse
                for (int i = 1 ; i <= 12; i++){
                    cards.add(new Card(Card.values[i] + "-" + p +"-card.png", Card.values[i]  , p ) );
                    cards.add(new Card(Card.values[i] + "-" + p +"-card.png", Card.values[i]  , p ) );
                }


            } else {

                 // Add the wild cards
                for ( int i = 13 ; i <= 14 ; i ++){
                    cards.add(new Card( Card.values[i] + "-WILD-card.png", Card.values[i]  , p ));
                    cards.add(new Card( Card.values[i] + "-WILD-card.png", Card.values[i]  , p ));
                    cards.add(new Card( Card.values[i] + "-WILD-card.png", Card.values[i]  , p ));
                    cards.add(new Card( Card.values[i] + "-WILD-card.png", Card.values[i]  , p ));
                }
            }
        }
    }

    // to shuffle the deck before playing
    public void shuffleDeck  (){
        Collections.shuffle(cards);
    }

    // the card will be withdrawn from the deck
    public Card drawTopCard (){
        Card card = cards.get(cards.size() - 1); // take the last card of the deck
        cards.remove(cards.size() - 1); // reduce the size of the deck
        return card;
    }

    // Get size of the deck
    public int getSize(){
        return cards.size();
    }

    // Get arraylist of cards

    public ArrayList<Card> getCards() {
        return cards;
    }
}
