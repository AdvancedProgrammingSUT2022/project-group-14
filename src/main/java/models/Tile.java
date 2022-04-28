package models;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import enums.Progresses;
import enums.tiles.TileFeatureTypes;
import enums.tiles.TileTypes;
import models.resources.LuxuryResource;
import models.resources.Resource;
import models.resources.StrategicResource;
import models.units.CombatUnit;
import models.units.NonCombatUnit;

public class Tile {
    private int x;
    private int y;

    private TileTypes type;
    private TileFeatureTypes feature;
    private String color;

    private double food;
    private double production;
    private double gold;

    private int combatImpact;
    private int movingPoint;

    private StrategicResource strategicResource;
    private LuxuryResource luxuryResource;
    private Resource bonusResource;
    private Progresses progress;

    private boolean[] isRiver = new boolean[6];
    private boolean hasRoad;
    private boolean hasRailroad;

    private boolean pillaged;

    private String civilizationName;
    private City city;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;

    private static HashMap<TileTypes, Tile> tileInformationMap = new HashMap<>();

    public static void writeData() {
        try {
            final String RESET = "\u001B[0m";
            final String BLACK = "\u001B[40m";
            final String RED = "\u001B[41m";
            final String GREEN = "\u001B[42m";
            final String YELLOW = "\u001B[43m";
            final String PURPLE = "\u001B[44m";
            final String PINK = "\u001B[45m";
            final String CYAN = "\u001B[46m";
            final String WHITE = "\u001B[47m";

            final String BBLACK = "\u001B[100m";
            final String BRED = "\u001B[101m";
            final String BGREEN = "\u001B[102m";
            final String BYELLOW = "\u001B[103m";
            final String BBLUE = "\u001B[104m";
            final String BPURPLE = "\u001B[105m";
            final String BCYAN = "\u001B[106m";
            final String BWHITE = "\u001B[107m";

            FileWriter writer = new FileWriter("resources/TileTypeInformation.json");
            Tile tile = null;
            tile = new Tile(TileTypes.DESERT, 0, 0, 0, -33, 1, YELLOW);
            tileInformationMap.put(TileTypes.DESERT, tile);
            tile = new Tile(TileTypes.MEDOW, 2, 0, 0, -33, 1, GREEN);
            tileInformationMap.put(TileTypes.MEDOW, tile);
            tile = new Tile(TileTypes.HEEL, 0, 2, 0, 25, 2, BLACK);
            tileInformationMap.put(TileTypes.HEEL, tile);
            tile = new Tile(TileTypes.MOUNTAIN, 0, 0, 0, 25, -1, PURPLE);
            tileInformationMap.put(TileTypes.MOUNTAIN, tile);
            tile = new Tile(TileTypes.OCEAN, 0, 0, 0, 25, -1, CYAN);
            tileInformationMap.put(TileTypes.OCEAN, tile);
            tile = new Tile(TileTypes.PLAIN, 1, 1, 0, -33, 1, RED);
            tileInformationMap.put(TileTypes.PLAIN, tile);
            tile = new Tile(TileTypes.SNOW, 0, 0, 0, -33, 1, WHITE);
            tileInformationMap.put(TileTypes.SNOW, tile);
            tile = new Tile(TileTypes.TUNDRA, 1, 0, 0, -33, 1, PINK);
            tileInformationMap.put(TileTypes.TUNDRA, tile);

            writer.write(new Gson().toJson(tileInformationMap));

            writer.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public Tile(TileTypes type, double food, double production, double gold, int militaryImpact, int movingPoint,
            String color) {
        this.type = type;
        this.food = food;
        this.production = production;
        this.gold = gold;
        this.combatImpact = militaryImpact;
        this.movingPoint = movingPoint;
        this.color = color;
    }

    public Tile(TileTypes type, int x, int y) {
        Tile tile = tileInformationMap.get(type);
        this.x = x;
        this.y = y;
        this.type = tile.type;
        this.color = tile.color;
        this.food = tile.food;
        this.production = tile.production;
        this.gold = tile.gold;
        this.combatImpact = tile.combatImpact;
        this.movingPoint = tile.movingPoint;
    }

    public static Tile generateRandomTile(int x, int y) {
        return new Tile(TileTypes.generateRandomTileType(), x, y);
    }

    public Tile copy() {
        Tile tile = new Tile(TileTypes.UNKOWN, this.x, this.y);
        tile.type = this.type;
        tile.food = this.food;
        tile.production = this.production;
        tile.gold = this.gold;
        tile.combatImpact = this.combatImpact;
        tile.movingPoint = this.movingPoint;
        tile.color = this.color;
        return tile;
    }

    public static void readTileTypesInformationFromJson() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/TileTypeInformation.json")));
            tileInformationMap = new Gson().fromJson(json, new TypeToken<HashMap<TileTypes, Tile>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public TileTypes getType() {
        return this.type;
    }

    public void setType(TileTypes type) {
        this.type = type;
    }

    public double getFood() {
        return this.food;
    }

    public void setFood(double food) {
        this.food = food;
    }

    public double getProduction() {
        return this.production;
    }

    public void setProduction(double production) {
        this.production = production;
    }

    public double getGold() {
        return this.gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public int getMilitaryImpact() {
        return this.combatImpact;
    }

    public void setMilitaryImpact(int militaryImpact) {
        this.combatImpact = militaryImpact;
    }

    public int getMovingPoint() {
        return this.movingPoint;
    }

    public void setMovingPoint(int movingPoint) {
        this.movingPoint = movingPoint;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public TileFeatureTypes getFeature() {
        return this.feature;
    }

    public void setFeature(TileFeatureTypes feature) {
        this.feature = feature;
    }

    public StrategicResource getStrategicResource() {
        return this.strategicResource;
    }

    public void setStrategicResource(StrategicResource strategicResource) {
        this.strategicResource = strategicResource;
    }

    public LuxuryResource getLuxuryResource() {
        return this.luxuryResource;
    }

    public void setLuxuryResource(LuxuryResource luxuryResource) {
        this.luxuryResource = luxuryResource;
    }

    public Resource getBonusResource() {
        return this.bonusResource;
    }

    public void setBonusResource(Resource bonusResource) {
        this.bonusResource = bonusResource;
    }

    public boolean[] getIsRiver() {
        return this.isRiver;
    }

    public void setIsRiver(boolean[] isRiver) {
        this.isRiver = isRiver;
    }

    public boolean isHasRoad() {
        return this.hasRoad;
    }

    public boolean getHasRoad() {
        return this.hasRoad;
    }

    public void setHasRoad(boolean hasRoad) {
        this.hasRoad = hasRoad;
    }

    public boolean isHasRailroad() {
        return this.hasRailroad;
    }

    public boolean getHasRailroad() {
        return this.hasRailroad;
    }

    public void setHasRailroad(boolean hasRailroad) {
        this.hasRailroad = hasRailroad;
    }

    public Progresses getProgress() {
        return this.progress;
    }

    public void setProgress(Progresses progress) {
        this.progress = progress;
    }

    public boolean isPillaged() {
        return this.pillaged;
    }

    public boolean getPillaged() {
        return this.pillaged;
    }

    public void setPillaged(boolean pillaged) {
        this.pillaged = pillaged;
    }

    public CombatUnit getCombatUnit() {
        return this.combatUnit;
    }

    public void setCombatUnit(CombatUnit combatUnit) {
        this.combatUnit = combatUnit;
    }

    public NonCombatUnit getNonCombatUnit() {
        return this.nonCombatUnit;
    }

    public void setNonCombatUnit(NonCombatUnit nonCombatUnit) {
        this.nonCombatUnit = nonCombatUnit;
    }

    public City getCity() {
        return this.city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getCivilizationName() {
        return this.civilizationName;
    }

    public void setCivilization(String civilizationName) {
        this.civilizationName = civilizationName;
    }
}
