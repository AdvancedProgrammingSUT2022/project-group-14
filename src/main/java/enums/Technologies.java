package enums;

import java.util.EnumSet;

public enum Technologies {
    AGRICULTURE("agriculture", 20, EnumSet.of(null)), // keshavarzi
    ANIMAL_HUSBANDRY("animal_husbandry", 35, EnumSet.of(Technologies.AGRICULTURE)),
    ARCHERY("archery", 35, EnumSet.of(Technologies.AGRICULTURE)),
    MINING("mining", 35, EnumSet.of(Technologies.AGRICULTURE)),
    BRONZE_WORKING("bronze_working", 55, EnumSet.of(Technologies.MINING)),
    POTTERY("pottery", 35, EnumSet.of(Technologies.AGRICULTURE)),
    MASONRY("masonry", 55, EnumSet.of(Technologies.MINING)),
    CALENDAR("calendar", 70, EnumSet.of(Technologies.POTTERY)),
    THE_WHEEL("the_wheel", 55, EnumSet.of(Technologies.ANIMAL_HUSBANDRY)),
    TRAPPING("trapping", 55, EnumSet.of(Technologies.ANIMAL_HUSBANDRY)),
    CONSTRUCTION("construction", 100, EnumSet.of(Technologies.MASONRY)),
    WRITING("writing", 55, EnumSet.of(Technologies.POTTERY)),
    HORSEBACK_RIDING("horseback_riding", 100, EnumSet.of(Technologies.THE_WHEEL)),
    IRON_WORKING("iron_working", 150, EnumSet.of(Technologies.BRONZE_WORKING)),
    MATHEMATICS("mathematics", 100, EnumSet.of(Technologies.THE_WHEEL, Technologies.ARCHERY)),
    PHILOSOPHY("philosophy", 100, EnumSet.of(Technologies.WRITING)),
    CIVIL_SERVICE("civil_service", 440, EnumSet.of(Technologies.PHILOSOPHY, Technologies.TRAPPING)),
    CURRENCY("currency", 250, EnumSet.of(Technologies.MATHEMATICS)),
    CHIVALRY("chivalry", 440,
            EnumSet.of(Technologies.CIVIL_SERVICE, Technologies.HORSEBACK_RIDING, Technologies.CURRENCY)),
    THEOLOGY("theology", 250, EnumSet.of(Technologies.CALENDAR, Technologies.PHILOSOPHY)),
    EDUCATION("education", 440, EnumSet.of(Technologies.THEOLOGY)),
    ENGINEERING("engineering", 250, EnumSet.of(Technologies.MATHEMATICS, Technologies.CONSTRUCTION)),
    MACHINERY("machinery", 440, EnumSet.of(Technologies.ENGINEERING)),
    METAL_CASTING("metal_casting", 240, EnumSet.of(Technologies.IRON_WORKING)),
    PHYSICS("physics", 440, EnumSet.of(Technologies.ENGINEERING, Technologies.METAL_CASTING)),
    STEEL("steel", 440, EnumSet.of(Technologies.METAL_CASTING)),
    ACOUSTICS("acoustics", 650, EnumSet.of(Technologies.EDUCATION)),
    ARCHAEOLOGY("archaeology", 1300, EnumSet.of(Technologies.ACOUSTICS)),
    BANKING("banking", 650, EnumSet.of(Technologies.EDUCATION, Technologies.CHIVALRY)),
    GUNPOWDER("gunpowder", 680, EnumSet.of(Technologies.PHYSICS, Technologies.STEEL)),
    CHEMISTRY("chemistry", 900, EnumSet.of(Technologies.GUNPOWDER)),
    PRINTING_PRESS("printing_press", 650, EnumSet.of(Technologies.MACHINERY, Technologies.PHYSICS)),
    ECONOMICS("economics", 900, EnumSet.of(Technologies.BANKING, Technologies.PRINTING_PRESS)),
    FERTILIZER("fertilizer", 1300, EnumSet.of(Technologies.CHEMISTRY)),
    METALLURGY("metallurgy", 900, EnumSet.of(Technologies.GUNPOWDER)),
    MILITARY_SCIENCE("military_science", 1300, EnumSet.of(Technologies.ECONOMICS, Technologies.CHEMISTRY)),
    RIFLING("rifling", 1425, EnumSet.of(Technologies.METALLURGY)),
    SCIENTIFIC_THEORY("scientific_theory", 1300, EnumSet.of(Technologies.ACOUSTICS)),
    BIOLOGY("biology", 1680, EnumSet.of(Technologies.ARCHAEOLOGY, Technologies.SCIENTIFIC_THEORY)),
    STEAM_POWER("steam_power", 1680, EnumSet.of(Technologies.SCIENTIFIC_THEORY, Technologies.MILITARY_SCIENCE)),
    RAILROAD("railroad", 1900, EnumSet.of(Technologies.STEAM_POWER)),
    REPLACEABLE_PARTS("replaceable_parts", 1900, EnumSet.of(Technologies.STEAM_POWER)),
    DYNAMITE("dynamite", 1900, EnumSet.of(Technologies.FERTILIZER, Technologies.RIFLING)),
    COMBUSTION("combustion", 2200,
            EnumSet.of(Technologies.REPLACEABLE_PARTS, Technologies.RAILROAD, Technologies.DYNAMITE)),
    ELECTRICITY("electricity", 1900, EnumSet.of(Technologies.BIOLOGY, Technologies.STEAM_POWER)),
    RADIO("radio", 220, EnumSet.of(Technologies.ELECTRICITY)),
    TELEGRAPH("telegraph", 2200, EnumSet.of(Technologies.ELECTRICITY));

    private final String name;
    private final int cost;
    private EnumSet<Technologies> technologiesSet;

    Technologies(String name, int cost, EnumSet<Technologies> thechnologiesSet) {
        this.name = name;
        this.cost = cost;
        this.technologiesSet = technologiesSet;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public EnumSet<Technologies> getTechnologiesSet() {
        return technologiesSet;
    }
}
