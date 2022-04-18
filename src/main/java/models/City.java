package models;

import java.util.ArrayList;

import models.units.Unit;

public class City {
    private Tile centerOfCity;

    private double food;
    private double gold;
    private double production;
    private double happiness;
    private int citizens;

    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Tile> territory = new ArrayList<>();


    private Unit currentUnit;
    private Building currentBuilding;
    private int countdownUnit;

    private double defenseStrength;
    private double attackStrength;
    private double healthPoint;

    private boolean isColonised = false;

    private String name;


}
