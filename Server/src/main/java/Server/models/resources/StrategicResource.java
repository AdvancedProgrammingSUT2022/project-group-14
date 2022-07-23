package Server.models.resources;

import java.util.ArrayList;

import Server.enums.Technologies;
import Server.enums.resources.StrategicResourceTypes;
import Server.models.Building;
import Server.models.units.Unit;

public class StrategicResource extends Resource {
    private final Technologies requiredTechnology;
    private final ArrayList<Building> dependentBuildings = new ArrayList<>();
    private final ArrayList<Unit> dependentUnit = new ArrayList<>();

    public StrategicResource(StrategicResourceTypes type) {
        super(type);
        this.requiredTechnology = type.getRequiredTechnology();
    }

    public Technologies getRequiredTechnology() {
        return this.requiredTechnology;
    }

    public ArrayList<Building> getDependentBuildings() {
        return this.dependentBuildings;
    }

    public ArrayList<Unit> getDependentUnit() {
        return this.dependentUnit;
    }

}
