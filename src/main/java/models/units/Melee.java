package models.units;

import enums.units.Unit;

public class Melee extends CombatUnit{
    public Melee(Unit unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
    }
}