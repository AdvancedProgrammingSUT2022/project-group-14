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

    public static void updateUnitPositions() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                if (map[i][j].getCombatUnit() != null &&
                        (map[i][j].getCombatUnit().getCurrentX() != i || map[i][j].getCombatUnit().getCurrentY() != j)) {
                    map[map[i][j].getCombatUnit().getCurrentX()][map[i][j].getCombatUnit().getCurrentY()].setCombatUnit(map[i][j].getCombatUnit());
                    map[i][j].setCombatUnit(null);
                }
                if (map[i][j].getNonCombatUnit() != null &&
                        (map[i][j].getNonCombatUnit().getCurrentX() != i || map[i][j].getNonCombatUnit().getCurrentY() != j)) {
                    map[map[i][j].getNonCombatUnit().getCurrentX()][map[i][j].getNonCombatUnit().getCurrentY()].setNonCombatUnit(map[i][j].getNonCombatUnit());
                    map[i][j].setNonCombatUnit(null);
                }
            }
        }
    }
}
