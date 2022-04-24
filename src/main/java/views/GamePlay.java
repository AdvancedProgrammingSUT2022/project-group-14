package views;

import controllers.MoveController;
import controllers.TileController;
import controllers.UnitController;
import enums.Progresses;
import models.*;
import models.units.CombatUnit;
import models.units.NonCombatUnit;
import models.units.Unit;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GamePlay {

    private static World world;
    private static City selectedCity;
    private static CombatUnit selectedCombatUnit;
    private static NonCombatUnit selectedNonCombatUnit;
    private static Tile selectedTile;


    public void run(ArrayList<String> usernames, Scanner scanner) {
        Tile.readTileTypesInformationFromJson();
        world = new World(usernames);

        String input;
        GameCommandsValidation gameCommandsValidation = new GameCommandsValidation();
        while (true) {
            input = scanner.nextLine();
            if (!gameCommandsValidation.checkCommands(input))
                break;
        }

        resetGameValues();
    }
    //selecting methods
    public static void selectUnit(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Tile wantedTile = getTileByPosition(x, y);
        if (matcher.group("militaryStatus").equals("combat")) {
            if (TileController.combatUnitExistsInTile(wantedTile)){
                selectedCombatUnit = wantedTile.getCombatUnit();
                selectedNonCombatUnit = null;
                showCombatUnitInfo();
            } else {
                System.out.println("there isn't any combat unit in this position");
            }
        } else {
            if (TileController.nonCombatUnitExistsInTile(wantedTile)){
                selectedNonCombatUnit = wantedTile.getNonCombatUnit();
                selectedCombatUnit = null;
                showNonCombatUnitInfo();
            } else {
                System.out.println("there isn't any non combat unit in this position");
            }
        }
    }

    public static void selectCityByPosition(Matcher matcher) {

    }

    public static void selectCityByName(Matcher matcher) {

    }
    //getting methods
    public static Tile getTileByPosition(int x, int y) {
        return world.getTileByCoordinates(x, y);
    }

    public static Tile getTileByCityName(String name) {
        return null;

    }

    public static Tile getSelectedTile() {
        return selectedTile;
    }
    //showing methods
    public static void showResearches() {

    }

    public static void showUnits() {

    }

    public static void showCities() {

    }

    public static void showDiplomacyPanel() {
        // TODO
    }

    public static void showDemographicsPanel() {
        // TODO
    }

    public static void showVictoryPanel() {
        // TODO
    }

    public static void showNotifications() {
        // TODO
    }

    public static void showMilitaryUnits() {
        // TODO
    }

    public static void showEconomicStatus() {

    }

    public static void showDiplomaticHistory() {
        // TODO
    }

    public static void showDealsHistory() {
        // TODO
    }

    public static void showCombatUnitInfo() {
        System.out.println("you have selected : " + selectedCombatUnit.getName());
    }

    public static void showNonCombatUnitInfo() {
        System.out.println("you have selected : " + selectedNonCombatUnit.getName());
    }

    public static void showCityInfo() {

    }
    //showing map methods
    public static void showMapBasedOnTile(Tile tile) {
        selectedTile = tile;
        Tile[][] allTiles = world.getMap();
        Tile[][] wantedTiles = new Tile[3][12];
        int k = 0, z = 0;
        for (int i = Math.max(tile.getX()-1, 0); i < Math.min(tile.getX() + 2, World.getWidth()); i++, k++) {
            z = 0;
            for (int j = Math.max(tile.getY() - 5, 0); j < Math.min(tile.getY() + 7, World.getLength()); j++, z++) {
                wantedTiles[k][z] = allTiles[i][j];
            }
        }
        showMap(wantedTiles, k, z, tile.getX(), tile.getY()-4);
    }

    public static void showMap(Tile[][] map, int m, int n, int originalX, int originalY) {
        for (int i = 1; i <= m; i++) {
            showUpMap(i, map, m, n, originalX-1, originalY + i-2);
            showDownMap(i, map, m, n, originalX-1, originalY + i-2);
        }
        showUpMap(m + 1, map, m, n, originalX-1, originalY + m-1);
    }

    private static void showUpMap(int row, Tile[][] map, int m, int n, int originalX, int originalY) {
        int x, y, originalXCopy = originalX, originalYCopy = originalY;
        boolean printingCoordinatesFlag = false;
        String coordinates = "";
        int currentChar = 0;
        boolean changeColor = false;
        final String resetColor = "\u001B[0m";
        for (int j = 3; j >= 1; j--) {
            // if (row == m)
            // x = 0;
            // else
            x = -1;
            y = row - 2;
            originalX = originalXCopy;
            originalY = originalYCopy;
            for (int k = 1; k <= 8 * n + 3; k++) {
                if (j == 1 && k % 16 == 4 && row <= m) {
                    printingCoordinatesFlag = true;
                    coordinates = (x + 1) + "," + (y + 1);
//                    coordinates = (originalX) + "," + originalY;
                }
                if ((k - j) % 16 == 0 && (row > 1 || k < 8 * n) && (m < row && k <= 3)) {
                    // System.out.print(resetColor + "/");
                    // changeColor = true;
                    y++;
                    x++;
                    originalX++;
                    originalY++;
                }
                if ((k - j) % 16 == 0 && (row > 1 || k < 8 * n) && (m >= row || k > 3)) {
                    System.out.print(resetColor + "/");
                    changeColor = true;
                    y++;
                    x++;
                    originalX++;
                    originalY++;
                } else if ((k - (12 - j)) % 16 == 0) {
                    System.out.print(resetColor + "\\");
                    changeColor = true;
                    y--;
                    x++;
                    originalX++;
                    originalY--;
                } else if (j == 1 && (k % 16 == 12 || k % 16 == 13 || k % 16 == 14 || k % 16 == 15 || k % 16 == 0)) {
                    if (changeColor == true && -1 < x && -1 < y && x < n && y < m)
                        System.out.print(map[y][x].getColor() + "_");
                    else
                        System.out.print("_");
                } else {
                    if (printingCoordinatesFlag) {
                        System.out.print(coordinates.charAt(currentChar));
                        currentChar++;
                        if (currentChar > (coordinates.length() - 1)) {
                            currentChar = 0;
                            printingCoordinatesFlag = false;
                        }
                    } else if (changeColor == true && -1 < x && -1 < y && x < n && y < m)
                        System.out.print(map[y][x].getColor() + " ");
                    else
                        System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static void showDownMap(int row, Tile[][] map, int m, int n, int originalX, int originalY) {
        int x, y, originalXCopy = originalX, originalYCopy = originalY;
        boolean printingCoordinatesFlag = false;
        String coordinates = "";
        int currentChar = 0;
        boolean changeColor = false;
        final String resetColor = "\u001B[0m";
        for (int j = 1; j <= 3; j++) {
            x = -1;
            y = row - 1;
            originalX = originalXCopy;
            originalY = originalYCopy;
            for (int k = 1; k <= 8 * n + 3; k++) {
                if (j == 3 && k % 16 == 12) {
                    printingCoordinatesFlag = true;
                    coordinates = (x + 1) + "," + (y + 1);
//                    coordinates = originalX + "," + originalY;
                }
                if ((k - j) % 16 == 0) {
                    System.out.print(resetColor + "\\");
                    changeColor = true;
                    x++;
                    originalX++;
                } else if ((k - (12 - j)) % 16 == 0) {
                    System.out.print(resetColor + "/");
                    changeColor = true;
                    x++;
                    originalX++;
                } else if (j == 3 && (k % 16 == 4 || k % 16 == 5 || k % 16 == 6 || k % 16 == 7 || k % 16 == 8)) {
                    if (changeColor == true && -1 < x && -1 < y && x < n && y < m)
                        System.out.print(map[y][x].getColor() + "_");
                    else
                        System.out.print("_");
                } else {
                    if (printingCoordinatesFlag) {
                        System.out.print(coordinates.charAt(currentChar));
                        currentChar++;
                        if (currentChar > (coordinates.length() - 1)) {
                            currentChar = 0;
                            printingCoordinatesFlag = false;
                        }
                    } else if (changeColor == true && -1 < x && -1 < y && x < n && y < m)
                        System.out.print(map[y][x].getColor() + " ");
                    else
                        System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
    //units methods
    public static void attack(int destinationX, int destinationY) {

    }

    public static void moveTo(Matcher matcher) {
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        String error;
        if (selectedNonCombatUnit == null && selectedCombatUnit == null) {
            System.out.println("you haven't selected a unit yet");
        } else if (selectedCombatUnit != null) {
            if ((error = MoveController.setUnitDestinationCoordinates(selectedCombatUnit, x, y, world)) != null) {
                System.out.println(error);
            }
        } else {
            if ((error = MoveController.setUnitDestinationCoordinates(selectedNonCombatUnit, x, y, world)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitSleep() {
        String error;
        if (selectedNonCombatUnit == null && selectedCombatUnit == null) {
            System.out.println("you haven't selected a unit yet");
        } else if (selectedCombatUnit != null) {
            if ((error = UnitController.sleepUnit(selectedCombatUnit, world)) != null) {
                System.out.println(error);
            }
        } else {
            if ((error = UnitController.sleepUnit(selectedNonCombatUnit, world)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitAlert() {
        String error;
        if (selectedNonCombatUnit == null && selectedCombatUnit == null) {
            System.out.println("you haven't selected a unit yet");
        } else if (selectedNonCombatUnit != null) {
            System.out.println("the selected unit is not a combat unit");
        } else {
            if ((error = UnitController.alertUnit(selectedCombatUnit, world)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitFortify() {
        String error;
        if (selectedNonCombatUnit == null && selectedCombatUnit == null) {
            System.out.println("you haven't selected a unit yet");
        } else if (selectedNonCombatUnit != null) {
            System.out.println("the selected unit is not a combat unit");
        } else {
            if ((error = UnitController.fortifyUnit(selectedCombatUnit, world)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitFortifyHeal() {
        String error;
        if (selectedNonCombatUnit == null && selectedCombatUnit == null) {
            System.out.println("you haven't selected a unit yet");
        } else if (selectedNonCombatUnit != null) {
            System.out.println("the selected unit is not a combat unit");
        } else {
            if ((error = UnitController.fortifyUnitUntilHealed(selectedCombatUnit, world)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitGarrison() {

    }

    public static void setupRanged() {
        String error;
        if (selectedNonCombatUnit == null && selectedCombatUnit == null) {
            System.out.println("you haven't selected a unit yet");
        } else {
            if ((error = UnitController.setupRanged(selectedCombatUnit, 0, 0, world)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void cancelMission() {
        String error;
        if (selectedNonCombatUnit == null && selectedCombatUnit == null) {
            System.out.println("you haven't selected a unit yet");
        } else if (selectedCombatUnit != null) {
            if ((error = UnitController.cancelMission(selectedCombatUnit, world)) != null) {
                System.out.println(error);
            }
        } else {
            if ((error = UnitController.cancelMission(selectedNonCombatUnit, world)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void deleteUnit() {
        String error;
        if (selectedNonCombatUnit == null && selectedCombatUnit == null) {
            System.out.println("you haven't selected a unit yet");
        } else if (selectedCombatUnit != null) {
            if ((error = UnitController.delete(selectedCombatUnit, world)) != null) {
                System.out.println(error);
            }
        } else {
            if ((error = UnitController.delete(selectedNonCombatUnit, world)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void unitWake() {
        String error;
        if (selectedNonCombatUnit == null && selectedCombatUnit == null) {
            System.out.println("you haven't selected a unit yet");
        } else if (selectedCombatUnit != null) {
            if ((error = UnitController.wakeUp(selectedCombatUnit, world)) != null) {
                System.out.println(error);
            }
        } else {
            if ((error = UnitController.wakeUp(selectedNonCombatUnit, world)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void foundCity() {

    }

    public static void buildRoadOnTile() {

    }

    public static void buildRailroadOnTile() {

    }

    public static void buildProgressOnTile() {

    }

    public static void removeRoadAndRailroadFromTile() {

    }

    public static void removeJungleFromTile() {

    }

    public static void repairCurrentTile() {

    }

    public void resetGameValues() {
        world = null;
        selectedCity = null;
        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
        selectedTile = null;
    }
}
