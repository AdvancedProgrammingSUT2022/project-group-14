package controllers;

import models.Tile;
import models.units.Unit;

import java.util.ArrayList;

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
        if (true) {
            return "chosen tile is in the enemy territory";
        } else if (true) {
            return "can not move to those kind of tiles";
        }
        return null;
    }

    public void moveUnitToDestination(Unit unit, Tile tile) {
        //TODO if their x's are different change their x else change their y if possible

    }
}
