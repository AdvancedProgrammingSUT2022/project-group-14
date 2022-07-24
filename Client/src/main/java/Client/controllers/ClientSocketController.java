package Client.controllers;

import Client.enums.QueryRequests;
import Client.models.network.Request;
import Client.models.network.Response;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class ClientSocketController {
    private static Socket socket;
    private static BufferedWriter bufferedWriter;
    private static BufferedReader bufferedReader;

    public static void startConnecting(int serverPort) {
        try {
            socket = new Socket("localhost", serverPort);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("You've successfully connected to the server.");
        } catch (IOException e) {
            System.err.print("Couldn't connect to the server!");
            System.exit(0);
        }
    }

    public static Response sendRequestAndGetResponse(QueryRequests command, HashMap<String, String> params) {
        try {
            String requestJson = new Gson().toJson(new Request(command, params));
            bufferedWriter.write(requestJson + "\n");
            bufferedWriter.flush();
            return new Gson().fromJson(bufferedReader.readLine(), Response.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
