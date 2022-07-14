package views;

import application.App;
import controllers.WorldController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import models.City;
import models.units.Unit;

public class CitiesPageController {

    @FXML
    private ScrollPane citiesScrollPane;
    @FXML
    private VBox citiesVBox;

    public void initialize(){
        for (City city : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCities()) {
            Text nameText = new Text(" " + city.getName() + "    ");
            Text infoText = new Text(" " + city.getInfo());
            Button cityPanelButton = new Button("city panel");
            cityPanelButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    //TODO going to this city's panel
                }
            });

            nameText.setFill(Color.WHITE);
            infoText.setFill(Color.ORANGE);
            HBox hBox = new HBox(//new Circle(20, new ImagePattern(Objects.requireNonNull(UserController.getUserByUsername(message.getSenderUsername())).getImage())),
                    nameText, infoText, cityPanelButton);
            hBox.setPrefWidth(citiesVBox.getPrefWidth());
            hBox.setPrefHeight(50);
            citiesVBox.getChildren().add(hBox);
        }

    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("gamePage");
    }
}
