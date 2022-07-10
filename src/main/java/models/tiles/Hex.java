package models.tiles;

import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import models.tiles.Tile;

public class Hex {
    private final Polygon polygon;
    private final Group group;
    private final int xOfTile;
    private final int yOfTile;
    private final double width;
    private final double height;
    private final double verticalSpacing;
    private final double horizontalSpacing;
    private final Text coordinationText;
    private ImageView hexImage;

    public Hex(Tile tile) {
        this.xOfTile = tile.getX();
        this.yOfTile = tile.getY();
        this.group = new Group();
        this.verticalSpacing = yOfTile * 70 + 5;
        this.horizontalSpacing = 5 * Math.sqrt(3) * xOfTile * 7 + 5;
        this.width = 140;
        this.height = 70 * Math.sqrt(3);
        this.polygon = new Polygon(setX(5), setY(0), setX(15), setY(0), setX(20), setY(5 * Math.sqrt(3)),
                setX(15), setY(10 * Math.sqrt(3)), setX(5), setY(10 * Math.sqrt(3)), setX(0), setY(5 * Math.sqrt(3)));
        this.coordinationText = new Text(xOfTile + " " + yOfTile);
        this.coordinationText.setLayoutX(this.getCenterX() - this.coordinationText.getBoundsInLocal().getWidth() / 2);
        this.coordinationText.setLayoutY(this.getCenterY());
    }

    public void updateHexOfGivenTile(Tile tile) {
        polygon.setFill(Color.ORANGE);
        this.group.getChildren().clear();
        this.group.getChildren().add(this.polygon);
        this.group.getChildren().add(this.coordinationText);
        //TODO adding units and features
    }

    private double setX(double x) {
        return x * 7 + width * this.yOfTile + verticalSpacing + (xOfTile % 2 == 0 ? 0 : 105);
    }

    private double setY(double y) {
        return y * 7 + height * this.xOfTile - horizontalSpacing;
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

    public double getCenterX() {
        return 70 + this.setX(0);
    }

    public double getCenterY() {
        return 25 * Math.sqrt(3) + this.setY(0);
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
