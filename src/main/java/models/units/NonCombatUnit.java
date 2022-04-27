package models.units;

public class NonCombatUnit extends Unit{
    private int turnsLeftToWork;

    public NonCombatUnit(int currentX, int currentY, int movementPoint, String name, String civilization,
                         int requiredGold, String requiredStrategicResourceName, String requiredTechnology,
                         double healthPoint) {
        super(currentX, currentY, movementPoint, name, civilization, requiredGold, requiredStrategicResourceName,
                requiredTechnology, healthPoint);
        this.turnsLeftToWork = 0;
    }
}
