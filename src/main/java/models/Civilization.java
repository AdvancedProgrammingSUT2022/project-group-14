
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
    private ArrayList<String> citiesNames = new ArrayList<>();
    private ArrayList<City> colonies = new ArrayList<>();
    private City firstCapital;
    private City currentCapital;

    private HashMap<Technologies, Integer> technologies = new HashMap<>();
    private Technologies currentTechnology;
    private ArrayList<Researches> researches = new ArrayList<>();

    private double food, gold, production, happiness, science;

    private int citizens;
    //TODO may consider a new way to handle citizens

    public Civilization(String name) {
        Random random = new Random();
        int randomX = random.nextInt(40), randomY = random.nextInt(80);
        Melee melee = new Melee(enums.units.Unit.getUnitByName("warrior"), randomX, randomY, name);
        Settler settler = new Settler(enums.units.Unit.getUnitByName("settler"), randomX, randomY, name);
        addMeleeUnit(melee);
        addSettler(settler);
        this.name = name;
        citiesNames.add(name + "1"); citiesNames.add(name + "2"); citiesNames.add(name + "3");

        for (Technologies technology : Technologies.values()) {
            technologies.put(technology, technology.getCost());
        }
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

    public String getCityName() {
        String name = citiesNames.get(0);
        citiesNames.remove(0);
        return name;
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

    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    public void setScience(double science) {
        this.science = science;
    }

    public double getHappiness() {
        return happiness;
    }

    public double getScience() {
        return science;
    }
}
