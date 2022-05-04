package enums.units;

import enums.Technologies;
import enums.resources.ResourceTypes;
import enums.resources.StrategicResourceTypes;

public enum Ranged implements CombatUnit {
    ARCHER("archer", 70, CombatType.ARCHERY, 4, 6, 2, 2, null, Technologies.ARCHERY),
    CHARIOT_ARCHER("chariot_archer", 60, CombatType.MOUNTED, 3, 6, 2, 4, StrategicResourceTypes.HORSE,
            Technologies.THE_WHEEL),
    CATAPULT("catapult", 100, CombatType.SIEGE, 4, 14, 2, 2, StrategicResourceTypes.IRON, Technologies.MATHEMATICS),
    CROSSBOW_MAN("crossbow_man", 120, CombatType.ARCHERY, 6, 12, 2, 2, null, Technologies.MACHINERY),
    TREBUCHET("trebuchet", 170, CombatType.SIEGE, 6, 20, 2, 2, StrategicResourceTypes.IRON, Technologies.PHYSICS),
    CANON("canon", 250, CombatType.SIEGE, 10, 26, 2, 2, null, Technologies.CHEMISTRY),
    ARTILLERY("artillery", 420, CombatType.SIEGE, 16, 32, 3, 2, null, Technologies.DYNAMITE),
    PANZER("panzer", 450, CombatType.ARMORED, 60, 9999, 9999, 5, null, Technologies.COMBUSTION),
    TANK("tank", 450 , CombatType.ARMORED , 50 , 9999 , 9999, 4 , null , Technologies.COMBUSTION);

    private final String name;
    private int cost;
    private CombatType type;
    private int combatStrength;
    private int rangedCombatStrength;
    private int range;
    private int movement;
    private ResourceTypes requiredResource;
    private Technologies requiredTechnology;

    Ranged(String name, int cost, CombatType type, int combatStrength,
            int rangedCombatStrength, int range, int movement, ResourceTypes requiredResource,
            Technologies requiredTechnology) {
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

    public static Ranged getRangedUnitByName(String name){
        return Ranged.valueOf(name);
    }
}
