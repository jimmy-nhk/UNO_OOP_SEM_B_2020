package Controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Template for a row in leaderboard
 */

public class LeaderboardRow {
    private SimpleStringProperty nameColumn;
    private SimpleIntegerProperty winColumn;

    public LeaderboardRow(String nameColumn, Integer winColumn){
        this.nameColumn = new SimpleStringProperty(nameColumn);
        this.winColumn = new SimpleIntegerProperty(winColumn);
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn.set(nameColumn);
    }

    public void setWinColumn(Integer winColumn) {
        this.winColumn.set(winColumn);
    }

    public LeaderboardRow() {
    }

    public String getNameColumn() {
        return nameColumn.get();
    }

    public Integer getWinColumn() {
        return winColumn.get();
    }
}