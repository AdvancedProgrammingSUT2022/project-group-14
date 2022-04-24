package views;

import controllers.MoveController;
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
    private static Civilization currentCivilization;
    private static Tile selectedTile;


    public void run(ArrayList<String> usernames, Scanner scanner) {
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
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        if (matcher.group("militaryStatus").equals("combat")) {
            //TODO get the combat unit on tile
        } else {
            //TODO get the nonCombat unit tile
        }
    }

    public static void selectCityByPosition(Matcher matcher){

    }

    public static void selectCityByName(Matcher matcher){

    }

    public static void moveTo(Matcher matcher){
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

    public static void unitSleep(){
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

    public static void unitAlert(){
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

    public static void unitFortify(){
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

    public static void unitFortifyHeal(){
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

    public static void unitGarrison(){

    }

    public static void setupRanged(){
        String error;
        if (selectedNonCombatUnit == null && selectedCombatUnit == null) {
            System.out.println("you haven't selected a unit yet");
        } else {
            if ((error = UnitController.setupRanged(selectedCombatUnit,0, 0, world)) != null) {
                System.out.println(error);
            }
        }
    }

    public static void cancelMission(){
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

    public static void foundCity(){

    }

    public static void deleteUnit(){
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

    public static void unitWake(){
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
