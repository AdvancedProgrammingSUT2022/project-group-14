package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class World {
    private ArrayList<String> civilizationNames = new ArrayList<>();
    private HashMap<String, Civilization> nations = new HashMap<>();

    private int year;

    private Tile[][] map = new Tile[100][100];

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

    public void generateMap(){

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
