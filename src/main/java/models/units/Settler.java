package models.units;

public class Settler extends NonCombatUnit{
    public Settler(int currentX, int currentY, int movementPoint, String name, String civilization, int requiredGold,
                   String requiredStrategicResourceName, String requiredTechnology, double healthPoint) {
        super(currentX, currentY, movementPoint, name, civilization, requiredGold, requiredStrategicResourceName,
                requiredTechnology, healthPoint);
    }
}
