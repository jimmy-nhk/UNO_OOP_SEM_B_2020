package Model;

import java.util.ArrayList;

public class PlayedCards
{
	private ArrayList<Card> cards;

	public PlayedCards()
	{
		cards = new ArrayList<Card>();
	}
	
	public void add(Card card)
	{
		cards.add(card);
	}

	public ArrayList<Card> getCards()
	{
		return cards;
	}	
}