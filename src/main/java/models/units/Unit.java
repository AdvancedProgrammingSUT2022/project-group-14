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
