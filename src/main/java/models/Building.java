package models;

import enums.BuildingTypes;
import enums.Technologies;

import java.util.ArrayList;

public class Building {
    private final BuildingTypes buildingType;
    private ArrayList<String> requiredBuildings;

    public Building(BuildingTypes type) {
        this.buildingType = type;
    }


    public String getName() {
        return this.buildingType.getName();
    }

    public int getCost() {
        return this.buildingType.getCost();
    }

    public int getMaintenance() {
        return this.buildingType.getMaintenance();
    }

    public Technologies getRequiredTechnology() {
        return this.buildingType.getRequiredTechnology();
    }
}
