package models.resources;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import enums.Progresses;
import enums.resources.LuxuryResourceTypes;

public class LuxuryResource extends Resource {

    private static int happiness = 4;
    private static LuxuryResourceTypes type;

    private static HashMap<LuxuryResourceTypes, LuxuryResource> typesMap = new HashMap<>();

    public static void writeData() {
        try {

            FileWriter writer = new FileWriter("./src/main/resources/resources/LuxuryResourceTypesInformation.json");
            LuxuryResource resource = null;
            resource = new LuxuryResource(LuxuryResourceTypes.COTTON, 0, 0, 2, Progresses.FARM);
            typesMap.put(LuxuryResourceTypes.COTTON, resource);
            resource = new LuxuryResource(LuxuryResourceTypes.COLOR, 0, 0, 2, Progresses.FARM);
            typesMap.put(LuxuryResourceTypes.COLOR, resource);
            resource = new LuxuryResource(LuxuryResourceTypes.FUR, 0, 0, 2, Progresses.CAMP);
            typesMap.put(LuxuryResourceTypes.FUR, resource);
            resource = new LuxuryResource(LuxuryResourceTypes.JEWEL, 0, 0, 3, Progresses.MINE);
            typesMap.put(LuxuryResourceTypes.JEWEL, resource);
            resource = new LuxuryResource(LuxuryResourceTypes.GOLD, 0, 0, 2, Progresses.MINE);
            typesMap.put(LuxuryResourceTypes.GOLD, resource);
            resource = new LuxuryResource(LuxuryResourceTypes.INCENSE, 0, 0, 2, Progresses.FARM);
            typesMap.put(LuxuryResourceTypes.INCENSE, resource);
            resource = new LuxuryResource(LuxuryResourceTypes.IVORY, 0, 0, 2, Progresses.CAMP);
            typesMap.put(LuxuryResourceTypes.IVORY, resource);
            resource = new LuxuryResource(LuxuryResourceTypes.MARBLE, 0, 0, 2, Progresses.MINE);
            typesMap.put(LuxuryResourceTypes.MARBLE, resource);
            resource = new LuxuryResource(LuxuryResourceTypes.SILK, 0, 0, 2, Progresses.FARM);
            typesMap.put(LuxuryResourceTypes.SILK, resource);
            resource = new LuxuryResource(LuxuryResourceTypes.SILVER, 0, 0, 2, Progresses.MINE);
            typesMap.put(LuxuryResourceTypes.SILVER, resource);
            resource = new LuxuryResource(LuxuryResourceTypes.SUGAR, 0, 0, 2, Progresses.FARM);
            typesMap.put(LuxuryResourceTypes.SUGAR, resource);

            writer.write(new Gson().toJson(typesMap));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readData() {
        try {
            String json = new String(Files
                    .readAllBytes(Paths.get("./src/main/resources/resources/LuxuryResourceTypesInformation.json")));
            typesMap = new Gson().fromJson(json, new TypeToken<HashMap<LuxuryResourceTypes, LuxuryResource>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (var element : typesMap.entrySet()) {
            System.out.println(element.getValue().getFood());
        }
    }

    public LuxuryResource(LuxuryResourceTypes type, double food, double production, double gold,
            Progresses requiredProgress) {
        super(food, production, gold, requiredProgress);
        this.type = type;
    }

    public LuxuryResource(LuxuryResourceTypes type) {
        super(type);
        this.type = type;

    }

    public static HashMap<LuxuryResourceTypes, LuxuryResource> typeMapGetter() {
        return typesMap;
    }
}
