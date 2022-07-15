package views;

import application.App;
import controllers.MapController;
import controllers.UnitController;
import controllers.WorldController;
import enums.units.UnitTypes;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.Civilization;
import models.tiles.Hex;
import models.tiles.Tile;
import models.units.CombatUnit;
import models.units.Ranged;
import models.units.Unit;

import java.util.ArrayList;
import java.util.Objects;
import java.util.RandomAccess;

public class GamePageController {
    @FXML
    private AnchorPane hexPane;
    @FXML
    private Circle goldCircle;
    @FXML
    private Circle happinessCircle;
    @FXML
    private Circle scienceCircle;
    @FXML
    private Circle techCircle;
    @FXML
    private Text goldText;
    @FXML
    private Text happinessText;
    @FXML
    private Text scienceText;
    @FXML
    private Text yearText;
    @FXML
    private Text techText;
    @FXML
    private AnchorPane unitPanelPane;
    @FXML
    private Circle unitPanelCircle;
    @FXML
    private Text unitPanelText;


    public void initialize() {
        initNavBar();
        initHexes();
        initPanelsTimeLine();
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

    public void initHexes() {
        for (int i = 0; i < MapController.getWidth(); i++) {
            for (int j = 0; j < MapController.getHeight(); j++) {
                Tile tile = MapController.getTileByCoordinates(i, j);
                Hex hex = tile.getHex();
                hexPane.getChildren().add(hex.getGroup());
                hex.updateHexOfGivenTile(tile);
            }
        }
    }

    public void initPanelsTimeLine() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        unitPanelPane.setVisible(false);
        techCircle.setVisible(false);
        techText.setText("");
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            if (WorldController.getSelectedCombatUnit() != null && !unitPanelPane.isVisible()) {
                unitPanelPane.setVisible(true);
                setUnitPanelInfo(WorldController.getSelectedCombatUnit());
            } else if (WorldController.getSelectedNonCombatUnit() != null && !unitPanelPane.isVisible()) {
                unitPanelPane.setVisible(true);
                setUnitPanelInfo(WorldController.getSelectedNonCombatUnit());
            } else if (WorldController.unitIsNotSelected()) {
                unitPanelPane.setVisible(false);
            }

            if (currentCivilization.getCurrentTechnology() != null) {
                techCircle.setFill(new ImagePattern(currentCivilization.getCurrentTechnology().getImage()));
                techText.setText(currentCivilization.getCurrentTechnology().getName());
            } else {
                techCircle.setVisible(false);
                techText.setText("");
            }
        }));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public void setUnitPanelInfo(Unit unit) {
        initUnitAbilities(unit);
        unitPanelCircle.setFill(new ImagePattern(unit.getUnitType().getLogoImage()));
        unitPanelText.setText(unit.getName());
    }

    private void initUnitAbilities(Unit unit) {
        if (unitPanelPane.getChildren().size() > 5)
            unitPanelPane.getChildren().subList(5, unitPanelPane.getChildren().size()).clear();
        Circle sleep = new Circle(25, new ImagePattern(UnitController.getActionImage("sleep")));
        if (unit instanceof CombatUnit) {
            if (unit instanceof Ranged) {
            }
        } else if (unit.getUnitType() == UnitTypes.WORKER) {

        } else {

        }
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("startGameMenuPage");
    }

    public void goToUnitsPanel(MouseEvent mouseEvent) {
        App.changeScene("unitsPanel");
    }

    public void goToCitiesPage(MouseEvent mouseEvent) {
        App.changeScene("citiesPage");
    }
}
