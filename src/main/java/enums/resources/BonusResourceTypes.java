package enums.resources;

import enums.Improvements;

public enum BonusResourceTypes implements ResourceTypes {

    BANANA("banana", 1, 0, 0, Improvements.FARM),
    COW("cow", 1, 0, 0, Improvements.CAMP),
    GAZELLE("gazelle", 1, 0, 0, Improvements.CAMP),
    SHEEP("sheep", 2, 0, 0, Improvements.FARM),
    WHEAT("wheat", 1, 0, 0, Improvements.FARM);

    private String name;
    private double food;
    private double production;
    private double gold;
    private Improvements requiredImprovement;

    BonusResourceTypes(String name, double food, double production, double gold, Improvements requiredImprovement) {
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
}
