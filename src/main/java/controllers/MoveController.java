package controllers;

import models.Tile;
import models.World;
import models.units.Unit;

import java.util.ArrayList;

public class MoveController {

    public static String setUnitDestinationCoordinates(Unit unit, int x, int y, World world) {
        String reason;
        if (!unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
            return "the unit is not under your control";
        } else if ((reason = impossibleToMoveToTile(x, y, world)) != null) {
            return reason;
        } else {
            unit.setDestinationCoordinates(x, y);
        }
        return null;
    }

    public static String impossibleToMoveToTile(int x, int y, World world) {
        if (!world.getTileByCoordinates(x, y).getCivilization().getName().equals(world.getCurrentCivilizationName())) {
            return "chosen tile is in the enemy territory";
        } else if (world.getTileByCoordinates(x, y).getMovingPoint() == -1) {
            return "can not move to those kind of tiles";
        }
        return null;
    }

    public void moveUnitToDestination(Unit unit, World world) {
        boolean[][] visitedTiles = new boolean[World.getWidth()][World.getLength()];
        int[][] distanceFromStartingTile = new int[World.getWidth()][World.getLength()];
        Tile[][] previousTile = new Tile[World.getWidth()][World.getLength()];

        //initializing the arrays
        for (int i = 0; i < World.getWidth(); i++) {
            for (int j = 0; j < World.getLength(); j++) {
                visitedTiles[i][j] = false;
                distanceFromStartingTile[i][j] = Integer.MAX_VALUE;
            }
        }
        distanceFromStartingTile[unit.getCurrentX()][unit.getCurrentY()] = 0;
        //main Dijkstra part
        for (int i = 0; i < World.getWidth() * World.getLength(); i++) {
            Tile tempPreviousTile = null;
            int minDistance = Integer.MAX_VALUE;
            ArrayList<Tile> neighbourTiles;

            for (int k = 0; k < World.getWidth(); k++) {
                for (int z = 0; z < World.getLength(); z++) {
                    if (!visitedTiles[k][z] && distanceFromStartingTile[k][z] < minDistance) {
                        minDistance = distanceFromStartingTile[k][z];
                        tempPreviousTile = world.getTileByCoordinates(k, z);
                    }
                }
            }
            neighbourTiles = TileController.getAvailableNeighbourTiles(tempPreviousTile.getX(), tempPreviousTile.getY(), world);
            for (Tile tile : neighbourTiles) {
                if (minDistance + tile.getMovingPoint() < distanceFromStartingTile[tile.getX()][tile.getY()]) {
                    distanceFromStartingTile[tile.getX()][tile.getY()] = minDistance + tile.getMovingPoint();
                    previousTile[tile.getX()][tile.getY()] = tempPreviousTile;
                }
            }
            visitedTiles[tempPreviousTile.getX()][tempPreviousTile.getY()] = true;
        }
        //getting back to the tile that the unit should move to
        Tile lastTile = world.getTileByCoordinates(unit.getDestinationX(), unit.getDestinationY());
        while (true) {
            if (previousTile[lastTile.getX()][lastTile.getY()].equals(world.getTileByCoordinates(unit.getCurrentX(), unit.getCurrentY()))) {
                unit.updatePosition(lastTile.getX(), lastTile.getY());
                return;
            }
            lastTile = previousTile[lastTile.getX()][lastTile.getY()];
        }
    }

}
