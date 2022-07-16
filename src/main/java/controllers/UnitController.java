package controllers;

import application.App;
import enums.Improvements;
import enums.tiles.TileFeatureTypes;
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
            WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn() +
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
            currentTile.setCity(city);
            currentCivilization.addCity(city);
            CivilizationController.updateMapVision(currentCivilization);
            WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                            + " you found the city " + city.getName() + " in ( " + String.valueOf(settler.getCurrentX() + 1)
                            + " , " + String.valueOf(settler.getCurrentY() + 1) + " ) coordinates",
                    currentCivilization.getName());
        }
        return null;
    }

    public static String buildRoad(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        if (currentTile.getRoadState() == 0) {
            return "there is already road on this tile";
        } else {
            currentTile.setRoadState(3);
            worker.putToWork(3);
            WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                    + " you built a road on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                    + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
        }
        return null;
    }

    public static String buildRailRoad(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        if (currentTile.getRailRoadState() == 0) {
            return "there is already railRoad on this tile";
        } else {
            currentTile.setRailRoadState(3);
            worker.putToWork(3);
            WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                    + " you built a railRoad on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                    + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
        }
        return null;
    }

    public static String removeRouteFromTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        if (currentTile.getRoadState() != 0 && currentTile.getRailRoadState() != 0) {
            return "there is not any roads or railRoads on this tile";
        } else {
            currentTile.setRoadState(9999);
            currentTile.setRailRoadState(9999);
            worker.putToWork(3);
            WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                    + " you removed routes from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                    + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
        }
        return null;
    }

    public static String buildImprovement(Worker worker, Improvements improvement) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        if (currentTile.getImprovement() != null && currentTile.getImprovement().equals(improvement)) {
            return "there is already this kind of improvement on this tile";
        } else if (WorldController.getWorld().getCivilizationByName(worker.getCivilizationName())
                .getTechnologies().get(improvement.getRequiredTechnology()) > 0) {
            return "you don't have the required technology";
        } else if (!improvement.getPossibleTiles().contains(currentTile.getType()) &&
                !improvement.getPossibleTiles().contains(currentTile.getFeature())) {
            return "can't build on these kinds of tiles";
        } else {
            currentTile.setImprovement(improvement);
            currentTile.setImprovementTurnsLeftToBuild(6);
            worker.putToWork(6);
            WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " you built the " +
                    improvement.name().toLowerCase(Locale.ROOT) + " improvement on ( "
                    + String.valueOf(worker.getCurrentX() + 1) + " , " + String.valueOf(worker.getCurrentY() + 1)
                    + " ) coordinates", worker.getCivilizationName());
        }
        return null;
    }

    public static String removeJungleFromTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        if (currentTile.getFeature() != TileFeatureTypes.JUNGLE) {
            return "there is not a jungle on this tile";
        } else {
            currentTile.setFeature(null);
            worker.putToWork(3);
            WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                    + " you removed jungle from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                    + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
        }
        return null;
    }

    public static String removeForestFromTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        if (currentTile.getFeature() != TileFeatureTypes.FOREST) {
            return "there is not a forest on this tile";
        } else {
            currentTile.setFeature(null);
            worker.putToWork(3);
            WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                    + " you removed forest from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                    + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
        }
        return null;
    }

    public static String removeMarshFromTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        if (currentTile.getFeature() != TileFeatureTypes.SWAMP) {
            return "there is not a marsh on this tile";
        } else {
            currentTile.setFeature(null);
            worker.putToWork(3);
            WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                    + " you removed marsh from the tile on" + " ( " + String.valueOf(worker.getCurrentX() + 1) + " , "
                    + String.valueOf(worker.getCurrentY() + 1) + " ) coordinates", worker.getCivilizationName());
        }
        return null;
    }

    public static String repairTile(Worker worker) {
        Tile currentTile = MapController.getTileByCoordinates(worker.getCurrentX(), worker.getCurrentY());
        if (currentTile.getPillageState() == 0) {
            return "this tile has not been pillaged";
        } else {
            currentTile.setPillageState(3);
            worker.putToWork(3);
            WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
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
            WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn()
                    + " you removed jungle from the tile on" + " ( " + String.valueOf(x + 1) + " , "
                    + String.valueOf(y + 1) + " ) coordinates", WorldController.getWorld().getCurrentCivilizationName());
        }
        return null;
    }

    public static String delete(Unit unit) {
        Civilization wantedCivilization = WorldController.getWorld().getCivilizationByName(unit.getCivilizationName());
        if (unit instanceof Ranged) {
            wantedCivilization.removeRangedUnit((Ranged) unit);
        } else if (unit instanceof Melee) {
            wantedCivilization.removeMeleeUnit((Melee) unit);
        } else if (unit instanceof Worker) {
            wantedCivilization.removeWorker((Worker) unit);
        } else if (unit instanceof Settler) {
            wantedCivilization.removeSettler((Settler) unit);
        }
        WorldController.addNotification("In turn " + WorldController.getWorld().getActualTurn() + " you deleted the " +
                unit.getName() + " unit", unit.getCivilizationName());
        return null;
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
        imageView.setFitWidth(30);
        imageView.setFitHeight(50);
        imageView.setLayoutX(4 - 2 * imageView.getImage().getWidth() / 3);
        imageView.setLayoutY(12);
        Text text = new Text(unit.getUnitState().getName());
        text.setLayoutX(5 - 2 * imageView.getImage().getWidth() / 3);
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
