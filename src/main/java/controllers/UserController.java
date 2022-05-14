package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;

public class UserController {
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser = null;

    public static void readAllUsers() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/usersData.json")));
        users = new Gson().fromJson(json,
                new TypeToken<List<User>>(){}.getType());
        if (users == null)
            users = new ArrayList<>();
    }

    public static void saveAllUsers() throws IOException {
        FileWriter fileWriter = new FileWriter("./src/main/resources/usersData.json");
        fileWriter.write(new Gson().toJson(users));
        fileWriter.close();
    }

    public static void addUser(String username, String password, String nickname) {
        users.add(new User(username, password, nickname));
    }

    public static User getUserByUsername(String username){
        for (User user : users) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }

    public static User getUserByNickname(String nickname){
        for (User user : users) {
            if (user.getNickname().equals(nickname))
                return user;
        }
        return null;
    }

    public static boolean playerNumberIsCorrect(Matcher matcher, int numberOfPlayersSoFar){
        if (Integer.parseInt(matcher.group("playerNumber")) == numberOfPlayersSoFar)
            return true;
        return false;
    }

    public static boolean usernameExistsInArraylist(String username, ArrayList<String> usernames){
        for (String playerUsername : usernames) {
            if (playerUsername.equals(username))
                return true;
        }
        return false;
    }

    //this function checks if current player format is correct and exists in users and not repetitive and returns the error
    // and returns "" if everything is ok and add the players username to usernames
    public static String checkPlayerValidation(Matcher matcher, ArrayList<String> usernames, int numberOfPlayersSoFar){
        if (!playerNumberIsCorrect(matcher, numberOfPlayersSoFar)){
            return "invalid command";
        }
        if (getUserByUsername(matcher.group("username")) == null){
            return "one of the usernames doesn't exist";
        }
        if (usernameExistsInArraylist(matcher.group("username"), usernames)){
            return "one of the usernames is repetitive";
        }
        usernames.add(matcher.group("username"));
        return "";
    }

    public static ArrayList<User> getUsers() {
        return users;
    }
}
