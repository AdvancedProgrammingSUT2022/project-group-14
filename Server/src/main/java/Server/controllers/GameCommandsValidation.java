package Server.controllers;

import Server.enums.Commands;
import Server.enums.QueryResponses;
import Server.enums.Technologies;
import Server.models.City;
import Server.models.Civilization;
import Server.models.network.Response;
import Server.models.units.Ranged;
import Server.models.units.Unit;

import java.util.HashMap;
import java.util.regex.Matcher;

public class GameCommandsValidation {

    public static void checkCommands(String input) {
        Matcher matcher;
        if ((matcher = Commands.getMatcher(input, Commands.INCREASE_GOODS)) != null) {
            checkIncreaseGoods(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.INCREASE_TURN)) != null) {
            checkIncreaseTurn(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.INCREASE_MP)) != null) {
            checkIncreaseMP(matcher);
        } else if (Commands.getMatcher(input, Commands.SEE_WHOLE_MAP) != null) {
            checkSeeWholeMap();
        } else if ((matcher = Commands.getMatcher(input, Commands.BUY_TILE_FREE)) != null) {
            checkBuyTileFree(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.INCREASE_RANGE)) != null) {
            checkIncreaseRange(matcher);
        } else if (Commands.getMatcher(input, Commands.GET_ALL_TECHS) != null) {
            checkGetAllTechs();
        } else if ((matcher = Commands.getMatcher(input, Commands.SET_SPAWN_LOCATION)) != null) {
            setSpawnLocations(matcher);
        } else {
            System.out.println("invalid command");
        }
    }

    public static void checkIncreaseGoods(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());

        switch (matcher.group("goods")) {
            case "gold":
                currentCivilization.setGold(currentCivilization.getGold() + amount);
                break;
            case "food":
                for (City city : currentCivilization.getCities())
                    city.setFood(city.getFood() + amount);
                break;
            case "production":
                for (City city : currentCivilization.getCities())
                    city.setProduction(city.getProduction() + amount);
                break;
            case "happiness":
                currentCivilization.setHappiness(currentCivilization.getHappiness() + amount);
                break;
            case "science":
                currentCivilization.setScience(currentCivilization.getScience() + amount);
                break;
            default:
                break;
        }
    }

    public static void checkIncreaseTurn(Matcher matcher) {
        for (int i = 0; i < Integer.parseInt(matcher.group("amount")); i++) {
            WorldController.nextTurn();
        }
        for (Civilization civilization : WorldController.getWorld().getAllCivilizations()) {
            if (civilization.getName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
                ServerUpdateController.sendUpdate(civilization.getName(), new Response(QueryResponses.CHANGE_SCENE, new HashMap<>(){{
                    put("width", String.valueOf(MapController.getWidth()));
                    put("height", String.valueOf(MapController.getHeight()));
                    put("sceneName", "gamePage");
                    put("turn", String.valueOf(true));
                }}));
            } else {
                ServerUpdateController.sendUpdate(civilization.getName(), new Response(QueryResponses.CHANGE_SCENE, new HashMap<>(){{
                    put("sceneName", "gamePage");
                    put("turn", String.valueOf(false));
                }}));
            }
        }
    }

    public static void checkIncreaseMP(Matcher matcher) {
        for (Unit unit : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits())
            unit.setMovementPoint(unit.getMovementPoint() + Integer.parseInt(matcher.group("amount")));

    }

    public static void checkSeeWholeMap() {
        int[][] visionStates = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getVisionStatesOfMap();
        for (int i = 0; i < MapController.getWidth(); i++) {
            for (int j = 0; j < MapController.getHeight(); j++) {
                visionStates[i][j] = 2;
            }
        }
    }

    public static void checkBuyTileFree(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("y")) - 1;
        if (!TileController.selectedTileIsValid(x, y)) {
            System.out.println("selected tile is not valid");
        } else if (MapController.getTileByCoordinates(x, y).getCivilizationName() != null) {
            System.out.println("tile is already in control of another civilization");
        } else {
            MapController.getTileByCoordinates(x, y).setCivilization(WorldController.getWorld().getCurrentCivilizationName());
        }
    }

    public static void checkIncreaseRange(Matcher matcher) {
        for (Unit unit : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits())
            if (unit instanceof Ranged)
                ((Ranged) unit).setRange(Integer.parseInt(matcher.group("amount")));
    }

    public static void checkGetAllTechs() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        for (Technologies value : Technologies.values())
            currentCivilization.getTechnologies().put(value, 0);
    }

    public static void setSpawnLocations(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("y")) - 1;
        WorldController.setCheatCoordination(x, y);
    }
}
