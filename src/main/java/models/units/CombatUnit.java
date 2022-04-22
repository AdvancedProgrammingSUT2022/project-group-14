package models.units;

import models.Civilization;
import models.Tile;
import models.resources.StrategicResource;

public class CombatUnit extends Unit{
    private double defenseStrength;
    private double attackStrength;

    private boolean isAlert;
    private boolean isFortifiedTillHealed;

    public CombatUnit(Tile currentTile, int movementPoint, String name, Civilization civilization, int requiredGold,
                      StrategicResource requiredStrategicResource, String requiredTechnology, double healthPoint,
                      double defenseStrength, double attackStrength) {
        super(currentTile, movementPoint, name, civilization, requiredGold, requiredStrategicResource,
                requiredTechnology, healthPoint);
        this.defenseStrength = defenseStrength;
        this.attackStrength = attackStrength;
        this.isAlert = false;
        this.isFortifiedTillHealed = false;
    }

    public void alertUnit() {
        this.isAlert = true;
    }

    public void fortifyUnitTillHealed() {
        this.isFortifiedTillHealed = true;
    }

    public void healUnit(double amount) {
        this.addHealthPoint(amount);
    }
}
