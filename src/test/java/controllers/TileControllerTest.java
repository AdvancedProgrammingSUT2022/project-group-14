package controllers;

import enums.Improvements;
import enums.Technologies;
import enums.resources.StrategicResourceTypes;
import enums.units.UnitStates;
import models.Civilization;
import models.tiles.Tile;
import models.resources.Resource;
import models.resources.StrategicResource;
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
public class TileControllerTest {

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
    @Mock
    Resource resource;

    @BeforeEach
    public void setup() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("ali");
        usernames.add("hassan");

        WorldController.newWorld(usernames, 40 , 40);


    }

    @Test
    public void selectedTileIsValidTestTrue() {
        Assertions.assertTrue(TileController.selectedTileIsValid(MapController.width - 20, MapController.height - 30));
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
        when(worker.getUnitState() == UnitStates.WORKING).thenReturn(true);
        when(worker.getCurrentX()).thenReturn(10);
        when(worker.getCurrentY()).thenReturn(10);

        tile.setPillageState(0);
        tile.setRoadState(0);
        tile.setRailRoadState(0);
        tile.setImprovementTurnsLeftToBuild(1);

        TileController.updateBuildingProgress(civilization);


        Assertions.assertEquals(0, tile.getImprovementTurnsLeftToBuild());
    }

    @Test
    public void resourceIsAvailableToBeUsed() {
        for (Technologies value : Technologies.values()) {
        WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getTechnologies().put(value, 100);
        }
        StrategicResource resource1 = new StrategicResource(StrategicResourceTypes.IRON);

        when(tile.getImprovement()).thenReturn(Improvements.MINE);
        when(tile.getImprovementTurnsLeftToBuild()).thenReturn(0);



        boolean result = TileController.resourceIsAvailableToBeUsed(resource1, tile);

        Assertions.assertFalse(result);

    }
}
