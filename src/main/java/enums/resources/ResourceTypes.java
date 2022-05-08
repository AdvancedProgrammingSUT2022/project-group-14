package enums.resources;

import enums.Improvements;

public interface ResourceTypes {
    double foodGetter();

    double productionGetter();

    double goldGetter();

    Improvements requiredImprovementGetter();

    String nameGetter();

}
