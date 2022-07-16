package controllers;

import models.*;
import models.tiles.Ruin;
import models.tiles.Tile;
import models.units.Unit;

public class CivilizationController {

    public static void updateMapVision(Civilization civilization) {
        int[][] visionState = civilization.getVisionStatesOfMap();
        for (int i = 0; i < MapController.width; i++) {
            for (int j = 0; j < MapController.height; j++) {
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
            if (TileController.coordinatesAreInRange(unit.getCurrentX(), unit.getCurrentY(), x, y, 2))
                return true;
        }
        for (City city : civilization.getCities()) {
            for (Tile tile : city.getTerritory()) {
                if (TileController.coordinatesAreInRange(tile.getX(), tile.getY(), x, y, 1)){
                    return true;
                }
            }
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

    public static void payRequiredPriceForKeepingBuildings(Civilization civilization){
        for (City city : civilization.getCities()) {
            for (Building building : city.getBuildings()) {
                if (civilization.getGold() > 0) civilization.setGold(civilization.getGold() - building.getMaintenance());
                else civilization.setScience(civilization.getScience() - building.getMaintenance());
            }
        }
    }

    public static void updateRuins(Civilization civilization) {
        for (Unit unit : civilization.getAllUnits()) {
            Tile tile = MapController.getMap()[unit.getCurrentX()][unit.getCurrentY()];
            Ruin ruin = tile.getRuin();
            if (ruin != null) {
                if (ruin.getFreeTechnology() != null) {
                    civilization.getTechnologies().put(ruin.getFreeTechnology(), 0);
                }
                if (ruin.isProvideCitizen()) {
                    for (City city : civilization.getCities()) {
                        city.getCitizens().add(new Citizen(city.getCitizens().size() + 1));
                    }
                }
                if (tile.getNonCombatUnit() == null && ruin.getNonCombatUnit() != null) {
                    ruin.getNonCombatUnit().setCivilizationName(civilization.getName());
                    tile.setNonCombatUnit(ruin.getNonCombatUnit());
                }
                civilization.setGold(civilization.getGold() + ruin.getGold());
                tile.setRuin(null);
            }

        }
    }
}
