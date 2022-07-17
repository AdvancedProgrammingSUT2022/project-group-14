package controllers;


import enums.Improvements;
import enums.tiles.TileFeatureTypes;
import enums.units.UnitStates;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import models.Civilization;
import models.tiles.Coordination;
import models.tiles.Tile;
import models.resources.Resource;
import models.resources.StrategicResource;
import models.units.Unit;
import models.units.Worker;

import java.util.ArrayList;

public class TileController {

    public static boolean selectedTileIsValid(int x, int y) {
        return x < MapController.getWidth() && y < MapController.getHeight() && x >= 0 && y >= 0;
    }

    public static void updateBuildingProgress(Civilization civilization) {
        ArrayList<Unit> units = civilization.getAllUnits();
        for (Unit unit : units) {
            if (unit instanceof Worker) {
                if (unit.getUnitState() == UnitStates.WORKING) {
                    ((Worker) unit).doWork();
                    Tile tile = MapController.getTileByCoordinates(unit.getCurrentX(), unit.getCurrentY());
                    if (tile.getPillageState() != 0 && tile.getPillageState() != 9999) {
                        tile.setPillageState(tile.getPillageState() - 1);
                    } else if (tile.getRoadState() != 0 && tile.getRoadState() != 9999) {
                        tile.setRoadState(tile.getRoadState() - 1);
                    } else if (tile.getRailRoadState() != 0 && tile.getRailRoadState() != 9999) {
                        tile.setRailRoadState(tile.getRailRoadState() - 1);
                    } else if (tile.getImprovementTurnsLeftToBuild() != 0 && tile.getImprovementTurnsLeftToBuild() != 9999) {
                        tile.setImprovementTurnsLeftToBuild(tile.getImprovementTurnsLeftToBuild() - 1);
                    }
                    if (tile.getImprovementTurnsLeftToBuild() == 0) {
                        tile.addAvailableResourcesToCivilizationAndTile();
                    }
                }
            }
        }
    }

    public static ArrayList<Tile> getAvailableNeighbourTiles(int x, int y) {
        ArrayList<Tile> neighbours = new ArrayList<>();
        if (selectedTileIsValid(x - 2, y)) neighbours.add(MapController.getTileByCoordinates(x - 2, y));
        if (selectedTileIsValid(x - 1, y + (x % 2 == 1 ? 1 : -1))) neighbours.add(MapController.getTileByCoordinates(x - 1, y + (x % 2 == 1 ? 1 : -1)));
        if (selectedTileIsValid(x + 1, y + (x % 2 == 1 ? 1 : -1))) neighbours.add(MapController.getTileByCoordinates(x + 1, y + (x % 2 == 1 ? 1 : -1)));
        if (selectedTileIsValid(x + 2, y)) neighbours.add(MapController.getTileByCoordinates(x + 2, y));
        if (selectedTileIsValid(x - 1, y)) neighbours.add(MapController.getTileByCoordinates(x - 1, y));
        if (selectedTileIsValid(x + 1, y)) neighbours.add(MapController.getTileByCoordinates(x + 1, y));
        return neighbours;
    }

    public static boolean coordinatesAreInRange(int x1, int y1, int x2, int y2, int distance) {
        int[][] neighbors = new int[MapController.getWidth()][MapController.getHeight()];
        for (int i = 0; i < MapController.getWidth(); i++) {
            for (int j = 0; j < MapController.getHeight(); j++) {
                neighbors[i][j] = 0;
            }
        }
        int range = 1;
        if (x1 == x2 && y1 == y2 && distance >= 0) return true;
        for (Tile neighbourTile : getAvailableNeighbourTiles(x2, y2)) {
            neighbors[neighbourTile.getX()][neighbourTile.getY()] = range;
        }
        while (range < distance) {
            for (int i = 0; i < MapController.getWidth(); i++) {
                for (int j = 0; j < MapController.getHeight(); j++) {
                    if (neighbors[i][j] == range) for (Tile neighbourTile : getAvailableNeighbourTiles(i, j)) {
                        if (neighbors[neighbourTile.getX()][neighbourTile.getY()] == 0)
                            neighbors[neighbourTile.getX()][neighbourTile.getY()] = range + 1;
                    }
                }
            }
            range++;
        }

        return neighbors[x1][y1] > 0;
    }

    public static ArrayList<String> getAvailableImprovements(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        ArrayList<String> availableImprovements = new ArrayList<>();
        for (Improvements value : Improvements.values()) {
            if (!(currentTile.getImprovement() != null && currentTile.getImprovement().equals(value))
                    && !(WorldController.getWorld().getCivilizationByName(worker.getCivilizationName()).getTechnologies().get(value.getRequiredTechnology()) > 0)
                    && !(!value.getPossibleTiles().contains(currentTile.getType()) && !value.getPossibleTiles().contains(currentTile.getFeature()))) {
                availableImprovements.add(value.getName());
            }
        }
        if (currentTile.getRailRoadState() != 0)
            availableImprovements.add("railRoad");
        if (currentTile.getRoadState() != 0)
            availableImprovements.add("road");

        return availableImprovements;
    }

    public static ArrayList<String> getRemovableFeatures(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        ArrayList<String> removableFeatures = new ArrayList<>();
        if (currentTile.getRoadState() == 0 || currentTile.getRailRoadState() == 0)
            removableFeatures.add("Routes");
        if (currentTile.getFeature() == TileFeatureTypes.JUNGLE) {
            removableFeatures.add("Jungle");
        } else if (currentTile.getFeature() == TileFeatureTypes.FOREST) {
            removableFeatures.add("Forest");
        } else if (currentTile.getFeature() == TileFeatureTypes.SWAMP) {
            removableFeatures.add("Marsh");
        }
        return removableFeatures;
    }

    public static boolean resourceIsAvailableToBeUsed(Resource resource, Tile tile) {
        if (resource.getRequiredImprovement() == tile.getImprovement() && tile.getImprovementTurnsLeftToBuild() == 0) {
            return !(resource instanceof StrategicResource) ||
                    WorldController.currentCivilizationHasTechnology(((StrategicResource) resource).getRequiredTechnology());
        }
        return false;
    }

    public static Group getInfoPopup(Coordination coordination) {
        Group group = new Group();
        Text text = new Text(MapController.getTileByCoordinates(coordination).getInfo());
        Rectangle rectangle = new Rectangle(text.getBoundsInLocal().getWidth() + 10, text.getBoundsInLocal().getHeight() - 5, Color.WHITE);
        text.setLayoutY(rectangle.getLayoutY() + 15);
        text.setLayoutX(rectangle.getLayoutX() + 5);
        group.getChildren().add(rectangle);
        group.getChildren().add(text);

        return group;
    }
}
