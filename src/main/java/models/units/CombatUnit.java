package models.units;

import enums.units.UnitTypes;
import models.tiles.Coordination;

public class CombatUnit extends Unit{
    private final double defenseStrength;
    private final double attackStrength;
    private final int range;

    private boolean isAlert;
    private boolean isFortifiedTillHealed;
    private boolean garrisoned;

    private final Coordination attackingCoordination;

    public CombatUnit(UnitTypes unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
        this.defenseStrength = unitInfo.getCombatStrength();
        this.attackStrength = unitInfo.getCombatStrength();
        this.range = unitInfo.getRange();
        this.attackingCoordination = new Coordination(-1, -1);
    }

    public int getRange() {
        return range;
    }

    public void wakeUpFromAlert(){
        this.isAlert = false;
    }

    public void alertUnit() {
        isAlert = true;
    }

    public void fortifyUnitTillHealed() {
        isFortifiedTillHealed = true;
    }

    public void healUnit(double amount) {
        addHealthPoint(amount);
    }

    public void garrisonUnit() {
        garrisoned = true;
    }

    public void unGarrisonUnit() {
        garrisoned = false;
    }

    public double getDefenseStrength() {
        return defenseStrength;
    }

    public double getAttackStrength() {
        return attackStrength;
    }

    public boolean isAlert() {
        return isAlert;
    }

    public boolean isFortifiedTillHealed() {
        return isFortifiedTillHealed;
    }

    public boolean isGarrisoned() {
        return garrisoned;
    }

    public int getAttackingTileX() {
        return this.attackingCoordination.getX();
    }

    public void setAttackingTileX(int attackingTileX) {
        this.attackingCoordination.setX(attackingTileX);
    }

    public int getAttackingTileY() {
        return this.attackingCoordination.getY();
    }

    public void setAttackingTileY(int attackingTileY) {
        this.attackingCoordination.setY(attackingTileY);
    }

    public String getCombatInfo() {
        return "Name : " + this.getName() + "\n" +
                "Defense strength : " + defenseStrength + "\n" +
                "Attack strength : " + attackStrength + "\n" +
                "IsAlert : " + isAlert + "\n" +
                "IsFortified : " + isFortifiedTillHealed + "\n" +
                "IsGarrisoned : " + garrisoned + "\n";
    }
}
