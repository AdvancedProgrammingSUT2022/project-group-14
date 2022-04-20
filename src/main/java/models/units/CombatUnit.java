package models.units;

public class CombatUnit extends Unit{
    private double defenseStrength;
    private double attackStrength;

    private boolean isAlert;
    private boolean isFortifiedTillHealed;

    public void alertUnit() {
        this.isAlert = true;
    }

    public void fortifyUnitTillHealed() {
        this.isFortifiedTillHealed = true;
    }

    public void healUnit(double amount) {
        this.addHealthPoint(amount);
    }
}
