package controllers;

import models.City;
import models.Civilization;
import models.units.Unit;

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
        civilization.getTechnologies().put(civilization.getCurrentTechnology(),
                civilization.getTechnologies().get(civilization.getCurrentTechnology())-1);
        if (civilization.getTechnologies().get(civilization.getCurrentTechnology()) <= 0) {
            civilization.setCurrentTechnology(null);
        }
    }

    public static void updateGoods(Civilization civilization) {
        for (City city : civilization.getCities()) {
            CityController.addGoodsToCity(city);
        }
    }

    public static void updateCitiesProductions(Civilization civilization){
        for (City city : civilization.getCities()) {
            CityController.updateCityProduction(city);
        }
    }
}
