package controllers;

import models.Civilization;
import models.Tile;
import models.units.CombatUnit;
import models.units.NonCombatUnit;
import models.units.Unit;

import java.util.ArrayList;

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
        for (Tile[] tiles : map) {
            for (Tile tile : tiles) {
                if (tile.getCombatUnit() != null &&
                        (tile.getCombatUnit().getCurrentX() != tile.getX() || tile.getCombatUnit().getCurrentY() != tile.getY())) {
                    tile.setCombatUnit(null);
                }
                if (tile.getNonCombatUnit() != null &&
                        (tile.getNonCombatUnit().getCurrentX() != tile.getX() || tile.getNonCombatUnit().getCurrentY() != tile.getY())) {
                    tile.setNonCombatUnit(null);
                }
            }
        }
    }
}
