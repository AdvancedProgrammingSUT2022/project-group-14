package Client.models.units;


import Client.enums.units.UnitStates;
import Client.enums.units.UnitTypes;

public class Ranged extends CombatUnit {
    private final double rangedCombatStrength;
    private final boolean isSiegeUnit;

    public Ranged(UnitTypes unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
        this.rangedCombatStrength = unitInfo.getRangedCombatStrength();
        this.isSiegeUnit = unitInfo.getCombatType().name().equals("SIEGE");
    }

    public boolean isSiegeUnit() {
        return this.isSiegeUnit;
    }

    public double getRangedCombatStrength() {
        return this.rangedCombatStrength;
    }

    public void setCoordinatesToSetup(int x, int y) {
        this.setDestinationCoordinates(x, y);
        this.setUnitState(UnitStates.SETUP_RANGED);
    }
}
