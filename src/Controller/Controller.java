package Controller;

import Model.*;
import achievements.Achievement;
import achievements.Achievement.Status;
import achievements.AchievementHandler;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import tools.PathUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;

public class Controller {
    private final ResourceBundle bundle = ResourceBundle.getBundle("resources/", Locale.ENGLISH);
    private final double CARD_HEIGHT = 90.0;
    private final double CARD_WIDTH = 57.0;
    private final double CARD_SPACING_LARGE = 14.0;
    private final double CARD_SPACING_MEDIUM = 0.0;
    private final double CARD_SPACING_SMALL = -25.0;
    private final double CARD_SPACING_ULTRA_SMALL = -35.0;
    private final Point2D AI_1_STARTING_POINT = new Point2D(100.0, 75.0);
    private final javafx.scene.paint.Color COLOR_YELLOW = javafx.scene.paint.Color.web("#FFAA00");
    private final javafx.scene.paint.Color COLOR_RED = javafx.scene.paint.Color.web("#FF5555");
    private final javafx.scene.paint.Color COLOR_BLUE = javafx.scene.paint.Color.web("#5555FD");
    private final javafx.scene.paint.Color COLOR_GREEN = javafx.scene.paint.Color.web("#55AA55");
    public Game game;
    public Color chosenWishColor;
    public int drawCounter;
    public Settings settings;
    public AchievementHandler handler;
    public boolean playerMustChallenge;
    public TranslateTransition translateTransition;
    public Stage stage;
    public Image icon = new Image("images/icon.png");
    public static final Sound backgroundMusic = new Sound("src/resources/sound/background.mp3");

    @FXML
    private ImageView iconLastCard;
    @FXML
    private ImageView iconDeck;
    @FXML
    private Label labelCurrentPlayer;
    @FXML
    private AnchorPane mainPane;
    @FXML
    private Label labelWishColor;
    @FXML
    private Circle circleWishColor;
    @FXML
    private ImageView imageViewWishColor;
    @FXML
    private HBox hboxInfo;
    @FXML
    private Label labelInfo;
    @FXML
    private Button buttonInfo;
    @FXML
    private Label labelChallengeCounter;
    @FXML
    private ImageView imageViewDirection;
    @FXML
    private Label labelDirection;
    @FXML
    private Label labelAI1Name;
    @FXML
    private Label labelAI2Name;
    @FXML
    private Label labelAI3Name;
    @FXML
    private Button buttonStart;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Menu menu1;
    @FXML
    private MenuItem menuItem1;
    @FXML
    private MenuItem menuItem3;
    @FXML
    private MenuItem menuItemBack;
    @FXML
    private ImageView imageViewLogo;
    @FXML
    private Label labelLogo;
    @FXML
    private Button buttonNewGame;
    @FXML

    private Button buttonSettings;
    private int secretCounter;
    private boolean playerHasDrawn;
    private Point2D PLAYER_STARTING_POINT;
    private Point2D AI_2_STARTING_POINT;
    private Point2D AI_3_STARTING_POINT;

    public void init() {
        imageViewWishColor.setImage(new Image("/images/circle-all.png"));

        PLAYER_STARTING_POINT = new Point2D(100.0, stage.getScene().getHeight() - 50.0 - CARD_HEIGHT);
        AI_2_STARTING_POINT = new Point2D(stage.getScene().getWidth() - CARD_HEIGHT - 30, 70.0);
        AI_3_STARTING_POINT = new Point2D(60.0, 70.0);

        clearAll();
        showNeutralUI();

        settings = new Settings();
        try {
            settings.load();
        } catch (Exception e) {
            e.printStackTrace();
        }

        handler = new AchievementHandler(stage);
        handler.setPath(PathUtils.getOSindependentPath() + "/OOP/UNO/achievements.save");
        try {
            handler.loadAchievements();
        } catch (Exception e) {
            // if file does not exist, create a new file
            createAchievements();
            try {
                handler.loadAchievements();
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }

        iconLastCard.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (secretCounter < 5) {
                secretCounter++;
                if (secretCounter == 5) {
                    try {
                        handler.unlockAchievement(9);
                        handler.saveAndLoad();
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            }
        });
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Start the game
    public void startGame() {
        Sound startGameSound = new Sound("src/resources/sound/sound_launch.mp3");

        if (game != null) {
            game.stop();
        }

        clearAll();

        drawCounter = 0;
        playerHasDrawn = false;
        playerMustChallenge = false;

        labelCurrentPlayer.setVisible(true);
        labelCurrentPlayer.setText("");

        iconDeck.setImage(createEmptyBackCard());

        iconDeck.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (game.isRunning() && game.getCurrentPlayer() == 1 && !game.isShowingInfo() && !playerHasDrawn && !playerMustChallenge) {
                playerHasDrawn = true;
                playerMustChallenge = false;
                Card drawCard = game.getDeck().drawCard(game.getDeadDeck());
                ArrayList<Card> allCards = new ArrayList<>();
                allCards.add(drawCard);
                moveCardFromDeckToPlayer(allCards);
            }
        });

        game = new Game(this, settings.getNumberOfBots(), settings.getBotSpeed());
        setLabelNames(game.getPlayer(), game.getAIs());
        game.newGame(settings.getNumberOfStartingCards());

        buttonStart.setOnAction(event -> {
            buttonStart.setVisible(false);
            game.start();
        });
        buttonStart.setVisible(true);
    }

    // Show main menu
    public void showMainMenu() {
        Sound startGameSound = new Sound("src/resources/sound/sound_launch.mp3");
        if (game != null) {
            game.stop();
        }

        clearAll();
        clearPlayerDeck();
        clearAllDecks(game.getAIs());

        showNeutralUI();
    }


    public void showNeutralUI() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");
        imageViewLogo.setVisible(true);
        labelLogo.setVisible(true);
        buttonNewGame.setVisible(true);
        buttonSettings.setVisible(true);
    }

    public void hideNeutralUI() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        imageViewLogo.setVisible(false);
        labelLogo.setVisible(false);
        buttonNewGame.setVisible(false);
        buttonSettings.setVisible(false);
    }

    public void setLabelNames(Player player, ArrayList<Bot> bots) {
        labelAI2Name.setVisible(false);
        labelAI3Name.setVisible(false);

        labelAI1Name.setText(bots.get(0).getName());
        labelAI1Name.setVisible(true);

        if (bots.size() >= 2) {
            labelAI2Name.setText(bots.get(1).getName());
            labelAI2Name.setVisible(true);
        }

        if (bots.size() == 3) {
            labelAI1Name.setText(bots.get(1).getName());
            labelAI2Name.setText(bots.get(2).getName());
            labelAI3Name.setText(bots.get(0).getName());
            labelAI3Name.setVisible(true);
        }
    }

    public void showCircleWishColor(Color color) {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        hideImageViewWishColor();

        Sound buttonClickingSound1 = new Sound("src/resources/sound/sound_button_click.mp3");


        switch (color) {
            case YELLOW:
                circleWishColor.setFill(COLOR_YELLOW);
                circleWishColor.setVisible(true);
                break;
            case RED:
                circleWishColor.setFill(COLOR_RED);
                circleWishColor.setVisible(true);
                break;
            case BLUE:
                circleWishColor.setFill(COLOR_BLUE);
                circleWishColor.setVisible(true);
                break;
            case GREEN:
                circleWishColor.setFill(COLOR_GREEN);
                circleWishColor.setVisible(true);
                break;
            case ALL:
                showImageViewWishColor();
                break;
            default:
                break;
        }

        labelWishColor.setVisible(true);
    }

    public void showImageViewWishColor() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        hideCircleWishColor();
        Sound buttonClickingSound1 = new Sound("src/resources/sound/sound_button_click.mp3");

        imageViewWishColor.setVisible(true);
    }

    public void hideCircleWishColor() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        labelWishColor.setVisible(false);
        circleWishColor.setVisible(false);
    }

    public void hideImageViewWishColor() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        imageViewWishColor.setVisible(false);
        circleWishColor.setVisible(false);
    }

    public void hideWishColor() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        hideCircleWishColor();
        hideImageViewWishColor();
    }

    public void hideInfo() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        hboxInfo.setVisible(false);
    }

    public void showInfo(String text, int numberOfCards) {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        labelInfo.setText(text);
        buttonInfo.setOnAction(event -> {
            if (game.getChallengeCounter() > 10) {
                try {
                    handler.unlockAchievement(5);
                    handler.saveAndLoad();
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }
            moveCardFromDeckToPlayer(game.getDeck().drawCards(game.getChallengeCounter(), game.getDeadDeck()));
        });

        hboxInfo.setVisible(true);
    }

    public void hideLabelChallengeCounter() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");
        labelChallengeCounter.setVisible(false);
    }

    public void showLabelChallengeCounter(String text) {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        labelChallengeCounter.setText(text);
        labelChallengeCounter.setVisible(true);
    }

    public void hideImageViewDirection() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        imageViewDirection.setVisible(false);
        labelDirection.setVisible(false);
    }

    public void setImageViewDirection(Direction direction) {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        imageViewDirection.setVisible(true);
        labelDirection.setVisible(true);

        if (direction.equals(Direction.RIGHT)) {
            imageViewDirection.setImage(new Image("/images/DIRECTION_RIGHT.png"));
        } else {
            imageViewDirection.setImage(new Image("/images/DIRECTION_LEFT.png"));
        }
    }

    public void setLabelCurrentPlayer(String text) {
        labelCurrentPlayer.setText(text);
    }

    public void setLastCard(Card card) {
        iconLastCard.setImage(createCard(card, true).getImage());
    }

    private Image createEmptyBackCard() {
        return new Image("images/card-back.png");
    }

    private ImageView createBackCard() {
        ImageView imageView = new ImageView(new Image("images/card-back.png"));
        imageView.setFitHeight(CARD_HEIGHT);
        imageView.setFitWidth(CARD_WIDTH);

        return imageView;
    }

    // Loading cards from the resources
    private ImageView createCard(Card card, boolean valid) {
        ImageView imageView = new ImageView(new Image("images/" + card.getType() + "-" + card.getColor() + ".png"));
        imageView.setFitHeight(CARD_HEIGHT);
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setSmooth(true);

        if (!valid) {
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(javafx.scene.paint.Color.TRANSPARENT);
            WritableImage snapshot = imageView.snapshot(parameters, null);

            if (card.getType().equals(Property.DRAW_FOUR) && card.getType().equals(Property.WILD)) {
                for (int x = 0; x < snapshot.getWidth(); x++) {
                    for (int y = 0; y < snapshot.getHeight(); y++) {
                        javafx.scene.paint.Color oldColor = snapshot.getPixelReader().getColor(x, y).desaturate().desaturate().brighter();
                        snapshot.getPixelWriter().setColor(x, y, new javafx.scene.paint.Color(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue(), oldColor.getOpacity() * 1.0));
                    }
                }
                imageView.setImage(snapshot);
            } else {
                for (int x = 0; x < snapshot.getWidth(); x++) {
                    for (int y = 0; y < snapshot.getHeight(); y++) {
                        javafx.scene.paint.Color oldColor = snapshot.getPixelReader().getColor(x, y).darker().desaturate();
                        snapshot.getPixelWriter().setColor(x, y, new javafx.scene.paint.Color(oldColor.getRed(), oldColor.getGreen(), oldColor.getBlue(), oldColor.getOpacity() * 1.0));
                    }
                }
                imageView.setImage(snapshot);
            }
        }
        Controller main = this;

        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");
            Sound dealCardSound = new Sound("src/resources/sound/Card_Dealing.mp3");

            @Override
            public void handle(MouseEvent event) {
                if (game.isRunning() && game.getCurrentPlayer() == 1) {
                    if (valid) {
                        if (card.getType().equals(Property.WILD) || card.getType().equals(Property.DRAW_FOUR)) {
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/ColorChooser.fxml"));

                                Parent root = fxmlLoader.load();
                                Stage newStage = new Stage();
                                newStage.setScene(new Scene(root, 300, 300));
                                newStage.setTitle("Chosen color");
                                newStage.initOwner(stage);

                                newStage.getIcons().add(icon);

                                ColorChooserController newController = fxmlLoader.getController();
                                newController.init(newStage, main);

                                newStage.initModality(Modality.APPLICATION_MODAL);
                                newStage.setResizable(false);
                                newStage.showAndWait();

                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        } else {
                            chosenWishColor = null;
                        }

                        moveCardToDeadDeck(imageView, card, chosenWishColor);
                    }
                }
            }
        });

        return imageView;
    }

    public void moveCardToDeadDeck(ImageView view, Card card, Color newWishColor) {
        Point2D deckPosition = iconLastCard.localToScene(Point2D.ZERO);

        translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(500));
        translateTransition.setNode(view);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.setFromX(0);
        translateTransition.setFromY(0);
        translateTransition.setToX(-(view.getX() - deckPosition.getX()));
        translateTransition.setToY(-(view.getY() - deckPosition.getY()));
        translateTransition.setOnFinished(new EventHandler<>() {
            @Override
            public void handle(ActionEvent event) {
                Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");
                Sound dealCardSound = new Sound("src/resources/sound/Card_Dealing.mp3");

                if (game.isRunning()) {
                    if (newWishColor != null) {
                        showCircleWishColor(newWishColor);
                    } else {
                        hideWishColor();
                    }
                    Card playedCard = game.getPlayer().playCard(card);

                    if (playedCard.getType().equals(Property.DRAW_FOUR) && game.getDeadDeck().getCards().get(game.getDeadDeck().getCards().size() - 1).getType().equals(Property.DRAW_FOUR) && game.getChallengeCounter() > 0) {
                        try {
                            handler.unlockAchievement(6);
                            handler.saveAndLoad();
                        } catch (Exception e) {
                        }
                    }

                    if (playedCard.getType().equals(Property.WILD)) {
                        try {
                            handler.unlockAchievement(7);
                            handler.saveAndLoad();
                        } catch (Exception e) {
                        }
                    }

                    setPlayerDeck(game.getPlayer().getDeck());
                    game.playCard(playedCard, newWishColor);
                }
            }
        });

        if (game.isRunning()) {
            translateTransition.play();
        }
    }

    public void moveAICardToDeadDeck(Bot bot, int currentPlayer, Card card, int cardPosition, Color newWishColor) {
        ObservableList<Node> nodes = mainPane.getChildren();
        ArrayList<Integer> possibleNodes = new ArrayList<Integer>();
        for (int i = 0; i < nodes.size(); i++) {
            Node current = nodes.get(i);
            if (current.getId().contains("ai" + bot.getID())) {
                possibleNodes.add(i);
            }
        }

        ImageView view = (ImageView) mainPane.getChildren().get(possibleNodes.get(cardPosition));
        view.setImage(new Image("images/" + card.getType() + "-" + card.getColor() + ".png"));

        Point2D deckPosition = iconLastCard.localToScene(Point2D.ZERO);

        translateTransition = new TranslateTransition();
        translateTransition.setDuration(Duration.millis(500));
        translateTransition.setNode(view);
        translateTransition.setCycleCount(1);
        translateTransition.setAutoReverse(false);
        translateTransition.setFromX(0);
        translateTransition.setFromY(0);
        translateTransition.setToX(-(view.getX() - deckPosition.getX()));
        translateTransition.setToY(-(view.getY() - deckPosition.getY()));
        translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (game.isRunning()) {
                    if (newWishColor != null) {
                        showCircleWishColor(newWishColor);
                    } else {
                        hideWishColor();
                    }
                    Card playedCard = bot.playCard(card);
                    setAIDeck(bot);
                    game.playCard(playedCard, newWishColor);
                }
            }
        });

        if (game.isRunning()) {
            translateTransition.play();
        }
    }

    public void moveCardFromDeckToPlayer(ArrayList<Card> cards) {
        if (game.isRunning()) {
            Point2D deckPosition = iconDeck.localToScene(Point2D.ZERO);

            ImageView view = createCard(cards.get(drawCounter), true);
            view.setId("drawAnimation");
            view.setX(deckPosition.getX());
            view.setY(deckPosition.getY());
            mainPane.getChildren().add(view);

            translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.millis(500));
            translateTransition.setNode(view);
            translateTransition.setCycleCount(1);
            translateTransition.setAutoReverse(false);
            translateTransition.setFromX(0);
            translateTransition.setFromY(0);
            translateTransition.setToX(-(view.getX() - getPositionOfRightCard(null)));
            translateTransition.setToY(-(view.getY() - PLAYER_STARTING_POINT.getY()));
            translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ObservableList<Node> nodes = mainPane.getChildren();
                    Iterator<Node> iterator = nodes.iterator();
                    while (iterator.hasNext()) {
                        if (iterator.next().getId().equals("drawAnimation")) {
                            iterator.remove();
                        }
                    }
                    if (game.isRunning()) {
                        game.getPlayer().drawCard(cards.get(drawCounter));
                        setPlayerDeck(game.getPlayer().getDeck());
                        drawCounter++;
                        playerHasDrawn = false;

                        if (drawCounter < cards.size()) {
                            moveCardFromDeckToPlayer(cards);
                        } else {
                            game.setShowingInfo(false);
                            hideInfo();
                            drawCounter = 0;
                            game.draw();
                        }
                    }
                }
            });

            if (game.isRunning()) {
                translateTransition.play();
            }
        }
    }

    private double getPositionOfRightCard(Bot bot) {
        if (bot == null) {
            double maxWidth = stage.getScene().getWidth() - (PLAYER_STARTING_POINT.getX() * 2) - CARD_WIDTH;
            int deckSize = game.getPlayer().getDeckSize();
            if ((deckSize * (CARD_WIDTH + CARD_SPACING_LARGE)) > maxWidth) {
                if ((deckSize * (CARD_WIDTH + CARD_SPACING_MEDIUM)) > maxWidth) {
                    if ((deckSize * (CARD_WIDTH + CARD_SPACING_SMALL)) > maxWidth) {
                        return PLAYER_STARTING_POINT.getX() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_ULTRA_SMALL)) - CARD_SPACING_ULTRA_SMALL;
                    } else {
                        return PLAYER_STARTING_POINT.getX() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_SMALL)) - CARD_SPACING_SMALL;
                    }
                } else {
                    return PLAYER_STARTING_POINT.getX() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_MEDIUM)) - CARD_SPACING_MEDIUM;
                }
            } else {
                return PLAYER_STARTING_POINT.getX() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_LARGE)) - CARD_SPACING_LARGE;
            }
        }
        //AI 1 (Above Player)
        else {
            double maxWidth = stage.getScene().getWidth() - (AI_1_STARTING_POINT.getX() * 2) - CARD_WIDTH;
            int deckSize = bot.getDeckSize();
            if ((deckSize * (CARD_WIDTH + CARD_SPACING_LARGE)) > maxWidth) {
                if ((deckSize * (CARD_WIDTH + CARD_SPACING_MEDIUM)) > maxWidth) {
                    if ((deckSize * (CARD_WIDTH + CARD_SPACING_SMALL)) > maxWidth) {
                        return AI_1_STARTING_POINT.getX() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_ULTRA_SMALL)) - CARD_SPACING_ULTRA_SMALL;
                    } else {
                        return AI_1_STARTING_POINT.getX() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_SMALL)) - CARD_SPACING_SMALL;
                    }
                } else {
                    return AI_1_STARTING_POINT.getX() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_MEDIUM)) - CARD_SPACING_MEDIUM;
                }
            } else {
                return AI_1_STARTING_POINT.getX() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_LARGE)) - CARD_SPACING_LARGE;
            }
        }
    }

    private double getPositionOfBottomCard(Bot bot) {
        double maxHeight = stage.getScene().getHeight() - ((AI_2_STARTING_POINT.getY() + 40.0) * 2) - CARD_WIDTH;
        int deckSize = bot.getDeckSize();

        if ((deckSize * (CARD_WIDTH + CARD_SPACING_LARGE)) > maxHeight) {
            if ((deckSize * (CARD_WIDTH + CARD_SPACING_MEDIUM)) > maxHeight) {
                if ((deckSize * (CARD_WIDTH + CARD_SPACING_SMALL)) > maxHeight) {
                    return AI_2_STARTING_POINT.getY() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_ULTRA_SMALL)) - CARD_SPACING_ULTRA_SMALL;
                } else {
                    return AI_2_STARTING_POINT.getY() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_SMALL)) - CARD_SPACING_SMALL;
                }
            } else {
                return AI_2_STARTING_POINT.getY() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_MEDIUM)) - CARD_SPACING_MEDIUM;
            }
        } else {
            return AI_2_STARTING_POINT.getY() + ((deckSize + 1) * (CARD_WIDTH + CARD_SPACING_LARGE)) - CARD_SPACING_LARGE;
        }
    }

    @SuppressWarnings("unused")
    public void moveCardFromDeckToAI(Bot bot, ArrayList<Card> cards) {
        if (game.isRunning()) {
            Card card = game.getDeck().drawCard(game.getDeadDeck());

            Point2D deckPosition = iconDeck.localToScene(Point2D.ZERO);

            ImageView view = createBackCard();
            view.setId("drawAnimation");
            view.setX(deckPosition.getX());
            view.setY(deckPosition.getY());
            mainPane.getChildren().add(view);

            translateTransition = new TranslateTransition();
            translateTransition.setDuration(Duration.millis(500));
            translateTransition.setNode(view);
            translateTransition.setCycleCount(1);
            translateTransition.setAutoReverse(false);
            translateTransition.setFromX(0);
            translateTransition.setFromY(0);

            switch (bot.getID()) {
                case 1:
                    translateTransition.setToX(-(view.getX() - getPositionOfRightCard(bot)));
                    translateTransition.setToY(-(view.getY() - AI_1_STARTING_POINT.getY()));
                    break;
                case 2:
                    translateTransition.setToX(-(view.getX() - AI_2_STARTING_POINT.getX()));
                    translateTransition.setToY(-(view.getY() - getPositionOfBottomCard(bot)));
                    break;
                case 3:
                    translateTransition.setToX(-(view.getX() - AI_3_STARTING_POINT.getX()));
                    translateTransition.setToY(-(view.getY() - getPositionOfBottomCard(bot)));
                    break;
                default:
                    break;
            }

            translateTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    ObservableList<Node> nodes = mainPane.getChildren();
                    Iterator<Node> iterator = nodes.iterator();
                    while (iterator.hasNext()) {
                        if (iterator.next().getId().equals("drawAnimation")) {
                            iterator.remove();
                        }
                    }

                    if (game.isRunning()) {
                        bot.drawCard(cards.get(drawCounter));
                        setAIDeck(bot);
                        drawCounter++;

                        if (drawCounter < cards.size()) {
                            moveCardFromDeckToAI(bot, cards);
                        } else {
                            game.setShowingInfo(false);
                            hideInfo();
                            drawCounter = 0;
                            game.draw();
                        }
                    }
                }
            });

            if (game.isRunning()) {
                translateTransition.play();
            }
        }
    }

    public void clearPlayerDeck() {
        ObservableList<Node> nodes = mainPane.getChildren();
        Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals("player")) {
                iterator.remove();
            }
        }
    }

    public void setPlayerDeck(ArrayList<Card> deck) {
        clearPlayerDeck();

        int counter = 1;

        for (int i = 0; i < deck.size(); i++) {
            ImageView current = createCard(deck.get(i), true);

            current.setId("player");
            mainPane.getChildren().add(current);

            if (i == 0) {
                current.setX(AI_1_STARTING_POINT.getX() + CARD_WIDTH);
            } else {
                double maxWidth = stage.getScene().getWidth() - (PLAYER_STARTING_POINT.getX() * 2) - CARD_WIDTH;
                if ((deck.size() * (CARD_WIDTH + CARD_SPACING_LARGE)) > maxWidth) {
                    if ((deck.size() * (CARD_WIDTH + CARD_SPACING_MEDIUM)) > maxWidth) {
                        if ((deck.size() * (CARD_WIDTH + CARD_SPACING_SMALL)) > maxWidth) {
                            current.setX(PLAYER_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_ULTRA_SMALL)) - CARD_SPACING_ULTRA_SMALL);
                        } else {
                            current.setX(PLAYER_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_SMALL)) - CARD_SPACING_SMALL);
                        }
                    } else {
                        current.setX(PLAYER_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_MEDIUM)) - CARD_SPACING_MEDIUM);
                    }
                } else {
                    current.setX(PLAYER_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_LARGE)) - CARD_SPACING_LARGE);
                }
            }

            current.setY(PLAYER_STARTING_POINT.getY());

            counter++;
        }
    }

    public void setValidPlayerDeck(ArrayList<Card> deck, ArrayList<Card> validDeck) {
        clearPlayerDeck();

        int counter = 1;

        for (int i = 0; i < deck.size(); i++) {
            Card currentCard = deck.get(i);
            ImageView current;

            if (validDeck.contains(currentCard)) {
                current = createCard(currentCard, true);
                current.setY(PLAYER_STARTING_POINT.getY() - CARD_HEIGHT / 4);
            } else {
                current = createCard(currentCard, false);
                current.setY(PLAYER_STARTING_POINT.getY());
            }

            current.setId("player");

            mainPane.getChildren().add(current);

            if (i == 0) {
                current.setX(AI_1_STARTING_POINT.getX() + CARD_WIDTH);
            } else {
                double maxWidth = stage.getScene().getWidth() - (PLAYER_STARTING_POINT.getX() * 2) - CARD_WIDTH;
                if ((deck.size() * (CARD_WIDTH + CARD_SPACING_LARGE)) > maxWidth) {
                    if ((deck.size() * (CARD_WIDTH + CARD_SPACING_MEDIUM)) > maxWidth) {
                        if ((deck.size() * (CARD_WIDTH + CARD_SPACING_SMALL)) > maxWidth) {
                            current.setX(PLAYER_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_ULTRA_SMALL)) - CARD_SPACING_ULTRA_SMALL);
                        } else {
                            current.setX(PLAYER_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_SMALL)) - CARD_SPACING_SMALL);
                        }
                    } else {
                        current.setX(PLAYER_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_MEDIUM)) - CARD_SPACING_MEDIUM);
                    }
                } else {
                    current.setX(PLAYER_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_LARGE)) - CARD_SPACING_LARGE);
                }
            }

            counter++;
        }
    }

    public void clearAIDeck(Bot bot) {
        ObservableList<Node> nodes = mainPane.getChildren();
        Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().contains("ai" + bot.getID())) {
                iterator.remove();
            }
        }
    }

    public void setAIDeck(Bot bot) {
        clearAIDeck(bot);

        ArrayList<Card> deck = bot.getDeck();

        int counter = 1;

        for (int i = 0; i < deck.size(); i++) {
            ImageView current = createBackCard();

            current.setId("ai" + bot.getID());

            mainPane.getChildren().add(current);

            double maxWidth;
            double maxHeight;
            int deckSize;

            switch (bot.getID()) {
                case 1:
                    maxWidth = stage.getScene().getWidth() - ((AI_1_STARTING_POINT.getX() + 0.0) * 2) - CARD_WIDTH;
                    deckSize = bot.getDeckSize();

                    if (i == 0) {
                        current.setX(AI_1_STARTING_POINT.getX() + CARD_WIDTH);
                    } else {
                        if ((deckSize * (CARD_WIDTH + CARD_SPACING_LARGE)) > maxWidth) {
                            if ((deckSize * (CARD_WIDTH + CARD_SPACING_MEDIUM)) > maxWidth) {
                                if ((deckSize * (CARD_WIDTH + CARD_SPACING_SMALL)) > maxWidth) {
                                    current.setX(AI_1_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_ULTRA_SMALL)) - CARD_SPACING_ULTRA_SMALL);
                                } else {
                                    current.setX(AI_1_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_SMALL)) - CARD_SPACING_SMALL);
                                }
                            } else {
                                current.setX(AI_1_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_MEDIUM)) - CARD_SPACING_MEDIUM);
                            }
                        } else {
                            current.setX(AI_1_STARTING_POINT.getX() + (counter * (CARD_WIDTH + CARD_SPACING_LARGE)) - CARD_SPACING_LARGE);
                        }
                    }

                    current.setY(AI_1_STARTING_POINT.getY());
                    break;

                case 2:
                    maxHeight = stage.getScene().getHeight() - ((AI_2_STARTING_POINT.getY() + 40.0) * 2) - CARD_WIDTH;
                    deckSize = bot.getDeckSize();

                    current.setRotate(-90.0);

                    if (i == 0) {
                        current.setY(AI_2_STARTING_POINT.getY() + CARD_WIDTH);
                    } else {
                        if ((deckSize * (CARD_WIDTH + CARD_SPACING_LARGE)) > maxHeight) {
                            if ((deckSize * (CARD_WIDTH + CARD_SPACING_MEDIUM)) > maxHeight) {
                                if ((deckSize * (CARD_WIDTH + CARD_SPACING_SMALL)) > maxHeight) {
                                    current.setY(AI_2_STARTING_POINT.getY() + (counter * (CARD_WIDTH + CARD_SPACING_ULTRA_SMALL)) - CARD_SPACING_ULTRA_SMALL);
                                } else {
                                    current.setY(AI_2_STARTING_POINT.getY() + (counter * (CARD_WIDTH + CARD_SPACING_SMALL)) - CARD_SPACING_SMALL);
                                }
                            } else {
                                current.setY(AI_2_STARTING_POINT.getY() + (counter * (CARD_WIDTH + CARD_SPACING_MEDIUM)) - CARD_SPACING_MEDIUM);
                            }
                        } else {
                            current.setY(AI_2_STARTING_POINT.getY() + (counter * (CARD_WIDTH + CARD_SPACING_LARGE)) - CARD_SPACING_LARGE);
                        }
                    }

                    current.setX(AI_2_STARTING_POINT.getX());
                    break;

                case 3:
                    maxHeight = stage.getScene().getHeight() - ((AI_3_STARTING_POINT.getY() + 40.0) * 2) - CARD_WIDTH;
                    deckSize = bot.getDeckSize();

                    current.setRotate(90.0);

                    if (i == 0) {
                        current.setY(AI_3_STARTING_POINT.getY() + CARD_WIDTH);
                    } else {
                        if ((deckSize * (CARD_WIDTH + CARD_SPACING_LARGE)) > maxHeight) {
                            if ((deckSize * (CARD_WIDTH + CARD_SPACING_MEDIUM)) > maxHeight) {
                                if ((deckSize * (CARD_WIDTH + CARD_SPACING_SMALL)) > maxHeight) {
                                    current.setY(AI_3_STARTING_POINT.getY() + (counter * (CARD_WIDTH + CARD_SPACING_ULTRA_SMALL)) - CARD_SPACING_ULTRA_SMALL);
                                } else {
                                    current.setY(AI_3_STARTING_POINT.getY() + (counter * (CARD_WIDTH + CARD_SPACING_SMALL)) - CARD_SPACING_SMALL);
                                }
                            } else {
                                current.setY(AI_3_STARTING_POINT.getY() + (counter * (CARD_WIDTH + CARD_SPACING_MEDIUM)) - CARD_SPACING_MEDIUM);
                            }
                        } else {
                            current.setY(AI_3_STARTING_POINT.getY() + (counter * (CARD_WIDTH + CARD_SPACING_LARGE)) - CARD_SPACING_LARGE);
                        }
                    }

                    current.setX(AI_3_STARTING_POINT.getX());
                    break;
                default:
                    break;
            }

            counter++;
        }
    }

    public void clearAllDecks(ArrayList<Bot> bots) {
        clearPlayerDeck();

        for (Bot currentBot : bots) {
            clearAIDeck(currentBot);
        }
    }

    public void openSettings() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Settings.fxml"));

            Parent root = (Parent) fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            newStage.setTitle("Setting");
            newStage.initOwner(stage);

            newStage.getIcons().add(icon);
            SettingsController newController = fxmlLoader.getController();
            newController.init(newStage, this);

            newStage.initModality(Modality.APPLICATION_MODAL);
            newStage.setResizable(true);
            newStage.showAndWait();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void clearAll() {
        hideNeutralUI();
        hideWishColor();
        hideInfo();
        labelCurrentPlayer.setVisible(false);
        hideLabelChallengeCounter();
        hideImageViewDirection();
        labelAI1Name.setVisible(false);
        labelAI2Name.setVisible(false);
        labelAI3Name.setVisible(false);
        buttonStart.setVisible(false);
        iconDeck.setImage(null);
        iconLastCard.setImage(null);
    }

    private void createAchievements() {
        AchievementHandler handler = new AchievementHandler(stage);
        handler.setPath(PathUtils.getOSindependentPath() + "/OOP/UNO/achievements.save");
        handler.addAchievement(new Achievement("Anf�nger", "Gewinne dein erstes Spiel", null, null, Status.LOCKED));
        handler.addAchievement(new Achievement("Fortgeschrittener", "Gewinne insgesamt 10 Spiele", null, null, Status.LOCKED, 0, 10, 0));
        handler.addAchievement(new Achievement("Experte", "Gewinne insgesamt 50 Spiele", null, null, Status.LOCKED, 0, 50, 0));

        handler.addAchievement(new Achievement("Gl�cksstr�hne", "Gewinne hintereinander 3 Spiele", null, null, Status.LOCKED, 0, 3, 0));
        handler.addAchievement(new Achievement("L�uft bei dir!", "Gewinne hintereinander 5 Spiele", null, null, Status.LOCKED, 0, 5, 0));

        handler.addAchievement(new Achievement("Arme Sau", "Du musst mehr als 10 Karten ziehen", null, null, Status.LOCKED));
        handler.addAchievement(new Achievement("Gegenangriff", "Kontere eine +4", null, null, Status.LOCKED));
        handler.addAchievement(new Achievement("Wunschkonzert", "W�nsch dir eine Farbe", null, null, Status.LOCKED));
        handler.addAchievement(new Achievement("Cheatest du?", "Besitze zwei +4 Karten gleichzeitig", null, null, Status.LOCKED));

        handler.addAchievement(new Achievement("Unm�glich", "Klicke 5 mal auf den Ablagestapel", null, null, Status.HIDDEN));

        try {
            handler.saveAchievements();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void buttonAchievements() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        Stage newStage = new Stage();

        AnchorPane root = new AnchorPane();

        try {
            handler.loadAchievements();
        } catch (Exception e) {
        }

        AnchorPane list = handler.getAchievementList();
        AnchorPane summary = handler.getSummary();

        root.getChildren().add(summary);
        root.getChildren().add(list);

        AnchorPane.setTopAnchor(summary, 50.0);
        AnchorPane.setLeftAnchor(summary, 25.0);
        AnchorPane.setRightAnchor(summary, 50.0);

        AnchorPane.setTopAnchor(list, 180.0);
        AnchorPane.setLeftAnchor(list, 25.0);
        AnchorPane.setRightAnchor(list, 25.0);
        AnchorPane.setBottomAnchor(list, 25.0);

        root.setStyle("-fx-background-color: #3F3F3F;");

        Scene scene = new Scene(root, 600, 600);
        newStage.setScene(scene);
        newStage.setMinHeight(500);
        newStage.setMinWidth(600);

        newStage.setTitle("Achievements");
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.getIcons().add(new Image("/images/icon.png"));
        newStage.setResizable(true);
        newStage.show();
    }

    public void about() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("About " + bundle.getString("app.name"));
        alert.setHeaderText(bundle.getString("app.name"));
        //alert.setContentText("Version:     " + bundle.getString("version.name") + "\r\nDate:      " + bundle.getString("version.date") + "\r\nAuthor:        Robert Goldmann\r\nCard images from:\nhttps://upload.wikimedia.org/wikipedia/commons/thumb/9/95/UNO_cards_deck.svg/800px-UNO_cards_deck.svg.png");
        Stage dialogStage = (Stage) alert.getDialogPane().getScene().getWindow();
        dialogStage.getIcons().add(icon);
        alert.showAndWait();
        Sound buttonClickingSound1 = new Sound("src/resources/sound/sound_button_click.mp3");

    }
}