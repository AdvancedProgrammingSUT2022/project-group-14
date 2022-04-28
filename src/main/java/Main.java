
import controllers.UserController;
import enums.tiles.TileFeatureTypes;
import models.resources.LuxuryResource;
import models.resources.StrategicResource;
import views.menus.LoginMenu;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // UserController.readAllUsers();
        // Scanner scanner = new Scanner(System.in);
        // LoginMenu loginMenu = new LoginMenu(scanner);
        // loginMenu.run();
        // UserController.saveAllUsers();


        // LuxuryResource.writeData();
        // LuxuryResource.readData();

        System.out.println(TileFeatureTypes.VALLEY.foodGetter());
        TileFeatureTypes.VALLEY.foodSetter(56.7);
        System.out.println(TileFeatureTypes.VALLEY.foodGetter());
        System.out.println(TileFeatureTypes.VALLEY.foodGetter());
        
    }
} 
