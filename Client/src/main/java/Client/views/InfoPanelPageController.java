package Client.views;

import Client.application.App;
import Client.enums.units.UnitTypes;
import Client.models.City;
import Client.models.Civilization;
import Client.models.units.CombatUnit;
import Client.models.units.Unit;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Locale;

public class InfoPanelPageController {

//    @FXML
//    private Text nameText;
//    @FXML
//    private AnchorPane pane;
//
//    public void initialize() {
//        switch (GamePageController.infoPanelName) {
//            case "UnitPanel" -> initUnitPanel();
//            case "CityPanel" -> initCityPanel();
//            case "DemographicPanel" -> initDemographicPanel();
//            case "NotificationsPanel" -> initNotificationsPanel();
//            case "MilitaryPanel" -> initMilitaryPanel();
//            case "EconomicStatusPanel" -> initEconomicStatusPanel();
//            default -> {
//            }
//        }
//
//    }
//
//    private void initEconomicStatusPanel() {
//        nameText.setText("EconomicStatus Panel");
//        VBox vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);
//        vBox.setSpacing(10);
//        vBox.setLayoutX(50);
//        vBox.setLayoutY(50);
//        int counter = 1;
//        for (City city : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCities()) {
//            Text name = new Text(counter + " -> ");
//            name.setFill(Color.rgb(238, 128, 0));
//            Text info = new Text(city.getInfo());
//            info.setFill(Color.WHITE);
//            HBox hBox = new HBox(name, info);
//            hBox.setSpacing(10);
//            hBox.setAlignment(Pos.CENTER);
//            vBox.getChildren().add(hBox);
//        }
//        pane.getChildren().add(vBox);
//    }
//
//    private void initMilitaryPanel() {
//        nameText.setText("Military Panel");
//        VBox vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);
//        vBox.setSpacing(10);
//        vBox.setLayoutX(50);
//        vBox.setLayoutY(50);
//        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
//        int totalValueOfCombatUnits = 0, totalCombatUnits = 0;
//        for (Unit unit : currentCivilization.getAllUnits()) {
//            if (unit instanceof CombatUnit) {
//                totalValueOfCombatUnits += UnitTypes.valueOf(unit.getName().toUpperCase(Locale.ROOT)).getCost();
//                totalCombatUnits++;
//            }
//        }
//        Text cities = new Text("You have " + currentCivilization.getCities().size() + " city(ies) in total");
//        cities.setFill(Color.WHITE);
//        vBox.getChildren().add(cities);
//        int counter = 1;
//        for (City city : currentCivilization.getCities()) {
//            Text name = new Text(counter + "");
//            name.setFill(Color.rgb(238, 128, 0));
//            Text info = new Text(city.getCombatInfo());
//            info.setFill(Color.WHITE);
//            counter++;
//            HBox hBox = new HBox(name, info);
//            hBox.setSpacing(10);
//            hBox.setAlignment(Pos.CENTER);
//            vBox.getChildren().add(hBox);
//        }
//        Text valuation = new Text("And you have " + totalCombatUnits + " combat units \nwith total valuation of "
//                + totalValueOfCombatUnits + " golds and your combat units are : ");
//        valuation.setFill(Color.WHITE);
//        vBox.getChildren().add(valuation);
//        counter = 1;
//        for (Unit unit : currentCivilization.getAllUnits()) {
//            if (unit instanceof CombatUnit) {
//                Text name = new Text(counter + "");
//                name.setFill(Color.rgb(238, 128, 0));
//                Text info = new Text(((CombatUnit) unit).getCombatInfo());
//                info.setFill(Color.WHITE);
//                counter++;
//                HBox hBox = new HBox(name, info);
//                hBox.setSpacing(10);
//                hBox.setAlignment(Pos.CENTER);
//                vBox.getChildren().add(hBox);
//            }
//        }
//        pane.getChildren().add(vBox);
//    }
//
//    private void initNotificationsPanel() {
//        nameText.setText("Notifications Panel");
//        VBox vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);
//        vBox.setSpacing(10);
//        vBox.setLayoutX(50);
//        vBox.setLayoutY(50);
//        for (String notification : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications()) {
//            Text info = new Text(notification);
//            info.setFill(Color.WHITE);
//            vBox.getChildren().add(info);
//        }
//        pane.getChildren().add(vBox);
//    }
//
//    private void initDemographicPanel() {
//        nameText.setText("Demographic Panel");
//        VBox vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);
//        vBox.setSpacing(10);
//        vBox.setLayoutX(50);
//        vBox.setLayoutY(50);
//        Text name = new Text(WorldController.getWorld().getCurrentCivilizationName());
//        name.setFill(Color.rgb(238, 128, 0));
//        Text info = new Text(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getInfo());
//        info.setFill(Color.WHITE);
//        vBox.getChildren().add(name);
//        vBox.getChildren().add(info);
//        pane.getChildren().add(vBox);
//    }
//
//    private void initCityPanel() {
//        nameText.setText("City Panel");
//        VBox vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);
//        vBox.setSpacing(10);
//        vBox.setLayoutX(50);
//        vBox.setLayoutY(50);
//        int counter = 1;
//        for (City city : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCities()) {
//            Text name = new Text(counter + " -> ");
//            name.setFill(Color.rgb(238, 128, 0));
//            Text info = new Text(city.getName());
//            info.setFill(Color.WHITE);
//            Button button = new Button("Panel");
//            button.setOnMouseClicked(mouseEvent -> {
//                WorldController.setSelectedCity(city);
//                App.changeScene("cityPanelPage");
//            });
//            HBox hBox = new HBox(name, info, button);
//            hBox.setSpacing(10);
//            hBox.setAlignment(Pos.CENTER);
//            vBox.getChildren().add(hBox);
//        }
//        pane.getChildren().add(vBox);
//    }
//
//    private void initUnitPanel() {
//        nameText.setText("Unit Panel");
//        VBox vBox = new VBox();
//        vBox.setAlignment(Pos.CENTER);
//        vBox.setSpacing(10);
//        vBox.setLayoutX(50);
//        vBox.setLayoutY(50);
//        for (Unit unit : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits()) {
//            Text name = new Text(unit.getName() + " :    ");
//            name.setFill(Color.rgb(238, 128, 0));
//            Text info = new Text(unit.getInfo());
//            info.setFill(Color.WHITE);
//            vBox.getChildren().add(name);
//            vBox.getChildren().add(info);
//        }
//        pane.getChildren().add(vBox);
//    }
//
//    public void backButtonClicked(MouseEvent mouseEvent) {
//        App.changeScene("gamePage");
//    }
}
