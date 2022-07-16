package models.units;

import enums.units.UnitTypes;
import models.tiles.Coordination;

public class CombatUnit extends Unit {
    private final double defenseStrength;
    private final double attackStrength;
    private final int range;
    private Coordination attackingCoordination;

    public CombatUnit(UnitTypes unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
        this.defenseStrength = unitInfo.getCombatStrength();
        this.attackStrength = unitInfo.getCombatStrength();
        this.range = unitInfo.getRange();
        this.attackingCoordination = new Coordination(-1, -1);
    }

    public int getRange() {
        return this.range;
    }

    public void healUnit(double amount) {
        this.addHealthPoint(amount);
    }

    public double getDefenseStrength() {
        return this.defenseStrength;
    }

    public double getAttackStrength() {
        return this.attackStrength;
    }

    public int getAttackingTileX() {
        return this.attackingCoordination.getX();
    }

    public int getAttackingTileY() {
        return this.attackingCoordination.getY();
    }

    public void setAttackingCoordination(int x, int y) {
        this.attackingCoordination = new Coordination(x, y);
    }

    public String getCombatInfo() {
        return "Name : " + this.getName() + "\n" +
                "Defense strength : " + defenseStrength + "\n" +
                "Attack strength : " + attackStrength + "\n" +
                "State : " + getUnitState().getName();
    }
}
