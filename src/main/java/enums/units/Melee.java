package enums.units;

public enum Melee implements CombatUnit {
    SPEARMAN("//s*spearman//s*"),
    WARRIOR("//s*warrior//s*"),
    HORSE_MAN("//s*horse_man//s*"),
    SWORD_MAN("//s*sword_man//s*"),
    KNIGHT("//s*knight//s*"),
    LONGSWORD_MAN("//s*longsword_man//s*"),
    PIKE_MAN("//s*pike_man//s*"),
    CAVALRY("//s*cavalry//s*"),
    LANCER("//s*lancer//s*"),
    MUSKET_MAN("//s*musket_man//s*"),
    RIFLE_MAN("//s*rifle_man//s*"),
    ANTI_TANK_GUN("//s*anti_tank_gun//s*"),
    INFANTRY("//s*infantry//s*");

    private final String regex;

    Melee(String regex) {
        this.regex = regex;
    }
}
