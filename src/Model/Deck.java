package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Deck {

    // The deck consists of 108 cards: four each of "Wild" and "Wild Draw Four,"
    // and 25 each of four different colors (red, yellow, green, blue).
    // Each color consists of one zero, two each of 1 through 9,
    // and two each of "Skip," "Draw Two," and "Reverse." These last three types are known as "action cards."

    enum Color {
        Red,
        Yellow,
        Green,
        Blue,
        Wild
    }

    Color[] colors = Color.values();

    // Data fields
    ArrayList<Card> cards;

    // Methods:

    Deck(){
        loadCards(); // Load cards from the source to it
        shuffleDeck(); // Shuffle the deck before playing

    }

    // Load the cards inside the deck
    public void loadCards(){
        // Load the color cards inside the deck first
        for (Color c :colors){

            // Check if the color is not WILD
            if (!c.equals(Color.Wild)){

                // add the zero card first
                cards.add(new Card("pngfind.com-"+ 0 + c +"-card-png-3806009.png"));

                // there are two each of digit 1 through 9
                // and two each of action cards: skip , draw too , reverse
                for (int i = 1 ; i < 13; i++){
                    cards.add(new Card("pngfind.com-"+ i + c +"-card-png-3806009.png"));
                    cards.add(new Card("pngfind.com-"+ i + c +"-card-png-3806009.png"));
                }

            } else {

                // Add the wild cards
                for ( int i = 13 ; i < 14 ; i ++){
                    cards.add(new Card("pngfind.com-"+ i + "-card-png-3806009.png"));
                    cards.add(new Card("pngfind.com-"+ i + "-card-png-3806009.png"));
                    cards.add(new Card("pngfind.com-"+ i + "-card-png-3806009.png"));
                    cards.add(new Card("pngfind.com-"+ i + "-card-png-3806009.png"));
                }
            }
        }
    }

    // to shuffle the deck before playing
    public void shuffleDeck  (){
        Collections.shuffle(cards);
    }

    // the card will be withdrawn from the deck
    public Card drawCard (){
        Card card = cards.get(cards.size() - 1); // take the last card of the deck
        cards.remove(cards.size() - 1); // reduce the size of the deck
        return card;
    }
}
