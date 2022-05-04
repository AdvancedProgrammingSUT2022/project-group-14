package enums.resources;

import enums.Improvements;

public interface ResourceTypes {
    // double food = 0;
    // double production = 0 ;
    // double gold = 0 ;
    // Improvements requiredImprovement = Improvements.AGRICULTURE;
    double foodGetter();

    double productionGetter();

    double goldGetter();

    Improvements requiredImprovementGetter();

    String nameGetter();

}
