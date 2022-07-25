package Client.models;

import Client.models.tiles.Coordination;
import Client.models.tiles.Tile;
import Client.models.units.*;

import java.util.ArrayList;

public class City {
    private final String name;
    private final Coordination centerCoordination;
    private String civilizationName;

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


    public City(String name, int x, int y, String civilizationName, ArrayList<Tile> tiles) {
        this.name = name;
        this.centerCoordination = new Coordination(x, y);
        this.civilizationName = civilizationName;
        citizens.add(new Citizen(1));
        this.territory.addAll(tiles);
        for (Tile tile : this.territory) {
            tile.setCivilization(civilizationName);
        }
        healthPoint = 20;
        defenseStrength = 10;
        attackStrength = 10;
    }

    public int numberOfWorkingCitizens() {
        int output = 0;
        for (Citizen citizen : this.citizens) {
            if (citizen.isWorking()) output++;
        }
        return output;
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

    public ArrayList<Building> getBuildings() {
        return buildings;
    }

    public Unit getCurrentUnit() {
        return currentUnit;
    }

    public Building getCurrentBuilding() {
        return currentBuilding;
    }

    public double getCurrentProductionRemainingCost() {
        return currentProductionRemainingCost;
    }

    public String getName() {
        return name;
    }

    public String unemployedCitizensData() {
        ArrayList<Citizen> unemployedCitizens = new ArrayList<>();
        for (Citizen citizen : this.getCitizens())
            if (!citizen.isWorking())
                unemployedCitizens.add(citizen);
        if (unemployedCitizens.size() == 0)
            return "there is no unemployed citizen in this city\n";
        StringBuilder output = new StringBuilder("unemployed citizens:\n");
        int counter = 1;
        for (Citizen unemployedCitizen : unemployedCitizens) {
            output.append(counter).append("- citizen with id ").append(unemployedCitizen.getId()).append('\n');
            counter++;
        }
        return output.toString();
    }

    public String employedCitizensData() {
        ArrayList<Citizen> employedCitizens = new ArrayList<>();
        for (Citizen citizen : this.getCitizens())
            if (citizen.isWorking())
                employedCitizens.add(citizen);
        if (employedCitizens.size() == 0)
            return "there is no employed citizen in this city\n";
        StringBuilder output = new StringBuilder("employed citizens:\n");
        int counter = 1;
        for (Citizen employedCitizen : employedCitizens) {
            output.append(counter).append("- citizen with id ").append(employedCitizen.getId());
            output.append(" is working on tile ").append(employedCitizen.getXOfWorkingTile() + 1).append(" and ");
            output.append(employedCitizen.getYOfWorkingTile() + 1).append('\n');
            counter++;
        }
        return output.toString();
    }
}
