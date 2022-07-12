package enums.tiles;

import enums.resources.ResourceTypes;

import java.util.HashSet;

public interface TileTypes {
    String getName();
    double getFood();
    double getProduction();
    double getGold();
    int getCombatImpact();
    int getMovementPoint();
    HashSet<ResourceTypes> getPossibleResources();
}
