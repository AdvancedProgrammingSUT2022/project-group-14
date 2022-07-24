package Server.enums.resources;

import Server.enums.Improvements;

public enum BonusResourceTypes implements ResourceTypes {

    BANANA("banana", 1, 0, 0, Improvements.FARM),
    COW("cow", 1, 0, 0, Improvements.CAMP),
    GAZELLE("gazelle", 1, 0, 0, Improvements.CAMP),
    SHEEP("sheep", 2, 0, 0, Improvements.FARM),
    WHEAT("wheat", 1, 0, 0, Improvements.FARM);

    private final String name;
    private final double food;
    private final double production;
    private final double gold;
    private final Improvements requiredImprovement;

    BonusResourceTypes(String name, double food, double production, double gold, Improvements requiredImprovement) {
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
    public String getName(){
        return this.name;
    }
}
