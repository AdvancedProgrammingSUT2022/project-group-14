package controllers;

import models.City;
import models.Civilization;
import models.units.Unit;

import java.util.ArrayList;

public class CivilizationController {

    public static void updateMapVision(Civilization civilization) {
        ArrayList<Unit> allUnits = civilization.getAllUnits();
        if (true) {
            //TODO if it was near our units or cities is lucid
        } else {
            //TODO if it isn't and the tile is lucid change to half lucid
        }
    }

    public static void updateTechnology(Civilization civilization) {
        civilization.getTechnologies().put(civilization.getCurrentTechnology(),
                civilization.getTechnologies().get(civilization.getCurrentTechnology())-1);
        if (civilization.getTechnologies().get(civilization.getCurrentTechnology()) <= 0) {
            civilization.setCurrentTechnology(null);
        }
    }

    public static void updateGoods(Civilization civilization) {
        for (City city : civilization.getCities()) {
            //TODO add each city goods
        }
    }
}
