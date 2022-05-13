package controllers;

import enums.Colors;
import models.Cell;
import models.Civilization;
import models.Tile;
import models.units.CombatUnit;
import models.units.NonCombatUnit;
import models.units.Unit;

import java.util.ArrayList;
import java.util.Random;

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
        mapInit();
        generateRivers();
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

    //Refresh data
    private static void riverCellsRefresh() {
        Civilization civilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        int[][] visionStatesOfMap = civilization.getVisionStatesOfMap();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < length; j++)
                for (int k = 0; k < 6; k++) {
                    if (visionStatesOfMap[i][j] != 0 && tilesMap[i][j].getIsRiver()[k])
                        setRiverCells(i, j, k);
                }
    }

    private static void upLayerTileCellsRefresh(int[] tileCenter, Tile tile) {
        Colors color = tile.getColor();
        Civilization civilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        int[][] visionStatesOfMap = civilization.getVisionStatesOfMap();
        if (visionStatesOfMap[tile.getX()][tile.getY()] == 0)
            color = Colors.BLACK;
        for (int i = tileCenter[0]; i >= tileCenter[0] - 2; i--)
            for (int j = tileCenter[1] - 4 + (tileCenter[0] - i); j <= tileCenter[1] + 4 - (tileCenter[0] - i); j++) {
                cellsMap[i][j].setColor(color);
            }
    }

    private static void downLayerTileCellsRefresh(int[] tileCenter, Tile tile) {
        Colors color = tile.getColor();
        Civilization civilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        int[][] visionStatesOfMap = civilization.getVisionStatesOfMap();
        if (visionStatesOfMap[tile.getX()][tile.getY()] == 0)
            color = Colors.BLACK;
        for (int i = tileCenter[0] + 1; i <= tileCenter[0] + 3; i++)
            for (int j = tileCenter[1] - 4 + (i - tileCenter[0] - 1); j <= tileCenter[1] + 4 - (i - tileCenter[0] - 1); j++) {
                cellsMap[i][j].setColor(color);
            }
    }

    private static void tileTextsRefresh(int[] tileCenter, Tile tile, Civilization civilization) {
        //TODO name substring
        int[][] visionStatesOfMap = civilization.getVisionStatesOfMap();
        String civilizationName = civilization.getName();
        if (civilizationName.length() > 5)
            civilizationName = civilizationName.substring(0, 5);

        if (visionStatesOfMap[tile.getX()][tile.getY()] != 0) {
            String coordinates = "(" + (tile.getX() + 1) + "," + (tile.getY() + 1) + ")";
            printStringToCellsMap(coordinates, tileCenter[0] - 1, tileCenter[1] - 3);
            printStringToCellsMap(civilizationName, tileCenter[0] - 2, tileCenter[1] - 2);
            printStringToCellsMap(tile.getName(), tileCenter[0] + 1, tileCenter[1] - 4);
            Unit unit;
            if ((unit = tile.getCombatUnit()) != null)
                printStringToCellsMap(unit.getName(), tileCenter[0], tileCenter[1] - 4);
            if ((unit = tile.getNonCombatUnit()) != null)
                printStringToCellsMap(unit.getName(), tileCenter[0] + 2, tileCenter[1] - 3);

        }

    }

    public static void cellsRefresh() { // initialize cells of every tile
        Civilization civilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        int[][] visionStatesOfMap = civilization.getVisionStatesOfMap();
        String coordinates;
        String civilizationName = civilization.getName();
        if (civilizationName.length() > 5)
            civilizationName = civilizationName.substring(0, 5);
        for (int i = 0; i < width; i++)
            for (int j = 0; j < length; j++) {
                upLayerTileCellsRefresh(tileCenters[i][j], tilesMap[i][j]);
                downLayerTileCellsRefresh(tileCenters[i][j], tilesMap[i][j]);
                tileTextsRefresh(tileCenters[i][j], tilesMap[i][j], civilization);




            }
        riverCellsRefresh();
    }

    // River initialization
    private static void setRiver(int x, int y, int riverSide) { // creates river
        boolean[] isRiver = tilesMap[x][y].getIsRiver();
        isRiver[riverSide] = true;
        Tile neighbourTile = getTileByRiver(x, y, riverSide);
        if (neighbourTile == null)
            return;
        boolean[] neighbourIsRiver = neighbourTile.getIsRiver();
        int neighbourRiverSide = (riverSide + 3) % 6;
        neighbourIsRiver[neighbourRiverSide] = true;
    }

    private static Tile getTileByRiver(int x, int y, int riverSide) {
        if (y % 2 == 1) {
            if (riverSide == 0 && x - 1 >= 0)
                return tilesMap[x - 1][y];
            else if (riverSide == 1 && y + 1 < length)
                return tilesMap[x][y + 1];
            else if (riverSide == 2 && x + 1 < width && y + 1 < length)
                return tilesMap[x + 1][y + 1];
            else if (riverSide == 3 && x + 1 < width)
                return tilesMap[x + 1][y];
            else if (riverSide == 4 && x + 1 < width)
                return tilesMap[x + 1][y - 1];
            else if (riverSide == 5)
                return tilesMap[x][y - 1];
        } else if (y % 2 == 0) {
            if (riverSide == 0 && x - 1 >= 0)
                return tilesMap[x - 1][y];
            if (riverSide == 1 && x - 1 >= 0 && y + 1 < length)
                return tilesMap[x - 1][y + 1];
            if (riverSide == 2 && y + 1 < length)
                return tilesMap[x][y + 1];
            if (riverSide == 3 && x + 1 < width)
                return tilesMap[x + 1][y];
            if (riverSide == 4 && y - 1 >= 0)
                return tilesMap[x][y - 1];
            if (riverSide == 5 && x - 1 >= 0 && y - 1 >= 0)
                return tilesMap[x - 1][y - 1];
        }
        return null;
    }

    private static void setRiverCells(int x, int y, int riverSide) {
        int cellX = tileCenters[x][y][0], cellY = tileCenters[x][y][1];
        if (riverSide == 0 || riverSide == 3) {
            int direction = 1;
            if (riverSide == 3) direction = -1;
            for (int i = cellX - direction * 3, j = cellY - 2; j <= cellY + 2; j++)
                cellsMap[i][j].setColor(Colors.CYAN);
        } else if (riverSide == 1 || riverSide == 5) {
            int direction = 1;
            if (riverSide == 5) direction = -1;
            for (int i = cellX - 2, j = cellY + direction * 3; i <= cellX; i++, j += direction)
                cellsMap[i][j].setColor(Colors.CYAN);
        } else {
            int direction = 1;
            if (riverSide == 4) direction = -1;
            for (int i = cellX + 3, j = cellY + direction * 3; i >= cellX + 1; i--, j += direction)
                cellsMap[i][j].setColor(Colors.CYAN);
        }


    }

    private static void generateRivers() {
        Random rand = new Random();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                for (int k = 0; k < 6; k++) {
                    if (rand.nextInt(12) == 0) {
                        setRiver(i, j, k);
                    }
                }
            }
        }
    }

    private static void mapInit() {
        bordersInit();
        tileCentersInit();
    }

    public static void printStringToCellsMap(String input, int x, int y) {
        for (int i = 0; i < input.length(); i++) {
            if (i + y < outputMapLength)
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


}
