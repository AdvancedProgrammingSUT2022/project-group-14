package enums.tiles;

public enum TileType {
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

    TileType(String regex) {
        this.regex = regex;
    }

    public String getName() {
        return this.regex;
    }
}
