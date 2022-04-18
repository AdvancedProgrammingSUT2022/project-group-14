package models.resources;

import java.util.ArrayList;

import models.Building;
import models.units.Unit;

public class StrategicResource extends Resource{
    private Building building;
    private String technology;
    private ArrayList<Building> dependentBuildings = new ArrayList<>();
    private ArrayList<Unit> dependentUnit = new ArrayList<>();
}
