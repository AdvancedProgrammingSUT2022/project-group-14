package views;

import application.App;
import controllers.MapController;
import controllers.WorldController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import models.tiles.Hex;
import models.tiles.Tile;

import java.util.Objects;

public class GamePageController {
    @FXML
    private Circle goldCircle;
    @FXML
    private Circle happinessCircle;
    @FXML
    private Circle scienceCircle;
    @FXML
    private Text goldText;
    @FXML
    private Text happinessText;
    @FXML
    private Text scienceText;
    @FXML
    private Text yearText;
    @FXML
    private AnchorPane hexPane;

    public void initialize() {
        initNavBar();
        initHexes();
    }

    private void initNavBar() {
        goldCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resourceLogos/goldLogo.png")).toString())));
        happinessCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resourceLogos/happinessLogo.png")).toString())));
        scienceCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resourceLogos/scienceLogo.png")).toString())));
        goldText.setText("" + WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getGold());
        goldText.setFill(Color.GOLD);
        happinessText.setText("" + WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getHappiness());
        happinessText.setFill(Color.rgb(17, 140, 33));
        scienceText.setText("" + WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getScience());
        scienceText.setFill(Color.rgb(7, 146, 169));
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
