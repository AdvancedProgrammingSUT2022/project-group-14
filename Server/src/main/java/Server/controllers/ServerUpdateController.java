package Server.controllers;

import Server.models.network.Response;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;

public class ServerUpdateController {

    public static void sendUpdate(String username, Response response) {
        try {
            String updateJson = new Gson().toJson(response);
            Objects.requireNonNull(UserController.getUserByUsername(username)).getBufferedWriter().write(updateJson + "\n");
            Objects.requireNonNull(UserController.getUserByUsername(username)).getBufferedWriter().flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
