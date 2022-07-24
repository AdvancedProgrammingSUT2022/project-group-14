package Client.models;

import Client.enums.BuildingTypes;
import Server.enums.BuildingTypes;
import Server.enums.Technologies;

public class Building {
    private final BuildingTypes buildingType;
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

    public BuildingTypes getBuildingType() {
        return buildingType;
    }
}
