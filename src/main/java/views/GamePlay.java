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
