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


    public BonusResource(BonusResourceTypes type) {
        super(type);
        this.type = type;
    }

    public static HashMap<BonusResourceTypes, BonusResource> typeMapGetter() {
        return typesMap;
    }
}
