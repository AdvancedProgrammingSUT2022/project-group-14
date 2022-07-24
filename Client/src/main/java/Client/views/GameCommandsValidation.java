package Client.views;

import Client.enums.Commands;
import Client.enums.Technologies;
import Client.models.City;
import Client.models.Civilization;
import Client.models.units.Unit;
import Common.models.units.Unit;
import Common.enums.Commands;
import Common.enums.Technologies;
import Common.models.City;
import Server.models.Civilization;
import Common.models.units.Ranged;

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
        for (int i = 0; i < Integer.parseInt(matcher.group("amount")); i++)
            WorldController.nextTurn();
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
