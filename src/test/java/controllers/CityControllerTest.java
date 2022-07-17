package controllers;

import enums.BuildingTypes;
import enums.tiles.TileBaseTypes;
import enums.tiles.TileFeatureTypes;
import enums.units.UnitTypes;
import models.*;
import models.tiles.Tile;
import models.units.CombatUnit;
import models.units.NonCombatUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityControllerTest {
    @Mock
    City city;
    @Mock
    NonCombatUnit nonCombatUnit;
    @Mock
    CombatUnit combatUnit;

    @BeforeEach
    public void setUpWorld() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("ali"); usernames.add("hassan");
        WorldController.newWorld(usernames, 10, 10);
    }

    @Test
    public void addGoodsToCityTest() {
        ArrayList<Citizen> citizens = new ArrayList<>();
        citizens.add(new Citizen(1));
        citizens.get(0).setIsWorking(true);
        citizens.get(0).setXOfWorkingTile(10);
        citizens.get(0).setYOfWorkingTile(10);
        Tile tile = new Tile(TileFeatureTypes.OASIS, TileBaseTypes.PLAIN, 10 ,10, null);
        tile.setCivilization("ali");
        MapController.getMap()[10][10] = tile;
        when(city.getCitizens()).thenReturn(citizens);
        when(city.getFood()).thenReturn(20.0);
        when(city.getCenterOfCity()).thenReturn(tile);
        CityController.addGoodsToCity(city);
        verify(city).setFood(0);
        verify(city).setProduction(2.0);
    }

    @Test
    public void consumeCityFoodTest() {
        ArrayList<Citizen> citizens = new ArrayList<>();
        citizens.add(new Citizen(1));
        citizens.add(new Citizen(2));
        Tile tile = new Tile(TileFeatureTypes.OASIS, TileBaseTypes.PLAIN, 10 ,10, null);
        tile.setCivilization("ali");
        MapController.getMap()[10][10] = tile;
        when(city.getCitizens()).thenReturn(citizens);
        when(city.getCenterOfCity()).thenReturn(tile);
        CityController.consumeCityFood(3.0, city);
        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());

    }

    @Test
    public void starveCitizenTest() {
        ArrayList<Citizen> citizens = new ArrayList<>();
        citizens.add(new Citizen(1));
        citizens.get(0).setIsWorking(true);
        citizens.get(0).setXOfWorkingTile(10);
        citizens.get(0).setYOfWorkingTile(10);
        Tile tile = new Tile(TileFeatureTypes.OASIS, TileBaseTypes.PLAIN, 10 ,10, null);
        tile.setCivilization("ali");
        MapController.getMap()[10][10] = tile;
        when(city.getCitizens()).thenReturn(citizens);
        when(city.getCenterOfCity()).thenReturn(tile);
        CityController.starveCitizen(city);
        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
    }

    @Test
    public void lockCitizenToTileTest() {
        Tile tile = new Tile(TileFeatureTypes.OASIS, TileBaseTypes.PLAIN, 10 ,10, null);
        tile.setCivilization("ali");
        MapController.getMap()[10][10] = tile;
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(MapController.getTileByCoordinates(10, 10));
        tiles.add(MapController.getTileByCoordinates(11, 11));
        ArrayList<Citizen> citizens = new ArrayList<>();
        citizens.add(new Citizen(1));
        when(city.getTerritory()).thenReturn(tiles);
        when(city.getCitizens()).thenReturn(citizens);
        CityController.lockCitizenToTile(city, 1, 10, 10);
        Assertions.assertNotEquals(-1, citizens.get(0).getXOfWorkingTile());
        Assertions.assertNotEquals(-1, citizens.get(0).getYOfWorkingTile());
        Assertions.assertTrue(citizens.get(0).isWorking());
        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
    }

    @Test
    public void unlockCitizenFromTileTest() {
        Tile tile = new Tile(TileFeatureTypes.OASIS, TileBaseTypes.PLAIN, 10 ,10, null);
        tile.setCivilization("ali");
        MapController.getMap()[10][10] = tile;
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(MapController.getTileByCoordinates(10, 10));
        tiles.add(MapController.getTileByCoordinates(11, 11));
        ArrayList<Citizen> citizens = new ArrayList<>();
        citizens.add(new Citizen(1));
        citizens.get(0).setIsWorking(true);
        citizens.get(0).setXOfWorkingTile(10);
        citizens.get(0).setYOfWorkingTile(10);
        when(city.getCitizens()).thenReturn(citizens);
        CityController.unlockCitizenFromTile(city, 1);
        Assertions.assertEquals(-1, citizens.get(0).getXOfWorkingTile());
        Assertions.assertEquals(-1, citizens.get(0).getYOfWorkingTile());
        Assertions.assertFalse(citizens.get(0).isWorking());
        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());

    }

    @Test
    public void updateCityProductionGoldPayingTest() {
        WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).addCity(city);
        when(city.getCurrentProductionRemainingCost()).thenReturn(1.0);
        when(city.isPayingGoldForCityProduction()).thenReturn(true);
        CityController.updateCityProduction(city);
        verify(city).setCurrentProductionRemainingCost(1.0);
    }

    @Test
    public void updateCityProductionNotGoldPayingTest() {
        WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).addCity(city);
        when(city.getCurrentProductionRemainingCost()).thenReturn(1.0);
        when(city.isPayingGoldForCityProduction()).thenReturn(false);
        when(city.getProduction()).thenReturn(1.0);
        CityController.updateCityProduction(city);
        verify(city).setCurrentProductionRemainingCost(0.0);
    }

    @Test
    public void cityProductionWarningsTest() {
        when(city.getCurrentUnit()).thenReturn(nonCombatUnit);
        MapController.getTileByCoordinates(10, 10).setNonCombatUnit(nonCombatUnit);
        when(city.getCenterOfCity()).thenReturn(MapController.getTileByCoordinates(10, 10));
        CityController.cityProductionWarnings(city);
    }

    @Test
    public void producingUnitTest() {
        WorldController.setSelectedCity(city);
        UnitTypes unitEnum = UnitTypes.HORSE_MAN;
        WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getTechnologies().put(unitEnum.getRequiredTechnology(), 0);
        WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getStrategicResources().put(unitEnum.getRequiredResource().getName(), 2);
        when(city.getCenterOfCity()).thenReturn(MapController.getTileByCoordinates(10, 10));
        CityController.producingUnit(unitEnum, "gold");
        verify(city).setCurrentUnit(any(models.units.Unit.class));
        verify(city).setPayingGoldForCityProduction(true);
        verify(city).setCurrentProductionRemainingCost(unitEnum.getCost());
    }

    @Test
    public void producingSettlerUnitTest() {
        WorldController.setSelectedCity(city);
        UnitTypes unitEnum = UnitTypes.SETTLER;
        ArrayList<Citizen> citizens = new ArrayList<>();
        citizens.add(new Citizen(1));
        citizens.add(new Citizen(2));
        citizens.add(new Citizen(3));
        WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getTechnologies().put(unitEnum.getRequiredTechnology(), 0);
        when(city.getCenterOfCity()).thenReturn(MapController.getTileByCoordinates(10, 10));
        when(city.getCitizens()).thenReturn(citizens);
        CityController.producingUnit(unitEnum, "gold");
        verify(city).setCurrentUnit(any(models.units.Unit.class));
        verify(city).setPayingGoldForCityProduction(true);
        verify(city).setCurrentProductionRemainingCost(unitEnum.getCost());
    }

    @Test
    public void producingBuildingTest() {
        WorldController.setSelectedCity(city);
        Building building = new Building(BuildingTypes.ARMORY);
        Tile tile = new Tile(TileFeatureTypes.OASIS, TileBaseTypes.PLAIN, 10 ,10, null);
        tile.setCivilization("ali");
        MapController.getMap()[10][10] = tile;
        when(city.getCenterOfCity()).thenReturn(tile);
        CityController.producingBuilding(building, "gold");
        verify(city).setCurrentBuilding(building);
        verify(city).setPayingGoldForCityProduction(true);
        verify(city).setCurrentProductionRemainingCost(building.getCost());
        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
    }

    @Test
    public void buyTileAndAddItToCityTerritoryTest() {
        WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).setGold(200.0);
        CityController.buyTileAndAddItToCityTerritory(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()), city, 10, 10);
        Assertions.assertNotEquals(200.0, WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getGold());
        Assertions.assertNotNull(MapController.getMap()[10][10].getCivilizationName());
        verify(city).getTerritory();
    }

    @Test
    public void unemployedCitizensDataTest() {
        ArrayList<Citizen> citizens = new ArrayList<>();
        citizens.add(new Citizen(1));
        citizens.add(new Citizen(2));
        citizens.get(0).setIsWorking(true);
        citizens.get(0).setXOfWorkingTile(10);
        citizens.get(0).setYOfWorkingTile(10);
        when(city.getCitizens()).thenReturn(citizens);
        Assertions.assertNotEquals("", CityController.unemployedCitizensData(city));
    }

    @Test
    public void employedCitizensDataTest() {
        ArrayList<Citizen> citizens = new ArrayList<>();
        citizens.add(new Citizen(1));
        citizens.add(new Citizen(2));
        citizens.get(0).setIsWorking(true);
        citizens.get(0).setXOfWorkingTile(10);
        citizens.get(0).setYOfWorkingTile(10);
        when(city.getCitizens()).thenReturn(citizens);
        Assertions.assertNotEquals("", CityController.employedCitizensData(city));
    }

    @Test
    public void conquerCityTest() {
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(MapController.getTileByCoordinates(10, 10));
        tiles.add(MapController.getTileByCoordinates(11, 11));
        when(city.getTerritory()).thenReturn(tiles);
        when(combatUnit.getCivilizationName()).thenReturn("ali");
        CityController.conquerCity(city, combatUnit);
        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCities());
        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
    }

    @Test
    public void destroyCityTest() {
        ArrayList<Tile> tiles = new ArrayList<>();
        tiles.add(MapController.getTileByCoordinates(10, 10));
        tiles.add(MapController.getTileByCoordinates(11, 11));
        when(city.getTerritory()).thenReturn(tiles);
        when(combatUnit.getCivilizationName()).thenReturn("ali");
        CityController.destroyCity(city, combatUnit);
        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCities());
        Assertions.assertNotNull(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getNotifications());
    }
}
