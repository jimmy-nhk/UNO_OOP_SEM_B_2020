package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class MainMenu {
    @FXML
    private VBox mainMenu;
    private Instruction instruction;
    private Leaderboard leaderboard;
    private SettingController settingController;

    public void playGame(MouseEvent actionEvent) {
        /** Will go to the gameBoard or the selection scene **/

        leaderboard.getLeaderBoard().setVisible(false);
        mainMenu.setVisible(false);
        instruction.getInstruction().setVisible(false);
        leaderboard.getLeaderBoard().setVisible(false);
        settingController.getSettingBoard().setVisible(false);
    }

    public void showLeaderBoard(ActionEvent actionEvent) {
        mainMenu.setVisible(false);
        instruction.getInstruction().setVisible(false);
        leaderboard.getLeaderBoard().setVisible(false);
        settingController.getSettingBoard().setVisible(false);
        leaderboard.getLeaderBoard().setVisible(true);
    }

    public void showInstruction(ActionEvent actionEvent) {
        leaderboard.getLeaderBoard().setVisible(false);
        mainMenu.setVisible(false);
        leaderboard.getLeaderBoard().setVisible(false);
        settingController.getSettingBoard().setVisible(false);
        instruction.getInstruction().setVisible(true);
    }

    public void showSettings(ActionEvent actionEvent) {
        leaderboard.getLeaderBoard().setVisible(false);
        mainMenu.setVisible(false);
        instruction.getInstruction().setVisible(false);
        leaderboard.getLeaderBoard().setVisible(false);
        settingController.getSettingBoard().setVisible(true);
    }

    public void quit(ActionEvent actionEvent) {
    }

    public VBox getMainMenu() {
        return mainMenu;
    }
}
