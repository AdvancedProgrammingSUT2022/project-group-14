package controllers;

import enums.units.CombatType;
import enums.units.Unit;
import models.City;
import models.Civilization;
import models.User;
import models.units.CombatUnit;
import models.units.Melee;
import models.units.Ranged;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import views.menus.ProfileMenu;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WarControllerTest {
    @Mock
    CombatUnit combatUnit;
    @Mock
    City city;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUpUsers() throws IOException {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("ali"); usernames.add("hassan");
        WorldController.newWorld(usernames);
        System.setOut(new PrintStream(outputStreamCaptor));
        MapController.getTileByCoordinates(10, 10).setCombatUnit(combatUnit);
        WorldController.setSelectedCombatUnit(combatUnit);
    }

    @Test
    public void testCombatUnitNotUnderUserControl(){
        when(combatUnit.getCivilizationName()).thenReturn("hassan");
        String output = WarController.combatUnitAttacksTile(11, 10);
        Assertions.assertEquals("unit is not under your control", output);
    }

    @Test
    public void testDestinationTileIsInvalid(){
        when(combatUnit.getCivilizationName()).thenReturn("ali");
        String output = WarController.combatUnitAttacksTile(-1, 0);
        Assertions.assertEquals("wanted tile is invalid", output);
    }

    @Test
    public void testAttackCityMelee(){
        Melee melee = new Melee(Unit.WARRIOR, 10, 10, "ali");
        WorldController.setSelectedCombatUnit(melee);
        when(city.getCenterOfCity()).thenReturn(MapController.getTileByCoordinates(11,10));
        when(city.getAttackStrength()).thenReturn(300.0);
        when(city.getHealthPoint()).thenReturn(20.0);
        when(city.getDefenseStrength()).thenReturn(10.0);
        //when(melee.getCombatType()).thenReturn(CombatType.MELEE);
        MapController.getTileByCoordinates(10, 10).setCombatUnit(melee);
        MapController.getTileByCoordinates(11,10).setCivilization("hassan");
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        currentCivilization.addCity(city);
        MapController.getTileByCoordinates(11,10).setCity(city);
        String output = WarController.combatUnitAttacksTile(11, 10);
        Assertions.assertEquals("", outputStreamCaptor.toString().trim());
    }

    @Test
    public void testAttackCityRanged(){
        Ranged ranged = new Ranged(Unit.WARRIOR, 10, 10, "ali");
        WorldController.setSelectedCombatUnit(ranged);
        when(city.getCenterOfCity()).thenReturn(MapController.getTileByCoordinates(11,10));
        when(city.getAttackStrength()).thenReturn(300.0);
        when(city.getHealthPoint()).thenReturn(20.0);
        when(city.getDefenseStrength()).thenReturn(10.0);
        //when(melee.getCombatType()).thenReturn(CombatType.MELEE);
        MapController.getTileByCoordinates(10, 10).setCombatUnit(ranged);
        MapController.getTileByCoordinates(11,10).setCivilization("hassan");
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        currentCivilization.addCity(city);
        MapController.getTileByCoordinates(11,10).setCity(city);
        String output = WarController.combatUnitAttacksTile(11, 10);
        Assertions.assertEquals("", outputStreamCaptor.toString().trim());
    }

    @AfterEach
    public void closing() throws IOException {
        System.setOut(standardOut);
    }
}
