package controllers;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import enums.Progresses;
import enums.tiles.TileType;
import models.Tile;

public class TileController {


   

    public static boolean selectedTileIsValid(int x, int y) {
        return false;

    }

    public static boolean tileHasJungle(Tile tile) {
        return false;

    }

    public void buildRoadOnTile() {

    }

    public void buildRailroadOnTile() {

    }

    public void buildProgressOnTile(Progresses progress) {

    }

    public void removeRoadAndRailroadFromTile() {

    }

    public void removeJungleFromTile() {

    }

    public void repairCurrentTile() {
        // TODO
    }

    public static boolean combatUnitExistsInTile(int x, int y) {
        return false;
    }

    public static boolean nonCombatUnitExistsInTile(int x, int y) {
        return false;
    }
}
