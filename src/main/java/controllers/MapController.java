package controllers;

import models.Civilization;
import models.Tile;
import models.units.CombatUnit;
import models.units.NonCombatUnit;
import models.units.Unit;

import java.util.ArrayList;

public class MapController {

    private static final int width = 3;
    private static final int length = 7;
    private static final int outputMapWidth = 6 * width + 3;
    private static final int outputMapLength = 8 * length + 3;

    private static int[][][] tileCenter = new int[width][length][2];
    private static Cell[][] cellsMap = new Cell[outputMapWidth][outputMapLength];
    private static Tile[][] tilesMap = new Tile[width][length];

    public static void generateMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                tilesMap[i][j] = Tile.generateRandomTile(i, j);
            }
        }
    }

    private void upLayerBordersInit(int row) {
        for (int j = 0; j < 3; j++)
            for (int k = 0; k < outputMapLength; k++) {
                Cell cell = new Cell();
                cellsMap[6 * row + j][k] = cell;
                cell.setColor(Colors.RESET);

                if (k % 16 == (2 - j) && (k < outputMapLength - 3 || row > 0) && (k > 2 || row < width)) {
                    cell.setCh('/');
                } else if (k % 16 == (8 + j) && (row < width || k < outputMapLength - 3)) {
                    cell.setCh('\\');
                } else if (j == 2 && (k % 16) >= 11 && (k % 16) <= 15) {
                    cell.setCh('_');
                } else {
                    cell.setCh(' ');
                }
            }
    }

    private void downLayerBordersInit(int row) {
        for (int j = 0; j < 3; j++)
            for (int k = 0; k < outputMapLength; k++) {
                Cell cell = new Cell();
                cellsMap[6 * row + 3 + j][k] = cell;
                cell.setColor(Colors.RESET);
                if (k % 16 == (0 + j)) {
                    cell.setCh('\\');
                } else if (k % 16 == (10 - j)) {
                    cell.setCh('/');
                } else if (j == 2 && (k % 16) >= 3 && (k % 16) <= 7) {
                    cell.setCh('_');
                } else {
                    cell.setCh(' ');
                }
            }
    }

    private void bordersInit() {
        for (int i = 0; i <= width - 1; i++) {
            upLayerBordersInit(i);
            downLayerBordersInit(i);
        }
        upLayerBordersInit(width);

    }

    private void tileCentersInit() {
        for (int x = 2, i = 0; x < outputMapWidth - width; x += 6, i++)
            for (int y = 5, j = 0; y < outputMapLength; y += 16, j += 2) {
                tileCenter[i][j][0] = x;
                tileCenter[i][j][1] = y;
            }

        for (int x = 5, i = 0; x < outputMapWidth; x += 6, i++)
            for (int y = 13, j = 1; y < outputMapLength; y += 16, j += 2) {
                tileCenter[i][j][0] = x;
                tileCenter[i][j][1] = y;
            }

    }

    private void upLayerTileCellsInit(int[] tileCenter, Tile tile) {
        for (int i = tileCenter[0]; i >= tileCenter[0] - 2; i--)
            for (int j = tileCenter[1] - 4 + (tileCenter[0] - i); j <= tileCenter[1] + 4 - (tileCenter[0] - i); j++) {
                cellsMap[i][j].setColor(tile.getColor());
            }
    }

    private void downLayerTileCellsInit(int[] tileCenter, Tile tile) {
        for (int i = tileCenter[0] + 1; i <= tileCenter[0] + 3; i++)
            for (int j = tileCenter[1] - 4 + (i - tileCenter[0] - 1); j <= tileCenter[1] + 4
                    - (i - tileCenter[0] - 1); j++) {
                cellsMap[i][j].setColor(tile.getColor());
            }

    }

    private void tileCellsInit() { // initialaze cells of every tile
        String cordinates = "";
        for (int i = 0; i < width; i++)
            for (int j = 0; j < length; j++) {
                upLayerTileCellsInit(tileCenter[i][j], tilesMap[i][j]);
                downLayerTileCellsInit(tileCenter[i][j], tilesMap[i][j]);
                cordinates = "(" + i + "," + j + ")";
                printStringToCellsMap(cordinates, tileCenter[i][j][0] - 1, tileCenter[i][j][1] - 3);
                printStringToCellsMap(tilesMap[i][j].getType().getName(), tileCenter[i][j][0] + 1,
                        tileCenter[i][j][1] - 4);
            }

    }

    public void mapInit() {
        generateMap();
        bordersInit();
        tileCentersInit();
        tileCellsInit();
        printStringToCellsMap("salam", 3, 1);
    }

    private void printStringToCellsMap(String input, int x, int y) {
        for (int i = 0; i < input.length(); i++) {
            cellsMap[x][i + y].setCh(input.charAt(i));
        }
    }

    // --------------------------

    public static int getWidth() {
        return width;
    }

    public static int getLength() {
        return length;
    }

    public static void resetMap() {
        for (Tile[] tiles : tilesMap) {
            for (Tile tile : tiles) {
                tile = null;
            }
        }
    }

    public static Tile[][] getMap() {
        return tilesMap;
    }

    public static Tile getTileByCoordinates(int x, int y) {
        return tilesMap[x][y];
    }

    public static void updateUnitPositions() {
        ArrayList<Unit> allUnitsInGame = new ArrayList<>();
        for (Civilization civilization : WorldController.getWorld().getAllCivilizations()) {
            allUnitsInGame.addAll(civilization.getAllUnits());
        }

        for (Unit unit : allUnitsInGame) {
            if (unit instanceof CombatUnit) {
                map[unit.getCurrentX()][unit.getCurrentY()].setCombatUnit((CombatUnit) unit);
            }
            if (unit instanceof NonCombatUnit) {
                map[unit.getCurrentX()][unit.getCurrentY()].setNonCombatUnit((NonCombatUnit) unit);
            }
        }
        for (Tile[] tiles : tilesMap) {
            for (Tile tile : tiles) {
                if (tile.getCombatUnit() != null &&
                        (tile.getCombatUnit().getCurrentX() != tile.getX()
                                || tile.getCombatUnit().getCurrentY() != tile.getY())) {
                    tile.setCombatUnit(null);
                }
                if (tile.getNonCombatUnit() != null &&
                        (tile.getNonCombatUnit().getCurrentX() != tile.getX()
                                || tile.getNonCombatUnit().getCurrentY() != tile.getY())) {
                    tile.setNonCombatUnit(null);
                }
            }
        }
    }
}
