package Server.controllers;


import Server.Main;
import Server.enums.BuildingTypes;
import Server.enums.QueryResponses;
import Server.enums.tiles.TileBaseTypes;
import Server.enums.tiles.TileFeatureTypes;
import Server.enums.units.CombatType;
import Server.enums.units.UnitTypes;
import Server.models.Building;
import Server.models.Citizen;
import Server.models.City;
import Server.models.Civilization;
import Server.models.tiles.Tile;
import Server.models.units.*;
import javafx.scene.image.Image;

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

    public static QueryResponses lockCitizenToTile(City city, int id, int x, int y) {
        for (int i = 0; i < city.getTerritory().size(); i++) {
            if (city.getTerritory().get(i).getX() == x && city.getTerritory().get(i).getY() == y)
                break;
            if (i == city.getTerritory().size() - 1 && (city.getTerritory().get(i).getX() != x || city.getTerritory().get(i).getY() != y))
                return QueryResponses.SELECT_TILE_IN_CITY;
        }
        for (Citizen citizen : city.getCitizens()) {
            if (id == citizen.getId()) {
                citizen.setXOfWorkingTile(x);
                citizen.setYOfWorkingTile(y);
                citizen.setIsWorking(true);
                CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                        + " you locked " + citizen.getId() + " to ( " + String.valueOf(x + 1) + " , "
                        + String.valueOf(y + 1) + " ) coordinates", city.getCenterOfCity().getCivilizationName());
                return QueryResponses.OK;
            }
        }
        return QueryResponses.NO_CITIZEN_EXISTS;
    }

    public static QueryResponses unlockCitizenFromTile(City city, int id) {
        for (Citizen citizen : city.getCitizens()) {
            if (id == citizen.getId()) {
                citizen.setYOfWorkingTile(-1);
                citizen.setXOfWorkingTile(-1);
                citizen.setIsWorking(false);
                CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                                + " you unlocked " + citizen.getId() + " from ( " + String.valueOf(citizen.getXOfWorkingTile() + 1) + " , "
                                + String.valueOf(citizen.getYOfWorkingTile() + 1) + " ) coordinates",
                        city.getCenterOfCity().getCivilizationName());
                return QueryResponses.OK;
            }
        }
        return QueryResponses.NO_CITIZEN_EXISTS;
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

    public static void producingUnit(UnitTypes unitEnum, String payment) {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        City wantedCity = WorldController.getSelectedCity();
        Unit unit;
        if (unitEnum.getName().equals("settler")) {
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
    }

    public static Boolean canProduceUnit(UnitTypes unitEnum) {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        City wantedCity = WorldController.getSelectedCity();
        return (unitEnum.getRequiredTechnology() == null ||
                currentCivilization.getTechnologies().get(unitEnum.getRequiredTechnology()) <= 0)
                && (unitEnum.getRequiredResource() == null ||
                currentCivilization.getStrategicResources().get(unitEnum.getRequiredResource().getName()) >= 1)
                && (!unitEnum.getName().equals("settler") || (wantedCity.getCitizens().size() >= 2 && !(currentCivilization.getHappiness() < 0)));
    }

    public static void producingBuilding(BuildingTypes buildingType, String payment) {
        City wantedCity = WorldController.getSelectedCity();
        Building building = new Building(buildingType);
        wantedCity.setCurrentBuilding(building);
        wantedCity.setPayingGoldForCityProduction(payment.equals("gold"));
        if (wantedCity.cityHasBuilding("workshop")) {
            wantedCity.setCurrentProductionRemainingCost(buildingType.getCost() * 4 / 5);
        } else {
            wantedCity.setCurrentProductionRemainingCost(buildingType.getCost());
        }
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " you started producing "
                        + buildingType.getName() + " in ( " + String.valueOf(WorldController.getSelectedCity().getCenterOfCity().getX() + 1)
                        + " , " + String.valueOf(WorldController.getSelectedCity().getCenterOfCity().getY() + 1) + " ) coordinates",
                WorldController.getWorld().getCurrentCivilizationName());
    }

    public static boolean canProduceBuilding(BuildingTypes building) {
        City city = WorldController.getSelectedCity();
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName());
        if ((building.equals(BuildingTypes.WINDMILL) && city.getCenterOfCity().getType().equals(TileBaseTypes.HILL))
                || (building.isRequiresRiver() && !city.getCenterOfCity().hasRiver())
                || (building.getRequiredTechnology() != null &&
                currentCivilization.getTechnologies().get(building.getRequiredTechnology()) > 0)
                || (building.getRequiredBuildings() != null &&
                !city.cityHasRequiredBuildings(building.getRequiredBuildings()))) {
            return false;
        }
        if (building.getRequiredResource() != null) {
            for (int i = 0; i < city.getTerritory().size(); i++) {
                if (building.getRequiredResource().getName().equals(city.getTerritory().get(i).getResource().getName())) {
                    if (currentCivilization.getStrategicResources().get(building.getRequiredResource().getName()) > 0) {
                        currentCivilization.getStrategicResources().put(building.getRequiredResource().getName(), currentCivilization.getStrategicResources().get(building.getRequiredResource().getName()) - 1);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static QueryResponses buyTileAndAddItToCityTerritory(Civilization civilization, City city, int tileX, int tileY) {
        if (civilization.getGold() < 100)
            return QueryResponses.NOT_ENOUGH_GOLD;
        ArrayList<Tile> tiles = new ArrayList<>();
        for (Tile tile : city.getTerritory())
            tiles.addAll(TileController.getAvailableNeighbourTiles(tile.getX(), tile.getY()));
        for (Tile tile : tiles) {
            if (tile.getX() == tileX && tile.getY() == tileY) {
                if (tile.getCivilizationName() != null && tile.getCivilizationName().equals(city.getCenterOfCity().getCivilizationName()))
                    return QueryResponses.ALREADY_HAVE_TILE;
                civilization.setGold(civilization.getGold() - 100);
                Tile bought = MapController.getMap()[tileX][tileY];
                bought.setCivilization(civilization.getName());
                city.getTerritory().add(bought);
                CivilizationController.updateMapVision(civilization);
                return QueryResponses.OK;
            }
        }
        return QueryResponses.CANT_BUY_THIS_TILE;
    }

    public static void conquerCity(City city, CombatUnit unit) {
        City actualCity = WorldController.getWorld().getCivilizationByName(city.getCivilizationName()).getCityByName(city.getName());
        for (Tile tile : actualCity.getTerritory())
            tile.setCivilization(unit.getCivilizationName());
        WorldController.getWorld().getCivilizationByName(unit.getCivilizationName()).addCity(city);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you conquered the " + city.getName() + "city", unit.getCivilizationName());
    }

    public static void destroyCity(City city, CombatUnit unit) {
        City actualCity = WorldController.getWorld().getCivilizationByName(city.getCivilizationName()).getCityByName(city.getName());
        for (Tile tile : actualCity.getTerritory())
            tile.setCivilization(null);
        MapController.getMap()[city.getCenterOfCity().getX()][city.getCenterOfCity().getY()].setCity(null);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you destroyed the " + city.getName() + "city", unit.getCivilizationName());
    }

    public static double getCityGold(City city) {
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
        double addedGold = 0;
        for (Citizen citizen : city.getCitizens()) {
            if (citizen.isWorking()) {
                addedGold += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getGold();
            }
        }
        for (Building building : city.getBuildings()) {
            addedGold += addedGold * building.getBuildingType().getPercentOfGold() / 100;
            if (building.getName().equals("mint"))
                addedGold += city.numberOfWorkingCitizens() * 3;
        }
        return addedGold;
    }

    public static double getCityFood(City city) {
        double addedFood = 0;
        for (Citizen citizen : city.getCitizens()) {
            if (citizen.isWorking()) {
                addedFood += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getFood();
            }
        }
        for (Building building : city.getBuildings()) {
            addedFood += building.getBuildingType().getFood();
        }
        return addedFood;
    }

    public static double getCityProduction(City city) {
        double addedProduction = 0;
        for (Citizen citizen : city.getCitizens()) {
            if (citizen.isWorking()) {
                addedProduction += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getProduction();
            }
        }
        for (Building building : city.getBuildings()) {
            addedProduction += addedProduction * building.getBuildingType().getPercentOfProduction() / 100;
        }
        addedProduction += city.getCitizens().size();
        return addedProduction;
    }
}
