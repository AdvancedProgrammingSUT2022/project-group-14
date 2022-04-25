package controllers;

import models.Tile;

public class MapController {
    private static int width = 45;
    private static int length = 80;

    private static Tile[][] map = new Tile[width][length];

    public static int getWidth() {
        return width;
    }
    public static int getLength() {
        return length;
    }

    public static void generateMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                map[i][j] = Tile.generateRandomTile(i, j);
            }
        }
    }

    public static void resetMap() {
        for (Tile[] tiles : map) {
            for (Tile tile : tiles) {
                tile = null;
            }
        }
    }

    public static Tile[][] getMap() {
        return map;
    }

    public static Tile getTileByCoordinates(int x, int y) {
        return map[x][y];
    }
}
