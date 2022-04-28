package enums;

import java.util.EnumSet;

public enum Technologies {
    AGRICULTURE("agriculture", 20,  EnumSet.of(null), 1), // keshavarzi
    ANIMAL_HUSBANDRY("animal_husbandry", 35,  EnumSet.of(Technologies.AGRICULTURE), 0),
    ARCHERY("archery", 35,  EnumSet.of(Technologies.AGRICULTURE), 0),
    MINING("mining", 35,  EnumSet.of(Technologies.AGRICULTURE), 0),
    BRONZE_WORKING("bronze_working", 55,  EnumSet.of(Technologies.MINING), 0),
    POTTERY("pottery", 35,  EnumSet.of(Technologies.AGRICULTURE), 0),
    MASONRY("masonry", 55,  EnumSet.of(Technologies.MINING), 0),
    CALENDAR("calendar", 70,  EnumSet.of(Technologies.POTTERY), 0),
    THE_WHEEL("the_wheel", 55,  EnumSet.of(Technologies.ANIMAL_HUSBANDRY), 0),
    TRAPPING("trapping", 55,  EnumSet.of(Technologies.ANIMAL_HUSBANDRY), 0),
    CONSTRUCTION("construction", 100,  EnumSet.of(Technologies.MASONRY), 0),
    WRITING("writing", 55,  EnumSet.of(Technologies.POTTERY), 0),
    HORSEBACK_RIDING("horseback_riding", 100,  EnumSet.of(Technologies.THE_WHEEL), 0),
    IRON_WORKING("iron_working", 150,  EnumSet.of(Technologies.BRONZE_WORKING), 0),
    MATHEMATICS("mathematics", 100,  EnumSet.of(Technologies.THE_WHEEL , Technologies.ARCHERY), 0),
    PHILOSOPHY("philosophy", 100,  EnumSet.of(Technologies.WRITING), 0),
    CIVIL_SERVICE("civil_service", 440,  EnumSet.of(Technologies.PHILOSOPHY , Technologies.TRAPPING), 0),
    CURRENCY("currency", 250,  EnumSet.of(Technologies.MATHEMATICS), 0),
    CHIVALRY("chivalry", 440,  EnumSet.of(Technologies.CIVIL_SERVICE , Technologies.HORSEBACK_RIDING, Technologies.CURRENCY), 0),
    THEOLOGY("theology", 250,  EnumSet.of(Technologies.CALENDAR , Technologies.PHILOSOPHY), 0),
    EDUCATION("education", 440,  EnumSet.of(Technologies.THEOLOGY), 0),
    ENGINEERING("engineering", 250,  EnumSet.of(Technologies.MATHEMATICS , Technologies.CONSTRUCTION), 0),
    MACHINERY("machinery", 440,  EnumSet.of(Technologies.ENGINEERING), 0),
    METAL_CASTING("metal_casting", 240,  EnumSet.of(Technologies.IRON_WORKING), 0),
    PHYSICS("physics", 440,  EnumSet.of(Technologies.ENGINEERING , Technologies.METAL_CASTING), 0),
    STEEL("steel", 440,  EnumSet.of(Technologies.METAL_CASTING), 0),
    ACOUSTICS("acoustics", 650,  EnumSet.of(Technologies.EDUCATION), 0),
    ARCHAEOLOGY("archaeology", 1300,  EnumSet.of(Technologies.ACOUSTICS), 0),
    BANKING("banking", 650,  EnumSet.of(Technologies.EDUCATION , Technologies.CHIVALRY), 0),
    GUNPOWDER("gunpowder", 680,  EnumSet.of(Technologies.PHYSICS , Technologies.STEEL), 0),
    CHEMISTRY("chemistry", 900,  EnumSet.of(Technologies.GUNPOWDER), 0),
    PRINTING_PRESS("printing_press", 650,  EnumSet.of(Technologies.MACHINERY , Technologies.PHYSICS), 0),
    ECONOMICS("economics", 900,  EnumSet.of(Technologies.BANKING, Technologies.PRINTING_PRESS), 0),
    FERTILIZER("fertilizer", 1300,  EnumSet.of(Technologies.CHEMISTRY), 0),
    METALLURGY("metallurgy", 900,  EnumSet.of(Technologies.GUNPOWDER), 0),
    MILITARY_SCIENCE("military_science", 1300,  EnumSet.of(Technologies.ECONOMICS , Technologies.CHEMISTRY), 0),
    RIFLING("rifling", 1425,  EnumSet.of(Technologies.METALLURGY), 0),
    SCIENTIFIC_THEORY("scientific_theory", 1300,  EnumSet.of(Technologies.ACOUSTICS), 0),
    BIOLOGY("biology", 1680,  EnumSet.of(Technologies.ARCHAEOLOGY , Technologies.SCIENTIFIC_THEORY), 0),
    STEAM_POWER("steam_power", 1680,  EnumSet.of(Technologies.SCIENTIFIC_THEORY , Technologies.MILITARY_SCIENCE), 0),
    RAILROAD("railroad", 1900,  EnumSet.of(Technologies.STEAM_POWER), 0),
    REPLACEABLE_PARTS("replaceable_parts", 1900,  EnumSet.of(Technologies.STEAM_POWER), 0),
    DYNAMITE("dynamite", 1900,  EnumSet.of(Technologies.FERTILIZER , Technologies.RIFLING), 0),
    COMBUSTION("combustion", 2200,  EnumSet.of(Technologies.REPLACEABLE_PARTS , Technologies.RAILROAD , Technologies.DYNAMITE), 0),
    ELECTRICITY("electricity", 1900,  EnumSet.of(Technologies.BIOLOGY , Technologies.STEAM_POWER), 0),
    RADIO("radio", 220,  EnumSet.of(Technologies.ELECTRICITY), 0),
    TELEGRAPH("telegraph", 2200,  EnumSet.of(Technologies.ELECTRICITY), 0);

    private final String name;
    private final int cost;
    private EnumSet<Technologies> technologiesSet;
    private int state; // 0 unavailable , 1 avalable , 2 reasearched

    Technologies(String name, int cost, EnumSet<Technologies> thechnologiesSet, int state) {
        this.name = name;
        this.cost = cost;
        this.technologiesSet = technologiesSet;
        this.state = state;
    }
}
