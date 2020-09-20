package Controller;

import Model.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler implements Runnable {
    // Fields
    private ServerController serverController;
    private Socket socket;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private boolean stop = false;

    // Constructor
    public ClientHandler(ServerController serverController, Socket socket) throws IOException {
        this.serverController = serverController;
        this.socket = socket;
        ois = new ObjectInputStream(this.socket.getInputStream());
        oos = new ObjectOutputStream(this.socket.getOutputStream());

    }

    /**
     * start server and listen on port 8080
     */
    @Override
    public void run() {
        System.out.printf("player %s connect\n", socket.getInetAddress().getHostAddress());

        while (!stop) {
            // wait and listen to a client
            try {
                Message message = (Message) this.ois.readObject();
                handleMessage(message);
            } catch (IOException | ClassNotFoundException e) {
                System.out.printf("player %s was disconnected\n", socket.getInetAddress().getHostAddress());
                serverController.removeClient(this);
                stop = true;
            }
        }
    }

    /**
     * handle sent message
     */
    private void handleMessage(Message message) {
        System.out.printf("receive message %s\n", message.toString());
        this.serverController.broadcast(message, this);
    }


    /**
     * send message to server
     */
    public void send(Object object) throws IOException {

        this.oos.writeObject(object);
        this.oos.flush();
        this.oos.reset();
    }


}
