/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020B
  Assessment: Final Project
 Team members:
        1. Nguyen Dang Huynh Chau - s3777214
        2. Nguyen Hoang Khang - s3802040
        3. Nguyen Dinh Dang Nguyen - s3759957
        4. Bui Thanh Huy - s3740934
        5. Nguyen Phuoc Nhu Phuc - s3819660
  Acknowledgement:
https://www.geeksforgeeks.org/stack-class-in-java/ - Stack Class in Java
https://www.javatpoint.com/enum-in-java#:~:text=Java%20Enums%20can%20be%20thought,own%20data%20type%20like%20classes. - Java Enums
https://www.geeksforgeeks.org/collections-shuffle-java-examples/ - Collections.shuffle() in Java with Examples
https://www.javatpoint.com/java-map - Java Map Interface
https://www.geeksforgeeks.org/serialization-in-java/ - Serialization and Deserialization in Java with Example
https://www.baeldung.com/a-guide-to-java-sockets - A Guide to Java Sockets
https://www.educba.com/javafx-alert/ - Introduction to JavaFX Alert
https://www.tutorialspoint.com/java/util/java_util_locale.htm - Java.util.Locale Class
https://stackoverflow.com/questions/22490064/how-to-use-javafx-mediaplayer-correctly - How to use JavaFX MediaPlayer correctly?
https://www.geeksforgeeks.org/javafx-duration-class/ - JavaFX | Duration Class
*/

package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    private Stack<Card> cards;

    public Deck() {
        cards = new Stack<>();
//      to add all card to the deck
        for (Color currentColor : Color.values()) {
            if (currentColor != Color.BLACK && currentColor != Color.ALL) {
                cards.add(new Card(Property.ZERO, currentColor, 0));

                for (int i = 0; i < 2; i++) {
                    cards.add(new Card(Property.ONE, currentColor, 0));
                    cards.add(new Card(Property.TWO, currentColor, 0));
                    cards.add(new Card(Property.THREE, currentColor, 0));
                    cards.add(new Card(Property.FOUR, currentColor, 0));
                    cards.add(new Card(Property.FIVE, currentColor, 0));
                    cards.add(new Card(Property.SIX, currentColor, 0));
                    cards.add(new Card(Property.SEVEN, currentColor, 0));
                    cards.add(new Card(Property.EIGHT, currentColor, 0));
                    cards.add(new Card(Property.NINE, currentColor, 0));

                    cards.add(new Card(Property.REVERSE, currentColor, 1));
                    cards.add(new Card(Property.SKIP, currentColor, 1));
                    cards.add(new Card(Property.DRAW_TWO, currentColor, 2));
                }
            }
        }

        cards.add(new Card(Property.DRAW_FOUR, Color.BLACK, 10));
        cards.add(new Card(Property.DRAW_FOUR, Color.BLACK, 10));
        cards.add(new Card(Property.DRAW_FOUR, Color.BLACK, 10));
        cards.add(new Card(Property.DRAW_FOUR, Color.BLACK, 10));

        cards.add(new Card(Property.WILD, Color.BLACK, 5));
        cards.add(new Card(Property.WILD, Color.BLACK, 5));
        cards.add(new Card(Property.WILD, Color.BLACK, 5));
        cards.add(new Card(Property.WILD, Color.BLACK, 5));
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
//  in combination with method refill() to manage the empty deck
    public Card drawCard(PlayedCards playedCards) {
        if (cards.size() <= 0) {
            refill(playedCards);
        }
        return cards.pop();
    }
//  every card on the table should be added in a array deadDeck
    public ArrayList<Card> drawCards(int numberOfCards, PlayedCards deadDeck) {
        ArrayList<Card> drawnCards = new ArrayList<>();
        for (int i = 0; i < numberOfCards; i++) {
            drawnCards.add(drawCard(deadDeck));
        }

        return drawnCards;
    }

    public void refill(PlayedCards deadDeck) {
        cards.addAll(deadDeck.getCards());

        shuffle();
    }

    public Stack<Card> getCards() {
        return cards;
    }

    //TODO --> refill should let newest card of deadDeck in deadDeck	
}
