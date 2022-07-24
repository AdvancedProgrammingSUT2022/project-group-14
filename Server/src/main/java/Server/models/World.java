package Server.models;

import java.util.ArrayList;

import Server.controllers.CivilizationController;
import Server.controllers.WorldController;
import Server.models.units.Unit;

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
        evolutionSpeed = 100;
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
        turn %= civilizations.size();
        if (civilizations.size() == 1) {
//            GamePageController.stopTimeline = true;
            WorldController.endGame(civilizations.get(0).getName());
//            App.changeScene("endGamePage");
        }
    }

    public int getTurn() {
        return this.turn;
    }

    public int getActualTurn() {
        return this.actualTurn;
    }

    public void nextTurn() {
        actualTurn++;
        turn++;
        turn %= civilizations.size();
        year += evolutionSpeed;
        if (actualTurn % 10 == 0 && evolutionSpeed > 10)
            decreaseEvolutionSpeed(4);
        if (year == 2050) {
            WorldController.endGame(CivilizationController.getBestCivilization());
//            App.changeScene("endGamePage");
        }
    }

    public void decreaseEvolutionSpeed(int amount) {
        evolutionSpeed -= amount;
    }

    public int getYear() {
        return year;
    }
}
