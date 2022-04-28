package enums.resources;

import enums.Improvements;

public enum BonusResourceTypes implements ResourceTypes {

    BANANA(1, 0, 0, Improvements.FARM),
    COW(1, 0, 0, Improvements.CAMP),
    GAZELLE(1, 0, 0, Improvements.CAMP),
    SHEEP(2, 0, 0, Improvements.FARM),
    WHEAT(1, 0, 0, Improvements.FARM);

    private double food;
    private double production;
    private double gold;
    private Improvements requiredImprovement;

    BonusResourceTypes(double food, double production, double gold, Improvements requiredImprovement) {
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
    public Improvements improvementGetter() {
        return this.requiredImprovement;
    }
}
