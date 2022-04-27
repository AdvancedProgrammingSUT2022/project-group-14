package views;

import controllers.MapController;
import controllers.TileController;
import controllers.WorldController;
import enums.Commands;
import models.Tile;

import java.util.regex.Matcher;

public class GameCommandsValidation {

    public boolean checkCommands(String input) {
        Matcher matcher;

        if ((matcher = Commands.getMatcher(input, Commands.SHOW_INFO)) != null) {
            checkShowInfo(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.SELECT_UNIT)) != null) {
            checkSelectUnit(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.SELECT_CITY_BY_POSITION)) != null) {
            checkSelectCityByPosition(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.SELECT_CITY_BY_NAME)) != null) {
            GamePlay.selectCityByName(matcher.group("name"));
        } else if ((matcher = Commands.getMatcher(input, Commands.UNIT_MOVE_TO)) != null) {
            checkMoveTo(matcher);
        } else if (Commands.getMatcher(input, Commands.UNIT_ALERT) != null) {
            GamePlay.unitAlert();
        } else if (Commands.getMatcher(input, Commands.UNIT_SLEEP) != null) {
            GamePlay.unitSleep();
        } else if (Commands.getMatcher(input, Commands.UNIT_FORTIFY) != null) {
            GamePlay.unitFortify();
        } else if (Commands.getMatcher(input, Commands.UNIT_FORTIFY_HEAL) != null) {
            GamePlay.unitFortifyUntilHeal();
        } else if (Commands.getMatcher(input, Commands.UNIT_GARRISON) != null) {
            GamePlay.unitGarrison();
        } else if (Commands.getMatcher(input, Commands.UNIT_SETUP_RANGED) != null) {
            GamePlay.setupRanged();
        } else if ((matcher = Commands.getMatcher(input, Commands.UNIT_ATTACK)) != null) {
            checkAttack(matcher);
        } else if (Commands.getMatcher(input, Commands.UNIT_FOUND_CITY) != null) {
            GamePlay.foundCity();
        } else if (Commands.getMatcher(input, Commands.UNIT_CANCEL_MISSION) != null) {
            GamePlay.cancelMission();
        } else if (Commands.getMatcher(input, Commands.UNIT_WAKE) != null) {
            GamePlay.unitWake();
        } else if (Commands.getMatcher(input, Commands.UNIT_DELETE) != null) {
            GamePlay.deleteUnit();
        } else if ((matcher = Commands.getMatcher(input, Commands.UNIT_BUILD)) != null) {
            checkBuild(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.UNIT_REMOVE)) != null) {
            checkRemove(matcher);
        } else if (Commands.getMatcher(input, Commands.UNIT_REPAIR) != null) {
            GamePlay.repairCurrentTile();
        } else if ((matcher = Commands.getMatcher(input, Commands.MAP_SHOW_BY_POSITION)) != null) {
            checkShowMapByPosition(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.MAP_SHOW_BY_NAME)) != null) {
            checkShowMapByName(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.MAP_MOVE)) != null) {
            checkMapMove(matcher);
        } else if (Commands.getMatcher(input, Commands.END_GAME) != null) {
            WorldController.resetWorld();
            return false;
        } else if (Commands.getMatcher(input, Commands.NEXT_TURN) != null) {
            WorldController.nextTurn();
        } else System.out.println("invalid command");

        return true;
    }

    private boolean matcherPositionIsValid(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("y")) - 1;
        if (TileController.selectedTileIsValid(x, y)) {
            return true;
        }
        return false;
    }

    private void checkShowInfo(Matcher matcher) {
        String field = matcher.group("field");

        switch (field) {
            case "research" -> GamePlay.showResearches();
            case "units" -> GamePlay.showUnits();
            case "cities" -> GamePlay.showCities();
            case "diplomacy" -> GamePlay.showDiplomacyPanel();
            case "victory" -> GamePlay.showVictoryPanel();
            case "demographics" -> GamePlay.showDemographicsPanel();
            case "notifications" -> GamePlay.showNotifications();
            case "military" -> GamePlay.showMilitaryUnits();
            case "economic" -> GamePlay.showEconomicStatus();
            case "diplomatic" -> GamePlay.showDiplomaticHistory();
            case "deals" -> GamePlay.showDealsHistory();
            default -> {
            }
        }
    }

    private void checkSelectUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("y")) - 1;
        if (TileController.selectedTileIsValid(x, y)) {
            GamePlay.selectUnit(x, y, matcher.group("militaryStatus"));
            return;
        }
        System.out.println("the given position is invalid");
    }

    private void checkSelectCityByPosition(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("y")) - 1;
        if (TileController.selectedTileIsValid(x, y)) {
            GamePlay.selectCityByPosition(x, y);
            return;
        }
        System.out.println("the given position is invalid");
    }

    private void checkMoveTo(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("y")) - 1;
        if (TileController.selectedTileIsValid(x, y)) {
            GamePlay.moveTo(x, y);
            return;
        }
        System.out.println("the given position is invalid");
    }

    private void checkAttack(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("y")) - 1;
        if (TileController.selectedTileIsValid(x, y)) {
            GamePlay.attack(x, y);
            return;
        }
        System.out.println("the given position is invalid");

    }

    private void checkBuild(Matcher matcher) {
        String progress = matcher.group("progress");

        switch (progress) {
            case "road" -> GamePlay.buildRoadOnTile();
            case "railroad" -> GamePlay.buildRailroadOnTile();
            default -> GamePlay.buildProgressOnTile();
        }
    }

    private void checkRemove(Matcher matcher) {
        String foundation = matcher.group("foundation");

        if (foundation.equals("jungle"))
            GamePlay.removeJungleFromTile();
        else GamePlay.removeRoutsFromTile();
    }

    public void checkShowCombatUnitInfo() {

    }

    public void checkShowNonCombatUnitInfo() {

    }

    public boolean checkUnit(String input) {
        return true;
    }

    public void checkShowCityInfo() {
    }

    public void checkShowMapByPosition(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("y")) - 1;
        if (TileController.selectedTileIsValid(x, y)) {
            GamePlay.showMapBasedOnTile(x, y);
        } else System.out.println("given position is invalid");
    }

    public void checkShowMapByName(Matcher matcher) {
        String cityName = matcher.group("name");
        Tile tile = GamePlay.getTileByCityName(cityName);
        if (tile != null)
            GamePlay.showMapBasedOnTile(tile.getX(), tile.getY());
        else System.out.println("given city name is not valid");
    }

    public void checkMapMove(Matcher matcher) {
        String direction = matcher.group("direction");
        int movementAmount = Integer.parseInt(matcher.group("movementAmount"));

        Tile oldTile = WorldController.getSelectedTile();
        if (oldTile == null) {
            System.out.println("Tile is not selected");
            return;
        }
        int x = oldTile.getX();
        int y = oldTile.getY();

        switch (direction) {
            case "right" -> y += movementAmount;
            case "left" -> y -= movementAmount;
            case "down" -> x += movementAmount;
            case "up" -> x -= movementAmount;
        }

        if (TileController.selectedTileIsValid(x, y)){
            Tile newTile = MapController.getTileByCoordinates(x, y);
            GamePlay.showMapBasedOnTile(newTile.getX(), newTile.getY());

        }else System.out.println("the wanted movement can't be done");
    }

}
