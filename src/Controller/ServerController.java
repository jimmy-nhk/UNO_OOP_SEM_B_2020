package Controller;

import Network.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ServerController {
    //Fields
    private boolean isListening = true;
    private boolean checkStarted = false;
    private List<ClientHandler> clientHandlers = new ArrayList<>();
    private int port;

    // Constructor
    public ServerController(int port) {
        this.port = port;
    }

    public void removeClient(ClientHandler client) {
        clientHandlers.remove(client);
    }
    /**
     * start listening to clients
     */
    public void startListening() {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.printf("Start server successfully on host %s port %d\n", serverSocket.getInetAddress().getHostAddress(), port);

            while (true) {
                // wait for a connection
                Socket socket = serverSocket.accept();

                // start new thread to do steps belows:
                // somehow:
                // 1. read message from socket
                // 2. process the messge
                // 3. write reponse messget to socket
                //
                ClientHandler clientHandler = new ClientHandler(this, socket);
                clientHandlers.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            //System.out.println(e.st));
            e.printStackTrace();
        }
        System.out.println("Server has been stopped");
    }

    /**
     * stop listening temporarily
     */
//    public void stopListening() {
//        this.isListening = false;
//    }

    /**
     * stop listening temporarily
     */
//    public void continueListening() {
//        this.isListening = false;
//    }

    public void broadcast(Object object, ClientHandler sender) {
        for (ClientHandler receiver : clientHandlers) {
            if (receiver != sender) {
                try {
                    receiver.send(object);
                } catch (IOException e) {
                    System.out.println("Failed to send message " + e.getMessage());
                }
            }
        }
    }

    /**
     * check if game is ready to play
     */
//    public boolean isGameReady() {
//        // game is only ready when there are 4 players
//        if (this.server.countClientHandlers() != 4) return false;
//        // game is ready when all players are ready
//        for (ClientHandler player : this.server.getClientHandlers()) {
//            if (!player.isReady()) return false;
//        }
//        return true;
//    }
//
//    public void startGame() {
//
//    }
//
//    public void prepareNewGame() {
//
//    }
}
