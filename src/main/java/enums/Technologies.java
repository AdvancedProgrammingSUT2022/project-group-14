package enums;

import application.App;
import javafx.scene.image.Image;

import java.util.*;

import static java.util.List.*;

public enum Technologies {
    AGRICULTURE("agriculture", 20, new HashSet<>()), // keshavarzi
    ANIMAL_HUSBANDRY("animal_husbandry", 35, new HashSet<>(of("AGRICULTURE"))),
    ARCHERY("archery", 35, new HashSet<>(of("AGRICULTURE"))),
    MINING("mining", 35, new HashSet<>(of("AGRICULTURE"))),
    BRONZE_WORKING("bronze_working", 55, new HashSet<>(of("MINING"))),
    POTTERY("pottery", 35, new HashSet<>(of("AGRICULTURE"))),
    MASONRY("masonry", 55, new HashSet<>(of("MINING"))),
    CALENDAR("calendar", 70, new HashSet<>(of("POTTERY"))),
    THE_WHEEL("the_wheel", 55, new HashSet<>(of("ANIMAL_HUSBANDRY"))),
    TRAPPING("trapping", 55, new HashSet<>(of("ANIMAL_HUSBANDRY"))),
    CONSTRUCTION("construction", 100, new HashSet<>(of("MASONRY"))),
    WRITING("writing", 55, new HashSet<>(of("POTTERY"))),
    HORSEBACK_RIDING("horseback_riding", 100, new HashSet<>(of("THE_WHEEL"))),
    IRON_WORKING("iron_working", 150, new HashSet<>(of("BRONZE_WORKING"))),
    MATHEMATICS("mathematics", 100, new HashSet<>(Arrays.asList("THE_WHEEL", "ARCHERY"))),
    PHILOSOPHY("philosophy", 100, new HashSet<>(of("WRITING"))),
    CIVIL_SERVICE("civil_service", 440, new HashSet<>(Arrays.asList("PHILOSOPHY", "TRAPPING"))),
    CURRENCY("currency", 250, new HashSet<>(of("MATHEMATICS"))),
    CHIVALRY("chivalry", 440, new HashSet<>(Arrays.asList("CIVIL_SERVICE", "HORSEBACK_RIDING", "CURRENCY"))),
    THEOLOGY("theology", 250, new HashSet<>(Arrays.asList("CALENDAR", "PHILOSOPHY"))),
    EDUCATION("education", 440, new HashSet<>(of("THEOLOGY"))),
    ENGINEERING("engineering", 250, new HashSet<>(Arrays.asList("MATHEMATICS", "CONSTRUCTION"))),
    MACHINERY("machinery", 440, new HashSet<>(of("ENGINEERING"))),
    METAL_CASTING("metal_casting", 240, new HashSet<>(of("IRON_WORKING"))),
    PHYSICS("physics", 440, new HashSet<>(Arrays.asList("ENGINEERING", "METAL_CASTING"))),
    STEEL("steel", 440, new HashSet<>(of("METAL_CASTING"))),
    ACOUSTICS("acoustics", 650, new HashSet<>(of("EDUCATION"))),
    ARCHAEOLOGY("archaeology", 1300, new HashSet<>(of("ACOUSTICS"))),
    BANKING("banking", 650, new HashSet<>(Arrays.asList("EDUCATION", "CHIVALRY"))),
    GUNPOWDER("gunpowder", 680, new HashSet<>(Arrays.asList("PHYSICS", "STEEL"))),
    CHEMISTRY("chemistry", 900, new HashSet<>(of("GUNPOWDER"))),
    PRINTING_PRESS("printing_press", 650, new HashSet<>(Arrays.asList("MACHINERY", "PHYSICS"))),
    ECONOMICS("economics", 900, new HashSet<>(Arrays.asList("BANKING", "PRINTING_PRESS"))),
    FERTILIZER("fertilizer", 1300, new HashSet<>(of("CHEMISTRY"))),
    METALLURGY("metallurgy", 900, new HashSet<>(of("GUNPOWDER"))),
    MILITARY_SCIENCE("military_science", 1300, new HashSet<>(Arrays.asList("ECONOMICS", "CHEMISTRY"))),
    RIFLING("rifling", 1425, new HashSet<>(of("METALLURGY"))),
    SCIENTIFIC_THEORY("scientific_theory", 1300, new HashSet<>(of("ACOUSTICS"))),
    BIOLOGY("biology", 1680, new HashSet<>(Arrays.asList("ARCHAEOLOGY", "SCIENTIFIC_THEORY"))),
    STEAM_POWER("steam_power", 1680, new HashSet<>(Arrays.asList("SCIENTIFIC_THEORY", "MILITARY_SCIENCE"))),
    RAILROAD("railroad", 1900, new HashSet<>(of("STEAM_POWER"))),
    REPLACEABLE_PARTS("replaceable_parts", 1900, new HashSet<>(of("STEAM_POWER"))),
    DYNAMITE("dynamite", 1900, new HashSet<>(Arrays.asList("FERTILIZER", "RIFLING"))),
    COMBUSTION("combustion", 2200, new HashSet<>(Arrays.asList("REPLACEABLE_PARTS", "RAILROAD", "DYNAMITE"))),
    ELECTRICITY("electricity", 1900, new HashSet<>(Arrays.asList("BIOLOGY", "STEAM_POWER"))),
    RADIO("radio", 220, new HashSet<>(of("ELECTRICITY"))),
    TELEGRAPH("telegraph", 2200, new HashSet<>(of("ELECTRICITY")));

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

    public Image getImage() {
        return new Image(Objects.requireNonNull(App.class.getResource("/images/technologies/" + this.name + ".png")).toString());
    }

    public HashSet<String> getRequiredTechnologies() {
        return this.requiredTechnologies;
    }

    public static HashMap<Technologies, Integer> getAllTechnologies() {
        HashMap<Technologies, Integer> allTechs = new HashMap<>();
        for (Technologies technology : Technologies.values())
            allTechs.put(technology, technology.getCost());
        return allTechs;
    }
}
