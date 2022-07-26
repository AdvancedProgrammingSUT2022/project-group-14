package Server.controllers;

import Server.enums.QueryResponses;
import Server.enums.units.UnitStates;
import Server.models.City;
import Server.models.Civilization;
import Server.models.network.Response;
import Server.models.tiles.Tile;
import Server.models.units.*;
import com.google.gson.Gson;

import java.util.HashMap;

public class WarController {

    public static void updateUnitsAttack(Unit unit) {
        if (unit instanceof CombatUnit && ((CombatUnit) unit).getAttackingTileX() != -1) {
            combatUnitAttacksTile(((CombatUnit) unit).getAttackingTileX(), ((CombatUnit) unit).getAttackingTileY(), (CombatUnit) unit);
        }
    }

    public static String combatUnitAttacksTile(int x, int y, CombatUnit attackingUnit) {
        if (attackingUnit instanceof Ranged && ((Ranged) attackingUnit).isSiegeUnit() && attackingUnit.getUnitState() != UnitStates.SETUP_RANGED) {
            return "siege unit isn't ready for attack";
        } else {
            Tile attackingTile = MapController.getTileByCoordinates(x, y);
            if (attackingTile.getCity() != null) {
                if (attackingTile.getCity().getCivilizationName().equals(attackingUnit.getCivilizationName()))
                    return "you have your own city in this tile";
                else {
                    Civilization unitCivilization = WorldController.getWorld().getCivilizationByName(attackingUnit.getCivilizationName());
                    if (unitCivilization.getEnemies().contains(attackingTile.getCity().getCivilizationName())) {
                        attackingUnit.setAttackingCoordination(x, y);
                        attackingUnit.setDestinationCoordinates(x, y);
                        attackCity(attackingUnit, attackingTile.getCity());
                    } else {
                        ServerUpdateController.sendUpdate(attackingUnit.getCivilizationName(), new Response(QueryResponses.CHOOSE_WAR_OPTIONS, new HashMap<>(){{
                            put("enemyName", new Gson().toJson(attackingTile.getCity().getCivilizationName()));
                        }}));
                    }
                }
            } else {
                if (attackingTile.getCombatUnit() != null) {
                    if (attackingTile.getCombatUnit().getCivilizationName().equals(attackingUnit.getCivilizationName()))
                        return "there is already one of your units in this tile";
                    else {
                        Civilization unitCivilization = WorldController.getWorld().getCivilizationByName(attackingUnit.getCivilizationName());
                        if (unitCivilization.getEnemies().contains(attackingTile.getCombatUnit().getCivilizationName())) {
                            attackingUnit.setAttackingCoordination(x, y);
                            attackingUnit.setDestinationCoordinates(x, y);
                            attackCombatUnit(attackingUnit, attackingTile.getCombatUnit());
                        } else {
                            ServerUpdateController.sendUpdate(attackingUnit.getCivilizationName(), new Response(QueryResponses.CHOOSE_WAR_OPTIONS, new HashMap<>(){{
                                put("enemyName", new Gson().toJson(attackingTile.getCombatUnit().getCivilizationName()));
                            }}));
                        }

                    }
                } else if (attackingTile.getNonCombatUnit() != null) {
                    if (attackingTile.getNonCombatUnit().getCivilizationName().equals(attackingUnit.getCivilizationName()))
                        return "there is already one of your units in this tile";
                    else {
                        Civilization unitCivilization = WorldController.getWorld().getCivilizationByName(attackingUnit.getCivilizationName());
                        if (unitCivilization.getEnemies().contains(attackingTile.getNonCombatUnit().getCivilizationName())) {
                            attackingUnit.setAttackingCoordination(x, y);
                            attackingUnit.setDestinationCoordinates(x, y);
                            attackNonCombatUnit(attackingUnit, attackingTile.getNonCombatUnit());
                        } else {
                            ServerUpdateController.sendUpdate(attackingUnit.getCivilizationName(), new Response(QueryResponses.CHOOSE_WAR_OPTIONS, new HashMap<>(){{
                                put("enemyName", new Gson().toJson(attackingTile.getNonCombatUnit().getCivilizationName()));
                            }}));
                        }

                    }
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
        Tile defendingUnitTile = MapController.getTileByCoordinates(defendingUnit.getCurrentX(), defendingUnit.getCurrentY());
        if (TileController.coordinatesAreInRange(attackingUnit.getCurrentX(), attackingUnit.getCurrentY(), defendingUnit.getCurrentX(), defendingUnit.getCurrentY(), attackingUnit.getRange())) {
            defendingUnitAttackDamage = defendingUnit.getAttackStrength();
            if (attackingUnit.getCombatType().hasDefenseBonuses())
                defendingUnitAttackDamage -= attackingUnit.getDefenseStrength() / 2;
            attackingUnitAttackDamage = attackingUnit.getAttackStrength();
            if (defendingUnit.getCombatType().hasDefenseBonuses())
                attackingUnitAttackDamage -= defendingUnit.getDefenseStrength() * (100 + defendingUnitTile.getMilitaryImpact()) / 100;
            if (attackingUnit instanceof Ranged)
                attackingUnitAttackDamage -= attackingUnit.getAttackStrength() - ((Ranged) attackingUnit).getRangedCombatStrength();
            defendingUnitAttackDamage = Math.max(0, defendingUnitAttackDamage);
            attackingUnitAttackDamage = Math.max(0, attackingUnitAttackDamage);

            attackingUnit.receiveDamage(defendingUnitAttackDamage);
            defendingUnit.receiveDamage(attackingUnitAttackDamage);
            System.out.println("your combat unit received " + defendingUnitAttackDamage + "and your enemy received " + attackingUnitAttackDamage + "damage");
            if (defendingUnit.getHealthPoint() <= 0) {
                System.out.println("you destroyed a combat unit");
                CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " Your combat unit died :(", defendingUnit.getCivilizationName());
                CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " You destroyed a combat unit :)", attackingUnit.getCivilizationName());
                Civilization defendingCivilization = WorldController.getWorld().getCivilizationByName(defendingUnit.getCivilizationName());
                if (defendingUnit instanceof Melee) {
                    defendingCivilization.getMelees().remove((Melee) defendingUnit);
                } else {
                    defendingCivilization.getRanges().remove((Ranged) defendingUnit);
                }
                if (defendingUnitTile.getNonCombatUnit() != null)
                    attackNonCombatUnit(attackingUnit, defendingUnitTile.getNonCombatUnit());
                defendingUnitTile.setCombatUnit(null);
                attackingUnit.setAttackingCoordination(-1, -1);
                if (attackingUnit instanceof Ranged)
                    attackingUnit.setDestinationCoordinates(-1, -1);
                else MoveController.moveUnitToDestination(attackingUnit);
            } else if (attackingUnit.getHealthPoint() <= 0) {
                System.out.println("your combat unit died");
                CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " Your combat unit died :(", attackingUnit.getCivilizationName());
                CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " You destroyed a combat unit :)", defendingUnit.getCivilizationName());
                WorldController.setSelectedCombatUnit(null);
                Civilization attackingCivilization = WorldController.getWorld().getCivilizationByName(attackingUnit.getCivilizationName());
                if (attackingUnit instanceof Melee) {
                    attackingCivilization.getMelees().remove((Melee) attackingUnit);
                    attackingUnitTile.setCombatUnit(null);
                } else {
                    attackingCivilization.getRanges().remove((Ranged) attackingUnit);
                    attackingUnitTile.setCombatUnit(null);
                }
            }
        } else MoveController.moveUnitToDestination(attackingUnit);
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
            CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " You've captured a nonCombat unit :)", attackingUnit.getCivilizationName());
            CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " You've lost a nonCombat unit :(", nonCombatUnit.getCivilizationName());
            System.out.println("you got a non combat unit");
        }
        MoveController.moveUnitToDestination(attackingUnit);
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
            unitAttackDamage = 10000;
            city.receiveDamage(unitAttackDamage);
            combatUnit.receiveDamage(cityAttackDamage);
            if (city.getHealthPoint() <= 0) {
                System.out.println("city conquered");
                if (combatUnit instanceof Melee) {
                    if (city.getCenterOfCity().getCombatUnit() != null) {
                        if (city.getCenterOfCity().getCombatUnit() instanceof Melee)
                            WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName()).getMelees().remove((Melee) city.getCenterOfCity().getCombatUnit());
                        else
                            WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName()).getRanges().remove((Ranged) city.getCenterOfCity().getCombatUnit());
                        city.getCenterOfCity().setCombatUnit(null);
                    }
                    if (city.getCenterOfCity().getNonCombatUnit() != null) {
                        if (city.getCenterOfCity().getNonCombatUnit() instanceof Worker) {
                            WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName()).getWorkers().remove((Worker) city.getCenterOfCity().getNonCombatUnit());
                            WorldController.getWorld().getCivilizationByName(combatUnit.getCivilizationName()).getWorkers().add((Worker) city.getCenterOfCity().getNonCombatUnit());
                        } else {
                            WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName()).getSettlers().remove((Settler) city.getCenterOfCity().getNonCombatUnit());
                            WorldController.getWorld().getCivilizationByName(combatUnit.getCivilizationName()).getSettlers().add((Settler) city.getCenterOfCity().getNonCombatUnit());
                        }
                        city.getCenterOfCity().getNonCombatUnit().setCivilizationName(combatUnit.getCivilizationName());

                    }
                    WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName()).removeCity(city);
                    combatUnit.setAttackingCoordination(-1, -1);
                    chooseCityOptions(city, combatUnit);
                    MoveController.moveUnitToDestination(combatUnit);
                } else {
                    city.setHealthPoint(5);
                }
            } else if (combatUnit.getHealthPoint() <= 0) {
                WorldController.setSelectedCombatUnit(null);
                Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(combatUnit.getCivilizationName());
                currentCivilization.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " "
                        + combatUnit.getName() + " died in order to conquer a city :(");
                unitTile.setCombatUnit(null);
                if (combatUnit instanceof Melee)
                    currentCivilization.getMelees().remove((Melee) combatUnit);
                else if (combatUnit instanceof Ranged)
                    currentCivilization.getRanges().remove((Ranged) combatUnit);

            }
        } else MoveController.moveUnitToDestination(combatUnit);
    }

    public static void chooseCityOptions(City city, CombatUnit combatUnit) {
        if (WorldController.getWorld() == null)
            return;
        if (WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName()) == null ||
                WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName()).getFirstCapital() == city) {
            CivilizationController.addNotification(
                    "In turn " + WorldController.getWorld().getActualTurn()
                            + " Congrats! you attached the first capital of the enemy to your civilization",
                    combatUnit.getCivilizationName());
            CityController.conquerCity(city, combatUnit);
        } else {
            ServerUpdateController.sendUpdate(combatUnit.getCivilizationName(), new Response(QueryResponses.CHOOSE_CITY_OPTIONS, new HashMap<>(){{
                put("city", new Gson().toJson(city));
                put("combatUnit", new Gson().toJson(combatUnit));
            }}));
        }
    }

    public static void declareWar(String enemyName) {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        currentCivilization.addEnemy(enemyName);
        WorldController.getWorld().getCivilizationByName(enemyName).addEnemy(currentCivilization.getName());
    }

    public static void makePeace(String name) {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        currentCivilization.removeEnemy(name);
        WorldController.getWorld().getCivilizationByName(name).removeEnemy(currentCivilization.getName());
    }
}
