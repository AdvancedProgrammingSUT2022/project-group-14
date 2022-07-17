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
        if (!attackingUnit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else if (!TileController.selectedTileIsValid(x, y)) {
            return "wanted tile is invalid";
        } else if (attackingUnit instanceof Ranged && ((Ranged) attackingUnit).isSiegeUnit() && attackingUnit.getUnitState() != UnitStates.SETUP_RANGED) {
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
                } else return "there is no unit or city in the destination";
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
            attackingUnitAttackDamage = attackingUnit.getAttackStrength() + attackingUnit.getCombatType().getBonusAgainstCities() - defendingUnit.getDefenseStrength();
            if (attackingUnit instanceof Ranged)
                attackingUnitAttackDamage -= attackingUnit.getAttackStrength() - ((Ranged) attackingUnit).getRangedCombatStrength();
            defendingUnitAttackDamage = Math.max(0, defendingUnitAttackDamage);
            attackingUnitAttackDamage = Math.max(0, attackingUnitAttackDamage);

            attackingUnit.receiveDamage(defendingUnitAttackDamage);
            defendingUnit.receiveDamage(attackingUnitAttackDamage);
            if (defendingUnit.getHealthPoint() <= 0) {
                Civilization defendingCivilization = WorldController.getWorld().getCivilizationByName(defendingUnit.getCivilizationName());
                Tile defendingUnitTile = MapController.getTileByCoordinates(defendingUnit.getCurrentX(), defendingUnit.getCurrentY());
                if (defendingUnit instanceof Melee){
                    defendingCivilization.removeMeleeUnit((Melee) defendingUnit);
                    if (defendingUnitTile.getNonCombatUnit() != null) {
                        attackNonCombatUnit(attackingUnit, defendingUnitTile.getNonCombatUnit());
                    }
                } else {
                    defendingCivilization.removeRangedUnit((Ranged) defendingUnit);
                }
                defendingUnitTile.setCombatUnit(null);
                attackingUnit.setAttackingTileX(-1);
                attackingUnit.setAttackingTileY(-1);
                if (attackingUnit instanceof Ranged) {
                    attackingUnit.setDestinationCoordinates(-1, -1);
                }
            } else if (attackingUnit.getHealthPoint() <= 0) {
                Civilization attackingCivilization = WorldController.getWorld().getCivilizationByName(attackingUnit.getCivilizationName());
                if (attackingUnit instanceof Melee){
                    attackingCivilization.removeMeleeUnit((Melee) attackingUnit);
                    attackingUnitTile.setCombatUnit(null);
                } else {
                    attackingCivilization.removeRangedUnit((Ranged) attackingUnit);
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
                defendingCivilization.removeWorker((Worker) nonCombatUnit);
                attackingCivilization.addWorker((Worker) nonCombatUnit);
            } else {
                defendingCivilization.removeSettler((Settler) nonCombatUnit);
                attackingCivilization.addSettler((Settler) nonCombatUnit);
            }
            attackingUnit.setAttackingTileX(-1);
            attackingUnit.setAttackingTileY(-1);
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
                String notification = "In turn " + WorldController.getWorld().getActualTurn() + " " + combatUnit.getName() + " died in order to conquer a city :(";
                currentCivilization.addNotification(notification);
                unitTile.setCombatUnit(null);
                if (combatUnit instanceof Melee)
                    currentCivilization.getMelees().remove((Melee) combatUnit);
                else if (combatUnit instanceof Ranged)
                    currentCivilization.getRanges().remove((Ranged) combatUnit);

            }
        }
    }
}
