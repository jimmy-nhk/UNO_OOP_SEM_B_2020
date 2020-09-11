package Model;


import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class Timer extends Pane {
    private int minute;
    private int second;
    private Timeline animation;
    private static  Integer TIMESTART = 60;
    private Timeline timeline;
    private Integer timeSeconds = TIMESTART;

    // Clock pane's width and height
    private double w = 100, h = 100;

    /** Construct a default clock with the current time */
    public Timer() {
        animation = new Timeline(
                new KeyFrame(Duration.millis(1000), e -> moveClock()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    /** Construct a click with specified hour, minute, and second */
    public Timer(int minute, int second) {
        this.minute = minute;
        this.second = second;
        paintClock();
        animation = new Timeline(
                new KeyFrame(Duration.millis(1000), e -> moveClock()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    /** Return minute */
    public int getMinute() {
        return minute;
    }

    /** Set a new minute */
    public void setMinute(int minute) {
        this.minute = minute;
        paintClock();
    }

    /** Return second */
    public int getSecond() {
        return second;
    }

    /** Set a new second */
    public void setSecond(int second) {
        this.second = second;
        paintClock();
    }

    /** Return clock pane's width */
    public double getW() {
        return w;
    }

    /** Set clock pane's width */
    public void getW(double w) {
        this.w = w;
        paintClock();
    }

    /** Return clock pane's height */
    public double getH() {
        return h;
    }

    /** Set clock pane's height */
    public void setH(double h) {
        this.h = h;
        paintClock();
    }

    /** Paint the clock */
    protected void paintClock() {
        // Initialize clock parameters
        double clockRadius = Math.min(w, h) * 0.8 * 0.5;
        double centerX = w / 2;
        double centerY = h / 2;

        // Draw circle
        Circle circle = new Circle(centerX, centerY, clockRadius);
        circle.setFill(Color.WHITE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(10);

        // Draw second hand
        double sLength = clockRadius * 0.8;
        double secondX = centerX + sLength *
                Math.sin(second * (2 * Math.PI / 60));
        double secondY = centerY - sLength *
                Math.cos(second * (2 * Math.PI / 60));
        Line sLine = new Line(centerX, centerY, secondX, secondY);
        sLine.setStroke(Color.RED);
        getChildren().clear();
        getChildren().addAll(circle, sLine);
    }

    /* Animate the clock */
    protected void moveClock() {
        if (second == 60) {
            minute += 1;
        }
        second = (second < 60 ? second + 1 : 1);
        paintClock();
    }

    public void countTime() {
        if (timeline != null) {
            timeline.stop();
        }
        timeSeconds = TIMESTART;

        // update timerLabel
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
                new KeyFrame(Duration.seconds(1),
                        new EventHandler() {
                            @Override
                            public void handle(Event event) {
                                timeSeconds--;
                                // update timerLabel
                                if (timeSeconds <= 0) {
                                    timeline.stop();
                                    pause();
                                }
                            }
                        }));
        timeline.playFromStart();
    }

    public void pause() {
        animation.pause();
    }
}
