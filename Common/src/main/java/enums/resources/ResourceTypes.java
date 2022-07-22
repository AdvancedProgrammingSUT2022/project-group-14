package enums.resources;

import enums.Improvements;

public interface ResourceTypes {
    double getFood();

    double getProduction();

    double getGold();

    Improvements getRequiredImprovement();

    String getName();

}
