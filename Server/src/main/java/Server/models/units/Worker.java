package Server.models.units;

import Server.enums.units.UnitTypes;

public class Worker extends NonCombatUnit {
    public Worker(UnitTypes unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
    }
}
