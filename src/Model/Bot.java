package Model;

import Controller.GameBoard;

import java.util.ArrayList;

public class Bot
{
	private String name;
	private int id;
	private ArrayList<Card> deck;
	private int wins;
	private GameBoard gameBoard;

	public Bot(String name, int id, GameBoard gameBoard)
	{
		this.name = name;
		this.id = id;
		deck = new ArrayList<>();
		wins = 0;
		this.gameBoard = gameBoard;
	}

	public void initialize()
	{
		deck = new ArrayList<>();
	}

	public void win()
	{
		wins++;
	}

	public int getWins()
	{
		return wins;
	}

	public int getID()
	{
		return id;
	}

	public void drawCard(Card card)
	{
		deck.add(card);
		gameBoard.getController().setAIDeck(this);
	}

	public void drawCards(ArrayList<Card> cards)
	{
		deck.addAll(cards);
		gameBoard.getController().setAIDeck(this);
	}

	public Card playCard(Card card)
	{
		deck.remove(card);
		return card;
	}

	public ArrayList<Card> getValidCards(Card lastCard, Color wishColor, boolean challenge)
	{
		ArrayList<Card> validCards = new ArrayList<Card>();
		if(challenge)
		{
			for(Card currentCard : deck)
			{
				if(lastCard.getProperty().equals(Property.DRAW_TWO))
				{
					if(gameBoard.getController().settings.isAllowChallengePlusTwo())
					{
						if(currentCard.getProperty().equals(Property.DRAW_TWO) || currentCard.getProperty().equals(Property.DRAW_FOUR))
						{
							validCards.add(currentCard);
						}
					}
				}
				else // lastCard == +4
				{
					if(gameBoard.getController().settings.isAllowChallengePlusFourWithFour())
					{
						if(currentCard.getProperty().equals(Property.DRAW_FOUR))
						{
							validCards.add(currentCard);
						}
					}

					if(gameBoard.getController().settings.isAllowChallengePlusFourWithTwo())
					{
						if(currentCard.getProperty().equals(Property.DRAW_TWO))
						{
							if(wishColor == Color.ALL)
							{
								validCards.add(currentCard);
							}
							else if(currentCard.getColor().equals(wishColor))
							{
								validCards.add(currentCard);
							}
						}
					}
				}
			}
		}
		else
		{
			if(wishColor == null)
			{
				for(Card currentCard : deck)
				{
					if(currentCard.getColor().equals(lastCard.getColor()) || currentCard.getProperty().equals(lastCard.getProperty()) || currentCard.getProperty().equals(Property.WILD) || currentCard.getProperty().equals(Property.DRAW_FOUR))
					{
						validCards.add(currentCard);
					}
				}
			}
			else if(wishColor.equals(Color.ALL))
			{
				for(Card currentCard : deck)
				{
					if(!currentCard.getProperty().equals(Property.WILD) && !currentCard.getProperty().equals(Property.DRAW_FOUR))
					{
						validCards.add(currentCard);
					}
				}
			}
			else
			{
				for(Card currentCard : deck)
				{
					if(currentCard.getColor().equals(wishColor))
					{
						validCards.add(currentCard);
					}
				}
			}
		}

		return validCards;
	}
	public int getDeckSize()
	{
		return deck.size();
	}

	public String getName()
	{
		return name;
	}

	public ArrayList<Card> getDeck()
	{
		return deck;
	}

	public void turn(Card lastCard, Color wishColor, boolean challenge)
	{
		System.out.println("All cards on hand: \n" + deck);
		ArrayList<Card> validDeck = getValidCards(lastCard, wishColor, challenge);
		System.out.println("validCards: \n" + validDeck);
		if(validDeck.size() == 0)
		{
			if(challenge)
			{
				System.out.println("draw " + gameBoard.getDrawnCardsCount() + " cards");
				ArrayList<Card> drawCards = gameBoard.getDeck().drawCards(gameBoard.getDrawnCardsCount(), gameBoard.getPlayedCards());
				if(gameBoard.isRunning())
				{
					gameBoard.getController().moveCardFromDeckToAI(this, drawCards);
				}
				System.out.println("deck after draw: " + deck);
			}
			else
			{
				System.out.println("draw 1 card");
				ArrayList<Card> drawnCards = new ArrayList<Card>();
				drawnCards.add(gameBoard.getDeck().drawCard(gameBoard.getPlayedCards()));
				if(gameBoard.isRunning())
				{
					gameBoard.getController().moveCardFromDeckToAI(this, drawnCards);
				}
				System.out.println("deck after draw: " + deck);
			}
		}
		else
		{
			System.out.println("choose");
			System.out.println("AI chooses: " + getHighestValuedCard(validDeck));

			Card playedCard = getHighestValuedCard(validDeck);
			Color newWishColor = null;

			if(playedCard.getProperty().equals(Property.WILD) || playedCard.getProperty().equals(Property.DRAW_FOUR))
			{
				newWishColor = getBestColor();				
			}

			if(gameBoard.isRunning())
			{
				gameBoard.getController().moveBotCardToPlayedCards(this, gameBoard.getCurrentPlayer(), playedCard, getCardPositionInDeck(playedCard), newWishColor);
			}
		}
	}

	private Card getHighestValuedCard(ArrayList<Card> validDeck)
	{
		Card highestValuedCard = validDeck.get(0);
		for(Card currentCard : validDeck)
		{
			if(currentCard.getValue() > highestValuedCard.getValue())
			{
				highestValuedCard = currentCard;
			}
		}

		return highestValuedCard;
	}
	
	private int getCardPositionInDeck(Card card)
	{
		for(int i = 0; i < deck.size(); i++)
		{
			if(deck.get(i).equals(card))
			{
				return i;
			}
		}
		return 0;
	}

	private Color getBestColor()
	{
		int[] times = new int[4];

		for(Card currentCard : deck)
		{
			switch(currentCard.getColor())
			{
				case YELLOW:
				case BLUE:
				case RED:
				case GREEN:
					times[0]++;
					break;
				default:
					break;
			}
		}

		int maxIndex = 0;
		for(int i = 1; i < times.length; i++)
		{
			int newNumber = times[i];
			if((newNumber > times[maxIndex]))
			{
				maxIndex = i;
			}
		}
		
		switch(maxIndex)
		{
			case 0:	return Color.YELLOW;
			case 1: return Color.RED;
			case 2: return Color.BLUE;
			case 3: return Color.GREEN;
			default: return null;
		}		
	}
}