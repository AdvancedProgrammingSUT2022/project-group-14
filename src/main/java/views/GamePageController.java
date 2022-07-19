package views;

import application.App;
import controllers.MapController;
import controllers.MoveController;
import controllers.UnitController;
import controllers.WorldController;
import enums.Technologies;
import enums.units.UnitTypes;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import models.Civilization;
import models.Technology;
import models.tiles.Hex;
import models.tiles.Tile;
import models.units.CombatUnit;
import models.units.Ranged;
import models.units.Unit;

import java.util.Objects;

public class GamePageController {
    @FXML
    public ScrollPane researchPanel;
    @FXML
    public AnchorPane researchPanelPane;
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
        initResearchPanel();
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

    public void initResearchPanel() {
        researchPanel.setVisible(false); // this isn't related to timeline
        Technology technology = new Technology(Technologies.IRON_WORKING , 50 , 90);
        researchPanelPane.getChildren().add(technology.getGroup());

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
            if (!unitPanelPane.isVisible()) {
                unitPanelPane.setVisible(true);
                setUnitPanelInfo(WorldController.getSelectedCombatUnit());
            } else {
                unitPanelMPText.setText("MP : " + WorldController.getSelectedCombatUnit().getMovementPoint());
                setUnitPanelInfo(WorldController.getSelectedCombatUnit());
            }
        } else if (WorldController.getSelectedNonCombatUnit() != null) {
            if (!unitPanelPane.isVisible()) {
                unitPanelPane.setVisible(true);
                setUnitPanelInfo(WorldController.getSelectedNonCombatUnit());
            } else {
                unitPanelMPText.setText("MP : " + WorldController.getSelectedNonCombatUnit().getMovementPoint());
                setUnitPanelInfo(WorldController.getSelectedNonCombatUnit());
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
            unitPanelPane.getChildren().add(new Circle(25, new ImagePattern(UnitController.getActionImage("alert"))));
            unitPanelPane.getChildren().add(new Circle(25, new ImagePattern(UnitController.getActionImage("fortify"))));
            unitPanelPane.getChildren().add(new Circle(25, new ImagePattern(UnitController.getActionImage("fortifyTillHealed"))));
            unitPanelPane.getChildren().add(new Circle(25, new ImagePattern(UnitController.getActionImage("garrison"))));
            if (unit instanceof Ranged) {
                unitPanelPane.getChildren().add(new Circle(25, new ImagePattern(UnitController.getActionImage("setupRanged"))));
            }
        } else if (unit.getUnitType() == UnitTypes.WORKER) {
            unitPanelPane.getChildren().add(new Circle(25, new ImagePattern(UnitController.getActionImage("working"))));
            unitPanelPane.getChildren().add(new Circle(25, new ImagePattern(UnitController.getActionImage("buildRoad"))));
            unitPanelPane.getChildren().add(new Circle(25, new ImagePattern(UnitController.getActionImage("repair"))));
            unitPanelPane.getChildren().add(new Circle(25, new ImagePattern(UnitController.getActionImage("remove"))));
        } else {
            unitPanelPane.getChildren().add(new Circle(25, new ImagePattern(UnitController.getActionImage("foundCity"))));
        }
        for (int i = 11; i < unitPanelPane.getChildren().size(); i++) {
            unitPanelPane.getChildren().get(i).setLayoutX(unitPanelPane.getChildren().get(i - 2).getLayoutX() + 62);
            unitPanelPane.getChildren().get(i).setLayoutY(unitPanelPane.getChildren().get(i - 2).getLayoutY());
        }
        unitPanelCircle.setFill(new ImagePattern(unit.getUnitType().getLogoImage()));
        unitPanelNameText.setText(unit.getName());
        unitPanelMPText.setText("MP : " + unit.getMovementPoint());
        unitPanelCSText.setText("CS : ");
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
        unitPanelPane.getChildren().get(9).setOnMouseClicked(mouseEvent -> {
            UnitController.sleepUnit(unit);
        });
        unitPanelPane.getChildren().get(9).setCursor(Cursor.HAND);
        ((Circle) unitPanelPane.getChildren().get(10)).setFill(new ImagePattern(UnitController.getActionImage("wake")));
        unitPanelPane.getChildren().get(10).setOnMouseClicked(mouseEvent -> {
            UnitController.wakeUp(unit);
        });
        unitPanelPane.getChildren().get(10).setCursor(Cursor.HAND);
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

    public void showResearchPanel(MouseEvent mouseEvent) {
        researchPanel.setVisible(!researchPanel.isVisible());
    }
}
