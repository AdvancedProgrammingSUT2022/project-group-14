package models;

import controllers.TileController;
import controllers.WorldController;
import enums.Colors;
import enums.Improvements;
import enums.tiles.TileBaseTypes;
import enums.tiles.TileFeatureTypes;
import models.resources.BonusResource;
import models.resources.LuxuryResource;
import models.resources.Resource;
import models.resources.StrategicResource;
import models.units.CombatUnit;
import models.units.NonCombatUnit;

import java.util.Random;

public class Tile {
    private int x;
    private int y;

    private TileBaseTypes type;
    private TileFeatureTypes feature;
    public final String name;
    private Colors color;

    private double food;
    private double production;
    private double gold;

    private int combatImpact;
    private int movingPoint;

//    private StrategicResource strategicResource;
//    private LuxuryResource luxuryResource;
//    private BonusResource bonusResource;
    private Resource resource;

    private Improvements improvement;
    private int improvementTurnsLeftToBuild; // 9999 -> has not been started to build | 0 -> has been build

    private boolean[] isRiver;
    private int roadState; // 9999 -> has not been started to build | 0 -> has been build
    private int railRoadState; // 9999 -> has not been started to build | 0 -> has been build

    private int pillageState; // 0 -> has not been pillaged | 9999 -> been pillaged

    private String civilizationName = null;
    private City city;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;

    public Tile(TileFeatureTypes feature, TileBaseTypes type, int x, int y) {
        this.x = x;
        this.y = y;
        this.feature = feature;
        this.type = type;
        this.color = type.getColor();
        this.food = type.getFood() + feature.getFood();
        this.production = type.getProduction() + feature.getProduction();
        this.gold = type.getGold() + feature.getGold();
        this.combatImpact = type.getCombatImpact() + feature.getCombatImpact();
        this.movingPoint = type.getMovingPoint() + feature.getMovingPoint();
        this.improvementTurnsLeftToBuild = 9999;
        this.roadState = 9999;
        this.railRoadState = 9999;
        this.isRiver = new boolean[6];
        for (int i = 0; i < 6; i++)
            this.isRiver[i] = false;
        if (feature != TileFeatureTypes.NULL)
            this.name = feature.getName();
        else
            this.name = type.getName();
        this.resource = Resource.generateRandomResource(type,feature);
    }

    //randomTile generation
    public static TileFeatureTypes generateRandomFeature(TileBaseTypes type) {
        int featuresNumber = type.getPossibleFeatures().size();
        TileFeatureTypes[] possibleFeatures = type.getPossibleFeatures().toArray(new TileFeatureTypes[featuresNumber]);
        Random rand = new Random();
        int randomInt = rand.nextInt(featuresNumber + 2);
        if (randomInt >= featuresNumber)
            return TileFeatureTypes.NULL;
        else
            return possibleFeatures[randomInt];
    }

    public static Tile generateRandomTile(int x, int y) {
        TileBaseTypes baseType = TileBaseTypes.generateRandom();
        TileFeatureTypes featureType = generateRandomFeature(baseType);
        return new Tile(featureType, baseType, x, y);
    }

    public void addAvailableResourcesToCivilizationAndTile() {
        if (this.resource != null &&
                this.resource instanceof BonusResource &&
                !this.resource.hasBeenUsed() && TileController.resourceIsAvailableToBeUsed(this.resource, this)) {
            addResourceToCivilizationAndTile(this.resource);
            this.resource.setHasBeenUsed(true);
        }
        if (this.resource != null &&
                this.resource instanceof LuxuryResource &&
                !this.resource.hasBeenUsed() && TileController.resourceIsAvailableToBeUsed(this.resource, this)) {
            addResourceToCivilizationAndTile(this.resource);
            this.resource.setHasBeenUsed(true);
        }
        if (this.resource != null &&
                this.resource instanceof StrategicResource &&
                !this.resource.hasBeenUsed() && TileController.resourceIsAvailableToBeUsed(this.resource, this)) {
            addResourceToCivilizationAndTile(this.resource);
            this.resource.setHasBeenUsed(true);
        }
    }

    public void addResourceToCivilizationAndTile(Resource resource) {
        this.food += resource.getFood();
        this.gold += resource.getGold();
        this.production += resource.getProduction();
        Civilization currenCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());

        if (resource instanceof LuxuryResource) {
            currenCivilization.getLuxuryResources().put(resource.getName(), currenCivilization.getLuxuryResources().get(resource.getName()) + 1);
            currenCivilization.setHappiness(currenCivilization.getHappiness() + 4);
        } else if (resource instanceof StrategicResource) {
            currenCivilization.getStrategicResources().put(resource.getName(), currenCivilization.getStrategicResources().get(resource.getName()) + 1);
        }
    }


    // setters and getters
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

    public void setMovingPoint(int movingPoint) {
        this.movingPoint = movingPoint;
    }

    public int getMovingPointFromSide(int x, int y, int movingPoints) {
        //TODO this returns wrong answer
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
        if (feature == null){
            this.food -= this.feature.getFood();
            this.production -= this.feature.getProduction();
            this.gold -= this.feature.getGold();
            this.movingPoint -= this.feature.getMovementPoint();
            this.combatImpact -= this.feature.getCombatImpact();
        }
        this.feature = feature;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public boolean[] getIsRiver() {
        return this.isRiver;
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
