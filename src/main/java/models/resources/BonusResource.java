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

    public static void writeData() {
        try {

            FileWriter writer = new FileWriter("./src/main/resources/resources/BonusResourceTypesInformation.json");
            BonusResource resource = null;
            resource = new BonusResource(BonusResourceTypes.BANANA, 1, 0, 0, Improvements.FARM);
            typesMap.put(BonusResourceTypes.BANANA, resource);
            resource = new BonusResource(BonusResourceTypes.COW, 1, 0, 0, Improvements.PLANTATION);
            typesMap.put(BonusResourceTypes.COW, resource);
            resource = new BonusResource(BonusResourceTypes.GAZELLE, 1, 0, 0, Improvements.CAMP);
            typesMap.put(BonusResourceTypes.GAZELLE, resource);
            resource = new BonusResource(BonusResourceTypes.SHEEP, 2, 0, 0, Improvements.PLANTATION);
            typesMap.put(BonusResourceTypes.SHEEP, resource);
            resource = new BonusResource(BonusResourceTypes.WHEAT, 1, 0, 0, Improvements.FARM);
            typesMap.put(BonusResourceTypes.WHEAT, resource);

            writer.write(new Gson().toJson(typesMap));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readData() {
        try {
            String json = new String(Files
                    .readAllBytes(Paths.get("./src/main/resources/resources/BonuxResourceTypesInformation.json")));
            typesMap = new Gson().fromJson(json, new TypeToken<HashMap<BonusResourceTypes, BonusResource>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (var element : typesMap.entrySet()) {
            System.out.println(element.getValue().getFood());
        }
    }

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
