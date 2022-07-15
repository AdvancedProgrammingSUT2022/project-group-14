package models.units;

import enums.units.UnitTypes;

public class NonCombatUnit extends Unit{
    private boolean isWorking;
    private int turnsLeftToWork;

    public NonCombatUnit(UnitTypes unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
    }

    public int getTurnsLeftToWork() {
        return turnsLeftToWork;
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
