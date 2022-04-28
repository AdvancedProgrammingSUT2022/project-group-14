package models.resources;

import enums.Improvements;
import enums.resources.BonusResourceTypes;
import enums.resources.LuxuryResourceTypes;
import enums.resources.ResourceTypes;
import enums.resources.StrategicResourceTypes;

public class Resource {
    private double food;
    private double production;
    private double gold;

    private Improvements requiredImprovement;
    private boolean isActive;// what the hell is this?

    public Resource(double food, double production, double gold, Improvements requiredProgress) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.requiredImprovement = requiredProgress;
    }

    public Resource(ResourceTypes type) {
        this.food = type.foodGetter();
        this.production = type.productionGetter();
        this.gold = type.goldGetter();
        this.requiredImprovement = type.improvementGetter();
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

    public Improvements getRequiredProgress() {
        return this.requiredImprovement;
    }

    public boolean getIsActive() {
        return this.isActive;
    }

    public boolean isIsActive() {
        return this.isActive;
    }

}
