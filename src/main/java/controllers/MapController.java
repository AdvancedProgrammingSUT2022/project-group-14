package controllers;

import enums.Colors;
import models.Cell;
import models.Civilization;
import models.Tile;
import models.units.CombatUnit;
import models.units.NonCombatUnit;
import models.units.Unit;

import java.util.ArrayList;

public class MapController {

    public static final int width = 40;
    public static final int length = 80;
    public static final int outputMapWidth = 6 * width + 3;
    public static final int outputMapLength = 8 * length + 3;
    private static Cell[][] cellsMap = new Cell[outputMapWidth][outputMapLength];
    private static int[][][] tileCenters = new int[width][length][2];
    private static Tile[][] tilesMap = new Tile[width][length];

    public static void generateMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                tilesMap[i][j] = Tile.generateRandomTile(i, j);
            }
        }
    }

    // Initializations for tiles map and cells map
    private static void upLayerBordersInit(int row) {
        for (int j = 0; j < 3; j++)
            for (int k = 0; k < outputMapLength; k++) {
                Cell cell = new Cell();
                cellsMap[6 * row + j][k] = cell;
                cell.setColor(Colors.RESET);

                if (k % 16 == (2 - j) && (k < outputMapLength - 3 || row > 0) && (k > 2 || row < width)) {
                    cell.setCharacter('/');
                } else if (k % 16 == (8 + j) && (row < width || k < outputMapLength - 3)) {
                    cell.setCharacter('\\');
                } else if (j == 2 && (k % 16) >= 11) {
                    cell.setCharacter('_');
                } else {
                    cell.setCharacter(' ');
                }
            }
    }

    private static void downLayerBordersInit(int row) {
        for (int j = 0; j < 3; j++)
            for (int k = 0; k < outputMapLength; k++) {
                Cell cell = new Cell();
                cellsMap[6 * row + 3 + j][k] = cell;
                cell.setColor(Colors.RESET);
                if (k % 16 == (j)) {
                    cell.setCharacter('\\');
                } else if (k % 16 == (10 - j)) {
                    cell.setCharacter('/');
                } else if (j == 2 && (k % 16) >= 3 && (k % 16) <= 7) {
                    cell.setCharacter('_');
                } else {
                    cell.setCharacter(' ');
                }
            }
    }


    private static void bordersInit() {
        for (int i = 0; i <= width - 1; i++) {
            upLayerBordersInit(i);
            downLayerBordersInit(i);
        }
        upLayerBordersInit(width);
    }

    private static void tileCentersInit() {
        for (int x = 2, i = 0; i < width; x += 6, i++)
            for (int y = 5, j = 0; j < length; y += 16, j += 2) {
                tileCenters[i][j][0] = x;
                tileCenters[i][j][1] = y;
            }
        for (int x = 5, i = 0; i < width; x += 6, i++)
            for (int y = 13, j = 1; j < length; y += 16, j += 2) {
                tileCenters[i][j][0] = x;
                tileCenters[i][j][1] = y;
            }
    }

    private static void upLayerTileCellsRefresh(int[] tileCenter, Tile tile) {
        for (int i = tileCenter[0]; i >= tileCenter[0] - 2; i--)
            for (int j = tileCenter[1] - 4 + (tileCenter[0] - i); j <= tileCenter[1] + 4 - (tileCenter[0] - i); j++) {
                cellsMap[i][j].setColor(tile.getColor());
            }
    }

    private static void downLayerTileCellsRefresh(int[] tileCenter, Tile tile) {
        for (int i = tileCenter[0] + 1; i <= tileCenter[0] + 3; i++)
            for (int j = tileCenter[1] - 4 + (i - tileCenter[0] - 1); j <= tileCenter[1] + 4 - (i - tileCenter[0] - 1); j++) {
                cellsMap[i][j].setColor(tile.getColor());
            }
    }

    public static void tileCellsRefresh() { // initialize cells of every tile
        String coordinates;
        for (int i = 0; i < width; i++)
            for (int j = 0; j < length; j++) {
                upLayerTileCellsRefresh(tileCenters[i][j], tilesMap[i][j]);
                downLayerTileCellsRefresh(tileCenters[i][j], tilesMap[i][j]);
                coordinates = "(" + i + "," + j + ")";
                printStringToCellsMap(coordinates, tileCenters[i][j][0] - 1, tileCenters[i][j][1] - 3);
                printStringToCellsMap(tilesMap[i][j].getType().getName(), tileCenters[i][j][0] + 1, tileCenters[i][j][1] - 4);
            }
    }

    public static void mapInit() {
        generateMap();
        bordersInit();
        tileCentersInit();
        tileCellsRefresh();
    }

    public static void printStringToCellsMap(String input, int x, int y) {
        for (int i = 0; i < input.length(); i++) {
            cellsMap[x][i + y].setCharacter(input.charAt(i));
        }
    }

    public static void resetMap() {
        tileCenters = new int[width][length][2];
        cellsMap = new Cell[outputMapWidth][outputMapLength];
        tilesMap = new Tile[width][length];
    }

    public static void updateUnitPositions() {
        ArrayList<Unit> allUnitsInGame = new ArrayList<>();
        for (Civilization civilization : WorldController.getWorld().getAllCivilizations()) {
            allUnitsInGame.addAll(civilization.getAllUnits());
        }

        for (Unit unit : allUnitsInGame) {
            if (unit instanceof CombatUnit) {
                tilesMap[unit.getCurrentX()][unit.getCurrentY()].setCombatUnit((CombatUnit) unit);
            }
            if (unit instanceof NonCombatUnit) {
                tilesMap[unit.getCurrentX()][unit.getCurrentY()].setNonCombatUnit((NonCombatUnit) unit);
            }
        }
        for (Tile[] tiles : tilesMap) {
            for (Tile tile : tiles) {
                if (tile.getCombatUnit() != null && (tile.getCombatUnit().getCurrentX() != tile.getX() || tile.getCombatUnit().getCurrentY() != tile.getY())) {
                    tile.setCombatUnit(null);
                }
                if (tile.getNonCombatUnit() != null && (tile.getNonCombatUnit().getCurrentX() != tile.getX() || tile.getNonCombatUnit().getCurrentY() != tile.getY())) {
                    tile.setNonCombatUnit(null);
                }
            }
        }
    }

    // Getters and Setters
    public static int getWidth() {
        return width;
    }

    public static int getLength() {
        return length;
    }

    public static Tile[][] getMap() {
        return tilesMap;
    }

    public static Cell[][] getCellsMap() {
        return cellsMap;
    }

    public static int[] getTileCenterByCoordinates(int x, int y) {
        return tileCenters[x][y];
    }

    public static Tile getTileByCoordinates(int x, int y) {
        return tilesMap[x][y];
    }
    //-------------------------

    private void setRiver(int x, int y, int riverSide) {

    }

    private void setRiverCells(int x, int y, int riverSide) {
        int cellX = tileCenters[x][y][0], cellY = tileCenters[x][y][1];
        if (riverSide == 0 || riverSide == 3) {
            int direction = 1;
            if (riverSide == 3) {
                direction = -1;
            }
            for (int i = cellX - direction * 2, j = cellY - 2; j <= cellY + 2; j++)
                cellsMap[i][j].setColor(Colors.BLACK);
        }


    }

    private Tile getTileByRiver(int x, int y, int riverSide) {
        if (y % 2 == 1) {
            if (riverSide == 0)
                return tilesMap[x - 1][y];
            else if (riverSide == 1)
                return tilesMap[x][y + 1];
            else if (riverSide == 2)
                return tilesMap[x + 1][y + 1];
            else if (riverSide == 3)
                return tilesMap[x + 1][y];
            else if (riverSide == 4)
                return tilesMap[x + 1][y - 1];
            else if (riverSide == 5)
                return tilesMap[x][y - 1];
        } else if (y % 2 == 0) {
            if (riverSide == 0)
                return tilesMap[x - 1][y];
            if (riverSide == 1)
                return tilesMap[x - 1][y + 1];
            if (riverSide == 2)
                return tilesMap[x][y + 1];
            if (riverSide == 3)
                return tilesMap[x + 1][y];
            if (riverSide == 4)
                return tilesMap[x][y - 1];
            if (riverSide == 5)
                return tilesMap[x - 1][y - 1];
        }
        return null;
    }

    private void generateRivers() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < 6; k++) {

                }
            }
        }
    }

}