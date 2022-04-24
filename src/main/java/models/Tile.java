package models;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import enums.Progresses;
import enums.tiles.TileFeature;
import enums.tiles.TileType;
import models.resources.LuxuryResource;
import models.resources.Resource;
import models.resources.StrategicResource;
import models.units.CombatUnit;
import models.units.NonCombatUnit;

public class Tile {
    private int x;
    private int y;
    private TileType type;
    private double food;
    private double production;
    private double gold;
    private int militaryImpact;
    private int movingPoint;
    private String color;
    private TileFeature feature;
    private StrategicResource strategicResource;
    private LuxuryResource luxuryResource;
    private Resource bonusResource;
    private boolean[] isRiver = new boolean[6];
    private boolean hasRoad;
    private boolean hasRailroad;
    private Progresses progress;
    // if the tile was attacked pillaged becomes true and when it is repaired the
    // tile it becomes false again
    private boolean pillaged = false;

    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private City city;
    private Civilization civilization = null;

    private static HashMap<TileType, Tile> tileInformationMap = new HashMap<>();

    public Tile(TileType type) {
        Tile tile = tileInformationMap.get(type);
        this.type = tile.type;
        this.food = tile.food;
        this.production = tile.production;
        this.gold = tile.gold;
        this.militaryImpact = tile.militaryImpact;
        this.movingPoint = tile.movingPoint;
        this.color = tile.color;
    }

    public static Tile generateRandomTile() {
        return new Tile(TileType.generateRandomTileType());
    }

    public Tile copy() {
        Tile tile = new Tile(TileType.UNKOWN);
        tile.type = this.type;
        tile.food = this.food;
        tile.production = this.production;
        tile.gold = this.gold;
        tile.militaryImpact = this.militaryImpact;
        tile.movingPoint = this.movingPoint;
        tile.color = this.color;
        return tile;
    }

    public static void readTileTypesInformationFromJson() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("resources/TileTypeInformation.json")));
            tileInformationMap = new Gson().fromJson(json, new TypeToken<HashMap<TileType, Tile>>() {
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

    public TileType getType() {
        return this.type;
    }

    public void setType(TileType type) {
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
        return this.militaryImpact;
    }

    public void setMilitaryImpact(int militaryImpact) {
        this.militaryImpact = militaryImpact;
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

    public TileFeature getFeature() {
        return this.feature;
    }

    public void setFeature(TileFeature feature) {
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

    public Civilization getCivilization() {
        return this.civilization;
    }

    public void setCivilization(Civilization civilization) {
        this.civilization = civilization;
    }
}
