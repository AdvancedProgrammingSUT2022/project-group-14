package models;

import controllers.TileController;
import controllers.WorldController;
import enums.Colors;
import enums.Improvements;
import enums.tiles.TileFeatureTypes;
import enums.tiles.TileBaseTypes;
import models.resources.BonusResource;
import models.resources.LuxuryResource;
import models.resources.Resource;
import models.resources.StrategicResource;
import models.units.CombatUnit;
import models.units.NonCombatUnit;

public class Tile {
    private int x;
    private int y;

    private TileBaseTypes type;
    private TileFeatureTypes feature;
    private Colors color;

    private double food;
    private double production;
    private double gold;

    private int combatImpact;
    private int movingPoint;

    private StrategicResource strategicResource;
    private LuxuryResource luxuryResource;
    private Resource bonusResource;
    private Improvements improvement;
    private int improvementTurnsLeftToBuild; // 9999 -> has not been started to build | 0 -> has been build

    private boolean[] isRiver = new boolean[6];
    private int roadState; // 9999 -> has not been started to build | 0 -> has been build
    private int railRoadState; // 9999 -> has not been started to build | 0 -> has been build

    private int pillageState; // 0 -> has not been pillaged | 9999 -> been pillaged

    private String civilizationName = null;
    private City city;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;

    public Tile(TileBaseTypes type, int x, int y) {
        this.x = x;
        this.y = y;
        this.type = type;
        this.color = type.getColor();
        this.food = type.getFood();
        this.production = type.getProduction();
        this.gold = type.getGold();
        this.combatImpact = type.getCombatImpact();
        this.movingPoint = type.getMovingPoint();
        this.improvementTurnsLeftToBuild = 9999;
        this.roadState = 9999;
        this.railRoadState = 9999;
    }

    public void addAvailableResourcesToCivilizationAndTile(){
        if (this.bonusResource != null &&
                !this.bonusResource.hasBeenUsed() &&
                TileController.resourceIsAvailableToBeUsed(this.bonusResource, this)){
            addResourceToCivilizationAndTile(this.bonusResource);
            this.bonusResource.setHasBeenUsed(true);
        }
        if (this.luxuryResource != null &&
                !this.luxuryResource.hasBeenUsed() &&
                TileController.resourceIsAvailableToBeUsed(this.luxuryResource, this)){
            addResourceToCivilizationAndTile(this.luxuryResource);
            this.luxuryResource.setHasBeenUsed(true);
        }
        if (this.strategicResource != null &&
                !this.strategicResource.hasBeenUsed() &&
                TileController.resourceIsAvailableToBeUsed(this.strategicResource, this)){
            addResourceToCivilizationAndTile(this.bonusResource);
            this.strategicResource.setHasBeenUsed(true);
        }
    }

    public void addResourceToCivilizationAndTile(Resource resource){
        this.food += resource.getFood();
        this.gold += resource.getGold();
        this.production += resource.getProduction();
        Civilization currenCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());

        if (resource instanceof LuxuryResource){
            currenCivilization.getLuxuryResources().put(resource.getName(),
                    currenCivilization.getLuxuryResources().get(resource.getName()) + 1);
            currenCivilization.setHappiness(currenCivilization.getHappiness() + 4);
        }else if (resource instanceof StrategicResource){
            currenCivilization.getStrategicResources().put(resource.getName(),
                    currenCivilization.getStrategicResources().get(resource.getName()) + 1);
        }
    }

    public static Tile generateRandomTile(int x, int y) {
        return new Tile(TileBaseTypes.generateRandomTileType(), x, y);
    }

    public Tile copy() {
        Tile tile = new Tile(TileBaseTypes.MEDOW, this.x, this.y);
        tile.type = this.type;
        tile.food = this.food;
        tile.production = this.production;
        tile.gold = this.gold;
        tile.combatImpact = this.combatImpact;
        tile.movingPoint = this.movingPoint;
        tile.color = this.color;
        return tile;
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

    public TileBaseTypes getType() {
        return this.type;
    }

    public void setType(TileBaseTypes type) {
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

    public int getMovingPointFromSide(int x, int y, int movingPoints) {
        if (x == -1 && y == 0 && isRiver[0]) {
            return movingPoints;
        } else if (x == 0 && y == 1 && isRiver[1]) {
            return movingPoints;
        } else if (x == 1 && y == 1 && isRiver[2]) {
            return movingPoints;
        } else if (x == 1 && y == 0 && isRiver[3]) {
            return movingPoints;
        } else if (x == 1 && y == -1 && isRiver[4]) {
            return movingPoints;
        } else if (x == 0 && y == -1 && isRiver[5]) {
            return movingPoints;
        }
        return this.movingPoint;
    }

    public void setMovingPoint(int movingPoint) {
        this.movingPoint = movingPoint;
    }

    public Colors getColor() {
        return this.color;
    }

    public void setColor(Colors color) {
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

    public boolean[] isRiver() {
        return this.isRiver;
    }

    public void setIsRiver(boolean[] isRiver) {
        this.isRiver = isRiver;
    }

    public int getRoadState() {
        return roadState;
    }

    public void setRoadState(int roadState) {
        this.roadState = roadState;
    }

    public int getRailRoadState() {
        return railRoadState;
    }

    public void setRailRoadState(int railRoadState) {
        this.railRoadState = railRoadState;
    }

    public Improvements getImprovement() {
        return this.improvement;
    }

    public void setImprovement(Improvements improvement) {
        this.improvement = improvement;
    }

    public int getImprovementTurnsLeftToBuild() {
        return improvementTurnsLeftToBuild;
    }

    public void setImprovementTurnsLeftToBuild(int improvementTurnsLeftToBuild) {
        this.improvementTurnsLeftToBuild = improvementTurnsLeftToBuild;
    }

    public int getPillageState() {
        return pillageState;
    }

    public void setPillageState(int pillageState) {
        this.pillageState = pillageState;
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
