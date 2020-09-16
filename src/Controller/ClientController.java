package Controller;

import Model.Message;
import Service.GameBoardService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientController implements Runnable {
    // send message to server
    // receive message from server

    // Fields
    private Socket socket;
    private final String serverIP;
    private final int serverPort;
    public static int total = 0;
    private int replica = 0;
    private GameBoard gameBoard;


    // Constructor
    public ClientController(String serverIP, int serverPort) {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        this.replica = total;
        total ++;
    }

    public int getReplica() {
        return replica;
    }

    public void writeMessage(Message message) throws IOException {
        // write this message to server
        ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
        oos.writeObject(message);
        oos.flush();
        oos.reset();
    }

    public void startClient() throws IOException, ClassNotFoundException {
        socket = new Socket(serverIP, serverPort);

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
        switch (message.getTypeOfAction()) {
            case "initialize":
                gameBoard.processMessageInitialize(message);
                break;
            case "play":
                gameBoard.processMessageEachTurn(message);
                break;
            case "draw":
                gameBoard.processMessageEachTurn(message);
                break;
            case "reset":
                gameBoard.processMessageInitialize(message);
                break;
            case "start":
                mainMenu.proccessMessageStart(message);
                break;
        }
        System.out.printf("received message: %s\n", message.toString());
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
