
package models;

import enums.Researches;
import enums.Technologies;
import models.units.*;
import org.w3c.dom.ranges.Range;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {
    private String name;

    private Tile[][] mapFromPov;

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
        this.name = name;
        updateMapVision();
        //TODO  add first warrior and settler
        firstCapital = null;
        currentCapital = null;
        currentTechnology = null;
        food = 0; gold = 0; production = 0; happiness = 0;
        citizens = 0;
    }

    public String getName() {
        return name;
    }

    public void updateMapVision() {
        ArrayList<Unit> allUnits = getAllUnits();
        if (true) {
            //TODO if it was near our units or cities is lucid
        } else {
            //TODO if it isn't and the tile is lucid change to half lucid
        }
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

    public void setCurrentTechnology(Technologies wantedTechnology) {
        this.currentTechnology = wantedTechnology;
    }

    public void updateTechnology() {
        this.technologies.put(this.currentTechnology, this.technologies.get(this.currentTechnology)-1);
        if (this.technologies.get(this.currentTechnology) <= 0) {
            this.currentTechnology = null;
        }
    }

    public void updateGoods() {
        for (City city : cities) {
            //TODO add each city goods
        }
    }
}
