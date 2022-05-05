package controllers;

import models.Citizen;
import models.City;
import models.Civilization;
import models.Tile;
import models.units.CombatUnit;
import models.units.NonCombatUnit;

import java.util.ArrayList;

public class CityController {

    public static City getCityByName(String name) {
        ArrayList<String> allCitiesNames = new ArrayList<>();
        for (Civilization civilization : WorldController.getWorld().getAllCivilizations()) {
            for (City city : civilization.getCities()) {
                if (city.getName().equals(name))
                    return city;
            }
        }
        return null;
    }

    public static boolean cityExistsInTile(Tile tile) {
        return false;
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
                    if (citizen.getxOfWorkingTile() == tile.getX() && citizen.getYOfWorkingTile() == tile.getY()) {
                        addedFood += MapController.getMap()[citizen.getxOfWorkingTile()][citizen.getYOfWorkingTile()].getFood();
                        addedGold += MapController.getMap()[citizen.getxOfWorkingTile()][citizen.getYOfWorkingTile()].getGold();
                        addedProduction += MapController.getMap()[citizen.getxOfWorkingTile()][citizen.getYOfWorkingTile()].getProduction();
                    }
                }
            }
        }
        double cityFood = city.getFood() + addedFood;
        double cityGold = city.getGold() + addedGold;
        double cityProduction = city.getProduction() + addedProduction;
        cityFood = consumeCityFood(cityFood, city.getCitizens());
        city.setFood(cityFood);
        city.setGold(cityGold);
        city.setProduction(cityProduction);
        addCitizenIfPossible(city);
    }

    public static double consumeCityFood(double cityFood, ArrayList<Citizen> citizens) {
        while (citizens.size() * 2 > cityFood) {
            starveCitizen(citizens);
        }
        cityFood -= citizens.size() * 2;
        return cityFood;
    }

    public static void starveCitizen(ArrayList<Citizen> citizens){
        for (Citizen citizen : citizens) {
            if (!citizen.isWorking()){
                citizens.remove(citizen);
                return;
            }
        }
        citizens.remove(citizens.size() - 1);
    }

    public static void addCitizenIfPossible(City city){
        if (city.getFood() >= city.getGrowthFoodLimit()){
            city.setFood(0);
            city.getCitizens().add(new Citizen(city.getCitizens().size() + 1));
            city.setGrowthFoodLimit(city.getGrowthFoodLimit() * 2);
        }
    }

    public static String lockCitizenToTile(City city, int id, int x, int y){
        for (Citizen citizen : city.getCitizens()) {
            if (id == citizen.getId()){
                if (citizen.isWorking()) return "the citizen is currently working";
                else {
                    citizen.setXOfWorkingTile(x);
                    citizen.setYOfWorkingTile(y);
                    citizen.setIsWorking(true);
                    return "citizen locked to tile successfully";
                }
            }
        }
        return "no citizen exists in the city with the given id";
    }

    public static String unlockCitizenFromTile(City city, int id){
        for (Citizen citizen : city.getCitizens()) {
            if (id == citizen.getId()){
                if (!citizen.isWorking()) return "the citizen with the given id isn't currently locked to any tile";
                else {
                    citizen.setYOfWorkingTile(-1);
                    citizen.setXOfWorkingTile(-1);
                    citizen.setIsWorking(false);
                    return "citizen unlocked successfully";
                }
            }
        }
        return "no citizen exists in the city with the given id";
    }

    public static void updateCityProduction(City city){
        if (city.getCurrentProductionRemainingCost() <= 0) {
            city.finishCityProduction();
            return;
        }

        if (city.isPayingGoldForCityProduction()) {
            city.setCurrentProductionRemainingCost(Math.max(city.getCurrentProductionRemainingCost() - city.getGold(), 0));
            city.setGold(Math.max(city.getGold() - city.getCurrentProductionRemainingCost(), 0));
        }else {
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

}
