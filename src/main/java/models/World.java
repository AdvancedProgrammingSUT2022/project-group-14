package models;

import java.util.ArrayList;
import models.units.Unit;

public class World {
    private final ArrayList<Civilization> civilizations = new ArrayList<>();

    private int year;
    private int evolutionSpeed;
    private int turn, actualTurn;

    public World(ArrayList<String> players) {
        for (int i = 0; i < players.size(); i++) {
            this.civilizations.add(new Civilization(players.get(i), i));
        }
        year = -3000;
        evolutionSpeed = 10;
    }

    public String getCurrentCivilizationName() {
        return this.civilizations.get(turn).getName();
    }

    public Civilization getCivilizationByName(String name) {
        for (Civilization civilization : civilizations) {
            if (civilization.getName().equals(name))
                return civilization;
        }
        return null;
    }

    public ArrayList<Civilization> getAllCivilizations() {
        return this.civilizations;
    }

    public void removeCivilization(Civilization civilization) {
        for (Unit unit : civilization.getAllUnits()) {
            unit = null;
        }
        civilizations.remove(civilization);
    }

    public int getTurn() {
        return this.turn;
    }

    public int getActualTurn(){
        return this.actualTurn;
    }

    public void nextTurn() {
        actualTurn++;
        turn++;
        turn %= civilizations.size();
        year += evolutionSpeed;
    }

    public void increaseEvolutionSpeed(int amount) {
        evolutionSpeed += amount;
    }

    public int getYear() {
        return year;
    }
}
