package models.units;

public class CombatUnit extends Unit{
    private double defenseStrength;
    private double attackStrength;
    private int range;

    private boolean isAlert;
    private boolean isFortifiedTillHealed;
    private boolean garrisoned;

    private int attackingTileX, attackingTileY;

    public CombatUnit(enums.units.Unit unitInfo, int x, int y, String civilization) {
        super(unitInfo, x, y, civilization);
        this.defenseStrength = unitInfo.getCombatStrength();
        this.attackStrength = unitInfo.getCombatStrength();
        this.range = unitInfo.getRange();
    }

    public void setDefenseStrength(double defenseStrength) {
        this.defenseStrength = defenseStrength;
    }

    public void setAttackStrength(double attackStrength) {
        this.attackStrength = attackStrength;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public void wakeUpFromAlert(){
        this.isAlert = false;
    }

    public void alertUnit() {
        isAlert = true;
    }

    public void fortifyUnitTillHealed() {
        isFortifiedTillHealed = true;
    }

    public void healUnit(double amount) {
        addHealthPoint(amount);
    }

    public void garrisonUnit() {
        garrisoned = true;
    }

    public void unGarrisonUnit() {
        garrisoned = false;
    }

    public double getDefenseStrength() {
        return defenseStrength;
    }

    public double getAttackStrength() {
        return attackStrength;
    }

    public boolean isAlert() {
        return isAlert;
    }

    public boolean isFortifiedTillHealed() {
        return isFortifiedTillHealed;
    }

    public boolean isGarrisoned() {
        return garrisoned;
    }

    public int getAttackingTileX() {
        return attackingTileX;
    }

    public void setAttackingTileX(int attackingTileX) {
        this.attackingTileX = attackingTileX;
    }

    public int getAttackingTileY() {
        return attackingTileY;
    }

    public void setAttackingTileY(int attackingTileY) {
        this.attackingTileY = attackingTileY;
    }

    public String getCombatInfo() {
        return "Name : " + this.getName() + "\n" +
                "Defense strength : " + defenseStrength + "\n" +
                "Attack strength : " + attackStrength + "\n" +
                "IsAlert : " + isAlert + "\n" +
                "IsFortified : " + isFortifiedTillHealed + "\n" +
                "IsGarrisoned : " + garrisoned + "\n";
    }
}
