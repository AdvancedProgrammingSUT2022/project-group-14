package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.controllers.HexController;
import Client.enums.Improvements;
import Client.enums.QueryRequests;
import Client.enums.Technologies;
import Client.enums.units.CombatType;
import Client.enums.units.UnitTypes;
import Client.models.City;
import Client.models.network.Response;
import Client.models.tiles.Hex;
import Client.models.units.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.*;

import Client.models.units.CombatUnit;
import Client.models.units.NonCombatUnit;
import Client.models.units.Unit;

import java.util.HashMap;
import java.util.Objects;

public class GamePageController {
    public static boolean isMyTurn;
    public static boolean stopTimeline;
    public static String infoPanelName;
    public static boolean showCityOptions;
    public static boolean showDeclareWar;
    public static City city;
    public static CombatUnit combatUnit;
    public static String enemyName;
    @FXML
    private AnchorPane notMyTurnPane;
    @FXML
    private AnchorPane mainPane;
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
    private ChoiceBox<String> infoPanelsBox;
    @FXML
    private Text yearText;
    @FXML
    private Circle settingsCircle;
    @FXML
    private Text techText;
    @FXML
    private Text cheatCodeText;
    @FXML
    private TextArea cheatCodeArea;
    @FXML
    public ScrollPane researchPanelScrollPane;
    @FXML
    public AnchorPane researchPanelPane;
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
    @FXML
    private AnchorPane cityOptionsPane;
    @FXML
    private AnchorPane declareWarOptionsPane;
    private Timeline timeline;

    public void initialize() {
        if (isMyTurn) {
            notMyTurnPane.setVisible(false);
            cityOptionsPane.setVisible(false);
            declareWarOptionsPane.setVisible(false);
            initNavBar();
            initTimeLine();
            initResearchPanel();
            initHexes();
            mainPane.setOnKeyReleased(keyEvent -> {
                if (keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getCode().getName().equals("C")) {
                    cheatCodeArea.setVisible(!cheatCodeArea.isVisible());
                    cheatCodeText.setVisible(!cheatCodeText.isVisible());
                }
            });
            timeline.play();
            yearText.setText(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_YEAR, new HashMap<>())).getParams().get("year"));
        } else {
            notMyTurnPane.setVisible(true);
        }
    }


    private void initNavBar() {
        goldCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/goldLogo.png")).toString())));
        happinessCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/happinessLogo.png")).toString())));
        scienceCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/scienceLogo.png")).toString())));
        goldText.setText(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CIV_GOLD, new HashMap<>())).getParams().get("gold"));
        goldText.setFill(Color.GOLD);
        happinessText.setText(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CIV_HAPPINESS, new HashMap<>())).getParams().get("happiness"));
        happinessText.setFill(Color.rgb(17, 140, 33));
        scienceText.setText(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CIV_SCIENCE, new HashMap<>())).getParams().get("science"));
        scienceText.setFill(Color.rgb(7, 146, 169));
        infoPanelsBox.setValue("InfoPanels");
        infoPanelsBox.getItems().addAll("UnitPanel", "CityPanel", "DemographicPanel", "NotificationsPanel", "MilitaryPanel", "EconomicStatusPanel", "DiplomacyPanel");
        settingsCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/settings.png")).toString())));
        cheatCodeArea.setVisible(false);
        cheatCodeText.setVisible(false);
    }

    public void initHexes() {
        for (int i = 0; i < HexController.getWidth(); i++) {
            for (int j = 0; j < HexController.getHeight(); j++) {
                Hex hex = HexController.getHexOfTheGivenCoordination(i, j);
                hexPane.getChildren().add(hex.getGroup());
                HexController.updateHex(i, j);
            }
        }
    }

    public void initResearchPanel() {
        researchPanelScrollPane.setVisible(false);
        researchPanelPane.getChildren().remove(1, researchPanelPane.getChildren().size());
        int i = 1;
        HashSet<Technologies> technologies = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_AVAILABLE_TECHNOLOGIES, new HashMap<>())).getParams().get("technologies"), new TypeToken<Set<Technologies>>() {
        }.getType());
        for (Technologies availableTechnology : technologies) {
            researchPanelPane.getChildren().add(availableTechnology.getTechnologyGroup(50, i * 90));
            i++;
        }
    }

    public void initTimeLine() {
        unitPanelPane.setVisible(false);
        techCircle.setVisible(false);
        techText.setText("");
        timeline = new Timeline(new KeyFrame(Duration.millis(250), actionEvent -> {
            if (stopTimeline) timeline.stop();
            checkUnitPanelUpdate();
            checkTechnologyPanelUpdate();
            if (showCityOptions && !cityOptionsPane.isVisible()) {
                cityOptionsPane.setVisible(true);
            } else if (!showCityOptions && cityOptionsPane.isVisible()) {
                cityOptionsPane.setVisible(false);
            }
            if (showDeclareWar && !declareWarOptionsPane.isVisible()) {
                declareWarOptionsPane.setVisible(true);
            } else if (!showDeclareWar && declareWarOptionsPane.isVisible()) {
                declareWarOptionsPane.setVisible(false);
            }
        }));
        timeline.setCycleCount(-1);
    }

    public void checkUnitPanelUpdate() {
        if (stopTimeline)
            return;
        Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.UNIT_PANEL_UPDATE, new HashMap<>());
        switch (Objects.requireNonNull(response).getQueryResponse()) {
            case COMBAT_UNIT_SELECTED -> {
                if (!unitPanelPane.isVisible() || UnitTypes.valueOf(unitPanelNameText.getText().toUpperCase()).getCombatType() == CombatType.NON_COMBAT) {
                    unitPanelPane.setVisible(true);
                    setUnitPanelInfo(new Gson().fromJson(response.getParams().get("unit"), CombatUnit.class));
                }
            }
            case NONCOMBAT_UNIT_SELECTED -> {
                if (!unitPanelPane.isVisible() || UnitTypes.valueOf(unitPanelNameText.getText().toUpperCase()).getCombatType() != CombatType.NON_COMBAT) {
                    unitPanelPane.setVisible(true);
                    setUnitPanelInfo(new Gson().fromJson(response.getParams().get("unit"), NonCombatUnit.class));
                }
            }
            case UNIT_NOT_SELECTED -> unitPanelPane.setVisible(false);
        }
    }

    public void checkTechnologyPanelUpdate() {
        if (stopTimeline)
            return;
        happinessText.setText(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CIV_HAPPINESS, new HashMap<>())).getParams().get("happiness"));
        goldText.setText(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CIV_GOLD, new HashMap<>())).getParams().get("gold"));
        scienceText.setText(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CIV_SCIENCE, new HashMap<>())).getParams().get("science"));
        Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.TECH_PANEL_UPDATE, new HashMap<>() {{
            put("boolean", String.valueOf(techCircle.isVisible()));
        }});
        switch (Objects.requireNonNull(response).getQueryResponse()) {
            case SET_TECH_VISIBLE -> {
                techCircle.setVisible(true);
                techCircle.setFill(new ImagePattern(new Gson().fromJson(response.getParams().get("technology"), Technologies.class).getImage()));
                techText.setText(new Gson().fromJson(response.getParams().get("technology"), Technologies.class).getName());
                initResearchPanel();
            }
            case SET_TECH_INVISIBLE -> {
                techCircle.setVisible(false);
                techText.setText("");
            }
        }
    }

    public void setUnitPanelInfo(Unit unit) {
        if (unitPanelPane.getChildren().size() > 11)
            unitPanelPane.getChildren().subList(11, unitPanelPane.getChildren().size()).clear();
        initCommonActions();
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

    public void initCommonActions() {
        ((Circle) unitPanelPane.getChildren().get(7)).setFill(new ImagePattern(getActionImage("move")));
        unitPanelPane.getChildren().get(7).setCursor(Cursor.HAND);
        unitPanelPane.getChildren().get(7).setOnMouseClicked(mouseEvent -> {
            unitPanelPane.setVisible(false);
            ClientSocketController.sendRequestAndGetResponse(QueryRequests.MOVE_ACTION, new HashMap<>());
        });
        ((Circle) unitPanelPane.getChildren().get(8)).setFill(new ImagePattern(getActionImage("delete")));
        unitPanelPane.getChildren().get(8).setOnMouseClicked(mouseEvent -> ClientSocketController.sendRequestAndGetResponse(QueryRequests.DELETE_ACTION, new HashMap<>()));
        unitPanelPane.getChildren().get(8).setCursor(Cursor.HAND);
        ((Circle) unitPanelPane.getChildren().get(9)).setFill(new ImagePattern(getActionImage("sleep")));
        unitPanelPane.getChildren().get(9).setOnMouseClicked(mouseEvent -> {
            unitPanelPane.setVisible(false);
            ClientSocketController.sendRequestAndGetResponse(QueryRequests.SLEEP_ACTION, new HashMap<>());
        });
        unitPanelPane.getChildren().get(9).setCursor(Cursor.HAND);
        ((Circle) unitPanelPane.getChildren().get(10)).setFill(new ImagePattern(getActionImage("wake")));
        unitPanelPane.getChildren().get(10).setOnMouseClicked(mouseEvent -> {
            unitPanelPane.setVisible(false);
            ClientSocketController.sendRequestAndGetResponse(QueryRequests.WAKE_ACTION, new HashMap<>());
        });
        unitPanelPane.getChildren().get(10).setCursor(Cursor.HAND);
    }

    public void initCombatUnitActions(CombatUnit unit) {
        Circle alert = new Circle(25, new ImagePattern(getActionImage("alert")));
        alert.setOnMouseClicked(mouseEvent -> {
            unitPanelPane.setVisible(false);
            ClientSocketController.sendRequestAndGetResponse(QueryRequests.ALERT_ACTION, new HashMap<>());
        });
        Circle fortify = new Circle(25, new ImagePattern(getActionImage("fortify")));
        fortify.setOnMouseClicked(mouseEvent -> {
            unitPanelPane.setVisible(false);
            ClientSocketController.sendRequestAndGetResponse(QueryRequests.FORTIFY_ACTION, new HashMap<>());
        });
        Circle fortifyTillHealed = new Circle(25, new ImagePattern(getActionImage("fortifyTillHealed")));
        fortifyTillHealed.setOnMouseClicked(mouseEvent -> {
            unitPanelPane.setVisible(false);
            ClientSocketController.sendRequestAndGetResponse(QueryRequests.FORTIFY_TILL_HEALED_ACTION, new HashMap<>());
        });
        Circle garrison = new Circle(25, new ImagePattern(getActionImage("garrison")));
        garrison.setOnMouseClicked(mouseEvent -> {
            unitPanelPane.setVisible(false);
            ClientSocketController.sendRequestAndGetResponse(QueryRequests.GARRISON_ACTION, new HashMap<>());
        });
        Circle pillage = new Circle(25, new ImagePattern(getActionImage("pillage")));
        pillage.setOnMouseClicked(mouseEvent -> {
            unitPanelPane.setVisible(false);
            ClientSocketController.sendRequestAndGetResponse(QueryRequests.PILLAGE_ACTION, new HashMap<>());
        });
        Circle attack = new Circle(25, new ImagePattern(getActionImage("attack")));
        attack.setOnMouseClicked(mouseEvent -> {
            unitPanelPane.setVisible(false);
            ClientSocketController.sendRequestAndGetResponse(QueryRequests.ATTACK_ACTION, new HashMap<>());
        });
        if (unit instanceof Ranged) {
            Circle setupRanged = new Circle(25, new ImagePattern(getActionImage("setupRanged")));
            setupRanged.setOnMouseClicked(mouseEvent -> {
                unitPanelPane.setVisible(false);
                ClientSocketController.sendRequestAndGetResponse(QueryRequests.SETUP_RANGED_ACTION, new HashMap<>());
            });
            unitPanelPane.getChildren().add(setupRanged);
        }
        unitPanelPane.getChildren().add(alert);
        unitPanelPane.getChildren().add(fortify);
        unitPanelPane.getChildren().add(fortifyTillHealed);
        unitPanelPane.getChildren().add(garrison);
        unitPanelPane.getChildren().add(pillage);
        unitPanelPane.getChildren().add(attack);
    }

    public void initNonCombatUnitActions(NonCombatUnit unit) {
        if (unit.getUnitType() == UnitTypes.WORKER) {
            ChoiceBox<String> buildChoiceBox = new ChoiceBox<>();
            buildChoiceBox.setValue("Build");
            ArrayList<String> availableImprovements = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_AVAILABLE_IMPROVEMENTS_FOR_WORKER, new HashMap<>()))
                    .getParams().get("improvements"), new TypeToken<List<String>>() {
            }.getType());
            buildChoiceBox.getItems().addAll(availableImprovements);
            buildChoiceBox.setLayoutX(265);
            buildChoiceBox.setLayoutY(145);
            buildChoiceBox.setPrefWidth(74);
            Circle build = new Circle(25, new ImagePattern(getActionImage("working")));
            build.setOnMouseClicked(mouseEvent -> {
                switch (buildChoiceBox.getValue()) {
                    case "road":
                        unitPanelPane.setVisible(false);
                        ClientSocketController.sendRequestAndGetResponse(QueryRequests.ROAD_BUILD, new HashMap<>());
                        break;
                    case "railRoad":
                        unitPanelPane.setVisible(false);
                        ClientSocketController.sendRequestAndGetResponse(QueryRequests.RAILROAD_BUILD, new HashMap<>());
                        break;
                    case "Build":
                        break;
                    default:
                        unitPanelPane.setVisible(false);
                        ClientSocketController.sendRequestAndGetResponse(QueryRequests.IMPROVEMENT_BUILD, new HashMap<>() {{
                            put("improvement", new Gson().toJson(Improvements.getImprovementByName(buildChoiceBox.getValue())));
                        }});
                        break;
                }
            });
            ChoiceBox<String> removeChoiceBox = new ChoiceBox<>();
            removeChoiceBox.setValue("Remove");
            ArrayList<String> removableFeatures = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_REMOVABLE_FEATURES, new HashMap<>()))
                    .getParams().get("features"), new TypeToken<List<String>>() {
            }.getType());
            removeChoiceBox.getItems().addAll(removableFeatures);
            removeChoiceBox.setLayoutX(350);
            removeChoiceBox.setLayoutY(145);
            removeChoiceBox.setPrefWidth(74);
            Circle remove = new Circle(25, new ImagePattern(getActionImage("remove")));
            remove.setOnMouseClicked(mouseEvent -> {
                switch (removeChoiceBox.getValue()) {
                    case "Routes" -> {
                        unitPanelPane.setVisible(false);
                        ClientSocketController.sendRequestAndGetResponse(QueryRequests.ROUTES_REMOVE, new HashMap<>());
                    }
                    case "Jungle" -> {
                        unitPanelPane.setVisible(false);
                        ClientSocketController.sendRequestAndGetResponse(QueryRequests.JUNGLE_REMOVE, new HashMap<>());
                    }
                    case "Forest" -> {
                        unitPanelPane.setVisible(false);
                        ClientSocketController.sendRequestAndGetResponse(QueryRequests.FOREST_REMOVE, new HashMap<>());
                    }
                    case "Marsh" -> {
                        unitPanelPane.setVisible(false);
                        ClientSocketController.sendRequestAndGetResponse(QueryRequests.MARSH_REMOVE, new HashMap<>());
                    }
                }
            });
            Circle repair = new Circle(25, new ImagePattern(getActionImage("repair")));
            repair.setOnMouseClicked(mouseEvent -> {
                unitPanelPane.setVisible(false);
                ClientSocketController.sendRequestAndGetResponse(QueryRequests.REPAIR_TILE, new HashMap<>());
            });
            unitPanelPane.getChildren().add(build);
            unitPanelPane.getChildren().add(buildChoiceBox);
            unitPanelPane.getChildren().add(remove);
            unitPanelPane.getChildren().add(removeChoiceBox);
            unitPanelPane.getChildren().add(repair);
        } else {
            Circle foundCity = new Circle(25, new ImagePattern(getActionImage("foundCity")));
            foundCity.setOnMouseClicked(mouseEvent -> {
                unitPanelPane.setVisible(false);
                ClientSocketController.sendRequestAndGetResponse(QueryRequests.FOUND_CITY, new HashMap<>());
            });
            unitPanelPane.getChildren().add(foundCity);
        }
    }

    public void setUnitPanelTexts(Unit unit) {
        unitPanelCircle.setFill(new ImagePattern(unit.getUnitType().getLogoImage()));
        unitPanelNameText.setText(unit.getName());
        unitPanelMPText.setText("MP : " + unit.getMovementPoint());
        if (unit instanceof CombatUnit) {
            unitPanelCSText.setText("CS : " + String.valueOf(unit.getUnitType().getCombatStrength() + unit.getUnitType().getRangedCombatStrength()));
        } else if (unit instanceof Worker) {
            unitPanelCSText.setText("TLW : " + ((Worker) unit).getTurnsLeftToWork());
        } else {
            unitPanelCSText.setText("");
        }
        String type = (unit instanceof CombatUnit ? "combatUnit" : "nonCombatUnit");
        try {
            ((Text) HexController.getHexOfTheGivenCoordination(unit.getCurrentX(), unit.getCurrentY())
                    .getUnitGroups().get(type).getChildren().get(1)).setText(unit.getUnitState().getName());
        } catch (NullPointerException e) {
            System.err.println(unit.getCurrentX() + " " + unit.getCurrentY() + " -> " + unit.getUnitType() + "  " + type);
        }
    }

    public void nextTurnButtonClicked(MouseEvent mouseEvent) {
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.NEXT_TURN, new HashMap<>());
    }

    public void cheatCodeAreaTyped(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Enter")) {
            String text = cheatCodeArea.getText().substring(0, cheatCodeArea.getText().length() - 1);
            String command = cheatCodeArea.getText().substring(text.lastIndexOf("\n") + 1, cheatCodeArea.getText().length() - 1);
            if (command.equals("clear")) {
                cheatCodeArea.clear();
            } else {
                ClientSocketController.sendRequestAndGetResponse(QueryRequests.CHEAT_COMMAND, new HashMap<>() {{
                    put("command", command);
                }});
                unitPanelPane.setVisible(false);
            }
        }
    }

    public void menuButtonCLicked(MouseEvent mouseEvent) {
        timeline.stop();
        App.changeScene("startGameMenuPage");
    }

    public void showResearchPanel(MouseEvent mouseEvent) {
        researchPanelScrollPane.setVisible(!researchPanelScrollPane.isVisible());
    }

    public void techTreeButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("technologyTree");
    }

    public void gotoPanelButtonClicked(MouseEvent mouseEvent) {
        if (!infoPanelsBox.getValue().equals("InfoPanels")) {
            infoPanelName = infoPanelsBox.getValue();
            App.changeScene("infoPanelPage");
        }
    }

    public static void setCityOptions(City city, CombatUnit combatUnit) {
        showCityOptions = true;
        GamePageController.city = city;
        GamePageController.combatUnit = combatUnit;
    }

    public static void setDeclareWarOptions(String civilizationName) {
        showDeclareWar = true;
        GamePageController.enemyName = civilizationName;
    }

    public void conquerButtonClicked(MouseEvent mouseEvent) {
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.CONQUER_CITY, new HashMap<>() {{
            put("city", new Gson().toJson(city));
            put("combatUnit", new Gson().toJson(combatUnit));
        }});
        showCityOptions = false;
    }

    public void destroyButtonClicked(MouseEvent mouseEvent) {
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.DESTROY_CITY, new HashMap<>() {{
            put("city", new Gson().toJson(city));
            put("combatUnit", new Gson().toJson(combatUnit));
        }});
        showCityOptions = false;
    }

    public static Image getActionImage(String name) {
        return new Image(Objects.requireNonNull(App.class.getResource("/images/units/actions/" + name + ".png")).toString());
    }

    public void declareWarButtonClicked(MouseEvent mouseEvent) {
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.DECLARE_WAR, new HashMap<>() {{
            put("enemyName", GamePageController.enemyName);
        }});
        showDeclareWar = false;
    }

    public void cancelButtonClicked(MouseEvent mouseEvent) {
        showDeclareWar = false;
    }
}
