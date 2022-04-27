package enums.tiles;

import java.util.Random;

public enum TileTypes {
    DESERT("//s+desert//s+"),
    MEDOW("//s+medow//s+"),
    HEEL("//s+heel//s+"),
    MOUNTAIN("//s+mountain//s+"),
    OCEAN("//s+ocean//s+"),
    PLAIN("//s+plain//s+"),
    SNOW("//s+snow//s+"),
    TUNDRA("//s+tundra//s+"),
    UNKOWN("ERROR");

    private final String regex;

    TileTypes(String regex) {
        this.regex = regex;
    }

    public String getName() {
        return this.regex;
    }

    public static TileTypes generateRandomTileType() {
        Random rand = new Random();
        return TileTypes.values()[rand.nextInt(TileTypes.values().length - 1)];
    }
}
