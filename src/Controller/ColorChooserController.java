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
  Acknowledgement: If you use any resources, acknowledge here. Failure to do so will be considered as plagiarism.
*/

package Controller;

import Model.Color;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ColorChooserController {

    @FXML
    public Label chooseColor;
    @FXML
    private Rectangle rectYellow;
    @FXML
    private Rectangle rectRed;
    @FXML
    private Rectangle rectBlue;
    @FXML
    private Rectangle rectGreen;

    public void init(Stage stage, MainController mainController) {
        LanguageController.switchLanguage(SettingsController.locale);
        setBindingTextChoseColor();
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

        // Define
        stage.setOnCloseRequest(Event::consume);
    }

    // Set the chosen color text
    public void setBindingTextChoseColor() {
        LanguageController.setUpLabelText(chooseColor, "chosenColor.pleaseChooseColor");
    }
}