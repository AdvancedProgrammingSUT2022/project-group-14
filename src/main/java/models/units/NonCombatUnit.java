package models.units;

public class NonCombatUnit extends Unit{
    private boolean isWorking;
    private int turnsLeftToWork;

    public NonCombatUnit(int currentX, int currentY, int movementPoint, String name, String civilization,
                         int requiredGold, String requiredStrategicResourceName, String requiredTechnology,
                         double healthPoint) {
        super(currentX, currentY, movementPoint, name, civilization, requiredGold, requiredStrategicResourceName,
                requiredTechnology, healthPoint);
    }

    public Boolean isWorking() {
        return this.isWorking;
    }

    public void putToWork(int turns) {
        this.isWorking = true;
        this.turnsLeftToWork = turns;
    }

    public void work() {
        this.turnsLeftToWork--;
        if (this.turnsLeftToWork <= 0)
            finishWork();
    }

    public void finishWork() {
        this.isWorking = false;
    }
}
