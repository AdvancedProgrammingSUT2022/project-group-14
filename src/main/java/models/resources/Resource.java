package models.resources;

import enums.Improvements;
import enums.resources.BonusResourceTypes;
import enums.resources.LuxuryResourceTypes;
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

    public Resource(StrategicResourceTypes type) {
        Resource resource = StrategicResource.typesMapGetter().get(type);
        this.food = resource.food;
        this.production = resource.production;
        this.gold = resource.gold;
        this.requiredImprovement = resource.requiredImprovement;
    }

    public Resource(LuxuryResourceTypes type) {
        Resource resource = LuxuryResource.typeMapGetter().get(type);
        this.food = resource.food;
        this.production = resource.production;
        this.gold = resource.gold;
        this.requiredImprovement = resource.requiredImprovement;
    }

    public Resource(BonusResourceTypes type) {
        Resource resource = BonusResource.typeMapGetter().get(type);
        this.food = resource.food;
        this.production = resource.production;
        this.gold = resource.gold;
        this.requiredImprovement = resource.requiredImprovement;
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
