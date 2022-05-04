package enums;

public enum Buildings {
    BARRACKS("barracks", 80 , 1 , Technologies.BRONZE_WORKING),
    GRANARY("granary" , 100 , 1 , Technologies.POTTERY),
    LIBRARY("library", 80 , 1 , Technologies.WRITING),
    MONUMENT("monument" , 60 , 1 , null),
    WALLS("walls", 100 , 1 ,  Technologies.MASONRY),
    WATER_MILL("water_mill" , 120 , 2 , Technologies.THE_WHEEL),
    ARMORY("armory" , 130 , 3 , Technologies.IRON_WORKING),
    BURIAL_TOMB("burial_tomb", 120 , 0 , Technologies.PHILOSOPHY),
    CIRCUS("circus", 150 , 3 , Technologies.HORSEBACK_RIDING),
    COLOSSEUM("colosseum",150 , 3 , Technologies.CONSTRUCTION),
    COURTHOUSE("courthouse", 200 , 5 , Technologies.MATHEMATICS),
    STABLE("stable", 100 , 1 , Technologies.HORSEBACK_RIDING),
    TEMPLE("temple", 120 , 2 , Technologies.PHILOSOPHY),
    CASTLE("castle",200 , 3 , Technologies.CHIVALRY),
    FORGE("forge", 150 , 2 , Technologies.METAL_CASTING),
    GARDEN("garden" , 120 , 2 ,Technologies.THEOLOGY ),
    MARKET("market", 120 , 0 , Technologies.CURRENCY),
    MINT("mint", 120 , 0 ,Technologies.CURRENCY),
    MONASTERY("monastery", 120 , 2 , Technologies.THEOLOGY),
    UNIVERSITY("university", 200 , 3 , Technologies.EDUCATION),
    WORKSHOP("workshop",100 , 2 , Technologies.METAL_CASTING),
    BANK("bank", 220 , 0 , Technologies.BANKING),
    MILITARY_ACADEMY("military_academy", 350 , 3 , Technologies.MILITARY_SCIENCE),
    MUSEUM("museum", 350 , 3 , Technologies.ARCHAEOLOGY),
    OPERA_HOUSE("opera_house",220 , 3 , Technologies.ACOUSTICS),
    PUBLIC_SCHOOL("public_school", 350 , 3 ,Technologies.SCIENTIFIC_THEORY),
    SATRAPS_COURT("satrap\'s_court",220 , 0 , Technologies.BANKING),
    THEATER("theater",300 , 5 ,Technologies.PRINTING_PRESS),
    WINDMILL("windmill", 180 , 2 ,Technologies.ECONOMICS),
    ARSENAL("arsenal", 350 , 3 ,Technologies.RAILROAD),
    BROADCAST_TOWER("broadcast_tower", 600 , 3 , Technologies.RADIO),
    FACTORY("factory", 300 , 3 , Technologies.STEAM_POWER),
    HOSPITAL("hospital", 400 , 2 , Technologies.BIOLOGY),
    MILITARY_BASE("military_base",450 , 4 ,Technologies.TELEGRAPH),
    STOCK_EXCHANGE("stock_exchange",650 ,0 ,Technologies.ELECTRICITY);

    private final String name;
    private int cost;
    private int maintenance;
    private Technologies requiredTechnology;

    Buildings(String name, int cost, int maintenance, Technologies requiredTechnology) {
        this.name = name;
        this.cost = cost;
        this.maintenance = maintenance;
        this.requiredTechnology = requiredTechnology;
    }

    public static Buildings getBuildingByName(String name){
        return Buildings.valueOf(name);
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
}
