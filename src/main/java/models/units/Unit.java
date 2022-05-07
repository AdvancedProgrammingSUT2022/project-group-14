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
        if (unitInfo.getRequiredResource() == null){
            this.requiredStrategicResourceName = null;
        } else {
            this.requiredStrategicResourceName = unitInfo.getRequiredResource().nameGetter();
        }
        if (unitInfo.getRequiredTechnology() == null){
            this.requiredTechnology = null;
        } else {
            this.requiredTechnology = unitInfo.getRequiredTechnology().getName();
        }
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
        int x = currentX+1, y = currentY+1, destX = destinationX+1, destY = destinationY+1;
        String output = "Name : " + name + '\n' +
                "Current coordination : ( " + x + " , " + y + " )\n" +
                "Total MovementPoints : " + movementPoint + "\n" +
                "Civilization's name : " + civilizationName + '\n' +
                "HealthPoint : " + healthPoint + "\n" +
                "IsSleeping : " + isSleep + "\n";
        if (destinationX != -1 && destinationY != -1) {
            output += "IsMoving : true\nDestination coordinates : ( " + destX + " , " + destY + " )\n";
        } else {
            output += "IsMoving : false\n";
        }
        return output;
    }
}
