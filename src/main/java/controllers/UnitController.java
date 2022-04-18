package controllers;

import models.*;
import models.units.CombatUnit;
import models.units.Settler;
import models.units.Unit;

public class UnitController {

    public static boolean combatUnitExistsInTile(Tile tile) {
        return false;

    }

    public static boolean nonCombatUnitExistsInTile(Tile tile) {
        return false;

    }

    public static boolean UnitBelongsToCivilization(Unit unit, Civilization civilization) {
        return false;

    }

    public static City UnitsCity(Unit unit) {
        return null;

    }

    public static void cancelMission(Unit unit) {
        // TODO
    }

    public static void sleepUnit(Unit unit) {

    }

    public static void alertUnit(Unit unit) {

    }

    public String fortifyUnit(Unit unit) {
        return null;
    }

    public String fortifyUnitUntilHealed(Unit unit) {
        return null;
    }

    public String setupRanged(Unit unit) {
        return null;
    }



    // returns if the city could be founded or not and do it if possible and returns
    // the error if not
    public static String foundCity(Settler settler) {
        return null;

    }

    public String wakeUp(Unit unit) {
        return null;
    }

    public String delete(Unit unit)
    {
        return null;
    }

    public void garrisonCity(CombatUnit combatUnit){

    }

}
