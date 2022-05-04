package enums.units;

import enums.Technologies;
import enums.resources.ResourceTypes;
import enums.resources.StrategicResourceTypes;

public enum Melee implements CombatUnit {
    SCOUT("scout", 25, CombatType.RECON, 4, 2, null, null),
    SPEARMAN("spearman", 50, CombatType.MELEE, 7, 2, null, Technologies.BRONZE_WORKING),
    WARRIOR("warrior", 40, CombatType.MELEE, 6, 2, null, null),
    HORSE_MAN("horse_man", 80, CombatType.MOUNTED, 13, 4, StrategicResourceTypes.HORSE, Technologies.HORSEBACK_RIDING),
    SWORD_MAN("sword_man", 80, CombatType.MELEE, 11, 2, StrategicResourceTypes.IRON, Technologies.IRON_WORKING),
    KNIGHT("knight", 150, CombatType.MOUNTED, 18, 3, StrategicResourceTypes.HORSE, Technologies.CHIVALRY),
    LONGSWORD_MAN("longsword_man",150 , CombatType.MELEE , 15 , 3 , StrategicResourceTypes.IRON ,  Technologies.STEEL),
    PIKE_MAN("pike_man", 100 , CombatType.MELEE , 10 , 2 , null , Technologies.CIVIL_SERVICE),
    CAVALRY("cavalry", 260 , CombatType.MOUNTED , 25 , 3 , StrategicResourceTypes.HORSE , Technologies.MILITARY_SCIENCE),
    LANCER("lancer", 220 , CombatType.MOUNTED , 22 , 4 ,StrategicResourceTypes.HORSE , Technologies.METALLURGY),
    MUSKET_MAN("musket_man", 120 , CombatType.GUNPOWDER , 16 , 2 , null , Technologies.GUNPOWDER),
    RIFLE_MAN("rifle_man", 200 , CombatType.GUNPOWDER , 25 , 2 , null , Technologies.RIFLING),
    ANTI_TANK_GUN("anti_tank_gun", 300 , CombatType.GUNPOWDER , 32 , 2 , null , Technologies.REPLACEABLE_PARTS),
    INFANTRY("infantry", 300 , CombatType.GUNPOWDER , 36 , 2 , null , Technologies.REPLACEABLE_PARTS);

    private final String name;
    private int cost;
    private CombatType type;
    private int combatStrength;
    private int rangedCombatStrength;
    private int range;
    private int movement;
    private ResourceTypes requiredResource;
    private Technologies requiredTechnology;

    Melee(String name, int cost, CombatType type, int combatStrength,
            int movement, ResourceTypes requiredResource,
            Technologies requiredTechnology) {
        this.name = name;
        this.cost = cost;
        this.type = type;
        this.combatStrength = combatStrength;
        this.movement = movement;
        this.requiredResource = requiredResource;
        this.requiredTechnology = requiredTechnology;
    }

    public static Melee getMeleeUnitByName(String name){
        return Melee.valueOf(name);
    }
}
