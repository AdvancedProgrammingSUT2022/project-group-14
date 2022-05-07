package models;

public class Citizen {
    private int id;
    private boolean isWorking = false;
    private int xOfWorkingTile = -1;
    private int YOfWorkingTile = -1;

    public Citizen(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean isWorking() {
        return isWorking;
    }

    public int getXOfWorkingTile() {
        return xOfWorkingTile;
    }

    public int getYOfWorkingTile() {
        return YOfWorkingTile;
    }

    public void setIsWorking(boolean isWorking) {
        isWorking = isWorking;
    }

    public void setXOfWorkingTile(int xOfWorkingTile) {
        this.xOfWorkingTile = xOfWorkingTile;
    }

    public void setYOfWorkingTile(int YOfWorkingTile) {
        this.YOfWorkingTile = YOfWorkingTile;
    }
}
