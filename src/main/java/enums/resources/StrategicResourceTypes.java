package enums.resources;

import enums.Improvements;
import enums.Technologies;

public enum StrategicResourceTypes implements ResourceTypes {

    COAL("coal", 0, 1, 0, Improvements.MINE, Technologies.SCIENTIFIC_THEORY),
    HORSE("horse", 0, 1, 0, Improvements.AGRICULTURE, Technologies.ANIMAL_HUSBANDRY),
    IRON("iron", 0, 1, 0, Improvements.MINE, Technologies.IRON_WORKING);

    private String name;
    private double food;
    private double production;
    private double gold;
    private Improvements requiredImprovement;
    private Technologies requiredTechnology;

    StrategicResourceTypes(String name, double food, double production, double gold, Improvements requiredImprovement,
            Technologies requiredTechnology) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.requiredImprovement = requiredImprovement;
    }

    @Override
    public double foodGetter() {
        return this.food;
    }

    @Override
    public double productionGetter() {
        return this.production;
    }

    @Override
    public double goldGetter() {
        return this.gold;
    }

    @Override
    public Improvements requiredImprovementGetter() {
        return this.requiredImprovement;
    }

    @Override
    public String nameGetter(){
        return this.name;
    }

    public Technologies requiredTechnologyGetter() {
        return this.requiredTechnology;
    }

}
