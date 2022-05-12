package enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

import enums.tiles.TileBaseTypes;
import enums.tiles.TileFeatureTypes;
import enums.tiles.TileTypes;

public enum Improvements {
    CAMP("camp", 0, 0, 0, Technologies.TRAPPING,
            new HashSet<>(Arrays.asList(TileFeatureTypes.JUNGLE, TileBaseTypes.TUNDRA, TileBaseTypes.PLAIN,
                    TileBaseTypes.HEEL))), // camp
    FARM("farm", 1, 0, 0, Technologies.AGRICULTURE,
            new HashSet<>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEADOW))), // mazrae
    LUMBERMILL("LubmerMill", 0, 1, 0, Technologies.CONSTRUCTION,
            new HashSet<>(Arrays.asList(TileFeatureTypes.JUNGLE))), // karkhane choob
    MINE("mine", 0, 1, 0, Technologies.MINING,
            new HashSet<>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEADOW,
                    TileBaseTypes.TUNDRA, TileBaseTypes.SNOW, TileBaseTypes.HEEL, TileFeatureTypes.JUNGLE,
                    TileFeatureTypes.FOREST, TileFeatureTypes.SWAMP))),
    PASTURE("pasture", 0, 0, 0, Technologies.ANIMAL_HUSBANDRY,
            new HashSet<>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEADOW,
                    TileBaseTypes.HEEL, TileBaseTypes.TUNDRA))), // cheragah
    AGRICULTURE("agriculture", 0, 0, 0, Technologies.CALENDAR,
            new HashSet<>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEADOW,
                    TileFeatureTypes.JUNGLE, TileFeatureTypes.FOREST, TileFeatureTypes.SWAMP,
                    TileFeatureTypes.VALLEY))), // kesht va kar
    QUARRY("quarry", 0, 0, 0, Technologies.MASONRY,
            new HashSet<>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEADOW,
                    TileBaseTypes.TUNDRA, TileBaseTypes.HEEL))), // madan sang
    TRADINGPOST("trading post", 0, 0, 1, Technologies.TRAPPING,
            new HashSet<>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEADOW,
                    TileBaseTypes.TUNDRA))), // poste - e - tejari
    FACTORY("factory", 0, 2, 0, Technologies.ENGINEERING, new HashSet<>(Arrays.asList(TileBaseTypes.PLAIN,
            TileBaseTypes.DESERT, TileBaseTypes.MEADOW, TileBaseTypes.TUNDRA, TileBaseTypes.SNOW)));

    private final double food;
    private final double production;
    private final double gold;
    private final Technologies requiredTechnology;
    private final HashSet<TileTypes> possibleTiles;

    Improvements(String name, double food, double production, double gold, Technologies requiredTechnology,
            HashSet<TileTypes> possibleTiles) {
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.requiredTechnology = requiredTechnology;
        this.possibleTiles = possibleTiles;
    }

    public static Improvements getImprovementByName(String name) {
        return Improvements.valueOf(name.toUpperCase(Locale.ROOT));
    }

    public Technologies getRequiredTechnology() {
        return this.requiredTechnology;
    }

    public HashSet<TileTypes> getPossibleTiles() {
        return this.possibleTiles;
    }
}
