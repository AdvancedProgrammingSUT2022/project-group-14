package Server.enums.resources;

import Server.enums.Improvements;

import java.util.HashMap;

public enum LuxuryResourceTypes implements ResourceTypes {
    COTTON("cotton", 0, 0, 2, Improvements.FARM),//panbeh
    COLOR("color", 0, 0, 2, Improvements.FARM),
    FUR("fur", 0, 0, 2, Improvements.CAMP),
    JEWEL("jewel", 0, 0, 3, Improvements.MINE),
    GOLD("gold", 0, 0, 2, Improvements.MINE),
    INCENSE("incense", 0, 0, 2, Improvements.FARM),
    IVORY("ivory", 0, 0, 2, Improvements.CAMP),
    MARBLE("marble", 0, 0, 2, Improvements.MINE),//marmar
    SILK("silk", 0, 0, 2, Improvements.FARM),
    SILVER("silver", 0, 0, 2, Improvements.MINE),
    SUGAR("sugar", 0, 0, 2, Improvements.FARM);

    private final String name;
    private final double food;
    private final double production;
    private final double gold;
    private final Improvements requiredImprovement;

    LuxuryResourceTypes(String name, double food, double production, double gold, Improvements requiredImprovement) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.requiredImprovement = requiredImprovement;
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

    public static HashMap<String, Integer> getAllResources() {
        HashMap<String, Integer> allResources = new HashMap<>();
        for (LuxuryResourceTypes luxuryResource : LuxuryResourceTypes.values())
            allResources.put(luxuryResource.getName(), 0);
        return allResources;
    }

}
