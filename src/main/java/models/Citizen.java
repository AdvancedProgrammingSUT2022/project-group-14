package models;

import models.tiles.Coordination;

public class Citizen {
    private final int id;
    private boolean isWorking;
    private final Coordination workingCoordination;

    public Citizen(int id) {
        this.id = id;
        this.workingCoordination = new Coordination(-1, -1);
    }

    public int getId() {
        return this.id;
    }

    public boolean isWorking() {
        return this.isWorking;
    }

    public void setIsWorking(boolean isWorking) {
        this.isWorking = isWorking;
    }

    public int getXOfWorkingTile() {
        return this.workingCoordination.getX();
    }

    public int getYOfWorkingTile() {
        return this.workingCoordination.getY();
    }

    public void setXOfWorkingTile(int xOfWorkingTile) {
        this.workingCoordination.setX(xOfWorkingTile);
    }

    public void setYOfWorkingTile(int yOfWorkingTile) {
        this.workingCoordination.setY(yOfWorkingTile);
    }
}
