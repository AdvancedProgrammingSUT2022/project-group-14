package Client.models.tiles;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.controllers.HexController;
import Client.enums.QueryRequests;
import Client.enums.QueryResponses;
import Client.enums.tiles.TileFeatureTypes;
import Client.models.City;
import Client.models.network.Response;
import Client.models.units.CombatUnit;
import Client.models.units.NonCombatUnit;
import Client.models.units.Unit;
import com.google.gson.Gson;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Popup;

import java.util.HashMap;
import java.util.Objects;

public class Hex {
    private final Polygon polygon;
    private final Group group;
    private final Coordination coordination;
    private final double width;
    private final double height;
    private final double verticalSpacing;
    private final double horizontalSpacing;
    private final ImageView cityImage = new ImageView(new Image(Objects.requireNonNull(App.class.getResource("/images/cities/cityCenter.png")).toString()));
    private final HashMap<String, Group> unitGroups = new HashMap<>();
    private final Text coordinationText;
    private final Text infoText;
    private final ColorAdjust colorAdjust = new ColorAdjust();
    private final Popup popup = new Popup();

    public Hex(int x, int y) {
        this.group = new Group();
        this.coordination = new Coordination(x, y);
        this.verticalSpacing = y * 100 + 5;
        this.horizontalSpacing = 5 * Math.sqrt(3) * x * 10 + 5;
        this.width = 200;
        this.height = 100 * Math.sqrt(3);
        this.polygon = new Polygon(setX(5), setY(0), setX(15), setY(0), setX(20), setY(5 * Math.sqrt(3)),
                setX(15), setY(10 * Math.sqrt(3)), setX(5), setY(10 * Math.sqrt(3)), setX(0), setY(5 * Math.sqrt(3)));
        this.coordinationText = new Text(String.valueOf(x + 1) + "," + String.valueOf(y + 1));
        this.coordinationText.setLayoutX(this.getCenterX() - 7 * this.coordinationText.getBoundsInLocal().getWidth() / 10);
        this.coordinationText.setLayoutY(this.getCenterY() - 8);
        this.infoText = new Text();
        this.infoText.setLayoutY(this.getCenterY() - 23);
        setCityEventHandlers();
        this.group.setEffect(this.colorAdjust);
        setGroupEventHandlers();
    }

    public void setGroupEventHandlers() {
        this.group.setCursor(Cursor.HAND);
        this.group.setOnMouseEntered(mouseEvent -> {
            Hex.this.group.toFront();
            if (Hex.this.colorAdjust.getInput() == null)
                Hex.this.colorAdjust.setInput(new DropShadow(25, Color.BLACK));
        });
        this.group.setOnMouseExited(mouseEvent -> {
            if (!(Hex.this.colorAdjust.getInput() instanceof Bloom))
                Hex.this.colorAdjust.setInput(null);
            this.infoText.setText("");
            popup.getContent().clear();
            popup.hide();
        });
        this.group.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.HEX_MOUSE_CLICKED, new HashMap<>() {{
                    put("x", String.valueOf(coordination.getX()));
                    put("y", String.valueOf(coordination.getY()));
                }});
                switch (Objects.requireNonNull(response).getQueryResponse()) {
                    case IGNORE -> {
                    }
                    case RESET_COLOR_ADJUST -> {
                        Hex.this.colorAdjust.setInput(null);
                        setPopup(getInfoPopup(coordination), mouseEvent.getSceneX() + 30, mouseEvent.getSceneY() + 15);
                    }
                    case BLOOM_COLOR_ADJUST -> {
                        Hex.this.colorAdjust.setInput(new Bloom());
                        setPopup(getInfoPopup(coordination), mouseEvent.getSceneX() + 30, mouseEvent.getSceneY() + 15);
                    }
                    case RESET_GIVEN_TILE_COLOR -> {
                        HexController.getHexOfTheGivenCoordination(Integer.parseInt(response.getParams().get("x")), Integer.parseInt(response.getParams().get("y"))).setColorAdjust(null);
                        setPopup(getInfoPopup(coordination), mouseEvent.getSceneX() + 30, mouseEvent.getSceneY() + 15);
                    }
                }
            }
        });
    }

    public static Group getInfoPopup(Coordination coordination) {
        Group group = new Group();
        Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_TILE_INFO, new HashMap<>() {{
            put("x", String.valueOf(coordination.getX()));
            put("y", String.valueOf(coordination.getY()));
        }});
        assert response != null;
        Text text = new Text(response.getParams().get("info"));
        Rectangle rectangle = new Rectangle(text.getBoundsInLocal().getWidth() + 10, text.getBoundsInLocal().getHeight() - 5, Color.WHITE);
        text.setLayoutY(rectangle.getLayoutY() + 15);
        text.setLayoutX(rectangle.getLayoutX() + 5);
        group.getChildren().add(rectangle);
        group.getChildren().add(text);

        return group;
    }

    public void updateHex(Tile chosenTile) {
        Tile[] tile = new Tile[1];
        tile[0] = chosenTile;
        Platform.runLater(() -> {
            Hex.this.group.getChildren().clear();
            this.colorAdjust.setBrightness(0);
            this.unitGroups.clear();
            Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.HEX_VISION_UPDATE, new HashMap<>() {{
                put("x", String.valueOf(coordination.getX()));
                put("y", String.valueOf(coordination.getY()));
            }});
            switch (Objects.requireNonNull(response).getQueryResponse()) {
                case FOG -> {
                    this.colorAdjust.setBrightness(-1);
                    this.polygon.setFill(Color.BLACK);
                    this.group.getChildren().add(polygon);
                    return;
                }
                case REVEALED -> {
                    tile[0] = response.getTile();
                    this.colorAdjust.setBrightness(-0.5);
                }
            }

            if (tile[0].getFeature() != TileFeatureTypes.NULL) {
                this.polygon.setFill(new ImagePattern(tile[0].getFeature().getImage()));
            } else {
                this.polygon.setFill(new ImagePattern(tile[0].getType().getImage()));
            }
            this.group.getChildren().add(this.polygon);
            if (tile[0].getRoadState() == 0) {
                ImageView roadImage = new ImageView(Objects.requireNonNull(App.class.getResource("/images/resources/road.png")).toString());
                roadImage.setFitWidth(30);
                roadImage.setFitHeight(30);
                roadImage.setLayoutX(this.getCenterX() - 60);
                roadImage.setLayoutY(this.getCenterY() - 10);
                this.group.getChildren().add(roadImage);
            } else if (tile[0].getRailRoadState() == 0) {
                ImageView railRoadImage = new ImageView(Objects.requireNonNull(App.class.getResource("/images/resources/railRoad.png")).toString());
                railRoadImage.setFitWidth(30);
                railRoadImage.setFitHeight(30);
                railRoadImage.setLayoutX(this.getCenterX() - 60);
                railRoadImage.setLayoutY(this.getCenterY() - 10);
                this.group.getChildren().add(railRoadImage);
            }
            if (isTerritory()) {
                this.cityImage.setImage(new Image(Objects.requireNonNull(App.class.getResource("/images/cities/cityDistrict.png")).toString()));
                this.group.getChildren().add(this.cityImage);
            }
            if (tile[0].getCity() != null) {
                this.cityImage.setImage(new Image(Objects.requireNonNull(App.class.getResource("/images/cities/cityCenter.png")).toString()));
                this.group.getChildren().add(this.cityImage);
            }
            if (tile[0].getCombatUnit() != null)
                this.addUnitToGroup(tile[0].getCombatUnit());
            if (tile[0].getNonCombatUnit() != null)
                this.addUnitToGroup(tile[0].getNonCombatUnit());
            this.group.getChildren().add(this.coordinationText);
            this.group.getChildren().add(this.infoText);
        });
    }

    public void setPopup(Group group, double x, double y) {
        popup.getContent().add(group);
        popup.setX(x);
        popup.setY(y);
        App.showPopUp(popup);
    }

    public void setInfoText(String info, Color color) {
        infoText.setText(info);
        infoText.setFill(color);
        this.infoText.setLayoutX(this.getCenterX() - 5 * this.infoText.getBoundsInLocal().getWidth() / 10);
    }

    public boolean isTerritory() {
        Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.HEX_IS_TERRITORY, new HashMap<>() {{
            put("x", String.valueOf(coordination.getX()));
            put("y", String.valueOf(coordination.getY()));
        }});
        assert response != null;
        return Boolean.parseBoolean(response.getParams().get("boolean"));
    }

    public void addUnitToGroup(Unit unit) {
        Group unitGroup = getUnitGroup(unit);
        unitGroup.setTranslateY(this.getCenterY() + 50);
        unitGroup.setTranslateX(this.getCenterX() + 82 + 30 * (unit instanceof NonCombatUnit ? 1 : -1));
        setUnitGroupEventHandlers(unitGroup, unit);
        this.group.getChildren().add(unitGroup);
        if (unit instanceof CombatUnit) {
            this.unitGroups.put("combatUnit", unitGroup);
        } else {
            this.unitGroups.put("nonCombatUnit", unitGroup);
        }
    }

    public static Group getUnitGroup(Unit unit) {
        Group group = new Group();
        ImageView imageView = new ImageView(unit.getUnitType().getImage());
        imageView.setFitWidth(imageView.getImage().getWidth() / 5);
        imageView.setFitHeight(imageView.getImage().getHeight() / 5);
        imageView.setLayoutX(-100);
        imageView.setLayoutY(20);
        Text text = new Text(unit.getUnitState().getName());
        text.setLayoutX(-100);
        text.setLayoutY(15);
        group.getChildren().add(imageView);
        group.getChildren().add(text);
        group.setCursor(Cursor.HAND);
        return group;
    }

    public void setUnitGroupEventHandlers(Group group, Unit unit) {
        group.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                ClientSocketController.sendRequestAndGetResponse(QueryRequests.UNIT_HEX_MOUSE_CLICKED, new HashMap<>() {{
                    put("x", String.valueOf(unit.getCurrentX()));
                    put("y", String.valueOf(unit.getCurrentY()));
                    put("type", unit instanceof CombatUnit ? "combat" : "nonCombat");
                }});
            }
        });
    }

    public void setCityEventHandlers() {
        this.cityImage.setFitWidth(55);
        this.cityImage.setFitHeight(55);
        this.cityImage.setLayoutX(this.getCenterX() - cityImage.getFitWidth() / 2);
        this.cityImage.setLayoutY(this.getCenterY());
        this.cityImage.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.CITY_HEX_MOUSE_CLICKED, new HashMap<>() {{
                    put("x", String.valueOf(coordination.getX()));
                    put("y", String.valueOf(coordination.getY()));
                }});
                assert response != null;
                if (response.getQueryResponse() == QueryResponses.OK) {
                    App.changeScene("cityPanelPage");
                }
            }
        });
    }

    private double setX(double x) {
        return x * 10 + width * this.coordination.getY() + verticalSpacing + (this.coordination.getX() % 2 == 0 ? 0 : 150);
    }

    private double setY(double y) {
        return y * 10 + height * this.coordination.getX() - horizontalSpacing;
    }

    public Group getGroup() {
        return this.group;
    }

    public HashMap<String, Group> getUnitGroups() {
        return this.unitGroups;
    }

    public double getCenterX() {
        return 100 + this.setX(0);
    }

    public double getCenterY() {
        return 25 * Math.sqrt(3) + this.setY(0);
    }

    public void setColorAdjust(Effect effect) {
        this.colorAdjust.setInput(effect);
    }

}
