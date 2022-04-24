package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import enums.tiles.TileType;

public class World {
    private ArrayList<String> civilizationNames = new ArrayList<>();
    private HashMap<String, Civilization> nations = new HashMap<>();

    private int year;

    private static final int width = 45;
    private static final int length = 80;
    private Tile[][] map = new Tile[width][length];
    private int turn;


    public World(ArrayList<String> players) {
        civilizationNames.addAll(players);
        for (String civilizationName : civilizationNames) {
            nations.put(civilizationName, new Civilization(civilizationName));
        }
        this.year = -3000;
        this.turn = 0;
        generateMap();
    }

    public static int getWidth() {
        return width;
    }

    public static int getLength() {
        return length;
    }

    public String getCurrentCivilizationName() {
        return civilizationNames.get(turn);
    }

    public Civilization getCivilizationByName(String name) {
        return nations.get(name);
    }

    public Tile[][] getMap() {
        return this.map;
    }

    public void generateMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                this.map[i][j] = Tile.generateRandomTile(i, j);
            }
        }
    }

    public void nextTurn() {
        this.turn++;
        this.turn %= nations.size();
    }

    public int getTurn() {
        return this.turn;
    }

    public HashMap<String, Civilization> getNations() {
        return nations;
    }

    public Tile getTileByCoordinates(int x, int y) {
        return this.map[x-1][y-1];
    }
}
