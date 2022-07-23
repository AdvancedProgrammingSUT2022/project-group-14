//package Server.controllers;
//
//import Common.models.tiles.Tile;
//import Common.models.units.Melee;
//import Common.models.units.Worker;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class MapControllerTest {
//
//    @Mock
//    Tile tile;
//    @Mock
//    Melee melee;
//    @Mock
//    Worker worker;
//
//    @BeforeEach
//    public void setup() {
//        ArrayList<String> usernames = new ArrayList<>();
//        usernames.add("ali");
//        usernames.add("hassan");
//        WorldController.newWorld(usernames, 40, 40);
//    }
//
//    @Test
//    public void riverCellsRefreshTest() {
//        int[][] visionStatesOfMap = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getVisionStatesOfMap();
//        for (int i = 1; i < MapController.width; i++) {
//            for (int j = 1; j < MapController.height; j++) {
//                visionStatesOfMap[i][j] = 2;
//                boolean[] isRiver = MapController.getTileByCoordinates(i, j).getIsRiver();
//                for (int k = 0; k < 6; k++) {
//                    isRiver[k] = true;
//                }
//            }
//        }
//    }
//
//    @Test
//    public void updateUnitPostionsTest() {
//        MapController.getTileByCoordinates(10, 10).setCombatUnit(melee);
//        when(melee.getCurrentX()).thenReturn(5);
//
//        MapController.updateUnitPositions();
//
//        Assertions.assertNull(MapController.getTileByCoordinates(10,10).getCombatUnit());
//    }
//
//    @Test
//    public void updateUnitPostionsTest1() {
//        MapController.getTileByCoordinates(10, 10).setNonCombatUnit(worker);
//        when(worker.getCurrentX()).thenReturn(5);
//
//        MapController.updateUnitPositions();
//
//        Assertions.assertNull(MapController.getTileByCoordinates(10 , 10 ).getNonCombatUnit());
//    }
//
//    @Test
//    public void cutStringLengthTest() {
//
//    }
//
//
//    @Test
//    public void resetMapTest() {
//
//    }
//
//    @Test
//    public void getCellsMapTest() {
//
//    }
//
//    @Test
//    public void getTileCenterByCoordinatesTest() {
//
//    }
//
//    @Test
//    public void getTileByCoordinatesTest() {
//        Assertions.assertNotNull(MapController.getTileByCoordinates(10, 10));
//    }
//
//}
