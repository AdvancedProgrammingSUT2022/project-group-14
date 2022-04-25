package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import enums.tiles.TileType;

public class World {
    private ArrayList<Civilization> civilizations = new ArrayList<>();

    private int year;
    private int evolutionSpeed;
    private int turn;

    private static int width = 45;
    private static int length = 80;
    private Tile[][] map = new Tile[width][length];



    public World(ArrayList<String> players) {
        for (int i = 0; i < players.size(); i++) {
            civilizations.add(new Civilization(players.get(i)));
        }
        year = -3000;
        evolutionSpeed = 10;
        turn = 0;
        generateMap();
    }

    public String getCurrentCivilizationName() {
        return civilizations.get(turn).getName();
    }

    public Civilization getCivilizationByName(String name) {
        for (Civilization civilization : civilizations) {
            if (civilization.getName().equals(name))
                return civilization;
        }
        return null;
    }

    public int getTurn() {
        return this.turn;
    }

    public void nextTurn() {
        turn++;
        turn %= civilizations.size();
        year += evolutionSpeed;
    }

    public static int getWidth() {
        return width;
    }

    public static int getLength() {
        return length;
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

    public Tile getTileByCoordinates(int x, int y) {
        return this.map[x-1][y-1];
    }
}
