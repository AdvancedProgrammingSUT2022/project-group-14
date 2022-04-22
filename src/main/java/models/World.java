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

        generateMap();
    }

    public static ArrayList<Civilization> getAllCivilizations() {
        Collection<Civilization> civilizations = nations.values();
        return new ArrayList<>(civilizations);
    }

    public void generateMap(){

    }
}
