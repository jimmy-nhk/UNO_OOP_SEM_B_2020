package Controller;

import Controller.ServerController;
import Model.AccountList;
import Model.Message;
import Model.Player;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
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
                this.ois = new ObjectInputStream(this.socket.getInputStream());
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
     * check if player is ready
     */
//    public void setReady() {
//        this.isReady = true;
//    }

//    public boolean isConnected() {
//        return this.socket.isConnected();
//    }

    /**
     * send message to server
     */
    public void send(Object object) throws IOException {
        this.oos = new ObjectOutputStream(this.socket.getOutputStream());
        this.oos.writeObject(object);
        this.oos.flush();
        this.oos.reset();
    }

    /**
     * disconnect from server
     */
//    public void disconnect() throws IOException {
//        this.socket.close();
//    }
}
