package views;

import controllers.*;
import enums.Colors;
import enums.Improvements;
import enums.Technologies;
import models.Building;
import models.City;
import models.Civilization;
import models.Tile;
import models.units.CombatUnit;
import models.units.Settler;
import models.units.Unit;
import models.units.Worker;

import java.util.*;
import java.util.regex.Matcher;

public class GamePlay {
    public static Scanner scanner;

    public static void run(Scanner sc) {
        System.out.println("WELCOME TO CIVILIZATION");
        String input;
        scanner = sc;
        GameCommandsValidation gameCommandsValidation = new GameCommandsValidation();
        do {
            input = scanner.nextLine();
        } while (gameCommandsValidation.checkCommands(input));
    }

    // selecting methods
    public static void selectUnit(int x, int y, String militaryStatus) {
        Tile wantedTile = MapController.getTileByCoordinates(x, y);
        if (militaryStatus.equals("combat")) {
            if (TileController.combatUnitExistsInTile(wantedTile)) {
                WorldController.setSelectedCombatUnit(wantedTile.getCombatUnit());
                WorldController.setSelectedNonCombatUnit(null);
                showCombatUnitInfo();
            } else {
                System.out.println("there isn't any combat unit in this position");
            }
        } else {
            if (TileController.nonCombatUnitExistsInTile(wantedTile)) {
                WorldController.setSelectedNonCombatUnit(wantedTile.getNonCombatUnit());
                WorldController.setSelectedCombatUnit(null);
                showNonCombatUnitInfo();
            } else {
                System.out.println("there isn't any non combat unit in this position");
            }
        }
    }

    public static void selectCityByPosition(int x, int y) {
        if (MapController.getTileByCoordinates(x, y).getCity() == null) {
            System.out.println("there is not any cities over there");
        } else {
            WorldController.setSelectedCity(MapController.getTileByCoordinates(x, y).getCity());
        }
    }

    public static void selectCityByName(String name) {
        ArrayList<String> allCitiesNames = new ArrayList<>();
        for (Civilization civilization : WorldController.getWorld().getAllCivilizations()) {
            for (City city : civilization.getCities()) {
                allCitiesNames.add(city.getName());
            }
        }
        if (!allCitiesNames.contains(name)) {
            System.out.println("given name is invalid");
        } else {
            WorldController.setSelectedCity(CityController.getCityByName(name));
        }
    }

    // panels
    public static void researchesPanel() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        System.out.println("You are now researching the " + currentCivilization.getCurrentTechnology().getName() +
                " technology, and you will unlock it after " + currentCivilization.getTechnologies().get(currentCivilization.getCurrentTechnology()) +
                " turns");
    }

    public static void unitsPanel() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        for (Unit unit : currentCivilization.getAllUnits()) {
            System.out.println(unit.getInfo());
        }
    }

    public static void citiesPanel() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        int counter = 1;
        for (City city : currentCivilization.getCities()) {
            int x = city.getCenterOfCity().getX() + 1, y = city.getCenterOfCity().getY() + 1;
            System.out.println(counter + "-> " + city.getName() + " with ( " + x + " , " + y + " )" + " coordinates");
        }
    }

    public static void demographicsPanel() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        System.out.println(currentCivilization.getInfo());
    }

    public static void notificationsPanel() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        for (String notification : currentCivilization.getNotifications()) {
            System.out.println(notification);
        }
    }

    public static void militaryPanel() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        int totalValueOfCombatUnits = 0, totalCombatUnits = 0;
        for (Unit unit : currentCivilization.getAllUnits()) {
            if (unit instanceof CombatUnit) {
                totalValueOfCombatUnits += enums.units.Unit.getUnitByName(unit.getName()).getCost();
                totalCombatUnits++;
            }
        }
        System.out.println("You have " + currentCivilization.getCities().size() + " cities in total and " +
                totalCombatUnits + " combat units with total value of " + totalValueOfCombatUnits + " and your combat units are : ");
        int counter = 1;
        for (City city : currentCivilization.getCities()) {
            System.out.println(counter + "-> " + city.getCombatInfo());
            counter++;
        }
        counter = 1;
        for (Unit unit : currentCivilization.getAllUnits()) {
            if (unit instanceof CombatUnit) {
                System.out.println(counter + "-> " + ((CombatUnit) unit).getCombatInfo());
                counter++;
            }
        }
    }

    public static void economicStatusPanel() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        int counter = 1;
        for (City city : currentCivilization.getCities()) {
            System.out.println(counter + "-> " + city.getInfo());
            counter++;
        }
    }

    public static void diplomacyPanel() {
    }

    public static void victoryPanel() {
    }

    public static void diplomaticHistoryPanel() {
    }

    public static void dealsHistoryPanel() {
    }

    public static void showCombatUnitInfo() {
        //TODO these are temporary
        System.out.println(WorldController.getSelectedCombatUnit().getInfo());
    }

    public static void showNonCombatUnitInfo() {
        //TODO these are temporary
        System.out.println(WorldController.getSelectedNonCombatUnit().getInfo());
    }

    // showing map methods
    public static void showMapBasedOnTile(int x, int y) {
        int[] tileCenter = MapController.getTileCenterByCoordinates(x, y);
        WorldController.setSelectedTile(MapController.getTileByCoordinates(x, y));
        showMapByCoordinates(Math.max(0, tileCenter[0] - 11), Math.max(0, tileCenter[1] - 28), Math.min(MapController.outputMapWidth, tileCenter[0] + 11), Math.min(MapController.outputMapLength, tileCenter[1] + 28));
    }

    public static void showMapByCoordinates(int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                System.out.print(MapController.getCellsMap()[i][j].getColor().getAnsiEscapeCode() +"\u001B[1m"+ MapController.getCellsMap()[i][j].getCharacter() + Colors.RESET.getAnsiEscapeCode());
            }
            System.out.println();
        }
    }

    // units methods
    public static void attack(int x, int y) {
        String error;
        if (MapController.getTileByCoordinates(x, y).getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            System.out.println("can't attack your own base");
        } else if (WorldController.getSelectedCombatUnit() == null){
            System.out.println("you should select a combat unit to attack");
        } else if ((error = WarController.combatUnitAttacksTile(x, y)) != null){
            System.out.println(error);
        }
    }

    public static void pillage(int x, int y) {
        String error;
        if (WorldController.getSelectedCombatUnit() == null){
            System.out.println("you should select a combat unit to attack");
        } else if (MapController.getTileByCoordinates(x, y).getRoadState() != 0 &&
                MapController.getTileByCoordinates(x, y).getRailRoadState() != 0 &&
                MapController.getTileByCoordinates(x, y).getImprovementTurnsLeftToBuild() != 0) {
            System.out.println("there isn't anything for you to pillage");
        } else if ((error = UnitController.pillage(x, y)) != null) {
            System.out.println(error);
        }
    }

    public static void conquerCity(City city, CombatUnit unit) {
        if (WorldController.getWorld().getCivilizationByName(city.getCenterOfCity().getCivilizationName()).getFirstCapital() == city) {
            System.out.println("Congrats! you attached the city to your civilization");
            CityController.conquerCity(city, unit);
        } else {
            System.out.println("If you want to conquer city type 1 otherwise type 0 :");
            if (scanner.nextInt() == 1) {
                System.out.println("Congrats! you attached the city to your civilization");
                CityController.conquerCity(city, unit);
            } else {
                System.out.println("Congrats! you destroyed the city");
                CityController.destroyCity(city, unit);
            }
        }
    }

    public static void moveTo(int x, int y) {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null) {
            if ((error = UnitController.setUnitDestinationCoordinates(WorldController.getSelectedCombatUnit(), x, y)) != null) {
                System.out.println(error);
            }
        } else {
            if ((error = UnitController.setUnitDestinationCoordinates(WorldController.getSelectedNonCombatUnit(), x, y)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void cancelMission() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null) {
            if ((error = UnitController.cancelMission(WorldController.getSelectedCombatUnit())) != null) {
                System.out.println(error);
            }
        } else {
            if ((error = UnitController.cancelMission(WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitSleep() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null) {
            if ((error = UnitController.sleepUnit(WorldController.getSelectedCombatUnit())) != null) {
                System.out.println(error);
            }
        } else {
            if ((error = UnitController.sleepUnit(WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitWake() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null) {
            if ((error = UnitController.wakeUp(WorldController.getSelectedCombatUnit())) != null) {
                System.out.println(error);
            }
        } else {
            if ((error = UnitController.wakeUp(WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitAlert() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedNonCombatUnit() != null) {
            System.out.println("the selected unit is not a combat unit");
        } else {
            if ((error = UnitController.alertUnit(WorldController.getSelectedCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitFortify() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedNonCombatUnit() != null) {
            System.out.println("the selected unit is not a combat unit");
        } else {
            if ((error = UnitController.fortifyUnit(WorldController.getSelectedCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitFortifyUntilHealed() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedNonCombatUnit() != null) {
            System.out.println("the selected unit is not a combat unit");
        } else {
            if ((error = UnitController.fortifyUnitUntilHealed(WorldController.getSelectedCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitGarrison() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedNonCombatUnit() != null) {
            System.out.println("the selected unit is not combat unit");
        } else {
            if ((error = UnitController.garrisonCity(WorldController.getSelectedCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void setupRanged() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else {
            if ((error = UnitController.setupRangedUnit(WorldController.getSelectedCombatUnit(), 0, 0)) != null) {
                System.out.println(error);
            }
        }
    }

    //non combat methods
    public static void foundCity() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null ||
                (WorldController.getSelectedNonCombatUnit() != null && WorldController.getSelectedNonCombatUnit() instanceof Worker)) {
            System.out.println("the selected unit is not a settler");
        } else {
            if ((error = UnitController.foundCity((Settler) WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void buildRoadOnTile() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null ||
                (WorldController.getSelectedNonCombatUnit() != null && WorldController.getSelectedNonCombatUnit() instanceof Settler)) {
            System.out.println("the selected unit is not a worker");
        } else {
            if ((error = UnitController.buildRoad((Worker) WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void buildRailroadOnTile() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null ||
                (WorldController.getSelectedNonCombatUnit() != null && WorldController.getSelectedNonCombatUnit() instanceof Settler)) {
            System.out.println("the selected unit is not a worker");
        } else {
            if ((error = UnitController.buildRailRoad((Worker) WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void removeRoutsFromTile() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null ||
                (WorldController.getSelectedNonCombatUnit() != null && WorldController.getSelectedNonCombatUnit() instanceof Settler)) {
            System.out.println("the selected unit is not a worker");
        } else {
            if ((error = UnitController.removeRouteFromTile((Worker) WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void buildImprovementOnTile(String improvement) {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null ||
                (WorldController.getSelectedNonCombatUnit() != null && WorldController.getSelectedNonCombatUnit() instanceof Settler)) {
            System.out.println("the selected unit is not a worker");
        } else {
            Improvements wantedImprovement = Improvements.getImprovementByName(improvement);
            if ((error = UnitController.buildImprovement((Worker) WorldController.getSelectedNonCombatUnit(), wantedImprovement)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void removeJungleFromTile() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null ||
                (WorldController.getSelectedNonCombatUnit() != null && WorldController.getSelectedNonCombatUnit() instanceof Settler)) {
            System.out.println("the selected unit is not a worker");
        } else {
            if ((error = UnitController.removeJungleFromTile((Worker) WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void removeForestFromTile() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null ||
                (WorldController.getSelectedNonCombatUnit() != null && WorldController.getSelectedNonCombatUnit() instanceof Settler)) {
            System.out.println("the selected unit is not a worker");
        } else {
            if ((error = UnitController.removeForestFromTile((Worker) WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void removeMarshFromTile() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null ||
                (WorldController.getSelectedNonCombatUnit() != null && WorldController.getSelectedNonCombatUnit() instanceof Settler)) {
            System.out.println("the selected unit is not a worker");
        } else {
            if ((error = UnitController.removeMarshFromTile((Worker) WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void repairCurrentTile() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null ||
                (WorldController.getSelectedNonCombatUnit() != null && WorldController.getSelectedNonCombatUnit() instanceof Settler)) {
            System.out.println("the selected unit is not a worker");
        } else {
            if ((error = UnitController.repairTile((Worker) WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void deleteUnit() {
        String error;
        if (WorldController.unitIsNotSelected()) {
            System.out.println("you haven't selected a unit yet");
        } else if (WorldController.getSelectedCombatUnit() != null) {
            if ((error = UnitController.delete(WorldController.getSelectedCombatUnit())) != null) {
                System.out.println(error);
            }
        } else {
            if ((error = UnitController.delete(WorldController.getSelectedNonCombatUnit())) != null) {
                System.out.println(error);
            }
        }
    }

    public static void lockCitizen(int x, int y, int id) {
        if (WorldController.getSelectedCity() == null)
            System.out.println("you should select a city first");
        else {
            String message = CityController.lockCitizenToTile(WorldController.getSelectedCity(), id, x, y);
            System.out.println(message);
        }
    }

    public static void unlockCitizen(Matcher matcher) {
        if (WorldController.getSelectedCity() == null)
            System.out.println("you should select a city first");
        else {
            int id = Integer.parseInt(matcher.group("id"));
            String message = CityController.unlockCitizenFromTile(WorldController.getSelectedCity(), id);
            System.out.println(message);
        }
    }

    public static void startProducingBuilding(Building building, String payment) {
        String error;
        if ((error = CityController.producingBuilding(building, payment)) != null) {
            System.out.println(error);
        }
    }

    public static void startProducingUnit(enums.units.Unit unitEnum, String payment) {
        String error;
        if ((error = CityController.producingUnit(unitEnum, payment)) != null) {
            System.out.println(error);
        }
    }

    public static void nextTurn() {
        String error;
        if ((error = WorldController.nextTurnImpossible()) != null) {
            System.out.println(error);
        } else {
            Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
            for (City city : currentCivilization.getCities()) {
                if ((error = CityController.cityProductionWarnings(city)) != null)
                    System.out.println(city.getName() + " : " + error);
            }
            WorldController.nextTurn();
            System.out.println(WorldController.getWorld().getCurrentCivilizationName() + "'s turn");
            currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
            if (currentCivilization.getCurrentCapital() != null) {
                showMapBasedOnTile(currentCivilization.getCurrentCapital().getCenterOfCity().getX(),
                        currentCivilization.getCurrentCapital().getCenterOfCity().getY());
            }else {
                showMapBasedOnTile(currentCivilization.getAllUnits().get(0).getCurrentX(),
                        currentCivilization.getAllUnits().get(0).getCurrentY());
            }
        }
    }

    public static void cancelCurrentResearch() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        currentCivilization.setCurrentTechnology(null);
    }

    public static void startResearch(Technologies technology) {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        if (currentCivilization.getTechnologies().get(technology) <= 0) {
            System.out.println("you have already studied this technology");
            return;
        }
        for (String requiredTechnologyName : technology.getRequiredTechnologies()) {
            Technologies requiredTechnology = Technologies.getTechnologyByName(requiredTechnologyName);
            if (currentCivilization.getTechnologies().get(requiredTechnology) > 0) {
                System.out.println("you should first study " + requiredTechnologyName.toLowerCase(Locale.ROOT));
                return;
            }
        }
        currentCivilization.setCurrentTechnology(technology);
        String notification = "In turn " + WorldController.getWorld().getActualTurn() + " you started researching " +
                technology.getName() + " technology";
        currentCivilization.addNotification(notification);

    }

    public static void buyTile(int x, int y) {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        if (MapController.getTileByCoordinates(x, y).getCivilizationName() != null) {
            System.out.println("this tile belongs to a civilization");
            return;
        }
        for (City city : currentCivilization.getCities()) {
            for (Tile tile : city.getTerritory()) {
                if (TileController.coordinatesAreInRange(tile.getX(), tile.getY(), x, y, 1)) {
                    System.out.println(CityController.buyTileAndAddItToCityTerritory(currentCivilization, city, x, y));
                    return;
                }
            }
        }
        System.out.println("you should choose a neighbor tile to one of your cities territory");
    }

    public static void upgradeUnit(enums.units.Unit unitEnum) {
        String error;
        if ((error = UnitController.upgradeUnit(unitEnum)) != null) {
            System.out.println(error);
        } else System.out.println("unit upgraded successfully");
    }

    public static void showCityBanner() {
        if (WorldController.getSelectedCity() == null) {
            System.out.println("you should select a city first");
        } else {
            System.out.println(WorldController.getSelectedCity().getInfo() + WorldController.getSelectedCity().getCombatInfo());
        }

    }

    public static void showEmployedCitizens() {
        if (WorldController.getSelectedCity() == null) {
            System.out.println("you should select a city first");
            return;
        }
        System.out.println(CityController.employedCitizensData(WorldController.getSelectedCity()));
    }

    public static void showUnemployedCitizens() {
        if (WorldController.getSelectedCity() == null) {
            System.out.println("you should select a city first");
            return;
        }
        System.out.println(CityController.unemployedCitizensData(WorldController.getSelectedCity()));
    }
}
