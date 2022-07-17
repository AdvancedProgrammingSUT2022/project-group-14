package controllers;

import models.Civilization;
import models.tiles.Coordination;
import models.tiles.Tile;
import models.units.CombatUnit;
import models.units.NonCombatUnit;
import models.units.Unit;

import java.util.ArrayList;
import java.util.Random;

public class MapController {

    public static int width;
    public static int height;
    private static Tile[][] tilesMap;

    public static void generateMap(int wantedWidth, int wantedHeight) {
        width = wantedWidth;
        height = wantedHeight;
        tilesMap = new Tile[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tilesMap[i][j] = Tile.generateRandomTile(i, j);
            }
        }
        generateRivers();
    }

    // River initialization
    private static void generateRivers() {
        Random rand = new Random();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < 6; k++) {
                    if (rand.nextInt(12) == 0 && !tilesMap[i][j].getIsRiver()[k]) {
                        setRiver(i, j, k);
                    }
                }
            }
        }
    }

    private static void setRiver(int x, int y, int riverSide) { // creates river
        boolean[] isRiver = tilesMap[x][y].getIsRiver();
        isRiver[riverSide] = true;
        Tile neighbourTile = getTileByRiver(x, y, riverSide);
        tilesMap[x][y].setGold(tilesMap[x][y].getGold() + 1);
        if (neighbourTile == null)
            return;
        neighbourTile.setGold(neighbourTile.getGold() + 1);
        boolean[] neighbourIsRiver = neighbourTile.getIsRiver();
        int neighbourRiverSide = (riverSide + 3) % 6;
        neighbourIsRiver[neighbourRiverSide] = true;
    }

    private static Tile getTileByRiver(int x, int y, int riverSide) {
        switch (riverSide) {
            case 0:
                if (x - 2 >= 0)
                    return tilesMap[x - 2][y];
                break;
            case 1:
                if (x - 1 >= 0 && y + (x % 2 == 1 ? 1 : 0) < height)
                    return tilesMap[x - 1][y + (x % 2 == 1 ? 1 : 0)];
                break;
            case 2:
                if (x + 1 < width && y + (x % 2 == 1 ? 1 : 0) < height)
                    return tilesMap[x + 1][y + (x % 2 == 1 ? 1 : 0)];
                break;
            case 3:
                if (x + 2 < width)
                    return tilesMap[x + 2][y];
                break;
            case 4:
                if (x + 1 < width && y + (x % 2 == 1 ? 0 : -1) >= 0)
                    return tilesMap[x + 1][y + (x % 2 == 1 ? 0 : -1)];
                break;
            case 5:
                if (x - 1 >= 0 && y + (x % 2 == 1 ? 0 : -1) >= 0)
                    return tilesMap[x - 1][y + (x % 2 == 1 ? 0 : -1)];
                break;
        }

        return null;
    }

    public static void updateUnitPositions() {
        ArrayList<Unit> allUnitsInGame = new ArrayList<>();
        for (Civilization civilization : WorldController.getWorld().getAllCivilizations()) {
            allUnitsInGame.addAll(civilization.getAllUnits());
        }

        for (Unit unit : allUnitsInGame) {
            if (unit instanceof CombatUnit)
                tilesMap[unit.getCurrentX()][unit.getCurrentY()].setCombatUnit((CombatUnit) unit);
            if (unit instanceof NonCombatUnit)
                tilesMap[unit.getCurrentX()][unit.getCurrentY()].setNonCombatUnit((NonCombatUnit) unit);
        }
        for (Tile[] tiles : tilesMap) {
            for (Tile tile : tiles) {
                if (tile.getCombatUnit() != null && (tile.getCombatUnit().getCurrentX() != tile.getX() || tile.getCombatUnit().getCurrentY() != tile.getY())) {
                    tile.setCombatUnit(null);
                }
                if (tile.getNonCombatUnit() != null && (tile.getNonCombatUnit().getCurrentX() != tile.getX() || tile.getNonCombatUnit().getCurrentY() != tile.getY())) {
                    tile.setNonCombatUnit(null);
                }
                tile.getHex().updateHex();
            }
        }
    }

    public static int getWidth() {
        return width;
    }

    public static int getHeight() {
        return height;
    }

    public static Tile[][] getMap() {
        return tilesMap;
    }

    public static Tile getTileByCoordinates(int x, int y) {
        return tilesMap[x][y];
    }

    public static Tile getTileByCoordinates(Coordination coordination) {
        return tilesMap[coordination.getX()][coordination.getY()];
    }


}
