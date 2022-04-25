package views.menus;

import controllers.UserController;
import controllers.WorldController;
import enums.Commands;
import models.User;
import views.GamePlay;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu {
    private Scanner scanner;
    private User user;

    public MainMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
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

    private boolean checkCommand(String input) {


        Matcher matcher;

        if ((matcher = Commands.getMatcher(input, Commands.ENTER_MENU)) != null) {
            if (checkEnterMenu(matcher))
                return false;
        } else if (Commands.startsWith(input, Commands.START_GAME)) {
            checkPlayGame(input);
        } else if ((matcher = Commands.getMatcher(input, Commands.SHOW_MENU)) != null) {
            System.out.println("Main Menu");
        } else if ((matcher = Commands.getMatcher(input, Commands.EXIT_MENU)) != null ||
                (matcher = Commands.getMatcher(input, Commands.LOGOUT)) != null) {
            System.out.println("user logged out and returned to login menu successfully");
            return false;
        } else System.out.println("invalid command");

        return true;
    }

    private boolean checkEnterMenu(Matcher matcher) {
        String menuName = matcher.group("menuName");
        switch (menuName) {
            case "login menu":
                System.out.println("user logged out and returned to login menu successfully");
                return true;
            case "profile menu":
                System.out.println("user successfully entered profile menu");
                goToProfileMenu();
                break;
            case "game menu":
                System.out.println("menu navigation is not possible");
                break;
            case "main menu":
                System.out.println("this is your current menu");
            default:
        }

        return false;
    }


    private void checkPlayGame(String input) {
        Matcher matcher;
        int numberOfPlayersSoFar = 1;

        if ((matcher = Commands.matcherFindsRegex(input, Commands.PLAYER_USERNAME)) != null) {

            ArrayList<String> usernames = new ArrayList<>();
            usernames.add(this.user.getUsername());
            String output;

            if ((output = UserController.checkPlayerValidation(matcher,usernames, numberOfPlayersSoFar)).equals("")){
                numberOfPlayersSoFar++;
                while (matcher.find()){
                    if (!(output = UserController.checkPlayerValidation(matcher,usernames, numberOfPlayersSoFar)).equals("")){
                        System.out.println(output);
                        return;
                    }
                    numberOfPlayersSoFar++;
                }
                startGame(usernames);

            }else System.out.println(output);


        } else System.out.println("invalid command");
    }

    private void startGame(ArrayList<String> usernames){
        GamePlay gamePlay = new GamePlay();
        WorldController.newWorld(usernames);
        gamePlay.run(usernames, this.scanner);
    }

    private void goToProfileMenu() {
        ProfileMenu profileMenu = new ProfileMenu(this.scanner, this.user);
        profileMenu.run();
    }


}
