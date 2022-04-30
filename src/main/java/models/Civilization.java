
package models;

import controllers.MapController;
import enums.Researches;
import enums.Technologies;
import models.units.*;

import java.util.*;

public class Civilization {
    private String name;

    private int[][] visionStatesOfMap = new int[MapController.getWidth()][MapController.getLength()];
    private Tile[][] revealedTiles = new Tile[MapController.getWidth()][MapController.getLength()];


    private ArrayList<Melee> melees = new ArrayList<>();
    private ArrayList<Ranged> ranges = new ArrayList<>();
    private ArrayList<Worker> workers = new ArrayList<>();
    private ArrayList<Settler> settlers = new ArrayList<>();

    private ArrayList<City> cities = new ArrayList<>();
    private ArrayList<City> colonies = new ArrayList<>();
    private City firstCapital;
    private City currentCapital;

    private HashMap<Technologies, Integer> technologies = new HashMap<>();
    private Technologies currentTechnology;
    private ArrayList<Researches> researches = new ArrayList<>();

    private double food, gold, production, happiness;

    private int citizens;
    //TODO may consider a new way to handle citizens

    public Civilization(String name) {
        Random random = new Random();
        int randomX = random.nextInt(40), randomY = random.nextInt(80);
        Melee melee = new Melee(randomX, randomY, 2, "warrior", name, 0, " ", " ", 10, 10, 10);
        addMeleeUnit(melee);
        Settler settler = new Settler(randomX, randomY, 2, "settler", name, 0, " ", " ", 10);
        addSettler(settler);
        System.out.println(randomX + " " + randomY);
        this.name = name;
        firstCapital = null;
        currentCapital = null;
        currentTechnology = null;
        food = 0; gold = 0; production = 0; happiness = 0;
        citizens = 0;
    }

    public String getName() {
        return name;
    }

    public int[][] getVisionStatesOfMap() {
        return this.visionStatesOfMap;
    }

    public Tile[][] getRevealedTiles() {
        return this.revealedTiles;
    }

    public void addMeleeUnit(Melee unit) {
        melees.add(unit);
    }

    public void addRangedUnit(Ranged unit) {
        ranges.add(unit);
    }

    public void addSettler(Settler unit) {
        settlers.add(unit);
    }

    public void addWorker(Worker unit) {
        workers.add(unit);
    }

    public void removeMeleeUnit(Melee unit) {
        melees.remove(unit);
    }

    public void removeRangedUnit(Ranged unit) {
        ranges.remove(unit);
    }

    public void removeWorker(Worker unit) {
        workers.remove(unit);
    }

    public void removeSettler(Settler unit) {
        settlers.remove(unit);
    }

    public ArrayList<Unit> getAllUnits() {
        ArrayList<Unit> allUnits = new ArrayList<>();
        allUnits.addAll(melees);
        allUnits.addAll(ranges);
        allUnits.addAll(workers);
        allUnits.addAll(settlers);
        return allUnits;
    }

    public ArrayList<City> getCities() {
        return cities;
    }

    public void addCity(City city) {
        if(cities.size() == 0 && firstCapital == null) {
            firstCapital = city;
        }
        cities.add(city);
    }

    public void removeCity(City city) {
        cities.remove(city);
    }

    public void addColony(City city) {
        colonies.add(city);
    }

    public void removeColony(City city) {
        colonies.remove(city);
    }

    public HashMap<Technologies, Integer> getTechnologies() {
        return technologies;
    }

    public Technologies getCurrentTechnology() {
        return currentTechnology;
    }

    public void setCurrentTechnology(Technologies wantedTechnology) {
        this.currentTechnology = wantedTechnology;
    }
}
