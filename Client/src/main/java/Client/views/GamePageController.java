package Client.views;

import Client.application.App;

import Client.enums.Improvements;
import Client.enums.Technologies;
import Client.enums.units.CombatType;
import Client.enums.units.UnitStates;
import Client.enums.units.UnitTypes;
import Client.models.City;
import Client.models.Civilization;
import Client.models.units.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Objects;

public class GamePageController {
//    public static boolean stopTimeline;
//    public static String infoPanelName;
//    public static boolean showCityOptions;
//    public static City city;
//    public static CombatUnit combatUnit;
//    @FXML
//    private AnchorPane mainPane;
//    @FXML
//    private AnchorPane hexPane;
//    @FXML
//    private Circle goldCircle;
//    @FXML
//    private Circle happinessCircle;
//    @FXML
//    private Circle scienceCircle;
//    @FXML
//    private Circle techCircle;
//    @FXML
//    private Text goldText;
//    @FXML
//    private Text happinessText;
//    @FXML
//    private Text scienceText;
//    @FXML
//    private ChoiceBox<String> infoPanelsBox;
//    @FXML
//    private Text yearText;
//    @FXML
//    private Circle settingsCircle;
//    @FXML
//    private Text techText;
//    @FXML
//    private Text cheatCodeText;
//    @FXML
//    private TextArea cheatCodeArea;
//    @FXML
//    public ScrollPane researchPanelScrollPane;
//    @FXML
//    public AnchorPane researchPanelPane;
//    @FXML
//    private AnchorPane unitPanelPane;
//    @FXML
//    private Circle unitPanelCircle;
//    @FXML
//    private Text unitPanelNameText;
//    @FXML
//    private Text unitPanelMPText;
//    @FXML
//    private Text unitPanelCSText;
//    @FXML
//    private AnchorPane cityOptionsPane;
//    private Timeline timeline;
//
//    public void initialize() {
//        cityOptionsPane.setVisible(false);
//        initNavBar();
//        initTimeLine();
//        initResearchPanel();
//        initHexes();
//        mainPane.setOnKeyReleased(keyEvent -> {
//            if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getCode().getName().equals("C")) {
//                cheatCodeArea.setVisible(!cheatCodeArea.isVisible());
//                cheatCodeText.setVisible(!cheatCodeText.isVisible());
//            }
//        });
//        timeline.play();
//    }
//
//
//    private void initNavBar() {
//        goldCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/goldLogo.png")).toString())));
//        happinessCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/happinessLogo.png")).toString())));
//        scienceCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/scienceLogo.png")).toString())));
//        goldText.setText("" + WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getGold());
//        goldText.setFill(Color.GOLD);
//        happinessText.setText("" + WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getHappiness());
//        happinessText.setFill(Color.rgb(17, 140, 33));
//        scienceText.setText("" + WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getScience());
//        scienceText.setFill(Color.rgb(7, 146, 169));
//        infoPanelsBox.setValue("InfoPanels");
//        infoPanelsBox.getItems().addAll("UnitPanel", "CityPanel", "DemographicPanel", "NotificationsPanel", "MilitaryPanel", "EconomicStatusPanel");
//        settingsCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/settings.png")).toString())));
//        cheatCodeArea.setVisible(false);
//        cheatCodeText.setVisible(false);
//    }
//
//    public void initHexes() {
//        for (int i = 0; i < MapController.getWidth(); i++) {
//            for (int j = 0; j < MapController.getHeight(); j++) {
//                Hex hex = MapController.getTileByCoordinates(i, j).getHex();
//                hexPane.getChildren().add(hex.getGroup());
//                hex.updateHex();
//            }
//        }
//    }
//
//    public void initResearchPanel() {
//        researchPanelScrollPane.setVisible(false);
//        researchPanelPane.getChildren().remove(1, researchPanelPane.getChildren().size());
//        int i = 1;
//        for (Technologies availableTechnology : CivilizationController.getAvailableTechnologies(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()))) {
//            if (WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCurrentTechnology() != availableTechnology) {
//                researchPanelPane.getChildren().add(availableTechnology.getTechnologyGroup(50, i * 90));
//                i++;
//            }
//        }
//    }
//
//    public void initTimeLine() {
//        unitPanelPane.setVisible(false);
//        techCircle.setVisible(false);
//        techText.setText("");
//        timeline = new Timeline(new KeyFrame(Duration.millis(100), actionEvent -> {
//            if (stopTimeline) timeline.stop();
//            checkUnitPanelUpdate();
//            checkTechnologyPanelUpdate();
//            if (showCityOptions && !cityOptionsPane.isVisible()) {
//                cityOptionsPane.setVisible(true);
//            } else if (!showCityOptions && cityOptionsPane.isVisible()) {
//                cityOptionsPane.setVisible(false);
//            }
//        }));
//        timeline.setCycleCount(-1);
//    }
//
//    public void checkUnitPanelUpdate() {
//        if (stopTimeline)
//            return;
//        if (WorldController.getSelectedCombatUnit() != null) {
//            if (!unitPanelPane.isVisible() || UnitTypes.valueOf(unitPanelNameText.getText().toUpperCase()).getCombatType() == CombatType.NON_COMBAT) {
//                unitPanelPane.setVisible(true);
//                setUnitPanelInfo(WorldController.getSelectedCombatUnit());
//            } else {
//                setUnitPanelTexts(WorldController.getSelectedCombatUnit());
//            }
//        } else if (WorldController.getSelectedNonCombatUnit() != null) {
//            if (!unitPanelPane.isVisible() || UnitTypes.valueOf(unitPanelNameText.getText().toUpperCase()).getCombatType() != CombatType.NON_COMBAT) {
//                unitPanelPane.setVisible(true);
//                setUnitPanelInfo(WorldController.getSelectedNonCombatUnit());
//            } else {
//                setUnitPanelTexts(WorldController.getSelectedNonCombatUnit());
//            }
//            //TODO WHAT'S THE POINT OF THIS IF AND THE FUNCTION UNIT IS NOT SELECTED??
//        } else if (WorldController.unitIsNotSelected()) {
//            unitPanelPane.setVisible(false);
//        }
//    }
//
//    public void checkTechnologyPanelUpdate() {
//        if (stopTimeline)
//            return;
//        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
//        happinessText.setText(currentCivilization.getHappiness() + "");
//        goldText.setText(currentCivilization.getGold() + "");
//        scienceText.setText(currentCivilization.getScience() + "");
//        if (currentCivilization.getCurrentTechnology() != null && !techCircle.isVisible()) {
//            techCircle.setVisible(true);
//            techCircle.setFill(new ImagePattern(currentCivilization.getCurrentTechnology().getImage()));
//            techText.setText(currentCivilization.getCurrentTechnology().getName());
//            initResearchPanel();
//        } else if (currentCivilization.getCurrentTechnology() == null && techCircle.isVisible()) {
//            techCircle.setVisible(false);
//            techText.setText("");
//        }
//    }
//
//    public void setUnitPanelInfo(Unit unit) {
//        if (unitPanelPane.getChildren().size() > 11)
//            unitPanelPane.getChildren().subList(11, unitPanelPane.getChildren().size()).clear();
//        initCommonActions(unit);
//        if (unit instanceof CombatUnit) {
//            initCombatUnitActions((CombatUnit) unit);
//        } else {
//            initNonCombatUnitActions((NonCombatUnit) unit);
//        }
//        for (int i = 11; i < unitPanelPane.getChildren().size(); i++) {
//            if (!(unitPanelPane.getChildren().get(i) instanceof ChoiceBox<?>)) {
//                unitPanelPane.getChildren().get(i).setLayoutX(unitPanelPane.getChildren().get(i - 2).getLayoutX() + 62);
//                unitPanelPane.getChildren().get(i).setLayoutY(unitPanelPane.getChildren().get(i - 2).getLayoutY());
//            }
//            unitPanelPane.getChildren().get(i).setCursor(Cursor.HAND);
//        }
//        setUnitPanelTexts(unit);
//    }
//
//    public void initCommonActions(Unit unit) {
//        ((Circle) unitPanelPane.getChildren().get(7)).setFill(new ImagePattern(UnitController.getActionImage("move")));
//        unitPanelPane.getChildren().get(7).setCursor(Cursor.HAND);
//        unitPanelPane.getChildren().get(7).setOnMouseClicked(mouseEvent -> {
//            if (WorldController.getSelectedTile() != null) {
//                UnitController.setUnitDestinationCoordinates(unit, WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY());
//                MoveController.moveUnitToDestination(unit);
//            }
//        });
//        ((Circle) unitPanelPane.getChildren().get(8)).setFill(new ImagePattern(UnitController.getActionImage("delete")));
//        unitPanelPane.getChildren().get(8).setOnMouseClicked(mouseEvent -> UnitController.delete(unit));
//        unitPanelPane.getChildren().get(8).setCursor(Cursor.HAND);
//        ((Circle) unitPanelPane.getChildren().get(9)).setFill(new ImagePattern(UnitController.getActionImage("sleep")));
//        unitPanelPane.getChildren().get(9).setOnMouseClicked(mouseEvent -> unit.setUnitState(UnitStates.SLEEP));
//        unitPanelPane.getChildren().get(9).setCursor(Cursor.HAND);
//        ((Circle) unitPanelPane.getChildren().get(10)).setFill(new ImagePattern(UnitController.getActionImage("wake")));
//        unitPanelPane.getChildren().get(10).setOnMouseClicked(mouseEvent -> unit.setUnitState(UnitStates.WAKE));
//        unitPanelPane.getChildren().get(10).setCursor(Cursor.HAND);
//    }
//
//    public void initCombatUnitActions(CombatUnit unit) {
//        Circle alert = new Circle(25, new ImagePattern(UnitController.getActionImage("alert")));
//        alert.setOnMouseClicked(mouseEvent -> unit.setUnitState(UnitStates.ALERT));
//        Circle fortify = new Circle(25, new ImagePattern(UnitController.getActionImage("fortify")));
//        fortify.setOnMouseClicked(mouseEvent -> unit.setUnitState(UnitStates.FORTIFY));
//        Circle fortifyTillHealed = new Circle(25, new ImagePattern(UnitController.getActionImage("fortifyTillHealed")));
//        fortifyTillHealed.setOnMouseClicked(mouseEvent -> unit.setUnitState(UnitStates.FORTIFY_TILL_HEALED));
//        Circle garrison = new Circle(25, new ImagePattern(UnitController.getActionImage("garrison")));
//        garrison.setOnMouseClicked(mouseEvent -> UnitController.garrisonCity(unit));
//        Circle pillage = new Circle(25, new ImagePattern(UnitController.getActionImage("pillage")));
//        pillage.setOnMouseClicked(mouseEvent -> UnitController.pillage(WorldController.getSelectedCombatUnit().getCurrentX(), WorldController.getSelectedCombatUnit().getCurrentY()));
//        Circle attack = new Circle(25, new ImagePattern(UnitController.getActionImage("attack")));
//        attack.setOnMouseClicked(mouseEvent -> {
//            if (WorldController.getSelectedTile() != null)
//                WarController.combatUnitAttacksTile(WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY(), WorldController.getSelectedCombatUnit());
//        });
//        if (unit instanceof Ranged) {
//            Circle setupRanged = new Circle(25, new ImagePattern(UnitController.getActionImage("setupRanged")));
//            setupRanged.setOnMouseClicked(mouseEvent -> UnitController.setupRangedUnit(unit, WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY()));
//            unitPanelPane.getChildren().add(setupRanged);
//        }
//        unitPanelPane.getChildren().add(alert);
//        unitPanelPane.getChildren().add(fortify);
//        unitPanelPane.getChildren().add(fortifyTillHealed);
//        unitPanelPane.getChildren().add(garrison);
//        unitPanelPane.getChildren().add(pillage);
//        unitPanelPane.getChildren().add(attack);
//    }
//
//    public void initNonCombatUnitActions(NonCombatUnit unit) {
//        if (unit.getUnitType() == UnitTypes.WORKER) {
//            ChoiceBox<String> buildChoiceBox = new ChoiceBox<>();
//            buildChoiceBox.setValue("Build");
//            buildChoiceBox.getItems().addAll(TileController.getAvailableImprovements((Worker) unit));
//            buildChoiceBox.setLayoutX(265);
//            buildChoiceBox.setLayoutY(145);
//            buildChoiceBox.setPrefWidth(74);
//            Circle build = new Circle(25, new ImagePattern(UnitController.getActionImage("working")));
//            build.setOnMouseClicked(mouseEvent -> {
//                switch (buildChoiceBox.getValue()) {
//                    case "road":
//                        UnitController.buildRoad((Worker) unit);
//                        break;
//                    case "railRoad":
//                        UnitController.buildRailRoad((Worker) unit);
//                        break;
//                    case "Build":
//                        break;
//                    default:
//                        UnitController.buildImprovement((Worker) unit, Improvements.getImprovementByName(buildChoiceBox.getValue()));
//                        break;
//                }
//            });
//            ChoiceBox<String> removeChoiceBox = new ChoiceBox<>();
//            removeChoiceBox.setValue("Remove");
//            removeChoiceBox.getItems().addAll(TileController.getRemovableFeatures((Worker) unit));
//            removeChoiceBox.setLayoutX(350);
//            removeChoiceBox.setLayoutY(145);
//            removeChoiceBox.setPrefWidth(74);
//            Circle remove = new Circle(25, new ImagePattern(UnitController.getActionImage("remove")));
//            remove.setOnMouseClicked(mouseEvent -> {
//                switch (removeChoiceBox.getValue()) {
//                    case "Routes" -> UnitController.removeRouteFromTile((Worker) unit);
//                    case "Jungle" -> UnitController.removeJungleFromTile((Worker) unit);
//                    case "Forest" -> UnitController.removeForestFromTile((Worker) unit);
//                    case "Marsh" -> UnitController.removeMarshFromTile((Worker) unit);
//                }
//            });
//            Circle repair = new Circle(25, new ImagePattern(UnitController.getActionImage("repair")));
//            repair.setOnMouseClicked(mouseEvent -> {
//                UnitController.repairTile((Worker) unit);
//            });
//            unitPanelPane.getChildren().add(build);
//            unitPanelPane.getChildren().add(buildChoiceBox);
//            unitPanelPane.getChildren().add(remove);
//            unitPanelPane.getChildren().add(removeChoiceBox);
//            unitPanelPane.getChildren().add(repair);
//        } else {
//            Circle foundCity = new Circle(25, new ImagePattern(UnitController.getActionImage("foundCity")));
//            foundCity.setOnMouseClicked(mouseEvent -> {
//                UnitController.foundCity((Settler) unit);
//            });
//            unitPanelPane.getChildren().add(foundCity);
//        }
//    }
//
//    public void setUnitPanelTexts(Unit unit) {
//        String type = (unit instanceof CombatUnit ? "combatUnit" : "nonCombatUnit");
//        ((Text) MapController.getTileByCoordinates(unit.getCurrentX(), unit.getCurrentY()).getHex()
//                .getUnitGroups().get(type).getChildren().get(1)).setText(unit.getUnitState().getName());
//        unitPanelCircle.setFill(new ImagePattern(unit.getUnitType().getLogoImage()));
//        unitPanelNameText.setText(unit.getName());
//        unitPanelMPText.setText("MP : " + unit.getMovementPoint());
//        if (unit instanceof CombatUnit) {
//            unitPanelCSText.setText("CS : " + String.valueOf(unit.getUnitType().getCombatStrength() + unit.getUnitType().getRangedCombatStrength()));
//        } else if (unit instanceof Worker) {
//            unitPanelCSText.setText("TLW : " + ((Worker) unit).getTurnsLeftToWork());
//        }
//
//    }
//
//    public void nextTurnButtonClicked(MouseEvent mouseEvent) {
//        if (WorldController.nextTurnImpossible() == null) {
//            WorldController.nextTurn();
//            yearText.setText(WorldController.getWorld().getYear() < 0 ? String.valueOf(-WorldController.getWorld().getYear()) + " BC" : String.valueOf(WorldController.getWorld().getYear()));
//            unitPanelPane.setVisible(false);
//        }
//    }
//
//    public void cheatCodeAreaTyped(KeyEvent keyEvent) {
//        if (keyEvent.getCode().getName().equals("Enter")) {
//            String text = cheatCodeArea.getText().substring(0, cheatCodeArea.getText().length() - 1);
//            String command = cheatCodeArea.getText().substring(text.lastIndexOf("\n") + 1, cheatCodeArea.getText().length() - 1);
//            if (command.equals("clear")) {
//                cheatCodeArea.clear();
//            } else {
//                GameCommandsValidation.checkCommands(command);
//            }
//        }
//    }
//
//    public void menuButtonCLicked(MouseEvent mouseEvent) {
//        timeline.stop();
//        App.changeScene("startGameMenuPage");
//    }
//
//    public void showResearchPanel(MouseEvent mouseEvent) {
//        researchPanelScrollPane.setVisible(!researchPanelScrollPane.isVisible());
//    }
//
//    public void techTreeButtonClicked(MouseEvent mouseEvent) {
//        System.out.println("hello");
//        //TODO goto techTree fxml
//    }
//
//    public void gotoPanelButtonClicked(MouseEvent mouseEvent) {
//        if (!infoPanelsBox.getValue().equals("InfoPanels")) {
//            infoPanelName = infoPanelsBox.getValue();
//            App.changeScene("infoPanelPage");
//        }
//    }
//
//    public static void setCityOptions(City city, CombatUnit combatUnit) {
//        showCityOptions = true;
//        GamePageController.city = city;
//        GamePageController.combatUnit = combatUnit;
//    }
//
//    public void conquerButtonClicked(MouseEvent mouseEvent) {
//        CityController.conquerCity(city, combatUnit);
//        showCityOptions = false;
//    }
//
//    public void destroyButtonClicked(MouseEvent mouseEvent) {
//        CityController.destroyCity(city, combatUnit);
//        showCityOptions = false;
//    }
}
