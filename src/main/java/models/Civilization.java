
package models;

import enums.Researches;
import enums.Technologies;
import models.units.Ranged;
import models.units.Settler;
import models.units.Melee;
import models.units.Worker;

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

}
