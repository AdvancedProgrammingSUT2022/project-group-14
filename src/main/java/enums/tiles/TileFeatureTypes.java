package enums.tiles;

import enums.resources.BonusResourceTypes;
import enums.resources.LuxuryResourceTypes;
import enums.resources.ResourceTypes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public enum TileFeatureTypes implements TileTypes {
    VALLEY("valley", 2, 0, 0, -33, 1,
            new HashSet<ResourceTypes>(Arrays.asList(LuxuryResourceTypes.SUGAR, BonusResourceTypes.WHEAT))), // jolge
    JUNGLE("jungle", 1, 1, 0, 25, 2,
            new HashSet<ResourceTypes>(Arrays.asList(BonusResourceTypes.GAZELLE, LuxuryResourceTypes.COTTON,
                    LuxuryResourceTypes.COLOR, LuxuryResourceTypes.SILK))), // jangal
    ICE("ice", 0, 0, 0, 0, 9999, new HashSet<ResourceTypes>()),
    FOREST("forest", 1, -1, 0, 25, 2,
            new HashSet<ResourceTypes>(
                    Arrays.asList(BonusResourceTypes.BANANA, LuxuryResourceTypes.JEWEL, LuxuryResourceTypes.COLOR))), // jangale
    // anbooh
    SWAMP("swamp", -1, 0, 0, -33, 2, new HashSet<ResourceTypes>(Arrays.asList(LuxuryResourceTypes.SUGAR))), // mordab
    OASIS("oasis", 3, 0, 1, -33, 1, new HashSet<ResourceTypes>()), // vahe
    LAKE("lake", 0, 0, 1, 0, 9999, new HashSet<ResourceTypes>()),
    NULL("nothing" , 0 , 0 , 0 , 0 , 0,new HashSet<ResourceTypes>());

    private String name;
    private double food;
    private double production;
    private double gold;
    private int combatImpact;
    private int movementPoint;
    private HashSet<ResourceTypes> possibleResources;

    TileFeatureTypes(String name, double food, double production, double gold, int combatImpact,
                     int movementPoint, HashSet<ResourceTypes> possibleResources) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatImpact = combatImpact;
        this.movementPoint = movementPoint;
        this.possibleResources = possibleResources;
    }

    public static TileFeatureTypes generateRandom() {
        Random rand = new Random();
        return TileFeatureTypes.values()[rand.nextInt(TileFeatureTypes.values().length - 1)];
    }

    public String getName() {
        return this.name;
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

    public int getMovingPoint() {
        return movementPoint;
    }

    public HashSet<ResourceTypes> getPossibleResources() {
        return possibleResources;
    }

    public int getCombatImpact() {
        return combatImpact;
    }

    public void setFood(double food) {
        this.food = food;
    }

}
