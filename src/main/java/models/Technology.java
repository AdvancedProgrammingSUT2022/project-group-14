package models;

import controllers.WorldController;
import enums.Technologies;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Technology {
    private final Group group;
    private final Circle backgroundCircle;
    private final Circle imageCircle;
    private final Rectangle nameRectangle;
    private final Rectangle turnsRectangle;
    private final Text nameText;
    private final Text turnsText;
    private final ColorAdjust colorAdjust = new ColorAdjust();

    public Technology(Technologies technology, int x, int y) {
        this.group = new Group();
        this.nameRectangle = new Rectangle();
        this.nameRectangle.setWidth(230);
        this.nameRectangle.setHeight(27);
        this.nameRectangle.setLayoutX(x);
        this.nameRectangle.setLayoutY(y - this.nameRectangle.getHeight());
        this.nameRectangle.setFill(Color.CADETBLUE);
        this.turnsRectangle = new Rectangle();
        this.turnsRectangle.setWidth(145);
        this.turnsRectangle.setHeight(27);
        this.turnsRectangle.setLayoutX(x);
        this.turnsRectangle.setLayoutY(y);
        this.turnsRectangle.setFill(Color.rgb(238, 128, 0));
        this.nameText = new Text(turnsRectangle.getLayoutX() + turnsRectangle.getWidth() / 2 - 25, nameRectangle.getLayoutY() + nameRectangle.getHeight() - 7, technology.getName().replaceAll("_", " "));
        this.nameText.setFill(Color.WHITE);
        this.turnsText = new Text(turnsRectangle.getLayoutX() + turnsRectangle.getWidth() / 2, turnsRectangle.getLayoutY() + turnsRectangle.getHeight() - 7, "" + WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getTechnologies().get(technology));
        this.turnsText.setFill(Color.WHITE);
        this.backgroundCircle = new Circle(40);
        this.backgroundCircle.setFill(Color.CADETBLUE);
        this.backgroundCircle.setLayoutX(x);
        this.backgroundCircle.setLayoutY(y);
        this.imageCircle = new Circle(40, new ImagePattern(technology.getImage()));
        this.imageCircle.setLayoutX(x);
        this.imageCircle.setLayoutY(y);
        this.group.setEffect(colorAdjust);
        this.group.getChildren().clear();
        this.group.getChildren().add(nameRectangle);
        this.group.getChildren().add(turnsRectangle);
        this.group.getChildren().add(nameText);
        this.group.getChildren().add(turnsText);
        this.group.getChildren().add(backgroundCircle);
        this.group.getChildren().add(imageCircle);
        setGroupEventHandlers();
    }

    public void setGroupEventHandlers() {
        this.group.setCursor(Cursor.HAND);
    }

    public Group getGroup() {
        return group;
    }

}
