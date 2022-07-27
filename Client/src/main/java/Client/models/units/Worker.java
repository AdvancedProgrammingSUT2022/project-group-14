package Client.models.units;


import Client.enums.units.UnitTypes;

public class Worker extends NonCombatUnit {
    public Worker(UnitTypes unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
    }
}
