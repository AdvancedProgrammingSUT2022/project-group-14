package views;

import enums.Progresses;
import models.*;
import models.units.CombatUnit;
import models.units.NonCombatUnit;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

public class GamePlay {

    private static World world;
    private static City selectedCity;
    private static CombatUnit selectedCombatUnit;
    private static NonCombatUnit selectedNonCombatUnit;
    private static Civilization currentCivilization;
    private static Tile selectedTile;



    public void run(ArrayList<String> usernames, Scanner scanner) {
          // TODO user validation
        Tile.readTileTypesInformationFromJson();
        world = new World(usernames);
        int numberOfPlayers = usernames.size();
        world.setTurn(0);
        currentCivilization = world.getNations().get(world.getCurrentCivilizationName());

        String input;
        GameCommandsValidation gameCommandsValidation = new GameCommandsValidation();
        while (true) {
            input = scanner.nextLine();
            if (!gameCommandsValidation.checkCommands(input))
                break;
        }


        world = null;
        selectedCity = null;
        selectedCombatUnit = null;
        selectedNonCombatUnit = null;
        currentCivilization = null;
        selectedTile = null;
    }

    public static Tile getTileByPosition(int x, int y){
        return world.getTileByCoordinates(x, y);
    }

    public static Tile getTileByCityName(String name){
        return null;

    }

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

    public static void selectUnit(Matcher matcher){

    }

    public static void selectCityByPosition(Matcher matcher){

    }

    public static void selectCityByName(Matcher matcher){

    }

    public static void moveTo(Matcher matcher){

    }

    public static void unitSleep(){

    }

    public static void unitAlert(){

    }

    public static void unitFortify(){

    }

    public static void unitFortifyHeal(){

    }

    public static void unitGarrison(){

    }

    public static void setupRanged(){

    }

    public static void cancelMission(){

    }

    public static void foundCity(){

    }


    public void showMap() {
        Tile[][] map = world.getMap();
        int m = world.width;
        int n = world.length;
        int x = -1;
        int y = -1;
        boolean E = true;
        boolean O = false;
        boolean printingCordinatesFlag = false;
        String cordinates = "";
        int currentChar = 0;
        for (int i = 1; i <= m; i++) {
            this.showUpMap(i);
            this.showDownMap(i);
        }
        this.showUpMap(m + 1);

    }

    private void showUpMap(int row) {
        Tile[][] map = world.getMap();
        int m = world.width, n = world.length;
        int x, y;
        boolean printingCordinatesFlag = false;
        String cordinates = "";
        int currentChar = 0;
        boolean changeColor = false;
        final String resetColor = "\u001B[0m";
        for (int j = 3; j >= 1; j--) {
            // if (row == m)
            // x = 0;
            // else
            x = -1;
            y = row - 2;
            for (int k = 1; k <= 8 * n + 3; k++) {
                if (j == 1 && k % 16 == 4 && row <= m) {
                    printingCordinatesFlag = true;
                    cordinates = (x + 1) + "," + (y + 1);
                }
                if ((k - j) % 16 == 0 && (row > 1 || k < 8 * n) && (m < row && k <= 3)) {
                    // System.out.print(resetColor + "/");
                    // changeColor = true;
                    y++;
                    x++;
                }
                if ((k - j) % 16 == 0 && (row > 1 || k < 8 * n) && (m >= row || k > 3)) {
                    System.out.print(resetColor + "/");
                    changeColor = true;
                    y++;
                    x++;
                }else if ((k - (12 - j)) % 16 == 0) {
                    System.out.print(resetColor + "\\");
                    changeColor = true;
                    y--;
                    x++;
                } else if (j == 1 && (k % 16 == 12 || k % 16 == 13 || k % 16 == 14 || k % 16 == 15 || k % 16 == 0)) {
                    if (changeColor == true && -1 < x && -1 < y && x < n && y < m)
                        System.out.print(map[y][x].getColor() + "_");
                    else
                        System.out.print("_");
                } else {
                    if (printingCordinatesFlag) {
                        System.out.print(cordinates.charAt(currentChar));
                        currentChar++;
                        if (currentChar > (cordinates.length() - 1)) {
                            currentChar = 0;
                            printingCordinatesFlag = false;
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

    private void showDownMap(int row) {
        Tile[][] map = world.getMap();
        int m = world.width, n = world.length;
        int x, y;
        boolean printingCordinatesFlag = false;
        String cordinates = "";
        int currentChar = 0;
        boolean changeColor = false;
        final String resetColor = "\u001B[0m";
        for (int j = 1; j <= 3; j++) {
            x = -1;
            y = row - 1;
            for (int k = 1; k <= 8 * n + 3; k++) {
                if (j == 3 && k % 16 == 12) {
                    printingCordinatesFlag = true;
                    cordinates = (x + 1) + "," + (y + 1);
                }
                if ((k - j) % 16 == 0) {
                    System.out.print(resetColor + "\\");
                    changeColor = true;
                    x++;
                } else if ((k - (12 - j)) % 16 == 0) {
                    System.out.print(resetColor + "/");
                    changeColor = true;
                    x++;
                } else if (j == 3 && (k % 16 == 4 || k % 16 == 5 || k % 16 == 6 || k % 16 == 7 || k % 16 == 8)) {
                    if (changeColor == true && -1 < x && -1 < y && x < n && y < m)
                        System.out.print(map[y][x].getColor() + "_");
                    else
                        System.out.print("_");
                } else {
                    if (printingCordinatesFlag) {
                        System.out.print(cordinates.charAt(currentChar));
                        currentChar++;
                        if (currentChar > (cordinates.length() - 1)) {
                            currentChar = 0;
                            printingCordinatesFlag = false;
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

    public void attack(Tile tile) {
    }
    public static void deleteUnit(){

    }

    public static void unitWake(){

    }

    public static void showCombatUnitInfo() {

    }

    public static void showNonCombatUnitInfo() {

    }

    public static void showCityInfo() {

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

    public static void showMapBasedOnTile(Tile tile) {

    }

    public static void attack(int destinationX, int destinationY){

    }


    public static Tile getSelectedTile() {
        return selectedTile;
    }
}
