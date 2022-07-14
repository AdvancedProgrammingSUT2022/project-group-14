package views;

import application.App;
import controllers.UserController;
import controllers.WarController;
import controllers.WorldController;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import models.units.Unit;

import java.util.Objects;

public class UnitsPanelController {
    @FXML
    private VBox unitsVBox;
    @FXML
    private ScrollPane unitsScrollPane;

    public void initialize(){
        for (Unit unit : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits()) {
            Text nameText = new Text(" " + unit.getName() + "    ");
            Text infoText = new Text(" " + unit.getInfo());

            nameText.setFill(Color.WHITE);
            infoText.setFill(Color.ORANGE);
            HBox hBox = new HBox(//new Circle(20, new ImagePattern(Objects.requireNonNull(UserController.getUserByUsername(message.getSenderUsername())).getImage())),
                    nameText, infoText);
            hBox.setPrefWidth(unitsVBox.getPrefWidth());
            hBox.setPrefHeight(50);
            unitsVBox.getChildren().add(hBox);
        }

    }



    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("gamePage");
    }
}
