package Controller;

import Model.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainMenu {
    @FXML
    private VBox mainMenu;
    private Instruction instruction;
    private Leaderboard leaderboard;
    private SettingController settingController;
    private Message message = new Message("start");
    private int total = 0;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void playGame(MouseEvent actionEvent) throws IOException {
        /** Will go to the gameBoard or the selection scene **/

        leaderboard.getLeaderBoard().setVisible(false);
        mainMenu.setVisible(false);
        instruction.getInstruction().setVisible(false);
        leaderboard.getLeaderBoard().setVisible(false);
        settingController.getSettingBoard().setVisible(false);
        GameBoard.clientController.writeMessage(message);
        total ++;
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
//    start game message
    public void proccessMessageStart(Message message){
        this.message.setTotal(message.getTotal());
        if (this.getTotal() == 4) {
//          start scene Main Game
        }
    }
}
