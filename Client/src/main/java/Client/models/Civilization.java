package Client.models;

import Client.enums.Technologies;
import Client.models.units.*;
import Server.controllers.MapController;
import Server.controllers.TileController;
import Server.controllers.WorldController;
import Server.enums.Technologies;
import Server.enums.resources.LuxuryResourceTypes;
import Server.enums.resources.StrategicResourceTypes;
import Server.enums.units.UnitTypes;
import Server.models.tiles.Tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Civilization {
    private final String name;
    private final int[][] visionStatesOfMap = new int[MapController.getWidth()][MapController.getHeight()];
    private final Tile[][] revealedTiles = new Tile[MapController.getWidth()][MapController.getHeight()];

    private final ArrayList<Melee> melees = new ArrayList<>();
    private final ArrayList<Ranged> ranges = new ArrayList<>();
    private final ArrayList<Worker> workers = new ArrayList<>();
    private final ArrayList<Settler> settlers = new ArrayList<>();

    private final ArrayList<City> cities = new ArrayList<>();
    private final ArrayList<String> citiesNames = new ArrayList<>();
    private final HashMap<String, Integer> strategicResources;
    private final HashMap<String, Integer> luxuryResources;
    private City firstCapital;
    private City currentCapital;

    private final HashMap<Technologies, Integer> technologies;
    private Technologies currentTechnology;

    private double gold, happiness, science;

    private final ArrayList<String> notifications = new ArrayList<>();

    public Civilization(String name, int i) {
        this.name = name;
        int randomX, randomY;
        do {
            randomX = new Random().nextInt(2, MapController.width - 2);
            randomY = new Random().nextInt(2, MapController.height - 2);
        } while (MapController.getMap()[randomX][randomY].getType().getMovementPoint() == 9999);
        if (i == 0 && WorldController.getCheatCoordination() != null
                && TileController.selectedTileIsValid(WorldController.getCheatCoordination().getX(), WorldController.getCheatCoordination().getY())) {
            randomX = WorldController.getCheatCoordination().getX();
            randomY = WorldController.getCheatCoordination().getY();
        }
        melees.add(new Melee(UnitTypes.WARRIOR, randomX, randomY, name));
        settlers.add(new Settler(UnitTypes.SETTLER, randomX, randomY, name));
        this.happiness = 10;
        citiesNames.add(name + "1");
        citiesNames.add(name + "2");
        citiesNames.add(name + "3");

        technologies = Technologies.getAllTechnologies();
        strategicResources = StrategicResourceTypes.getAllResources();
        luxuryResources = LuxuryResourceTypes.getAllResources();
    }

    public String getName() {
        return this.name;
    }

    public int[][] getVisionStatesOfMap() {
        return this.visionStatesOfMap;
    }

    public Tile[][] getRevealedTiles() {
        return this.revealedTiles;
    }


    public ArrayList<Unit> getAllUnits() {
        ArrayList<Unit> allUnits = new ArrayList<>();
        allUnits.addAll(this.melees);
        allUnits.addAll(this.ranges);
        allUnits.addAll(this.workers);
        allUnits.addAll(this.settlers);
        return allUnits;
    }

    public ArrayList<Melee> getMelees() {
        return melees;
    }

    public ArrayList<Ranged> getRanges() {
        return ranges;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }

    public ArrayList<Settler> getSettlers() {
        return settlers;
    }

    public ArrayList<City> getCities() {
        return this.cities;
    }

    public City getCurrentCapital() {
        return currentCapital;
    }

    public City getFirstCapital() {
        return firstCapital;
    }

    public void addCity(City city) {
        if (this.cities.size() == 0 && this.firstCapital == null) {
            this.firstCapital = city;
            this.currentCapital = city;
        }
        this.cities.add(city);
        this.happiness -= 2;
    }

    public void removeCity(City city) {
        if (this.cities.size() == 1) {
            WorldController.getWorld().removeCivilization(this);
        } else {
            this.cities.remove(city);
            this.currentCapital = this.cities.get(0);
            this.happiness += 2;
        }
    }

    public String getCityName() {
        String name = this.citiesNames.get(0);
        this.citiesNames.remove(0);
        return name;
    }

    public HashMap<String, Integer> getStrategicResources() {
        return strategicResources;
    }

    public HashMap<String, Integer> getLuxuryResources() {
        return luxuryResources;
    }

    public HashMap<Technologies, Integer> getTechnologies() {
        return technologies;
    }

    public Technologies getCurrentTechnology() {
        return currentTechnology;
    }

    public void setCurrentTechnology(Technologies wantedTechnology) {
        this.currentTechnology = wantedTechnology;
    }

    public double getGold() {
        return this.gold;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public double getScience() {
        return science;
    }

    public void setScience(double science) {
        this.science = science;
    }

    public double getHappiness() {
        return happiness;
    }

    public void setHappiness(double happiness) {
        this.happiness = happiness;
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public void addNotification(String notification) {
        notifications.add(notification);
    }

    public String getInfo() {
        int totalTiles = 0, totalTechnologiesAcquired = 0;
        for (City city : cities) {
            totalTiles += city.getTerritory().size();
        }
        for (Integer value : technologies.values()) {
            if (value <= 0)
                totalTechnologiesAcquired++;
        }
        return "Total tiles in map : " + totalTiles + "\n" +
                "Gold : " + gold + "\n" +
                "Happiness : " + happiness + "\n" +
                "Science : " + science + "\n" +
                "Total number of units : " + getAllUnits().size() + "\n" +
                "Total number of cities : " + cities.size() + "\n" +
                "Total Technologies Acquired : " + totalTechnologiesAcquired + "\n";
    }
}
