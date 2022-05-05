package controllers;

import enums.Colors;
import models.Cell;
import models.Tile;

public class NewMapController {
    private static final int width = 3;
    private static final int length = 7;
    private static final int outputMapWidth = 6 * width + 3;
    private static final int outputMapLength = 8 * length + 3;

    private static int[][][] tileCenter = new int[width][length][2];
    private static Cell[][] outputMap = new Cell[outputMapWidth][outputMapLength];
    private static Tile[][] map = new Tile[width][length];

    public static void generateMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                map[i][j] = Tile.generateRandomTile(i, j);
            }
        }
    }

    private void upLayerBordersInit(int row) {
        for (int j = 0; j < 3; j++)
            for (int k = 0; k < outputMapLength; k++) {
                Cell cell = new Cell();
                outputMap[6 * row + j][k] = cell;
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
                outputMap[6 * row + 3 + j][k] = cell;
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
                outputMap[i][j].setColor(tile.getColor());
            }
    }

    private void downLayerTileCellsInit(int[] tileCenter, Tile tile) {
        for (int i = tileCenter[0] + 1; i <= tileCenter[0] + 3; i++)
            for (int j = tileCenter[1] - 4 + (i - tileCenter[0] - 1); j <= tileCenter[1] + 4
                    - (i - tileCenter[0] - 1); j++) {
                outputMap[i][j].setColor(tile.getColor());
            }

    }

    private void cellsInit() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                outputMap[tileCenter[i][j][0]][tileCenter[i][j][1]].setColor(map[i][j].getColor());
            }
        }
        upLayerTileCellsInit(tileCenter[0][0], map[0][0]);
        downLayerTileCellsInit(tileCenter[0][0], map[0][0]);
    }

    public void mapInit() {
        generateMap();
        bordersInit();
        tileCentersInit();
        cellsInit();
    }

    public void showMap() {
        for (int i = 0; i < 6 * width + 3; i++) {
            for (int j = 0; j < 8 * length + 3; j++) {
                System.out.print(outputMap[i][j].getColor().getAnsiEscapeCode() + outputMap[i][j].getCh()
                        + Colors.RESET.getAnsiEscapeCode());
            }
            System.out.println();
        }
    }

}