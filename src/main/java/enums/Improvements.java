package enums;

import java.util.Arrays;
import java.util.HashSet;

import enums.tiles.TileBaseTypes;
import enums.tiles.TileFeatureTypes;
import enums.tiles.TileTypes;

public enum Improvements {
    CAMP("camp", 0, 0, 0, Technologies.TRAPPING,
            new HashSet<TileTypes>(Arrays.asList(TileFeatureTypes.JUNGLE, TileBaseTypes.TUNDRA, TileBaseTypes.PLAIN,
                    TileBaseTypes.HEEL))), // camp
    FARM("farm", 1, 0, 0, Technologies.AGRICULTURE,
            new HashSet<TileTypes>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEDOW))), // mazrae
    LUMBERMILL("LubmerMill", 0, 1, 0, Technologies.CONSTRUCTION,
            new HashSet<TileTypes>(Arrays.asList(TileFeatureTypes.JUNGLE))), // karkhane choob
    MINE("mine", 0, 1, 0, Technologies.MINING,
            new HashSet<TileTypes>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEDOW,
                    TileBaseTypes.TUNDRA, TileBaseTypes.SNOW, TileBaseTypes.HEEL, TileFeatureTypes.JUNGLE,
                    TileFeatureTypes.FOREST, TileFeatureTypes.SWAMP))),
    PASTURE("pasture", 0, 0, 0, Technologies.ANIMAL_HUSBANDRY,
            new HashSet<TileTypes>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEDOW,
                    TileBaseTypes.HEEL, TileBaseTypes.TUNDRA))), // cheragah
    AGRICULTURE("agriculture", 0, 0, 0, Technologies.CALENDAR,
            new HashSet<TileTypes>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEDOW,
                    TileFeatureTypes.JUNGLE, TileFeatureTypes.FOREST, TileFeatureTypes.SWAMP,
                    TileFeatureTypes.VALLEY))), // kesht va kar
    QUARRY("quarry", 0, 0, 0, Technologies.MASONRY,
            new HashSet<TileTypes>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEDOW,
                    TileBaseTypes.TUNDRA, TileBaseTypes.HEEL))), // madan sang
    TRADINGPOST("trading post", 0, 0, 1, Technologies.TRAPPING,
            new HashSet<TileTypes>(Arrays.asList(TileBaseTypes.PLAIN, TileBaseTypes.DESERT, TileBaseTypes.MEDOW,
                    TileBaseTypes.TUNDRA))), // poste - e - tejari
    FACTORY("factory", 0, 2, 0, Technologies.ENGINEERING, new HashSet<TileTypes>(Arrays.asList(TileBaseTypes.PLAIN,
            TileBaseTypes.DESERT, TileBaseTypes.MEDOW, TileBaseTypes.TUNDRA, TileBaseTypes.SNOW)));

    Improvements(String name, double food, double production, double gold, Technologies requiredTechnology,
            HashSet<TileTypes> possibleTiles) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.requiredTechnology = requiredTechnology;
        this.possibleTiles = possibleTiles;
    }

    private String name;
    private double food;
    private double production;
    private double gold;
    private Technologies requiredTechnology;
    private HashSet<TileTypes> possibleTiles;
}
