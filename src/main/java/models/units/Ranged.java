package models.units;


public class Ranged extends CombatUnit{
    private boolean isSiegeUnit;
    private boolean unitIsReadyForRangedBattle;

    public Ranged(int currentX, int currentY, int movementPoint, String name, String civilization, int requiredGold,
                  String requiredStrategicResourceName, String requiredTechnology, double healthPoint,
                  double defenseStrength, double attackStrength, boolean isSiegeUnit) {
        super(currentX, currentY, movementPoint, name, civilization, requiredGold, requiredStrategicResourceName,
                requiredTechnology, healthPoint, defenseStrength, attackStrength);
        this.isSiegeUnit = isSiegeUnit;
    }

    public boolean isSiegeUnit() {
        return isSiegeUnit;
    }

    public void setCoordinatesToSetup(int x, int y) {
        this.setDestinationCoordinates(x, y);
        this.unitIsReadyForRangedBattle =  true;
    }
}
