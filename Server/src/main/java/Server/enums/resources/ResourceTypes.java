package Server.enums.resources;

import Server.enums.Improvements;

public interface ResourceTypes {
    double getFood();

    double getProduction();

    double getGold();

    Improvements getRequiredImprovement();

    String getName();

}
