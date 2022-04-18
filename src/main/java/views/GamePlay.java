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

    public void attack(Tile tile){

    }
}
