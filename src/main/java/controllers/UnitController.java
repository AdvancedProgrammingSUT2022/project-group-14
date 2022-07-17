package controllers;

import application.App;
import enums.Improvements;
import enums.units.CombatType;
import enums.units.UnitStates;
import enums.units.UnitTypes;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import models.*;
import models.tiles.Tile;
import models.units.*;

import java.util.Locale;
import java.util.Objects;

public class UnitController {

    public static String setUnitDestinationCoordinates(Unit unit, int x, int y) {
        String reason;
        if ((reason = MoveController.impossibleToMoveToTile(x, y, unit)) != null) {
            return reason;
        } else {
            unit.setDestinationCoordinates(x, y);
            MoveController.moveUnitToDestination(unit);
            CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() +
                            " you moved " + unit.getName() + " to ( " + String.valueOf(x + 1) + " , " + String.valueOf(y + 1) + " ) coordinates",
                    unit.getCivilizationName());
        }
        return null;
    }

    public static void resetMovingPoints(Civilization currentCivilization) {
        for (Unit unit : currentCivilization.getAllUnits()) {
            if (unit != null)
                unit.setMovementPoint(UnitTypes.valueOf(unit.getName().toUpperCase(Locale.ROOT)).getMovement());
        }
    }

    public static String setupRangedUnit(Unit unit, int x, int y) {
        if (unit instanceof Ranged && ((Ranged) unit).isSiegeUnit()) {
            ((Ranged) unit).setCoordinatesToSetup(x, y);
        } else {
            return "this unit doesn't have the ability to setup";
        }
        return null;
    }

    public static String garrisonCity(CombatUnit combatUnit) {
        Tile currentTile = MapController.getTileByCoordinates(combatUnit.getCurrentX(), combatUnit.getCurrentY());
        if (currentTile.getCity() == null) {
            return "you should be in a city to garrison";
        } else {
            combatUnit.setUnitState(UnitStates.GARRISON);
            currentTile.getCity().addGarrisonedUnits();
        }
        return null;
    }

    public static String foundCity(Settler settler) {
        Tile currentTile = MapController.getTileByCoordinates(settler.getCurrentX(), settler.getCurrentY());
        if (currentTile.getCity() != null) {
            return "can not found a city over another city";
        } else if (currentTile.getCivilizationName() != null
                && !currentTile.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
            return "can not found a city in enemy territory";
        } else {
            Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(settler.getCivilizationName());
            City city = new City(currentCivilization.getCityName(), settler.getCurrentX(), settler.getCurrentY());
            currentCivilization.addCity(city);
            currentTile.setCity(city);
            CivilizationController.updateMapVision(currentCivilization);
            CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                            + " you found the city " + city.getName() + " in ( " + String.valueOf(settler.getCurrentX() + 1)
                            + " , " + String.valueOf(settler.getCurrentY() + 1) + " ) coordinates",
                    currentCivilization.getName());
        }
        return null;
    }

    public static void buildRoad(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        currentTile.setRoadState(3);
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you built a road on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static void buildRailRoad(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        currentTile.setRailRoadState(3);
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you built a railRoad on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static void removeRouteFromTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        currentTile.setRoadState(9999);
        currentTile.setRailRoadState(9999);
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you removed routes from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static void buildImprovement(Worker worker, Improvements improvement) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        currentTile.setImprovement(improvement);
        currentTile.setImprovementTurnsLeftToBuild(6);
        worker.putToWork(6);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " you built the " +
                improvement.name().toLowerCase(Locale.ROOT) + " improvement on ( "
                + String.valueOf(worker.getCurrentX() + 1) + " , " + String.valueOf(worker.getCurrentY() + 1)
                + " ) coordinates", worker.getCivilizationName());
    }

    public static void removeJungleFromTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        currentTile.setFeature(null);
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you removed jungle from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static void removeForestFromTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        currentTile.setFeature(null);
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you removed forest from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static void removeMarshFromTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        currentTile.setFeature(null);
        worker.putToWork(3);
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                + " you removed marsh from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
    }

    public static String repairTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        if (currentTile.getPillageState() == 0) {
            return "this tile has not been pillaged";
        } else {
            currentTile.setPillageState(3);
            worker.putToWork(3);
            CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                    + " you started to repair the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                    + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
        }
        return null;
    }

    public static String pillage(int x, int y) {
        Tile currentTile = MapController.getTileByCoordinates(x, y);
        if (currentTile.getPillageState() == 9999) {
            return "this tile has been pillaged before";
        } else {
            currentTile.setPillageState(9999);
            CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                    + " you removed jungle from the tile on" + " ( " + String.valueOf(x + 1) + " , "
                    + String.valueOf(y + 1) + " ) coordinates", WorldController.getWorld().getCurrentCivilizationName());
        }
        return null;
    }

    public static void delete(Unit unit) {
        Civilization wantedCivilization = WorldController.getWorld().getCivilizationByName(unit.getCivilizationName());
        if (unit instanceof Ranged) {
            wantedCivilization.getRanges().remove((Ranged) unit);
        } else if (unit instanceof Melee) {
            wantedCivilization.getMelees().remove((Melee) unit);
        } else if (unit instanceof Worker) {
            wantedCivilization.getWorkers().remove((Worker) unit);
        } else if (unit instanceof Settler) {
            wantedCivilization.getSettlers().remove((Settler) unit);
        }
        CivilizationController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " you deleted the " +
                unit.getName() + " unit", unit.getCivilizationName());
    }

    public static String upgradeUnit(UnitTypes unitEnum) {
        if (WorldController.getSelectedCombatUnit() == null) {
            return "you should select a combat unit first";
        } else if (unitEnum.getCombatType() == CombatType.NON_COMBAT) {
            return "you can only upgrade a combat unit to a combat unit";
        } else {
            Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
            if (currentCivilization.getGold() < ((double) unitEnum.getCost() / 2))
                return "you don't have enough gold for upgrading to this unit";
            if (WorldController.getSelectedCombatUnit() instanceof Ranged) {
                if (unitEnum.getRangedCombatStrength() == 0)
                    return "you can't upgrade a ranged unit into a melee unit";
                currentCivilization.setGold(currentCivilization.getGold() - ((double) unitEnum.getCost() / 2));
                Ranged newUnit = new Ranged(unitEnum,
                        WorldController.getSelectedCombatUnit().getCurrentX(),
                        WorldController.getSelectedCombatUnit().getCurrentY(),
                        currentCivilization.getName());
                currentCivilization.getRanges().add(newUnit);
                currentCivilization.getRanges().remove(WorldController.getSelectedCombatUnit());
                MapController.getMap()[newUnit.getCurrentX()][newUnit.getCurrentY()].setCombatUnit(newUnit);
            } else {
                if (unitEnum.getRangedCombatStrength() != 0)
                    return "you can't upgrade a melee unit into a ranged unit";
                currentCivilization.setGold(currentCivilization.getGold() - ((double) unitEnum.getCost() / 2));
                Melee newUnit = new Melee(unitEnum,
                        WorldController.getSelectedCombatUnit().getCurrentX(),
                        WorldController.getSelectedCombatUnit().getCurrentY(),
                        currentCivilization.getName());
                currentCivilization.getMelees().add(newUnit);
                currentCivilization.getMelees().remove(WorldController.getSelectedCombatUnit());
                MapController.getMap()[newUnit.getCurrentX()][newUnit.getCurrentY()].setCombatUnit(newUnit);
            }
            return null;
        }
    }

    public static Group getUnitGroup(Unit unit) {
        Group group = new Group();
        ImageView imageView = new ImageView(unit.getUnitType().getImage());
        imageView.setFitWidth(imageView.getImage().getWidth() / 5);
        imageView.setFitHeight(imageView.getImage().getHeight() / 5);
        imageView.setLayoutX(-100);
        imageView.setLayoutY(12);
        Text text = new Text(unit.getUnitState().getName());
        text.setWrappingWidth(50);
        text.setLayoutX(-100);
        text.setLayoutY(2);
        group.getChildren().add(imageView);
        group.getChildren().add(text);
        group.setCursor(Cursor.HAND);
        return group;
    }

    public static Image getActionImage(String name) {
        return new Image(Objects.requireNonNull(App.class.getResource("/images/units/actions/" + name + ".png")).toString());
    }
}
