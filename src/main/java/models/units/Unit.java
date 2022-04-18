package models.units;

import enums.Technologies;
import models.Civilization;
import models.Tile;
import models.resources.StrategicResource;

import java.util.ArrayList;

public class Unit {
    private Tile currentTile;
    private Tile destinationTile;
    private String name;
    private int requiredGold;
    private int movementPoint;
    private Civilization civilization;
    private StrategicResource requiredStrategicResource;
    private String requiredTechnology;
    private double healthPoint;

    private boolean isSleep;

}
