package models.units;

public class CombatUnit extends Unit{
    private double defenseStrength;
    private double attackStrength;

    private boolean isAlert;
    private boolean isFortifiedTillHealed;

    public CombatUnit(int currentX, int currentY, int movementPoint, String name, String civilization, int requiredGold,
                      String requiredStrategicResourceName, String requiredTechnology, double healthPoint,
                      double defenseStrength, double attackStrength) {
        super(currentX, currentY, movementPoint, name, civilization, requiredGold, requiredStrategicResourceName,
                requiredTechnology, healthPoint);
        this.defenseStrength = defenseStrength;
        this.attackStrength = attackStrength;
        this.isAlert = false;
        this.isFortifiedTillHealed = false;
    }

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
