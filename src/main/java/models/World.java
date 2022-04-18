package models;

import java.util.ArrayList;
import java.util.HashMap;

public class World {
    private ArrayList<String> civilizationNames = new ArrayList<>();
    private HashMap<String, Civilization> nations = new HashMap<>();

    private int year;

    private Tile[][] map = new Tile[100][100];

    private int turn;

    public World(ArrayList<String> players){

        generateMap();
    }

    public void generateMap(){

    }
}
