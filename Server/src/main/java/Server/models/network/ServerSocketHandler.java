package Server.models.network;

import Server.controllers.*;
import Server.enums.Improvements;
import Server.enums.QueryResponses;
import Server.enums.Technologies;
import Server.enums.units.UnitStates;
import Server.models.City;
import Server.models.Civilization;
import Server.models.User;
import Server.models.tiles.Coordination;
import Server.models.tiles.Tile;
import Server.models.units.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ServerSocketHandler extends Thread {
    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;

    public ServerSocketHandler(Socket socket) throws IOException {
        this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        while (true) {
            try {
                String responseJson = new Gson().toJson(handleRequest(new Gson().fromJson(bufferedReader.readLine(), Request.class)));
                bufferedWriter.write(responseJson + "\n");
                bufferedWriter.flush();
            } catch (IOException ignored) {
                System.out.println("Client disconnected");
                break;
            }
        }
    }

    public Response handleRequest(Request request) {
        System.out.println(request.getQueryRequest() + " : " + request.getParams().toString());
        Civilization currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        switch (request.getQueryRequest()) {
            case LOGIN_USER -> {
                if (UserController.getUserByUsername(request.getParams().get("username")) == null) {
                    return new Response(QueryResponses.USER_NOT_EXIST, new HashMap<>());
                } else if (!Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).getPassword().equals(request.getParams().get("password"))) {
                    return new Response(QueryResponses.PASSWORD_INCORRECT, new HashMap<>());
                } else {
                    UserController.setLoggedInUser(Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))));
                    return new Response(QueryResponses.OK, new HashMap<>() {{
                        put("user", new Gson().toJson(UserController.getLoggedInUser()));
                    }});
                }
            }
            case CREATE_USER -> {
                if (UserController.getUserByUsername(request.getParams().get("username")) != null) {
                    return new Response(QueryResponses.USERNAME_EXIST, new HashMap<>());
                } else if (UserController.getUserByNickname(request.getParams().get("nickname")) != null) {
                    return new Response(QueryResponses.NICKNAME_EXIST, new HashMap<>());
                } else {
                    UserController.addUser(request.getParams().get("username"), request.getParams().get("password"), request.getParams().get("nickname"));
                    return new Response(QueryResponses.OK, new HashMap<>());
                }
            }
            case SET_CURRENT_TECHNOLOGY -> WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).setCurrentTechnology(Technologies.valueOf(request.getParams().get("technologyName")));
            case CHANGE_NICKNAME -> {
                if (!Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).getPassword().equals(request.getParams().get("password"))) {
                    return new Response(QueryResponses.PASSWORD_INCORRECT, new HashMap<>());
                } else if (UserController.getUserByNickname(request.getParams().get("nickname")) != null) {
                    return new Response(QueryResponses.NICKNAME_EXIST, new HashMap<>());
                } else {
                    Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).setNickname(request.getParams().get("nickname"));
                    return new Response(QueryResponses.OK, new HashMap<>() {{
                        put("user", new Gson().toJson(UserController.getUserByUsername(request.getParams().get("username"))));
                    }});
                }
            }
            case CHANGE_PASSWORD -> {
                if (!Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).getPassword().equals(request.getParams().get("oldPassword"))) {
                    return new Response(QueryResponses.PASSWORD_INCORRECT, new HashMap<>());
                } else {
                    Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).setPassword(request.getParams().get("newPassword"));
                    return new Response(QueryResponses.OK, new HashMap<>() {{
                        put("user", new Gson().toJson(UserController.getUserByUsername(request.getParams().get("username"))));
                    }});
                }
            }
            case CHANGE_AVATAR -> {
                Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).setAvatarFileAddress(request.getParams().get("address"));
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("user", new Gson().toJson(UserController.getUserByUsername(request.getParams().get("username"))));
                }});
            }
            case SORT_USERS -> UserController.sortUsers();
            case GET_USERS -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("users", new Gson().toJson(UserController.getUsers()));
                }});
            }
            case SET_STRATEGIC_RESOURCE -> currentCivilization.getStrategicResources().put(request.getParams().get("name"), currentCivilization.getStrategicResources().get(request.getParams().get("name")) + 1);
            case SET_LUXURY_RESOURCE -> {
                currentCivilization.getStrategicResources().put(request.getParams().get("name"), currentCivilization.getStrategicResources().get(request.getParams().get("name")) + 1);
                currentCivilization.setHappiness(currentCivilization.getHappiness() + 4);
            }
            case CHEAT_COMMAND -> GameCommandsValidation.checkCommands(request.getParams().get("command"));
            case HEX_MOUSE_CLICKED -> {
                if (WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName())
                        .getVisionStatesOfMap()[Integer.parseInt(request.getParams().get("x"))][Integer.parseInt(request.getParams().get("y"))] > 0) {
                    Coordination coordination = new Coordination(Integer.parseInt(request.getParams().get("x")), Integer.parseInt(request.getParams().get("y")));
                    HashMap<String, String> hashMap = new HashMap<>() {{
                        put("infoPopup", new Gson().toJson(TileController.getInfoPopup(coordination)));
                    }};
                    if (WorldController.getSelectedTile() != null
                            && WorldController.getSelectedTile().equals(MapController.getTileByCoordinates(coordination))) {
                        WorldController.setSelectedTile(null);
                        return new Response(QueryResponses.RESET_COLOR_ADJUST, hashMap);
                    } else {
                        if (WorldController.getSelectedTile() != null) {
                            //TODO set selected tile color adjust
                        }
                        WorldController.setSelectedTile(MapController.getTileByCoordinates(coordination));
                        return new Response(QueryResponses.BLOOM_COLOR_ADJUST, hashMap);
                    }
                } else {
                    return new Response(QueryResponses.IGNORE, new HashMap<>());
                }
            }
            case CITY_HEX_MOUSE_CLICKED -> {
                Coordination coordination = new Coordination(Integer.parseInt(request.getParams().get("x")), Integer.parseInt(request.getParams().get("y")));
                if (WorldController.getWorld().getCurrentCivilizationName().equals(MapController.getTileByCoordinates(coordination).getCity().getCenterOfCity().getCivilizationName())) {
                    WorldController.setSelectedCity(MapController.getTileByCoordinates(coordination).getCity());
                    return new Response(QueryResponses.OK, new HashMap<>());
                }
            }
            case UNIT_HEX_MOUSE_CLICKED -> {
                Unit unit = new Gson().fromJson(request.getParams().get("unit"), Unit.class);
                if (unit.getCivilizationName().equals(WorldController.getWorld().getCurrentCivilizationName())) {
                    if (unit instanceof CombatUnit) {
                        if (WorldController.getSelectedCombatUnit() != null && WorldController.getSelectedCombatUnit().equals(unit)) {
                            WorldController.setSelectedCombatUnit(null);
                        } else {
                            WorldController.setSelectedCombatUnit((CombatUnit) unit);
                        }
                    } else if (unit instanceof NonCombatUnit) {
                        if (WorldController.getSelectedNonCombatUnit() != null && WorldController.getSelectedNonCombatUnit().equals(unit)) {
                            WorldController.setSelectedNonCombatUnit(null);
                        } else {
                            WorldController.setSelectedNonCombatUnit((NonCombatUnit) unit);
                        }
                    }
                }
            }
            case GET_UNIT_GROUP -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("group", new Gson().toJson(UnitController.getUnitGroup(new Gson().fromJson(request.getParams().get("unit"), Unit.class))));
                }});
            }
            case HEX_IS_TERRITORY -> {
                for (City city : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCities()) {
                    for (Tile tile : city.getTerritory()) {
                        if (MapController.getTileByCoordinates(new Coordination(Integer.parseInt(request.getParams().get("X")), Integer.parseInt(request.getParams().get("y")))).equals(tile) && tile.getCity() == null)
                            return new Response(QueryResponses.OK, new HashMap<>() {{
                                put("boolean", String.valueOf(true));
                            }});
                    }
                }
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("boolean", String.valueOf(false));
                }});
            }
            case HEX_VISION_UPDATE -> {
                int[][] visionState = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getVisionStatesOfMap();
                if (visionState[Integer.parseInt(request.getParams().get("x"))][Integer.parseInt(request.getParams().get("y"))] == 0) {
                    return new Response(QueryResponses.FOG, new HashMap<>());
                } else if (visionState[Integer.parseInt(request.getParams().get("x"))][Integer.parseInt(request.getParams().get("y"))] == 1) {
                    return new Response(QueryResponses.REVEALED, new HashMap<>() {{
                        put("tile", new Gson().toJson(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getRevealedTiles()[Integer.parseInt(request.getParams().get("x"))][Integer.parseInt(request.getParams().get("y"))]));
                    }});
                }
            }
            case NEW_WORLD -> {
                WorldController.newWorld(new Gson().fromJson(request.getParams().get("people"), new TypeToken<List<String>>() {
                }.getType()), Integer.parseInt(request.getParams().get("width")), Integer.parseInt(request.getParams().get("height")));
            }
            case CIV_GOLD -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("gold", String.valueOf(currentCivilization.getGold()));
                }});
            }
            case CIV_HAPPINESS -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("happiness", String.valueOf(currentCivilization.getHappiness()));
                }});
            }
            case CIV_SCIENCE -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("science", String.valueOf(currentCivilization.getScience()));
                }});
            }
            case GET_AVAILABLE_TECHNOLOGIES -> {
                HashSet<Technologies> technologies = CivilizationController.getAvailableTechnologies(WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()));
                technologies.removeIf(technology -> WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCurrentTechnology() == technology);
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("technologies", new Gson().toJson(technologies));
                }});
            }
            case UNIT_PANEL_UPDATE -> {
                if (WorldController.getSelectedCombatUnit() != null) {
                    return new Response(QueryResponses.COMBAT_UNIT_SELECTED, new HashMap<>() {{
                        put("unit", new Gson().toJson(WorldController.getSelectedCombatUnit()));
                    }});
                } else if (WorldController.getSelectedNonCombatUnit() != null) {
                    return new Response(QueryResponses.NONCOMBAT_UNIT_SELECTED, new HashMap<>() {{
                        put("unit", new Gson().toJson(WorldController.getSelectedNonCombatUnit()));
                    }});
                } else if (WorldController.unitIsNotSelected()) {
                    return new Response(QueryResponses.UNIT_NOT_SELECTED, new HashMap<>());
                }
            }
            case TECH_PANEL_UPDATE -> {
                if (currentCivilization.getCurrentTechnology() != null && !Boolean.parseBoolean(request.getParams().get("boolean"))) {
                    return new Response(QueryResponses.SET_TECH_VISIBLE, new HashMap<>() {{
                        put("technology", new Gson().toJson(currentCivilization.getCurrentTechnology()));
                    }});
                } else if (currentCivilization.getCurrentTechnology() == null && Boolean.parseBoolean(request.getParams().get("boolean"))) {
                    return new Response(QueryResponses.SET_TECH_INVISIBLE, new HashMap<>());
                }
            }
            case MOVE_ACTION -> {
                if (WorldController.getSelectedTile() != null) {
                    Unit unit = new Gson().fromJson(request.getParams().get("unit"), Unit.class);
                    UnitController.setUnitDestinationCoordinates(unit, WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY());
                    MoveController.moveUnitToDestination(unit);
                }
            }
            case DELETE_ACTION -> UnitController.delete(new Gson().fromJson(request.getParams().get("unit"), Unit.class));
            case SLEEP_ACTION -> new Gson().fromJson(request.getParams().get("unit"), Unit.class).setUnitState(UnitStates.SLEEP);
            case WAKE_ACTION -> new Gson().fromJson(request.getParams().get("unit"), Unit.class).setUnitState(UnitStates.WAKE);
            case ALERT_ACTION -> new Gson().fromJson(request.getParams().get("unit"), Unit.class).setUnitState(UnitStates.ALERT);
            case FORTIFY_ACTION -> new Gson().fromJson(request.getParams().get("unit"), Unit.class).setUnitState(UnitStates.FORTIFY);
            case FORTIFY_TILL_HEALED_ACTION -> new Gson().fromJson(request.getParams().get("unit"), Unit.class).setUnitState(UnitStates.FORTIFY_TILL_HEALED);
            case GARRISON_ACTION -> UnitController.garrisonCity(new Gson().fromJson(request.getParams().get("unit"), CombatUnit.class));
            case PILLAGE_ACTION -> UnitController.pillage(WorldController.getSelectedCombatUnit().getCurrentX(), WorldController.getSelectedCombatUnit().getCurrentY());
            case ATTACK_ACTION -> {
                if (WorldController.getSelectedTile() != null)
                    WarController.combatUnitAttacksTile(WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY(), WorldController.getSelectedCombatUnit());
            }
            case SETUP_RANGED_ACTION -> UnitController.setupRangedUnit(new Gson().fromJson(request.getParams().get("unit"), Unit.class), WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY());
            case GET_AVAILABLE_IMPROVEMENTS_FOR_WORKER -> {
                return new Response(QueryResponses.OK, new HashMap<>(){{
                    put("improvements", new Gson().toJson(TileController.getAvailableImprovements(new Gson().fromJson(request.getParams().get("unit"), Worker.class))));
                }});
            }
            case GET_REMOVABLE_FEATURES -> {
                return new Response(QueryResponses.OK, new HashMap<>(){{
                    put("features", new Gson().toJson(TileController.getRemovableFeatures(new Gson().fromJson(request.getParams().get("unit"), Worker.class))));
                }});
            }
            case ROAD_BUILD -> UnitController.buildRoad(new Gson().fromJson(request.getParams().get("unit"), Worker.class));
            case RAILROAD_BUILD -> UnitController.buildRailRoad(new Gson().fromJson(request.getParams().get("unit"), Worker.class));
            case IMPROVEMENT_BUILD -> UnitController.buildImprovement(new Gson().fromJson(request.getParams().get("unit"), Worker.class), new Gson().fromJson(request.getParams().get("improvement"), Improvements.class));
            case ROUTES_REMOVE -> UnitController.removeRouteFromTile(new Gson().fromJson(request.getParams().get("unit"), Worker.class));
            case JUNGLE_REMOVE ->  UnitController.removeJungleFromTile(new Gson().fromJson(request.getParams().get("unit"), Worker.class));
            case FOREST_REMOVE -> UnitController.removeForestFromTile(new Gson().fromJson(request.getParams().get("unit"), Worker.class));
            case MARSH_REMOVE -> UnitController.removeMarshFromTile(new Gson().fromJson(request.getParams().get("unit"), Worker.class));
            case REPAIR_TILE -> UnitController.repairTile(new Gson().fromJson(request.getParams().get("unit"), Worker.class));
            case FOUND_CITY -> UnitController.foundCity(new Gson().fromJson(request.getParams().get("unit"), Settler.class));
            case NEXT_TURN -> {
                if (WorldController.nextTurnImpossible() == null) {
                    WorldController.nextTurn();
                    return new Response(QueryResponses.OK, new HashMap<>(){{
                        put("year", String.valueOf(WorldController.getWorld().getYear()));
                    }});
                } else {
                    return new Response(QueryResponses.IGNORE, new HashMap<>());
                }
            }
        }
        return new Response(QueryResponses.OK, new HashMap<>());
    }

}
