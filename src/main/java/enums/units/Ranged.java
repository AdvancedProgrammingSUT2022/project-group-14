package enums.units;

public enum Ranged {
    ARCHER("//s*archer//s*"),
    CHARIOT_ARCHER("//s*chariot_archer//s*"),
    CATAPULT("//s*catapult//s*"),
    CROSSBOW_MAN("//s*crossbow_man//s*"),
    TREBUCHET("//s*trebuchet//s*"),
    CANON("//s*canon//s*"),
    ARTILLERY("//s*artillery//s*"),
    PANZER("//s*panzer//s*"),
    TANK("//s*tank//s*");


    private final String regex;

    Ranged(String regex) {
        this.regex = regex;
    }
}
