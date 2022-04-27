package models.resources;

import enums.Progresses;

public class LuxuryResource extends Resource{
    
    private static int happiness = 4;
    
    
    public LuxuryResource(double food, double production, double gold, Progresses requiredProgress) {
        super(food, production, gold, requiredProgress);
        //TODO Auto-generated constructor stub
    }
}
