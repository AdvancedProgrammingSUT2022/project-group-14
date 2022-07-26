package Server.controllers;

import Server.enums.QueryResponses;
import Server.enums.Technologies;
import Server.models.*;
import Server.models.network.Response;
import javafx.scene.paint.Color;
import Server.models.Building;
import Server.models.Citizen;
import Server.models.City;
import Server.models.Civilization;

import Server.models.tiles.Ruin;
import Server.models.tiles.Tile;
import Server.models.units.Settler;
import Server.models.units.Unit;
import Server.models.units.Worker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

public class CivilizationController {

    public static void updateMapVision(Civilization civilization) {
        int[][] visionState = civilization.getVisionStatesOfMap();
        for (int i = 0; i < MapController.width; i++) {
            for (int j = 0; j < MapController.height; j++) {
                if (visionState[i][j] == 0 && hasVisionOnTile(i, j, civilization)) {
                    visionState[i][j] = 2;
                } else if (visionState[i][j] == 1 && hasVisionOnTile(i, j, civilization)) {
                    visionState[i][j] = 2;
                    civilization.getRevealedTiles()[i][j] = null;
                } else if (visionState[i][j] == 2 && !hasVisionOnTile(i, j, civilization)) {
                    visionState[i][j] = 1;
                    civilization.getRevealedTiles()[i][j] = MapController.getTileByCoordinates(i, j);
                }
            }
        }
    }

    public static boolean hasVisionOnTile(int x, int y, Civilization civilization) {
        for (Unit unit : civilization.getAllUnits()) {
            if (TileController.coordinatesAreInRange(unit.getCurrentX(), unit.getCurrentY(), x, y, 2))
                return true;
        }
        for (City city : civilization.getCities()) {
            for (Tile tile : city.getTerritory()) {
                if (TileController.coordinatesAreInRange(tile.getX(), tile.getY(), x, y, 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    public static boolean civilizationHasTechnology(Technologies technology) {
        return WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getTechnologies().get(technology) <= 0;
    }

    public static void updateTechnology(Civilization civilization) {
        if (civilization.getCurrentTechnology() == null)
            return;

        int civilizationScience = (int) civilization.getScience();
        civilization.getTechnologies().put(civilization.getCurrentTechnology(),
                Math.max(civilization.getTechnologies().get(civilization.getCurrentTechnology()) - civilizationScience, 0));
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

    public static void updateCitiesProductions(Civilization civilization) {
        for (City city : civilization.getCities()) {
            CityController.updateCityProduction(city);
        }
    }

    public static void updateScience(Civilization civilization) {
        double addedScience = 3;
        for (City city : civilization.getCities()) {
            addedScience += city.getCitizens().size();
        }
        civilization.setScience(civilization.getScience() + addedScience);
    }

    public static void payRequiredPriceForKeepingRoadsAndRailroads(Civilization civilization) {
        for (City city : civilization.getCities()) {
            for (Tile tile : city.getTerritory()) {
                if (tile.getRoadState() == 0 || tile.getRailRoadState() == 0) {
                    if (civilization.getGold() > 0) civilization.setGold(civilization.getGold() - 1);
                    else civilization.setScience(civilization.getScience() - 1);
                }
            }
        }
    }

    public static void payRequiredPriceForKeepingUnits(Civilization civilization) {
        for (int i = 0; i < civilization.getAllUnits().size(); i++) {
            if (civilization.getGold() > 0) civilization.setGold(civilization.getGold() - 1);
            else civilization.setScience(civilization.getScience() - 1);
        }
    }

    public static void payRequiredPriceForKeepingBuildings(Civilization civilization) {
        for (City city : civilization.getCities()) {
            for (Building building : city.getBuildings()) {
                if (civilization.getGold() > 0)
                    civilization.setGold(civilization.getGold() - building.getMaintenance());
                else civilization.setScience(civilization.getScience() - building.getMaintenance());
            }
        }
    }

    public static void updateRuins(Civilization civilization) {
        for (Unit unit : civilization.getAllUnits()) {
            Tile tile = MapController.getMap()[unit.getCurrentX()][unit.getCurrentY()];
            Ruin ruin = tile.getRuin();
            if (ruin != null) {
                String found = "Found ruin! : ";
                if (ruin.getFreeTechnology() != null) {
                    civilization.getTechnologies().put(ruin.getFreeTechnology(), 0);
                    found += "FreeTech + ";
                }
                if (ruin.isProvideCitizen()) {
                    found += "Provided citizens + ";
                    for (City city : civilization.getCities())
                        city.getCitizens().add(new Citizen(city.getCitizens().size() + 1));
                }
                if (tile.getNonCombatUnit() == null && ruin.getNonCombatUnit() != null) {
                    ruin.getNonCombatUnit().setCivilizationName(civilization.getName());
                    if (ruin.getNonCombatUnit() instanceof Worker) {
                        civilization.getWorkers().add((Worker) ruin.getNonCombatUnit());
                    } else {
                        civilization.getSettlers().add((Settler) ruin.getNonCombatUnit());
                    }
                    tile.setNonCombatUnit(ruin.getNonCombatUnit());
                    found += "FreeNonCombatUnit  + ";
                }
                tile.setRuin(null);
                civilization.setGold(civilization.getGold() + ruin.getGold());
                found += ruin.getGold() + "Golds";
                ServerUpdateController.sendUpdate(civilization.getName(), new Response(QueryResponses.CHANGE_HEX_INFO_TEXT, new HashMap<>(){{
                    put("x", String.valueOf(tile.getX()));
                    put("y", String.valueOf(tile.getY()));
                    put("info", "Found ruin!");
                    put("color", "green");
                }}));
                civilization.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                        + " you've found a ruin with these benefits : \n" + found);
            }
        }
    }


    public static HashSet<Technologies> getAvailableTechnologies(Civilization civilization) {
        HashSet<Technologies> availableTechnologies = new HashSet<>();
        int sum = 0;
        for (Technologies value : Technologies.values()) {
            sum = 0;
            for (String requiredTechnology : value.getRequiredTechnologies()) {
                sum += civilization.getTechnologies().get(Technologies.valueOf(requiredTechnology.toUpperCase(Locale.ROOT)));
            }
            if (sum <= 0) {
                availableTechnologies.add(value);
            }
        }
        return availableTechnologies;
    }

    public static void addNotification(String notification, String civilizationName) {
        WorldController.getWorld().getCivilizationByName(civilizationName).addNotification(notification);
    }

    public static String getBestCivilization() {
        int maximumPoints = 0;
        String winnerCivilizationName = "";
        for (Civilization civilization : WorldController.getWorld().getAllCivilizations()) {
            if (maximumPoints < getPoints(civilization)) {
                maximumPoints = getPoints(civilization);
                winnerCivilizationName = civilization.getName();
            }
        }
        return winnerCivilizationName;
    }

    public static int getPoints(Civilization civilization) {
        int points = 0;
        points += civilization.getGold();
        points += civilization.getAllUnits().size() * 50;
        points += civilization.getCities().size() * 150;
        points += civilization.getHappiness() * 5;
        return points;
    }

    public static void addTrade(Trade trade) {
        Civilization civilization = WorldController.getWorld().getCivilizationByName(trade.getSecondCivilization());
        civilization.addTrade(trade);
    }
}
