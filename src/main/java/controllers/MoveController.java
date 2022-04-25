package controllers;

import models.Tile;
import models.units.Unit;

import java.util.ArrayList;

public class MoveController {

    public static String impossibleToMoveToTile(int x, int y) {
        if (MapController.getTileByCoordinates(x, y).getCivilization() != null &&
                !MapController.getTileByCoordinates(x, y).getCivilization().getName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "chosen tile is in the enemy territory";
        } else if (MapController.getTileByCoordinates(x, y).getMovingPoint() == 9999) {
            return "can not move to those kind of tiles";
        }
        return null;
    }

    public static void moveUnitToDestination(Unit unit) {
        if (unit.getDestinationX() == -1 && unit.getDestinationY() == -1) {
            return;
        }
        int movementPointsConsumed = 0;
        Tile nextTileToMove;
        while(unit.getMovementPoint() - movementPointsConsumed > 0 && unit.getDestinationX() != -1 && unit.getDestinationY() != -1) {
            nextTileToMove = bestNextTileToMove(MapController.getTileByCoordinates(unit.getCurrentX(), unit.getCurrentY()),
                                                MapController.getTileByCoordinates(unit.getDestinationX(), unit.getDestinationY()));
            unit.updatePosition(nextTileToMove.getX(), nextTileToMove.getY());
            movementPointsConsumed += nextTileToMove.getMovingPoint();
        }
    }

    public static Tile bestNextTileToMove(Tile startingTile, Tile finishingTile) {
        boolean[][] visitedTiles = new boolean[MapController.getWidth()][MapController.getLength()];
        int[][] distanceFromStartingTile = new int[MapController.getWidth()][MapController.getLength()];
        Tile[][] previousTile = new Tile[MapController.getWidth()][MapController.getLength()];

        //initializing the arrays
        for (int i = 0; i < MapController.getWidth(); i++) {
            for (int j = 0; j < MapController.getLength(); j++) {
                visitedTiles[i][j] = false;
                distanceFromStartingTile[i][j] = 9999;
            }
        }
        distanceFromStartingTile[startingTile.getX()][startingTile.getY()] = 0;
        //main Dijkstra part
        for (int i = 0; i < MapController.getWidth() * MapController.getLength(); i++) {
            Tile tempPreviousTile = null;
            int minDistance = 9999;
            ArrayList<Tile> neighbourTiles;

            for (int k = 0; k < MapController.getWidth(); k++) {
                for (int z = 0; z < MapController.getLength(); z++) {
                    if (!visitedTiles[k][z] && distanceFromStartingTile[k][z] <= minDistance) {
                        minDistance = distanceFromStartingTile[k][z];
                        tempPreviousTile = MapController.getTileByCoordinates(k, z);
                    }
                }
            }
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
        Tile lastTile = finishingTile;
        while (true) {
            if (previousTile[lastTile.getX()][lastTile.getY()].equals(startingTile)) {
                return lastTile;
            }
            lastTile = previousTile[lastTile.getX()][lastTile.getY()];
        }
    }

}
