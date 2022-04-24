package views;

import enums.Progresses;
import models.*;
import models.units.CombatUnit;
import models.units.NonCombatUnit;

import java.util.ArrayList;

public class GamePlay {

    private World world;
    private City selectedCity;
    private CombatUnit selectedCombatUnit;
    private NonCombatUnit selectedNonCombatUnit;

    public void run(ArrayList<String> usernames) {
        // TODO user validation
        Tile.readTileTypesInformationFromJson();
        world = new World(usernames);
        this.showMap();

    }

    public void showResearches(Civilization civilization) {

    }

    public void showUnits(Civilization civilization) {

    }

    public void showCities(Civilization civilization) {

    }

    public void showDiplomacyPanel(Civilization civilization) {
        // TODO
    }

    public void showDemographicsPanel(Civilization civilization) {
        // TODO
    }

    public void showVictoryPanel(Civilization civilization) {
        // TODO
    }

    public void showNotifications(Civilization civilization) {
        // TODO
    }

    public void showMilitaryUnits(Civilization civilization) {
        // TODO
    }

    public void showEconomicStatus(Civilization civilization) {

    }

    public void showDiplomaticHistory(Civilization civilization) {
        // TODO
    }

    public void showDealsHistory(Civilization civilization) {
        // TODO
    }

    public void showCombatUnitInfo() {

    }

    public void showNonCombatUnitInfo() {

    }

    public void showCityInfo() {

    }

    public void buildRoadOnTile() {

    }

    public void buildRailroadOnTile() {

    }

    public void buildProgressOnTile(Progresses progress) {

    }

    public void removeRoadAndRailroadFromTile() {

    }

    public void removeJungleFromTile() {

    }

    public void repairCurrentTile() {

    }

    public void showMapBasedOnTile(Tile tile) {

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
}
