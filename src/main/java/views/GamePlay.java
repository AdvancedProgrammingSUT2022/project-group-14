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

        world = new World(usernames);
    }

    public boolean checkCommands(String input) {
        return false;

    }

    public boolean checkUnit(String input) {
        return true;
    }

    public boolean checkMoveTo(String input) {
        return true;
    }

    public boolean checkSleep(String input) {
        return true;
    }

    public boolean checkAlert(String input) {
        return true;
    }

    public boolean checkFortify(String input) {
        return true;
    }

    public boolean checkGarrison(String input) {
        return true;
    }

    public boolean checkSetupRanged(String input) {
        return true;
    }

    public boolean checkAttack(String input) {

        return true;
    }

    public boolean checkFoundCity(String input) {
        return true;
    }

    public boolean checkCancelMission(String input) {
        return true;
    }

    public boolean checkDelete(String input) {
        return true;
    }

    public boolean checkBuild(String input) {
        return true;
    }

    public void checkShowResearches(Civilization civilization) {

    }

    public void checkShowUnits(Civilization civilization) {

    }

    public void checkShowCities(Civilization civilization) {

    }

    public void checkShowDiplomacyPanel(Civilization civilization) {
        // TODO
    }

    public void checkShowDemographicsPanel(Civilization civilization) {
        // TODO
    }

    public void checkShowVictoryPanel(Civilization civilization) {
        // TODO
    }

    public void checkShowNotifications(Civilization civilization) {
        // TODO
    }

    public void checkShowMilitaryUnits(Civilization civilization) {
        // TODO
    }

    public void checkShowEconomicStatus(Civilization civilization) {

    }

    public void checkShowDiplomaticHistory(Civilization civilization) {
        // TODO
    }

    public void checkShowDealsHistory(Civilization civilization) {
        // TODO
    }

    public void checkShowCombatUnitInfo() {

    }

    public void checkShowNonCombatUnitInfo() {

    }

    public void checkShowCityInfo() {

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

    public void checkBuildRoadOnTile() {

    }

    public void checkBuildRailroadOnTile() {

    }

    public void checkBuildProgressOnTile(Progresses progress) {

    }

    public void checkRemoveRoadAndRailroadFromTile() {

    }

    public void checkRemoveJungleFromTile() {

    }

    public void checkRepairCurrentTile() {
        // TODO
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

    public void checkShowMapBasedOnTile(Tile tile) {

    }

    public void showMapBasedOnTile(Tile tile) {

    }

    public void attack(Tile tile){

    }
}
