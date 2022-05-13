package views.menus;

import controllers.UserController;
import enums.Commands;
import models.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu {

    private Scanner scanner;

    public LoginMenu(Scanner scanner){
        this.scanner = scanner;
    }

    public void run() {
        String input;
        loop:
        while (true) {
            input = this.scanner.nextLine();
            if (!checkCommand(input))
                break loop;
        }
    }

    public boolean checkCommand(String input) {

        Matcher matcher;
        if (Commands.startsWith(input, Commands.LOGIN)) {
            checkLogin(input);

        } else if (Commands.startsWith(input, Commands.CREATE_USER)) {
            checkCreateUser(input);
        } else if ((matcher = Commands.getMatcher(input, Commands.ENTER_MENU)) != null) {
            checkEnterMenu(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.SHOW_MENU)) != null) {
            System.out.println("Login Menu");
        } else if ((matcher = Commands.getMatcher(input, Commands.EXIT_MENU)) != null) {
            System.out.println("you exited the game successfully");
            return false;
        } else System.out.println("invalid command");


        return true;

    }


    public void checkEnterMenu(Matcher matcher) {
        String menuName = matcher.group("menuName");
        switch (menuName){
            case "login menu":
                System.out.println("this is your current menu");
                break;
            case "profile menu":
            case "game menu":
                System.out.println("menu navigation is not possible");
                break;
            case "main menu":
                System.out.println("please login first");
            default:
        }
    }


    public void checkLogin(String input) {
        Matcher usernameMatcher;
        Matcher passwordMatcher;
        if ((usernameMatcher = Commands.matcherFindsRegex(input, Commands.USERNAME)) != null &&
                (passwordMatcher = Commands.matcherFindsRegex(input, Commands.PASSWORD)) != null) {


            if (!usernameExists(usernameMatcher)) System.out.println("Username and password didn’t match!");
            else {
                User user = UserController.getUserByUsername(usernameMatcher.group("username"));
                if (passwordIsCorrect(passwordMatcher, user)) {
                    System.out.println("user logged in successfully!");

                    loginUser(user);
                } else System.out.println("Username and password didn’t match!");
            }
        } else System.out.println("invalid command");

    }

    public void checkCreateUser(String input) {
        Matcher usernameMatcher;
        Matcher passwordMatcher;
        Matcher nicknameMatcher;
        if ((usernameMatcher = Commands.matcherFindsRegex(input, Commands.USERNAME)) != null &&
                (passwordMatcher = Commands.matcherFindsRegex(input, Commands.PASSWORD)) != null &&
                (nicknameMatcher = Commands.matcherFindsRegex(input, Commands.NICKNAME)) != null) {

            if (usernameExists(usernameMatcher))
                System.out.println("user with username " + usernameMatcher.group("username") +" already exists");
            else if (nicknameExists(nicknameMatcher))
                System.out.println("user with nickname " + nicknameMatcher.group("nickname") +" already exists");
            else {
                UserController.addUser(usernameMatcher.group("username"),
                        passwordMatcher.group("password"),
                        nicknameMatcher.group("nickname"));

                System.out.println("user created successfully!");
            }

        } else System.out.println("invalid command");


    }

    public boolean usernameExists(Matcher usernameMatcher) {
        return UserController.getUserByUsername(usernameMatcher.group("username")) != null;
    }

    public boolean passwordIsCorrect(Matcher passwordMatcher, User user) {
        return user.getPassword().equals(passwordMatcher.group("password"));
    }

    public boolean nicknameExists(Matcher nicknameMatcher) {
        return UserController.getUserByNickname(nicknameMatcher.group("nickname")) != null;
    }

    public void loginUser(User user) {
        MainMenu mainMenu = new MainMenu(this.scanner, user);
        mainMenu.run();
    }
}
