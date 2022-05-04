package controllers;

import enums.Colors;
import models.Cell;

public class NewMapController {
    private static final int width = 3;
    private static final int length = 7;

    private static int[][][] tileCenter = new int[width][length][2];
    private static Cell[][] map = new Cell[6 * width + 3][8 * length + 3];

    private void upLayerBordersInit(int row) {

        for (int j = 0; j < 3; j++)
            for (int k = 0; k < 8 * length + 3; k++) {
                Cell cell = new Cell();
                map[6 * row + j][k] = cell;
                cell.setColor(Colors.RESET);

                if (k % 16 == (2 - j) && (k < 8 * length || row > 0) && (k > 2 || row < width)) {
                    cell.setCh('/');
                } else if (k % 16 == (8 + j) && (row < width || k < 8 * length)) {
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
            for (int k = 0; k < 8 * length + 3; k++) {
                Cell cell = new Cell();
                map[6 * row + 3 + j][k] = cell;
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

    public void mapInit() {
        bordersInit();

    }

    public void showMap() {
        for (int i = 0; i < 6 * width + 3; i++) {
            for (int j = 0; j < 8 * length + 3; j++) {
                System.out.print(map[i][j].getCh());
            }
            System.out.println();
        }
    }

}
