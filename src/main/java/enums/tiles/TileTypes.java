package enums.tiles;

import java.util.Random;

public enum TileTypes {
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
