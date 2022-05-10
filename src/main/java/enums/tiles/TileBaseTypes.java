package enums.tiles;

import enums.Colors;

import java.util.Random;

public enum TileBaseTypes implements TileTypes {

    DESERT("desert", 0, 0, 0, -33, 1, Colors.YELLOW),
    MEDOW("medow", 2, 0, 0, -33, 1, Colors.GREEN),
    HEEL("heel", 0, 2, 0, 25, 2, Colors.BLACK),
    MOUNTAIN("mountain", 0, 0, 0, 25, 9999, Colors.PURPLE),
    OCEAN("ocean", 0, 0, 0, 25, 9999, Colors.CYAN),
    PLAIN("plain", 1, 1, 0, -33, 1, Colors.RED),
    SNOW("snow", 0, 0, 0, -33, 1, Colors.WHITE),
    TUNDRA("tundra", 1, 0, 0, -33, 1, Colors.PINK);

    private final String name;
    private double food;
    private double production;
    private double gold;
    private int combatImpact;
    private int movingPoint;
    private Colors color;

    TileBaseTypes(String name, double food, double production, double gold, int combatImpact, int movingPoint,
                  Colors color) {
        this.name = name;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatImpact = combatImpact;
        this.movingPoint = movingPoint;
        this.color = color;
    }

    public static TileBaseTypes generateRandomTileType() {
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
