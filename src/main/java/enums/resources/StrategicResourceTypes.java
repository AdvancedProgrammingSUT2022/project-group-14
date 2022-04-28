package enums.resources;

import enums.Improvements;
import enums.Technologies;

public enum StrategicResourceTypes implements ResourceTypes {

    COAL(0, 1, 0, Improvements.MINE, Technologies.SCIENTIFIC_THEORY),
    HORSE(0, 1, 0, Improvements.AGRICULTURE, Technologies.ANIMAL_HUSBANDRY),
    IRON(0, 1, 0, Improvements.MINE, Technologies.IRON_WORKING);

    private double food;
    private double production;
    private double gold;
    private Improvements requiredImprovement;
    private Technologies requiredTechnology;

    StrategicResourceTypes(double food, double production, double gold, Improvements requiredImprovement,
            Technologies requiredTechnology) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.requiredImprovement = requiredImprovement;
    }
}
