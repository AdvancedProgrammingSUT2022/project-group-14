package enums;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;

import static java.util.List.*;

public enum Technologies{
    AGRICULTURE("agriculture", 20, new HashSet<>()), // keshavarzi
    ANIMAL_HUSBANDRY("animal_husbandry", 35, new HashSet<>(Arrays.asList("AGRICULTURE"))),
    ARCHERY("archery", 35, new HashSet<>(Arrays.asList("AGRICULTURE"))),
    MINING("mining", 35, new HashSet<>(Arrays.asList("AGRICULTURE"))),
    BRONZE_WORKING("bronze_working", 55, new HashSet<>(Arrays.asList("MINING"))),
               POTTERY("pottery", 35, new HashSet<>(Arrays.asList("AGRICULTURE"))),
    MASONRY("masonry", 55, new HashSet<>(Arrays.asList("MINING"))),
               CALENDAR("calendar", 70, new HashSet<>(Arrays.asList("POTTERY"))),
                THE_WHEEL("the_wheel", 55, new HashSet<>(Arrays.asList("ANIMAL_HUSBANDRY"))),
    TRAPPING("trapping", 55, new HashSet<>(Arrays.asList("ANIMAL_HUSBANDRY"))),
    CONSTRUCTION("construction", 100, new HashSet<>(Arrays.asList("MASONRY"))),
                WRITING("writing", 55, new HashSet<>(Arrays.asList("POTTERY"))),
                HORSEBACK_RIDING("horseback_riding", 100, new HashSet<>(Arrays.asList("THE_WHEEL"))),
    IRON_WORKING("iron_working", 150, new HashSet<>(Arrays.asList("BRONZE_WORKING"))),
    MATHEMATICS("mathematics", 100, new HashSet<>(Arrays.asList("THE_WHEEL", "ARCHERY"))),
    PHILOSOPHY("philosophy", 100, new HashSet<>(Arrays.asList("WRITING"))),
                CIVIL_SERVICE("civil_service", 440, new HashSet<String>(Arrays.asList("PHILOSOPHY", "TRAPPING"))),
    CURRENCY("currency", 250, new HashSet<>(Arrays.asList("MATHEMATICS"))),
    CHIVALRY("chivalry", 440, new HashSet<>(Arrays.asList("CIVIL_SERVICE", "HORSEBACK_RIDING", "CURRENCY"))),
    THEOLOGY("theology", 250, new HashSet<>(Arrays.asList("CALENDAR", "PHILOSOPHY"))),
    EDUCATION("education", 440, new HashSet<>(Arrays.asList("THEOLOGY"))),
    ENGINEERING("engineering", 250, new HashSet<>(Arrays.asList("MATHEMATICS", "CONSTRUCTION"))),
    MACHINERY("machinery", 440, new HashSet<>(Arrays.asList("ENGINEERING"))),
    METAL_CASTING("metal_casting", 240, new HashSet<String>(Arrays.asList("IRON_WORKING"))),
    PHYSICS("physics", 440, new HashSet<>(Arrays.asList("ENGINEERING", "METAL_CASTING"))),
    STEEL("steel", 440, new HashSet<>(Arrays.asList("METAL_CASTING"))),
    ACOUSTICS("acoustics", 650, new HashSet<>(Arrays.asList("EDUCATION"))),
    ARCHAEOLOGY("archaeology", 1300, new HashSet<>(Arrays.asList("ACOUSTICS"))),
    BANKING("banking", 650, new HashSet<>(Arrays.asList("EDUCATION", "CHIVALRY"))),
    GUNPOWDER("gunpowder", 680, new HashSet<>(Arrays.asList("PHYSICS", "STEEL"))),
    CHEMISTRY("chemistry", 900, new HashSet<>(Arrays.asList("GUNPOWDER"))),
    PRINTING_PRESS("printing_press", 650, new HashSet<>(Arrays.asList("MACHINERY", "PHYSICS"))),
    ECONOMICS("economics", 900, new HashSet<>(Arrays.asList("BANKING", "PRINTING_PRESS"))),
    FERTILIZER("fertilizer", 1300, new HashSet<>(Arrays.asList("CHEMISTRY"))),
    METALLURGY("metallurgy", 900, new HashSet<>(Arrays.asList("GUNPOWDER"))),
    MILITARY_SCIENCE("military_science", 1300, new HashSet<String>(Arrays.asList("ECONOMICS", "CHEMISTRY"))),
    RIFLING("rifling", 1425, new HashSet<>(Arrays.asList("METALLURGY"))),
    SCIENTIFIC_THEORY("scientific_theory", 1300, new HashSet<String>(Arrays.asList("ACOUSTICS"))),
    BIOLOGY("biology", 1680, new HashSet<>(Arrays.asList("ARCHAEOLOGY", "SCIENTIFIC_THEORY"))),
    STEAM_POWER("steam_power", 1680, new HashSet<>(Arrays.asList("SCIENTIFIC_THEORY", "MILITARY_SCIENCE"))),
    RAILROAD("railroad", 1900, new HashSet<>(Arrays.asList("STEAM_POWER"))),
    REPLACEABLE_PARTS("replaceable_parts", 1900, new HashSet<String>(Arrays.asList("STEAM_POWER"))),
    DYNAMITE("dynamite", 1900, new HashSet<>(Arrays.asList("FERTILIZER", "RIFLING"))),
    COMBUSTION("combustion", 2200,
            new HashSet<String>(Arrays.asList("REPLACEABLE_PARTS", "RAILROAD", "DYNAMITE"))),
    ELECTRICITY("electricity", 1900, new HashSet<>(Arrays.asList("BIOLOGY", "STEAM_POWER"))),
    RADIO("radio", 220, new HashSet<>(Arrays.asList("ELECTRICITY"))),
    TELEGRAPH("telegraph", 2200, new HashSet<>(Arrays.asList("ELECTRICITY")));

    private final String name;
    private final int cost;
    private final HashSet<String> requiredTechnologies;

    Technologies(String name, int cost, HashSet<String> requiredTechnologies) {
        this.name = name;
        this.cost = cost;
        this.requiredTechnologies = requiredTechnologies;
    }

    public String getName() {
        return this.name;
    }

    public int getCost() {
        return this.cost;
    }

    public HashSet<String> getRequiredTechnologies() {
        return this.requiredTechnologies;
    }

    public static Technologies getTechnologyByName(String name){
        return Technologies.valueOf(name.toUpperCase(Locale.ROOT));
    }
}
