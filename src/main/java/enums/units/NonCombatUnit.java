package enums.units;

public enum NonCombatUnit {
    WORKER("worker", 70, 2),
    SETTLER("settler", 89, 2);

    private final String name;
    private int cost;
    private int movement;

    NonCombatUnit(String name, int cost, int movement){
        this.name = name;
        this.cost = cost;
        this.movement = movement;
    }

    public static NonCombatUnit getNonCombatUnitByName(String name){
        return NonCombatUnit.valueOf(name);
    }
}
