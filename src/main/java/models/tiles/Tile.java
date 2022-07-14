package models.tiles;

import controllers.MapController;
import controllers.TileController;
import controllers.WorldController;
import enums.Improvements;
import enums.resources.BonusResourceTypes;
import enums.resources.LuxuryResourceTypes;
import enums.tiles.TileBaseTypes;
import enums.tiles.TileFeatureTypes;
import models.City;
import models.Civilization;
import models.resources.Resource;
import models.resources.StrategicResource;
import models.units.CombatUnit;
import models.units.NonCombatUnit;

import java.util.Random;

public class Tile {
    private final Hex hex;
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
    private int improvementTurnsLeftToBuild; // 9999 -> has not been started to build | 0 -> has been build

    private final boolean[] isRiver;
    private int roadState; // 9999 -> has not been started to build | 0 -> has been build
    private int railRoadState; // 9999 -> has not been started to build | 0 -> has been build

    private int pillageState; // 0 -> has not been pillaged | 9999 -> been pillaged

    private String civilizationName;
    private City city;
    private CombatUnit combatUnit;
    private NonCombatUnit nonCombatUnit;

    public Tile(TileFeatureTypes feature, TileBaseTypes type, int x, int y) {
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
        this.hex = new Hex(this);
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
        if (x == 0 || x == MapController.getHeight()-1 || (y == 0 && x % 2 == 0) || (y == MapController.getWidth()-1 && x % 2 == 1)){
            baseType = TileBaseTypes.OCEAN;
            featureType = TileFeatureTypes.NULL;
        }
        return new Tile(featureType, baseType, x, y);
    }

    public void addAvailableResourcesToCivilizationAndTile() {
        if (this.resource != null &&
                this.resource.getType() instanceof BonusResourceTypes &&
                !this.resource.hasBeenUsed() && TileController.resourceIsAvailableToBeUsed(this.resource, this)) {
            addResourceToCivilizationAndTile(this.resource);
            this.resource.setHasBeenUsed(true);
        }
        if (this.resource != null &&
                this.resource.getType() instanceof LuxuryResourceTypes &&
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

        if (resource.getType() instanceof LuxuryResourceTypes) {
            currenCivilization.getLuxuryResources().put(resource.getName(), currenCivilization.getLuxuryResources().get(resource.getName()) + 1);
            currenCivilization.setHappiness(currenCivilization.getHappiness() + 4);
        } else if (resource instanceof StrategicResource) {
            currenCivilization.getStrategicResources().put(resource.getName(), currenCivilization.getStrategicResources().get(resource.getName()) + 1);
        }
    }

    public boolean hasRiver(){
        for (int i = 0; i < this.isRiver.length; i++) {
            if (this.isRiver[i]) return true;
        }
        return false;
    }


    // setters and getters
    public int getX() {
        return this.coordination.getX();
    }

    public void setX(int x) {
        this.coordination.setX(x);
    }

    public int getY() {
        return this.coordination.getY();
    }

    public void setY(int y) {
        this.coordination.setY(y);
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
        if (x == -1 && y == 0 && isRiver[0]) {
            return movingPoints;
        } else if ((this.coordination.getY() % 2 == 0 && x == -1 && y == 1 && isRiver[1]) || (this.coordination.getY() % 2 == 1 && x == 0 && y == 1 && isRiver[1])) {
            return movingPoints;
        } else if ((this.coordination.getY() % 2 == 0 && x == 0 && y == 1 && isRiver[2]) || (this.coordination.getY() % 2 == 1 && x == 1 && y == 1 && isRiver[2])) {
            return movingPoints;
        } else if (x == 1 && y == 0 && isRiver[3]) {
            return movingPoints;
        } else if ((this.coordination.getY() % 2 == 0 && x == 0 && y == -1 && isRiver[4]) || (this.coordination.getY() % 2 == 1 && x == 1 && y == -1 && isRiver[4])) {
            return movingPoints;
        } else if ((this.coordination.getY() % 2 == 0 && x == -1 && y == -1 && isRiver[5]) || (this.coordination.getY() % 2 == 1 && x == 0 && y == -1 && isRiver[5])) {
            return movingPoints;
        }
        return this.movingPoint;
    }

    public TileFeatureTypes getFeature() {
        return this.feature;
    }

    public void setFeature(TileFeatureTypes feature) {
        if (feature == null) {
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
        if (this.roadState == 0 && this.movingPoint != 9999)
            this.movingPoint *= 2 / 3;
    }

    public int getRailRoadState() {
        return railRoadState;

    }

    public void setRailRoadState(int railRoadState) {
        this.railRoadState = railRoadState;
        if (this.railRoadState == 0 && this.movingPoint != 9999)
            this.movingPoint *= 1 / 2;
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
        if (this.pillageState == 9999 && this.resource != null) {
            this.food -= this.resource.getFood();
            this.gold -= this.resource.getGold();
            this.production -= this.resource.getProduction();
            this.movingPoint *= 3 / 2;
        } else if (this.pillageState == 0 && this.resource != null) {
            this.food += this.resource.getFood();
            this.gold += this.resource.getGold();
            this.production += this.resource.getProduction();
            this.movingPoint /= 3 / 2;
        }
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

    public Hex getHex() {
        return hex;
    }

    public String getInfo() {
        return "Food : " + food + "\n" +
                "Production : " + production + "\n" +
                "Gold : " + gold + "\n" +
                "CombatImpact : " + combatImpact + "\n" +
                "MovementPoint : " + (movingPoint != 9999 ? movingPoint : "N/A") + "\n" +
                "BaseType : " + type.getName() + "\n" +
                "Feature : " + (feature != null ? feature.getName() : "N/A") + "\n" +
                "Owner : " + (civilizationName != null ? civilizationName : "N/A") + "\n" +
                "Improvement : " + (improvement != null ? improvement.getName() : "N/A") + "\n";
    }
}
