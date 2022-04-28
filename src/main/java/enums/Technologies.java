package enums;

public enum Technologies {
    AGRICULTURE("agriculture", 20, null, 1), // keshavarzi
    ANIMAL_HUSBANDRY("animal_husbandry", 35, AGRICULTURE, 0),
    ARCHERY("archery", 35, AGRICULTURE, 0),
    MINING("mining", 35, AGRICULTURE, 0),
    BRONZE_WORKING("bronze_working", 55, MINING, 0),
    POTTERY("pottery", 35, AGRICULTURE, 0),
    CALENDAR("calendar", 70, POTTERY, 0),
    MASONRY("masonry", 55, MINING, 0),
    THE_WHEEL("the_wheel", 55, ANIMAL_HUSBANDRY, 0),
    TRAPPING("trapping", 55, ANIMAL_HUSBANDRY, 0),
    WRITING("writing", 55, POTTERY, 0),
    CONSTRUCTION("construction", 100, MASONRY, 0),
    HORSEBACK_RIDING("horseback_riding", 100, THE_WHEEL, 0),
    IRON_WORKING("iron_working", 150, BRONZE_WORKING, 0),
    //
    MATHEMATICS("mathematics", 100, THE_WHEEL, 0),
    PHILOSOPHY("philosophy", 100, WRITING, 0),
    //
    CIVIL_SERVICE("civil_service", 440, PHILOSOPHY, 0),
    CURRENCY("currency", 250, MATHEMATICS, 0),
    //
    CHIVALRY("chivalry", 440, CIVIL_SERVICE, 0),
    THEOLOGY("theology", 250, CALENDAR, 0),
    EDUCATION("education", 440, THEOLOGY, 0),
    //
    ENGINEERING("engineering", 250, MATHEMATICS, 0),
    MACHINERY("machinery", 440, ENGINEERING, 0),
    METAL_CASTING("metal_casting", 240, IRON_WORKING, 0),
    PHYSICS("physics", 440, ENGINEERING, 0),
    STEEL("steel", 440, METAL_CASTING, 0),
    ACOUSTICS("acoustics", 650, EDUCATION, 0),
    ARCHAEOLOGY("archaeology", 1300, ACOUSTICS, 0),
    BANKING("banking", 650, EDUCATION, 0),
    GUNPOWDER("gunpowder", 680, PHYSICS, 0),
    CHEMISTRY("chemistry", 900, GUNPOWDER, 0),
    ECONOMICS("economics", 900, BANKING, 0),
    FERTILIZER("fertilizer", 1300, CHEMISTRY, 0),
    METALLURGY("metallurgy", 900, GUNPOWDER, 0),
    MILITARY_SCIENCE("military_science", 1300, ECONOMICS, 0),
    PRINTING_PRESS("printing_press", 650, MACHINERY, 0),
    RIFLING("rifling", 1425, METALLURGY, 0),
    SCIENTIFIC_THEORY("scientific_theory", 1300, ACOUSTICS, 0),
    BIOLOGY("biology", 1680, ARCHAEOLOGY, 0),
    STEAM_POWER("steam_power", 1680, SCIENTIFIC_THEORY, 0),
    RAILROAD("railroad", 1900, STEAM_POWER, 0),
    REPLACEABLE_PARTS("replaceable_parts", 1900, STEAM_POWER, 0),
    COMBUSTION("combustion", 2200, REPLACEABLE_PARTS, 0),
    DYNAMITE("dynamite", 1900, FERTILIZER, 0),
    ELECTRICITY("electricity", 1900, BIOLOGY, 0),
    RADIO("radio", 220, ELECTRICITY, 0),
    TELEGRAPH("telegraph", 2200, ELECTRICITY, 0);

    private final String name;
    private final int cost;
    private Technologies technology;
    private Technologies technology1;
    private Technologies technology2;
    private Technologies technology3;
    private int state; // 0 unavailable , 1 avalable , 2 reasearched

    Technologies(String name, int cost, Technologies thechnology, int state) {
        this.name = name;
        this.cost = cost;
        this.technology = technology;
        this.state = state;
    }
}
