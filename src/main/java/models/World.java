package models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import controllers.MapController;
import enums.tiles.TileType;

public class World {
    private ArrayList<Civilization> civilizations = new ArrayList<>();

    private int year;
    private int evolutionSpeed;
    private int turn;

    public World(ArrayList<String> players) {
        for (int i = 0; i < players.size(); i++) {
            civilizations.add(new Civilization(players.get(i)));
        }
        year = -3000;
        evolutionSpeed = 10;
        turn = 0;
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

    public ArrayList<Civilization> getAllCivilizations() {
        return civilizations;
    }

    public int getTurn() {
        return this.turn;
    }

    public void nextTurn() {
        turn++;
        turn %= civilizations.size();
        year += evolutionSpeed;
    }

    public void increaseEvolutionSpeed(int amount) {
        evolutionSpeed += amount;
    }

}
