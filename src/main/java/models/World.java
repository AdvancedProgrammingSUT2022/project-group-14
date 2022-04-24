package models;

import java.util.ArrayList;
import java.util.HashMap;

import enums.tiles.TileType;

public class World {
    private ArrayList<String> civilizationNames = new ArrayList<>();
    private HashMap<String, Civilization> nations = new HashMap<>();

    private int year;

    public final int width = 5;
    public final int length = 12;
    private Tile[][] map = new Tile[width][length];
    private int turn;

    public World(ArrayList<String> players) {
        generateMap();
    }

    public Tile[][] getMap() {
        return this.map;
    }

    public void generateMap() {
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < length; j++) {
                this.map[i][j] = Tile.generateRandomTile();
            }
        }
    }
}
