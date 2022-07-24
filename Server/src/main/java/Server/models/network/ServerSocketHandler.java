package Server.models.network;

import Server.controllers.UserController;
import Server.controllers.WorldController;
import Server.enums.QueryResponses;
import Server.enums.Technologies;
import Server.models.User;
import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Objects;

public class ServerSocketHandler extends Thread {
    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;

    public ServerSocketHandler(Socket socket) throws IOException {
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        while (true) {
            try {
                String responseJson = new Gson().toJson(handleRequest(new Gson().fromJson(bufferedReader.readLine(), Request.class)));
                bufferedWriter.write(responseJson + "\n");
                bufferedWriter.flush();
            } catch (IOException ignored) {
                System.out.println("Client disconnected");
                break;
            }
        }
    }

    public Response handleRequest(Request request) {
        System.out.println(request.getQueryRequest() + " : " + request.getParams().toString());
        switch (request.getQueryRequest()) {
            case LOGIN_USER -> {
                if (UserController.getUserByUsername(request.getParams().get("username")) == null) {
                    return new Response(QueryResponses.USER_NOT_EXIST, new HashMap<>());
                } else if (!Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).getPassword().equals(request.getParams().get("password"))) {
                    return new Response(QueryResponses.PASSWORD_INCORRECT, new HashMap<>());
                } else {
                    UserController.setLoggedInUser(Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))));
                    return new Response(QueryResponses.OK, new HashMap<>(){{
                        put("user", new Gson().toJson(UserController.getLoggedInUser()));
                    }});
                }
            }
            case CREATE_USER -> {
                if (UserController.getUserByUsername(request.getParams().get("username")) != null) {
                    return new Response(QueryResponses.USERNAME_EXIST, new HashMap<>());
                } else if (UserController.getUserByNickname(request.getParams().get("nickname")) != null) {
                    return new Response(QueryResponses.NICKNAME_EXIST, new HashMap<>());
                } else {
                    UserController.addUser(request.getParams().get("username"), request.getParams().get("password"), request.getParams().get("nickname"));
                    return new Response(QueryResponses.OK, new HashMap<>());
                }
            }
            case SET_CURRENT_TECHNOLOGY -> WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).setCurrentTechnology(Technologies.valueOf(request.getParams().get("technologyName")));
            case CHANGE_NICKNAME -> {
                if (!Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).getPassword().equals(request.getParams().get("password"))) {
                    return new Response(QueryResponses.PASSWORD_INCORRECT, new HashMap<>());
                } else if (UserController.getUserByNickname(request.getParams().get("nickname")) != null) {
                    return new Response(QueryResponses.NICKNAME_EXIST, new HashMap<>());
                } else {
                    Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).setNickname(request.getParams().get("nickname"));
                    return new Response(QueryResponses.OK, new HashMap<>(){{
                        put("user", new Gson().toJson(UserController.getUserByUsername(request.getParams().get("username"))));
                    }});
                }
            }
            case CHANGE_PASSWORD -> {
                if (!Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).getPassword().equals(request.getParams().get("oldPassword"))) {
                    return new Response(QueryResponses.PASSWORD_INCORRECT, new HashMap<>());
                } else {
                    Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).setPassword(request.getParams().get("newPassword"));
                    return new Response(QueryResponses.OK, new HashMap<>(){{
                        put("user", new Gson().toJson(UserController.getUserByUsername(request.getParams().get("username"))));
                    }});
                }
            }
            case CHANGE_AVATAR -> {
                Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).setAvatarFileAddress(request.getParams().get("address"));
                return new Response(QueryResponses.OK, new HashMap<>(){{
                    put("user", new Gson().toJson(UserController.getUserByUsername(request.getParams().get("username"))));
                }});
            }
            case SORT_USERS -> UserController.sortUsers();
            case GET_USERS -> {
                return new Response(QueryResponses.OK, new HashMap<>(){{
                    put("users", new Gson().toJson(UserController.getUsers()));
                }});
            }
        }
        return new Response(QueryResponses.OK, new HashMap<>());
    }

}
