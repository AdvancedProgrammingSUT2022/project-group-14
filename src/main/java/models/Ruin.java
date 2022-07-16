package models;

import enums.Technologies;
import enums.units.UnitTypes;
import models.tiles.Coordination;
import models.units.NonCombatUnit;

import java.util.Random;

public class Ruin {
    private Technologies freeTechnology;
    private boolean provideCitizen;
    private double gold;
    private NonCombatUnit nonCombatUnit;
    private Coordination coordination;

    public Ruin(Technologies freeTechnology, boolean provideCitizen, double gold, NonCombatUnit nonCombatUnit, Coordination coordination) {
        this.freeTechnology = freeTechnology;
        this.provideCitizen = provideCitizen;
        this.gold = gold;
        this.nonCombatUnit = nonCombatUnit;
        this.coordination = coordination;
    }

    public static Ruin generateRandomRuin(Coordination coordination) {
        Random random = new Random();
        if (random.nextInt(10) % 10 == 0) {
            Technologies technology = Technologies.generateRandom();
            double gold = random.nextInt(10, 1000);
            boolean provideCitizen = false;
            NonCombatUnit nonCombatUnit;
            int randomNumber = random.nextInt(15);
            if (randomNumber % 3 == 0) nonCombatUnit = new NonCombatUnit(UnitTypes.WORKER, coordination.getX(), coordination.getY(), null);
            else if (randomNumber % 3 == 1) nonCombatUnit = new NonCombatUnit(UnitTypes.SETTLER, coordination.getX(), coordination.getY(), null);
            else nonCombatUnit = null;

            if (randomNumber % 5 == 0) provideCitizen = true;

            return new Ruin(technology, provideCitizen, gold, nonCombatUnit, coordination);
        } else return null;
    }

    public Technologies getFreeTechnology() {
        return freeTechnology;
    }

    public boolean isProvideCitizen() {
        return provideCitizen;
    }

    public double getGold() {
        return gold;
    }

    public NonCombatUnit getNonCombatUnit() {
        return nonCombatUnit;
    }
}
