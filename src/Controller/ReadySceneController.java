package Controller;

import Model.Timer;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.Time;
import java.util.ResourceBundle;

public class ReadySceneController implements Initializable {
    public AnchorPane pane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Timer timer = new Timer();
        timer.countTime();
        pane.getChildren().add(timer);
    }
}