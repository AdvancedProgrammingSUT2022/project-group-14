package Server.controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import Server.models.User;

public class UserController {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<User> loggedInUsers = new ArrayList<>();


    public static void addLoggedInUser(User loggedInUser) {
        UserController.loggedInUsers.add(loggedInUser);
        loggedInUser.setDateOfLastLogin(new Date());
    }

    public static void removeLoggedOutUser(User loggedOutUser) {
        loggedOutUser.setToken(null);
        UserController.loggedInUsers.remove(loggedOutUser);
    }

    public static void readAllUsers() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("./Server/src/main/resources/usersData.json")));
        users = new Gson().fromJson(json,
                new TypeToken<List<User>>() {
                }.getType());
        if (users == null)
            users = new ArrayList<>();
        if (loggedInUsers == null)
            loggedInUsers = new ArrayList<>();
    }

    public static void saveAllUsers() throws IOException {
        FileWriter fileWriter = new FileWriter("./Server/src/main/resources/usersData.json");
        fileWriter.write(new Gson().toJson(users));
        fileWriter.close();
    }

    public static void addUser(String username, String password, String nickname) {
        users.add(new User(username, password, nickname));
    }

    public static User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static User getUserByNickname(String nickname) {
        for (User user : users) {
            if (user.getNickname().equals(nickname))
                return user;
        }
        return null;
    }

    public static void sortUsers() {
        users.sort((o1, o2) -> {
            if (o1.getScore() == o2.getScore()) {
                if ((o1.getDateOfLastWin() == null && o2.getDateOfLastWin() == null) || o1.getDateOfLastWin().equals(o2.getDateOfLastWin())) {
                    return o1.getUsername().compareTo(o2.getUsername());
                } else {
                    if (o1.getDateOfLastWin() == null || o2.getDateOfLastWin() == null) {
                        return o1.getDateOfLastWin() == null ? -1 : 1;
                    }
                    return o1.getDateOfLastWin().before(o2.getDateOfLastWin()) ? 1 : -1;
                }
            } else {
                return o1.getScore() < o2.getScore() ? 1 : -1;
            }
        });
    }

    public static String generateToken() {
        String alphabet = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            token.append(alphabet.charAt(new Random().nextInt(36)));
        }
        return token.toString();
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static ArrayList<User> getLoggedInUsers() {
        return loggedInUsers;
    }
}
