package Server;

import Server.controllers.UserController;
import Server.models.network.ServerSocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static int SERVER_PORT = 8000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

        System.out.println("Listening on port " + SERVER_PORT);
        UserController.readAllUsers();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                UserController.saveAllUsers();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client accepted!");
            new ServerSocketHandler(socket).start();
        }
    }


}
