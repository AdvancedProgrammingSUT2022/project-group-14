package views;

import controllers.*;
import enums.Improvements;
import enums.Technologies;
import models.*;
import models.units.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;

import static controllers.MapController.getMap;

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
        if (true) {
            //TODO name was invalid
        } else {
            //TODO get the city by name
            WorldController.setSelectedCity(null);
        }
    }

    // panels
    public static void researchesPanel() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        //TODO for every technology print the value of it
    }

    public static void unitsPanel() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        for (Unit unit : currentCivilization.getAllUnits()) {
            System.out.println(unit.getInfo());
        }
    }

    public static void citiesPanel() {
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        for (Unit unit : currentCivilization.getAllUnits()) {
            //TODO city info method
            System.out.println("city info");
        }
    }

    public static void diplomacyPanel() {
    }

    public static void demographicsPanel() {
    }

    public static void victoryPanel() {
    }

    public static void notificationsPanel() {
    }

    public static void militaryPanel() {
    }

    public static void economicStatusPanel() {

    }

    public static void diplomaticHistoryPanel() {
    }

    public static void dealsHistoryPanel() {
    }

    public static void showCombatUnitInfo() {
        //TODO these are temporary
        System.out.println("you have selected : " + WorldController.getSelectedCombatUnit().getName()
                + " from the " + WorldController.getSelectedCombatUnit().getCivilizationName() + " civilization"
                + " with these coordinates : " + WorldController.getSelectedCombatUnit().getCurrentX() + " "
                + WorldController.getSelectedCombatUnit().getCurrentY());
    }

    public static void showNonCombatUnitInfo() {
        //TODO these are temporary
        System.out.println("you have selected : " + WorldController.getSelectedNonCombatUnit().getName()
                + " from the " + WorldController.getSelectedNonCombatUnit().getCivilizationName() + " civilization"
                + " with these coordinates : " + WorldController.getSelectedNonCombatUnit().getCurrentX() + " "
                + WorldController.getSelectedNonCombatUnit().getCurrentY());
    }

    //-----------------------------------
    public void showMap() {
        for (int i = 0; i < 6 * width + 3; i++) {
            for (int j = 0; j < 8 * length + 3; j++) {
                System.out.print(cellsMap[i][j].getColor().getAnsiEscapeCode() + cellsMap[i][j].getCh()
                        + Colors.RESET.getAnsiEscapeCode());
            }
            System.out.println();
        }
    }
    // showing map methods
    public static void showMapBasedOnTile(int x, int y) {
        Tile tile = MapController.getTileByCoordinates(x, y);
        WorldController.setSelectedTile(tile);
        Tile[][] allTiles = MapController.getMap();
        Tile[][] wantedTiles = new Tile[3][12];
        int k = 0, z = 0;
        for (int i = Math.max(tile.getX() - 1, 0); i < Math.min(tile.getX() + 2, MapController.getWidth()); i++, k++) {
            z = 0;
            for (int j = Math.max(tile.getY() - 5, 0); j < Math.min(tile.getY() + 7, MapController.getLength()); j++, z++) {
                wantedTiles[k][z] = allTiles[i][j];
            }
        }
        showMap(allTiles, k, z, Math.max(tile.getX() - 1, 0), Math.max(tile.getY() - 5, 0));
    }

    public static void showMap(Tile[][] map, int m, int n, int originalX, int originalY) {
        for (int i = 1; i <= m; i++) {
            showUpMap(i, map, m, n, originalX, originalY);
            showDownMap(i, map, m, n, originalX, originalY);
        }
        showUpMap(m + 1, map, m, n, originalX, originalY);
    }

    private static void showUpMap(int row, Tile[][] map, int m, int n, int originalX, int originalY) {
        int y, x;
        boolean printingCoordinatesFlag = false;
        String coordinates = "";
        int currentChar = 0;
        boolean changeColor = false;
        final String resetColor = "\u001B[0m";
        for (int j = 3; j >= 1; j--) {
            // if (row == m)
            // x = 0;
            // else
            y = -1;
            x = row - 2;
            for (int k = 1; k <= 8 * n + 3; k++) {
                if (j == 1 && k % 16 == 4 && row <= m) {
                    printingCoordinatesFlag = true;
                    coordinates = (originalX + x + 1) + "," + (originalY + y + 1);
                }
                if ((k - j) % 16 == 0 && (row > 1 || k < 8 * n) && (m < row && k <= 3)) {
                    // System.out.print(resetColor + "/");
                    // changeColor = true;
                    x++;
                    y++;
                }
                if ((k - j) % 16 == 0 && (row > 1 || k < 8 * n) && (m >= row || k > 3)) {
                    System.out.print(resetColor + "/");
                    changeColor = true;
                    x++;
                    y++;
                } else if ((k - (12 - j)) % 16 == 0) {
                    System.out.print(resetColor + "\\");
                    changeColor = true;
                    x--;
                    y++;
                } else if (j == 1 && (k % 16 >= 12 || k % 16 == 0)) {
                    if (changeColor == true && -1 < y && -1 < x && y < n && x < m)
                        System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + "_");
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
                    } else if (changeColor == true && -1 < y && -1 < x && y < n && x < m) {
                        CombatUnit combatUnit= MapController.getTileByCoordinates(originalX + x, originalY + y).getCombatUnit();
                        NonCombatUnit nonCombatUnit = MapController.getTileByCoordinates(originalX + x, originalY + y).getNonCombatUnit();
                        if (combatUnit != null && j == 3 && k % 16 == 12) {
                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + combatUnit.getName().charAt(0));
                        } else if (nonCombatUnit != null && j == 3 && k % 16 == 14) {
                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + nonCombatUnit.getName().charAt(0));
                        } else {
                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + " ");
                        }
                    }
                    else
                        System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private static void showDownMap(int row, Tile[][] map, int m, int n, int originalX, int originalY) {
        int y, x;
        boolean printingCoordinatesFlag = false;
        String coordinates = "";
        int currentChar = 0;
        boolean changeColor = false;
        final String resetColor = "\u001B[0m";
        for (int j = 1; j <= 3; j++) {
            y = -1;
            x = row - 1;
            for (int k = 1; k <= 8 * n + 3; k++) {
                if (j == 3 && k % 16 == 12) {
                    printingCoordinatesFlag = true;
                    coordinates = (originalX + x + 1) + "," + (originalY + y + 1);
                }
                if ((k - j) % 16 == 0) {
                    System.out.print(resetColor + "\\");
                    changeColor = true;
                    y++;
                } else if ((k - (12 - j)) % 16 == 0) {
                    System.out.print(resetColor + "/");
                    changeColor = true;
                    y++;
                } else if (j == 3 && (k % 16 >= 4 && k % 16 <= 8)) {
                    if (changeColor == true && -1 < y && -1 < x && y < n && x < m)
                        System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + "_");
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
                    } else if (changeColor == true && -1 < y && -1 < x && y < n && x < m) {
                        CombatUnit combatUnit= MapController.getTileByCoordinates(originalX + x, originalY + y).getCombatUnit();
                        NonCombatUnit nonCombatUnit = MapController.getTileByCoordinates(originalX + x, originalY + y).getNonCombatUnit();
                        if (combatUnit != null && j == 1 && k % 16 == 4) {
                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + combatUnit.getName().charAt(0));
                        } else if (nonCombatUnit != null && j == 1 && k % 16 == 6) {
                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + nonCombatUnit.getName().charAt(0));
                        } else {
                            System.out.print(map[originalX + x][originalY + y].getColor().getAnsiEscapeCode() + " ");
                        }
                    }
                    else
                        System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

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
            if ((error = UnitController.buildRoad(( Worker) WorldController.getSelectedNonCombatUnit())) != null) {
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
            if ((error = UnitController.buildRailRoad(( Worker) WorldController.getSelectedNonCombatUnit())) != null) {
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
            if ((error = UnitController.removeRouteFromTile(( Worker) WorldController.getSelectedNonCombatUnit())) != null) {
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
            if ((error = UnitController.buildImprovement(( Worker) WorldController.getSelectedNonCombatUnit(), wantedImprovement)) != null) {
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
            if ((error = UnitController.removeJungleFromTile(( Worker) WorldController.getSelectedNonCombatUnit())) != null) {
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
            if ((error = UnitController.removeForestFromTile(( Worker) WorldController.getSelectedNonCombatUnit())) != null) {
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
            if ((error = UnitController.removeMarshFromTile(( Worker) WorldController.getSelectedNonCombatUnit())) != null) {
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
            if ((error = UnitController.repairTile(( Worker) WorldController.getSelectedNonCombatUnit())) != null) {
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

    public static void lockCitizen(int x, int y, int id){
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
}
