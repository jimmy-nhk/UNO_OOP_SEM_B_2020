package Controller;

import Model.Message;

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
    ObjectOutputStream oos;
    ObjectInputStream ois;
    private MainController mainController;

    // Constructor
    public ClientController(String serverIP, int serverPort) throws IOException {
        this.serverIP = serverIP;
        this.serverPort = serverPort;
        socket = new Socket(serverIP, serverPort);
        oos = new ObjectOutputStream(socket.getOutputStream());
        ois = new ObjectInputStream(socket.getInputStream());
    }

    public void writeMessage(Message message) throws IOException {
        // write this message to server
        oos.writeObject(message);
        oos.flush();
        oos.reset();
    }

    public void startClient() throws IOException, ClassNotFoundException {


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

        Message message = (Message) ois.readObject();
        // call game controller to process this message
        MainController.processMessage(message);
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
