package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import enums.tiles.TileType;

public class World {
    private ArrayList<String> civilizationNames = new ArrayList<>();
    private HashMap<String, Civilization> nations = new HashMap<>();

    private int year;

    public final int width = 3;
    public final int length = 12;
    private Tile[][] map = new Tile[width][length];
    private int turn;


    public World(ArrayList<String> players){
        civilizationNames.addAll(players);
        generateMap();
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
                this.map[i][j] = Tile.generateRandomTile();
            }
        }
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getTurn() {
        return this.turn;
    }

    public HashMap<String, Civilization> getNations() {
        return nations;
    }

    public Tile getTileByCoordinates(int x, int y){
        return this.map[x][y];
    }
}
