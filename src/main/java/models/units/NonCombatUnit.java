package models.units;

public class NonCombatUnit extends Unit{
    private boolean isWorking;
    private int turnsLeftToWork;

    public int getTurnsLeftToWork() {
        return turnsLeftToWork;
    }

    public NonCombatUnit(enums.units.Unit unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
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
