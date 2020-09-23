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

import Controller.GameBoard;
import Controller.LanguageController;
import Controller.SettingsController;

import java.io.*;
import java.util.ArrayList;

public class Player {
    //data field:
    private String name;
    private ArrayList<Card> deck;
    private int winsInARow;
    private GameBoard gameBoard;
    //constructor:
    public Player(String name, GameBoard gameBoard) {
        this.name = name;
        deck = new ArrayList<>();
        winsInARow = 0;
        this.gameBoard = gameBoard;
    }
    //initialise each player with a deck:
    public void initialize() {
        deck = new ArrayList<>();
    }
    //determine the winner:
    public void win() {
        FileInputStream fis = null; //save the activity in a file for leader board:
        try {
            ArrayList<String> namesList;
            ArrayList<Integer> winList;
            fis = new FileInputStream("listName");
            ObjectInputStream ois = new ObjectInputStream(fis);
            namesList = (ArrayList) ois.readObject();
            ois.close();
            fis.close();
            FileInputStream fis1 = new FileInputStream("listWin");
            ObjectInputStream ois1 = new ObjectInputStream(fis1);
            winList = (ArrayList) ois1.readObject();
            ois.close();
            fis.close();
            for (int i = 0; i < namesList.size(); i++) {
                if (this.name.equals(namesList.get(i))) {
                    int count = winList.get(i);
                    winList.remove(i);
                    winList.add(i, count + 1);
                }
            }
            FileOutputStream fos = new FileOutputStream("listName");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(namesList);
            oos.close();
            fos.close();
            FileOutputStream fos1 = new FileOutputStream("listWin");
            ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
            oos1.writeObject(winList);
            oos1.close();
            fos1.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        winsInARow++;
    }

    //reset the win in a row:
    public void resetWinsInARow() {
        winsInARow = 0;
    }
    //player draw a card:
    public void drawCard(Card card) {
        deck.add(card);

        gameBoard.getController().setPlayerDeck(deck);
    }
     //player draw list of cards:
    public void drawCards(ArrayList<Card> cards) {
        deck.addAll(cards);
        gameBoard.getController().setPlayerDeck(deck);
        gameBoard.getController().hideInfo();
    }

    //player play cards:
    public Card playCard(Card card) {
        deck.remove(card);
        return card;
    }

    //check the card of player when they play cards:
    public ArrayList<Card> getPossiblePlayableCards(Card lastCard, Color wishColor, boolean challenge) {
        return getCards(lastCard, wishColor, challenge, deck);
    }
     //get and check if the move is correct:
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
    //get the deck size:
    public int getDeckSize() {
        return deck.size();
    }
    // get the player name:
    public String getName() {
        return name;
    }
    //get deck:
    public ArrayList<Card> getDeck() {
        return deck;
    }
    //update the player turns:
    public void turn(Card lastCard, Color wishColor, boolean challenge) {
        LanguageController.switchLanguage(SettingsController.locale);
        System.out.println("All cards on hand: \n" + deck);
        ArrayList<Card> validDeck = getPossiblePlayableCards(lastCard, wishColor, challenge);
        System.out.println("validCards: \n" + validDeck);
        if (validDeck.size() == 0) {
            if (challenge) {
                gameBoard.setShowingInfo(true);
                gameBoard.getController().showInfo(LanguageController.get("gameBoard.showInfo") + " " + gameBoard.getDrawnCardsCount() + " " + LanguageController.get("gameBoard.card"), gameBoard.getDrawnCardsCount());
            } else {
                System.out.println("No valid cards --> please draw");
            }
        } else {
            System.out.println("choose");
        }
    }

}
