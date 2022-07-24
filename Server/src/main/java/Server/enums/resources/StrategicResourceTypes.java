package Server.enums.resources;

import Server.enums.Technologies;
import Server.enums.Improvements;

import java.util.HashMap;

public enum StrategicResourceTypes implements ResourceTypes {
    COAL("coal", 0, 1, 0, Improvements.MINE, Technologies.SCIENTIFIC_THEORY),
    HORSE("horse", 0, 1, 0, Improvements.AGRICULTURE, Technologies.ANIMAL_HUSBANDRY),
    IRON("iron", 0, 1, 0, Improvements.MINE, Technologies.IRON_WORKING);

    private final String name;
    private final double food;
    private final double production;
    private final double gold;
    private final Improvements requiredImprovement;
    private final Technologies requiredTechnology;

    StrategicResourceTypes(String name, double food, double production, double gold, Improvements requiredImprovement,
                           Technologies requiredTechnology) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.requiredImprovement = requiredImprovement;
        this.requiredTechnology = requiredTechnology;
    }

    @Override
    public double getFood() {
        return this.food;
    }

    @Override
    public double getProduction() {
        return this.production;
    }

    @Override
    public double getGold() {
        return this.gold;
    }

    @Override
    public Improvements getRequiredImprovement() {
        return this.requiredImprovement;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public Technologies getRequiredTechnology() {
        return this.requiredTechnology;
    }

    public static HashMap<String, Integer> getAllResources() {
        HashMap<String, Integer> allResources = new HashMap<>();
        for (StrategicResourceTypes strategicResource : StrategicResourceTypes.values())
            allResources.put(strategicResource.getName(), 0);
        return allResources;
    }

}
