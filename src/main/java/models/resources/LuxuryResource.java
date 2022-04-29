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
    private LuxuryResourceTypes type;

    public LuxuryResource(LuxuryResourceTypes type) {
        super(type);
        this.type = type;
    }

}
