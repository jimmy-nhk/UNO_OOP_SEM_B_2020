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
package Controller;

import Model.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class GameBoard {
    // The deck of the game
    private Deck deck;

    // The cards have been played
    private PlayedCards playedCards;

    // Player plays the game
    private Player player;

    // Bots for playing the game
    private ArrayList<Bot> bots;

    // If that card is draw card.
    private boolean ifDrawnCard;

    // How many cards needed to draw from the drawn card
    private int drawnCardsCount;

    // Set the current Player
    private int positionOfCurrentPlayer;

    // what is the previous card to validate the possible cards in that player
    private Card previousCard;

    // chosen color from the Wild card
    private Color chosenColor;

    // Which direction of the board
    private Direction direction;

    // Link between the logic game and the GUI
    private MainController mainController;


    // To check what is the last player played the reverse card , so as to switch the direction
    private boolean lastPlayerDraw;

    // If the skip card is played
    private boolean skipped;

    // If the game is still running
    private boolean isRunning;

    // Determine whether to show the info
    private boolean showingInfo;

    public GameBoard(MainController mainController, int numberOfBots) {
        this.mainController = mainController;
        deck = new Deck();
        playedCards = new PlayedCards();
        player = new Player(mainController.getPlayerName() + "", this);
        bots = new ArrayList<>();

        if (numberOfBots == 1) {

            bots.add(new Bot("Bot 1", 1, this));

        } else if (numberOfBots == 2) {

            bots.add(new Bot("Bot 1", 1, this));
            bots.add(new Bot("Bot 2", 2, this));

        } else if (numberOfBots == 3) {

            bots.add(new Bot("Bot 1", 3, this));
            bots.add(new Bot("Bot 2", 1, this));
            bots.add(new Bot("Bot 3", 2, this));
        }

        drawnCardsCount = 0;
    }

    // Create a new game
    public void newGame(int numberOfStartingCards) {

        deck = new Deck();
        deck.shuffle();
        playedCards = new PlayedCards();
        drawnCardsCount = 0;
        previousCard = null;
        chosenColor = null;
        ifDrawnCard = false;
        direction = Direction.RIGHT;
        mainController.setImageViewDirection(Direction.RIGHT); // The direction will go to the right at the beginning
        lastPlayerDraw = false;
        skipped = false;
        showingInfo = false;

        player.initialize();

        // The deck will send the number of starting cards to the player
        player.drawCards(deck.drawCards(numberOfStartingCards, playedCards));

        for (Bot currentBot : bots) {

            currentBot.initialize();

            // The deck will send the number of starting cards to the player
            currentBot.drawCards(deck.drawCards(numberOfStartingCards, playedCards));
        }

        // Start the game by draw a card from the main deck to the played cards
        playedCards.add(deck.drawCard(playedCards));

        // Set the previous card by getting the top card of the playedCards
        previousCard = playedCards.getCards().get(playedCards.getCards().size() - 1);

        // Set the previousCard in the GUI scene
        mainController.setPreviousCard(previousCard);

        // Check the card validation
        if (previousCard.getProperty().equals(Property.WILD)) {
            chosenColor = Color.ALL;

            // Set the color
            mainController.chosenWishColor = chosenColor;
            mainController.showCircleWishColor(chosenColor);
        } else if (previousCard.getProperty().equals(Property.DRAW_FOUR)) {
            chosenColor = Color.ALL;
            mainController.chosenWishColor = chosenColor;
            mainController.showCircleWishColor(chosenColor);

            // If it is the draw card, ifDrawnCard will be set
            ifDrawnCard = true;
            drawnCardsCount = 4;
        } else if (previousCard.getProperty().equals(Property.DRAW_TWO)) {
            ifDrawnCard = true;
            drawnCardsCount = 2;
        }
    }

    public void start() {

        // Set the game playing
        isRunning = true;

        // Random players
        Random random = new Random();
        positionOfCurrentPlayer = random.nextInt(bots.size() + 1) + 1;

        // Run the game
        run();
    }

    private void run() {

        // Check if game is still running
        if (isRunning) {
            if (player.getDeckSize() == 0) {

                // If deck is zero , then display the winner
                end(player.getName());

                return;
            }

            for (Bot winningBot : bots) {

                // if any bots is winning , display the message
                if (winningBot.getDeckSize() == 0) {
                    end(winningBot.getName());
                    return;
                }
            }

            // Identify the direction
            determineIfChangeDirection();

            // Identify next player
            identifyNextPlayer();

            System.out.println("Player " + positionOfCurrentPlayer + "'s turn");

            checkSkipCard();

        }


    }

    // Check skip card
    public void checkSkipCard() {
        LanguageController.switchLanguage(SettingsController.locale);
        if (skipped || !previousCard.getProperty().equals(Property.SKIP)) {
            if (positionOfCurrentPlayer == 1) {
                mainController.setLabelCurrentPlayer(player.getName() + " " + LanguageController.get("gameBoard.turn"));

                ArrayList<Card> validDeck = player.getPossiblePlayableCards(previousCard, chosenColor, ifDrawnCard);
                mainController.setValidPlayerDeck(player.getDeck(), validDeck);
                mainController.playerMustDraw = ifDrawnCard && validDeck.size() > 0;

                player.turn(previousCard, chosenColor, ifDrawnCard);
            } else {
                if (isRunning) {
                    Bot currentBot = bots.get(positionOfCurrentPlayer - 2);
                    mainController.setLabelCurrentPlayer(currentBot.getName() + " " + LanguageController.get("gameBoard.turn"));

                    mainController.setBotDeck(currentBot);

                    try {
                        Thread.sleep(700);
                    } catch (InterruptedException e) {
                        //Handle errors
                        e.printStackTrace();
                    }

                    currentBot.turn(previousCard, chosenColor, ifDrawnCard);
                }
            }
        } else {
            if (!skipped) {
                System.out.println("SKIPPED player " + positionOfCurrentPlayer);
                skipped = true;
                run();
            }
        }
    }

    // Check the direction of the code
    public void determineIfChangeDirection() {
        if (previousCard.getProperty().equals(Property.REVERSE) && !lastPlayerDraw) {
            if (direction.equals(Direction.RIGHT)) {
                direction = Direction.LEFT;
                mainController.setImageViewDirection(Direction.LEFT);

            } else {
                direction = Direction.RIGHT;
                mainController.setImageViewDirection(Direction.RIGHT);
            }
        }
    }

    // Determine which is the current player
    private void identifyNextPlayer() {
        if (direction.equals(Direction.RIGHT)) {

            if (positionOfCurrentPlayer == bots.size() + 1) {
                positionOfCurrentPlayer = 1;
            } else {
                positionOfCurrentPlayer++;
            }


        } else {
            if (positionOfCurrentPlayer == 1) {
                positionOfCurrentPlayer = bots.size() + 1;
            } else {
                positionOfCurrentPlayer--;
            }
        }
    }

    //  if the game is ended, display winning message
    private void end(String name) {
        LanguageController.switchLanguage(SettingsController.locale);
        mainController.clearAllDecks(bots);
        mainController.clearAll();
        System.err.println("Player " + name + " wins!");

        isRunning = false;

        // Winning message for player
        if (positionOfCurrentPlayer == 1) {
            player.win();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("UNO");
            alert.setHeaderText("");
            alert.setContentText(LanguageController.get("victory.youWon"));
            alert.initOwner(mainController.stage);
            Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(mainController.icon);
            alert.show();


            // Winning message for bot
        } else {
            player.resetWinsInARow();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("UNO");
            alert.setHeaderText("");
            alert.setContentText(name + " " + LanguageController.get("victory.someoneElseWon"));
            alert.initOwner(mainController.stage);
            Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(mainController.icon);
            alert.show();

        }

        // Back to menu
        mainController.showMenu();
    }

    public Deck getDeck() {
        return deck;
    }

    public PlayedCards getPlayedCards() {
        return playedCards;
    }

    public int getDrawnCardsCount() {
        return drawnCardsCount;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Bot> getBots() {
        return bots;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public int getCurrentPlayer() {
        return positionOfCurrentPlayer;
    }

    public MainController getController() {
        return mainController;
    }

    public boolean isShowingInfo() {
        return showingInfo;
    }

    public void setShowingInfo(boolean showingInfo) {
        this.showingInfo = showingInfo;
    }

    // draw card
    public void draw() {
        // set no drawn card is previously played
        ifDrawnCard = false;
        drawnCardsCount = 0;
        lastPlayerDraw = true;
        mainController.hideLabelChallengeCounter();

        run();
    }

    // Play card
    public void playCard(Card card, Color chosenColor) {

        playedCards.add(card);
        previousCard = card;
        this.chosenColor = chosenColor;

        updateCardStatus(card);

        lastPlayerDraw = false;
        skipped = false;
        mainController.setPreviousCard(previousCard);

        System.out.println("new previousCard: " + previousCard);
        System.out.println("new chosenColor: " + this.chosenColor);
        System.out.println("new challenge: " + ifDrawnCard);
        System.out.println("new challengeCounter: " + drawnCardsCount);

        run();
    }

    // Update status
    public void updateCardStatus(Card card) {
        LanguageController.switchLanguage(SettingsController.locale);
        if (card.getProperty().equals(Property.DRAW_TWO)) {
            ifDrawnCard = true;
            drawnCardsCount += 2;
            mainController.showLabelChallengeCounter(LanguageController.get("gameBoard.loserDraw4Cards") + " " + drawnCardsCount
                    + " " + LanguageController.get("gameBoard.card"));
        } else if (card.getProperty().equals(Property.DRAW_FOUR)) {
            ifDrawnCard = true;
            drawnCardsCount += 4;
            mainController.showLabelChallengeCounter(LanguageController.get("gameBoard.loserDraw4Cards") + " " + drawnCardsCount
                    + " " + LanguageController.get("gameBoard.card"));
        } else {
            ifDrawnCard = false;
            drawnCardsCount = 0;
            mainController.hideLabelChallengeCounter();
        }
    }

    // Stop the game
    public void stop() {
        isRunning = false;
        System.out.println("STOPPED");
    }
}
