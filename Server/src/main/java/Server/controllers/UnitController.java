package Server.controllers;

import Server.enums.Improvements;
import Server.enums.QueryResponses;
import Server.enums.units.CombatType;
import Server.enums.units.UnitStates;
import Server.enums.units.UnitTypes;
import Server.models.City;
import Server.models.Civilization;
import Server.models.network.Response;
import Server.models.units.*;
import Server.models.tiles.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class UnitController {

    public static void setUnitDestinationCoordinates(Unit unit, int x, int y) {
        if (MoveController.impossibleToMoveToTile(x, y, unit) != null) {
            ServerUpdateController.sendUpdate(WorldController.getWorld().getCurrentCivilizationName(), new Response(QueryResponses.CHANGE_HEX_INFO_TEXT, new HashMap<>(){{
                put("x", String.valueOf(x));
                put("y", String.valueOf(y));
                put("info", "Can't move!");
                put("color", "red");
            }}));
        } else {
            unit.setDestinationCoordinates(x, y);
            MoveController.moveUnitToDestination(unit);
            CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() +
                            " you moved " + unit.getName() + " to ( " + String.valueOf(x + 1) + " , " + String.valueOf(y + 1) + " ) coordinates",
                    unit.getCivilizationName());
        }
    }

    public static void resetMovingPoints(Civilization currentCivilization) {
        for (Unit unit : currentCivilization.getAllUnits()) {
            if (unit != null)
                unit.setMovementPoint(UnitTypes.valueOf(unit.getName().toUpperCase(Locale.ROOT)).getMovement());
        }
    }

    public static String setupRangedUnit(Unit unit, int x, int y) {
        if (unit instanceof Ranged && ((Ranged) unit).isSiegeUnit()) {
            ((Ranged) unit).setCoordinatesToSetup(x, y);
        } else {
            return "this unit doesn't have the ability to setup";
        }
        return null;
    }

    public static String garrisonCity(CombatUnit combatUnit) {
        Tile currentTile = MapController.getTileByCoordinates(combatUnit.getCurrentX(), combatUnit.getCurrentY());
        if (currentTile.getCity() == null) {
            return "you should be in a city to garrison";
        } else {
            combatUnit.setUnitState(UnitStates.GARRISON);
            currentTile.getCity().addGarrisonedUnits();
        }
        return null;
    }

    public static String foundCity(Settler settler) {
        Tile currentTile = MapController.getTileByCoordinates(settler.getCurrentX(), settler.getCurrentY());
        if (currentTile.getCity() != null) {
            return "can not found a city over another city";
        } else if (currentTile.getCivilizationName() != null
                && !currentTile.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "can not found a city in enemy territory";
        } else {
            Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(settler.getCivilizationName());
            ArrayList<Tile> tiles = new ArrayList<>(TileController.getAvailableNeighbourTiles(settler.getCurrentX(), settler.getCurrentY()));
            City city = new City(currentCivilization.getCityName(), settler.getCurrentX(), settler.getCurrentY(), settler.getCivilizationName(), tiles);
            currentCivilization.addCity(city);
            currentTile.setCity(city);
            CivilizationController.updateMapVision(currentCivilization);
            CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                            + " you found the city " + city.getName() + " in ( " + String.valueOf(settler.getCurrentX() + 1)
                            + " , " + String.valueOf(settler.getCurrentY() + 1) + " ) coordinates",
                    currentCivilization.getName());
        }
        return null;
    }

    public static void buildRoad(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        currentTile.setRoadState(3);
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you built a road on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static void buildRailRoad(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        currentTile.setRailRoadState(3);
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you built a railRoad on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static void removeRouteFromTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        currentTile.setRoadState(9999);
        currentTile.setRailRoadState(9999);
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you removed routes from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static void buildImprovement(Worker worker, Improvements improvement) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        currentTile.setImprovement(improvement);
        currentTile.setImprovementTurnsLeftToBuild(6);
        worker.putToWork(6);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " you built the " +
                improvement.name().toLowerCase(Locale.ROOT) + " improvement on ( "
                + String.valueOf(worker.getCurrentX() + 1) + " , " + String.valueOf(worker.getCurrentY() + 1)
                + " ) coordinates", worker.getCivilizationName());
    }

    public static void removeJungleFromTile(Worker worker) {
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you removed jungle from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static void removeForestFromTile(Worker worker) {
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you removed forest from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static void removeMarshFromTile(Worker worker) {
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you removed marsh from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static String repairTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        if (currentTile.getPillageState() == 0) {
            return "this tile has not been pillaged";
        } else {
            currentTile.setPillageState(3);
            worker.putToWork(3);
            CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                    + " you started to repair the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                    + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
        }
        return null;
    }

    public static String pillage(int x, int y) {
        Tile currentTile = MapController.getTileByCoordinates(x, y);
        if (currentTile.getPillageState() == 9999) {
            return "this tile has been pillaged before";
        } else {
            currentTile.setPillageState(9999);
            CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                    + " you removed jungle from the tile on" + " ( " + String.valueOf(x + 1) + " , "
                    + String.valueOf(y + 1) + " ) coordinates", WorldController.getWorld().getCurrentCivilizationName());
        }
        return null;
    }

    public static void delete(Unit unit) {
        Civilization wantedCivilization = WorldController.getWorld().getCivilizationByName(unit.getCivilizationName());
        if (unit instanceof Ranged) {
            wantedCivilization.getRanges().remove((Ranged) unit);
            MapController.getTileByCoordinates(unit.getCurrentX(), unit.getCurrentY()).setCombatUnit(null);
        } else if (unit instanceof Melee) {
            wantedCivilization.getMelees().remove((Melee) unit);
            MapController.getTileByCoordinates(unit.getCurrentX(), unit.getCurrentY()).setCombatUnit(null);
        } else if (unit instanceof Worker) {
            wantedCivilization.getWorkers().remove((Worker) unit);
            MapController.getTileByCoordinates(unit.getCurrentX(), unit.getCurrentY()).setNonCombatUnit(null);
        } else if (unit instanceof Settler) {
            wantedCivilization.getSettlers().remove((Settler) unit);
            MapController.getTileByCoordinates(unit.getCurrentX(), unit.getCurrentY()).setNonCombatUnit(null);
        }
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " you deleted the " +
                unit.getName() + " unit", unit.getCivilizationName());
    }

    public static String upgradeUnit(UnitTypes unitEnum) {
        if (WorldController.getSelectedCombatUnit() == null) {
            return "you should select a combat unit first";
        } else if (unitEnum.getCombatType() == CombatType.NON_COMBAT) {
            return "you can only upgrade a combat unit to a combat unit";
        } else {
            Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
            if (currentCivilization.getGold() < ((double) unitEnum.getCost() / 2))
                return "you don't have enough gold for upgrading to this unit";
            if (WorldController.getSelectedCombatUnit() instanceof Ranged) {
                if (unitEnum.getRangedCombatStrength() == 0)
                    return "you can't upgrade a ranged unit into a melee unit";
                currentCivilization.setGold(currentCivilization.getGold() - ((double) unitEnum.getCost() / 2));
                Ranged newUnit = new Ranged(unitEnum,
                        WorldController.getSelectedCombatUnit().getCurrentX(),
                        WorldController.getSelectedCombatUnit().getCurrentY(),
                        currentCivilization.getName());
                currentCivilization.getRanges().add(newUnit);
                currentCivilization.getRanges().remove(WorldController.getSelectedCombatUnit());
                MapController.getMap()[newUnit.getCurrentX()][newUnit.getCurrentY()].setCombatUnit(newUnit);
            } else {
                if (unitEnum.getRangedCombatStrength() != 0)
                    return "you can't upgrade a melee unit into a ranged unit";
                currentCivilization.setGold(currentCivilization.getGold() - ((double) unitEnum.getCost() / 2));
                Melee newUnit = new Melee(unitEnum,
                        WorldController.getSelectedCombatUnit().getCurrentX(),
                        WorldController.getSelectedCombatUnit().getCurrentY(),
                        currentCivilization.getName());
                currentCivilization.getMelees().add(newUnit);
                currentCivilization.getMelees().remove(WorldController.getSelectedCombatUnit());
                MapController.getMap()[newUnit.getCurrentX()][newUnit.getCurrentY()].setCombatUnit(newUnit);
            }
            return null;
        }
    }
}
