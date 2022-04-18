package models;

import enums.Progresses;
import enums.TileFeature;
import enums.TileType;
import models.resources.LuxuryResource;
import models.resources.Resource;
import models.resources.StrategicResource;
import models.units.CombatUnit;
import models.units.NonCombatUnit;

import java.util.ArrayList;

public class Tile {
    private int x;
    private int y;
    private TileType type;
    private TileFeature feature;
    private StrategicResource strategicResource;
    private LuxuryResource luxuryResource;
    private Resource bonusResource;
    private boolean[] isRiver = new boolean[6];
    private boolean hasRoad;
    private boolean hasRailroad;
    private double food;
    private double gold;
    private double production;
    private int movingPoint;
    private int militaryImpact;
    private Progresses progress;
    //if the tile was attacked pillaged becomes true and when it is repaired the tile it becomes false again
    private boolean pillaged = false;

    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private City city;
    private Civilization civilization = null;

    
}
