package models.units;

public class CombatUnit extends Unit{
    private double defenseStrength;
    private double attackStrength;

    private boolean isAlert;
    private boolean isFortifiedTillHealed;
    private boolean garrisoned;

    public CombatUnit(enums.units.Unit unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
        this.defenseStrength = unitInfo.getCombatStrength();
        this.attackStrength = unitInfo.getCombatStrength();
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
}
