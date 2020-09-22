package Controller;

import Model.*;
import Model.Color;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.animation.TranslateTransition;
import javafx.collections.ObservableList;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;


public class MainController {

    private final ResourceBundle bundle = ResourceBundle.getBundle("resources/", Locale.ENGLISH);
    private final double CARD_HEIGHT = 90.0;
    private final double CARD_WIDTH = 57.0;
    private final double CARD_SPACING_LARGE = 14.0;
    private final double CARD_SPACING_MEDIUM = 0.0;
    private final double CARD_SPACING_SMALL = -25.0;
    private final double CARD_SPACING_ULTRA_SMALL = -35.0;
    private final Point2D AI_1_STARTING_POINT = new Point2D(100.0, 75.0);

    // These color take from the internet the code of the color
    private final javafx.scene.paint.Color COLOR_YELLOW = javafx.scene.paint.Color.web("#FFAA00");
    private final javafx.scene.paint.Color COLOR_RED = javafx.scene.paint.Color.web("#FF5555");
    private final javafx.scene.paint.Color COLOR_BLUE = javafx.scene.paint.Color.web("#5555FD");
    private final javafx.scene.paint.Color COLOR_GREEN = javafx.scene.paint.Color.web("#55AA55");

    public GameBoard gameBoard;
    public int drawCounter;
    public Settings settings;
    //    public AchievementHandler handler;
    public Stage stage;
    public Image icon = new Image("images/icon.png");
    public static final Sound backgroundMusic = new Sound("src/resources/sound/background.mp3");
    public Color chosenWishColor;
    public TextArea textLeaderBoard;
    @FXML private Label labelLeaderBoard;
    @FXML private Pane leaderBoardPane1;
    @FXML private Button backButton1;
    private String playerName;
    public boolean playerMustDraw;
    public TranslateTransition translateTransition;
    @FXML
    private TextField textGetName;
    @FXML
    private Label labelSetName;
    @FXML
    private Button btSetName;
    @FXML
    private Button btnLeaderBoard;

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
    @FXML private Button backButton;
    @FXML private Button buttonSettings;
    private boolean playerHasDrawn;
    private Point2D PLAYER_STARTING_POINT;
    private Point2D AI_2_STARTING_POINT;
    private Point2D AI_3_STARTING_POINT;
    ArrayList<String> namesList = new ArrayList<String>();
    ArrayList<Integer> winList = new ArrayList<Integer>();

    public void init() {

        imageViewWishColor.setImage(new Image("/images/circle-all.png"));

        PLAYER_STARTING_POINT = new Point2D(100.0, stage.getScene().getHeight() - 50.0 - CARD_HEIGHT);
        AI_2_STARTING_POINT = new Point2D(stage.getScene().getWidth() - CARD_HEIGHT - 30, 70.0);
        AI_3_STARTING_POINT = new Point2D(60.0, 70.0);

        clearAll();
        showSetNameScene();

        labelLogo.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 30));
        buttonNewGame.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        buttonSettings.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));


        settings = new Settings();
        try {
            settings.load();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Start the game
    public void startGame() {
        Sound startGameSound = new Sound("src/resources/sound/sound_launch.mp3");

        if (gameBoard != null) {
            gameBoard.stop();
        }

        clearAll();

        drawCounter = 0;
        playerHasDrawn = false;
        playerMustDraw = false;

        labelCurrentPlayer.setVisible(true);
        labelCurrentPlayer.setText("");

        iconDeck.setImage(createEmptyBackCard());

        iconDeck.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (gameBoard.isRunning() && gameBoard.getCurrentPlayer() == 1 && !gameBoard.isShowingInfo() && !playerHasDrawn && !playerMustDraw) {
                playerHasDrawn = true;
                playerMustDraw = false;
                Card drawCard = gameBoard.getDeck().drawCard(gameBoard.getPlayedCards());
                ArrayList<Card> allCards = new ArrayList<>();
                allCards.add(drawCard);
                moveCardFromDeckToPlayer(allCards);
            }
        });

        gameBoard = new GameBoard(this, settings.getNumberOfBots());
        setLabelNames(gameBoard.getPlayer(), gameBoard.getBots());
        gameBoard.newGame(settings.getNumberOfStartingCards());

        setFontForGame(); // Set font for the game


        buttonStart.setOnAction(event -> {
            buttonStart.setVisible(false);
            gameBoard.start();
        });
        buttonStart.setVisible(true);
    }

    public void setFontForGame() {
        labelAI1Name.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        labelAI2Name.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        labelAI3Name.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        labelDirection.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 10));
        labelWishColor.setFont(Font.font("verdana", FontWeight.LIGHT, FontPosture.ITALIC, 10));
        labelCurrentPlayer.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
    }

    // Show main menu
    public void showMainMenu() {
        Sound startGameSound = new Sound("src/resources/sound/sound_launch.mp3");
        if (gameBoard != null) {
            gameBoard.stop();
        }

        clearAll();
        clearPlayerDeck();
        clearAllDecks(gameBoard.getBots());

        showMenu();
    }

    public void showSetNameScene() {
        textGetName.setVisible(true);
        labelSetName.setVisible(true);
        btSetName.setVisible(true);
        btnLeaderBoard.setVisible(false);
        leaderBoardPane1.setVisible(false);
        labelLeaderBoard.setVisible(false);
        btSetName.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 12));
        textGetName.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        labelSetName.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        labelSetName.setTextFill(COLOR_BLUE);


        btSetName.setOnAction(actionEvent -> {
            showMenu();
            playerName = textGetName.getText();
            hideSetNameScene();
            showMenu();
            labelLogo.setText("WELCOME " + playerName + " TO UNO !!!"); // Set the text for the Main Menu
            FileOutputStream fos = null;
            try {

                FileInputStream fis = new FileInputStream("listName");
                ObjectInputStream ois = new ObjectInputStream(fis);
                namesList = (ArrayList) ois.readObject();
                ois.close();
                fis.close();
                FileInputStream fis1 = new FileInputStream("listWin");
                ObjectInputStream ois1 = new ObjectInputStream(fis1);
                winList = (ArrayList) ois1.readObject();
                ois.close();
                fis.close();
                        namesList.add(playerName);
                        winList.add(0);
                        fos = new FileOutputStream("listName");
                        ObjectOutputStream oos = new ObjectOutputStream(fos);
                        oos.writeObject(namesList);
                        oos.close();
                        fos.close();
                        FileOutputStream fos1 = new FileOutputStream("listWin");
                        ObjectOutputStream oos1 = new ObjectOutputStream(fos1);
                        oos1.writeObject(winList);
                        oos1.close();
                        fos1.close();
//                for (int i =0; i < namesList.size();i++) {
//                    if (playerName.equals(namesList.get(i)))
//                    {break;}
//                    if (i==namesList.size()-1){


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        });
    }

    public void hideSetNameScene() {
        textGetName.setVisible(false);
        labelSetName.setVisible(false);
        btSetName.setVisible(false);
    }


    public void showMenu() {

        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");
        imageViewLogo.setVisible(true);
        labelLogo.setVisible(true);
        btnLeaderBoard.setVisible(true);
        buttonNewGame.setVisible(true);
        buttonSettings.setVisible(true);
        menuBar.setVisible(true);
        labelLogo.setTextFill(COLOR_RED);
    }

    public void hideMenu() {
        Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");
        btnLeaderBoard.setVisible(false);
        imageViewLogo.setVisible(false);
        labelLogo.setVisible(false);
        buttonNewGame.setVisible(false);
        buttonSettings.setVisible(false);
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
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
//            if (gameBoard.getDrawnCardsCount() > 10) {
//                try {

//                    handler.unlockAchievement(5);
//                    handler.saveAndLoad();

//                } catch (Exception e) {
//                    System.out.println(e.toString());
//                }
//
//            }
            moveCardFromDeckToPlayer(gameBoard.getDeck().drawCards(gameBoard.getDrawnCardsCount(), gameBoard.getPlayedCards()));
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

    public void setPreviousCard(Card card) {
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
        ImageView imageView = new ImageView(new Image("images/" + card.getProperty() + "-" + card.getColor() + ".png"));
        imageView.setFitHeight(CARD_HEIGHT);
        imageView.setFitWidth(CARD_WIDTH);
        imageView.setSmooth(true);

        if (!valid) {
            SnapshotParameters parameters = new SnapshotParameters();
            parameters.setFill(javafx.scene.paint.Color.TRANSPARENT);
            WritableImage snapshot = imageView.snapshot(parameters, null);

            if (card.getProperty().equals(Property.DRAW_FOUR) && card.getProperty().equals(Property.WILD)) {
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
        MainController main = this;

        imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<>() {
            Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");
            Sound dealCardSound = new Sound("src/resources/sound/Card_Dealing.mp3");

            @Override
            public void handle(MouseEvent event) {
                if (gameBoard.isRunning() && gameBoard.getCurrentPlayer() == 1) {
                    if (valid) {
                        if (card.getProperty().equals(Property.WILD) || card.getProperty().equals(Property.DRAW_FOUR)) {
                            try {
                                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/ColorChooser.fxml"));

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
        translateTransition.setOnFinished(event -> {
//            Sound buttonClickingSound = new Sound("src/resources/sound/sound_button_click.mp3");
            Sound dealCardSound = new Sound("src/resources/sound/Card_Dealing.mp3");

            if (gameBoard.isRunning()) {
                if (newWishColor != null) {
                    showCircleWishColor(newWishColor);
                } else {
                    hideWishColor();
                }
                Card playedCard = gameBoard.getPlayer().playCard(card);

                setPlayerDeck(gameBoard.getPlayer().getDeck());
                gameBoard.playCard(playedCard, newWishColor);
            }
        });

        if (gameBoard.isRunning()) {
            translateTransition.play();
        }
    }

    public void moveBotCardToPlayedCards(Bot bot, int currentPlayer, Card card, int cardPosition, Color newWishColor) {
        ObservableList<Node> nodes = mainPane.getChildren();
        ArrayList<Integer> possibleNodes = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node current = nodes.get(i);
            if (current.getId().contains("ai" + bot.getID())) {
                possibleNodes.add(i);
            }
        }

        ImageView view = (ImageView) mainPane.getChildren().get(possibleNodes.get(cardPosition));
        view.setImage(new Image("images/" + card.getProperty() + "-" + card.getColor() + ".png"));

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
        translateTransition.setOnFinished(event -> {
            if (gameBoard.isRunning()) {
                if (newWishColor != null) {
                    showCircleWishColor(newWishColor);
                } else {
                    hideWishColor();
                }
                Card playedCard = bot.playCard(card);
                setBotDeck(bot);
                gameBoard.playCard(playedCard, newWishColor);
            }
        });

        if (gameBoard.isRunning()) {
            translateTransition.play();
        }
    }

    public void moveCardFromDeckToPlayer(ArrayList<Card> cards) {
        if (gameBoard.isRunning()) {
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
            translateTransition.setOnFinished(event -> {
                ObservableList<Node> nodes = mainPane.getChildren();
                Iterator<Node> iterator = nodes.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getId().equals("drawAnimation"))
                        iterator.remove();
                }
                if (gameBoard.isRunning()) {
                    gameBoard.getPlayer().drawCard(cards.get(drawCounter));
                    setPlayerDeck(gameBoard.getPlayer().getDeck());
                    drawCounter++;
                    playerHasDrawn = false;

                    if (drawCounter < cards.size()) {
                        moveCardFromDeckToPlayer(cards);
                    } else {
                        gameBoard.setShowingInfo(false);
                        hideInfo();
                        drawCounter = 0;
                        gameBoard.draw();
                    }
                }
            });

            if (gameBoard.isRunning()) {
                translateTransition.play();
            }
        }
    }

    // Get the position of Right Player
    private double getPositionOfRightCard(Bot bot) {
        if (bot == null) {
            double maxWidth = stage.getScene().getWidth() - (PLAYER_STARTING_POINT.getX() * 2) - CARD_WIDTH;
            int deckSize = gameBoard.getPlayer().getDeckSize();
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

        //Bot 1 (Above Player)
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


    public void moveCardFromDeckToBot(Bot bot, ArrayList<Card> cards) {
        if (gameBoard.isRunning()) {
            Card card = gameBoard.getDeck().drawCard(gameBoard.getPlayedCards());

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

            translateTransition.setOnFinished(event -> {
                ObservableList<Node> nodes = mainPane.getChildren();
                Iterator<Node> iterator = nodes.iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getId().equals("drawAnimation"))
                        iterator.remove();
                }

                if (gameBoard.isRunning()) {
                    bot.drawCard(cards.get(drawCounter));
                    setBotDeck(bot);
                    drawCounter++;

                    if (drawCounter < cards.size()) {
                        moveCardFromDeckToBot(bot, cards);
                    } else {
                        gameBoard.setShowingInfo(false);
                        hideInfo();
                        drawCounter = 0;
                        gameBoard.draw();
                    }
                }
            });

            if (gameBoard.isRunning()) {
                translateTransition.play();
            }
        }
    }

    // Clear player deck
    public void clearPlayerDeck() {
        ObservableList<Node> nodes = mainPane.getChildren();
        Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().equals("player")) {
                iterator.remove();
            }
        }
    }

    // Set player deck
    public void setPlayerDeck(ArrayList<Card> deck) {
        clearPlayerDeck();

        int counter = 1;

        for (int i = 0; i < deck.size(); i++) {
            // Set the deck of the player
            ImageView current = createCard(deck.get(i), true);

            // Gets the card for its id
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

    public void clearBotDeck(Bot bot) {
        ObservableList<Node> nodes = mainPane.getChildren();
        Iterator<Node> iterator = nodes.iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getId().contains("ai" + bot.getID())) {
                iterator.remove();
            }
        }
    }

    public void setBotDeck(Bot bot) {
        clearBotDeck(bot);

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
            clearBotDeck(currentBot);
        }
    }

    public void openSettings() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Settings.fxml"));

            Parent root = fxmlLoader.load();
            Stage newStage = new Stage();
            newStage.setScene(new Scene(root, 600, 400));
            newStage.setTitle("UNO");
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
        hideMenu();
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

    public void changeToLeaderBoard(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        hideMenu();
        leaderBoardPane1.setVisible(true);
        backButton1.setVisible(true);
        labelLeaderBoard.setVisible(true);
        menuBar.setVisible(false);
        FileInputStream fis = new FileInputStream("listName");
        ObjectInputStream ois = new ObjectInputStream(fis);
        namesList = (ArrayList) ois.readObject();
        ois.close();
        fis.close();
        FileInputStream fis1 = new FileInputStream("listWin");
        ObjectInputStream ois1 = new ObjectInputStream(fis1);
        winList = (ArrayList) ois1.readObject();
        ois.close();
        fis.close();
        String string = "";
        for (int i = 0; i < namesList.size();i++) {
            string += namesList.get(i) + "\t\t\t\twin:   " + winList.get(i).toString()+"\n";
            textLeaderBoard.setText(string);
        }

    }

    public void backFromLeaderBoard(ActionEvent actionEvent) {
        leaderBoardPane1.setVisible(false);
        backButton1.setVisible(false);
        labelLeaderBoard.setVisible(false);
        showMenu();
    }
}
