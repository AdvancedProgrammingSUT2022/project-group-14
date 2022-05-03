package models;

import java.util.ArrayList;

import controllers.MapController;
import controllers.TileController;
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
    private int countdownUnit;

    private double defenseStrength;
    private double attackStrength;
    private double healthPoint;

    private String name;

    public City(int x, int y) {
        this.centerOfCity = MapController.getMap()[x][y];
        citizens.add(new Citizen(1));
        this.territory.add(centerOfCity);
        territory.addAll(TileController.getAvailableNeighbourTiles(centerOfCity.getX(), centerOfCity.getY()));
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
}
