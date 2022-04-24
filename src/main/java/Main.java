import java.util.ArrayList;

import controllers.TileController;
import models.Tile;
import models.User;
import views.GamePlay;

public class Main {
    public static void main(String[] args) throws Exception {
        // User user1 = new User("afsa1729", "333afsa1729", "ashkan");
        // User user2 = new User("aliTar", "333aliTar1729", "ali");
        // ArrayList<String> usernames = new ArrayList<>();
        // usernames.add(user1.getUsername());
        // usernames.add(user2.getUsername());
        // GamePlay gamePlay = new GamePlay();
        // gamePlay.run(usernames);

        Tile.readTileTypesInformationFromJson();

    }
} 
