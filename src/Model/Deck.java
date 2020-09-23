/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020B
  Assessment: Final Project
  Created date: dd/mm/yyyy (e.g. 31/03/2019)
  By: Your name (Your studen id)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Your name (Your studen id)
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Deck
{
    private Stack<Card> cards;

    public Deck()
    {
        cards = new Stack<>();

        for(Color currentColor : Color.values())
        {
            if(currentColor != Color.BLACK && currentColor != Color.ALL)
            {
                cards.add(new Card(Property.ZERO, currentColor, 0));

                for(int i = 0; i < 2; i++)
                {
                    cards.add(new Card(Property.ONE, currentColor, 0));
                    cards.add(new Card(Property.TWO, currentColor, 0));
                    cards.add(new Card(Property.THREE, currentColor, 0));
                    cards.add(new Card(Property.FOUR, currentColor, 0));
                    cards.add(new Card(Property.FIVE, currentColor, 0));
                    cards.add(new Card(Property.SIX , currentColor, 0));
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

    public void shuffle()
    {
        Collections.shuffle(cards);
    }

    public Card drawCard(PlayedCards playedCards)
    {
        if (cards.size() <= 0) {
            refill(playedCards);
        }
        return cards.pop();
    }

    public ArrayList<Card> drawCards(int numberOfCards, PlayedCards deadDeck)
    {
        ArrayList<Card> drawnCards = new ArrayList<>();
        for(int i = 0; i < numberOfCards; i++)
        {
            drawnCards.add(drawCard(deadDeck));
        }

        return drawnCards;
    }

    public void refill(PlayedCards deadDeck)
    {
        cards.addAll(deadDeck.getCards());

        shuffle();
    }

    public Stack<Card> getCards()
    {
        return cards;
    }

    //TODO --> refill should let newest card of deadDeck in deadDeck	
}