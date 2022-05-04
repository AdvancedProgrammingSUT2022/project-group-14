package models;

import enums.Buildings;
import enums.Technologies;

import java.util.ArrayList;

public class Building {
    private String name;
    private int cost;
    private int maintenance;
    private Technologies requiredTechnology;
    private ArrayList<String> requiredBuildings;

    public Building(Buildings buildings){
        this.name = buildings.name();
        this.cost = buildings.getCost();
        this.maintenance = buildings.getMaintenance();
        this.requiredTechnology = buildings.getRequiredTechnology();
    }

    public int getCost() {
        return cost;
    }
}
