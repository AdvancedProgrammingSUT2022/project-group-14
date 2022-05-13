package enums.tiles;

import enums.Colors;
import enums.resources.BonusResourceTypes;
import enums.resources.LuxuryResourceTypes;
import enums.resources.ResourceTypes;
import enums.resources.StrategicResourceTypes;
import models.resources.StrategicResource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public enum TileBaseTypes implements TileTypes {

    DESERT("desert", 0, 0, 0, -33, 1, Colors.YELLOW,new HashSet<>(List.of(TileFeatureTypes.OASIS , TileFeatureTypes.VALLEY)), new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON,LuxuryResourceTypes.GOLD,LuxuryResourceTypes.SILVER,LuxuryResourceTypes.JEWEL,LuxuryResourceTypes.MARBLE, LuxuryResourceTypes.COTTON,LuxuryResourceTypes.INCENSE,BonusResourceTypes.SHEEP))), //kavir
    MEADOW("meadow", 2, 0, 0, -33, 1, Colors.GREEN,new HashSet<>(List.of(TileFeatureTypes.JUNGLE , TileFeatureTypes.SWAMP)),new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON,StrategicResourceTypes.HORSE,StrategicResourceTypes.COAL, BonusResourceTypes.COW, LuxuryResourceTypes.GOLD, LuxuryResourceTypes.JEWEL,LuxuryResourceTypes.COTTON,LuxuryResourceTypes.MARBLE,BonusResourceTypes.SHEEP))),// chamanzar
    HEEL("heel", 0, 2, 0, 25, 2, Colors.BLACK,new HashSet<>(List.of(TileFeatureTypes.JUNGLE, TileFeatureTypes.FOREST)), new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON, StrategicResourceTypes.COAL , BonusResourceTypes.GAZELLE , LuxuryResourceTypes.GOLD,LuxuryResourceTypes.SILVER,LuxuryResourceTypes.JEWEL, LuxuryResourceTypes.MARBLE,BonusResourceTypes.SHEEP))),
    MOUNTAIN("mountain", 0, 0, 0, 25, 9999, Colors.PURPLE,new HashSet<>(List.of()), new HashSet<>()),
    OCEAN("ocean", 0, 0, 0, 25, 9999, Colors.CYAN,new HashSet<>(List.of()), new HashSet<>()),
    PLAIN("plain", 1, 1, 0, -33, 1, Colors.RED,new HashSet<>(List.of(TileFeatureTypes.JUNGLE , TileFeatureTypes.FOREST)), new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON,StrategicResourceTypes.HORSE,StrategicResourceTypes.COAL,BonusResourceTypes.WHEAT,LuxuryResourceTypes.GOLD,LuxuryResourceTypes.JEWEL,LuxuryResourceTypes.MARBLE,LuxuryResourceTypes.IVORY,LuxuryResourceTypes.COTTON,LuxuryResourceTypes.INCENSE,BonusResourceTypes.SHEEP))), // dasht
    SNOW("snow", 0, 0, 0, -33, 1, Colors.WHITE,new HashSet<>(List.of()),new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON))),
    TUNDRA("tundra", 1, 0, 0, -33, 1, Colors.PINK, new HashSet<>(List.of(TileFeatureTypes.JUNGLE)), new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON,StrategicResourceTypes.HORSE, BonusResourceTypes.GAZELLE , LuxuryResourceTypes.SILVER,LuxuryResourceTypes.JEWEL, LuxuryResourceTypes.MARBLE, LuxuryResourceTypes.COTTON)));

    private final String name;
    private final double food;
    private final double production;
    private final double gold;
    private final int combatImpact;
    private final int movingPoint;
    private final Colors color;

    private final HashSet<TileFeatureTypes> possibleFeatures;

    private final HashSet<ResourceTypes> possibleResources;
    TileBaseTypes(String name, double food, double production, double gold, int combatImpact, int movingPoint,
                  Colors color , HashSet<TileFeatureTypes> possibleFeatures , HashSet<ResourceTypes> possibleResources) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatImpact = combatImpact;
        this.movingPoint = movingPoint;
        this.color = color;
        possibleFeatures.add(TileFeatureTypes.NULL);
        this.possibleFeatures = possibleFeatures;
        this.possibleResources = possibleResources;
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

    public HashSet<TileFeatureTypes> getPossibleFeatures() {
        return possibleFeatures;
    }
    public HashSet<ResourceTypes> getPossibleResources(){
        return possibleResources;
    }
}
