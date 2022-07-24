package Server.models;

import Server.controllers.UserController;
import com.google.gson.Gson;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerSocketHandler extends Thread{
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private User loggedInUser = null;

    public ServerSocketHandler(Socket socket) throws IOException {
        this.inputStream = new DataInputStream(socket.getInputStream());
        this.outputStream = new DataOutputStream(socket.getOutputStream());
    }

    @Override
    public void run() {
        while (true) {
            try {
                Response response = handleRequest(new Gson().fromJson(inputStream.readUTF(), Request.class));
                outputStream.writeUTF(new Gson().toJson(response));
                outputStream.flush();
            } catch (IOException ignored) {
                System.out.println("Client disconnected");
                break;
            }
        }
    }

    public Response handleRequest(Request request) {
        switch (request.getCommand()){
            case GET_USER_BY_USERNAME -> {
                return getUserByUsername(request);
            }
            case GET_USER_BY_NICKNAME -> {
                return getUserByNickname(request);
            }
            case ADD_USER -> addUser(request);
            case SET_LOGGED_IN_USER -> loggedInUser = (User) request.getParams().get("user");
            case GET_USERS -> {
                return getUsers(request);
            }
            default -> {
                return null;
            }
        }

        return null;
    }

    public Response getUserByUsername(Request request) {
        return new Response(null, UserController.getUserByUsername((String) request.getParams().get("username")));
    }

    public Response getUserByNickname(Request request) {
        return new Response(null, UserController.getUserByNickname((String) request.getParams().get("username")));
    }

    public void addUser(Request request) {
        UserController.addUser((String) request.getParams().get("username"), (String) request.getParams().get("password"), (String) request.getParams().get("nickname"));
    }

    public Response getUsers(Request request) {
        return new Response(null, UserController.getUsers());
    }

}
