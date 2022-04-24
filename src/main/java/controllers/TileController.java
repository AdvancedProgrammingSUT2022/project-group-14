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
import models.World;

public class TileController {


    public static boolean selectedTileIsValid(int x, int y) {
        return x <= World.getWidth() && y <= World.getLength() && x > 0 && y > 0;
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

    public static boolean combatUnitExistsInTile(Tile tile) {
        return tile.getCombatUnit() != null;
    }

    public static boolean nonCombatUnitExistsInTile(Tile tile) {
        return tile.getNonCombatUnit() != null;
    }
}
