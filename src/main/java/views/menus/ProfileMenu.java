package views.menus;

import controllers.UserController;
import enums.Commands;
import models.User;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu {
    private Scanner scanner;
    private User user;

    public ProfileMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
    }

    public void run() {
        String input;
        loop: while (true) {
            input = this.scanner.nextLine();
            if (!checkCommand(input))
                break loop;
        }
    }

    public boolean checkCommand(String input) {
        Matcher matcher;

        if ((matcher = Commands.getMatcher(input, Commands.ENTER_MENU)) != null) {
            if (checkEnterMenu(matcher))
                return false;
        } else if (Commands.startsWith(input, Commands.PROFILE_CHANGE)) {
            checkChangeProfile(input);
        } else if ((matcher = Commands.getMatcher(input, Commands.SHOW_MENU)) != null) {
            System.out.println("Profile Menu");
        } else if ((matcher = Commands.getMatcher(input, Commands.EXIT_MENU)) != null) {
            System.out.println("user successfully transferred to main menu");
            return false;
        } else
            System.out.println("invalid command");
        return true;
    }

    public boolean checkEnterMenu(Matcher matcher) {
        switch (matcher.group("menuName")) {
            case "login menu":
                System.out.println("menu navigation is not possible");
                break;
            case "profile menu":
                System.out.println("this is your current menu");
                break;
            case "game menu":
                System.out.println("menu navigation is not possible");
                break;
            case "main menu":
                System.out.println("user successfully transferred to main menu");
                return true;
            default:
        }

        return false;
    }

    public void checkChangeProfile(String input) {
        Matcher matcher;

        if ((matcher = Commands.matcherFindsRegex(input, Commands.NICKNAME)) != null) {
            checkChangeNickname(matcher);
        } else if (Commands.matcherFindsRegex(input, Commands.PASSWORD_FLAG) != null) {
            checkChangePassword(input);
        } else
            System.out.println("invalid command");

    }

    public void checkChangeNickname(Matcher matcher) {
        if (UserController.getUserByNickname(matcher.group("nickname")) != null)
            System.out.println("user with nickname " + matcher.group("nickname") + " already exists");

        else {
            this.user.setNickname(matcher.group("nickname"));
            System.out.println("nickname changed successfully!");
        }
    }

    public void checkChangePassword(String input) {
        Matcher currentPasswordMatcher;
        Matcher newPasswordMatcher;

        if ((currentPasswordMatcher = Commands.matcherFindsRegex(input, Commands.CURRENT)) != null &&
                (newPasswordMatcher = Commands.matcherFindsRegex(input, Commands.NEW)) != null) {

            if (!this.user.getPassword().equals(currentPasswordMatcher.group("currentPassword")))
                System.out.println("current password is invalid");
            else if (currentPasswordMatcher.group("currentPassword").equals(newPasswordMatcher.group("newPassword")))
                System.out.println("please enter a new password");
            else {
                this.user.setPassword(newPasswordMatcher.group("newPassword"));
                System.out.println("password changed successfully!");
            }

        } else
            System.out.println("invalid command");
    }

}
