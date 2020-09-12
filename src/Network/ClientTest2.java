package Network;

import Controller.ClientController;
import Model.Message;

import java.io.IOException;
import java.util.Scanner;

public class ClientTest2 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ClientController controller = new ClientController("127.0.0.1", 8080);
        controller.startClient();

        Scanner scanner = new Scanner(System.in);
        while(true) {
            // test
            System.out.println("1. send something to server");
            System.out.println("2. disconnect");

            int option = Integer.parseInt(scanner.nextLine());

            if(option == 1) {

                // necessary
                Message message = new Message("player1", "player1_name", null);
                controller.writeMessage(message);
            }

            if(option == 2) {
                controller.stopClient();
            }
        }
    }
}
