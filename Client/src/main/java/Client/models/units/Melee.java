package Client.models.units;

import Client.enums.units.UnitTypes;

public class Melee extends CombatUnit {
    public Melee(UnitTypes unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
    }
}