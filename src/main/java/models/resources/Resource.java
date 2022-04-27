package models.resources;

import enums.Progresses;

public class Resource {
    private double food;
    private double production;
    private double gold;

    private Progresses requiredProgress;
    private boolean isActive;// what the hell is this?

    public Resource(double food, double production, double gold, Progresses requiredProgress) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.requiredProgress = requiredProgress;
    }
    
}
