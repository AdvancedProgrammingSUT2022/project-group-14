package controllers;

import models.Citizen;
import models.City;
import models.Civilization;
import models.Tile;

import java.util.ArrayList;

public class CityController {

    public static City getCityByName(String name) {
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
        }
    }


}
