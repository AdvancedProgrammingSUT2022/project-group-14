package controllers;

import enums.Technologies;
import models.City;
import models.Civilization;
import models.tiles.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CivilizationControllerTest
{

    @Mock
    Civilization civilization;
    @Mock
    City city;
    @Mock
    Tile tile;

    @BeforeEach
    public void setup() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("ali");
        usernames.add("hassan");
        WorldController.newWorld(usernames, 40, 40);
    }

    @Test
    public void updateTechnologyTest(){
        when(civilization.getCurrentTechnology()).thenReturn(Technologies.THEOLOGY);
        when(civilization.getScience()).thenReturn(0.0);
        when(civilization.getTechnologies()).thenReturn(new HashMap<>(Map.of(Technologies.THEOLOGY,0)));
        when(civilization.getCities()).thenReturn(new ArrayList<>(List.of(city)));
        when(city.getTerritory()).thenReturn(new ArrayList<>(List.of(tile)));

        CivilizationController.updateTechnology(civilization);
        verify(civilization).setCurrentTechnology(null);
    }

    @Test
    public void updateCitiesGoodsTest(){
        Civilization civilization1 = new Civilization("nikan", 0);
        City city1 = new City("Hey You" , 10 ,20, "nikan");
        civilization1.addCity(city1);

        CivilizationController.updateCitiesGoods(civilization1);
    }

    @Test
    public void updateCitiesProductionsTest(){
        Civilization civilization1 = new Civilization("nikan", 0);
        City city1 = new City("Hey You" , 10 ,20, "nikan");
        civilization1.addCity(city1);

        CivilizationController.updateCitiesProductions(civilization1);
    }

    @Test
    public void updateSienceTest(){
        Civilization civilization1 = new Civilization("nikan", 0);
        City city1 = new City("Hey You" , 10 ,20, "nikan");
        civilization1.addCity(city1);

        CivilizationController.updateScience(civilization1);
    }

    @Test
    public void payRequiredPriceForKeepingRoadsAndRailroadsTest(){

        WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).addCity(city);

        when(city.getTerritory()).thenReturn(new ArrayList<>(List.of(tile)));
        when(tile.getRoadState()).thenReturn(0);
        CivilizationController.payRequiredPriceForKeepingRoadsAndRailroads(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()));
    }

    @Test
    public void payRequiredPriceForKeepingUnitsTest(){
        Civilization civilization1 = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        CivilizationController.payRequiredPriceForKeepingUnits(civilization1);
    }

}
