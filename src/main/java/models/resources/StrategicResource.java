package models.resources;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import enums.Improvements;
import enums.Technologies;
import enums.resources.StrategicResourceTypes;
import models.Building;
import models.units.Unit;

public class StrategicResource extends Resource {
    private Building building;
    private Technologies requiredTechnology;
    private StrategicResourceTypes type;
    private ArrayList<Building> dependentBuildings = new ArrayList<>();
    private ArrayList<Unit> dependentUnit = new ArrayList<>();

    private static HashMap<StrategicResourceTypes, StrategicResource> typesMap = new HashMap<>();

    public static void writeData() {
        try {

            FileWriter writer = new FileWriter("./src/main/resources/resources/StrategicResourceTypesInformation.json");
            StrategicResource resource = null;
            resource = new StrategicResource(StrategicResourceTypes.COAL, 0, 1, 0, Improvements.MINE,
                    Technologies.SCIENTIFIC_THEORY);
            typesMap.put(StrategicResourceTypes.COAL, resource);
            resource = new StrategicResource(StrategicResourceTypes.HORSE, 0, 1, 0, Improvements.PLANTATION,
                    Technologies.ANIMAL_HUSBANDRY);
            typesMap.put(StrategicResourceTypes.HORSE, resource);
            resource = new StrategicResource(StrategicResourceTypes.IRON, 0, 1, 0, Improvements.MINE,
                    Technologies.IRON_WORKING);
            typesMap.put(StrategicResourceTypes.IRON, resource);

            writer.write(new Gson().toJson(typesMap));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void readData() {
        try {
            String json = new String(Files
                    .readAllBytes(Paths.get("./src/main/resources/resources/StrategicResourceTypesInformation.json")));
            typesMap = new Gson().fromJson(json, new TypeToken<HashMap<StrategicResourceTypes, StrategicResource>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }

        for(var element: typesMap.entrySet())
        {
            System.out.println(element.getValue().getFood());
        }
    }

    public StrategicResource(StrategicResourceTypes type, double food, double production, double gold,
            Improvements requiredProgress, Technologies requiredTechnology) {
        super(food, production, gold, requiredProgress);
        this.type = type;
        this.requiredTechnology = requiredTechnology;
    }

    public StrategicResource(StrategicResourceTypes type) {
        super(type);
        this.type = type;
        this.requiredTechnology = requiredTechnology;
    }

    public static HashMap<StrategicResourceTypes, StrategicResource> typesMapGetter() {
        return typesMap;
    }

    public Building getBuilding() {
        return this.building;
    }

    public Technologies getRequiredTechnology() {
        return this.requiredTechnology;
    }

    public StrategicResourceTypes getType() {
        return this.type;
    }

    public ArrayList<Building> getDependentBuildings() {
        return this.dependentBuildings;
    }

    public ArrayList<Unit> getDependentUnit() {
        return this.dependentUnit;
    }
    

}
