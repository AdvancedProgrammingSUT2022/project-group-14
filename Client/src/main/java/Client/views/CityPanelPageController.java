package Client.views;

import Client.application.App;
import Client.controllers.HexController;
import Client.enums.BuildingTypes;
import Client.enums.units.UnitTypes;
import Client.models.Building;
import Client.models.Citizen;
import Client.models.City;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.Objects;

public class CityPanelPageController {
//    @FXML
//    private Text goldText;
//    @FXML
//    private Text foodText;
//    @FXML
//    private Text productionText;
//    @FXML
//    private Text populationText;
//    @FXML
//    private Circle goldCircle;
//    @FXML
//    private Circle foodCircle;
//    @FXML
//    private Circle productionCircle;
//    @FXML
//    private Circle populationCircle;
//    @FXML
//    private Spinner<Integer> xTileSpinner;
//    @FXML
//    private Spinner<Integer> yTileSpinner;
//    @FXML
//    private ChoiceBox<String> unitsBox;
//    @FXML
//    private ChoiceBox<String> buildingsBox;
//    @FXML
//    private Text productionName;
//    @FXML
//    private Text productionRemainingCost;
//    @FXML
//    private Spinner<Integer> xCitizenSpinner;
//    @FXML
//    private Spinner<Integer> yCitizenSpinner;
//    @FXML
//    private ChoiceBox<String> unemployedCitizensBox;
//    @FXML
//    private ChoiceBox<String> employedCitizensBox;
//    @FXML
//    private Text infoText;
//    @FXML
//    private Text citizensText;
//    private City city;
//
//    public void initialize() {
//        city = WorldController.getSelectedCity();
//        calcGoods();
//        initSpinners();
//        initChoiceBoxes();
//        infoText.setText("");
//        citizensText.setText(city.employedCitizensData() + city.unemployedCitizensData());
//        initProduction();
//    }
//
//    public void initProduction() {
//        if (city.getCurrentUnit() != null) {
//            productionName.setText("Name : " + city.getCurrentUnit().getName());
//            productionRemainingCost.setText("Production remainingCost : " + city.getCurrentProductionRemainingCost());
//        } else if (city.getCurrentBuilding() != null) {
//            productionName.setText("Name : " + city.getCurrentBuilding().getName());
//            productionRemainingCost.setText("Production remainingCost : " + city.getCurrentProductionRemainingCost());
//        } else {
//            productionName.setText("Nothing!");
//            productionRemainingCost.setText("");
//        }
//    }
//
//    public void initChoiceBoxes() {
//        unitsBox.setValue("Units");
//        unitsBox.getItems().clear();
//        for (UnitTypes value : UnitTypes.values())
//            if (CityController.canProduceUnit(value))
//                unitsBox.getItems().add(value.getName());
//        unitsBox.setOnMouseClicked(mouseEvent -> {
//            buildingsBox.setValue("Buildings");
//        });
//        buildingsBox.setValue("Buildings");
//        buildingsBox.getItems().clear();
//        for (BuildingTypes value : BuildingTypes.values())
//            if (CityController.canProduceBuilding(value))
//                buildingsBox.getItems().add(value.getName());
//        buildingsBox.setOnMouseClicked(mouseEvent -> {
//            unitsBox.setValue("Units");
//            if (!buildingsBox.getValue().equals("Buildings")) {
//                infoText.setText(BuildingTypes.valueOf(buildingsBox.getValue().toUpperCase()).getInfo());
//            }
//        });
//        buildingsBox.getItems().add(BuildingTypes.WINDMILL.getName());
//        unemployedCitizensBox.setValue("UnemployedCitizens");
//        employedCitizensBox.setValue("EmployedCitizens");
//        unemployedCitizensBox.getItems().clear();
//        employedCitizensBox.getItems().clear();
//        for (Citizen citizen : city.getCitizens())
//            if (citizen.isWorking()) {
//                employedCitizensBox.getItems().add(citizen.getId() + "");
//            } else {
//                unemployedCitizensBox.getItems().add(citizen.getId() + "");
//            }
//    }
//
//    public void initSpinners() {
//        xTileSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, HexController.getHeight(), 1, 1));
//        yTileSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, HexController.getWidth(), 1, 1));
//        xCitizenSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, HexController.getWidth(), 1, 1));
//        yCitizenSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, HexController.getWidth(), 1, 1));
//    }
//
//    public void calcGoods() {
//        double addedGold = 0, addedFood = 0, addedProduction = 0;
//        for (Citizen citizen : city.getCitizens()) {
//            if (citizen.isWorking()) {
//                addedGold += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getGold();
//                addedFood += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getFood();
//                addedProduction += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getProduction();
//            }
//        }
//        for (Building building : city.getBuildings()) {
//            addedGold += addedGold * building.getBuildingType().getPercentOfGold() / 100;
//            addedFood += building.getBuildingType().getFood();
//            addedProduction += addedProduction * building.getBuildingType().getPercentOfProduction() / 100;
//            if (building.getName().equals("mint"))
//                addedGold += city.numberOfWorkingCitizens() * 3;
//        }
//        addedProduction += city.getCitizens().size();
//        goldText.setText("Gold : " + addedGold);
//        foodText.setText("Food : " + addedFood);
//        productionText.setText("Production : " + addedProduction);
//        populationText.setText("Population : " + city.getCitizens().size());
//        goldCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/goldLogo.png")).toString())));
//        foodCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/foodLogo.png")).toString())));
//        productionCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/productionLogo.png")).toString())));
//        populationCircle.setFill(new ImagePattern(new Image(Objects.requireNonNull(App.class.getResource("/images/resources/populationLogo.png")).toString())));
//    }
//
//    public void buyTileButtonClicked(MouseEvent mouseEvent) {
//        infoText.setText(CityController.buyTileAndAddItToCityTerritory(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()),
//                city, xTileSpinner.getValue() - 1, yTileSpinner.getValue() - 1));
//    }
//
//    public void purchaseButtonClicked(MouseEvent mouseEvent) {
//        if (!unitsBox.getValue().equals("Units")) {
//            CityController.producingUnit(UnitTypes.valueOf(unitsBox.getValue().toUpperCase()), "gold");
//            initProduction();
//        } else if (!buildingsBox.getValue().equals("Buildings")) {
//            CityController.producingBuilding(BuildingTypes.valueOf(buildingsBox.getValue().toUpperCase()), "gold");
//            initProduction();
//        }
//    }
//
//    public void unlockButtonClicked(MouseEvent mouseEvent) {
//        if (!employedCitizensBox.getValue().equals("EmployedCitizens")) {
//            infoText.setText(CityController.unlockCitizenFromTile(city, Integer.parseInt(employedCitizensBox.getValue())));
//            citizensText.setText(city.employedCitizensData() + city.unemployedCitizensData());
//        }
//        initChoiceBoxes();
//    }
//
//    public void lockButtonClicked(MouseEvent mouseEvent) {
//        if (!unemployedCitizensBox.getValue().equals("UnemployedCitizens")) {
//            infoText.setText(CityController.lockCitizenToTile(city, Integer.parseInt(unemployedCitizensBox.getValue()),
//                    xCitizenSpinner.getValue() - 1, yCitizenSpinner.getValue() - 1));
//            citizensText.setText(city.employedCitizensData() + city.unemployedCitizensData());
//        }
//        initChoiceBoxes();
//    }
//
//    public void backButtonClicked(MouseEvent mouseEvent) {
//        App.changeScene("gamePage");
//    }
//
//    public void cancelButtonClicked(MouseEvent mouseEvent) {
//        if (!productionName.getText().equals("")) {
//            city.cancelProduction();
//            initProduction();
//        }
//    }
}
