package models.resources;

import enums.Improvements;
import enums.resources.BonusResourceTypes;
import enums.resources.LuxuryResourceTypes;
import enums.resources.ResourceTypes;
import enums.resources.StrategicResourceTypes;

public class Resource {
    private String name;

    private double food;
    private double production;
    private double gold;

    private Improvements requiredImprovement;
    private boolean hasBeenUsed = false;

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
        this.requiredImprovement = type.requiredImprovementGetter();
        this.name = type.nameGetter();
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

    public String getName() {
        return name;
    }

    public Improvements getRequiredImprovement() {
        return this.requiredImprovement;
    }

    public boolean hasBeenUsed() {
        return hasBeenUsed;
    }

    public void setHasBeenUsed(boolean hasBeenUsed) {
        this.hasBeenUsed = hasBeenUsed;
    }
}
