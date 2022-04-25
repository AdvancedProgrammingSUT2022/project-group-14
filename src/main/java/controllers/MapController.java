package controllers;

import models.Tile;

public class MapController {
    private static int width = 45;
    private static int length = 80;

    public static int getWidth() {
        return width;
    }
    public static int getLength() {
        return length;
    }

    public static Tile[][] generateMap() {
        Tile[][] map = new Tile[width][length];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                map[i][j] = Tile.generateRandomTile(i, j);
            }
        }
        return map;
    }
}
