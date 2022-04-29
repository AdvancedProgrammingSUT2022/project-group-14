package enums.resources;

import enums.Improvements;

public enum LuxuryResourceTypes implements ResourceTypes {
    COTTON(0, 0, 2, Improvements.FARM),
    COLOR(0, 0, 2, Improvements.FARM),
    FUR(0, 0, 2, Improvements.CAMP),
    JEWEL(0, 0, 3, Improvements.MINE),
    GOLD(0, 0, 2, Improvements.MINE),
    INCENSE(0, 0, 2, Improvements.FARM),
    IVORY(0, 0, 2, Improvements.CAMP),
    MARBLE(0, 0, 2, Improvements.MINE),
    SILK(0, 0, 2, Improvements.FARM),
    SILVER(0, 0, 2, Improvements.MINE),
    SUGAR(0, 0, 2, Improvements.FARM);

    private double food;
    private double production;
    private double gold;
    private Improvements requiredImprovement;

    LuxuryResourceTypes(double food, double production, double gold, Improvements requiredImprovement) {
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


}
