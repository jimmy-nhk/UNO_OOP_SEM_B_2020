package Model;

import java.util.ArrayList;

public class Bot {
    private static int total = 0;
    private ArrayList<Card> cardList;
    private int replica = 0;

    //  Default Constructor
    public Bot() {
        replica = total;
        cardList = new ArrayList<Card>();
        total ++;
    }

    //Mutators and Accessors
    public static int getTotal() {
        return total;
    }

    public static void setTotal(int total) {
        Bot.total = total;
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    public void setCardList(ArrayList<Card> cardList) {
        this.cardList = cardList;
    }

    public int getReplica() {
        return replica;
    }

    public void setReplica(int replica) {
        this.replica = replica;
    }
    public Card play(Card previousCard) {
        Card newCard = null;
        for (int i=0; i < cardList.size();i++) {
            if (cardList.get(i).getProperty().equals(previousCard.getProperty()) || cardList.get(i).getValue().equals(previousCard.getValue())) {
                newCard = cardList.get(i);
                cardList.remove(i);
                if (i == cardList.size()-1) {
                    return null;
                }
            }
        }
        return newCard;
    }

    public void draw(Card card) {
        cardList.add(card);
    }
}
