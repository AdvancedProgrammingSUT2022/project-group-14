package models;

import enums.Technologies;

import java.util.ArrayList;

public class Building {
    String name;
    int cost;
    int maintenance;
    Technologies requiredTechnology;
    ArrayList<String> requiredBuildings;
}
