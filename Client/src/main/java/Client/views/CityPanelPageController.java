package Client.views;

import Client.application.App;
import Client.controllers.ClientSocketController;
import Client.controllers.HexController;
import Client.enums.BuildingTypes;
import Client.enums.QueryRequests;
import Client.enums.units.UnitTypes;
import Client.models.Building;
import Client.models.Citizen;
import Client.models.City;
import Client.models.network.Response;
import Client.models.units.Unit;
import com.google.gson.Gson;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Objects;

public class CityPanelPageController {
    @FXML
    private Text goldText;
    @FXML
    private Text foodText;
    @FXML
    private Text productionText;
    @FXML
    private Text populationText;
    @FXML
    private Circle goldCircle;
    @FXML
    private Circle foodCircle;
    @FXML
    private Circle productionCircle;
    @FXML
    private Circle populationCircle;
    @FXML
    private Spinner<Integer> xTileSpinner;
    @FXML
    private Spinner<Integer> yTileSpinner;
    @FXML
    private ChoiceBox<String> unitsBox;
    @FXML
    private ChoiceBox<String> buildingsBox;
    @FXML
    private Text productionName;
    @FXML
    private Text productionRemainingCost;
    @FXML
    private Spinner<Integer> xCitizenSpinner;
    @FXML
    private Spinner<Integer> yCitizenSpinner;
    @FXML
    private ChoiceBox<String> unemployedCitizensBox;
    @FXML
    private ChoiceBox<String> employedCitizensBox;
    @FXML
    private Text infoText;
    @FXML
    private Text citizensText;
    private City city;

    public void initialize() {
        city = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_SELECTED_CITY, new HashMap<>())).getParams().get("city"), City.class);
        calcGoods();
        initSpinners();
        initChoiceBoxes();
        infoText.setText("");
        citizensText.setText(city.employedCitizensData() + city.unemployedCitizensData());
        initProduction();
    }

    public void initProduction() {
        if (city.getCurrentUnit() != null) {
            productionName.setText("Name : " + city.getCurrentUnit().getName());
            productionRemainingCost.setText("Production remainingCost : " + city.getCurrentProductionRemainingCost());
        } else if (city.getCurrentBuilding() != null) {
            productionName.setText("Name : " + city.getCurrentBuilding().getName());
            productionRemainingCost.setText("Production remainingCost : " + city.getCurrentProductionRemainingCost());
        } else {
            productionName.setText("Nothing!");
            productionRemainingCost.setText("");
        }
    }

    public void initChoiceBoxes() {
        unitsBox.setValue("Units");
        unitsBox.getItems().clear();
        for (UnitTypes value : UnitTypes.values()) {
            if (Boolean.parseBoolean(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CAN_PRODUCE_UNIT, new HashMap<>() {{
                put("unitType", new Gson().toJson(value));
            }})).getParams().get("boolean"))) {
                unitsBox.getItems().add(value.getName());
            }
        }
        unitsBox.setOnMouseClicked(mouseEvent -> {
            buildingsBox.setValue("Buildings");
        });
        buildingsBox.setValue("Buildings");
        buildingsBox.getItems().clear();
        for (BuildingTypes value : BuildingTypes.values()) {
            if (Boolean.parseBoolean(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.CAN_PRODUCE_BUILDING, new HashMap<>() {{
                put("buildingType", new Gson().toJson(value));
            }})).getParams().get("boolean"))) {
                buildingsBox.getItems().add(value.getName());
            }
        }
        buildingsBox.setOnMouseClicked(mouseEvent -> {
            unitsBox.setValue("Units");
            if (!buildingsBox.getValue().equals("Buildings")) {
                infoText.setText(BuildingTypes.valueOf(buildingsBox.getValue().toUpperCase()).getInfo());
            }
        });
        buildingsBox.getItems().add(BuildingTypes.WINDMILL.getName());
        unemployedCitizensBox.setValue("UnemployedCitizens");
        employedCitizensBox.setValue("EmployedCitizens");
        unemployedCitizensBox.getItems().clear();
        employedCitizensBox.getItems().clear();
        for (Citizen citizen : city.getCitizens())
            if (citizen.isWorking()) {
                employedCitizensBox.getItems().add(citizen.getId() + "");
            } else {
                unemployedCitizensBox.getItems().add(citizen.getId() + "");
            }
    }

    public void initSpinners() {
        xTileSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, HexController.getHeight(), 1, 1));
        yTileSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, HexController.getWidth(), 1, 1));
        xCitizenSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, HexController.getWidth(), 1, 1));
        yCitizenSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, HexController.getWidth(), 1, 1));
    }

    public void calcGoods() {
        double addedGold = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_CITY_GOLD, new HashMap<>())).getParams().get("gold"), double.class);
        double addedProduction = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_CITY_PRODUCTION, new HashMap<>())).getParams().get("production"), double.class);
        double addedFood = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_CITY_FOOD, new HashMap<>())).getParams().get("food"), double.class);
        goldText.setText("Gold : " + addedGold);
        foodText.setText("Food : " + addedFood);
        productionText.setText("Production : " + addedProduction);
        populationText.setText("Population : " + city.getCitizens().size());
        goldCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/goldLogo.png")).toString())));
        foodCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/foodLogo.png")).toString())));
        productionCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/productionLogo.png")).toString())));
        populationCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/populationLogo.png")).toString())));
    }

    public void buyTileButtonClicked(MouseEvent mouseEvent) {
        Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.BUY_TILE, new HashMap<>() {{
            put("city", new Gson().toJson(city));
            put("x", new Gson().toJson(xTileSpinner.getValue() - 1));
            put("y", new Gson().toJson(yTileSpinner.getValue() - 1));
        }});
        switch (Objects.requireNonNull(response).getQueryResponse()) {
            case OK -> {
                infoText.setText("tile was bought successfully");
                city = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_SELECTED_CITY, new HashMap<>())).getParams().get("city"), City.class);
            }
            case NOT_ENOUGH_GOLD -> infoText.setText("you don't have enough gold for buying this tile");
            case CANT_BUY_THIS_TILE -> infoText.setText("can't buy this tile");
            case ALREADY_HAVE_TILE -> infoText.setText("you already have this tile");
        }
    }

    public void purchaseButtonClicked(MouseEvent mouseEvent) {
        if (!unitsBox.getValue().equals("Units")) {
            UnitTypes unit = UnitTypes.valueOf(unitsBox.getValue().toUpperCase());
            Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.PRODUCE_UNIT, new HashMap<>() {{
                put("unitType", new Gson().toJson(unit));
            }});
            assert response != null;
            city = new Gson().fromJson(response.getParams().get("city"), City.class);
            initProduction();
        } else if (!buildingsBox.getValue().equals("Buildings")) {
            BuildingTypes building = BuildingTypes.valueOf(buildingsBox.getValue().toUpperCase());
            Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.PRODUCE_BUILDING, new HashMap<>() {{
                put("buildingType", new Gson().toJson(building));
            }});
            assert response != null;
            city = new Gson().fromJson(response.getParams().get("city"), City.class);
            initProduction();
        }
    }

    public void unlockButtonClicked(MouseEvent mouseEvent) {
        if (!employedCitizensBox.getValue().equals("EmployedCitizens")) {
            Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.UNLOCK_CITIZEN_FROM_TILE, new HashMap<>() {{
                put("city", new Gson().toJson(city));
                put("id", employedCitizensBox.getValue());
            }});
            switch (Objects.requireNonNull(response).getQueryResponse()) {
                case OK -> {
                    infoText.setText("citizen unlocked successfully");
                    city = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_SELECTED_CITY, new HashMap<>())).getParams().get("city"), City.class);
                }
                case NO_CITIZEN_EXISTS -> infoText.setText("no citizens exists with this id");
            }
            citizensText.setText(city.employedCitizensData() + city.unemployedCitizensData());
        }
        initChoiceBoxes();
    }

    public void lockButtonClicked(MouseEvent mouseEvent) {
        if (!unemployedCitizensBox.getValue().equals("UnemployedCitizens")) {
            Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.LOCK_CITIZEN_TO_TILE, new HashMap<>() {{
                put("city", new Gson().toJson(city));
                put("id", unemployedCitizensBox.getValue());
                put("x", new Gson().toJson(xCitizenSpinner.getValue() - 1));
                put("y", new Gson().toJson(yCitizenSpinner.getValue() - 1));
            }});
            switch (Objects.requireNonNull(response).getQueryResponse()) {
                case OK -> {
                    infoText.setText("citizen locked successfully");
                    city = new Gson().fromJson(Objects.requireNonNull(ClientSocketController.sendRequestAndGetResponse(QueryRequests.GET_SELECTED_CITY, new HashMap<>())).getParams().get("city"), City.class);
                }
                case NO_CITIZEN_EXISTS -> infoText.setText("no citizens exists with this id");
                case SELECT_TILE_IN_CITY -> infoText.setText("you should select a tile inside city territory");
            }
            citizensText.setText(city.employedCitizensData() + city.unemployedCitizensData());
        }
        initChoiceBoxes();
    }

    public void backButtonClicked(MouseEvent mouseEvent) {
        App.changeScene("gamePage");
    }

    public void cancelButtonClicked(MouseEvent mouseEvent) {
        if (!productionName.getText().equals("")) {
            Response response = ClientSocketController.sendRequestAndGetResponse(QueryRequests.CANCEL_PRODUCTION, new HashMap<>());
            assert response != null;
            city = new Gson().fromJson(response.getParams().get("city"), City.class);
            initProduction();
        }
    }
}
