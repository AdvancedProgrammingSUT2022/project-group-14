package models.tiles;

import enums.Technologies;
import enums.units.UnitTypes;
import models.units.NonCombatUnit;
import models.units.Settler;
import models.units.Worker;

import java.util.Random;

public class Ruin {
    private final Technologies freeTechnology;
    private final boolean provideCitizen;
    private final double gold;
    private final NonCombatUnit nonCombatUnit;

    public Ruin(Technologies freeTechnology, boolean provideCitizen, double gold, NonCombatUnit nonCombatUnit) {
        this.freeTechnology = freeTechnology;
        this.provideCitizen = provideCitizen;
        this.gold = gold;
        this.nonCombatUnit = nonCombatUnit;
    }

    public static Ruin generateRandomRuin(Coordination coordination) {
        Random random = new Random();
        if (random.nextInt(20) % 20 == 0) {
            Technologies technology = Technologies.generateRandom();
            double gold = random.nextInt(10, 1000);
            boolean provideCitizen = false;
            NonCombatUnit nonCombatUnit = null;
            int randomNumber = random.nextInt(15);
            if (randomNumber % 3 == 0)
                nonCombatUnit = new Worker(UnitTypes.WORKER, coordination.getX(), coordination.getY(), null);
            else if (randomNumber % 3 == 1)
                nonCombatUnit = new Settler(UnitTypes.SETTLER, coordination.getX(), coordination.getY(), null);
            if (randomNumber % 5 == 0)
                provideCitizen = true;
            System.out.println(coordination.getX() + " " + coordination.getY());
            return new Ruin(technology, provideCitizen, gold, nonCombatUnit);
        } else {
            return null;
        }
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
