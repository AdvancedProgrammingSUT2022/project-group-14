package models.units;

import models.Tile;

public class Ranged extends CombatUnit{
    private boolean isSiegeUnit;
    private boolean unitIsReadyForRangedBattle;

    public boolean isSiegeUnit() {
        return isSiegeUnit;
    }

    public void setTileToSetup(Tile tile) {
        this.setDestinationTile(tile);
        this.unitIsReadyForRangedBattle =  true;
    }
}
