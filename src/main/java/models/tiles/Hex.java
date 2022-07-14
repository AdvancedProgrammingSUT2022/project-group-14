package models.tiles;

import application.App;
import controllers.MapController;
import controllers.TileController;
import controllers.UnitController;
import controllers.WorldController;
import enums.tiles.TileFeatureTypes;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import models.units.CombatUnit;
import models.units.NonCombatUnit;
import models.units.Unit;

public class Hex {
    private final Polygon polygon;
    private final Group group;
    private final Coordination coordination;
    private final double width;
    private final double height;
    private final double verticalSpacing;
    private final double horizontalSpacing;
    private final Text coordinationText;
    private final ColorAdjust colorAdjust = new ColorAdjust();
    private final Popup popup = new Popup();

    public Hex(Tile tile) {
        this.group = new Group();
        this.coordination = new Coordination(tile.getX(), tile.getY());
        this.verticalSpacing = tile.getY() * 70 + 5;
        this.horizontalSpacing = 5 * Math.sqrt(3) * tile.getX() * 7 + 5;
        this.width = 140;
        this.height = 70 * Math.sqrt(3);
        this.polygon = new Polygon(setX(5), setY(0), setX(15), setY(0), setX(20), setY(5 * Math.sqrt(3)),
                setX(15), setY(10 * Math.sqrt(3)), setX(5), setY(10 * Math.sqrt(3)), setX(0), setY(5 * Math.sqrt(3)));
        this.coordinationText = new Text(tile.getX() + "," + tile.getY());
        this.coordinationText.setLayoutX(this.getCenterX() - this.coordinationText.getBoundsInLocal().getWidth() / 2);
        this.coordinationText.setLayoutY(this.getCenterY());
        setGroupEventHandlers();
        this.group.setEffect(this.colorAdjust);
    }

    public void setGroupEventHandlers() {
        this.group.setCursor(Cursor.HAND);
        this.group.setOnMouseEntered(mouseEvent -> {
            Hex.this.group.toFront();
            Hex.this.colorAdjust.setInput(new DropShadow(25, Color.BLACK));
        });
        this.group.setOnMouseExited(mouseEvent -> {
            Hex.this.colorAdjust.setInput(null);
            popup.getContent().clear();
            popup.hide();
        });
        this.group.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                popup.getContent().add(TileController.getInfoPopup(coordination));
                popup.setX(mouseEvent.getSceneX() + 30);
                popup.setY(mouseEvent.getSceneY() + 15);
                App.showPopUp(popup);
            } else if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                WorldController.setSelectedTile(MapController.getTileByCoordinates(Hex.this.coordination));
            }
        });
    }

    public void updateHexOfGivenTile(Tile tile) {
        if (tile.getFeature() != TileFeatureTypes.NULL) {
            this.polygon.setFill(new ImagePattern(tile.getFeature().getImage()));
        } else {
            this.polygon.setFill(new ImagePattern(tile.getType().getImage()));
        }

        this.group.getChildren().clear();
        this.group.getChildren().add(this.polygon);
        if (tile.getNonCombatUnit() != null)
            this.addUnitToGroup(tile.getNonCombatUnit());
        if (tile.getCombatUnit() != null)
            this.addUnitToGroup(tile.getCombatUnit());

        this.group.getChildren().add(this.coordinationText);
        //TODO adding units
    }

    private void addUnitToGroup(Unit unit) {
        Group unitGroup = UnitController.getUnitGroup(unit);
        System.out.println(unitGroup.getLayoutX() + " * " + unitGroup.getLayoutY());
        setUnitGroupEventHandlers(group, unit);
        unitGroup.setTranslateY(this.getCenterY() - 12);
        unitGroup.setTranslateX(this.getCenterX() + 85 + 24 * (unit instanceof NonCombatUnit ? 1 : -1));
        this.group.getChildren().add(unitGroup);
    }

    public void setUnitGroupEventHandlers(Group group, Unit unit) {
        group.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                System.out.println(unit.getName());
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

    private double setX(double x) {
        return x * 7 + width * this.coordination.getY() + verticalSpacing + (this.coordination.getX() % 2 == 0 ? 0 : 105);
    }

    private double setY(double y) {
        return y * 7 + height * this.coordination.getX() - horizontalSpacing;
    }

    public Polygon getPolygon() {
        return this.polygon;
    }

    public Group getGroup() {
        return this.group;
    }

    public int getXOfTile() {
        return this.coordination.getX();
    }

    public int getYOfTile() {
        return this.coordination.getY();
    }

    public double getCenterX() {
        return 70 + this.setX(0);
    }

    public double getCenterY() {
        return 25 * Math.sqrt(3) + this.setY(0);
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public double getVerticalSpacing() {
        return this.verticalSpacing;
    }

    public double getHorizontalSpacing() {
        return this.horizontalSpacing;
    }

}
