package Server.models;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerSocketHandler extends Thread{
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;

    public ServerSocketHandler(Socket socket) throws IOException {
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
//            try {
//                String requestString = inputStream.readUTF();
//                System.out.println(requestString);
//                Response response = handleRequest(new Gson().fromJson(requestString, Request.class));
//                outputStream.writeUTF(new Gson().toJson(response));
//                outputStream.flush();
//            } catch (IOException ignored) {
//                System.out.println("Client disconnected");
//                break;
//            }
        }
    }

}
