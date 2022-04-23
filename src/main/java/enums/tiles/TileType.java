package enums.tiles;

public enum TileType {
    MEDOW("//s+medow//s+"),
    DESERT("//s+desert//s+"),
    HEEL("//s+heel//s+"),
    MOUNTAIN("//s+mountain//s+"),
    OCEAN("//s+ocean//s+"),
    PLAINI("//s+plain//s+"),
    SNOW("//s+snow//s+"),
    TUNDRA("//s+tundra//s+");

    private final String regex;

    TileType(String regex) {
        this.regex = regex;
    }
}
