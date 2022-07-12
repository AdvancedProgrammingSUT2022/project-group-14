package views;

import application.App;
import controllers.MapController;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
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
        for (int i = 0; i < MapController.getWidth(); i++) {
            for (int j = 0; j < MapController.getHeight(); j++) {
                Tile tile = MapController.getTileByCoordinates(i, j);
                Hex hex = tile.getHex();
                hexPane.getChildren().add(hex.getGroup());
                hex.updateHexOfGivenTile(tile);
            }
        }
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("startGameMenuPage");
    }
}
