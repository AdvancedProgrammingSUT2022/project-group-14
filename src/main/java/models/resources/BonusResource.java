package models.resources;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import enums.Improvements;
import enums.resources.BonusResourceTypes;
import enums.resources.StrategicResourceTypes;

public class BonusResource extends Resource {

    private BonusResourceTypes type;
    public static HashMap<BonusResourceTypes, BonusResource> typesMap = new HashMap<>();



    public BonusResource(BonusResourceTypes type, double food, double production, double gold,
            Improvements requiredProgress) {
        super(food, production, gold, requiredProgress);
        this.type = type;
    }

    public BonusResource(BonusResourceTypes type) {
        super(type);
        this.type = type;
    }

    public static HashMap<BonusResourceTypes, BonusResource> typeMapGetter() {
        return typesMap;
    }
}
