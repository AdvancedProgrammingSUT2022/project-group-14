package models.resources;

import enums.Improvements;
import enums.resources.ResourceTypes;
import enums.tiles.TileBaseTypes;
import enums.tiles.TileFeatureTypes;

import java.util.HashSet;
import java.util.Random;

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

    public static Resource generateRandomResource(TileBaseTypes type, TileFeatureTypes feature) {
        HashSet<ResourceTypes> allPossibleResources = type.getPossibleResources();
        allPossibleResources.addAll(feature.getPossibleResources());
        int possibleResourcesNumber = allPossibleResources.size();
        Random rand = new Random();
        if (rand.nextBoolean()) {
            ResourceTypes[] allPossibleResourcesArray = allPossibleResources.toArray(new ResourceTypes[possibleResourcesNumber]);
            int randomInt = rand.nextInt(possibleResourcesNumber);
            return new Resource(allPossibleResourcesArray[randomInt]);
        }
        return null;
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
