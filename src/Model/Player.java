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
    public Player(Account account) {
        this.account = account;
        cardList = new ArrayList<Card>();
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

    public void playCard(Card card) {
        for (int i=0 ; i < cardList.size(); i ++) {
            if (cardList.get(i).equals(card)) {
                cardList.remove(i);
            }
        }
    }
}
