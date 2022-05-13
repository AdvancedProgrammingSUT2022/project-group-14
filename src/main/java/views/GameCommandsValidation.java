package views;

import controllers.CityController;
import controllers.MapController;
import controllers.TileController;
import controllers.WorldController;
import enums.Buildings;
import enums.Commands;
import enums.Technologies;
import enums.units.Unit;
import models.Building;
import models.City;
import models.Civilization;
import models.Tile;
import models.units.Ranged;

import java.util.Map;
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
            GamePlay.unitFortifyUntilHealed();
        } else if (Commands.getMatcher(input, Commands.UNIT_GARRISON) != null) {
            GamePlay.unitGarrison();
        } else if (Commands.getMatcher(input, Commands.UNIT_SETUP_RANGED) != null) {
            GamePlay.setupRanged();
        } else if ((matcher = Commands.getMatcher(input, Commands.UNIT_ATTACK)) != null) {
            checkAttack(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.UNIT_PILLAGE)) != null) {
            checkPillage(matcher);
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
            GamePlay.nextTurn();
        } else if ((matcher = Commands.getMatcher(input, Commands.LOCK_CITIZEN)) != null) {
            checkLockCitizen(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.UNLOCK_CITIZEN)) != null) {
            GamePlay.unlockCitizen(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.CITY_PRODUCE)) != null) {
            checkCityProduce(matcher);
        } else if (Commands.getMatcher(input, Commands.CANCEL_CURRENT_RESEARCH) != null) {
            GamePlay.cancelCurrentResearch();
        } else if ((matcher = Commands.getMatcher(input, Commands.START_RESEARCH)) != null) {
            checkStartResearch(matcher);
        }  else if ((matcher = Commands.getMatcher(input, Commands.BUY_TILE)) != null) {
            checkBuyTile(matcher);
        } else if ((matcher = Commands.getMatcher(input, Commands.UPGRADE_UNIT)) != null) {
            checkUpgradeUnit(matcher);
        } else if (Commands.getMatcher(input, Commands.SHOW_CITY_BANNER) != null) {
            GamePlay.showCityBanner();
        } else if (Commands.getMatcher(input, Commands.SHOW_EMPLOYED_CITIZENS) != null) {
            GamePlay.showEmployedCitizens();
        } else if (Commands.getMatcher(input, Commands.SHOW_UNEMPLOYED_CITIZENS) != null) {
            GamePlay.showUnemployedCitizens();
        } else if ((matcher = Commands.getMatcher(input, Commands.INCREASE_GOODS)) != null) {
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
        } else System.out.println("invalid command");

        return true;
    }

    private void checkShowInfo(Matcher matcher) {
        String field = matcher.group("field");

        switch (field) {
            case "research" -> GamePlay.researchesPanel();
            case "units" -> GamePlay.unitsPanel();
            case "cities" -> GamePlay.citiesPanel();
            case "demographics" -> GamePlay.demographicsPanel();
            case "notifications" -> GamePlay.notificationsPanel();
            case "military" -> GamePlay.militaryPanel();
            case "economic" -> GamePlay.economicStatusPanel();
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

    private void checkPillage(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("y")) - 1;
        if (TileController.selectedTileIsValid(x, y)) {
            GamePlay.pillage(x, y);
            return;
        }
        System.out.println("the given position is invalid");

    }

    private void checkBuild(Matcher matcher) {
        String progress = matcher.group("progress");

        switch (progress) {
            case "road" -> GamePlay.buildRoadOnTile();
            case "railroad" -> GamePlay.buildRailroadOnTile();
            default -> GamePlay.buildImprovementOnTile(progress);
        }
    }

    private void checkRemove(Matcher matcher) {
        String foundation = matcher.group("foundation");

        switch (foundation) {
            case "jungle" -> GamePlay.removeJungleFromTile();
            case "forest" -> GamePlay.removeForestFromTile();
            case "marsh" -> GamePlay.removeMarshFromTile();
            default -> GamePlay.removeRoutsFromTile();
        }
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
        City wantedCity = CityController.getCityByName(cityName);
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        if (wantedCity == null) {
            System.out.println("given city name is not valid");
        } else if (currentCivilization.getVisionStatesOfMap()[wantedCity.getCenterOfCity().getX()][wantedCity.getCenterOfCity().getY()] != 2) {
            System.out.println("you don't have vision on that city");
        } else {
            GamePlay.showMapBasedOnTile(wantedCity.getCenterOfCity().getX(), wantedCity.getCenterOfCity().getY());
        }
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

        if (TileController.selectedTileIsValid(x, y)) {
            Tile newTile = MapController.getTileByCoordinates(x, y);
            GamePlay.showMapBasedOnTile(newTile.getX(), newTile.getY());

        } else System.out.println("the wanted movement can't be done");
    }

    public void checkLockCitizen(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("y")) - 1;
        if (TileController.selectedTileIsValid(x, y)) {
            int id = Integer.parseInt(matcher.group("id"));
            GamePlay.lockCitizen(x, y, id);
            return;
        }
        System.out.println("the given position is invalid");
    }

    public void checkCityProduce(Matcher matcher) {
        if (WorldController.getSelectedCity() == null) {
            System.out.println("you should select a city first");
            return;
        }

        String type = matcher.group("type");
        String productionName = matcher.group("productionName");
        String payment = matcher.group("payment");

        if (type.equals("unit")) {
            Unit unit = Unit.getUnitByName(productionName.toUpperCase());
            if (unit == null) System.out.println("no such unit exists");
            else {
                GamePlay.startProducingUnit(unit, payment);
            }
        } else {
            Buildings buildings = Buildings.getBuildingByName(productionName.toUpperCase());
            if (buildings == null) System.out.println("no such building exists");
            else {
                Building building = new Building(buildings);
                GamePlay.startProducingBuilding(building, payment);
            }
        }
    }

    public void checkStartResearch(Matcher matcher) {
        String technologyName = matcher.group("technology");
        Technologies technologies = Technologies.getTechnologyByName(technologyName);
        if (technologies == null)
            System.out.println("no such technology exists");
        else GamePlay.startResearch(technologies);
    }

    public void checkBuyTile(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("y")) - 1;
        if (TileController.selectedTileIsValid(x, y)) {
            GamePlay.buyTile(x, y);
        }
    }

    public void checkUpgradeUnit(Matcher matcher){
        String unitName = matcher.group("unitName");
        Unit unit = Unit.getUnitByName(unitName.toUpperCase());

        if (unit == null) System.out.println("no such unit exists");
        else {
            GamePlay.upgradeUnit(unit);
        }
    }

    public void checkIncreaseGoods(Matcher matcher) {
        String goodsName = matcher.group("goods");
        int amount = Integer.parseInt(matcher.group("amount"));
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());

        switch (goodsName) {
            case "gold":
                currentCivilization.addGold(amount);
                break;
            case "food":
                for (City city : currentCivilization.getCities()) {
                    city.setFood(city.getFood() + amount);
                }
                break;
            case "production":
                for (City city : currentCivilization.getCities()) {
                    city.setProduction(city.getProduction() + amount);
                }
                break;
            case "happiness":
                currentCivilization.addHappiness(amount);
                break;
            case "science":
                currentCivilization.addScience(amount);
                break;
        }
    }

    public void checkIncreaseTurn(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        for (int i = 0; i < amount; i++) {
            WorldController.nextTurn();
        }
    }

    public void checkIncreaseMP(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        for (models.units.Unit unit : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits()) {
            unit.setMovementPoint(unit.getMovementPoint() + amount);
        }

    }

    public void checkSeeWholeMap() {
        int[][] visionStates = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getVisionStatesOfMap();
        for (int i = 0; i < MapController.getWidth(); i++) {
            for (int j = 0; j < MapController.getLength(); j++) {
                visionStates[i][j] = 2;
            }
        }
    }

    public void checkBuyTileFree(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x")) - 1;
        int y = Integer.parseInt(matcher.group("Y")) - 1;
        if (!TileController.selectedTileIsValid(x, y)) {
            System.out.println("selected tile is not valid");
        } else if (MapController.getTileByCoordinates(x, y).getCivilizationName() != null) {
            System.out.println("tile is already in control of another civilization");
        } else {
            MapController.getTileByCoordinates(x, y).setCivilization(WorldController.getWorld().getCurrentCivilizationName());
        }
    }

    public void checkIncreaseRange(Matcher matcher) {
        int amount = Integer.parseInt(matcher.group("amount"));
        for (models.units.Unit unit : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits()) {
            if (unit instanceof Ranged)
                ((Ranged) unit).setRange(amount);
        }
    }

    public void checkGetAllTechs() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        for (Technologies value : Technologies.values()) {
            currentCivilization.getTechnologies().put(value, 0);
        }
    }
}
