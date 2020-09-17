//package Model;
//
//import Controller.GameBoard;
//import javafx.animation.RotateTransition;
//import javafx.application.Application;
//import javafx.scene.Group;
//import javafx.scene.Scene;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.Pane;
//import javafx.scene.transform.Rotate;
//import javafx.stage.Stage;
//import javafx.util.Duration;
//
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//
//public class TurnDirection extends Pane {
//    Group root;
//    Group root2;
//    private final GameBoard gameBoard = new GameBoard();
//    int direction;
//
//    {
//        direction = gameBoard.getDirectionOfPlay();
//    }
//
//    public TurnDirection() {
//            try {
//                Image reverse_clock = new Image(new FileInputStream("resources/image/anti_clock.png"));
//                Image clock = new Image(new FileInputStream("resources/image/clock.png"));
//                ImageView reverse_clock_wise = new ImageView(reverse_clock);
//                ImageView clock_wise = new ImageView(clock);
//                reverse_clock_wise.setX(50);
//                reverse_clock_wise.setY(25);
//                clock_wise.setX(100);
//                clock_wise.setY(25);
//                reverse_clock_wise.setFitHeight(455);
//                reverse_clock_wise.setFitWidth(500);
//                clock_wise.setFitHeight(455);
//                clock_wise.setFitWidth(500);
//
//                reverse_clock_wise.setPreserveRatio(true);
//                clock_wise.setPreserveRatio(true);
//                root = new Group(clock_wise);
//                root2 = new Group(reverse_clock_wise);
//            } catch (FileNotFoundException e) {
//                System.out.println("Cannot find File name.");
//                System.exit(0);
//            }
//
//
//
//                RotateTransition rotateTransition = new RotateTransition(Duration.seconds(3), root);
//                RotateTransition rotateTransition2 = new RotateTransition(Duration.seconds(3), root2);
//                rotateTransition2.setAxis(Rotate.Z_AXIS);
//                rotateTransition.setAxis(Rotate.Z_AXIS);
////            rotateTransition.setByAngle(180);
//                rotateTransition.setFromAngle(0);
//                rotateTransition.setToAngle(360);
//                rotateTransition.setAutoReverse(false);
//                rotateTransition.setCycleCount(10);
//                rotateTransition.play();
//
//                rotateTransition2.setFromAngle(0);
//                rotateTransition2.setToAngle(360);
//                rotateTransition2.setAutoReverse(false);
//                rotateTransition2.setCycleCount(10);
//                rotateTransition2.play();
//                if (direction == 1) {
//                    gameBoard.
//                } else if (direction == -1) {
//                    Scene scene = new Scene(root2, 600, 500);
//                }
//            }
//        }
