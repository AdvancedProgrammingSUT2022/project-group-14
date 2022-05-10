package models;

import java.util.ArrayList;

import controllers.MapController;
import controllers.TileController;
import controllers.WorldController;
import enums.units.CombatUnit;
import models.units.Unit;

public class City {
    private Tile centerOfCity;

    private double food = 0;
    private double gold = 0;
    private double production = 0;

    private double growthFoodLimit = 1;

    ArrayList<Citizen> citizens = new ArrayList<>();

    private ArrayList<Building> buildings = new ArrayList<>();
    private ArrayList<Tile> territory = new ArrayList<>();


    private Unit currentUnit = null;
    private Building currentBuilding = null;
    private boolean payingGoldForCityProduction = false;
    private double currentProductionRemainingCost = 0;

    private double defenseStrength;
    private double attackStrength;
    private double healthPoint;

    private String name;

    public City(String name, int x, int y) {
        this.name = name;
        this.centerOfCity = MapController.getMap()[x][y];
        citizens.add(new Citizen(1));
        this.territory.add(centerOfCity);
        territory.addAll(TileController.getAvailableNeighbourTiles(centerOfCity.getX(), centerOfCity.getY()));
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        for (Tile tile : this.territory) {
            tile.setCivilization(currentCivilization.getName());
        }
    }

    public void finishCityProduction(){
        this.currentProductionRemainingCost = 0;
        if (currentBuilding != null)
            addBuildingToCity();
        else
            addUnitToCity();

    }

    public void addBuildingToCity(){
        buildings.add(currentBuilding);
        currentBuilding = null;
    }

    public void setCurrentUnit(Unit currentUnit) {
        this.currentUnit = currentUnit;
    }

    public void setCurrentBuilding(Building currentBuilding) {
        this.currentBuilding = currentBuilding;
    }

    public void addUnitToCity(){
        if (currentUnit instanceof CombatUnit){
            if (centerOfCity.getCombatUnit() == null){
                centerOfCity.setCombatUnit((models.units.CombatUnit) currentUnit);
                currentUnit = null;
            }
        }else {
            if (centerOfCity.getNonCombatUnit() == null){
                centerOfCity.setNonCombatUnit((models.units.NonCombatUnit) currentUnit);
                currentUnit = null;
            }
        }
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
        return centerOfCity;
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
        for (Citizen citizen : citizens) {
            if (citizen.isWorking())
                output.append(citizen.getId()).append("is working\n");
            else
                output.append(citizen.getId()).append("is not working\n");
        }

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
