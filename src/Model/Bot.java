package Model;

import Controller.GameBoard;

import java.util.ArrayList;

import static Model.Player.getCards;

public class Bot {
    private String name;
    private int id;
    private ArrayList<Card> deck;
    private GameBoard gameBoard;

    public Bot(String name, int id, GameBoard gameBoard) {
        this.name = name;
        this.id = id;
        deck = new ArrayList<>();
        this.gameBoard = gameBoard;
    }

    public void initialize() {
        deck = new ArrayList<>();
    }

    public int getID() {
        return id;
    }

    public void drawCard(Card card) {
        deck.add(card);
        gameBoard.getController().setBotDeck(this);
    }

    public void drawCards(ArrayList<Card> cards) {
        deck.addAll(cards);
        gameBoard.getController().setBotDeck(this);
    }

    public Card playCard(Card card) {
        deck.remove(card);
        return card;
    }

    public ArrayList<Card> getValidCards(Card lastCard, Color wishColor, boolean challenge) {
        return getCards(lastCard, wishColor, challenge, deck);
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
        ArrayList<Card> validDeck = getValidCards(lastCard, wishColor, challenge);
        System.out.println("validCards: \n" + validDeck);
        if (validDeck.size() == 0) {
            if (challenge) {
                System.out.println("draw " + gameBoard.getDrawnCardsCount() + " cards");
                ArrayList<Card> drawCards = gameBoard.getDeck().drawCards(gameBoard.getDrawnCardsCount(), gameBoard.getPlayedCards());
                if (gameBoard.isRunning()) {
                    gameBoard.getController().moveCardFromDeckToBot(this, drawCards);
                }
            } else {
                System.out.println("draw 1 card");
                ArrayList<Card> drawnCards = new ArrayList<>();
                drawnCards.add(gameBoard.getDeck().drawCard(gameBoard.getPlayedCards()));
                if (gameBoard.isRunning()) {
                    gameBoard.getController().moveCardFromDeckToBot(this, drawnCards);
                }
            }
            System.out.println("deck after draw: " + deck);
        } else {
            System.out.println("choose");
            System.out.println("AI chooses: " + getHighestValuedCard(validDeck));

            Card playedCard = getHighestValuedCard(validDeck);
            Color newWishColor = null;

            if (playedCard.getProperty().equals(Property.WILD) || playedCard.getProperty().equals(Property.DRAW_FOUR)) {
                newWishColor = getBestColor();
            }

            if (gameBoard.isRunning()) {
                gameBoard.getController().moveBotCardToPlayedCards(this, gameBoard.getCurrentPlayer(), playedCard, getCardPositionInDeck(playedCard), newWishColor);
            }
        }
    }

    private Card getHighestValuedCard(ArrayList<Card> validDeck) {
        Card highestValuedCard = validDeck.get(0);
        for (Card currentCard : validDeck) {
            if (currentCard.getValue() > highestValuedCard.getValue()) {
                highestValuedCard = currentCard;
            }
        }

        return highestValuedCard;
    }

    private int getCardPositionInDeck(Card card) {
        for (int i = 0; i < deck.size(); i++) {
            if (deck.get(i).equals(card)) {
                return i;
            }
        }
        return 0;
    }

    private Color getBestColor() {
        int[] times = new int[4];

        for (Card currentCard : deck) {
            switch (currentCard.getColor()) {
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
        for (int i = 1; i < times.length; i++) {
            int newNumber = times[i];
            if ((newNumber > times[maxIndex])) {
                maxIndex = i;
            }
        }

        switch (maxIndex) {
            case 0:
                return Color.YELLOW;
            case 1:
                return Color.RED;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.GREEN;
            default:
                return null;
        }
    }
}