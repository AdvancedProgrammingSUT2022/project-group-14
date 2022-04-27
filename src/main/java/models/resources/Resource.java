package models.resources;

import enums.Progresses;
import enums.resources.StrategicResourceTypes;

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

    public Resource(StrategicResourceTypes type) {
        Resource resource = StrategicResource.typesMapGetter().get(type);
        this.food = resource.food;
        this.production = resource.production;
        this.gold = resource.gold;
        this.requiredProgress = resource.requiredProgress;
    }


    public double getFood() {
        return this.food;
    }

    public double getProduction() {
        return this.production;
    }

    public double getGold() {
        return this.gold;
    }

    public Progresses getRequiredProgress() {
        return this.requiredProgress;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public boolean isIsActive() {
        return this.isActive;
    }

}
