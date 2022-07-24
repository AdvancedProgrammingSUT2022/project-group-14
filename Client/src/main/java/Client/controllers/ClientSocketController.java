package Client.controllers;

import Client.enums.QueryCommands;
import Client.models.Request;
import Client.models.Response;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.regex.Matcher;

public class ClientSocketController {
    private static DataInputStream inputStream;
    private static DataOutputStream outputStream;

    public static void startConnecting(int serverPort) {
        try {
            Socket socket = new Socket("localhost", serverPort);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            System.out.println("You've successfully connected to the server.");
        } catch (IOException e) {
            System.err.print("Couldn't connect to the server!");
            System.exit(0);
        }
    }

    public static Response sendRequestAndGetResponse(QueryCommands command, HashMap<String, Object> params) {
        try {
            Request request = new Request(command, params);
            String requestString = new Gson().toJson(request);
            outputStream.writeUTF(requestString);
            outputStream.flush();
            String responseString = inputStream.readUTF();
            return new Gson().fromJson(responseString, Response.class);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
        return null;
    }

}
