package models.units;

public class CombatUnit extends Unit{
    private double defenseStrength;
    private double attackStrength;

    private boolean isAlert;
    private boolean isFortifiedTillHealed;
    private boolean garrisoned;

    public CombatUnit(int currentX, int currentY, int movementPoint, String name, String civilization, int requiredGold,
                      String requiredStrategicResourceName, String requiredTechnology, double healthPoint,
                      double defenseStrength, double attackStrength) {
        super(currentX, currentY, movementPoint, name, civilization, requiredGold, requiredStrategicResourceName,
                requiredTechnology, healthPoint);
        this.defenseStrength = defenseStrength;
        this.attackStrength = attackStrength;
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
}
