package Controller;

import javafx.beans.property.SimpleStringProperty;

public class LeaderboardRow {
    private SimpleStringProperty nameColumn;
    private SimpleStringProperty winColumn;

    public LeaderboardRow(String nameColumn, String winColumn){
        this.nameColumn = new SimpleStringProperty(nameColumn);
        this.winColumn = new SimpleStringProperty(winColumn);
    }

    public void setNameColumn(String nameColumn) {
        this.nameColumn.set(nameColumn);
    }

    public void setWinColumn(String winColumn) {
        this.winColumn.set(winColumn);
    }

    public LeaderboardRow() {
    }

    public String getNameColumn() {
        return nameColumn.get();
    }

    public String getWinColumn() {
        return nameColumn.get();
    }
}