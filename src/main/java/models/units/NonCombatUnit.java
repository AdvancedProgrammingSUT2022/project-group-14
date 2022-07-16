package models.units;

import enums.units.UnitStates;
import enums.units.UnitTypes;

public class NonCombatUnit extends Unit{
    private int turnsLeftToWork;

    public NonCombatUnit(UnitTypes unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
    }

    public void putToWork(int turns) {
        this.setUnitState(UnitStates.WORKING);
        this.turnsLeftToWork = turns;
    }

    public void doWork() {
        this.turnsLeftToWork--;
        if (this.turnsLeftToWork <= 0)
            this.setUnitState(UnitStates.WAKE);
    }

    public int getTurnsLeftToWork() {
        return this.turnsLeftToWork;
    }

}
