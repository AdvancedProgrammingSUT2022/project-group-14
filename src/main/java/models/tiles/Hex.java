package models.tiles;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import models.tiles.Tile;

public class Hex {
    private final Polygon polygon;
    private final Group group;
    private final int xOfTile;
    private final int yOfTile;
    private final int i;
    private final int j;
    private final double width;
    private final double height;
    private final double verticalSpacing;
    private final double horizontalSpacing;

    public Hex(Tile tile, int x, int y) {
        this.xOfTile = tile.getX();
        this.yOfTile = tile.getY();
        this.i = x;
        this.j = y;
        this.group = new Group();
        this.verticalSpacing = y * 70 + 5;
        this.horizontalSpacing = 5 * Math.sqrt(3) * x * 7 + 5;
        this.width = 140;
        this.height = 70 * Math.sqrt(3);
        this.polygon = new Polygon(setX(5), setY(0), setX(15), setY(0), setX(20), setY(5 * Math.sqrt(3)),
                setX(15), setY(10 * Math.sqrt(3)), setX(5), setY(10 * Math.sqrt(3)), setX(0), setY(5 * Math.sqrt(3)));
    }

    public void updateHexOfGivenTile(Tile tile) {
        polygon.setFill(Color.ORANGE);
        this.group.getChildren().clear();
        this.group.getChildren().add(this.polygon);
        //TODO adding units and features
    }

    private double setX(double x) {
        return x * 7 + width * this.j + verticalSpacing + x % 2 == 1 ? 0 : 105;
    }

    private double setY(double y) {
        return y * 7 + height * this.i - horizontalSpacing;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public Group getGroup() {
        return group;
    }

    public int getXOfTile() {
        return xOfTile;
    }

    public int getYOfTile() {
        return yOfTile;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public double getVerticalSpacing() {
        return verticalSpacing;
    }

    public double getHorizontalSpacing() {
        return horizontalSpacing;
    }

}
