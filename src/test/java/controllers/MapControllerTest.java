package controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class MapControllerTest {

    @BeforeEach
    public void setup() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("ali");
        usernames.add("hassan");
        WorldController.newWorld(usernames);
    }

    @Test
    public void riverCellsRefreshTest() {
        int[][] visionStatesOfMap = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getVisionStatesOfMap();
        for (int i = 1; i < MapController.width; i++) {
            for (int j = 1; j < MapController.length; j++) {
                visionStatesOfMap[i][j] = 2;
                boolean[] isRiver= MapController.getTileByCoordinates(i,j).getIsRiver();
                for (int k = 0; k < 6; k++) {
                    isRiver[k] = true;
                }
            }
        }
        MapController.cellsRefresh();
    }

    @Test
    public void updateUnitPostionstest(){

    }

    @Test
    public void cutStringLengthTest() {
        MapController.cutStringLenght("               ", 2);
    }


    @Test
    public void resetMapTest() {
        MapController.resetMap();
        Assertions.assertNotNull(MapController.getCellsMap());
    }

    @Test
    public void getCellsMapTest() {
        Assertions.assertNotNull(MapController.getCellsMap());
    }

    @Test
    public void getTileCenterByCoordinatesTest() {
        Assertions.assertNotNull(MapController.getTileCenterByCoordinates(10, 10));
    }

    @Test
    public void getTileByCoordinatesTest() {
        Assertions.assertNotNull(MapController.getTileByCoordinates(10, 10));
    }

}
