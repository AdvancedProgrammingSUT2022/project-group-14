package models;

import java.util.ArrayList;
import java.util.HashMap;

import enums.tiles.TileType;

public class World {
    private ArrayList<String> civilizationNames = new ArrayList<>();
    private HashMap<String, Civilization> nations = new HashMap<>();

    private int year;

    private Tile[][] map = new Tile[5][12];

    private int turn;

    public World(ArrayList<String> players) {

        generateMap();
    }

    public void generateMap() {

    }
}
