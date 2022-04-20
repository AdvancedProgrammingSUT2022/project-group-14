package models;

import enums.Researches;
import enums.Technologies;
import models.units.*;
import org.w3c.dom.ranges.Range;

import java.util.ArrayList;
import java.util.HashMap;

public class Civilization {
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

    private HashMap<Technologies , Integer> technologies = new HashMap<>();

    private City firstCapital;
    private City currentCapital;

    private double food;
    private double gold;
    private double production;
    private double happiness;
    private int citizens;
    private Technologies currentTechnology;

    public Civilization(){

    }

    public void addCity(City city){

    }

    public void removeCity(City city){

    }

    public void addColony(City city){

    }

    public void removeColony(City city){

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
        if(true) {
            //TODO if it was near our units or cities is lucid
        } else {
            //TODO if it isn't and the tile is lucid change to half lucid
        }
    }

}
