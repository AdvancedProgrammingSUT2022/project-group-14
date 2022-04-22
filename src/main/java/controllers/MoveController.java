package controllers;

import models.Tile;
import models.units.Unit;

public class MoveController {

    public String setUnitDestinationTile(Unit unit, Tile tile) {
        String reason;
        if (!unit.getCivilization().getAllUnits().contains(unit)) {
            return "the unit is not under your control";
        } else if ((reason = impossibleToMoveToTile(tile)) != null) {
            return reason;
        } else {
            unit.setDestinationTile(tile);
        }
        return null;
    }

    public String impossibleToMoveToTile(Tile tile) {
        //TODO some kinds of tiles
        return null;

    }

    public void moveUnitToDestination(Unit unit, Tile tile) {

    }
}
