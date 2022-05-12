package enums.tiles;

import enums.Colors;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

public enum TileBaseTypes implements TileTypes {

    DESERT("desert", 0, 0, 0, -33, 1, Colors.YELLOW,new HashSet<>(List.of(TileFeatureTypes.OASIS , TileFeatureTypes.VALLEY))), //kavir
    MEADOW("meadow", 2, 0, 0, -33, 1, Colors.GREEN,new HashSet<>(List.of(TileFeatureTypes.JUNGLE , TileFeatureTypes.SWAMP))),// chamanzar
    HEEL("heel", 0, 2, 0, 25, 2, Colors.BLACK,new HashSet<>(List.of(TileFeatureTypes.JUNGLE, TileFeatureTypes.FOREST))),
    MOUNTAIN("mountain", 0, 0, 0, 25, 9999, Colors.PURPLE,new HashSet<>(List.of())),
    OCEAN("ocean", 0, 0, 0, 25, 9999, Colors.CYAN,new HashSet<>(List.of())),
    PLAIN("plain", 1, 1, 0, -33, 1, Colors.RED,new HashSet<>(List.of(TileFeatureTypes.JUNGLE , TileFeatureTypes.FOREST))), // dasht
    SNOW("snow", 0, 0, 0, -33, 1, Colors.WHITE,new HashSet<>(List.of())),
    TUNDRA("tundra", 1, 0, 0, -33, 1, Colors.PINK, new HashSet<>(List.of(TileFeatureTypes.JUNGLE)));

    private final String name;
    private final double food;
    private final double production;
    private final double gold;
    private final int combatImpact;
    private final int movingPoint;
    private final Colors color;

    private final HashSet<TileFeatureTypes> possibleFeatures;
    TileBaseTypes(String name, double food, double production, double gold, int combatImpact, int movingPoint,
                  Colors color , HashSet<TileFeatureTypes> possibleFeatures) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatImpact = combatImpact;
        this.movingPoint = movingPoint;
        this.color = color;
        this.possibleFeatures = possibleFeatures;
    }

    public static TileBaseTypes generateRandom() {
        Random rand = new Random();
        return TileBaseTypes.values()[rand.nextInt(TileBaseTypes.values().length - 1)];
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

    public int getCombatImpact() {
        return this.combatImpact;
    }

    public int getMovingPoint() {
        return this.movingPoint;
    }

    public Colors getColor() {
        return this.color;
    }

}
