package models.units;


import enums.units.UnitTypes;

public class Ranged extends CombatUnit{
    private final double rangedCombatStrength;
    private final boolean isSiegeUnit;
    private boolean unitIsReadyForRangedBattle;

    public Ranged(UnitTypes unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
        this.rangedCombatStrength = unitInfo.getRangedCombatStrength();
        this.isSiegeUnit = unitInfo.getCombatType().name().equals("SIEGE");
    }

    public boolean isSiegeUnit() {
        return isSiegeUnit;
    }

    public double getRangedCombatStrength() {
        return rangedCombatStrength;
    }

    public boolean isUnitIsReadyForRangedBattle() {
        return unitIsReadyForRangedBattle;
    }

    public void setCoordinatesToSetup(int x, int y) {
        this.setDestinationCoordinates(x, y);
        this.unitIsReadyForRangedBattle =  true;
    }
}
