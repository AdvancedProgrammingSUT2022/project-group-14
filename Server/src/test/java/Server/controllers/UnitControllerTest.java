//package Server.controllers;
//
//
//import Common.enums.Improvements;
//import Common.enums.tiles.TileFeatureTypes;
//import Common.enums.units.UnitStates;
//import Common.enums.units.UnitTypes;
//import Common.models.City;
//import Server.models.Civilization;
//import Server.models.units.*;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class UnitControllerTest {
//    @Mock
//    Unit unit;
//    @Mock
//    Melee melee;
//    @Mock
//    Ranged ranged;
//    @Mock
//    Settler settler;
//    @Mock
//    Worker worker;
//    @Mock
//    Improvements improvement;
//
//
//    @BeforeEach
//    public void setUpWorld() {
//        ArrayList<String> usernames = new ArrayList<>();
//        usernames.add("ali");
//        usernames.add("hassan");
//        WorldController.newWorld(usernames, 40, 40);
//    }
//
//    @Test
//    public void setUnitDestinationCoordinatesTest() {
//        when(unit.getCivilizationName()).thenReturn("ali");
//        UnitController.setUnitDestinationCoordinates(unit, 10, 10);
//        verify(unit).setDestinationCoordinates(10, 10);
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void setUnitDestinationCoordinatesTest1() {
//        when(unit.getCivilizationName()).thenReturn("ali");
//        when(unit.getCivilizationName()).thenReturn("bastani");
//
//        Assertions.assertEquals("the unit is not under your control", UnitController.setUnitDestinationCoordinates(unit, 10, 10));
//    }
//
//    @Test
//    public void setUnitDestinationCoordinatesTest2() {
//        when(unit.getCivilizationName()).thenReturn("ali");
//        MapController.getTileByCoordinates(20, 20).setCombatUnit(ranged);
//        MapController.getTileByCoordinates(20, 20).setNonCombatUnit(worker);
//        when(unit.getCivilizationName()).thenReturn("ali");
//        UnitController.setUnitDestinationCoordinates(unit, 20, 20);
//    }
//
//    @Test
//    public void resetMovingPointsTest() {
//        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName("ali");
//        when(melee.getName()).thenReturn("warrior");
//        UnitController.resetMovingPoints(currentCivilization);
//        verify(melee).setMovementPoint(2);
//    }
//
//    @Test
//    public void cancelMissionTest() {
//        when(unit.getCivilizationName()).thenReturn("ali");
//        verify(unit).cancelMission();
//    }
//
//    @Test
//    public void sleepUnitTest() {
//        when(unit.getCivilizationName()).thenReturn("ali");
//        verify(unit).setUnitState(UnitStates.SLEEP);
//    }
//
//    @Test
//    public void wakeUpTest() {
//        when(melee.getCivilizationName()).thenReturn("ali");
//        verify(melee).setUnitState(UnitStates.WAKE);
//    }
//
//    @Test
//    public void alertUnitTest() {
//        when(melee.getCivilizationName()).thenReturn("ali");
//        verify(melee).setUnitState(UnitStates.ALERT);
//    }
//
//    @Test
//    public void fortifyUnitTest() {
//        when(melee.getCivilizationName()).thenReturn("ali");
//        verify(melee).healUnit(5);
//    }
//
//    @Test
//    public void fortifyUnitUntilHealedTest() {
//        when(melee.getCivilizationName()).thenReturn("ali");
//        verify(melee).setUnitState(UnitStates.FORTIFY_TILL_HEALED);
//        verify(melee).healUnit(5);
//    }
//
//    @Test
//    public void setupRangedUnitTest() {
//        when(ranged.getCivilizationName()).thenReturn("ali");
//        when(ranged.isSiegeUnit()).thenReturn(true);
//        UnitController.setupRangedUnit(ranged, 10, 10);
//        verify(ranged).setCoordinatesToSetup(10, 10);
//    }
//
//    @Test
//    public void garrisonCityTest() {
//        when(melee.getCivilizationName()).thenReturn("ali");
//        MapController.getTileByCoordinates(melee.getCurrentX(), melee.getCurrentY()).setCity(new City("ali1", melee.getCurrentX(), melee.getCurrentY(), "ali"));
//        UnitController.garrisonCity(melee);
//        verify(melee).setUnitState(UnitStates.GARRISON);
//        Assertions.assertTrue(MapController.getTileByCoordinates(melee.getCurrentX(), melee.getCurrentY()).getCity().getNumberOfGarrisonedUnit() > 0);
//    }
//
//    @Test
//    public void foundCityTest() {
//        when(settler.getCivilizationName()).thenReturn("ali");
//        UnitController.foundCity(settler);
//        Assertions.assertNotNull(MapController.getTileByCoordinates(settler.getCurrentX(), settler.getCurrentY()).getCity());
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCities());
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void buildRoadTest() {
//        when(worker.getCivilizationName()).thenReturn("ali");
//        UnitController.buildRoad(worker);
//        Assertions.assertEquals(3, MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY()).getRoadState());
//        verify(worker).putToWork(3);
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void buildRailRoadTest() {
//        when(worker.getCivilizationName()).thenReturn("ali");
//        UnitController.buildRailRoad(worker);
//        Assertions.assertEquals(3, MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY()).getRailRoadState());
//        verify(worker).putToWork(3);
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void removeRouteFromTileTest() {
//        when(worker.getCivilizationName()).thenReturn("ali");
//        MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY()).setRoadState(0);
//        UnitController.removeRouteFromTile(worker);
//        Assertions.assertEquals(9999, MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY()).getRailRoadState());
//        Assertions.assertEquals(9999, MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY()).getRoadState());
//        verify(worker).putToWork(3);
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void buildImprovementTest() {
//        when(worker.getCivilizationName()).thenReturn("ali");
//        when(improvement.getRequiredTechnology()).thenReturn(null);
//        when(improvement.getPossibleTiles()).thenReturn(new HashSet<>(List.of(MapController.getTileByCoordinates(0, 0).getType())));
//        when(improvement.name()).thenReturn("kham");
//        WorldController.getWorld().getCivilizationByName(worker.getCivilizationName()).getTechnologies().put(improvement.getRequiredTechnology(), 0);
//        UnitController.buildImprovement(worker, improvement);
//        Assertions.assertEquals(improvement, MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY()).getImprovement());
//        Assertions.assertEquals(6, MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY()).getImprovementTurnsLeftToBuild());
//        verify(worker).putToWork(6);
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void removeJungleFromTileTest() {
//        when(worker.getCivilizationName()).thenReturn("ali");
//        MapController.getTileByCoordinates(0, 0).setFeature(TileFeatureTypes.JUNGLE);
//        UnitController.removeJungleFromTile(worker);
//        Assertions.assertNull(MapController.getTileByCoordinates(0, 0).getFeature());
//        verify(worker).putToWork(3);
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void removeForestFromTileTest() {
//        when(worker.getCivilizationName()).thenReturn("ali");
//        MapController.getTileByCoordinates(0, 0).setFeature(TileFeatureTypes.FOREST);
//        UnitController.removeForestFromTile(worker);
//        Assertions.assertNull(MapController.getTileByCoordinates(0, 0).getFeature());
//        verify(worker).putToWork(3);
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void removeMarshFromTileTest() {
//        when(worker.getCivilizationName()).thenReturn("ali");
//        MapController.getTileByCoordinates(0, 0).setFeature(TileFeatureTypes.SWAMP);
//        UnitController.removeMarshFromTile(worker);
//        Assertions.assertNull(MapController.getTileByCoordinates(0, 0).getFeature());
//        verify(worker).putToWork(3);
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void repairTileTest() {
//        when(worker.getCivilizationName()).thenReturn("ali");
//        MapController.getTileByCoordinates(0, 0).setPillageState(9999);
//        UnitController.repairTile(worker);
//        Assertions.assertEquals(3, MapController.getTileByCoordinates(0, 0).getPillageState());
//        verify(worker).putToWork(3);
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void repairTileTest2() {
//        when(worker.getCivilizationName()).thenReturn("ali");
//        MapController.getTileByCoordinates(0, 0).setPillageState(0);
//        Assertions.assertEquals("this tile has not been pillaged", UnitController.repairTile(worker));
//    }
//
//    @Test
//    public void pillageTest() {
//        when(melee.getCivilizationName()).thenReturn("ali");
//        WorldController.setSelectedCombatUnit(melee);
//        MapController.getTileByCoordinates(0, 0).setPillageState(0);
//        UnitController.pillage(0, 0);
//        Assertions.assertEquals(9999, MapController.getTileByCoordinates(0, 0).getPillageState());
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void pillageTest2() {
//        when(melee.getCivilizationName()).thenReturn("ali");
//        WorldController.setSelectedCombatUnit(melee);
//        MapController.getTileByCoordinates(0, 0).setPillageState(9999);
//        Assertions.assertEquals("this tile has been pillaged before", UnitController.pillage(0, 0));
//    }
//
//    @Test
//    public void deleteTest() {
//        when(settler.getCivilizationName()).thenReturn("ali");
//        UnitController.delete(settler);
//        Assertions.assertFalse(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits().contains(worker));
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void deleteTest2() {
//        when(worker.getCivilizationName()).thenReturn("ali");
//        UnitController.delete(worker);
//        Assertions.assertFalse(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits().contains(worker));
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void deleteTest3() {
//        when(melee.getCivilizationName()).thenReturn("ali");
//        UnitController.delete(melee);
//        Assertions.assertFalse(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits().contains(worker));
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void deleteTest4() {
//        when(ranged.getCivilizationName()).thenReturn("ali");
//        UnitController.delete(ranged);
//        Assertions.assertFalse(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits().contains(worker));
//        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
//    }
//
//    @Test
//    public void upgradeMeleeUnitTest() {
//        UnitTypes unitEnum = UnitTypes.SWORD_MAN;
//        when(melee.getCivilizationName()).thenReturn("ali");
//        WorldController.setSelectedCombatUnit(melee);
//        WorldController.getWorld().getCivilizationByName(melee.getCivilizationName()).setGold(200.0);
//        UnitController.upgradeUnit(unitEnum);
//        Assertions.assertNotEquals(200.0, WorldController.getWorld().getCivilizationByName(melee.getCivilizationName()).getGold());
//        Assertions.assertFalse(WorldController.getWorld().getCivilizationByName(melee.getCivilizationName()).getAllUnits().contains(melee));
//        Assertions.assertNotSame(WorldController.getSelectedCombatUnit(), melee);
//    }
//
//    @Test
//    public void upgradeRangedUnitTest() {
//        UnitTypes unitEnum = UnitTypes.CROSSBOW_MAN;
//        when(ranged.getCivilizationName()).thenReturn("ali");
//        WorldController.setSelectedCombatUnit(ranged);
//        WorldController.getWorld().getCivilizationByName(ranged.getCivilizationName()).setGold(200.0);
//        UnitController.upgradeUnit(unitEnum);
//        Assertions.assertNotEquals(200.0, WorldController.getWorld().getCivilizationByName(ranged.getCivilizationName()).getGold());
//        Assertions.assertFalse(WorldController.getWorld().getCivilizationByName(ranged.getCivilizationName()).getAllUnits().contains(melee));
//        Assertions.assertNotSame(WorldController.getSelectedCombatUnit(), ranged);
//    }
//
//    @Test
//    public void unitUnderControl() {
//        when(melee.getCivilizationName()).thenReturn("hassan");
//        when(settler.getCivilizationName()).thenReturn("hassan");
//        when(worker.getCivilizationName()).thenReturn("hassan");
//        WorldController.setSelectedCombatUnit(melee);
//        Assertions.assertEquals("you can only upgrade a combat unit to a combat unit", UnitController.upgradeUnit(UnitTypes.SETTLER));
//
//    }
//
//
//}
