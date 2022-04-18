package views.menus;

import java.util.Scanner;

public class LoginMenu {

    public void run(Scanner scanner) {
        String input = new String();
        loop: while (true) {
            input = scanner.nextLine();
            if (!checkCommand(input))
                break loop;
        }
    }

    private boolean checkCommand(String input) {
        return true;
    }

    private boolean checkExitMenu(String input, Scanner scanner) {
        return true;
    }

    private boolean checkEnterMenu(String input, Scanner scanner) {
        return true;
    }

    private boolean checkShowMenu(String input, Scanner scanner) {
        return true;
    }

    private boolean checkLogin(String input) {
        return true;
    }

    private boolean checkCreateUser(String input) {
        return true;
    }

    private boolean checkUsername(String input) {
        return true;
    }

    private boolean checkPassword(String input) {
        return true;
    }

    private boolean checkNickname(String input) {
        return true;
    }

    private void loginUser(String username) {
    }
}
