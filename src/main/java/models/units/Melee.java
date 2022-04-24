package models.units;

public class Melee extends CombatUnit{
    public Melee(int currentX, int currentY, int movementPoint, String name, String civilization, int requiredGold,
                 String requiredStrategicResourceName, String requiredTechnology, double healthPoint,
                 double defenseStrength, double attackStrength) {
        super(currentX, currentY, movementPoint, name, civilization, requiredGold, requiredStrategicResourceName,
                requiredTechnology, healthPoint, defenseStrength, attackStrength);
    }
}