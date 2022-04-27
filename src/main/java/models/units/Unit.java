package models.units;

import controllers.MapController;
import controllers.TileController;
import controllers.WorldController;
import enums.Technologies;
import models.Civilization;
import models.Tile;
import models.resources.StrategicResource;

import java.util.ArrayList;
import java.util.Map;

public class Unit {
    private int currentX, currentY;
    private int destinationX, destinationY;
    private int movementPoint;

    private String name;
    private String civilizationName;

    private int requiredGold;
    private String requiredStrategicResourceName;
    private String requiredTechnology;

    private double healthPoint;
    private boolean isSleep;

    public Unit(int currentX, int currentY, int movementPoint, String name, String civilization, int requiredGold,
                String requiredStrategicResourceName, String requiredTechnology, double healthPoint) {
        this.currentX = currentX;
        this.currentY = currentY;
        destinationX = -1;
        destinationY = -1;
        this.movementPoint = movementPoint;
        this.name = name;
        this.civilizationName = civilization;
        this.requiredGold = requiredGold;
        this.requiredStrategicResourceName = requiredStrategicResourceName;
        this.requiredTechnology = requiredTechnology;
        this.healthPoint = healthPoint;
    }

    public String getCivilizationName() {
        return civilizationName;
    }

    public String getName() {
        return name;
    }

    public int getCurrentX() {
        return currentX;
    }

    public int getCurrentY() {
        return currentY;
    }

    public int getDestinationX() {
        return destinationX;
    }

    public int getDestinationY() {
        return destinationY;
    }

    public int getMovementPoint() {
        return movementPoint;
    }

    public void updatePosition(int x, int y) {
        this.currentX = x;
        this.currentY = y;
        if (currentX == destinationX && currentY == destinationY) {
            destinationX = -1;
            destinationY = -1;
        }
    }

    public void putToSleep() {
        this.isSleep = true;
    }

    public void wakeUp() {
        this.isSleep = false;
    }

    public void setDestinationCoordinates(int x, int y) {
        this.destinationX = x;
        this.destinationY = y;
    }

    public void addHealthPoint(double amount) {
        this.healthPoint += amount;
    }

    public void cancelMission() {
        this.destinationX = -1;
        this.destinationY = -1;
    }

    public String getInfo() {
        return  "Name : " + name + '\n' +
                "current coordination : ( " + currentX +
                " , " + currentY + " )\n" +
                "movementPoint : " + movementPoint + "\n" +
                "civilizationName : " + civilizationName + '\n' +
                "healthPoint : " + healthPoint + "\n" +
                "isSleeping : " + isSleep + "\n";
    }
}
