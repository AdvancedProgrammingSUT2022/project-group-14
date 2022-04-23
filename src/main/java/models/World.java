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

    public void generateMap(){

    }
}
