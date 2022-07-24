package Client.enums;

import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.*;

import static java.util.List.of;

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
        return new Image(Objects.requireNonNull(Main.class.getResource("/images/technologies/" + this.name + ".png")).toString());
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

    public static Technologies generateRandom() {
        Random rand = new Random();
        return Technologies.values()[rand.nextInt(Technologies.values().length)];
    }

    public Group getTechnologyGroup(int x, int y) {
        Group group = new Group();
        Rectangle nameRectangle = new Rectangle(230, 27, Color.CADETBLUE);
        nameRectangle.setLayoutX(x);
        nameRectangle.setLayoutY(y - nameRectangle.getHeight());
        Rectangle turnsRectangle = new Rectangle(145, 27, Color.rgb(238, 128, 0));
        turnsRectangle.setLayoutX(x);
        turnsRectangle.setLayoutY(y);
        Text nameText = new Text(turnsRectangle.getLayoutX() + turnsRectangle.getWidth() / 2 - 25, nameRectangle.getLayoutY() + nameRectangle.getHeight() - 7, this.getName().replaceAll("_", " "));
        nameText.setFill(Color.WHITE);
        Text turnsText = new Text(turnsRectangle.getLayoutX() + turnsRectangle.getWidth() / 2, turnsRectangle.getLayoutY() + turnsRectangle.getHeight() - 7, "" + WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getTechnologies().get(this));
        turnsText.setFill(Color.WHITE);
        Circle backgroundCircle = new Circle(40, Color.CADETBLUE);
        backgroundCircle.setLayoutX(x);
        backgroundCircle.setLayoutY(y);
        Circle imageCircle = new Circle(40, new ImagePattern(this.getImage()));
        imageCircle.setLayoutX(x);
        imageCircle.setLayoutY(y);
        group.getChildren().clear();
        group.getChildren().add(nameRectangle);
        group.getChildren().add(turnsRectangle);
        group.getChildren().add(nameText);
        group.getChildren().add(turnsText);
        group.getChildren().add(backgroundCircle);
        group.getChildren().add(imageCircle);
        group.setCursor(Cursor.HAND);
        group.setOnMouseClicked(mouseEvent -> {
            if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (mouseEvent.getClickCount() == 2) {
                    WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).setCurrentTechnology(this);
                }
            }
        });
        return group;
    }
}
