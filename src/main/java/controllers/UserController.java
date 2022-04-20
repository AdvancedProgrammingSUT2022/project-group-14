package controllers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;

public class UserController {
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser = null;

    public static void readAllUsers() throws IOException {
        String json = new String(Files.readAllBytes(Paths.get("usersData.json")));
        users = new Gson().fromJson(json,
                new TypeToken<List<User>>(){}.getType());
    }

    public static void saveAllUsers() throws IOException {
        FileWriter fileWriter = new FileWriter("usersData.json");
        fileWriter.write(new Gson().toJson(users));
        fileWriter.close();
    }

    public static void addUser(String username, String password, String nickname) {
        users.add(new User(username, password, nickname));
    }

    
}
