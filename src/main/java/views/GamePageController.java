package views;

import application.App;
import controllers.MapController;
import controllers.MoveController;
import controllers.UnitController;
import controllers.WorldController;
import enums.units.CombatType;
import enums.units.UnitStates;
import enums.units.UnitTypes;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.Civilization;
import models.tiles.Hex;
import models.tiles.Tile;
import models.units.*;

import java.util.ArrayList;
import java.util.Objects;

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
    private Text unitPanelNameText;
    @FXML
    private Text unitPanelMPText;
    @FXML
    private Text unitPanelCSText;


    public void initialize() {
        initNavBar();
        initHexes();
        initTimeLine();
    }

    private void initNavBar() {
        goldCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/goldLogo.png")).toString())));
        happinessCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/happinessLogo.png")).toString())));
        scienceCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/scienceLogo.png")).toString())));
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
                hex.updateHex();
            }
        }
    }

    public void initTimeLine() {
        unitPanelPane.setVisible(false);
        techCircle.setVisible(false);
        techText.setText("");
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
            checkUnitPanelUpdate();
            checkCityPanelUpdate();
            checkTechnologyPanelUpdate();
        }));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    public void checkUnitPanelUpdate() {
        if (WorldController.getSelectedCombatUnit() != null) {
            if (!unitPanelPane.isVisible() || UnitTypes.valueOf(unitPanelNameText.getText().toUpperCase()).getCombatType() == CombatType.NON_COMBAT) {
                unitPanelPane.setVisible(true);
                setUnitPanelInfo(WorldController.getSelectedCombatUnit());
            } else {
                setUnitPanelTexts(WorldController.getSelectedCombatUnit());
            }
        } else if (WorldController.getSelectedNonCombatUnit() != null) {
            if (!unitPanelPane.isVisible() || UnitTypes.valueOf(unitPanelNameText.getText().toUpperCase()).getCombatType() != CombatType.NON_COMBAT) {
                unitPanelPane.setVisible(true);
                setUnitPanelInfo(WorldController.getSelectedNonCombatUnit());
            } else {
                setUnitPanelTexts(WorldController.getSelectedNonCombatUnit());
            }
        } else if (WorldController.unitIsNotSelected()) {
            unitPanelPane.setVisible(false);
        }
    }

    public void checkCityPanelUpdate() {
        if (WorldController.getSelectedCity() != null) {
            //TODO show city banner
        } else {
            //TODO hide city banner
        }
    }

    public void checkTechnologyPanelUpdate() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        if (currentCivilization.getCurrentTechnology() != null) {
            techCircle.setFill(new ImagePattern(currentCivilization.getCurrentTechnology().getImage()));
            techText.setText(currentCivilization.getCurrentTechnology().getName());
        } else {
            techCircle.setVisible(false);
            techText.setText("");
        }
    }

    public void setUnitPanelInfo(Unit unit) {
        if (unitPanelPane.getChildren().size() > 11)
            unitPanelPane.getChildren().subList(11, unitPanelPane.getChildren().size()).clear();
        initCommonActions(unit);
        if (unit instanceof CombatUnit) {
            initCombatUnitActions((CombatUnit) unit);
        } else {
            initNonCombatUnitActions((NonCombatUnit) unit);
        }
        for (int i = 11; i < unitPanelPane.getChildren().size(); i++) {
            unitPanelPane.getChildren().get(i).setLayoutX(unitPanelPane.getChildren().get(i - 2).getLayoutX() + 62);
            unitPanelPane.getChildren().get(i).setLayoutY(unitPanelPane.getChildren().get(i - 2).getLayoutY());
            unitPanelPane.getChildren().get(i).setCursor(Cursor.HAND);
        }
        setUnitPanelTexts(unit);
    }

    public void initCommonActions(Unit unit) {
        ((Circle) unitPanelPane.getChildren().get(7)).setFill(new ImagePattern(UnitController.getActionImage("move")));
        unitPanelPane.getChildren().get(7).setCursor(Cursor.HAND);
        unitPanelPane.getChildren().get(7).setOnMouseClicked(mouseEvent -> {
            if (WorldController.getSelectedTile() != null) {
                UnitController.setUnitDestinationCoordinates(unit, WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY());
                MoveController.moveUnitToDestination(unit);
            }
        });
        ((Circle) unitPanelPane.getChildren().get(8)).setFill(new ImagePattern(UnitController.getActionImage("delete")));
        unitPanelPane.getChildren().get(8).setOnMouseClicked(mouseEvent -> UnitController.delete(unit));
        unitPanelPane.getChildren().get(8).setCursor(Cursor.HAND);
        ((Circle) unitPanelPane.getChildren().get(9)).setFill(new ImagePattern(UnitController.getActionImage("sleep")));
        unitPanelPane.getChildren().get(9).setOnMouseClicked(mouseEvent -> unit.setUnitState(UnitStates.SLEEP));
        unitPanelPane.getChildren().get(9).setCursor(Cursor.HAND);
        ((Circle) unitPanelPane.getChildren().get(10)).setFill(new ImagePattern(UnitController.getActionImage("wake")));
        unitPanelPane.getChildren().get(10).setOnMouseClicked(mouseEvent -> unit.setUnitState(UnitStates.WAKE));
        unitPanelPane.getChildren().get(10).setCursor(Cursor.HAND);
    }

    public void initCombatUnitActions(CombatUnit unit) {
        Circle alert = new Circle(25, new ImagePattern(UnitController.getActionImage("alert")));
        alert.setOnMouseClicked(mouseEvent -> unit.setUnitState(UnitStates.ALERT));
        Circle fortify = new Circle(25, new ImagePattern(UnitController.getActionImage("fortify")));
        fortify.setOnMouseClicked(mouseEvent -> unit.setUnitState(UnitStates.FORTIFY));
        Circle fortifyTillHealed = new Circle(25, new ImagePattern(UnitController.getActionImage("fortifyTillHealed")));
        fortifyTillHealed.setOnMouseClicked(mouseEvent -> unit.setUnitState(UnitStates.FORTIFY_TILL_HEALED));
        Circle garrison = new Circle(25, new ImagePattern(UnitController.getActionImage("garrison")));
        garrison.setOnMouseClicked(mouseEvent -> UnitController.garrisonCity(unit));
        if (unit instanceof Ranged) {
            Circle setupRanged = new Circle(25, new ImagePattern(UnitController.getActionImage("setupRanged")));
            setupRanged.setOnMouseClicked(mouseEvent -> UnitController.setupRangedUnit(unit, WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY()));
            unitPanelPane.getChildren().add(setupRanged);
        }
        unitPanelPane.getChildren().add(alert);
        unitPanelPane.getChildren().add(fortify);
        unitPanelPane.getChildren().add(fortifyTillHealed);
        unitPanelPane.getChildren().add(garrison);
    }

    public void initNonCombatUnitActions(NonCombatUnit unit) {
        if (unit.getUnitType() == UnitTypes.WORKER) {
            Circle working = new Circle(25, new ImagePattern(UnitController.getActionImage("working")));
            working.setOnMouseClicked(mouseEvent -> {

            });
            Circle buildRoad = new Circle(25, new ImagePattern(UnitController.getActionImage("buildRoad")));
            working.setOnMouseClicked(mouseEvent -> {

            });
            Circle repair = new Circle(25, new ImagePattern(UnitController.getActionImage("repair")));
            working.setOnMouseClicked(mouseEvent -> {
                UnitController.repairTile((Worker) unit);
            });
            Circle remove = new Circle(25, new ImagePattern(UnitController.getActionImage("remove")));
            working.setOnMouseClicked(mouseEvent -> {

            });
            unitPanelPane.getChildren().add(working);
            unitPanelPane.getChildren().add(buildRoad);
            unitPanelPane.getChildren().add(repair);
            unitPanelPane.getChildren().add(remove);
        } else {
            Circle foundCity = new Circle(25, new ImagePattern(UnitController.getActionImage("foundCity")));
            foundCity.setOnMouseClicked(mouseEvent -> {
                UnitController.foundCity((Settler) unit);
                System.out.println("yoo");
            });
            unitPanelPane.getChildren().add(foundCity);
        }
    }

    public void setUnitPanelTexts(Unit unit) {
        String type = (unit instanceof CombatUnit ? "combatUnit" : "nonCombatUnit");
        ((Text) MapController.getTileByCoordinates(unit.getCurrentX(), unit.getCurrentY()).getHex()
                .getUnitGroups().get(type).getChildren().get(1)).setText(unit.getUnitState().getName());
        unitPanelCircle.setFill(new ImagePattern(unit.getUnitType().getLogoImage()));
        unitPanelNameText.setText(unit.getName());
        unitPanelMPText.setText("MP : " + unit.getMovementPoint());
        unitPanelCSText.setText("CS : ");
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
