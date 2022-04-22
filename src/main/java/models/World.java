package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class World {
    private static ArrayList<String> civilizationNames = new ArrayList<>();
    private static HashMap<String, Civilization> nations = new HashMap<>();

    private static int year;

    private static Tile[][] map = new Tile[100][100];

    private static int turn;

    public World(ArrayList<String> players){
        civilizationNames.addAll(players);
        generateMap();
    }

    public static String getCurrentCivilizationName() {
        return civilizationNames.get(turn);
    }

    public void generateMap(){

    }
}
