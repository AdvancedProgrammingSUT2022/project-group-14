
package models;

import enums.Researches;
import enums.Technologies;
import models.units.*;
import org.w3c.dom.ranges.Range;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {
    private String name;
    private ArrayList<Tile> lucid = new ArrayList<>();
    private ArrayList<Tile> halfLucid = new ArrayList<>();

    private ArrayList<City> discoveredCities = new ArrayList<>();

    private ArrayList<Melee> melees = new ArrayList<>();
    private ArrayList<Ranged> ranges = new ArrayList<>();
    private ArrayList<Worker> workers = new ArrayList<>();
    private ArrayList<Settler> settlers = new ArrayList<>();

    private ArrayList<City> cities = new ArrayList<>();
    private ArrayList<City> colonies = new ArrayList<>();

    private ArrayList<Researches> researches = new ArrayList<>();

    private HashMap<Technologies, Integer> technologies = new HashMap<>();

    private City firstCapital;
    private City currentCapital;

    private double food;
    private double gold;
    private double production;
    private double happiness;
    private int citizens;
    private Technologies currentTechnology;

    public Civilization(String name) {
        //TODO  add first warrior and settler
        this.name = name;
        firstCapital = null;
        currentCapital = null;
        //TODO goods
        updateMapVision();
    }

    public String getName() {
        return name;
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

    public void addMeleeUnit(Melee unit) {
        melees.add(unit);
    }

    public void addRangedUnit(Ranged unit) {
        ranges.add(unit);
    }

    public void addSettler(Settler unit) {
        settlers.add(unit);
    }

    public void addMeleeUnit(Worker unit) {
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

    public void updateMapVision() {
        ArrayList<Unit> allUnits = getAllUnits();
        if (true) {
            //TODO if it was near our units or cities is lucid
        } else {
            //TODO if it isn't and the tile is lucid change to half lucid
        }
    }

    public void updateGoods() {
        for (City city : cities) {
            //TODO add each city goods
        }
    }

    public void setCurrentTechnology(Technologies wantedTechnology) {
        this.currentTechnology = wantedTechnology;
    }

    public void updateTechnology() {
        this.technologies.put(this.currentTechnology, this.technologies.get(this.currentTechnology)-1);
        if (this.technologies.get(this.currentTechnology) <= 0) {
            this.currentTechnology = null;
        }
    }
}
