package Client.enums.units;


import javafx.scene.image.Image;

import java.util.Objects;

public enum UnitStates {
    WAKE("wake"),
    SLEEP("sleep"),
    ALERT("alert"),
    FORTIFY("fortify"),
    FORTIFY_TILL_HEALED("fortifyTillHealed"),
    PILLAGE("pillage"),
    SETUP_RANGED("setupRanged"),
    GARRISON("garrison"),
    WORKING("working");

    private final String name;

    UnitStates(String name) {
        this.name = name;
    }

    public Image getImage(){
        return new Image(Objects.requireNonNull(Main.class.getResource("/images/units/actions/" + this.name + ".png")).toString());
    }

    public String getName() {
        return this.name;
    }
}
