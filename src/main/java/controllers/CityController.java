package controllers;

import models.*;
import models.units.*;

import java.util.ArrayList;

public class CityController {

    public static City getCityByName(String name) {
        for (Civilization civilization : WorldController.getWorld().getAllCivilizations()) {
            for (City city : civilization.getCities()) {
                if (city.getName().equals(name))
                    return city;
            }
        }
        return null;
    }

    public static boolean civilizationHasDiscoveredCity(Civilization civilization, City city) {
        return false;
    }

    public static void addGoodsToCity(City city) {
        double addedFood = 0;
        double addedGold = 0;
        double addedProduction = 0;
        for (Citizen citizen : city.getCitizens()) {
            if (citizen.isWorking()) {
                for (Tile tile : city.getTerritory()) {
                    if (citizen.getXOfWorkingTile() == tile.getX() && citizen.getYOfWorkingTile() == tile.getY()) {
                        addedFood += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getFood();
                        addedGold += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getGold();
                        addedProduction += MapController.getMap()[citizen.getXOfWorkingTile()][citizen.getYOfWorkingTile()].getProduction();
                    }
                }
            }
        }
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        double cityFood = city.getFood() + addedFood;
        double cityGold = addedGold;
        double cityProduction = city.getProduction() + addedProduction;
        cityProduction += city.getCitizens().size();
        cityFood = consumeCityFood(cityFood, city);
        city.setFood(cityFood);
        currentCivilization.setGold(currentCivilization.getGold() + cityGold);
        city.setProduction(cityProduction);
        addCitizenIfPossible(city);
    }

    public static double consumeCityFood(double cityFood, City city) {
        while (city.getCitizens().size() * 2 > cityFood) {
            starveCitizen(city.getCitizens());
        }
        cityFood -= city.getCitizens().size() * 2;

        if (city.getCurrentUnit() != null && city.getCurrentUnit().getName().equals("settler"))
            cityFood = 0;

        return cityFood;
    }

    public static void starveCitizen(ArrayList<Citizen> citizens) {
        for (Citizen citizen : citizens) {
            if (!citizen.isWorking()) {
                citizens.remove(citizen);
                return;
            }
        }
        citizens.remove(citizens.size() - 1);
    }

    public static void addCitizenIfPossible(City city) {
        if (city.getFood() >= city.getGrowthFoodLimit()) {
            city.setFood(0);
            city.getCitizens().add(new Citizen(city.getCitizens().size() + 1));
            city.setGrowthFoodLimit(city.getGrowthFoodLimit() * 2);
            WorldController.getWorld().getCivilizationByName(MapController.getTileByCoordinates(city.getCenterOfCity().getX(),
                    city.getCenterOfCity().getY()).getCivilizationName()).changeHappiness(-0.5);
        }
    }

    public static String lockCitizenToTile(City city, int id, int x, int y) {
        for (Citizen citizen : city.getCitizens()) {
            if (id == citizen.getId()) {
                if (citizen.isWorking()) return "the citizen is currently working";
                else {
                    citizen.setXOfWorkingTile(x);
                    citizen.setYOfWorkingTile(y);
                    citizen.setIsWorking(true);
                    x++;
                    y++;
                    String notification = "In turn " + WorldController.getWorld().getActualTurn() + " you locked " +
                            citizen.getId() + " to ( " + x + " , " + y + " ) coordinates";
                    WorldController.getWorld().getCivilizationByName(MapController.getTileByCoordinates(x - 1, y - 1).getCivilizationName()).addNotification(notification);
                    return "citizen locked to tile successfully";
                }
            }
        }
        return "no citizen exists in the city with the given id";
    }

    public static String unlockCitizenFromTile(City city, int id) {
        for (Citizen citizen : city.getCitizens()) {
            if (id == citizen.getId()) {
                if (!citizen.isWorking()) return "the citizen with the given id isn't currently locked to any tile";
                else {
                    citizen.setYOfWorkingTile(-1);
                    citizen.setXOfWorkingTile(-1);
                    citizen.setIsWorking(false);
                    int x = citizen.getXOfWorkingTile() + 1, y = citizen.getYOfWorkingTile() + 1;
                    String notification = "In turn " + WorldController.getWorld().getActualTurn() + " you unlocked " +
                            citizen.getId() + " from ( " + x + " , " + y + " ) coordinates";
                    WorldController.getWorld().getCivilizationByName(MapController.getTileByCoordinates(x - 1, y - 1).getCivilizationName()).addNotification(notification);
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
            Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
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

    public static String producingUnit(enums.units.Unit unitEnum, String payment) {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        City wantedCity = WorldController.getSelectedCity();
        Unit unit;
        if (unitEnum.getRequiredTechnology() != null &&
                currentCivilization.getTechnologies().get(unitEnum.getRequiredTechnology()) > 0) {
            return "technology" + unitEnum.getRequiredTechnology().getName() + " is required for producing this unit. you should study it first";
        }

        if (unitEnum.getRequiredResource() != null &&
                currentCivilization.getStrategicResources().get(unitEnum.getRequiredResource().nameGetter()) < 1) {
            return "resource" + unitEnum.getRequiredResource().nameGetter() + " is required for producing this unit. you should gain it first";
        }

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

        if (unitEnum.getRequiredResource() != null){
            currentCivilization.getStrategicResources().put(unitEnum.getRequiredResource().nameGetter(),
                    currentCivilization.getStrategicResources().get(unitEnum.getRequiredResource().nameGetter()) - 1);
        }

        wantedCity.setCurrentUnit(unit);
        wantedCity.setPayingGoldForCityProduction(payment.equals("gold"));
        wantedCity.setCurrentProductionRemainingCost(unitEnum.getCost());

        int x = WorldController.getSelectedCity().getCenterOfCity().getX()+1;
        int y = WorldController.getSelectedCity().getCenterOfCity().getY()+1;
        String notification = "In turn " + WorldController.getWorld().getActualTurn() + " you started producing " +
                unit.getName() + " in ( " + x + " , " + y + " ) coordinates";
        currentCivilization.addNotification(notification);
        return null;
    }

    public static String producingBuilding(Building building, String payment) {
        City wantedCity = WorldController.getSelectedCity();
        wantedCity.setCurrentBuilding(building);
        wantedCity.setPayingGoldForCityProduction(payment.equals("gold"));
        wantedCity.setCurrentProductionRemainingCost(building.getCost());
        int x = WorldController.getSelectedCity().getCenterOfCity().getX()+1;
        int y = WorldController.getSelectedCity().getCenterOfCity().getY()+1;
        String notification = "In turn " + WorldController.getWorld().getActualTurn() + " you started producing " +
                "building" + " in ( " + x + " , " + y + " ) coordinates";
        WorldController.getWorld().getCivilizationByName(MapController.getTileByCoordinates(x-1, y-1).getCivilizationName()).addNotification(notification);
        return null;
    }

    public static String buyTileAndAddItToCityTerritory(Civilization civilization, City city, int tileX, int tileY){
        if (civilization.getGold() < 100) return "you don't have enough gold for buying this tile";

        civilization.setGold(civilization.getGold() - 100);
        Tile tile = MapController.getMap()[tileX][tileY];
        tile.setCivilization(civilization.getName());
        city.getTerritory().add(tile);
        return "tile was bought successfully";
    }

    public static String unemployedCitizensData(City city){
        ArrayList<Citizen> unemployedCitizens = new ArrayList<>();
        for (Citizen citizen : city.getCitizens()) {
            if (!citizen.isWorking())
                unemployedCitizens.add(citizen);
        }
        if (unemployedCitizens.size() == 0)
            return "there is no unemployed citizen in this city";
        StringBuilder output = new StringBuilder("unemployed citizens:\n");
        int counter = 1;
        for (Citizen unemployedCitizen : unemployedCitizens) {
            output.append(counter).append("- citizen with id ").append(unemployedCitizen.getId()).append('\n');
            counter++;
        }
        return output.toString();
    }

    public static String employedCitizensData(City city){
        ArrayList<Citizen> employedCitizens = new ArrayList<>();
        for (Citizen citizen : city.getCitizens()) {
            if (citizen.isWorking())
                employedCitizens.add(citizen);
        }
        if (employedCitizens.size() == 0)
            return "there is no employed citizen in this city";
        StringBuilder output = new StringBuilder("employed citizens:\n");
        int counter = 1;
        for (Citizen employedCitizen : employedCitizens) {
            output.append(counter).append("- citizen with id ").append(employedCitizen.getId());
            output.append(" is working on tile ").append(employedCitizen.getXOfWorkingTile()).append(" and ");
            output.append(employedCitizen.getYOfWorkingTile()).append('\n');
            counter++;
        }
        return output.toString();
    }

}
