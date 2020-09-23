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