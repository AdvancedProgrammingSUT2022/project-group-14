package views;

import controllers.MapController;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import models.tiles.Hex;
import models.tiles.Tile;

public class GamePageController {
    @FXML
    private AnchorPane hexPane;

    public void initialize() {
        initHexes();
    }

    public void initHexes(){
        for (int i = 0; i < MapController.getHeight(); i++) {
            for (int j = 0; j < MapController.getWidth(); j++) {
                Tile tile = MapController.getTileByCoordinates(i, j);
                Hex hex = tile.getHex();
                hexPane.getChildren().add(hex.getGroup());
                hex.updateHexOfGivenTile(tile);
            }
        }
    }
}
