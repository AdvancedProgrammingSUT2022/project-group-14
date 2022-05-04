package controllers;

import models.City;
import models.Civilization;
import models.Tile;
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

    public static void newWorld(ArrayList<String> usernames) {
        MapController.generateMap();
        world = new World(usernames);
        MapController.updateUnitPositions();
    }

    public static void resetWorld() {
        world = null;
        selectedTile = null;
        selectedCity = null;
        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
        MapController.resetMap();
    }

    public static void resetSelection() {
        selectedTile = null;
        selectedCity = null;
        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
    }

    public static void nextTurn() {
        //TODO technologies, goods, buildings
        Civilization currentCivilization = world.getCivilizationByName(world.getCurrentCivilizationName());
        TileController.updateBuildingProgress(currentCivilization);
        CivilizationController.updateScience(currentCivilization);
        CivilizationController.updateTechnology(currentCivilization);
        CivilizationController.updateMapVision(currentCivilization);
        CivilizationController.updateCitiesGoods(currentCivilization);
        CivilizationController.updateCitiesProductions(currentCivilization);
        for (Unit unit : currentCivilization.getAllUnits()) {
            MoveController.moveUnitToDestination(unit);
        }
        world.nextTurn();
        resetSelection();
    }

    public static World getWorld() {
        return world;
    }

    public static void setWorld(World world) {
        WorldController.world = world;
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
