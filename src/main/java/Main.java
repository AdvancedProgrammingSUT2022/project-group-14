
import controllers.UserController;
import views.menus.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
         UserController.readAllUsers();
         Scanner scanner = new Scanner(System.in);
         LoginMenu loginMenu = new LoginMenu(scanner);
         loginMenu.run();
         UserController.saveAllUsers();
    }
} 
