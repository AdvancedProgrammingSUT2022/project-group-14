package Server.controllers;

import Server.enums.QueryResponses;
import Server.models.network.Response;
import javafx.scene.paint.Color;
import Server.models.tiles.Tile;
import Server.models.units.CombatUnit;
import Server.models.units.NonCombatUnit;
import Server.models.units.Unit;

import java.util.ArrayList;
import java.util.HashMap;

public class MoveController {

    public static String impossibleToMoveToTile(int x, int y, Unit unit) {
        if (MapController.getTileByCoordinates(x, y).getCity() != null &&
                !MapController.getTileByCoordinates(x, y).getCity().getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "chosen tile is in the enemy territory";
        } else if (MapController.getTileByCoordinates(x, y).getMovingPoint() == 9999) {
            return "can not move to those kind of tiles";
        } else if ((MapController.getTileByCoordinates(x, y).getCombatUnit() != null && unit instanceof CombatUnit) ||
                (MapController.getTileByCoordinates(x, y).getNonCombatUnit() != null && unit instanceof NonCombatUnit)) {
            return "there is not any space left on the tile to move";
        } else if ((MapController.getTileByCoordinates(x, y).getNonCombatUnit() != null && !MapController.getTileByCoordinates(x, y).getNonCombatUnit().getCivilizationName().equals(unit.getCivilizationName()))
            || (MapController.getTileByCoordinates(x, y).getCombatUnit() != null && !MapController.getTileByCoordinates(x, y).getCombatUnit().getCivilizationName().equals(unit.getCivilizationName()))) {
            return "there is an enemy's unit in this tile";
        }
        return null;
    }

    public static void moveUnitToDestination(Unit unit) {
        if ((unit == null) || (unit.getDestinationX() == -1 && unit.getDestinationY() == -1))
            return;

        Tile nextTileToMove;
        String error;
        while (unit.getMovementPoint() > 0 && unit.getDestinationX() != -1 && unit.getDestinationY() != -1) {
            if (unit instanceof CombatUnit && ((CombatUnit) unit).getAttackingTileX() != -1 && ((CombatUnit) unit).getAttackingTileY() != -1
                    && TileController.coordinatesAreInRange(unit.getCurrentX(), unit.getCurrentY(),
                    ((CombatUnit) unit).getAttackingTileX(), ((CombatUnit) unit).getAttackingTileY(), ((CombatUnit) unit).getRange())) {
                break;
            }
            nextTileToMove = bestNextTileToMove(MapController.getTileByCoordinates(unit.getCurrentX(), unit.getCurrentY()),
                    MapController.getTileByCoordinates(unit.getDestinationX(), unit.getDestinationY()));
            if (nextTileToMove == null || ((error = impossibleToMoveToTile(nextTileToMove.getX(), nextTileToMove.getY(), unit)) != null
                    && error.equals("there is not any space left on the tile to move")
                    && unit.getMovementPoint() - (nextTileToMove.getMovingPoint()) <= 0)) {
                ServerUpdateController.sendUpdate(WorldController.getWorld().getCurrentCivilizationName(), new Response(QueryResponses.CHANGE_HEX_INFO_TEXT, new HashMap<>(){{
                    put("x", String.valueOf(unit.getDestinationX()));
                    put("y", String.valueOf(unit.getDestinationY()));
                    put("info", "Can't move!");
                    put("color", "red");
                }}));
                unit.cancelMission();
                break;
            }
            unit.updatePosition(nextTileToMove.getX(), nextTileToMove.getY());
            unit.setMovementPoint(unit.getMovementPoint() - nextTileToMove.getMovingPointFromSide(
                    nextTileToMove.getX() - unit.getCurrentX(), nextTileToMove.getY() - unit.getCurrentY(), unit.getMovementPoint()));
            if ((unit.getMovementPoint() < 0) || (nextTileToMove.getCivilizationName() != null && !nextTileToMove.getCivilizationName().equals(unit.getCivilizationName())))
                unit.setMovementPoint(0);
            MapController.updateUnitPositions();
            CivilizationController.updateRuins(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()));
            CivilizationController.updateMapVision(WorldController.getWorld().getCivilizationByName(unit.getCivilizationName()));
        }
    }

    public static Tile bestNextTileToMove(Tile startingTile, Tile finishingTile) {
        int width = MapController.getWidth(), height = MapController.getHeight(), INFINITE = 9999;
        boolean[][] visitedTiles = new boolean[width][height];
        int[][] distanceFromStartingTile = new int[width][height];
        Tile[][] previousTile = new Tile[width][height];

        //initializing the arrays
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                visitedTiles[i][j] = false;
                distanceFromStartingTile[i][j] = INFINITE;
            }
        }
        distanceFromStartingTile[startingTile.getX()][startingTile.getY()] = 0;

        //main Dijkstra part
        for (int i = 0; i < width * height; i++) {
            Tile tempPreviousTile = null;
            int minDistance = INFINITE;
            ArrayList<Tile> neighbourTiles;

            for (int k = 0; k < width; k++) {
                for (int z = 0; z < height; z++) {
                    if (!visitedTiles[k][z] && distanceFromStartingTile[k][z] <= minDistance) {
                        minDistance = distanceFromStartingTile[k][z];
                        tempPreviousTile = MapController.getTileByCoordinates(k, z);
                    }
                }
            }
            assert tempPreviousTile != null;
            neighbourTiles = TileController.getAvailableNeighbourTiles(tempPreviousTile.getX(), tempPreviousTile.getY());
            for (Tile tile : neighbourTiles) {
                if (minDistance + tile.getMovingPoint() < distanceFromStartingTile[tile.getX()][tile.getY()]) {
                    distanceFromStartingTile[tile.getX()][tile.getY()] = minDistance + tile.getMovingPoint();
                    previousTile[tile.getX()][tile.getY()] = tempPreviousTile;
                }
            }
            visitedTiles[tempPreviousTile.getX()][tempPreviousTile.getY()] = true;
        }

        //getting back to the tile that the unit should move to
        Tile nextTile = finishingTile;
        while (true) {
            if (previousTile[nextTile.getX()][nextTile.getY()] == null)
                return null;
            if (previousTile[nextTile.getX()][nextTile.getY()].equals(startingTile)) {
                return nextTile;
            }
            nextTile = previousTile[nextTile.getX()][nextTile.getY()];
        }
    }

}
