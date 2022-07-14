package models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

import controllers.CityController;
import controllers.MapController;
import controllers.TileController;
import controllers.WorldController;
import enums.BuildingTypes;
import models.tiles.Coordination;
import models.tiles.Tile;
import models.units.*;

public class City {
    private final String name;
    private final String civilizationName;
    private final Coordination centerCoordination;

    private double food;
    private double gold;
    private double production;
    private double growthFoodLimit = 1;

    private final ArrayList<Citizen> citizens = new ArrayList<>();
    private final ArrayList<Building> buildings = new ArrayList<>();
    private final ArrayList<Tile> territory = new ArrayList<>();

    private Unit currentUnit;
    private Building currentBuilding;
    private boolean payingGoldForCityProduction;
    private double currentProductionRemainingCost;

    private double defenseStrength;
    private final double attackStrength;
    private double healthPoint;

    private int numberOfGarrisonedUnit;


    public City(String name, int x, int y) {
        this.name = name;
        this.civilizationName = MapController.getTileByCoordinates(x, y).getCivilizationName();
        this.centerCoordination = new Coordination(x, y);
        citizens.add(new Citizen(1));
        this.territory.add(MapController.getTileByCoordinates(x, y));
        territory.addAll(TileController.getAvailableNeighbourTiles(x, y));
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        for (Tile tile : this.territory) {
            tile.setCivilization(currentCivilization.getName());
        }
        healthPoint = 20;
        defenseStrength = 10;
        attackStrength = 10;
    }

    public void finishCityProduction() {
        this.currentProductionRemainingCost = 0;
        if (currentBuilding != null) {
            addBuildingToCity();
            this.defenseStrength += currentBuilding.getBuildingType().getDefense();
        } else
            addUnitToCity();

    }

    public void addBuildingToCity() {
        buildings.add(currentBuilding);
        currentBuilding = null;
    }

    public void setCurrentUnit(Unit currentUnit) {
        this.currentUnit = currentUnit;
    }

    public void setCurrentBuilding(Building currentBuilding) {
        this.currentBuilding = currentBuilding;
    }

    public void addUnitToCity() {
        if (currentUnit instanceof CombatUnit) {
            if (MapController.getTileByCoordinates(centerCoordination).getCombatUnit() == null) {
                MapController.getTileByCoordinates(centerCoordination).setCombatUnit((models.units.CombatUnit) currentUnit);
                if (currentUnit instanceof Melee)
                    WorldController.getWorld().getCivilizationByName(civilizationName).addMeleeUnit((Melee) currentUnit);
                else
                    WorldController.getWorld().getCivilizationByName(civilizationName).addRangedUnit((Ranged) currentUnit);
                currentUnit = null;
            }
        } else {
            if (MapController.getTileByCoordinates(centerCoordination).getNonCombatUnit() == null) {
                MapController.getTileByCoordinates(centerCoordination).setNonCombatUnit((models.units.NonCombatUnit) currentUnit);
                if (currentUnit instanceof Settler)
                    WorldController.getWorld().getCivilizationByName(civilizationName).addSettler((Settler) currentUnit);
                else
                    WorldController.getWorld().getCivilizationByName(civilizationName).addWorker((Worker) currentUnit);
                currentUnit = null;
            }
        }
    }

    public boolean cityHasRequiredBuildings(HashSet<String> buildings) {
        Iterator<String> iterator = buildings.iterator();
        while (iterator.hasNext()) {
            BuildingTypes buildingTypes = BuildingTypes.getBuildingByName(iterator.next());
            boolean cityHasBuilding = false;
            for (int i = 0; i < this.buildings.size(); i++) {
                if (this.buildings.get(i).getBuildingType().equals(buildingTypes)) {
                    cityHasBuilding = true;
                    break;
                }
            }
            if (!cityHasBuilding) return false;
        }
        return true;
    }

    public void addGarrisonedUnits() {
        this.numberOfGarrisonedUnit++;
    }

    public void removeGarrisonedUnits() {
        this.numberOfGarrisonedUnit--;
    }

    public void setFood(double food) {
        this.food = food;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public void setProduction(double production) {
        this.production = production;
    }

    public void setGrowthFoodLimit(double growthFoodLimit) {
        this.growthFoodLimit = growthFoodLimit;
    }

    public void setPayingGoldForCityProduction(boolean payingGoldForCityProduction) {
        this.payingGoldForCityProduction = payingGoldForCityProduction;
    }

    public void setCurrentProductionRemainingCost(double currentProductionRemainingCost) {
        this.currentProductionRemainingCost = currentProductionRemainingCost;
    }

    public double getFood() {
        return food;
    }

    public double getGold() {
        return gold;
    }

    public double getProduction() {
        return production;
    }

    public ArrayList<Citizen> getCitizens() {
        return citizens;
    }

    public ArrayList<Tile> getTerritory() {
        return territory;
    }

    public double getGrowthFoodLimit() {
        return growthFoodLimit;
    }

    public Tile getCenterOfCity() {
        return MapController.getTileByCoordinates(centerCoordination);
    }

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public Unit getCurrentUnit() {
        return currentUnit;
    }

    public Building getCurrentBuilding() {
        return currentBuilding;
    }

    public boolean isPayingGoldForCityProduction() {
        return payingGoldForCityProduction;
    }

    public double getCurrentProductionRemainingCost() {
        return currentProductionRemainingCost;
    }

    public void receiveDamage(double amount) {
        this.healthPoint -= amount;
    }

    public double getDefenseStrength() {
        return defenseStrength;
    }

    public double getAttackStrength() {
        return attackStrength;
    }

    public double getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(double healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getNumberOfGarrisonedUnit() {
        return numberOfGarrisonedUnit;
    }

    public String getName() {
        return name;
    }

    public String getInfo() {
        StringBuilder output = new StringBuilder("Name : " + name + "\n" +
                "Food : " + food + "\n" +
                "Gold : " + gold + "\n" +
                "Production : " + production + "\n" +
                "GrowthFoodLimit : " + growthFoodLimit + "\n" +
                "NumberOfTiles : " + territory.size() + "\n" +
                "NumberOFBuildings : " + buildings.size() + "\n" +
                "NumberOfCitizens : " + citizens.size() + "\n" +
                "Citizens info : \n");
        output.append(CityController.employedCitizensData(this)).append(CityController.unemployedCitizensData(this));

        if (this.getCurrentUnit() != null)
            output.append("current production : ").append(getCurrentUnit().getName()).append("\n");
        output.append("CurrentProductionRemainingCost : ").append(currentProductionRemainingCost).append("\n");

        return output.toString();
    }

    public String getCombatInfo() {
        return "NumberOfTiles : " + territory.size() + "\n" +
                "DefenseStrength : " + defenseStrength + "\n" +
                "AttackStrength : " + attackStrength + "\n" +
                "HealthPoint : " + healthPoint + "\n";
    }

}
