package controllers;

import models.units.CombatUnit;
import models.units.NonCombatUnit;
import models.units.Unit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MoveControllerTest {

    @Mock
    Unit unit;
    @Mock
    NonCombatUnit nonCombatUnit;

    @BeforeEach
    public void setUpWorld() {
        ArrayList<String> usernames = new ArrayList<>();
        usernames.add("ali"); usernames.add("hassan");
        WorldController.newWorld(usernames);
    }

    @Test
    public void impossibleToMoveToTileTest() {
        MapController.getTileByCoordinates(10, 10).setCivilization("hassan");
        MoveController.impossibleToMoveToTile(10, 10, unit);
    }

    @Test
    public void moveUnitToDestinationTest() {
        Unit newUnit = new Unit(enums.units.Unit.SETTLER, 10, 10, "ali");
        newUnit.setDestinationCoordinates(11, 11);
        MoveController.moveUnitToDestination(newUnit);
    }

    @Test
    public void moveUnitToDestinationErrorTest() {
        NonCombatUnit newUnit = new NonCombatUnit(enums.units.Unit.SETTLER, 10, 10, "ali");
        newUnit.setDestinationCoordinates(11, 10);
        MapController.getTileByCoordinates(11, 10).setNonCombatUnit(new NonCombatUnit(enums.units.Unit.SETTLER, 10, 10, "hassan"));
        MoveController.moveUnitToDestination(newUnit);
    }
}
