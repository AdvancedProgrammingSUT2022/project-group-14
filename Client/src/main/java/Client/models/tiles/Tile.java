package Client.models.tiles;

import Client.enums.Improvements;
import Client.enums.tiles.TileBaseTypes;
import Client.enums.tiles.TileFeatureTypes;
import Client.models.City;
import Client.models.resources.Resource;
import Client.models.units.CombatUnit;
import Client.models.units.NonCombatUnit;

import java.util.Arrays;
import java.util.Objects;

public class Tile {
    private final Coordination coordination;

    private TileBaseTypes type;
    private TileFeatureTypes feature;

    private double food;
    private double production;
    private double gold;
    private int combatImpact;
    private int movingPoint;

    private Resource resource;

    private Improvements improvement;
    private int improvementTurnsLeftToBuild; // 9999 -> has not been started yet | 0 -> has been build

    private final boolean[] isRiver;
    private int roadState; // 9999 -> has not been started to build | 0 -> has been build
    private int railRoadState; // 9999 -> has not been started to build | 0 -> has been build

    private int pillageState; // 0 -> has not been pillaged | 9999 -> been pillaged

    private String civilizationName;
    private City city;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;
    private Ruin ruin;
    private boolean showRuin;

    public Tile(TileFeatureTypes feature, TileBaseTypes type, int x, int y, Ruin ruin) {
        this.coordination = new Coordination(x, y);
        this.feature = feature;
        this.type = type;
        this.food = type.getFood() + feature.getFood();
        this.production = type.getProduction() + feature.getProduction();
        this.gold = type.getGold() + feature.getGold();
        this.combatImpact = type.getCombatImpact() + feature.getCombatImpact();
        this.movingPoint = type.getMovementPoint() + feature.getMovementPoint();
        this.improvementTurnsLeftToBuild = 9999;
        this.roadState = 9999;
        this.railRoadState = 9999;
        this.isRiver = new boolean[6];
        for (int i = 0; i < 6; i++)
            this.isRiver[i] = false;
        this.resource = Resource.generateRandomResource(type, feature);
        this.ruin = ruin;
    }

    public int getX() {
        return this.coordination.getX();
    }

    public int getY() {
        return this.coordination.getY();
    }

    public TileBaseTypes getType() {
        return this.type;
    }

    public TileFeatureTypes getFeature() {
        return this.feature;
    }

    public int getRoadState() {
        return roadState;
    }

    public int getRailRoadState() {
        return railRoadState;
    }

    public CombatUnit getCombatUnit() {
        return this.combatUnit;
    }

    public NonCombatUnit getNonCombatUnit() {
        return this.nonCombatUnit;
    }

    public City getCity() {
        return this.city;
    }

    public void setCivilization(String civilizationName) {
        this.civilizationName = civilizationName;
    }

    public boolean showRuin() {
        return this.showRuin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return Double.compare(tile.food, food) == 0 && Double.compare(tile.production, production) == 0 && Double.compare(tile.gold, gold) == 0 && combatImpact == tile.combatImpact && movingPoint == tile.movingPoint && improvementTurnsLeftToBuild == tile.improvementTurnsLeftToBuild && roadState == tile.roadState && railRoadState == tile.railRoadState && pillageState == tile.pillageState && coordination.equals(tile.coordination) && type == tile.type && feature == tile.feature && Objects.equals(resource, tile.resource) && improvement == tile.improvement && Arrays.equals(isRiver, tile.isRiver) && Objects.equals(civilizationName, tile.civilizationName) && Objects.equals(combatUnit, tile.combatUnit) && Objects.equals(nonCombatUnit, tile.nonCombatUnit);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(coordination, type, feature, food, production, gold, combatImpact, movingPoint, resource, improvement, improvementTurnsLeftToBuild, roadState, railRoadState, pillageState, civilizationName, combatUnit, nonCombatUnit);
        result = 31 * result + Arrays.hashCode(isRiver);
        return result;
    }
}
