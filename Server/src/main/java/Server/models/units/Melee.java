package Server.models.units;

import Server.enums.units.UnitTypes;

public class Melee extends CombatUnit{
    public Melee(UnitTypes unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
    }
}