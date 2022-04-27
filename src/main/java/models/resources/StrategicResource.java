package models.resources;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;

import enums.Progresses;
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

    static HashMap<StrategicResourceTypes, StrategicResource> TypesMap = new HashMap<>();

    public static void writeData() {
        try {

            FileWriter writer = new FileWriter("./src/main/resources/resources/StrategicRsourceTypesInformation.json");
            StrategicResource resource = null;
            resource = new StrategicResource(StrategicResourceTypes.COAL, 0, 1, 0, Progresses.MINE,
                    Technologies.SCIENTIFIC_THEORY);
            TypesMap.put(StrategicResourceTypes.COAL, resource);
            resource = new StrategicResource(StrategicResourceTypes.HORSE, 0, 1, 0, Progresses.PLANTATION,
                    Technologies.ANIMAL_HUSBANDRY);
            TypesMap.put(StrategicResourceTypes.HORSE, resource);
            resource = new StrategicResource(StrategicResourceTypes.IRON, 0, 1, 0, Progresses.MINE,
                    Technologies.IRON_WORKING);
            TypesMap.put(StrategicResourceTypes.IRON, resource);

            writer.write(new Gson().toJson(TypesMap));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public StrategicResource(StrategicResourceTypes type, double food, double production, double gold,
            Progresses requiredProgress, Technologies requiredTechnology) {
        super(food, production, gold, requiredProgress);
        this.type = type;
        this.requiredTechnology = requiredTechnology;
    }


}
