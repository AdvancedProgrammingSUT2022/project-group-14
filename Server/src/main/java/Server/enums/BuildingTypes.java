package Server.enums;

import Server.enums.resources.StrategicResourceTypes;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

import static java.util.List.of;

public enum BuildingTypes {
    BARRACKS("barracks", 80, 1, Technologies.BRONZE_WORKING, null, null, 0, 0, 0, false, 0, 0),
    GRANARY("granary", 100, 1, Technologies.POTTERY, null, null, 0, 2, 0, false, 0, 0),
    LIBRARY("library", 80, 1, Technologies.WRITING, null, null, 0, 0, 0, false, 0, 0),
    MONUMENT("monument", 60, 1, null, null, null, 0, 0, 0, false, 0, 0),
    WALLS("walls", 100, 1, Technologies.MASONRY, null, null, 5, 0, 0, false, 0, 0),
    WATER_MILL("water_mill", 120, 2, Technologies.THE_WHEEL, null, null, 0, 2, 0, true, 0, 0),
    ARMORY("armory", 130, 3, Technologies.IRON_WORKING, new HashSet<>(of("BARRACKS")), null, 0, 0, 0, false, 0, 0),
    BURIAL_TOMB("burial_tomb", 120, 0, Technologies.PHILOSOPHY, null, null, 0, 0, 2, false, 0, 0),
    CIRCUS("circus", 150, 3, Technologies.HORSEBACK_RIDING, null, StrategicResourceTypes.HORSE, 0, 0, 3, false, 0, 0),
    COLOSSEUM("colosseum", 150, 3, Technologies.CONSTRUCTION, null, null, 0, 0, 4, false, 0, 0),
    COURTHOUSE("courthouse", 200, 5, Technologies.MATHEMATICS, null, null, 0, 0, 0, false, 0, 0),
    STABLE("stable", 100, 1, Technologies.HORSEBACK_RIDING, null, StrategicResourceTypes.HORSE, 0, 0, 0, false, 0, 0),
    TEMPLE("temple", 120, 2, Technologies.PHILOSOPHY, new HashSet<>(of("MONUMENT")), null, 0, 0, 0, false, 0, 0),
    CASTLE("castle", 200, 3, Technologies.CHIVALRY, new HashSet<>(of("WALLS")), null, 7.5, 0, 0, false, 0, 0),
    FORGE("forge", 150, 2, Technologies.METAL_CASTING, null, StrategicResourceTypes.IRON, 0, 0, 0, false, 0, 0),
    GARDEN("garden", 120, 2, Technologies.THEOLOGY, null, null, 0, 0, 0, true, 0, 0),
    MARKET("market", 120, 0, Technologies.CURRENCY, null, null, 0, 0, 0, false, 0, 25),
    MINT("mint", 120, 0, Technologies.CURRENCY, null, null, 0, 0, 0, false, 0, 0),
    MONASTERY("monastery", 120, 2, Technologies.THEOLOGY, null, null, 0, 0, 0, false, 0, 0),
    UNIVERSITY("university", 200, 3, Technologies.EDUCATION, new HashSet<>(of("LIBRARY")), null, 0, 0, 0, false, 0, 0),
    WORKSHOP("workshop", 100, 2, Technologies.METAL_CASTING, null, null, 0, 0, 0, false, 0, 0),
    BANK("bank", 220, 0, Technologies.BANKING, new HashSet<>(of("MARKET")), null, 0, 0, 0, false, 0, 25),
    MILITARY_ACADEMY("military_academy", 350, 3, Technologies.MILITARY_SCIENCE, new HashSet<>(of("BARRACKS")), null, 0, 0, 0, false, 0, 0),
    MUSEUM("museum", 350, 3, Technologies.ARCHAEOLOGY, new HashSet<>(of("OPERA_HOUSE")), null, 0, 0, 0, false, 0, 0),
    OPERA_HOUSE("opera_house", 220, 3, Technologies.ACOUSTICS, new HashSet<>(Arrays.asList("TEMPLE", "BURIAL_TOMB")), null, 0, 0, 0, false, 0, 0),
    PUBLIC_SCHOOL("public_school", 350, 3, Technologies.SCIENTIFIC_THEORY, new HashSet<>(of("UNIVERSITY")), null, 0, 0, 0, false, 0, 0),
    SATRAPS_COURT("satrap's_court", 220, 0, Technologies.BANKING, new HashSet<>(of("MARKET")), null, 0, 0, 2, false, 0, 25),
    THEATER("theater", 300, 5, Technologies.PRINTING_PRESS, new HashSet<>(of("COLOSSEUM")), null, 0, 0, 4, false, 0, 0),
    WINDMILL("windmill", 180, 2, Technologies.ECONOMICS, null, null, 0, 0, 0, false, 15, 0),
    ARSENAL("arsenal", 350, 3, Technologies.RAILROAD, new HashSet<>(of("MILITARY_ACADEMY")), null, 0, 0, 0, false, 0, 0),
    BROADCAST_TOWER("broadcast_tower", 600, 3, Technologies.RADIO, new HashSet<>(of("MUSEUM")), null, 0, 0, 0, false, 0, 0),
    FACTORY("factory", 300, 3, Technologies.STEAM_POWER, null, StrategicResourceTypes.COAL, 0, 0, 0, false, 50, 0),
    HOSPITAL("hospital", 400, 2, Technologies.BIOLOGY, null, null, 0, 0, 0, false, 0, 0),
    MILITARY_BASE("military_base", 450, 4, Technologies.TELEGRAPH, new HashSet<>(of("CASTLE")), null, 12, 0, 0, false, 0, 0),
    STOCK_EXCHANGE("stock_exchange", 650, 0, Technologies.ELECTRICITY, new HashSet<>(of("BANK")), null, 0, 0, 0, false, 0, 33);

    private final String name;
    private final int cost;
    private final int maintenance;
    private final Technologies requiredTechnology;
    private final HashSet<String> requiredBuildings;
    private final StrategicResourceTypes requiredResource;
    private final double defense;
    private final double food;
    private final double happiness;
    private final boolean requiresRiver;
    private final int percentOfProduction;
    private final int percentOfGold;


    public HashSet<String> getRequiredBuildings() {
        return requiredBuildings;
    }

    public StrategicResourceTypes getRequiredResource() {
        return requiredResource;
    }

    public double getDefense() {
        return defense;
    }

    public double getFood() {
        return food;
    }

    public double getHappiness() {
        return happiness;
    }

    public boolean isRequiresRiver() {
        return requiresRiver;
    }

    public int getPercentOfProduction() {
        return percentOfProduction;
    }

    public int getPercentOfGold() {
        return percentOfGold;
    }


    BuildingTypes(String name, int cost, int maintenance, Technologies requiredTechnology, HashSet<String> requiredBuildings, StrategicResourceTypes requiredResource, double defense, double food, double happiness, boolean requiresRiver, int percentOfProduction, int percentOfGold) {
        this.name = name;
        this.cost = cost;
        this.maintenance = maintenance;
        this.requiredTechnology = requiredTechnology;
        this.requiredBuildings = requiredBuildings;
        this.requiredResource = requiredResource;
        this.defense = defense;
        this.food = food;
        this.happiness = happiness;
        this.requiresRiver = requiresRiver;
        this.percentOfProduction = percentOfProduction;
        this.percentOfGold = percentOfGold;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getMaintenance() {
        return maintenance;
    }

    public Technologies getRequiredTechnology() {
        return requiredTechnology;
    }

    public static BuildingTypes getBuildingByName(String name) {
        return BuildingTypes.valueOf(name.toUpperCase(Locale.ROOT));
    }

    public String getInfo() {
        return "Name : " + name + "\n" +
                "Cost : " + cost + "\n" +
                "Maintenance : " + maintenance + "\n" +
                "RequiredTechnology : " + (requiredTechnology != null ? requiredTechnology.getName() : "nothing") + "\n" +
                "RequiredResource : " + (requiredResource != null ? requiredResource.getName() : "nothing") + "\n" +
                "Defense : " + defense + "\n" +
                "Food : " + food + "\n" +
                "Happiness : " + happiness + "\n" +
                "RequiresRiver : " + requiresRiver + "\n" +
                "PercentOfProduction : " + percentOfProduction + "\n" +
                "PercentOfGold : " + percentOfGold;
    }
}
