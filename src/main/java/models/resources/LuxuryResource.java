package models.resources;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import enums.Improvements;
import enums.resources.LuxuryResourceTypes;

public class LuxuryResource extends Resource {

    private static int happiness = 4;
    private static LuxuryResourceTypes type;

    private static HashMap<LuxuryResourceTypes, LuxuryResource> typesMap = new HashMap<>();



    public LuxuryResource(LuxuryResourceTypes type, double food, double production, double gold,
            Improvements requiredProgress) {
        super(food, production, gold, requiredProgress);
        this.type = type;
    }

    public LuxuryResource(LuxuryResourceTypes type) {
        super(type);
        this.type = type;

    }


}
