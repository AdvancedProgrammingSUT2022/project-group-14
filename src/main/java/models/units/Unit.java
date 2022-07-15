package models.units;

import enums.units.CombatType;
import enums.units.UnitTypes;
import models.tiles.Coordination;

import java.util.Objects;

public class Unit {
    private Coordination currentCoordination;
    private Coordination destinationCoordination;
    private int movementPoint;

    private String name;
    private String civilizationName;

    private double healthPoint;
    private boolean isSleep;

    private final UnitTypes unitType;

    public Unit(UnitTypes unitInfo, int x, int y, String civilization) {
        this.currentCoordination = new Coordination(x, y);
        this.destinationCoordination = new Coordination(-1, -1);
        this.movementPoint = unitInfo.getMovement();
        this.name = unitInfo.getName();
        this.civilizationName = civilization;
        this.unitType = unitInfo;
        this.healthPoint = 10;
    }

    public String getCivilizationName() {
        return civilizationName;
    }

    public void setCivilizationName(String civilizationName) {
        this.civilizationName = civilizationName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentX() {
        return this.currentCoordination.getX();
    }

    public int getCurrentY() {
        return this.currentCoordination.getY();
    }

    public int getDestinationX() {
        return this.destinationCoordination.getX();
    }

    public int getDestinationY() {
        return this.destinationCoordination.getY();
    }

    public int getMovementPoint() {
        return movementPoint;
    }

    public void setMovementPoint(int movementPoint) {
        this.movementPoint = movementPoint;
    }

    public void updatePosition(int x, int y) {
        this.currentCoordination = new Coordination(x, y);
        if (currentCoordination.equals(destinationCoordination)) {
            destinationCoordination = new Coordination(-1, -1);
        }
    }

    public boolean isSleep() {
        return this.isSleep;
    }

    public void putToSleep() {
        this.isSleep = true;
    }

    public void wakeUp() {
        this.isSleep = false;
    }

    public void setDestinationCoordinates(int x, int y) {
        this.destinationCoordination = new Coordination(x, y);
    }

    public UnitTypes getUnitType() {
        return unitType;
    }

    public CombatType getCombatType() {
        return unitType.getCombatType();
    }

    public void addHealthPoint(double amount) {
        this.healthPoint += amount;
    }

    public void receiveDamage(double amount) {
        this.healthPoint -= amount;
    }

    public double getHealthPoint() {
        return healthPoint;
    }

    public void cancelMission() {
        this.destinationCoordination = new Coordination(-1, -1);
        if (this instanceof NonCombatUnit)
            ((NonCombatUnit) this).finishWork();
    }

    public Coordination getCurrentCoordination() {
        return currentCoordination;
    }

    public Coordination getDestinationCoordination() {
        return destinationCoordination;
    }

    public String getInfo() {
        int x = this.currentCoordination.getX()+1, y = this.currentCoordination.getY()+1,
                destX = this.destinationCoordination.getX()+1, destY = this.destinationCoordination.getY()+1;
        String output = "Name : " + name + '\n' +
                "Current coordination : ( " + x + " , " + y + " )\n" +
                "Total MovementPoints : " + movementPoint + "\n" +
                "Civilization's name : " + civilizationName + '\n' +
                "HealthPoint : " + healthPoint + "\n" +
                "IsSleeping : " + isSleep + "\n";
        if (!this.destinationCoordination.equals(new Coordination(-1 , -1))) {
            output += "IsMoving : true\nDestination coordinates : ( " + destX + " , " + destY + " )\n";
        } else {
            output += "IsMoving : false\n";
        }
        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Unit unit = (Unit) o;
        return movementPoint == unit.movementPoint && Double.compare(unit.healthPoint, healthPoint) == 0 && isSleep == unit.isSleep && currentCoordination.equals(unit.currentCoordination) && Objects.equals(destinationCoordination, unit.destinationCoordination) && name.equals(unit.name) && civilizationName.equals(unit.civilizationName) && unitType == unit.unitType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentCoordination, destinationCoordination, movementPoint, name, civilizationName, healthPoint, isSleep, unitType);
    }
}
