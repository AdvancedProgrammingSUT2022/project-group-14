package views;

import application.App;
import controllers.*;
import enums.Improvements;
import enums.units.CombatType;
import enums.units.UnitStates;
import enums.units.UnitTypes;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.Civilization;
import models.tiles.Hex;
import models.tiles.Tile;
import models.units.*;

import java.util.Objects;

public class GamePageController {
    public static String infoPanelName;
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
    private MenuButton infoPanelsMenuButton;
    @FXML
    private Text yearText;
    @FXML
    private Circle settingsCircle;
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
        initTimeLine();
        initHexes();
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
        infoPanelsMenuButton.getItems().add(new MenuItem("UnitPanel"));
        infoPanelsMenuButton.getItems().add(new MenuItem("CityPanel"));
        infoPanelsMenuButton.getItems().add(new MenuItem("DemographicPanel"));
        infoPanelsMenuButton.getItems().add(new MenuItem("NotificationsPanel"));
        infoPanelsMenuButton.getItems().add(new MenuItem("MilitaryPanel"));
        infoPanelsMenuButton.getItems().add(new MenuItem("EconomicStatusPanel"));
        for (MenuItem item : infoPanelsMenuButton.getItems()) {
            item.setOnAction(actionEvent -> {
                infoPanelName = item.getText();
                App.changeScene("infoPanelPage");
            });
        }
        settingsCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/settings.png")).toString())));
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
            if (!(unitPanelPane.getChildren().get(i) instanceof ChoiceBox<?>)) {
                unitPanelPane.getChildren().get(i).setLayoutX(unitPanelPane.getChildren().get(i - 2).getLayoutX() + 62);
                unitPanelPane.getChildren().get(i).setLayoutY(unitPanelPane.getChildren().get(i - 2).getLayoutY());
            }
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
        Circle pillage = new Circle(25, new ImagePattern(UnitController.getActionImage("pillage")));
        pillage.setOnMouseClicked(mouseEvent -> UnitController.pillage(WorldController.getSelectedCombatUnit().getCurrentX(), WorldController.getSelectedCombatUnit().getCurrentY()));
        if (unit instanceof Ranged) {
            Circle setupRanged = new Circle(25, new ImagePattern(UnitController.getActionImage("setupRanged")));
            setupRanged.setOnMouseClicked(mouseEvent -> UnitController.setupRangedUnit(unit, WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY()));
            unitPanelPane.getChildren().add(setupRanged);
        }
        unitPanelPane.getChildren().add(alert);
        unitPanelPane.getChildren().add(fortify);
        unitPanelPane.getChildren().add(fortifyTillHealed);
        unitPanelPane.getChildren().add(garrison);
        unitPanelPane.getChildren().add(pillage);
    }

    public void initNonCombatUnitActions(NonCombatUnit unit) {
        if (unit.getUnitType() == UnitTypes.WORKER) {
            ChoiceBox<String> buildChoiceBox = new ChoiceBox<>();
            buildChoiceBox.setValue("Build");
            buildChoiceBox.getItems().addAll(TileController.getAvailableImprovements((Worker) unit));
            buildChoiceBox.setLayoutX(265);
            buildChoiceBox.setLayoutY(145);
            buildChoiceBox.setPrefWidth(74);
            Circle build = new Circle(25, new ImagePattern(UnitController.getActionImage("working")));
            build.setOnMouseClicked(mouseEvent -> {
                switch (buildChoiceBox.getValue()) {
                    case "road":
                        UnitController.buildRoad((Worker) unit);
                        break;
                    case "railRoad":
                        UnitController.buildRailRoad((Worker) unit);
                        break;
                    case "Build":
                        break;
                    default:
                        UnitController.buildImprovement((Worker) unit, Improvements.getImprovementByName(buildChoiceBox.getValue()));
                        break;
                }
            });
            ChoiceBox<String> removeChoiceBox = new ChoiceBox<>();
            removeChoiceBox.setValue("Remove");
            removeChoiceBox.getItems().addAll(TileController.getRemovableFeatures((Worker) unit));
            removeChoiceBox.setLayoutX(350);
            removeChoiceBox.setLayoutY(145);
            removeChoiceBox.setPrefWidth(74);
            Circle remove = new Circle(25, new ImagePattern(UnitController.getActionImage("remove")));
            remove.setOnMouseClicked(mouseEvent -> {
                switch (removeChoiceBox.getValue()) {
                    case "Routes" -> UnitController.removeRouteFromTile((Worker) unit);
                    case "Jungle" -> UnitController.removeJungleFromTile((Worker) unit);
                    case "Forest" -> UnitController.removeForestFromTile((Worker) unit);
                    case "Marsh" -> UnitController.removeMarshFromTile((Worker) unit);
                }
            });
            Circle repair = new Circle(25, new ImagePattern(UnitController.getActionImage("repair")));
            repair.setOnMouseClicked(mouseEvent -> {
                UnitController.repairTile((Worker) unit);
            });
            unitPanelPane.getChildren().add(build);
            unitPanelPane.getChildren().add(buildChoiceBox);
            unitPanelPane.getChildren().add(remove);
            unitPanelPane.getChildren().add(removeChoiceBox);
            unitPanelPane.getChildren().add(repair);
        } else {
            Circle foundCity = new Circle(25, new ImagePattern(UnitController.getActionImage("foundCity")));
            foundCity.setOnMouseClicked(mouseEvent -> {
                UnitController.foundCity((Settler) unit);
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
        unitPanelCSText.setText("CS : " + String.valueOf(unit.getUnitType().getCombatStrength() + unit.getUnitType().getRangedCombatStrength()));
    }

    public void nextTurnButtonClicked(MouseEvent mouseEvent) {
        if (WorldController.nextTurnImpossible() == null) {
            WorldController.nextTurn();
            yearText.setText(String.valueOf(WorldController.getWorld().getYear()));
            unitPanelPane.setVisible(false);
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
