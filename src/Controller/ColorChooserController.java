/*
  RMIT University Vietnam
  Course: INTE2512 Object-Oriented Programming
  Semester: 2020B
  Assessment: Final Project
  Created date: dd/mm/yyyy (e.g. 31/03/2019)
  By: Your name (Your studen id)
  Last modified: dd/mm/yyyy (e.g. 05/04/2019)
  By: Your name (Your studen id)
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