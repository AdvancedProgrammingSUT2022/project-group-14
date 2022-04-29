package models.resources;

import java.util.ArrayList;

import enums.Improvements;
import enums.Technologies;
import enums.resources.StrategicResourceTypes;
import models.Building;
import models.units.Unit;

public class StrategicResource extends Resource {
    private Building building;
    private Technologies requiredTechnology;
    private StrategicResourceTypes type;
    private ArrayList<Building> dependentBuildings = new ArrayList<>();
    private ArrayList<Unit> dependentUnit = new ArrayList<>();

    public StrategicResource(StrategicResourceTypes type) {
        super(type);
        this.type = type;
        this.requiredTechnology = type.requiredTechnologyGetter();
    }

    public Building getBuilding() {
        return this.building;
    }

    public Technologies getRequiredTechnology() {
        return this.requiredTechnology;
    }

    public StrategicResourceTypes getType() {
        return this.type;
    }

    public ArrayList<Building> getDependentBuildings() {
        return this.dependentBuildings;
    }

    public ArrayList<Unit> getDependentUnit() {
        return this.dependentUnit;
    }

}
