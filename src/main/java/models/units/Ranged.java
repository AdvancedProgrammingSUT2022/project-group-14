package models.units;

import models.Civilization;
import models.Tile;
import models.resources.StrategicResource;

import java.util.ArrayList;

public class Ranged extends CombatUnit{
    private boolean isSiegeUnit;
    private boolean unitIsReadyForRangedBattle;

    public Ranged(Tile currentTile, int movementPoint, String name, Civilization civilization, int requiredGold,
                  StrategicResource requiredStrategicResource, String requiredTechnology, double healthPoint, double defenseStrength, double attackStrength, boolean isSiegeUnit) {
        super(currentTile, movementPoint, name, civilization, requiredGold, requiredStrategicResource,
                requiredTechnology, healthPoint, defenseStrength, attackStrength);
        this.isSiegeUnit = isSiegeUnit;
    }

    public boolean isSiegeUnit() {
        return isSiegeUnit;
    }

    public void setTileToSetup(Tile tile) {
        this.setDestinationTile(tile);
        this.unitIsReadyForRangedBattle =  true;
    }
}
