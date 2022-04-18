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

    // this function returns null if moving unit to tile is possible and moves it if
    // it is else returns error in string
    public static String moveUnitToTile(Unit unit, Tile tile) {
        // TODO shadiiiiiiiiiddddddddddddd
        return null;

    }

    public static City UnitsCity(Unit unit) {
        return null;

    }

    // calls one of the functions attackUnit or attackCity if possible and returns the error if attack couldn't be done
    public static String combatUnitAttacksTile(CombatUnit combatUnit, Tile tile) {
        return "";


    }

    public void attackUnit(CombatUnit combatUnit, Unit unit) {
    }

    public void attackCity(CombatUnit combatUnit, City city) {

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
