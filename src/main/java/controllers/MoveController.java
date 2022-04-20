package controllers;

import models.Tile;
import models.units.Unit;

public class MoveController {


    public String moveUnitToTile(Unit unit, Tile tile) {
        String reason;
        if (!unit.getCivilization().getAllUnits().contains(unit)) {
            return "the unit is not under your control";
        } else if ((reason = impossibleToMoveToTile(tile)) != null) {
            return reason;
        } else {

        }
        return null;
    }

    public String impossibleToMoveToTile(Tile tile) {
        return null;
    }
}
