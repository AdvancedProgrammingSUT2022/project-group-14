package controllers;

import enums.Technologies;
import models.City;
import models.Civilization;
import models.tiles.Tile;
import models.World;
import models.units.CombatUnit;
import models.units.NonCombatUnit;
import models.units.Unit;

import java.util.ArrayList;

public class WorldController {
    private static World world;
    private static Tile selectedTile;
    private static City selectedCity;
    private static CombatUnit selectedCombatUnit;
    private static NonCombatUnit selectedNonCombatUnit;

    public static void newWorld(ArrayList<String> usernames, int width, int height) {
        MapController.generateMap(width, height);
        world = new World(usernames);
        MapController.updateUnitPositions();
        for (Civilization civilization : world.getAllCivilizations()) {
            CivilizationController.updateMapVision(civilization);
        }
    }

    public static void resetWorld() {
        world = null;
        resetSelection();
    }

    public static void resetSelection() {
        selectedTile = null;
        selectedCity = null;
        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
    }

    public static void nextTurn() {
        Civilization currentCivilization = world.getCivilizationByName(world.getCurrentCivilizationName());
        //TODO buildings
        TileController.updateBuildingProgress(currentCivilization);
        CivilizationController.updateScience(currentCivilization);
        CivilizationController.updateTechnology(currentCivilization);
        CivilizationController.updateMapVision(currentCivilization);
        CivilizationController.updateCitiesGoods(currentCivilization);
        CivilizationController.payRequiredPriceForKeepingRoadsAndRailroads(currentCivilization);
        CivilizationController.payRequiredPriceForKeepingUnits(currentCivilization);
        CivilizationController.updateCitiesProductions(currentCivilization);
        for (Unit unit : currentCivilization.getAllUnits()) {
            MoveController.moveUnitToDestination(unit);
        }
        applyAttacks();
        UnitController.resetMovingPoints(currentCivilization);
        addAllHeals();
        world.nextTurn();
        resetSelection();
    }

    public static void applyAttacks() {
        Civilization currentCivilization = world.getCivilizationByName(world.getCurrentCivilizationName());
        for (Unit unit : currentCivilization.getAllUnits()) {
            if (unit instanceof CombatUnit)
                if (((CombatUnit) unit).getAttackingTileX() != -1 && ((CombatUnit) unit).getAttackingTileY() != -1)
                    if (MapController.getTileByCoordinates(((CombatUnit) unit).getAttackingTileX(), ((CombatUnit) unit).getAttackingTileY()).getCity() != null) {
                        WarController.attackCity((CombatUnit) unit, MapController.getTileByCoordinates(((CombatUnit) unit).getAttackingTileX(), ((CombatUnit) unit).getAttackingTileY()).getCity());
                    } else {
                        //TODO attacking unit vs unit
                    }
        }
    }

    public static void addAllHeals() {
        Civilization currentCivilization = world.getCivilizationByName(world.getCurrentCivilizationName());
        for (Unit unit : currentCivilization.getAllUnits()) {
            if (unit instanceof CombatUnit && ((CombatUnit) unit).isFortifiedTillHealed())
                ((CombatUnit) unit).healUnit(5);
        }
    }

    public static String nextTurnImpossible() {
        Civilization currentCivilization = world.getCivilizationByName(world.getCurrentCivilizationName());
        boolean civilizationHasAllTechnologies = true;
        for (Technologies technology : Technologies.values()) {
            if (!currentCivilizationHasTechnology(technology)) {
                civilizationHasAllTechnologies = false;
                break;
            }
        }
        if (currentCivilization.getCurrentTechnology() == null && !civilizationHasAllTechnologies) {
            return "you have to choose a technology to research";
        } else {
            for (Unit unit : currentCivilization.getAllUnits()) {
                int x = unit.getCurrentX() + 1, y = unit.getCurrentY() + 1;
                if ((unit.getMovementPoint() > 0) && (unit.getDestinationX() == -1 && unit.getDestinationY() == -1 && !unit.isSleep())) {
                    return unit.getName() + " in ( " + x + " , " + y + " ) coordinates needs to be moved";
                }
            }
        }
        return null;
    }

    public static boolean currentCivilizationHasTechnology(Technologies technology) {
        return world.getCivilizationByName(world.getCurrentCivilizationName()).getTechnologies().get(technology) <= 0;
    }

    public static World getWorld() {
        return world;
    }

    public static Tile getSelectedTile() {
        return selectedTile;
    }

    public static void setSelectedTile(Tile selectedTile) {
        WorldController.selectedTile = selectedTile;
    }

    public static City getSelectedCity() {
        return selectedCity;
    }

    public static void setSelectedCity(City selectedCity) {
        WorldController.selectedCity = selectedCity;
    }

    public static CombatUnit getSelectedCombatUnit() {
        return selectedCombatUnit;
    }

    public static void setSelectedCombatUnit(CombatUnit selectedCombatUnit) {
        WorldController.selectedCombatUnit = selectedCombatUnit;
    }

    public static NonCombatUnit getSelectedNonCombatUnit() {
        return selectedNonCombatUnit;
    }

    public static void setSelectedNonCombatUnit(NonCombatUnit selectedNonCombatUnit) {
        WorldController.selectedNonCombatUnit = selectedNonCombatUnit;
    }

    public static boolean unitIsNotSelected() {
        return selectedCombatUnit == null && selectedNonCombatUnit == null;
    }
}
