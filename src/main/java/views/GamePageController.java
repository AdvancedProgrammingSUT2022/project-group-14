package views;

import application.App;
import controllers.MapController;
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
        if (unitPanelPane.getChildren().size() > 7)
            unitPanelPane.getChildren().subList(7, unitPanelPane.getChildren().size()).clear();
        ArrayList<Button> buttons = new ArrayList<>();
        Button sleepWake = new Button("Sleep/Wake");
        sleepWake.setLayoutX(134);
        sleepWake.setLayoutY(72);
        sleepWake.setStyle("-fx-pref-width: 145");
        Button delete = new Button("Delete");
        delete.setLayoutX(162);
        delete.setLayoutY(122);
        delete.setStyle("-fx-pref-width: 90");
        buttons.add(sleepWake);
        buttons.add(delete);
        if (unit instanceof CombatUnit) {
            Button alert = new Button("Alert");
            Button fortify = new Button("Fortify");
            Button fortifyTillHealed = new Button("FortifyTillHealed");
            Button garrison = new Button("Garrison");
            buttons.add(alert);
            buttons.add(fortify);
            buttons.add(fortifyTillHealed);
            buttons.add(garrison);
            if (unit instanceof Ranged) {
                Button setupRanged = new Button("SetupRanged");
            }
        } else if (unit.getUnitType() == UnitTypes.WORKER) {
            ((Button) unitPanelPane.getChildren().get(7)).setText("BuildRoad");
            ((Button) unitPanelPane.getChildren().get(8)).setText("BuildRailRoad");
            ((Button) unitPanelPane.getChildren().get(9)).setText("BuildImprovement");
            ((Button) unitPanelPane.getChildren().get(10)).setText("RemoveRoute");
            ((Button) unitPanelPane.getChildren().get(11)).setText("Repair");
        } else {
            ((Button) unitPanelPane.getChildren().get(7)).setText("FoundCity");
        }
        for (int i = 2; i < buttons.size(); i++) {
            buttons.get(i).setLayoutX(buttons.get(i - 2).getLayoutX() + buttons.get(i - 2).getPrefWidth() + 4);
            buttons.get(i).setLayoutY(buttons.get(i - 2).getLayoutY());
            if (buttons.get(i).getText().length() * 15 <= 90) {
                buttons.get(i).setStyle("-fx-pref-width: 90");
            } else if (buttons.get(i).getText().length() * 15 <= 120) {
                buttons.get(i).setStyle("-fx-pref-width: 120");
            } else if (buttons.get(i).getText().length() * 15 <= 180) {
                buttons.get(i).setStyle("-fx-pref-width: 180");
            }  else if (buttons.get(i).getText().length() * 15 <= 255) {
                buttons.get(i).setStyle("-fx-pref-width: 255");
            }
        }
        unitPanelPane.getChildren().addAll(buttons);
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
