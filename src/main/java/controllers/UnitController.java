package controllers;

import models.*;
import models.units.*;
import org.w3c.dom.ranges.Range;

import java.util.ArrayList;
import java.util.Set;

public class UnitController {

    public static boolean combatUnitExistsInTile(Tile tile) {
        return false;
    }

    public static boolean nonCombatUnitExistsInTile(Tile tile) {
        return false;

    }

    public static boolean unitDoesNotExist(Unit unit) {
        ArrayList<Civilization> allCivilizations = World.getAllCivilizations();
        for (Civilization civilization : allCivilizations) {
            if (civilization.getAllUnits().contains(unit)) {
                return true;
            }
        }
        return false;
    }

    public static City UnitsCity(Unit unit) {
        return null;

    }

    public static String cancelMission(Unit unit) {
        if (unitDoesNotExist(unit)) {
            return "unit doesn't exist";
        } else if (!unit.getCivilization().getAllUnits().contains(unit)) {
            return "unit is not under your control";
        } else {
            unit.cancelMission();
        }
        return null;
    }

    public static String sleepUnit(Unit unit) {
        if (unitDoesNotExist(unit)) {
            return "unit doesn't exist";
        } else if (!unit.getCivilization().getAllUnits().contains(unit)) {
            return "unit is not under your control";
        } else {
            unit.putToSleep();
        }
        return null;
    }

    public static String alertUnit(Unit unit) {
        if (unitDoesNotExist(unit)) {
            return "unit doesn't exist";
        } else if (!unit.getCivilization().getAllUnits().contains(unit)) {
            return "unit is not under your control";
        } else if (unit instanceof CombatUnit) {
            ((CombatUnit) unit).alertUnit();
        } else {
            return "unit is not a combat unit";
        }
        return null;
    }

    public String fortifyUnit(Unit unit) {
        if (unitDoesNotExist(unit)) {
            return "unit doesn't exist";
        } else if (!unit.getCivilization().getAllUnits().contains(unit)) {
            return "unit is not under your control";
        } else if (unit instanceof CombatUnit) {
            ((CombatUnit) unit).healUnit(5);

        } else {
            return "unit is not a combat unit";
        }
        return null;
    }

    public String fortifyUnitUntilHealed(Unit unit) {
        if (unitDoesNotExist(unit)) {
            return "unit doesn't exist";
        } else if (!unit.getCivilization().getAllUnits().contains(unit)) {
            return "unit is not under your control";
        } else if (unit instanceof CombatUnit) {
            ((CombatUnit) unit).fortifyUnitTillHealed();
            unit.addHealthPoint(5);
        } else {
            return "unit is not a combat unit";
        }
        return null;
    }

    public String setupRanged(Unit unit, Tile tile) {
        if (unitDoesNotExist(unit)) {
            return "unit doesn't exist";
        } else if (!unit.getCivilization().getAllUnits().contains(unit)) {
            return "unit is not under your control";
        } else if (unit instanceof Ranged && ((Ranged) unit).isSiegeUnit()) {
            ((Ranged) unit).setTileToSetup(tile);
        } else {
            return "this unit doesn't have the ability to setup";
        }
        return null;
    }


    // returns if the city could be founded or not and do it if possible and returns
    // the error if not
    public static String foundCity(Settler settler) {
        return null;

    }

    public String wakeUp(Unit unit) {
        if (unitDoesNotExist(unit)) {
            return "unit doesn't exist";
        } else if (!unit.getCivilization().getAllUnits().contains(unit)) {
            return "unit is not under your control";
        } else {
            unit.wakeUp();
        }
        return null;
    }

    public String delete(Unit unit) {
        Civilization wantedCivil = unit.getCivilization();

        if (unit instanceof Ranged) {
            if (unit.getCivilization().getAllUnits().contains(unit)) {
                wantedCivil.removeRangedUnit((Ranged) unit);
            } else {
                return "unit is not under your control";
            }
        } else if (unit instanceof Melee) {
            if (unit.getCivilization().getAllUnits().contains(unit)) {
                wantedCivil.removeMeleeUnit((Melee) unit);
            } else {
                return "unit is not under your control";
            }
        } else if (unit instanceof Worker) {
            if (unit.getCivilization().getAllUnits().contains(unit)) {
                wantedCivil.removeWorker((Worker) unit);
            } else {
                return "unit is not under your control";
            }
        } else if (unit instanceof Settler) {
            if (unit.getCivilization().getAllUnits().contains(unit)) {
                wantedCivil.removeSettler((Settler) unit);
            } else {
                return "unit is not under your control";
            }
        } else {
            return "unit doesn't exist";
        }

        return null;
    }

    public void garrisonCity(CombatUnit combatUnit) {
        //TODO
    }

}
