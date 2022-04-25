package views;

import controllers.TileController;
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
            GamePlay.selectCityByName(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.UNIT_MOVE_TO)) != null) {
            checkMoveTo(matcher);
        } else if (Commands.getMatcher(input, Commands.UNIT_ALERT) != null) {
            GamePlay.unitAlert();
        } else if (Commands.getMatcher(input, Commands.UNIT_SLEEP) != null) {
            GamePlay.unitSleep();
        } else if (Commands.getMatcher(input, Commands.UNIT_FORTIFY) != null) {
            GamePlay.unitFortify();
        } else if (Commands.getMatcher(input, Commands.UNIT_FORTIFY_HEAL) != null) {
            GamePlay.unitFortifyHeal();
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
        } else System.out.println("invalid command");

        return true;

    }

    private boolean matcherPositionIsValid(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (TileController.selectedTileIsValid(x, y)) {
            return true;
        }
        return false;
    }

    private void checkShowInfo(Matcher matcher) {
        String field = matcher.group("field");

        switch (field) {
            case "research":
                GamePlay.showResearches();
                break;
            case "units":
                GamePlay.showUnits();
                break;
            case "cities":
                GamePlay.showCities();
                break;
            case "diplomacy":
                GamePlay.showDiplomacyPanel();
                break;
            case "victory":
                GamePlay.showVictoryPanel();
                break;
            case "demographics":
                GamePlay.showDemographicsPanel();
                break;
            case "notifications":
                GamePlay.showNotifications();
                break;
            case "military":
                GamePlay.showMilitaryUnits();
                break;
            case "economic":
                GamePlay.showEconomicStatus();
                break;
            case "diplomatic":
                GamePlay.showDiplomaticHistory();
                break;
            case "deals":
                GamePlay.showDealsHistory();
                break;
            default:
        }
    }

    private void checkSelectUnit(Matcher matcher) {
        if (matcherPositionIsValid(matcher)) {
            GamePlay.selectUnit(matcher);
            return;
        }
        System.out.println("the given position is invalid");
    }

    private void checkSelectCityByPosition(Matcher matcher) {
        if (matcherPositionIsValid(matcher)) {
            GamePlay.selectCityByPosition(matcher);
            return;
        }
        System.out.println("the given position is invalid");
    }

    private void checkMoveTo(Matcher matcher) {
        if (matcherPositionIsValid(matcher)) {
            GamePlay.moveTo(matcher);
            return;
        }
        System.out.println("the given position is invalid");
    }

    private void checkAttack(Matcher matcher) {
        if (matcherPositionIsValid(matcher)) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            GamePlay.attack(x, y);
            return;
        }
        System.out.println("the given position is invalid");

    }

    private void checkBuild(Matcher matcher) {
        String progress = matcher.group("progress");

        switch (progress) {
            case "road":
                GamePlay.buildRoadOnTile();
                break;
            case "railroad":
                GamePlay.buildRailroadOnTile();
                break;
            default:
                GamePlay.buildProgressOnTile();
        }
    }

    private void checkRemove(Matcher matcher) {
        String foundation = matcher.group("foundation");

        if (foundation.equals("jungle"))
            GamePlay.removeJungleFromTile();
        else GamePlay.removeRoadAndRailroadFromTile();
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
        if (matcherPositionIsValid(matcher)) {
            int x = Integer.parseInt(matcher.group("x"));
            int y = Integer.parseInt(matcher.group("y"));
            Tile tile = GamePlay.getTileByPosition(x, y);
            GamePlay.showMapBasedOnTile(tile);
        } else System.out.println("given position is invalid");
    }

    public void checkShowMapByName(Matcher matcher) {
        String cityName = matcher.group("name");
        Tile tile = GamePlay.getTileByCityName(cityName);
        if (tile != null)
            GamePlay.showMapBasedOnTile(tile);
        else System.out.println("given city name is not valid");
    }

    public void checkMapMove(Matcher matcher) {
        String direction = matcher.group("direction");
        int movementAmount = Integer.parseInt(matcher.group("movementAmount"));

        Tile oldTile = GamePlay.getSelectedTile();
        if (oldTile == null) {
            System.out.println("Tile is not selected");
            return;
        }
        int x = oldTile.getX() + 1;
        int y = oldTile.getY() + 1;

        switch (direction) {
            case "right":
                y += movementAmount;
                break;
            case "left":
                y -= movementAmount;
                break;
            case "down":
                x += movementAmount;
                break;
            case "up":
                x -= movementAmount;
        }

        if (TileController.selectedTileIsValid(x, y)){
            Tile newTile = GamePlay.getTileByPosition(x, y);
            GamePlay.showMapBasedOnTile(newTile);

        }else System.out.println("the wanted movement can't be done");
    }

}
