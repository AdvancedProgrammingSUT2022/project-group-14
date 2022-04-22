package models.units;

import enums.Technologies;
import models.Civilization;
import models.Tile;
import models.resources.StrategicResource;

import java.util.ArrayList;

public class Unit {
    private Tile currentTile;
    private Tile destinationTile;
    private int movementPoint;

    private String name;
    private Civilization civilization;

    private int requiredGold;
    private StrategicResource requiredStrategicResource;
    private String requiredTechnology;

    private double healthPoint;
    private boolean isSleep;

    public Unit(Tile currentTile, int movementPoint, String name, Civilization civilization, int requiredGold,
                StrategicResource requiredStrategicResource, String requiredTechnology, double healthPoint) {
        this.currentTile = currentTile;
        this.movementPoint = movementPoint;
        this.name = name;
        this.civilization = civilization;
        this.requiredGold = requiredGold;
        this.requiredStrategicResource = requiredStrategicResource;
        this.requiredTechnology = requiredTechnology;
        this.healthPoint = healthPoint;
    }

    public Civilization getCivilization() {
        return civilization;
    }

    public void updatePosition(Tile tile) {
        this.currentTile = tile;
    }

    public void putToSleep() {
        this.isSleep = true;
    }

    public void wakeUp() {
        this.isSleep = false;
    }

    public void setDestinationTile(Tile tile) {
        this.destinationTile = tile;
    }

    public void addHealthPoint(double amount) {
        this.healthPoint += amount;
    }

    public void cancelMission() {
        this.destinationTile = null;
    }


}
