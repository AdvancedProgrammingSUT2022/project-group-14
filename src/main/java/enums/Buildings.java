package enums;

public enum Buildings {
    BARRACKS("//s*barracks//s*"),
    GRANARY("//s*granary//s*"),
    LIBRARY("//s*library//s*"),
    MONUMENT("//s*monument//s*"),
    WALLS("//s*walls//s*"),
    WATER_MILL("//s*water_mill//s*"),
    CLASSICAL_ERA_BUILDINGS("//s*classical_era_buildings//s*"),
    ARMORY("//s*armory//s*"),
    BURIAL_TOMB("//s*burial_tomb//s*"),
    CIRCUS("//s*circus//s*"),
    COLOSSEUM("//s*colosseum//s*"),
    COURTHOUSE("//s*courthouse//s*"),
    STABLE("//s*stable//s*"),
    TEMPLE("//s*temple//s*"),
    MEDIEVAL_ERA_BUILDINGS("//s*medieval_era_buildings//s*"),
    CASTLE("//s*castle//s*"),
    FORGE("//s*forge//s*"),
    GARDEN("//s*garden//s*"),
    MARKET("//s*market//s*"),
    MINT("//s*mint//s*"),
    MONASTERY("//s*monastery//s*"),
    UNIVERSITY("//s*university//s*"),
    WORKSHOP("//s*workshop//s*"),
    RENAISSANCE_ERA_BUILDINGS("//s*renaissance_era_buildings//s*"),
    BANK("//s*bank//s*"),
    MILITARY_ACADEMY("//s*military_academy//s*"),
    MUSEUM("//s*museum//s*"),
    OPERA_HOUSE("//s*opera_house//s*"),
    PUBLIC_SCHOOL("//s*public_school//s*"),
    SATRAPS_COURT("//s*satrap\'s_court//s*"),
    THEATER("//s*theater//s*"),
    WINDMILL("//s*windmill//s*"),
    INDUSTRIAL_ERA_BUILDINGS("//s*industrial_era_buildings//s*"),
    ARSENAL("//s*arsenal//s*"),
    BROADCAST_TOWER("//s*broadcast_tower//s*"),
    FACTORY("//s*factory//s*"),
    HOSPITAL("//s*hospital//s*"),
    MILITARY_BASE("//s*military_base//s*"),
    STOCK_EXCHANGE("//s*stock_exchange//s*");

    private final String regex;

    Buildings(String regex) {
        this.regex = regex;
    }

}
