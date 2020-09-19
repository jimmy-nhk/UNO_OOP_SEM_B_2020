package Model;

import Controller.Controller;
import achievements.Achievement.Status;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;

public class Game {

    private Deck deck;
    private PlayedCards playedCards;
    private Player player;
    private ArrayList<Bot> bots;
    private int gameCount;
    private int challengeCounter;
    private int currentPlayer;
    private Card lastCard;
    private Color wishColor;
    private boolean challenge;
    private Direction direction;
    private Controller controller;
    private boolean lastPlayerDraw;
    private boolean skipped;
    private int counter;
    private boolean running;
    private boolean showingInfo;
    private int aiSpeed;

    public Game(Controller controller, int numberOfAIs, int aiSpeed) {
        this.controller = controller;
        this.aiSpeed = aiSpeed;
        deck = new Deck();
        playedCards = new PlayedCards();
        player = new Player("Player", this);
        bots = new ArrayList<Bot>();

        if (numberOfAIs == 1) {

            bots.add(new Bot("Bot 1", 1, this));

        } else if (numberOfAIs == 2) {

            bots.add(new Bot("Bot 1", 1, this));
            bots.add(new Bot("Bot 2", 2, this));

        } else if (numberOfAIs == 3) {

            bots.add(new Bot("Bot 1", 3, this));
            bots.add(new Bot("Bot 2", 1, this));
            bots.add(new Bot("Bot 3", 2, this));
        }

        gameCount = 0;
        challengeCounter = 0;
    }

    public void newGame(int numberOfStartingCards) {

        deck = new Deck();
        deck.shuffle();
        playedCards = new PlayedCards();
        gameCount++;
        challengeCounter = 0;
        lastCard = null;
        wishColor = null;
        challenge = false;
        direction = Direction.RIGHT;
        controller.setImageViewDirection(Direction.RIGHT);
        lastPlayerDraw = false;
        skipped = false;
        showingInfo = false;

        player.initialize();

        player.drawCards(deck.drawCards(numberOfStartingCards, playedCards));

        for (Bot currentBot : bots) {
            currentBot.initialize();
            currentBot.drawCards(deck.drawCards(numberOfStartingCards, playedCards));
        }

        playedCards.add(deck.drawCard(playedCards));
        lastCard = playedCards.getCards().get(playedCards.getCards().size() - 1);

        controller.setLastCard(lastCard);
        if (lastCard.getType().equals(Property.WILD)) {
            wishColor = Color.ALL;
            controller.chosenWishColor = wishColor;
            controller.showCircleWishColor(wishColor);
        } else if (lastCard.getType().equals(Property.DRAW_FOUR)) {
            wishColor = Color.ALL;
            controller.chosenWishColor = wishColor;
            controller.showCircleWishColor(wishColor);
            challenge = true;
            challengeCounter = 4;
        } else if (lastCard.getType().equals(Property.DRAW_TWO)) {
            challenge = true;
            challengeCounter = 2;
        }
    }

    public int getGameCount() {
        return gameCount;
    }

    public void start() {
        running = true;
        Random random = new Random();
        currentPlayer = random.nextInt(bots.size() + 1) + 1;

        counter = 0;

        run();
    }

    private void run() {
        if (running) {
            if (player.getDeckSize() == 0) {
                end(player.getName());
                return;
            }

            for (Bot winningBot : bots) {
                if (winningBot.getDeckSize() == 0) {
                    end(winningBot.getName());
                    return;
                }
            }

            System.out.println("Round: " + counter / 4 + 1);

            if (lastCard.getType().equals(Property.REVERSE) && !lastPlayerDraw) {
                if (direction.equals(Direction.RIGHT)) {
                    direction = Direction.LEFT;
                    controller.setImageViewDirection(Direction.LEFT);

                } else {
                    direction = Direction.RIGHT;
                    controller.setImageViewDirection(Direction.RIGHT);
                }
            }

            determineNextPlayer();

            System.out.println("Player " + currentPlayer + "'s turn");

            if (skipped || !lastCard.getType().equals(Property.SKIP)) {
                if (currentPlayer == 1) {
                    controller.setLabelCurrentPlayer(player.getName() + "'s turn");

                    ArrayList<Card> validDeck = player.getPossiblePlayableCards(lastCard, wishColor, challenge);
                    controller.setValidPlayerDeck(player.getDeck(), validDeck);

                    controller.playerMustChallenge = false;
                    if (challenge && validDeck.size() > 0) {
                        controller.playerMustChallenge = true;
                    }

                    player.turn(lastCard, wishColor, challenge);
                } else {
                    if (running) {
                        Bot currentBot = bots.get(currentPlayer - 2);

                        controller.setLabelCurrentPlayer(currentBot.getName() + "'s turn");

                        controller.setAIDeck(currentBot);

                        try {
                            switch (aiSpeed) {
                                case 1:
                                    Thread.sleep(500);
                                    break;
                                case 2:
                                    Thread.sleep(250);
                                    break;
                                case 3:
                                    Thread.sleep(50);
                                    break;
                                case 4:
                                    Thread.sleep(0);
                                    break;
                                default:
                                    break;
                            }
                        } catch (InterruptedException e) {
                            //Handle errors
                            e.printStackTrace();
                        }

                        currentBot.turn(lastCard, wishColor, challenge);
                    }
                }
            } else {
                if (!skipped) {
                    System.out.println("SKIPPED player " + currentPlayer);
                    skipped = true;
                    run();
                }
            }
            counter++;
        }
    }

    private void determineNextPlayer() {
        if (direction.equals(Direction.RIGHT)) {
            if (currentPlayer == bots.size() + 1) {
                currentPlayer = 1;
            } else {
                currentPlayer++;
            }
        } else {
            if (currentPlayer == 1) {
                currentPlayer = bots.size() + 1;
            } else {
                currentPlayer--;
            }
        }
    }

    private void end(String name) {
        controller.clearAllDecks(bots);
        controller.clearAll();
        System.err.println("Player " + name + " wins!");

        running = false;

        if (currentPlayer == 1) {
            player.win();

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Victory!");
            alert.setHeaderText("");
            alert.setContentText("You won!");
            alert.initOwner(controller.stage);
            Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(controller.icon);
            alert.show();

            controller.showNeutralUI();

            try {
                controller.handler.unlockAchievement(0);
                controller.handler.incrementAchievement(1, 1);
                controller.handler.incrementAchievement(2, 1);
                controller.handler.incrementAchievement(3, 1);
                controller.handler.incrementAchievement(4, 1);
                controller.handler.checkAllIncrementalAchievements();
                controller.handler.saveAndLoad();
            } catch (Exception e) {
            }
        } else {
            player.resetWinsInARow();

            try {
                if (controller.handler.getAchievements().get(3).getStatus().equals(Status.LOCKED)) {
                    controller.handler.resetAchievement(3);
                }
                if (controller.handler.getAchievements().get(4).getStatus().equals(Status.LOCKED)) {
                    controller.handler.resetAchievement(4);
                }
                controller.handler.saveAndLoad();
            } catch (Exception e) {
            }

            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Defeated!");
            alert.setHeaderText("");
            alert.setContentText(name + " has won.");
            alert.initOwner(controller.stage);
            Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
            dialogStage.getIcons().add(controller.icon);
            alert.show();

            controller.showNeutralUI();
        }
    }

    public Deck getDeck() {
        return deck;
    }

    public PlayedCards getDeadDeck() {
        return playedCards;
    }

    public int getChallengeCounter() {
        return challengeCounter;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Bot> getAIs() {
        return bots;
    }

    public boolean isRunning() {
        return running;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public Controller getController() {
        return controller;
    }

    public boolean isShowingInfo() {
        return showingInfo;
    }

    public void setShowingInfo(boolean showingInfo) {
        this.showingInfo = showingInfo;
    }

    public void draw() {
        challenge = false;
        challengeCounter = 0;
        lastPlayerDraw = true;
        controller.hideLabelChallengeCounter();

        run();
    }

    public void playCard(Card card, Color wishColor) {
        playedCards.add(card);
        lastCard = card;
        this.wishColor = wishColor;

        if (card.getType().equals(Property.DRAW_TWO)) {
            challenge = true;
            challengeCounter += 2;
            controller.showLabelChallengeCounter("Loser draws " + challengeCounter + " cards");
        } else if (card.getType().equals(Property.DRAW_FOUR)) {
            challenge = true;
            challengeCounter += 4;
            controller.showLabelChallengeCounter("Loser draws " + challengeCounter + " cards");
        } else {
            challenge = false;
            challengeCounter = 0;
            controller.hideLabelChallengeCounter();
        }

        lastPlayerDraw = false;
        skipped = false;
        controller.setLastCard(lastCard);

        System.out.println("new lastCard: " + lastCard);
        System.out.println("new wishColor: " + this.wishColor);
        System.out.println("new challenge: " + challenge);
        System.out.println("new challengeCounter: " + challengeCounter);

        run();
    }

    public void stop() {
        running = false;
        System.out.println("STOPPED");
    }
}