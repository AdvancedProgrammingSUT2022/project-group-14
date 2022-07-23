//package Server.controllers;
//
//import Common.enums.Technologies;
//import Common.models.tiles.Tile;
//import Common.models.units.Melee;
//import Common.models.units.Settler;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//
//@ExtendWith(MockitoExtension.class)
//public class WorldControllerTest {
//
//    @Mock
//    Melee unit1;
//    @Mock
//    Settler unit2;
//    @Mock
//    Tile tile;
//
//    @BeforeEach
//    public void setUpWorld() {
//        ArrayList<String> usernames = new ArrayList<>();
//        usernames.add("ali"); usernames.add("hassan");
//        WorldController.newWorld(usernames, 40, 40);
//    }
//
//    @Test
//    public void nextTurnTest() {
//        WorldController.nextTurn();
//        Assertions.assertNotEquals(0, WorldController.getWorld().getTurn());
//    }
//
//    @Test
//    public void nextTurnImpossibleAllTechsTest() {
//        for (Technologies value : Technologies.values()) {
//            WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getTechnologies().put(value, 1);
//        }
//        WorldController.nextTurnImpossible();
//    }
//
//    @Test
//    public void nextTurnImpossibleNotAllTechsTest() {
//        for (Technologies value : Technologies.values()) {
//            WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getTechnologies().put(value, 1);
//        }
//        WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).setCurrentTechnology(Technologies.HORSEBACK_RIDING);
//        WorldController.nextTurnImpossible();
//    }
//
//    @Test
//    public void gettersAndSettersTest() {
//        WorldController.setSelectedTile(tile);
//        Assertions.assertNotNull(WorldController.getSelectedTile());
//        WorldController.setSelectedNonCombatUnit(unit2);
//        Assertions.assertNotNull(WorldController.getSelectedNonCombatUnit());
//        Assertions.assertFalse(WorldController.unitIsNotSelected());
//    }
//}
