package models.units;

import models.Civilization;
import models.Tile;
import models.resources.StrategicResource;

public class Settler extends NonCombatUnit{
    public Settler(Tile currentTile, int movementPoint, String name, Civilization civilization, int requiredGold,
                  StrategicResource requiredStrategicResource, String requiredTechnology, double healthPoint) {
        super(currentTile, movementPoint, name, civilization, requiredGold, requiredStrategicResource,
                requiredTechnology, healthPoint);
    }
}
