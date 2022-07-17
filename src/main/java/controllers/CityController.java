package controllers;


import enums.BuildingTypes;
import enums.tiles.TileBaseTypes;
import enums.tiles.TileFeatureTypes;
import enums.units.CombatType;
import application.App;
import enums.units.UnitTypes;
import javafx.scene.image.Image;
import models.*;
import models.tiles.Tile;
import models.units.*;

import java.util.ArrayList;
import java.util.Objects;

public class CityController {

    public static void addGoodsToCity(City city) {
        double addedFood = 0, addedGold = 0, addedProduction = 0;
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());

        for (Citizen citizen : city.getCitizens()) {
            if (citizen.isWorking()) {
                addedFood += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getFood();
                addedGold += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getGold();
                addedProduction += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getProduction();
            }
        }

        for (Building building : city.getBuildings()) {
            addedFood += building.getBuildingType().getFood();
            addedGold += addedGold * building.getBuildingType().getPercentOfGold() / 100;
            addedProduction += addedProduction * building.getBuildingType().getPercentOfProduction() / 100;
            currentCivilization.setHappiness(currentCivilization.getHappiness() + building.getBuildingType().getHappiness());
            if (building.getName().equals("mint"))
                addedGold += city.numberOfWorkingCitizens() * 3;
            if (building.getName().equals("library"))
                currentCivilization.setScience(currentCivilization.getScience() + city.getCitizens().size() / 2);
            if (building.getName().equals("university"))
                currentCivilization.setScience(currentCivilization.getScience() + 50);
            if (building.getName().equals("public_school"))
                currentCivilization.setScience(currentCivilization.getScience() + 50);
            if (building.getName().equals("university"))
                for (Tile tile : city.getTerritory())
                    if (tile.getFeature().equals(TileFeatureTypes.JUNGLE))
                        currentCivilization.setScience(currentCivilization.getScience() + 2);

        }

        double cityFood = city.getFood() + addedFood;
        double cityGold = addedGold;
        cityFood = consumeCityFood(cityFood, city);
        city.setFood(cityFood);
        currentCivilization.setGold(currentCivilization.getGold() + cityGold);
        city.setProduction(city.getProduction() + addedProduction + city.getCitizens().size());
        addCitizenIfPossible(city);
    }

    public static double consumeCityFood(double cityFood, City city) {
        while (city.getCitizens().size() * 2 > cityFood)
            starveCitizen(city);

        cityFood -= city.getCitizens().size() * 2;
        if (city.getCurrentUnit() != null && city.getCurrentUnit().getName().equals("settler"))
            cityFood = 0;

        return cityFood;
    }

    public static void starveCitizen(City city) {
        for (Citizen citizen : city.getCitizens()) {
            if (!citizen.isWorking()) {
                CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                                + " the citizen with " + citizen.getId() + " id in " + city.getName() + "died :)",
                        city.getCenterOfCity().getCivilizationName());
                city.getCitizens().remove(citizen);
                return;
            }
        }
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                        + " the citizen with " + city.getCitizens().size() + " id in " + city.getName() + "died :)",
                city.getCenterOfCity().getCivilizationName());
        city.getCitizens().remove(city.getCitizens().size() - 1);
    }

    public static void addCitizenIfPossible(City city) {
        if (city.getFood() >= city.getGrowthFoodLimit()) {
            city.setFood(0);
            city.getCitizens().add(new Citizen(city.getCitizens().size() + 1));
            city.setGrowthFoodLimit(city.getGrowthFoodLimit() * 2);
            if (!city.cityHasBuilding("courthouse")) {
                Civilization civilization = WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName());
                civilization.setHappiness(civilization.getHappiness() - 0.5);
            }
        }
    }

    public static String lockCitizenToTile(City city, int id, int x, int y) {
        for (int i = 0; i < city.getTerritory().size(); i++) {
            if (i == city.getTerritory().size() - 1 && (city.getTerritory().get(i).getX() != x || city.getTerritory().get(i).getY() != y))
                return "you should select a tile in city";
        }
        for (Citizen citizen : city.getCitizens()) {
            if (id == citizen.getId()) {
                if (citizen.isWorking())
                    return "the citizen is currently working";
                else {
                    citizen.setXOfWorkingTile(x);
                    citizen.setYOfWorkingTile(y);
                    citizen.setIsWorking(true);
                    CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                            + " you locked " + citizen.getId() + " to ( " + String.valueOf(x + 1) + " , "
                            + String.valueOf(y + 1) + " ) coordinates", city.getCenterOfCity().getCivilizationName());
                    return "citizen locked to tile successfully";
                }
            }
        }
        return "no citizen exists in the city with the given id";
    }

    public static String unlockCitizenFromTile(City city, int id) {
        for (Citizen citizen : city.getCitizens()) {
            if (id == citizen.getId()) {
                if (!citizen.isWorking())
                    return "the citizen with the given id isn't currently locked to any tile";
                else {
                    citizen.setYOfWorkingTile(-1);
                    citizen.setXOfWorkingTile(-1);
                    citizen.setIsWorking(false);
                    CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                                    + " you unlocked " + citizen.getId() + " from ( " + String.valueOf(citizen.getXOfWorkingTile() + 1) + " , "
                                    + String.valueOf(citizen.getYOfWorkingTile() + 1) + " ) coordinates",
                            city.getCenterOfCity().getCivilizationName());
                    return "citizen unlocked successfully";
                }
            }
        }
        return "no citizen exists in the city with the given id";
    }

    public static void updateCityProduction(City city) {
        if (city.getCurrentProductionRemainingCost() <= 0) {
            city.finishCityProduction();
            return;
        }

        if (city.isPayingGoldForCityProduction()) {
            Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName());
            city.setGold(currentCivilization.getGold() / currentCivilization.getCities().size());
            city.setCurrentProductionRemainingCost(Math.max(city.getCurrentProductionRemainingCost() - city.getGold(), 0));
            currentCivilization.setGold(currentCivilization.getGold() + Math.max(city.getGold() - city.getCurrentProductionRemainingCost(), 0));
            city.setGold(0);
        } else {
            city.setCurrentProductionRemainingCost(Math.max(city.getCurrentProductionRemainingCost() - city.getProduction(), 0));
            city.setGold(Math.max(city.getProduction() - city.getCurrentProductionRemainingCost(), 0));
        }

        if (city.getCurrentProductionRemainingCost() <= 0)
            city.finishCityProduction();
    }

    public static String cityProductionWarnings(City city) {
        if (city.getCurrentUnit() == null && city.getCurrentBuilding() == null)
            return "you can set something to be produced";
        if (city.getCurrentUnit() instanceof CombatUnit && city.getCenterOfCity().getCombatUnit() != null)
            return "move the combat unit away from the center in order to use the produced unit";
        if (city.getCurrentUnit() instanceof NonCombatUnit && city.getCenterOfCity().getNonCombatUnit() != null)
            return "move the nonCombat unit away from the center in order to use the produced unit";
        return null;
    }

    public static String producingUnit(UnitTypes unitEnum, String payment) {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        City wantedCity = WorldController.getSelectedCity();
        Unit unit;
        if (unitEnum.getRequiredTechnology() != null &&
                currentCivilization.getTechnologies().get(unitEnum.getRequiredTechnology()) > 0)
            return "technology" + unitEnum.getRequiredTechnology().getName() + " is required for producing this unit. you should study it first";
        if (unitEnum.getRequiredResource() != null &&
                currentCivilization.getStrategicResources().get(unitEnum.getRequiredResource().getName()) < 1)
            return "resource" + unitEnum.getRequiredResource().getName() + " is required for producing this unit. you should gain it first";

        if (unitEnum.getName().equals("settler")) {
            if (wantedCity.getCitizens().size() < 2) {
                return "can't produce settler in a city with less than 2 citizens";
            } else if (currentCivilization.getHappiness() < 0) {
                return "can't produce settlers in an unhappy civilization";
            }
            unit = new Settler(unitEnum, wantedCity.getCenterOfCity().getX(), wantedCity.getCenterOfCity().getY(), currentCivilization.getName());
        } else if (unitEnum.getName().equals("worker")) {
            unit = new Worker(unitEnum,
                    wantedCity.getCenterOfCity().getX(), wantedCity.getCenterOfCity().getY(), currentCivilization.getName());
        } else if (unitEnum.getRangedCombatStrength() == 0) {
            unit = new Melee(unitEnum,
                    wantedCity.getCenterOfCity().getX(), wantedCity.getCenterOfCity().getY(), currentCivilization.getName());
        } else {
            unit = new Ranged(unitEnum,
                    wantedCity.getCenterOfCity().getX(), wantedCity.getCenterOfCity().getY(), currentCivilization.getName());
        }

        if (unitEnum.getRequiredResource() != null)
            currentCivilization.getStrategicResources().put(unitEnum.getRequiredResource().getName(),
                    currentCivilization.getStrategicResources().get(unitEnum.getRequiredResource().getName()) - 1);
        wantedCity.setCurrentUnit(unit);
        wantedCity.setPayingGoldForCityProduction(payment.equals("gold"));
        wantedCity.setCurrentProductionRemainingCost(unitEnum.getCost());
        if (wantedCity.cityHasBuilding("stable") && unit.getCombatType().equals(CombatType.MOUNTED))
            wantedCity.setCurrentProductionRemainingCost(unitEnum.getCost() * 3 / 4);
        if (wantedCity.cityHasBuilding("forge"))
            wantedCity.setCurrentProductionRemainingCost(unitEnum.getCost() * 3 / 20);
        if (wantedCity.cityHasBuilding("forge"))
            wantedCity.setCurrentProductionRemainingCost(unitEnum.getCost() * 4 / 5);
        currentCivilization.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " you started producing "
                + unit.getName() + " in ( " + String.valueOf(WorldController.getSelectedCity().getCenterOfCity().getX() + 1)
                + " , " + String.valueOf(WorldController.getSelectedCity().getCenterOfCity().getY() + 1) + " ) coordinates");
        return null;
    }

    public static String producingBuilding(Building building, String payment) {
        City wantedCity = WorldController.getSelectedCity();
        if (cityCanProduceBuilding(wantedCity, building)) {
            wantedCity.setCurrentBuilding(building);
            wantedCity.setPayingGoldForCityProduction(payment.equals("gold"));

            if (wantedCity.cityHasBuilding("workshop")) {
                wantedCity.setCurrentProductionRemainingCost(building.getCost() * 4 / 5);
            } else {
                wantedCity.setCurrentProductionRemainingCost(building.getCost());
            }
            CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " you started producing "
                            + "building" + " in ( " + String.valueOf(WorldController.getSelectedCity().getCenterOfCity().getX() + 1)
                            + " , " + String.valueOf(WorldController.getSelectedCity().getCenterOfCity().getY() + 1) + " ) coordinates",
                    WorldController.getWorld().getCurrentCivilizationName());
            return null;
        }
        return "couldn't build building";
    }

    public static boolean cityCanProduceBuilding(City city, Building building) {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName());
        if ((building.getBuildingType().equals(BuildingTypes.WINDMILL) && city.getCenterOfCity().getType().equals(TileBaseTypes.HILL))
                || (building.getBuildingType().isRequiresRiver() && !city.getCenterOfCity().hasRiver())
                || (building.getBuildingType().getRequiredTechnology() != null &&
                currentCivilization.getTechnologies().get(building.getBuildingType().getRequiredTechnology()) > 0)
                || (building.getBuildingType().getRequiredBuildings() != null &&
                !city.cityHasRequiredBuildings(building.getBuildingType().getRequiredBuildings()))) {
            return false;
        }
        if (building.getBuildingType().getRequiredResource() != null) {
            for (int i = 0; i < city.getTerritory().size(); i++) {
                if (building.getBuildingType().getRequiredResource().getName().equals(city.getTerritory().get(i).getResource().getName())) {
                    if (currentCivilization.getStrategicResources().get(building.getBuildingType().getRequiredResource().getName()) > 0) {
                        currentCivilization.getStrategicResources().put(building.getBuildingType().getRequiredResource().getName(), currentCivilization.getStrategicResources().get(building.getBuildingType().getRequiredResource().getName()) - 1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String buyTileAndAddItToCityTerritory(Civilization civilization, City city, int tileX, int tileY) {
        if (civilization.getGold() < 100)
            return "you don't have enough gold for buying this tile";
        civilization.setGold(civilization.getGold() - 100);
        Tile tile = MapController.getMap()[tileX][tileY];
        tile.setCivilization(civilization.getName());
        city.getTerritory().add(tile);
        CivilizationController.updateMapVision(civilization);
        return "tile was bought successfully";
    }

    public static String unemployedCitizensData(City city) {
        ArrayList<Citizen> unemployedCitizens = new ArrayList<>();
        for (Citizen citizen : city.getCitizens())
            if (!citizen.isWorking())
                unemployedCitizens.add(citizen);
        if (unemployedCitizens.size() == 0)
            return "there is no unemployed citizen in this city\n";
        StringBuilder output = new StringBuilder("unemployed citizens:\n");
        int counter = 1;
        for (Citizen unemployedCitizen : unemployedCitizens) {
            output.append(counter).append("- citizen with id ").append(unemployedCitizen.getId()).append('\n');
            counter++;
        }
        return output.toString();
    }

    public static String employedCitizensData(City city) {
        ArrayList<Citizen> employedCitizens = new ArrayList<>();
        for (Citizen citizen : city.getCitizens())
            if (citizen.isWorking())
                employedCitizens.add(citizen);
        if (employedCitizens.size() == 0)
            return "there is no employed citizen in this city\n";
        StringBuilder output = new StringBuilder("employed citizens:\n");
        int counter = 1;
        for (Citizen employedCitizen : employedCitizens) {
            output.append(counter).append("- citizen with id ").append(employedCitizen.getId());
            output.append(" is working on tile ").append(employedCitizen.getXOfWorkingTile() + 1).append(" and ");
            output.append(employedCitizen.getYOfWorkingTile() + 1).append('\n');
            counter++;
        }
        return output.toString();
    }

    public static void conquerCity(City city, CombatUnit unit) {
        for (Tile tile : city.getTerritory())
            tile.setCivilization(unit.getCivilizationName());
        WorldController.getWorld().getCivilizationByName(unit.getCivilizationName()).addCity(city);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you conquered the " + city.getName() + "city", unit.getCivilizationName());
    }

    public static void destroyCity(City city, CombatUnit unit) {
        for (Tile tile : city.getTerritory())
            tile.setCivilization(null);
        WorldController.getWorld().getCivilizationByName(unit.getCivilizationName()).addCity(city);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you destroyed the " + city.getName() + "city", unit.getCivilizationName());
    }

    public static Image getCenterImage() {
        return new Image(Objects.requireNonNull(App.class.getResource("/images/cities/cityCenter.png")).toString());
    }

    public static Image getDistrictImage() {
        return new Image(Objects.requireNonNull(App.class.getResource("/images/cities/cityDistrict.png")).toString());
    }
}
