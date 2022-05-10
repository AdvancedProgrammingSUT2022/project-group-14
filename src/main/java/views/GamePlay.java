package views;

import controllers.*;
import enums.Colors;
import enums.Improvements;
import enums.Technologies;
import models.*;
import models.units.*;

import java.util.*;
import java.util.regex.Matcher;

public class GamePlay {

    public void run(Scanner scanner) {
        String input;
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
            System.out.println(counter + "-> " + city.getName() + " with ( " + x + " , " + y + " )" + "coordinates");
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

    public static void showCombatUnitInfo() {
        //TODO these are temporary
        System.out.println(WorldController.getSelectedCombatUnit().getInfo());
    }

    public static void showNonCombatUnitInfo() {
        //TODO these are temporary
        System.out.println(WorldController.getSelectedNonCombatUnit().getInfo());
    }

    // showing map methods
    public static void showMapOnTiles(int i, int j) {
        MapController.tileCellsRefresh();
        int[] tileCenter = MapController.getTileCenterByCoordinates(i,j);
        showMapByCordinates(Math.max(0, tileCenter[0] - 11), Math.max(0, tileCenter[1] - 28), Math.min(MapController.outputMapWidth, tileCenter[0] + 11), Math.min(MapController.outputMapLength, tileCenter[1] + 28));
    }

    public static void showMapByCordinates(int x1, int y1, int x2, int y2) {
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                System.out.print(MapController.getCellsMap()[i][j].getColor().getAnsiEscapeCode() + MapController.getCellsMap()[i][j].getCharacter() + Colors.RESET.getAnsiEscapeCode());
            }
            System.out.println();
        }

        //TODO move to gameplay
    }
//    public static void showMapBasedOnTile(int x, int y) {
//        Tile tile = MapController.getTileByCoordinates(x, y);
//        WorldController.setSelectedTile(tile);
//        Tile[][] allTiles = MapController.getMap();
//        Tile[][] wantedTiles = new Tile[3][12];
//        int k = 0, z = 0;
//        for (int i = Math.max(tile.getX() - 1, 0); i < Math.min(tile.getX() + 2, MapController.getWidth()); i++, k++) {
//            z = 0;
//            for (int j = Math.max(tile.getY() - 5, 0); j < Math.min(tile.getY() + 7, MapController.getLength()); j++, z++) {
//                wantedTiles[k][z] = allTiles[i][j];
//            }
//        }
//        showMap(allTiles, k, z, Math.max(tile.getX() - 1, 0), Math.max(tile.getY() - 5, 0));
//    }
//
//    public static void showMap(Tile[][] map, int m, int n, int originalX, int originalY) {
//        for (int i = 1; i <= m; i++) {
//            showUpMap(i, map, m, n, originalX, originalY);
//            showDownMap(i, map, m, n, originalX, originalY);
//        }
//        showUpMap(m + 1, map, m, n, originalX, originalY);
//    }
//
//    private static void showUpMap(int row, Tile[][] map, int m, int n, int originalX, int originalY) {
//        int y, x;
//        boolean printingCoordinatesFlag = false;
//        String coordinates = "";
//        int currentChar = 0;
//        boolean changeColor = false;
//        final String resetColor = "\u001B[0m";
//        for (int j = 3; j >= 1; j--) {
//            // if (row == m)
//            // x = 0;
//            // else
//            y = -1;
//            x = row - 2;
//            for (int k = 1; k <= 8 * n + 3; k++) {
//                if (j == 1 && k % 16 == 4 && row <= m) {
//                    printingCoordinatesFlag = true;
//                    coordinates = (originalX + x + 1) + "," + (originalY + y + 1);
//                }
//                if ((k - j) % 16 == 0 && (row > 1 || k < 8 * n) && (m < row && k <= 3)) {
//                    // System.out.print(resetColor + "/");
//                    // changeColor = true;
//                    x++;
//                    y++;
//                }
//                if ((k - j) % 16 == 0 && (row > 1 || k < 8 * n) && (m >= row || k > 3)) {
//                    System.out.print(resetColor + "/");
//                    changeColor = true;
//                    x++;
//                    y++;
//                } else if ((k - (12 - j)) % 16 == 0) {
//                    System.out.print(resetColor + "\\");
//                    changeColor = true;
//                    x--;
//                    y++;
//                } else if (j == 1 && (k % 16 >= 12 || k % 16 == 0)) {
//                    if (changeColor == true && -1 < y && -1 < x && y < n && x < m)
//                        System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + "_");
//                    else
//                        System.out.print("_");
//                } else {
//                    if (printingCoordinatesFlag) {
//                        System.out.print(coordinates.charAt(currentChar));
//                        currentChar++;
//                        if (currentChar > (coordinates.length() - 1)) {
//                            currentChar = 0;
//                            printingCoordinatesFlag = false;
//                        }
//                    } else if (changeColor == true && -1 < y && -1 < x && y < n && x < m) {
//                        CombatUnit combatUnit = MapController.getTileByCoordinates(originalX + x, originalY + y).getCombatUnit();
//                        NonCombatUnit nonCombatUnit = MapController.getTileByCoordinates(originalX + x, originalY + y).getNonCombatUnit();
//                        if (combatUnit != null && j == 3 && k % 16 == 12) {
//                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + combatUnit.getName().charAt(0));
//                        } else if (nonCombatUnit != null && j == 3 && k % 16 == 14) {
//                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + nonCombatUnit.getName().charAt(0));
//                        } else {
//                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + " ");
//                        }
//                    } else
//                        System.out.print(" ");
//                }
//            }
//            System.out.println();
//        }
//    }
//
//    private static void showDownMap(int row, Tile[][] map, int m, int n, int originalX, int originalY) {
//        int y, x;
//        boolean printingCoordinatesFlag = false;
//        String coordinates = "";
//        int currentChar = 0;
//        boolean changeColor = false;
//        final String resetColor = "\u001B[0m";
//        for (int j = 1; j <= 3; j++) {
//            y = -1;
//            x = row - 1;
//            for (int k = 1; k <= 8 * n + 3; k++) {
//                if (j == 3 && k % 16 == 12) {
//                    printingCoordinatesFlag = true;
//                    coordinates = (originalX + x + 1) + "," + (originalY + y + 1);
//                }
//                if ((k - j) % 16 == 0) {
//                    System.out.print(resetColor + "\\");
//                    changeColor = true;
//                    y++;
//                } else if ((k - (12 - j)) % 16 == 0) {
//                    System.out.print(resetColor + "/");
//                    changeColor = true;
//                    y++;
//                } else if (j == 3 && (k % 16 >= 4 && k % 16 <= 8)) {
//                    if (changeColor == true && -1 < y && -1 < x && y < n && x < m)
//                        System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + "_");
//                    else
//                        System.out.print("_");
//                } else {
//                    if (printingCoordinatesFlag) {
//                        System.out.print(coordinates.charAt(currentChar));
//                        currentChar++;
//                        if (currentChar > (coordinates.length() - 1)) {
//                            currentChar = 0;
//                            printingCoordinatesFlag = false;
//                        }
//                    } else if (changeColor == true && -1 < y && -1 < x && y < n && x < m) {
//                        CombatUnit combatUnit = MapController.getTileByCoordinates(originalX + x, originalY + y).getCombatUnit();
//                        NonCombatUnit nonCombatUnit = MapController.getTileByCoordinates(originalX + x, originalY + y).getNonCombatUnit();
//                        if (combatUnit != null && j == 1 && k % 16 == 4) {
//                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + combatUnit.getName().charAt(0));
//                        } else if (nonCombatUnit != null && j == 1 && k % 16 == 6) {
//                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + nonCombatUnit.getName().charAt(0));
//                        } else {
//                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + " ");
//                        }
//                    } else
//                        System.out.print(" ");
//                }
//            }
//            System.out.println();
//        }
//    }

    // units methods
    public static void attack(int destinationX, int destinationY) {

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
        //TODO checking required resource
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
        }
    }

    public static void cancelCurrentResearch() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        currentCivilization.setCurrentTechnology(null);
    }

    public static void startResearch(Technologies technology) {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
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
}
