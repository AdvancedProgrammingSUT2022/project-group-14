package controllers;

import models.*;
import models.units.*;
import org.w3c.dom.ranges.Range;

import java.util.ArrayList;
import java.util.Set;

public class UnitController {

    public static boolean combatUnitExistsInTile(int x, int y) {
        return false;
    }

    public static boolean nonCombatUnitExistsInTile(int x, int y) {
        return false;

    }

    public static City UnitsCity(Unit unit) {
        return null;

    }

    public static String cancelMission(Unit unit, World world) {
        if (!unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else {
            unit.cancelMission();
        }
        return null;
    }

    public static String sleepUnit(Unit unit, World world) {
        if (!unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else {
            unit.putToSleep();
        }
        return null;
    }

    public static String alertUnit(Unit unit, World world) {
        if (!unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else if (unit instanceof CombatUnit) {
            ((CombatUnit) unit).alertUnit();
        } else {
            return "unit is not a combat unit";
        }
        return null;
    }

    public String fortifyUnit(Unit unit, World world) {
        if (!unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else if (unit instanceof CombatUnit) {
            ((CombatUnit) unit).healUnit(5);

        } else {
            return "unit is not a combat unit";
        }
        return null;
    }

    public String fortifyUnitUntilHealed(Unit unit, World world) {
        if (!unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else if (unit instanceof CombatUnit) {
            ((CombatUnit) unit).fortifyUnitTillHealed();
            unit.addHealthPoint(5);
        } else {
            return "unit is not a combat unit";
        }
        return null;
    }

    public String setupRanged(Unit unit, int x, int y, World world) {
        if (!unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else if (unit instanceof Ranged && ((Ranged) unit).isSiegeUnit()) {
            ((Ranged) unit).setCoordinatesToSetup(x, y);
        } else {
            return "this unit doesn't have the ability to setup";
        }
        return null;
    }


    // returns if the city could be founded or not and do it if possible and returns
    // the error if not
    public static String foundCity(Settler settler, World world) {
        return null;

    }

    public String wakeUp(Unit unit, World world) {
        if (!unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else {
            unit.wakeUp();
        }
        return null;
    }

    public String delete(Unit unit, World world) {
        Civilization wantedCivilization = world.getCivilizationByName(unit.getCivilizationName());

        if (unit instanceof Ranged) {
            if (unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
                wantedCivilization.removeRangedUnit((Ranged) unit);
            } else {
                return "unit is not under your control";
            }
        } else if (unit instanceof Melee) {
            if (unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
                wantedCivilization.removeMeleeUnit((Melee) unit);
            } else {
                return "unit is not under your control";
            }
        } else if (unit instanceof Worker) {
            if (unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
                wantedCivilization.removeWorker((Worker) unit);
            } else {
                return "unit is not under your control";
            }
        } else if (unit instanceof Settler) {
            if (unit.getCivilizationName().equals(world.getCurrentCivilizationName())) {
                wantedCivilization.removeSettler((Settler) unit);
            } else {
                return "unit is not under your control";
            }
        } else {
            return "unit doesn't exist";
        }

        return null;
    }

    public void garrisonCity(CombatUnit combatUnit, World world) {
        //TODO
    }

}
