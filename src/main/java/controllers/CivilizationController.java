package controllers;

import models.City;
import models.Civilization;
import models.Tile;
import models.units.Melee;
import models.units.Unit;

import java.util.ArrayList;

public class CivilizationController {

    public static void updateMapVision(Civilization civilization) {
        int[][] visionState = civilization.getVisionStatesOfMap();
        for (int i = 0; i < visionState.length; i++) {
            for (int j = 0; j < visionState.length; j++) {
                if (visionState[i][j] == 0 && tileIsInRange(i, j, civilization)) {
                    visionState[i][j] = 2;
                } else if (visionState[i][j] == 1 && tileIsInRange(i, j, civilization)) {
                    visionState[i][j] = 2;
                    civilization.getRevealedTiles()[i][j] = null;
                } else if (visionState[i][j] == 2 && !tileIsInRange(i, j, civilization)) {
                    visionState[i][j] = 1;
                    civilization.getRevealedTiles()[i][j] = MapController.getTileByCoordinates(i, j);
                }
            }
        }
    }

    public static boolean tileIsInRange(int x, int y, Civilization civilization) {
        for (Unit unit : civilization.getAllUnits()) {
            if (Math.abs(x - unit.getCurrentX()) <= 2 && Math.abs(y - unit.getCurrentY()) <= 2)
                return true;
        }
        for (City city : civilization.getCities()) {
            //TODO return true if its in range
        }

        return false;
    }

    public static void updateTechnology(Civilization civilization) {
        if (civilization.getCurrentTechnology() == null)
            return;

        int civilizationScience = (int) civilization.getScience();
        civilization.getTechnologies().put(civilization.getCurrentTechnology(),
                civilization.getTechnologies().get(civilization.getCurrentTechnology()) - civilizationScience);
        civilization.setScience(0);
        if (civilization.getTechnologies().get(civilization.getCurrentTechnology()) <= 0) {
            for (City city : civilization.getCities()) {
                for (Tile tile : city.getTerritory()) {
                    tile.addAvailableResourcesToCivilizationAndTile();
                }
            }
            civilization.setCurrentTechnology(null);
        }
    }

    public static void updateCitiesGoods(Civilization civilization) {
        for (City city : civilization.getCities()) {
            CityController.addGoodsToCity(city);
        }
    }

    public static void updateCitiesProductions(Civilization civilization){
        for (City city : civilization.getCities()) {
            CityController.updateCityProduction(city);
        }
    }

    public static void updateScience(Civilization civilization){
        double addedScience = 3;
        for (City city : civilization.getCities()) {
            addedScience += city.getCitizens().size();
        }
        civilization.setScience(civilization.getScience() + addedScience);
    }

    public static void payRequiredPriceForKeepingRoadsAndRailroads(Civilization civilization){
        for (City city : civilization.getCities()) {
            for (Tile tile : city.getTerritory()) {
                if (tile.getRoadState() == 0 || tile.getRailRoadState() == 0){
                    if (civilization.getGold() > 0) civilization.setGold(civilization.getGold() - 1);
                    else civilization.setScience(civilization.getScience() - 1);
                }
            }
        }
    }

    public static void payRequiredPriceForKeepingUnits(Civilization civilization){
        for (int i = 0; i < civilization.getAllUnits().size(); i++) {
            if (civilization.getGold() > 0) civilization.setGold(civilization.getGold() - 1);
            else civilization.setScience(civilization.getScience() - 1);
        }
    }
}
