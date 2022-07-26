package Server.models;

import Server.controllers.CivilizationController;
import Server.controllers.MapController;
import Server.controllers.TileController;
import Server.controllers.WorldController;
import Server.enums.QueryResponses;
import Server.enums.Technologies;
import Server.enums.resources.LuxuryResourceTypes;
import Server.enums.resources.StrategicResourceTypes;
import Server.enums.units.UnitTypes;
import Server.models.tiles.Tile;
import Server.models.units.*;

import java.util.*;

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
    private ArrayList<String> enemies = new ArrayList<>();
    private ArrayList<Trade> tradesFromOtherCivilizations = new ArrayList<>();

    public Civilization(String name, int i) {
        this.name = name;
        int randomX, randomY;
        mainLoop :
        do {
            randomX = new Random().nextInt(2, MapController.width - 2);
            randomY = new Random().nextInt(2, MapController.height - 2);
            for (int j = 0; j < i - 1; j++) {
                for (Unit unit : WorldController.getWorld().getAllCivilizations().get(j).getAllUnits()) {
                    if (TileController.coordinatesAreInRange(unit.getCurrentX(), unit.getCurrentY(), randomX, randomY, 1))
                        continue mainLoop;
                }
            }
        } while (MapController.getMap()[randomX][randomY].getType().getMovementPoint() == 9999);
        if (i == 0 && WorldController.getCheatCoordination() != null
                && TileController.selectedTileIsValid(WorldController.getCheatCoordination().getX(), WorldController.getCheatCoordination().getY())) {
            randomX = WorldController.getCheatCoordination().getX();
            randomY = WorldController.getCheatCoordination().getY();
        }
        melees.add(new Melee(UnitTypes.WARRIOR, randomX, randomY, name));
        settlers.add(new Settler(UnitTypes.SETTLER, randomX, randomY, name));
//        workers.add(new Worker(UnitTypes.WORKER, randomX, randomY, name));
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

    public ArrayList<String> getEnemies() {
        return enemies;
    }

    public void addEnemy(String enemyName) {
        enemies.add(enemyName);
    }

    public void removeEnemy(String enemyName) {
        try {
            enemies.remove(enemyName);
        } catch (Exception e) {
            System.out.println("this civilization isn't your enemy");
        }
    }

    public ArrayList<Trade> getTradesFromOtherCivilizations() {
        return tradesFromOtherCivilizations;
    }

    public QueryResponses acceptTrade(int indexOfTrade) {
        Trade trade = tradesFromOtherCivilizations.get(indexOfTrade);
        Civilization offeringCivilization = WorldController.getWorld().getCivilizationByName(trade.getOfferingCivilization());
        if (trade.getRequestedGold() > this.gold) {
            return QueryResponses.YOU_NOT_ENOUGH_GOLD;
        } else if (trade.getRequestedStrategicResource() != null && this.getStrategicResources().get(trade.getRequestedStrategicResource()) < 1) {
            return QueryResponses.YOU_LACK_STRATEGIC_RESOURCE;
        } else if (trade.getRequestedLuxuryResource() != null && this.getLuxuryResources().get(trade.getRequestedLuxuryResource()) < 1) {
            return QueryResponses.YOU_LACK_LUXURY_RESOURCE;
        } else if (trade.getOfferedGold() > offeringCivilization.getGold()) {
            return QueryResponses.OTHER_CIVILIZATION_NOT_ENOUGH_GOLD;
        } else if (trade.getOfferedStrategicResource() != null && offeringCivilization.getStrategicResources().get(trade.getOfferedStrategicResource()) < 1) {
            return QueryResponses.OTHER_CIVILIZATION_LACK_STRATEGIC_RESOURCE;
        } else if (trade.getOfferedLuxuryResource() != null && offeringCivilization.getLuxuryResources().get(trade.getOfferedLuxuryResource()) < 1) {
            return QueryResponses.OTHER_CIVILIZATION_LACK_LUXURY_RESOURCE;
        } else {
            this.gold -= trade.getRequestedGold() + trade.getOfferedGold();
            if (trade.getRequestedLuxuryResource() != null) {
                this.getLuxuryResources().put(trade.getRequestedLuxuryResource(), this.getLuxuryResources().get(trade.getRequestedLuxuryResource()) - 1);
                offeringCivilization.getLuxuryResources().put(trade.getRequestedLuxuryResource(), offeringCivilization.getLuxuryResources().get(trade.getRequestedLuxuryResource()) + 1);
                if (offeringCivilization.getLuxuryResources().get(trade.getRequestedLuxuryResource()) == 1)
                    offeringCivilization.setHappiness(offeringCivilization.getHappiness() + 4);
            }
            if (trade.getRequestedStrategicResource() != null) {
                this.getStrategicResources().put(trade.getRequestedStrategicResource(), this.getStrategicResources().get(trade.getRequestedStrategicResource()) - 1);
                offeringCivilization.getStrategicResources().put(trade.getRequestedStrategicResource(), offeringCivilization.getStrategicResources().get(trade.getRequestedStrategicResource()) + 1);
            }
            offeringCivilization.setGold(offeringCivilization.getGold() - trade.getOfferedGold() + trade.getRequestedGold());
            if (trade.getOfferedLuxuryResource() != null) {
                offeringCivilization.getLuxuryResources().put(trade.getOfferedLuxuryResource(), offeringCivilization.getLuxuryResources().get(trade.getOfferedLuxuryResource()) - 1);
                this.getLuxuryResources().put(trade.getOfferedLuxuryResource(), this.getLuxuryResources().get(trade.getOfferedLuxuryResource()) + 1);
                if (this.getLuxuryResources().get(trade.getOfferedLuxuryResource()) == 1)
                    this.happiness += 4;
            }
            if (trade.getOfferedStrategicResource() != null) {
                offeringCivilization.getStrategicResources().put(trade.getOfferedStrategicResource(), offeringCivilization.getStrategicResources().get(trade.getOfferedStrategicResource()) - 1);
                this.getStrategicResources().put(trade.getOfferedStrategicResource(), this.getStrategicResources().get(trade.getOfferedStrategicResource()) + 1);
            }

            CivilizationController.addNotification(
                    "In turn " + WorldController.getWorld().getActualTurn()
                            + " you accepted a trade from " + offeringCivilization.getName(),
                    this.name);
            CivilizationController.addNotification(
                    "In turn " + WorldController.getWorld().getActualTurn()
                            + " your trade was accepted by " + this.name,
                    trade.getOfferingCivilization());
            return QueryResponses.OK;
        }

    }

    public void addTrade(Trade trade) {
        tradesFromOtherCivilizations.add(trade);
        CivilizationController.addNotification(
                "In turn " + WorldController.getWorld().getActualTurn()
                        + " a trade was offered to you from " + trade.getOfferingCivilization(),
                this.name);
    }

    public City getCityByName(String name) {
        for (City city : this.cities) {
            if (city.getName().equals(name)) return city;
        }
        return null;
    }
}
