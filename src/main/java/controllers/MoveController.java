package controllers;

import models.Tile;
import models.World;
import models.units.Unit;

import java.util.ArrayList;

public class MoveController {

    public String setUnitDestinationTile(Unit unit, int x, int y, World world) {
        String reason;
        if (!unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
            return "the unit is not under your control";
        } else if ((reason = impossibleToMoveToTile(x, y)) != null) {
            return reason;
        } else {
            unit.setDestinationCoordinates(x, y);
        }
        return null;
    }

    public String impossibleToMoveToTile(int x, int y) {
        if (true) {
            return "chosen tile is in the enemy territory";
        } else if (true) {
            return "can not move to those kind of tiles";
        }
        return null;
    }

    public void moveUnitToDestination(Unit unit, World world) {
//       ArrayList<Tile> openTiles = new ArrayList<>();
//       ArrayList<Tile> closedTiles = new ArrayList<>();
//       openTiles.add(unit.getCurrentT());
//
//       while (openTiles.size() != 0) {
//           Tile current = getBestTileToMove();
//           if (current == unit.getDestinationTile()) {
//               break;
//           }
//           openTiles.remove(current);
//           closedTiles.add(current);
//       }
    }

    public Tile getBestTileToMove() {
        return null;
    }
}
