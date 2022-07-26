package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.enums.QueryRequests;
import Client.enums.units.UnitTypes;
import Client.models.City;
import Client.models.units.CombatUnit;
import Client.models.units.Unit;
import javafx.event.EventHandler;
import Client.models.network.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.*;

public class InfoPanelPageController {

    @FXML
    private Text nameText;
    @FXML
    private AnchorPane pane;

    public void initialize() {
        if (!App.isMute() && App.getMediaPlayer().isMute()) {
            App.playNext();
        }
        switch (GamePageController.infoPanelName) {
            case "UnitPanel" -> initUnitPanel();
            case "CityPanel" -> initCityPanel();
            case "DemographicPanel" -> initDemographicPanel();
            case "NotificationsPanel" -> initNotificationsPanel();
            case "MilitaryPanel" -> initMilitaryPanel();
            case "EconomicStatusPanel" -> initEconomicStatusPanel();
            case "DiplomacyPanel" -> initDiplomacyPanel();
            default -> {
            }
        }
    }

    public void initDiplomacyPanel() {
        Button discussButton = new Button("discuss");
        discussButton.setLayoutX(330);
        discussButton.setLayoutY(200);
        discussButton.setPrefWidth(120);
        discussButton.setOnMouseClicked(mouseEvent -> App.changeScene("discussPanel"));

        Button tradeButton = new Button("trade");
        tradeButton.setLayoutX(330);
        tradeButton.setLayoutY(240);
        tradeButton.setPrefWidth(120);
        tradeButton.setOnMouseClicked(mouseEvent -> App.changeScene("tradePanel"));

        Text message = new Text("");
        message.setVisible(false);
        message.setLayoutX(330);
        message.setLayoutY(360);
        message.setStyle("-fx-font-size: 25");
        message.setFill(Color.rgb(200, 110, 17));

        ArrayList<String> civilizationNames = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_ALL_CIVILIZATIONS_NAMES, new HashMap<>())).getParams().get("names"),
                new TypeToken<List<String>>() {
                }.getType());
        String currentCivilizationName = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_CURRENT_CIVILIZATION_NAME, new HashMap<>())).getParams().get("name"), String.class);
        civilizationNames.remove(currentCivilizationName);
        ChoiceBox<String> civilizationNamesChoiceBox = new ChoiceBox<>();
        civilizationNamesChoiceBox.setValue("civilizations");
        civilizationNamesChoiceBox.getItems().addAll(civilizationNames);
        civilizationNamesChoiceBox.setLayoutX(240);
        civilizationNamesChoiceBox.setLayoutY(300);
        civilizationNamesChoiceBox.setPrefWidth(130);
        civilizationNamesChoiceBox.setPrefHeight(36);

        Button declareWarButton = new Button("declare war");
        declareWarButton.setLayoutX(400);
        declareWarButton.setLayoutY(300);
        declareWarButton.setPrefWidth(120);
        declareWarButton.setOnMouseClicked(mouseEvent -> {
            message.setVisible(true);
            if (civilizationNamesChoiceBox.getValue() != null) {
                declareWar(civilizationNamesChoiceBox.getValue());
                message.setText("you declared war with " + civilizationNamesChoiceBox.getValue());
            } else {
                message.setText("select a civilization");
            }
        });

        Button makePeaceButton = new Button("make peace");
        makePeaceButton.setLayoutX(580);
        makePeaceButton.setLayoutY(300);
        makePeaceButton.setPrefWidth(120);
        makePeaceButton.setOnMouseClicked(mouseEvent -> {
            message.setVisible(true);
            if (civilizationNamesChoiceBox.getValue() != null) {
                makePeace(civilizationNamesChoiceBox.getValue());
                message.setText("you made peace with " + civilizationNamesChoiceBox.getValue());
            } else {
                message.setText("select a civilization");
            }
        });

        pane.getChildren().add(discussButton);
        pane.getChildren().add(tradeButton);
        pane.getChildren().add(civilizationNamesChoiceBox);
        pane.getChildren().add(declareWarButton);
        pane.getChildren().add(makePeaceButton);
        pane.getChildren().add(message);

    }

    public void declareWar(String civilizationName) {
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.DECLARE_WAR, new HashMap<>(){{put("enemyName", civilizationName);}});
    }

    public void makePeace(String civilizationName) {
        ClientSocketController.sendRequestAndGetResponse(QueryRequests.MAKE_PEACE, new HashMap<>(){{put("name", civilizationName);}});
    }

    private void initEconomicStatusPanel() {
        nameText.setText("EconomicStatus Panel");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setLayoutX(50);
        vBox.setLayoutY(50);
        int counter = 1;
        ArrayList<String> citiesInfo = new Gson().fromJson(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_CITIES_INFO, new HashMap<>()).getParams().get("info"),
                new TypeToken<List<String>>() {
                }.getType());
        for (String cityInfo : citiesInfo) {
            Text name = new Text(counter + " -> ");
            name.setFill(Color.rgb(238, 128, 0));
            Text info = new Text(cityInfo);
            info.setFill(Color.WHITE);
            HBox hBox = new HBox(name, info);
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
        }
        pane.getChildren().add(vBox);
    }

    private void initMilitaryPanel() {
        nameText.setText("Military Panel");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setLayoutX(50);
        vBox.setLayoutY(50);
        Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_MILITARY_INFO, new HashMap<>());
        assert response != null;
        ArrayList<String> cityCombatInfo = new Gson().fromJson(response.getParams().get("cityCombatInfo"), new TypeToken<List<String>>() {
        }.getType());
        ArrayList<String> unitCombatInfo = new Gson().fromJson(response.getParams().get("unitCombatInfo"), new TypeToken<List<String>>() {
        }.getType());
        Text cities = new Text("You have " + response.getParams().get("citySize") + " city(ies) in total");
        cities.setFill(Color.WHITE);
        vBox.getChildren().add(cities);
        int counter = 1;
        for (String cityInfo : cityCombatInfo) {
            Text name = new Text(counter + "");
            name.setFill(Color.rgb(238, 128, 0));
            Text info = new Text(cityInfo);
            info.setFill(Color.WHITE);
            counter++;
            HBox hBox = new HBox(name, info);
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
        }
        Text valuation = new Text("And you have " + response.getParams().get("totalCombatUnits") + " combat units \nwith total valuation of "
                + response.getParams().get("totalValueOfCombatUnits") + " golds and your combat units are : ");
        valuation.setFill(Color.WHITE);
        vBox.getChildren().add(valuation);
        counter = 1;
        for (String unitInfo : unitCombatInfo) {
            Text name = new Text(counter + "");
            name.setFill(Color.rgb(238, 128, 0));
            Text info = new Text(unitInfo);
            info.setFill(Color.WHITE);
            HBox hBox = new HBox(name, info);
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
            counter++;
        }
        pane.getChildren().add(vBox);
    }

    private void initNotificationsPanel() {
        nameText.setText("Notifications Panel");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setLayoutX(50);
        vBox.setLayoutY(50);
        ArrayList<String> notifications = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_NOTIFICATIONS, new HashMap<>()))
                .getParams().get("notifications"), new TypeToken<List<String>>() {
        }.getType());
        for (String notification : notifications) {
            Text info = new Text(notification);
            info.setFill(Color.WHITE);
            vBox.getChildren().add(info);
        }
        pane.getChildren().add(vBox);
    }

    private void initDemographicPanel() {
        nameText.setText("Demographic Panel");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setLayoutX(50);
        vBox.setLayoutY(50);
        Text name = new Text(MainMenuController.loggedInUser.getUsername());
        name.setFill(Color.rgb(238, 128, 0));
        Text info = new Text(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_CIV_INFO, new HashMap<>())).getParams().get("info"));
        info.setFill(Color.WHITE);
        vBox.getChildren().add(name);
        vBox.getChildren().add(info);
        pane.getChildren().add(vBox);
    }

    private void initCityPanel() {
        nameText.setText("City Panel");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setLayoutX(50);
        vBox.setLayoutY(50);
        int counter = 1;
        ArrayList<String> citiesName = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_CITIES_NAME, new HashMap<>()))
                .getParams().get("citiesName"), new TypeToken<List<String>>() {
        }.getType());
        for (String cityName : citiesName) {
            Text name = new Text(counter + " -> ");
            name.setFill(Color.rgb(238, 128, 0));
            Text info = new Text(cityName);
            info.setFill(Color.WHITE);
            Button button = new Button("Panel");
            button.setOnMouseClicked(mouseEvent -> {
                ClientSocketController.sendRequestAndGetResponse(QueryRequests.SET_SELECTED_CITY, new HashMap<>(){{
                    put("cityName", cityName);
                }});
                App.changeScene("cityPanelPage");
            });
            HBox hBox = new HBox(name, info, button);
            hBox.setSpacing(10);
            hBox.setAlignment(Pos.CENTER);
            vBox.getChildren().add(hBox);
        }
        pane.getChildren().add(vBox);
    }

    private void initUnitPanel() {
        nameText.setText("Unit Panel");
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.setLayoutX(50);
        vBox.setLayoutY(50);
        ArrayList<String> unitsInfo = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_UNITS_INFO, new HashMap<>()))
                .getParams().get("unitsInfo"), new TypeToken<List<String>>() {
        }.getType());
        for (String unitInfo : unitsInfo) {
            Text info = new Text(unitInfo);
            info.setFill(Color.WHITE);
            vBox.getChildren().add(info);
        }
        pane.getChildren().add(vBox);
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("gamePage");
    }
}
