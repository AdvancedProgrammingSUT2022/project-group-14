import controllers.MapController;

public class Main {
    public static void main(String[] args) throws Exception {
//         UserController.readAllUsers();
//         Scanner scanner = new Scanner(System.in);
//         LoginMenu loginMenu = new LoginMenu(scanner);
//         loginMenu.run();
//         UserController.saveAllUsers();

        MapController mapController = new MapController();
        MapController.mapInit();
        MapController.showMap();
    }
}
