package controllers;

import enums.Technologies;
import enums.units.UnitStates;
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
        for (Civilization civilization : world.getAllCivilizations())
            CivilizationController.updateMapVision(civilization);
    }

    public static void nextTurn() {
        Civilization currentCivilization = world.getCivilizationByName(world.getCurrentCivilizationName());
        TileController.updateBuildingProgress(currentCivilization);
        CivilizationController.updateCitiesGoods(currentCivilization);
        CivilizationController.updateScience(currentCivilization);
        CivilizationController.updateTechnology(currentCivilization);
        CivilizationController.updateMapVision(currentCivilization);
        CivilizationController.payRequiredPriceForKeepingRoadsAndRailroads(currentCivilization);
        CivilizationController.payRequiredPriceForKeepingUnits(currentCivilization);
        CivilizationController.payRequiredPriceForKeepingBuildings(currentCivilization);
        CivilizationController.updateCitiesProductions(currentCivilization);
//        CivilizationController.updateRuins(currentCivilization);
        for (Unit unit : currentCivilization.getAllUnits())
            MoveController.moveUnitToDestination(unit);
        UnitController.resetMovingPoints(currentCivilization);
        applyAttacks();
        addAllHeals();
        world.nextTurn();
        resetSelection();
    }

    public static String nextTurnImpossible() {
        Civilization currentCivilization = world.getCivilizationByName(world.getCurrentCivilizationName());
        boolean civilizationHasAllTechnologies = true;
        for (Technologies technology : Technologies.values())
            if (!CivilizationController.civilizationHasTechnology(technology)) {
                civilizationHasAllTechnologies = false;
                break;
            }
        if (currentCivilization.getCurrentTechnology() == null && !civilizationHasAllTechnologies)
            return "you have to choose a technology to research";
        for (Unit unit : currentCivilization.getAllUnits()) {
            int x = unit.getCurrentX() + 1, y = unit.getCurrentY() + 1;
            if ((unit.getMovementPoint() > 0) && (unit.getDestinationX() == -1 && unit.getDestinationY() == -1 && unit.getUnitState() != UnitStates.SLEEP)) {
                return unit.getName() + " in ( " + x + " , " + y + " ) coordinates needs to be moved";
            }
        }
        return null;
    }

    public static void applyAttacks() {
        for (Unit unit : world.getCivilizationByName(world.getCurrentCivilizationName()).getAllUnits()) {
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
        for (Unit unit : world.getCivilizationByName(world.getCurrentCivilizationName()).getAllUnits()) {
            if (unit.getUnitState() == UnitStates.FORTIFY_TILL_HEALED) {
                ((CombatUnit) unit).healUnit(5);
            } else if (unit.getUnitState() == UnitStates.FORTIFY) {
                ((CombatUnit) unit).healUnit(5);
                unit.setUnitState(UnitStates.WAKE);
            }
        }
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
        WorldController.selectedNonCombatUnit = null;
    }

    public static NonCombatUnit getSelectedNonCombatUnit() {
        return selectedNonCombatUnit;
    }

    public static void setSelectedNonCombatUnit(NonCombatUnit selectedNonCombatUnit) {
        WorldController.selectedNonCombatUnit = selectedNonCombatUnit;
        WorldController.selectedCombatUnit = null;
    }

    public static boolean unitIsNotSelected() {
        return selectedCombatUnit == null && selectedNonCombatUnit == null;
    }

    public static void resetSelection() {
        selectedTile = null;
        selectedCity = null;
        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
    }

}
