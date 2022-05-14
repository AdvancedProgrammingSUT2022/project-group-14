package controllers;

import models.Civilization;
import models.Tile;
import models.units.Settler;
import models.units.Worker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TIleControllerTest {

    @Mock
    Civilization civilization;
    @Mock
    models.units.CombatUnit combatUnit;
    @Mock
    Worker worker;
    @Mock
    Settler settler;
    @Mock
    Tile tile;

    @BeforeEach
    public void setup() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("ali");
        usernames.add("hassan");

        WorldController.newWorld(usernames);


    }

    @Test
    public void selectedTileIsValidTestTrue() {
        Assertions.assertTrue(TileController.selectedTileIsValid(MapController.width - 20, MapController.length - 30));
    }

    @Test
    public void selectedTileIsValidTestFalse() {
        Assertions.assertFalse(TileController.selectedTileIsValid(MapController.width, 1));
    }

    @Test
    public void updateBuildingProgressTest0() {
        ArrayList<models.units.Unit> units = new ArrayList<>();

        Tile tile = MapController.getTileByCoordinates(10, 10);

        units.add(worker);
        units.add(settler);
        units.add(combatUnit);

        when(civilization.getAllUnits()).thenReturn(units);
        when(worker.isWorking()).thenReturn(true);
        when(worker.getCurrentX()).thenReturn(10);
        when(worker.getCurrentY()).thenReturn(10);

        tile.setPillageState(0);
        tile.setRoadState(0);
        tile.setRailRoadState(0);
        tile.setImprovementTurnsLeftToBuild(1);

        TileController.updateBuildingProgress(civilization);


        Assertions.assertEquals(0, tile.getImprovementTurnsLeftToBuild());


    }
}
