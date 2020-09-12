package Network;

import Controller.ServerController;

public class ServerTest {
    public static void main(String[] args) {
        ServerController controller = new ServerController(8080);
        controller.startListening();
    }
}
