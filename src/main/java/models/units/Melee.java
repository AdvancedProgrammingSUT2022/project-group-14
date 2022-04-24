package models.units;

import models.Civilization;
import models.Tile;
import models.resources.StrategicResource;

public class Melee extends CombatUnit{
    public Melee(Tile currentTile, int movementPoint, String name, Civilization civilization, int requiredGold,
                 StrategicResource requiredStrategicResource, String requiredTechnology, double healthPoint, double defenseStrength, double attackStrength) {
        super(currentTile, movementPoint, name, civilization, requiredGold, requiredStrategicResource,
                requiredTechnology, healthPoint, defenseStrength, attackStrength);
    }
}