package controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import models.User;

public class UserController {
    private static ArrayList<User> users = new ArrayList<>();
    private static User loggedInUser = null;


    public static void setLoggedInUser(User loggedInUser) {
        UserController.loggedInUser = loggedInUser;
    }

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

    public static void sortUsers(){
        Collections.sort(users, new Comparator() {

            public int compare(Object o1, Object o2) {

                Integer score1 = ((User) o1).getScore();
                Integer score2 = ((User) o2).getScore();
                int scoreCompare = score1.compareTo(score2);
                if (scoreCompare != 0)
                    return scoreCompare;


                Date date1 = ((User) o1).getDateOfLastWin();
                Date date2 = ((User) o2).getDateOfLastWin();
                Integer year1 = date1.getYear();
                Integer year2 = date2.getYear();
                int yearCompare = year1.compareTo(year2);
                if (yearCompare != 0)
                    return yearCompare;

                Integer month1 = date1.getMonth();
                Integer month2 = date2.getMonth();
                int monthCompare = month1.compareTo(month2);
                if (monthCompare != 0)
                    return monthCompare;

                Integer day1 = date1.getDay();
                Integer day2 = date2.getDay();
                int dayCompare = day1.compareTo(day2);
                if (dayCompare != 0)
                    return monthCompare;

                Integer hour1 = date1.getHours();
                Integer hour2 = date2.getHours();
                int hourCompare = hour1.compareTo(hour2);
                if (hourCompare != 0)
                    return hourCompare;

                Integer minute1 = date1.getMinutes();
                Integer minute2 = date2.getMinutes();
                int minuteCompare = minute1.compareTo(minute2);
                if (minuteCompare != 0)
                    return minuteCompare;

                Integer second1 = date1.getSeconds();
                Integer second2 = date2.getSeconds();
                int secondCompare = second1.compareTo(second1);
                if (secondCompare != 0)
                    return secondCompare;

                String name1 = ((User) o1).getUsername();
                String name2 = ((User) o2).getUsername();
                int nameCompare = name1.compareTo(name2);
                return nameCompare;
            }});
    }

    public static ArrayList<User> getUsers() {
        return users;
    }

    public static User getLoggedInUser() {
        return loggedInUser;
    }
}
