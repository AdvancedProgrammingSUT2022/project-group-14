package Server.models;

import java.util.ArrayList;
import java.util.HashMap;

import Server.controllers.CivilizationController;
import Server.controllers.ServerUpdateController;
import Server.controllers.UserController;
import Server.controllers.WorldController;
import Server.enums.QueryResponses;
import Server.models.network.Response;
import Server.models.units.Unit;
import com.google.gson.Gson;

public class World {
    private final ArrayList<Civilization> civilizations = new ArrayList<>();

    private int year;
    private int evolutionSpeed;
    private int turn, actualTurn;

    public World(ArrayList<String> players) {
        for (int i = 0; i < players.size(); i++) {
            this.civilizations.add(new Civilization(players.get(i), i, civilizations));
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
        turn %= civilizations.size() - 1;
        for (Unit unit : civilization.getAllUnits()) {
            unit = null;
        }
        civilizations.remove(civilization);
        ServerUpdateController.sendUpdate(civilization.getName(), new Response(QueryResponses.CHANGE_SCENE, new HashMap<>(){{
            put("sceneName", "endGamePage");
            put("winner", String.valueOf(false));
            put("user", new Gson().toJson(UserController.getUserByUsername(civilization.getName())));
        }}));
        if (civilizations.size() == 1) {
            WorldController.endGame(civilizations.get(0).getName());
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
        }
    }

    public void decreaseEvolutionSpeed(int amount) {
        evolutionSpeed -= amount;
    }

    public int getYear() {
        return year;
    }
}
