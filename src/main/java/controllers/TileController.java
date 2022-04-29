package controllers;


import java.util.ArrayList;

import models.Tile;
import models.units.Unit;
import models.units.Worker;

public class TileController {

    public static boolean selectedTileIsValid(int x, int y) {
        return x < MapController.getWidth() && y < MapController.getLength() && x >= 0 && y >= 0;
    }

    public static void updateImprovements() {
        ArrayList<Unit> units = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits();
        for (Unit unit : units) {
            if (unit instanceof Worker) {
                if (((Worker) unit).isWorking()) {
                    ((Worker) unit).work();
                    Tile tile = MapController.getTileByCoordinates(unit.getCurrentX(), unit.getCurrentY());
                    if (tile.getPillageState() != 0 && tile.getPillageState() != 9999) {
                        tile.setPillageState(tile.getPillageState() - 1);
                    } else if (tile.getRoadState() != 0 && tile.getRoadState() != 9999) {
                        tile.setRoadState(tile.getRoadState() - 1);
                    } else if (tile.getRailRoadState() != 0 && tile.getRailRoadState() != 9999) {
                        tile.setRailRoadState(tile.getRailRoadState() - 1);
                    } else if (tile.getImprovementTurnsLeftToBuild() != 0 && tile.getImprovementTurnsLeftToBuild() != 9999) {
                        tile.setImprovementTurnsLeftToBuild(tile.getImprovementTurnsLeftToBuild() -1 );
                    }
                }
            }
        }
    }

    public static ArrayList<Tile> getAvailableNeighbourTiles(int x, int y) {
        ArrayList<Tile> neighbours = new ArrayList<>();
        if (selectedTileIsValid(x-1, y))
            neighbours.add(MapController.getTileByCoordinates(x-1, y));
        if (selectedTileIsValid(x, y+1))
            neighbours.add(MapController.getTileByCoordinates(x, y+1));
        if (selectedTileIsValid(x+1, y+1))
            neighbours.add(MapController.getTileByCoordinates(x+1, y+1));
        if (selectedTileIsValid(x+1, y))
            neighbours.add(MapController.getTileByCoordinates(x+1, y));
        if (selectedTileIsValid(x+1, y-1))
            neighbours.add(MapController.getTileByCoordinates(x+1, y-1));
        if (selectedTileIsValid(x, y-1))
            neighbours.add(MapController.getTileByCoordinates(x, y-1));

        return neighbours;
    }

    public static boolean combatUnitExistsInTile(Tile tile) {
        return tile.getCombatUnit() != null;
    }

    public static boolean nonCombatUnitExistsInTile(Tile tile) {
        return tile.getNonCombatUnit() != null;
    }
}
