package models.units;

import enums.units.CombatType;
import enums.units.UnitStates;
import enums.units.UnitTypes;
import models.tiles.Coordination;

import java.util.Objects;

public class Unit {
    private final String name;
    private String civilizationName;

    private Coordination currentCoordination;
    private Coordination destinationCoordination;
    private int movementPoint;

    private final UnitTypes unitType;
    private UnitStates unitState;
    private double healthPoint;

    public Unit(UnitTypes unitInfo, int x, int y, String civilization) {
        this.name = unitInfo.getName();
        this.civilizationName = civilization;
        this.currentCoordination = new Coordination(x, y);
        this.destinationCoordination = new Coordination(-1, -1);
        this.movementPoint = unitInfo.getMovement();
        this.unitType = unitInfo;
        this.unitState = UnitStates.WAKE;
        this.healthPoint = 10;
    }

    public String getName() {
        return this.name;
    }

    public String getCivilizationName() {
        return this.civilizationName;
    }

    public void setCivilizationName(String civilizationName) {
        this.civilizationName = civilizationName;
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

    public void updatePosition(int x, int y) {
        this.currentCoordination = new Coordination(x, y);
        if (currentCoordination.equals(destinationCoordination))
            this.destinationCoordination = new Coordination(-1, -1);
    }

    public void setDestinationCoordinates(int x, int y) {
        this.destinationCoordination = new Coordination(x, y);
    }

    public int getMovementPoint() {
        return this.movementPoint;
    }

    public void setMovementPoint(int movementPoint) {
        this.movementPoint = movementPoint;
    }

    public UnitTypes getUnitType() {
        return this.unitType;
    }

    public CombatType getCombatType() {
        return this.unitType.getCombatType();
    }

    public UnitStates getUnitState() {
        return this.unitState;
    }

    public void setUnitState(UnitStates state) {
        this.unitState = state;
    }

    public double getHealthPoint() {
        return this.healthPoint;
    }

    public void addHealthPoint(double amount) {
        this.healthPoint += amount;
    }

    public void receiveDamage(double amount) {
        this.healthPoint -= amount;
    }

    public void cancelMission() {
        this.destinationCoordination = new Coordination(-1, -1);
        if (this instanceof NonCombatUnit)
            this.unitState = UnitStates.WAKE;
    }

    public String getInfo() {
        int x = this.currentCoordination.getX() + 1, y = this.currentCoordination.getY() + 1,
                destX = this.destinationCoordination.getX() + 1, destY = this.destinationCoordination.getY() + 1;
        String output = "Name : " + name + '\n' +
                "Current coordination : ( " + x + " , " + y + " )\n" +
                "Total MovementPoints : " + movementPoint + "\n" +
                "Civilization's name : " + civilizationName + '\n' +
                "HealthPoint : " + healthPoint + "\n" +
                "State : " + unitState.getName() + "\n";
        if (!this.destinationCoordination.equals(new Coordination(-1, -1))) {
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
        return movementPoint == unit.movementPoint && Double.compare(unit.healthPoint, healthPoint) == 0 && unitState == unit.unitState && currentCoordination.equals(unit.currentCoordination) && Objects.equals(destinationCoordination, unit.destinationCoordination) && name.equals(unit.name) && civilizationName.equals(unit.civilizationName) && unitType == unit.unitType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentCoordination, destinationCoordination, movementPoint, name, civilizationName, healthPoint, unitState, unitType);
    }
}
