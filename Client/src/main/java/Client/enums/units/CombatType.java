package Client.enums.units;

import java.util.HashMap;
import java.util.Map;

public enum CombatType {
    ARCHERY(true, false, 0, 0, null),
    MOUNTED(false, true, 0, 0, new HashMap<>(Map.of("spearman", 100, "pike_man", 100))),
    RECON(true, false, 0, 0, null),
    MELEE(true, false, 0, 0, null),
    SIEGE(false, false, 10, 0, null),
    GUNPOWDER(true, false, 0, 0, null),
    ARMORED(false, false, 0, 10, new HashMap<>(Map.of("anti_tank_gun", 10))),
    NON_COMBAT(true, true, 0, 0, null);

    private final boolean hasDefenseBonuses;
    private final boolean canMoveAfterAttack;
    private final int bonusAgainstCities;
    private final int penaltyAttackingCities;
    private final HashMap<String, Integer> badAgainst;

    CombatType(boolean hasDefenseBonuses, boolean canMoveAfterAttack, int bonusAgainstCities, int penaltyAttackingCities,
               HashMap<String, Integer> badAgainst) {
        this.hasDefenseBonuses = hasDefenseBonuses;
        this.canMoveAfterAttack = canMoveAfterAttack;
        this.bonusAgainstCities = bonusAgainstCities;
        this.penaltyAttackingCities = penaltyAttackingCities;
        this.badAgainst = badAgainst;
    }

    public boolean hasDefenseBonuses() {
        return hasDefenseBonuses;
    }

    public boolean canMoveAfterAttack() {
        return canMoveAfterAttack;
    }

    public int getBonusAgainstCities() {
        return bonusAgainstCities;
    }

    public int getPenaltyAttackingCities() {
        return penaltyAttackingCities;
    }

    public HashMap<String, Integer> getBadAgainst() {
        return badAgainst;
    }
}
