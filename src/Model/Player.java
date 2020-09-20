package Model;

import Controller.GameBoard;

import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Card> deck;
    private int winsInARow;
    private GameBoard gameBoard;

    public Player(String name, GameBoard gameBoard) {
        this.name = name;
        deck = new ArrayList<>();
        winsInARow = 0;
        this.gameBoard = gameBoard;
    }

    public void initialize() {
        deck = new ArrayList<>();
    }

    public void win() {
        winsInARow++;
    }

    public void resetWinsInARow() {
        winsInARow = 0;
    }

    public void drawCard(Card card) {
        deck.add(card);
        if (getNumberOfDrawFourCards() >= 2) {
            try {
                gameBoard.getController().handler.unlockAchievement(8);
                gameBoard.getController().handler.saveAndLoad();
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

        gameBoard.getController().setPlayerDeck(deck);
    }

    public void drawCards(ArrayList<Card> cards) {
        deck.addAll(cards);
        gameBoard.getController().setPlayerDeck(deck);
        gameBoard.getController().hideInfo();
    }

    public Card playCard(Card card) {
        deck.remove(card);
        return card;
    }

    public ArrayList<Card> getPossiblePlayableCards(Card lastCard, Color wishColor, boolean challenge) {
        return getCards(lastCard, wishColor, challenge, deck);
    }

    static ArrayList<Card> getCards(Card lastCard, Color wishColor, boolean challenge, ArrayList<Card> deck) {
        ArrayList<Card> validCards = new ArrayList<>();
        if (challenge) {
            for (Card currentCard : deck) {
                if (lastCard.getProperty().equals(Property.DRAW_TWO)) {
                    if (currentCard.getProperty().equals(Property.DRAW_TWO) || currentCard.getProperty().equals(Property.DRAW_FOUR)) {
                        validCards.add(currentCard);
                    }
                } else // lastCard == +4
                {
                    if (currentCard.getProperty().equals(Property.DRAW_FOUR)) {
                        validCards.add(currentCard);
                    }

                    if (currentCard.getProperty().equals(Property.DRAW_TWO)) {
                        if (wishColor == Color.ALL) {
                            validCards.add(currentCard);
                        } else if (currentCard.getColor().equals(wishColor)) {
                            validCards.add(currentCard);
                        }
                    }
                }
            }
        } else {
            if (wishColor == null) {
                for (Card currentCard : deck) {
                    if (currentCard.getColor().equals(lastCard.getColor()) || currentCard.getProperty().equals(lastCard.getProperty()) || currentCard.getProperty().equals(Property.WILD) || currentCard.getProperty().equals(Property.DRAW_FOUR)) {
                        validCards.add(currentCard);
                    }
                }
            } else if (wishColor.equals(Color.ALL)) {
                for (Card currentCard : deck) {
                    if (!currentCard.getProperty().equals(Property.WILD) && !currentCard.getProperty().equals(Property.DRAW_FOUR)) {
                        validCards.add(currentCard);
                    }
                }
            } else {
                for (Card currentCard : deck) {
                    if (currentCard.getColor().equals(wishColor)) {
                        validCards.add(currentCard);
                    }
                }
            }
        }

        return validCards;
    }

    public int getDeckSize() {
        return deck.size();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getDeck() {
        return deck;
    }

    public void turn(Card lastCard, Color wishColor, boolean challenge) {
        System.out.println("All cards on hand: \n" + deck);
        ArrayList<Card> validDeck = getPossiblePlayableCards(lastCard, wishColor, challenge);
        System.out.println("validCards: \n" + validDeck);
        if (validDeck.size() == 0) {
            if (challenge) {
                gameBoard.setShowingInfo(true);
                gameBoard.getController().showInfo("You can not deal card. Draw " + gameBoard.getDrawnCardsCount() + " cards.", gameBoard.getDrawnCardsCount());
            } else {
                System.out.println("No valid cards --> please draw");
            }
        } else {
            System.out.println("choose");
        }
    }

    private int getNumberOfDrawFourCards() {
        int counter = 0;
        for (Card card : deck) {
            if (card.getProperty().equals(Property.DRAW_FOUR)) {
                counter++;
            }
        }
        return counter;
    }
}