package controllers;

import models.*;
import models.units.*;

public class UnitController {

    public static String setUnitDestinationCoordinates(Unit unit, int x, int y) {
        String reason;
        if (!unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "the unit is not under your control";
        } else if ((reason = MoveController.impossibleToMoveToTile(x, y, unit)) != null) {
            return reason;
        } else {
            unit.setDestinationCoordinates(x, y);
        }
        return null;
    }

    public static String cancelMission(Unit unit) {
        if (!unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else {
            unit.cancelMission();
        }
        return null;
    }

    public static String sleepUnit(Unit unit) {
        if (!unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else {
            unit.putToSleep();
        }
        return null;
    }

    public static String alertUnit(Unit unit) {
        if (!unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else if (unit instanceof CombatUnit) {
            ((CombatUnit) unit).alertUnit();
        } else {
            return "unit is not a combat unit";
        }
        return null;
    }

    public static String fortifyUnit(Unit unit) {
        if (!unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else if (unit instanceof CombatUnit) {
            ((CombatUnit) unit).healUnit(5);

        } else {
            return "unit is not a combat unit";
        }
        return null;
    }

    public static String fortifyUnitUntilHealed(Unit unit) {
        if (!unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else if (unit instanceof CombatUnit) {
            ((CombatUnit) unit).fortifyUnitTillHealed();
            unit.addHealthPoint(5);
        } else {
            return "unit is not a combat unit";
        }
        return null;
    }

    public static String setupRangedUnit(Unit unit, int x, int y) {
        if (!unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else if (unit instanceof Ranged && ((Ranged) unit).isSiegeUnit()) {
            ((Ranged) unit).setCoordinatesToSetup(x, y);
        } else {
            return "this unit doesn't have the ability to setup";
        }
        return null;
    }

    public static String wakeUp(Unit unit) {
        if (!unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else {
            unit.wakeUp();
        }
        return null;
    }

    public static String garrisonCity(CombatUnit combatUnit) {
        Tile currentTile = MapController.getTileByCoordinates(combatUnit.getCurrentX(), combatUnit.getCurrentY());
        if (currentTile.getCity() == null) {
            return "you should be in a city to garrison";
        } else {
            combatUnit.garrisonUnit();
        }
        return null;
    }

    public static String foundCity(Settler settler) {
        Tile currentTile = MapController.getTileByCoordinates(settler.getCurrentX(), settler.getCurrentY());
        if (currentTile.getCity() != null) {
            return "can not found a city over another city";
        } else if (!currentTile.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "can not found a city in enemy territory";
        } else {
            //TODO found a city
        }
        return null;
    }

    public static String delete(Unit unit) {
        Civilization wantedCivilization = WorldController.getWorld().getCivilizationByName(unit.getCivilizationName());

        if (unit instanceof Ranged) {
            if (unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
                wantedCivilization.removeRangedUnit((Ranged) unit);
            } else {
                return "unit is not under your control";
            }
        } else if (unit instanceof Melee) {
            if (unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
                wantedCivilization.removeMeleeUnit((Melee) unit);
            } else {
                return "unit is not under your control";
            }
        } else if (unit instanceof Worker) {
            if (unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
                wantedCivilization.removeWorker((Worker) unit);
            } else {
                return "unit is not under your control";
            }
        } else if (unit instanceof Settler) {
            if (unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
                wantedCivilization.removeSettler((Settler) unit);
            } else {
                return "unit is not under your control";
            }
        } else {
            return "unit doesn't exist";
        }
        return null;
    }
}
