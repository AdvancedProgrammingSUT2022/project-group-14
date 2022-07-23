package Server;

import Server.models.ServerSocketHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static int SERVER_PORT = 8000;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

        System.out.println("Listening on port " + SERVER_PORT);

        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("Client accepted!");
            new ServerSocketHandler(socket).start();
        }
    }

}
