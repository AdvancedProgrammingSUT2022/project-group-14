package Client.enums.tiles;

import Client.enums.resources.BonusResourceTypes;
import Client.enums.resources.LuxuryResourceTypes;
import Client.enums.resources.ResourceTypes;
import Client.enums.resources.StrategicResourceTypes;
import javafx.scene.image.Image;

import java.util.*;

public enum TileBaseTypes implements TileTypes {

    DESERT("desert", 0, 0, 0, -33, 1, new HashSet<>(List.of(TileFeatureTypes.OASIS, TileFeatureTypes.FLOODPLAIN)), new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON, LuxuryResourceTypes.GOLD, LuxuryResourceTypes.SILVER, LuxuryResourceTypes.JEWEL, LuxuryResourceTypes.MARBLE, LuxuryResourceTypes.COTTON, LuxuryResourceTypes.INCENSE, BonusResourceTypes.SHEEP))), //kavir
    MEADOW("meadow", 2, 0, 0, -33, 1, new HashSet<>(List.of(TileFeatureTypes.JUNGLE, TileFeatureTypes.SWAMP)), new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON, StrategicResourceTypes.HORSE, StrategicResourceTypes.COAL, BonusResourceTypes.COW, LuxuryResourceTypes.GOLD, LuxuryResourceTypes.JEWEL, LuxuryResourceTypes.COTTON, LuxuryResourceTypes.MARBLE, BonusResourceTypes.SHEEP))),// chamanzar
    HILL("hill", 0, 2, 0, 25, 2, new HashSet<>(List.of(TileFeatureTypes.JUNGLE, TileFeatureTypes.FOREST)), new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON, StrategicResourceTypes.COAL, BonusResourceTypes.GAZELLE, LuxuryResourceTypes.GOLD, LuxuryResourceTypes.SILVER, LuxuryResourceTypes.JEWEL, LuxuryResourceTypes.MARBLE, BonusResourceTypes.SHEEP))),
    MOUNTAIN("mountain", 0, 0, 0, 25, 9999, new HashSet<>(List.of()), new HashSet<>()),
    OCEAN("ocean", 0, 0, 0, 25, 9999, new HashSet<>(List.of()), new HashSet<>()),
    PLAIN("plain", 1, 1, 0, -33, 1, new HashSet<>(List.of(TileFeatureTypes.JUNGLE, TileFeatureTypes.FOREST)), new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON, StrategicResourceTypes.HORSE, StrategicResourceTypes.COAL, BonusResourceTypes.WHEAT, LuxuryResourceTypes.GOLD, LuxuryResourceTypes.JEWEL, LuxuryResourceTypes.MARBLE, LuxuryResourceTypes.IVORY, LuxuryResourceTypes.COTTON, LuxuryResourceTypes.INCENSE, BonusResourceTypes.SHEEP))), // dasht
    SNOW("snow", 0, 0, 0, -33, 1, new HashSet<>(List.of()), new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON))),
    TUNDRA("tundra", 1, 0, 0, -33, 1, new HashSet<>(List.of(TileFeatureTypes.JUNGLE)), new HashSet<>(Arrays.asList(StrategicResourceTypes.IRON, StrategicResourceTypes.HORSE, BonusResourceTypes.GAZELLE, LuxuryResourceTypes.SILVER, LuxuryResourceTypes.JEWEL, LuxuryResourceTypes.MARBLE, LuxuryResourceTypes.COTTON)));

    private final String name;
    private final double food;
    private final double production;
    private final double gold;
    private final int combatImpact;
    private final int movingPoint;

    private final HashSet<TileFeatureTypes> possibleFeatures;

    private final HashSet<ResourceTypes> possibleResources;

    TileBaseTypes(String name, double food, double production, double gold, int combatImpact, int movingPoint,
                  HashSet<TileFeatureTypes> possibleFeatures, HashSet<ResourceTypes> possibleResources) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatImpact = combatImpact;
        this.movingPoint = movingPoint;
        possibleFeatures.add(TileFeatureTypes.NULL);
        this.possibleFeatures = possibleFeatures;
        this.possibleResources = possibleResources;
    }

    public static TileBaseTypes generateRandom() {
        Random rand = new Random();
        return TileBaseTypes.values()[rand.nextInt(TileBaseTypes.values().length)];
    }

    public Image getImage() {
        return new Image(Objects.requireNonNull(Main.class.getResource("/images/tiles/bases/" + this.name + ".png")).toString());
    }

    @Override
    public String getName() {
        return this.name;
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
    public int getCombatImpact() {
        return this.combatImpact;
    }

    @Override
    public int getMovementPoint() {
        return this.movingPoint;
    }

    public HashSet<TileFeatureTypes> getPossibleFeatures() {
        return possibleFeatures;
    }

    @Override
    public HashSet<ResourceTypes> getPossibleResources() {
        return possibleResources;
    }
}
