package enums.tiles;

import java.util.Random;

public enum TileBaseTypes implements TileTypes{
    DESERT("//s+desert//s+"), // kavir
    MEDOW("//s+medow//s+"), // chamanzar
    HEEL("//s+heel//s+"), // tape
    MOUNTAIN("//s+mountain//s+"),
    OCEAN("//s+ocean//s+"),
    PLAIN("//s+plain//s+"), // dasht
    SNOW("//s+snow//s+"),
    TUNDRA("//s+tundra//s+"),
    UNKOWN("ERROR");

    private final String regex;

    TileBaseTypes(String regex) {
        this.regex = regex;
    }

    public String getName() {
        return this.regex;
    }

    public static TileBaseTypes generateRandomTileType() {
        Random rand = new Random();
        return TileBaseTypes.values()[rand.nextInt(TileBaseTypes.values().length - 1)];
    }
}
