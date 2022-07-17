package controllers;

import enums.units.UnitStates;
import models.City;
import models.Civilization;
import models.tiles.Tile;
import models.units.*;
import views.GamePlay;

public class WarController {

    public static String combatUnitAttacksTile(int x, int y) {
        CombatUnit attackingUnit = WorldController.getSelectedCombatUnit();
        if (attackingUnit instanceof Ranged && ((Ranged) attackingUnit).isSiegeUnit() && attackingUnit.getUnitState() != UnitStates.SETUP_RANGED) {
            return "siege unit isn't ready for attack";
        } else {
            Tile attackingTile = MapController.getTileByCoordinates(x, y);
            attackingUnit.setAttackingCoordination(x, y);
            attackingUnit.setDestinationCoordinates(x, y);
            if (attackingTile.getCity() != null) {
                attackCity(attackingUnit, attackingTile.getCity());
            } else {
                if (attackingTile.getCombatUnit() != null) {
                    attackCombatUnit(attackingUnit, attackingTile.getCombatUnit());
                } else if (attackingTile.getNonCombatUnit() != null) {
                    attackNonCombatUnit(attackingUnit, attackingTile.getNonCombatUnit());
                } else {
                    return "there is no unit or city in the destination";
                }
            }
        }
        return null;
    }

    public static void attackCombatUnit(CombatUnit attackingUnit, CombatUnit defendingUnit) {
        double defendingUnitAttackDamage, attackingUnitAttackDamage;
        Tile attackingUnitTile = MapController.getTileByCoordinates(attackingUnit.getCurrentX(), attackingUnit.getCurrentY());
        if (TileController.coordinatesAreInRange(attackingUnit.getCurrentX(), attackingUnit.getCurrentY(), defendingUnit.getCurrentX(), defendingUnit.getCurrentY(), attackingUnit.getRange())) {
            defendingUnitAttackDamage = defendingUnit.getAttackStrength();
            if (attackingUnit.getCombatType().hasDefenseBonuses())
                defendingUnitAttackDamage -= attackingUnit.getDefenseStrength() * (100 + attackingUnitTile.getMilitaryImpact()) / 100;
            attackingUnitAttackDamage = attackingUnit.getAttackStrength() - defendingUnit.getDefenseStrength();
            if (attackingUnit instanceof Ranged)
                attackingUnitAttackDamage -= attackingUnit.getAttackStrength() - ((Ranged) attackingUnit).getRangedCombatStrength();
            defendingUnitAttackDamage = Math.max(0, defendingUnitAttackDamage);
            attackingUnitAttackDamage = Math.max(0, attackingUnitAttackDamage);

            attackingUnit.receiveDamage(defendingUnitAttackDamage);
            defendingUnit.receiveDamage(attackingUnitAttackDamage);
            if (defendingUnit.getHealthPoint() <= 0) {
                Civilization defendingCivilization = WorldController.getWorld().getCivilizationByName(defendingUnit.getCivilizationName());
                Tile defendingUnitTile = MapController.getTileByCoordinates(defendingUnit.getCurrentX(), defendingUnit.getCurrentY());
                if (defendingUnit instanceof Melee) {
                    defendingCivilization.getMelees().remove((Melee) defendingUnit);
                    if (defendingUnitTile.getNonCombatUnit() != null)
                        attackNonCombatUnit(attackingUnit, defendingUnitTile.getNonCombatUnit());
                } else {
                    defendingCivilization.getRanges().remove((Ranged) defendingUnit);
                }
                defendingUnitTile.setCombatUnit(null);
                attackingUnit.setAttackingCoordination(-1, -1);
                if (attackingUnit instanceof Ranged)
                    attackingUnit.setDestinationCoordinates(-1, -1);
            } else if (attackingUnit.getHealthPoint() <= 0) {
                Civilization attackingCivilization = WorldController.getWorld().getCivilizationByName(attackingUnit.getCivilizationName());
                if (attackingUnit instanceof Melee) {
                    attackingCivilization.getMelees().remove((Melee) attackingUnit);
                    attackingUnitTile.setCombatUnit(null);
                } else {
                    attackingCivilization.getRanges().remove((Ranged) attackingUnit);
                    attackingUnitTile.setCombatUnit(null);
                }
            }
        }
    }

    public static void attackNonCombatUnit(CombatUnit attackingUnit, NonCombatUnit nonCombatUnit) {
        if (TileController.coordinatesAreInRange(attackingUnit.getCurrentX(), attackingUnit.getCurrentY(), nonCombatUnit.getCurrentX(), nonCombatUnit.getCurrentY(), attackingUnit.getRange())) {
            Civilization attackingCivilization = WorldController.getWorld().getCivilizationByName(attackingUnit.getCivilizationName());
            Civilization defendingCivilization = WorldController.getWorld().getCivilizationByName(nonCombatUnit.getCivilizationName());
            nonCombatUnit.setCivilizationName(attackingUnit.getCivilizationName());
            if (nonCombatUnit instanceof Worker) {
                defendingCivilization.getWorkers().remove((Worker) nonCombatUnit);
                attackingCivilization.getWorkers().add((Worker) nonCombatUnit);
            } else {
                defendingCivilization.getSettlers().remove((Settler) nonCombatUnit);
                attackingCivilization.getSettlers().add((Settler) nonCombatUnit);
            }
            attackingUnit.setAttackingCoordination(-1, -1);
        }
    }

    public static void attackCity(CombatUnit combatUnit, City city) {
        double cityAttackDamage, unitAttackDamage;
        Tile unitTile = MapController.getTileByCoordinates(combatUnit.getCurrentX(), combatUnit.getCurrentY());
        if (TileController.coordinatesAreInRange(unitTile.getX(), unitTile.getY(), city.getCenterOfCity().getX(), city.getCenterOfCity().getY(), combatUnit.getRange())) {
            cityAttackDamage = city.getAttackStrength();
            if (combatUnit.getCombatType().hasDefenseBonuses())
                cityAttackDamage -= combatUnit.getDefenseStrength() * (100 + unitTile.getMilitaryImpact()) / 100;
            unitAttackDamage = combatUnit.getAttackStrength() + combatUnit.getCombatType().getBonusAgainstCities() - (city.getDefenseStrength() * (3 + city.getNumberOfGarrisonedUnit()) / 3);
            if (combatUnit instanceof Ranged)
                unitAttackDamage -= combatUnit.getAttackStrength() - ((Ranged) combatUnit).getRangedCombatStrength();
            cityAttackDamage = Math.max(0, cityAttackDamage);
            unitAttackDamage = Math.max(0, unitAttackDamage);

            city.receiveDamage(unitAttackDamage);
            combatUnit.receiveDamage(cityAttackDamage);
            if (city.getHealthPoint() <= 0) {
                if (combatUnit instanceof Melee) {
                    if (city.getCenterOfCity().getCombatUnit() != null) {
                        if (city.getCenterOfCity().getCombatUnit() instanceof Melee)
                            WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName()).getMelees().remove((Melee) city.getCenterOfCity().getCombatUnit());
                        else
                            WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName()).getRanges().remove((Ranged) city.getCenterOfCity().getCombatUnit());
                    }
                    if (city.getCenterOfCity().getNonCombatUnit() != null) {
                        city.getCenterOfCity().getNonCombatUnit().setCivilizationName(combatUnit.getCivilizationName());
                    }
                    WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName()).removeCity(city);
                    combatUnit.setAttackingCoordination(-1, -1);
                    //GamePlay.conquerCity(city, combatUnit);
                } else {
                    city.setHealthPoint(5);
                }
            } else if (combatUnit.getHealthPoint() <= 0) {
                Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(combatUnit.getCivilizationName());
                currentCivilization.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " "
                        + combatUnit.getName() + " died in order to conquer a city :(");
                unitTile.setCombatUnit(null);
                if (combatUnit instanceof Melee)
                    currentCivilization.getMelees().remove((Melee) combatUnit);
                else if (combatUnit instanceof Ranged)
                    currentCivilization.getRanges().remove((Ranged) combatUnit);

            }
        }
    }
}
