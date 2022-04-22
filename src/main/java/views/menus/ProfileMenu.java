package views.menus;

import models.User;

import java.util.Scanner;

public class ProfileMenu {
    private Scanner scanner;
    private User user;

    public ProfileMenu(Scanner scanner, User user) {
        this.scanner = scanner;
        this.user = user;
    }

    public void run() {

    }

    private boolean checkCommand(String input) {
        return true;
    }

    private boolean checkExitMenu(String input, Scanner scanner)
    {
        return true;
    }

    private boolean checkEnterMenu(String input, Scanner scanner)
    {
        return true;
    }

    private boolean checkShowMenu(String input, Scanner scanner)
    {
        return true;
    }

    private boolean checkChangeProfile(String nickname)
    {
        return true;
    }

    private boolean checkChangePassword(String input)
    {
        return true;
    }

    
}
