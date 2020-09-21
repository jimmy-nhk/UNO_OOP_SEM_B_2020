package Controller;

import Model.Color;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ColorChooserController {

    @FXML
    private Rectangle rectYellow;
    @FXML
    private Rectangle rectRed;
    @FXML
    private Rectangle rectBlue;
    @FXML
    private Rectangle rectGreen;

    public void init(Stage stage, MainController mainController) {
        rectYellow.setOnMouseClicked(event -> {
            mainController.chosenWishColor = Color.YELLOW;
            stage.close();
        });

        rectRed.setOnMouseClicked(event -> {
            mainController.chosenWishColor = Color.RED;
            stage.close();
        });

        rectBlue.setOnMouseClicked(event -> {
            mainController.chosenWishColor = Color.BLUE;
            stage.close();
        });

        rectGreen.setOnMouseClicked(event -> {
            mainController.chosenWishColor = Color.GREEN;
            stage.close();
        });

        stage.setOnCloseRequest(Event::consume);
    }
}