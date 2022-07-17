package models.tiles;

import application.App;
import controllers.*;
import enums.tiles.TileFeatureTypes;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import models.City;
import models.units.CombatUnit;
import models.units.NonCombatUnit;
import models.units.Unit;

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
    private final ImageView cityImage = new ImageView(CityController.getCenterImage());
    private final HashMap<String, Group> unitGroups = new HashMap<>();
    private final Text coordinationText;
    private final ColorAdjust colorAdjust = new ColorAdjust();
    private final Popup popup = new Popup();

    public Hex(Tile tile) {
        this.group = new Group();
        this.coordination = new Coordination(tile.getX(), tile.getY());
        this.verticalSpacing = tile.getY() * 100 + 5;
        this.horizontalSpacing = 5 * Math.sqrt(3) * tile.getX() * 10 + 5;
        this.width = 200;
        this.height = 100 * Math.sqrt(3);
        this.polygon = new Polygon(setX(5), setY(0), setX(15), setY(0), setX(20), setY(5 * Math.sqrt(3)),
                setX(15), setY(10 * Math.sqrt(3)), setX(5), setY(10 * Math.sqrt(3)), setX(0), setY(5 * Math.sqrt(3)));
        this.coordinationText = new Text(String.valueOf(tile.getX() + 1) + "," + String.valueOf(tile.getY() + 1));
        this.coordinationText.setLayoutX(this.getCenterX() - 7 * this.coordinationText.getBoundsInLocal().getWidth() / 10);
        this.coordinationText.setLayoutY(this.getCenterY() - 10);
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
            popup.getContent().clear();
            popup.hide();
        });
        this.group.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                if (WorldController.getSelectedTile() != null
                        && WorldController.getSelectedTile().equals(MapController.getTileByCoordinates(Hex.this.coordination))) {
                    WorldController.setSelectedTile(null);
                    Hex.this.colorAdjust.setInput(null);
                } else {
                    if (WorldController.getSelectedTile() != null)
                        WorldController.getSelectedTile().getHex().setColorAdjust(null);
                    WorldController.setSelectedTile(MapController.getTileByCoordinates(Hex.this.coordination));
                    Hex.this.colorAdjust.setInput(new Bloom());
                }
                popup.getContent().add(TileController.getInfoPopup(coordination));
                popup.setX(mouseEvent.getSceneX() + 30);
                popup.setY(mouseEvent.getSceneY() + 15);
                App.showPopUp(popup);
            }
        });
    }

    public void updateHex() {
        Tile tile = MapController.getTileByCoordinates(this.coordination);
        if (tile.getFeature() != TileFeatureTypes.NULL) {
            this.polygon.setFill(new ImagePattern(tile.getFeature().getImage()));
        } else {
            this.polygon.setFill(new ImagePattern(tile.getType().getImage()));
        }

        this.group.getChildren().clear();
        this.unitGroups.clear();
        this.group.getChildren().add(this.polygon);
        if (tile.getRoadState() == 0) {
            ImageView roadImage = new ImageView(Objects.requireNonNull(App.class.getResource("/images/resources/road.png")).toString());
            roadImage.setFitWidth(30);
            roadImage.setFitHeight(30);
            roadImage.setLayoutX(this.getCenterX() - 60);
            roadImage.setLayoutY(this.getCenterY() - 10);
            this.group.getChildren().add(roadImage);
        } else if (tile.getRailRoadState() == 0) {
            ImageView railRoadImage = new ImageView(Objects.requireNonNull(App.class.getResource("/images/resources/railRoad.png")).toString());
            railRoadImage.setFitWidth(30);
            railRoadImage.setFitHeight(30);
            railRoadImage.setLayoutX(this.getCenterX() - 60);
            railRoadImage.setLayoutY(this.getCenterY() - 10);
            this.group.getChildren().add(railRoadImage);
        }
        if (isTerritory()) {
            this.cityImage.setImage(CityController.getDistrictImage());
            this.group.getChildren().add(this.cityImage);
        }
        if (tile.getCity() != null) {
            this.cityImage.setImage(CityController.getCenterImage());
            this.group.getChildren().add(this.cityImage);
        }
        if (tile.getCombatUnit() != null)
            this.addUnitToGroup(tile.getCombatUnit());
        if (tile.getNonCombatUnit() != null)
            this.addUnitToGroup(tile.getNonCombatUnit());

        this.group.getChildren().add(this.coordinationText);
    }

    public boolean isTerritory() {
        for (City city : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCities()) {
            for (Tile tile : city.getTerritory()) {
                if (MapController.getTileByCoordinates(this.coordination).equals(tile) && tile.getCity() == null)
                    return true;
            }
        }
        return false;
    }

    public void addUnitToGroup(Unit unit) {
        Group unitGroup = UnitController.getUnitGroup(unit);
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

    public void setUnitGroupEventHandlers(Group group, Unit unit) {
        group.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY
                    && unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
                if (unit instanceof CombatUnit) {
                    if (WorldController.getSelectedCombatUnit() != null && WorldController.getSelectedCombatUnit().equals(unit)) {
                        WorldController.setSelectedCombatUnit(null);
                    } else {
                        WorldController.setSelectedCombatUnit((CombatUnit) unit);
                    }
                } else {
                    if (WorldController.getSelectedNonCombatUnit() != null && WorldController.getSelectedNonCombatUnit().equals(unit)) {
                        WorldController.setSelectedNonCombatUnit(null);
                    } else {
                        WorldController.setSelectedNonCombatUnit((NonCombatUnit) unit);
                    }
                }
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
                WorldController.setSelectedCity(MapController.getTileByCoordinates(Hex.this.coordination).getCity());
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
