//package Server.controllers;
//
//import Common.enums.units.UnitTypes;
//import Common.models.City;
//import Server.models.Civilization;
//import Common.models.units.CombatUnit;
//import Common.models.units.Melee;
//import Common.models.units.Ranged;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class WarControllerTest {
//    @Mock
//    CombatUnit combatUnit;
//    @Mock
//    City city;
//    @Mock
//    City city2;
//
//    private final PrintStream standardOut = System.out;
//    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
//
//    @BeforeEach
//    public void setUpUsers() throws IOException {
//        ArrayList<String> usernames = new ArrayList<>();
//        usernames.add("ali"); usernames.add("hassan");
//        WorldController.newWorld(usernames, 40, 40);
//        System.setOut(new PrintStream(outputStreamCaptor));
//        MapController.getTileByCoordinates(10, 10).setCombatUnit(combatUnit);
//        WorldController.setSelectedCombatUnit(combatUnit);
//    }
//
//    @Test
//    public void testCombatUnitNotUnderUserControl(){
//        when(combatUnit.getCivilizationName()).thenReturn("hassan");
//        String output = WarController.combatUnitAttacksTile(11, 10, combatUnit);
//        Assertions.assertEquals("unit is not under your control", output);
//    }
//
//    @Test
//    public void testDestinationTileIsInvalid(){
//        when(combatUnit.getCivilizationName()).thenReturn("ali");
//        String output = WarController.combatUnitAttacksTile(-1, 0, combatUnit);
//        Assertions.assertEquals("wanted tile is invalid", output);
//    }
//
//    @Test
//    public void attackCityRangedConquerTest() {
//        Ranged ranged = new Ranged(UnitTypes.WARRIOR, 10, 10, "ali");
//        WorldController.setSelectedCombatUnit(ranged);
//        when(city.getCenterOfCity()).thenReturn(MapController.getTileByCoordinates(11,10));
//        when(city.getAttackStrength()).thenReturn(0.0);
//        when(city.getHealthPoint()).thenReturn(0.0);
//        when(city.getDefenseStrength()).thenReturn(0.0);
//        MapController.getTileByCoordinates(10, 10).setCombatUnit(ranged);
//        MapController.getTileByCoordinates(11,10).setCivilization("hassan");
//        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName("hassan");
//        currentCivilization.addCity(city);
//        currentCivilization.addCity(city2);
//        MapController.getTileByCoordinates(11,10).setCity(city);
//        WarController.combatUnitAttacksTile(11, 10, combatUnit);
//    }
//
//    @Test
//    public void attackCityMeleeConquerTest() {
//        Melee melee = new Melee(UnitTypes.WARRIOR, 10, 10, "ali");
//        WorldController.setSelectedCombatUnit(melee);
//        when(city.getCenterOfCity()).thenReturn(MapController.getTileByCoordinates(11,10));
//        when(city.getAttackStrength()).thenReturn(0.0);
//        when(city.getHealthPoint()).thenReturn(0.0);
//        when(city.getDefenseStrength()).thenReturn(0.0);
//        MapController.getTileByCoordinates(10, 10).setCombatUnit(melee);
//        MapController.getTileByCoordinates(11,10).setCivilization("hassan");
//        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName("hassan");
//        currentCivilization.addCity(city);
//        currentCivilization.addCity(city2);
//        MapController.getTileByCoordinates(11,10).setCity(city);
//        WarController.combatUnitAttacksTile(11, 10, combatUnit);
//    }
//
//    @Test
//    public void testAttackCityMelee(){
//        Melee melee = new Melee(UnitTypes.WARRIOR, 10, 10, "ali");
//        WorldController.setSelectedCombatUnit(melee);
//        when(city.getCenterOfCity()).thenReturn(MapController.getTileByCoordinates(11,10));
//        when(city.getAttackStrength()).thenReturn(300.0);
//        when(city.getHealthPoint()).thenReturn(20.0);
//        when(city.getDefenseStrength()).thenReturn(10.0);
//        MapController.getTileByCoordinates(10, 10).setCombatUnit(melee);
//        MapController.getTileByCoordinates(11,10).setCivilization("hassan");
//        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName("hassan");
//        currentCivilization.addCity(city);
//        MapController.getTileByCoordinates(11,10).setCity(city);
//        String output = WarController.combatUnitAttacksTile(11, 10, combatUnit);
//        Assertions.assertEquals("", outputStreamCaptor.toString().trim());
//    }
//
//    @Test
//    public void testAttackCityRanged(){
//        Ranged ranged = new Ranged(UnitTypes.WARRIOR, 10, 10, "ali");
//        WorldController.setSelectedCombatUnit(ranged);
//        when(city.getCenterOfCity()).thenReturn(MapController.getTileByCoordinates(11,10));
//        when(city.getAttackStrength()).thenReturn(300.0);
//        when(city.getHealthPoint()).thenReturn(20.0);
//        when(city.getDefenseStrength()).thenReturn(10.0);
//        MapController.getTileByCoordinates(10, 10).setCombatUnit(ranged);
//        MapController.getTileByCoordinates(11,10).setCivilization("hassan");
//        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName("hassan");
//        currentCivilization.addCity(city);
//        MapController.getTileByCoordinates(11,10).setCity(city);
//        String output = WarController.combatUnitAttacksTile(11, 10, combatUnit);
//        Assertions.assertEquals("", outputStreamCaptor.toString().trim());
//    }
//
//    @AfterEach
//    public void closing() throws IOException {
//        System.setOut(standardOut);
//    }
//}
