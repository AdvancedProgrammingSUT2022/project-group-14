package models.units;

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

    public Unit(enums.units.Unit unitInfo, int x, int y, String civilization) {
        this.currentX = x; this.currentY = y;
        this.destinationX = -1; this.destinationY = -1;
        this.movementPoint = unitInfo.getMovement();
        this.name = unitInfo.getName();
        this.civilizationName = civilization;
        this.requiredGold = unitInfo.getCost();
        this.requiredStrategicResourceName = unitInfo.getRequiredResource().nameGetter();
        this.requiredTechnology = unitInfo.getRequiredTechnology().getName();
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
