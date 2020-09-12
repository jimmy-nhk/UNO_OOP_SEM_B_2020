package Controller;

import Model.Card;
import Model.Deck;
import Model.Message;
import Network.ClientHandler;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientController implements Runnable {
    // send message to server
    // receive message from server

    // 2. connect to server
    // 3. send hello message.
    //      hello message have to client's information which need by server to talk
    //      example: player's id

    // Fields
    private Socket socket;
    private final String serverIp;
    private final int serverPort;

    // Constructor
    public ClientController(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void writeMessage(Message message) throws IOException {
        // write this message to server
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(message);
        oos.flush();
        oos.reset();
    }

    public void startClient() throws IOException, ClassNotFoundException {
        socket = new Socket(serverIp, serverPort);

        // create new thread to read message from server
        new Thread(() -> {
            try {
                readMessage();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void stopClient() throws IOException {
        socket.close();
    }

    public void readMessage() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Message message = (Message) ois.readObject();
        // call game controller to process this message
        // GameBoard.process(message);
        System.out.printf("got message %s\n", message.toString());
    }

    @Override
    public void run() {
        try {
            startClient();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
