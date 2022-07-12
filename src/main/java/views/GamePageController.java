package views;

import application.App;
import controllers.MapController;
import controllers.WorldController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import models.tiles.Hex;
import models.tiles.Tile;

import java.util.Objects;

public class GamePageController {
    @FXML
    private Circle foodCircle;
    @FXML
    private Circle productionCircle;
    @FXML
    private Circle goldCircle;
    @FXML
    private Text foodText;
    @FXML
    private Text productionText;
    @FXML
    private Text goldText;
    @FXML
    private AnchorPane hexPane;

    public void initialize() {
        initNavBar();
        initHexes();
    }

    private void initNavBar() {
        foodCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resourceLogos/foodLogo.png")).toString())));
        productionCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resourceLogos/productionLogo.png")).toString())));
        goldCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resourceLogos/goldLogo.png")).toString())));
        foodText.setText("" + WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getFood());
        productionText.setText("" + WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getProduction());
        goldText.setText("" + WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getGold());
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
