package Server.enums.units;

import Server.Main;
import Server.enums.Technologies;
import Server.enums.resources.ResourceTypes;
import Server.enums.resources.StrategicResourceTypes;
import javafx.scene.image.Image;

import java.util.Objects;

public enum UnitTypes {
    SCOUT("scout", 25, CombatType.RECON, 4, 0, 1, 2, null, null),
    SPEARMAN("spearman", 50, CombatType.MELEE, 7, 0, 1, 2, null, Technologies.BRONZE_WORKING),
    WARRIOR("warrior", 40, CombatType.MELEE, 6, 0, 1, 2, null, null),
    HORSE_MAN("horse_man", 80, CombatType.MOUNTED, 13, 0, 1, 4, StrategicResourceTypes.HORSE, Technologies.HORSEBACK_RIDING),
    SWORD_MAN("sword_man", 80, CombatType.MELEE, 11, 0, 1, 2, StrategicResourceTypes.IRON, Technologies.IRON_WORKING),
    KNIGHT("knight", 150, CombatType.MOUNTED, 18, 0, 1, 3, StrategicResourceTypes.HORSE, Technologies.CHIVALRY),
    LONGSWORD_MAN("longsword_man", 150, CombatType.MELEE, 15, 0, 1, 3, StrategicResourceTypes.IRON, Technologies.STEEL),
    PIKE_MAN("pike_man", 100, CombatType.MELEE, 10, 0, 1, 2, null, Technologies.CIVIL_SERVICE),
    CAVALRY("cavalry", 260, CombatType.MOUNTED, 25, 0, 1, 3, StrategicResourceTypes.HORSE, Technologies.MILITARY_SCIENCE),
    LANCER("lancer", 220, CombatType.MOUNTED, 22, 0, 1, 4, StrategicResourceTypes.HORSE, Technologies.METALLURGY),
    MUSKET_MAN("musket_man", 120, CombatType.GUNPOWDER, 16, 0, 1, 2, null, Technologies.GUNPOWDER),
    RIFLE_MAN("rifle_man", 200, CombatType.GUNPOWDER, 25, 0, 1, 2, null, Technologies.RIFLING),
    ANTI_TANK_GUN("anti_tank_gun", 300, CombatType.GUNPOWDER, 32, 0, 1, 2, null, Technologies.REPLACEABLE_PARTS),
    INFANTRY("infantry", 300, CombatType.GUNPOWDER, 36, 0, 1, 2, null, Technologies.REPLACEABLE_PARTS),

    ARCHER("archer", 70, CombatType.ARCHERY, 4, 6, 2, 2, null, Technologies.ARCHERY),
    CHARIOT_ARCHER("chariot_archer", 60, CombatType.MOUNTED, 3, 6, 2, 4, StrategicResourceTypes.HORSE, Technologies.THE_WHEEL),
    CATAPULT("catapult", 100, CombatType.SIEGE, 4, 14, 2, 2, StrategicResourceTypes.IRON, Technologies.MATHEMATICS),
    CROSSBOW_MAN("crossbow_man", 120, CombatType.ARCHERY, 6, 12, 2, 2, null, Technologies.MACHINERY),
    TREBUCHET("trebuchet", 170, CombatType.SIEGE, 6, 20, 2, 2, StrategicResourceTypes.IRON, Technologies.PHYSICS),
    CANNON("cannon", 250, CombatType.SIEGE, 10, 26, 2, 2, null, Technologies.CHEMISTRY),
    ARTILLERY("artillery", 420, CombatType.SIEGE, 16, 32, 3, 2, null, Technologies.DYNAMITE),
    PANZER("panzer", 450, CombatType.ARMORED, 60, 9999, 1, 5, null, Technologies.COMBUSTION),
    TANK("tank", 450, CombatType.ARMORED, 50, 9999, 1, 4, null, Technologies.COMBUSTION),

    SETTLER("settler", 89, CombatType.NON_COMBAT, 0, 0, 1, 2, null, null),
    WORKER("worker", 70, CombatType.NON_COMBAT, 0, 0, 1, 2, null, null);

    private final String name;
    private final int cost;
    private final CombatType type;
    private final int combatStrength;
    private final int rangedCombatStrength;
    private final int range;
    private final int movement;
    private final ResourceTypes requiredResource;
    private final Technologies requiredTechnology;

    UnitTypes(String name, int cost, CombatType type, int combatStrength, int rangedCombatStrength, int range, int movement,
              ResourceTypes requiredResource, Technologies requiredTechnology) {
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.combatStrength = combatStrength;
        this.rangedCombatStrength = rangedCombatStrength;
        this.range = range;
        this.movement = movement;
        this.requiredResource = requiredResource;
        this.requiredTechnology = requiredTechnology;
    }

    public String getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public CombatType getCombatType() {
        return type;
    }

    public int getCombatStrength() {
        return combatStrength;
    }

    public int getRangedCombatStrength() {
        return rangedCombatStrength;
    }

    public int getRange() {
        return range;
    }

    public int getMovement() {
        return movement;
    }

    public ResourceTypes getRequiredResource() {
        return requiredResource;
    }

    public Technologies getRequiredTechnology() {
        return requiredTechnology;
    }

}
