package models;

import java.util.ArrayList;
import java.util.HashMap;

import enums.tiles.TileType;

public class World {
    private ArrayList<String> civilizationNames = new ArrayList<>();
    private HashMap<String, Civilization> nations = new HashMap<>();

    private int year;

    private Tile[][] map = null;

    private int turn;

    public World(ArrayList<String> players) {

        generateMap();
    }

    public void generateMap() {
        for (int i = 1; i <= 5; i++) {
            for (int j = 1; j <= 12; j++) {
                map[i][j] = Tile.generateRandomTile();
            }
        }
    }
}
