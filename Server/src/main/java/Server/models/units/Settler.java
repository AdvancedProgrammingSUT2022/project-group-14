package Server.models.units;

import Server.enums.units.UnitTypes;

public class Settler extends NonCombatUnit{
    public Settler(UnitTypes unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
    }
}
