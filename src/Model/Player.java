package Model;

import java.util.ArrayList;

public class Player {

    private Account account;
    private ArrayList<Card> cardList;

    //  Default Constructor
    public Player() {
        account = new Account();
        cardList = new ArrayList<Card>();
    }

    //  Constructor
    public Player(Account account , ArrayList<Card> cardList) {
        this.account = account;
        this.cardList = cardList;

    }

    //  Accessor
    public Account getAccount() {
        return account;
    }

    public ArrayList<Card> getCardList() {
        return cardList;
    }

    //  Mutator
    public void setAccount(Account account) {
        this.account = account;
    }


    public void drawCard(Card card) {
        cardList.add(card);
    }

    public Card playCard(Card selectedCard) {
        for (int i=0 ; i < cardList.size(); i ++) {
            if (cardList.get(i).equals(selectedCard)) {
                cardList.remove(i);
            }
        }
        return selectedCard;
    }
}
