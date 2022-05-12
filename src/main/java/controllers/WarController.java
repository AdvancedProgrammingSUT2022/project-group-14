package controllers;

import enums.units.CombatType;
import models.City;
import models.Civilization;
import models.Tile;
import models.units.CombatUnit;
import models.units.Melee;
import models.units.Ranged;
import models.units.Unit;
import org.w3c.dom.ranges.Range;

import java.util.Map;

public class WarController {

    public static String combatUnitAttacksTile(int x, int y) {
        CombatUnit attackingUnit = WorldController.getSelectedCombatUnit();
        if (!attackingUnit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "unit is not under your control";
        } else if (!TileController.selectedTileIsValid(x, y)) {
            return "wanted tile is invalid";
        } else if (attackingUnit instanceof Ranged && ((Ranged) attackingUnit).isSiegeUnit() && !((Ranged) attackingUnit).isUnitIsReadyForRangedBattle()) {
            return "unit isn't ready for attack";
        } else {
            Tile attackingTile = MapController.getTileByCoordinates(x, y);
            attackingUnit.setAttackingTileX(x);
            attackingUnit.setAttackingTileY(y);
            attackingUnit.setDestinationCoordinates(x, y);
            if (attackingTile.getCity() != null) {
                attackCity(attackingUnit, attackingTile.getCity());
            } else {
                //TODO attacking unit vs unit
            }
        }

        return null;
    }

    public static void attackUnit(CombatUnit combatUnit, Unit unit) {
    }

    public static void attackCity(CombatUnit combatUnit, City city) {
        double cityDamage, unitDamage;
        Tile unitTile = MapController.getTileByCoordinates(combatUnit.getCurrentX(), combatUnit.getCurrentY());
        if (Math.abs(city.getCenterOfCity().getX() - combatUnit.getCurrentX()) +
                Math.abs(city.getCenterOfCity().getY() - combatUnit.getCurrentY()) <= combatUnit.getRange()) {
            cityDamage = city.getAttackStrength();
            if (combatUnit.getCombatType().hasDefenseBonuses())
                cityDamage -= combatUnit.getDefenseStrength() * (100 + unitTile.getMilitaryImpact()) / 100;
            unitDamage = combatUnit.getAttackStrength() + combatUnit.getCombatType().getBonusAgainstCities() - (city.getDefenseStrength() * (3 + city.getNumberOfGarrisonedUnit()) / 3);
            if (combatUnit instanceof Ranged)
                unitDamage -= combatUnit.getAttackStrength() - ((Ranged) combatUnit).getRangedCombatStrength();

            city.receiveDamage(unitDamage);
            combatUnit.receiveDamage(cityDamage);
            if (city.getHealthPoint() <= 0) {
                if (combatUnit instanceof Melee) {
                    //TODO conquer city
                } else {
                    city.setHealthPoint(10);
                }
            }
            if (combatUnit.getHealthPoint() <= 0) {
                Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
                String notification = "In turn " + WorldController.getWorld().getActualTurn() + " " + combatUnit.getName() + "died in order to conquer a city :(";
                currentCivilization.addNotification(notification);
                if (combatUnit instanceof Melee)
                    currentCivilization.removeMeleeUnit((Melee) combatUnit);
                else if (combatUnit instanceof Ranged)
                    currentCivilization.removeRangedUnit((Ranged) combatUnit);

            }
        }
    }
}
