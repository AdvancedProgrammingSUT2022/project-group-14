package Server.models.network;

import Server.controllers.*;
import Server.enums.Improvements;
import Server.enums.QueryResponses;
import Server.enums.Technologies;
import Server.enums.units.UnitStates;
import Server.enums.units.UnitTypes;
import Server.models.City;
import Server.models.Civilization;
import Server.models.User;
import Server.models.World;
import Server.models.chats.Chat;
import Server.models.chats.Message;
import Server.models.tiles.Coordination;
import Server.models.tiles.Tile;
import Server.models.units.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ServerSocketHandler extends Thread {
    private final Socket socket;
    private final BufferedWriter bufferedWriter;
    private final BufferedReader bufferedReader;

    public ServerSocketHandler(Socket socket) throws IOException {
        this.socket = socket;
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

    public Response handleRequest(Request request) throws IOException {
        System.out.println(request.getQueryRequest() + " : " + request.getParams().toString());
        Civilization currentCivilization;
        if (WorldController.getWorld() != null) {
            currentCivilization = WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName());
        } else {
            currentCivilization = null;
        }
        switch (request.getQueryRequest()) {
            case LOGIN_USER -> {
                if (UserController.getUserByUsername(request.getParams().get("username")) == null) {
                    return new Response(QueryResponses.USER_NOT_EXIST, new HashMap<>());
                } else if (!Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).getPassword().equals(request.getParams().get("password"))) {
                    return new Response(QueryResponses.PASSWORD_INCORRECT, new HashMap<>());
                } else {
                    UserController.addLoggedInUser(Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))));
                    return new Response(QueryResponses.OK, new HashMap<>() {{
                        put("user", new Gson().toJson(UserController.getUserByUsername(request.getParams().get("username"))));
                    }});
                }
            }
            case LOGOUT_USER ->
                    UserController.removeLoggedOutUser(UserController.getUserByUsername(request.getParams().get("username")));
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
            case GET_LOGGED_IN_USERS -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("loggedInUsers", new Gson().toJson(UserController.getLoggedInUsers()));
                }});
            }
            case SET_CURRENT_TECHNOLOGY ->
                    WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).setCurrentTechnology(Technologies.valueOf(request.getParams().get("technologyName")));
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
            case SET_STRATEGIC_RESOURCE -> {
                assert currentCivilization != null;
                currentCivilization.getStrategicResources().put(request.getParams().get("name"), currentCivilization.getStrategicResources().get(request.getParams().get("name")) + 1);
            }
            case SET_LUXURY_RESOURCE -> {
                assert currentCivilization != null;
                currentCivilization.getStrategicResources().put(request.getParams().get("name"), currentCivilization.getStrategicResources().get(request.getParams().get("name")) + 1);
                currentCivilization.setHappiness(currentCivilization.getHappiness() + 4);
            }
            case CHEAT_COMMAND -> GameCommandsValidation.checkCommands(request.getParams().get("command"));
            case HEX_MOUSE_CLICKED -> {
                if (WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName())
                        .getVisionStatesOfMap()[Integer.parseInt(request.getParams().get("x"))][Integer.parseInt(request.getParams().get("y"))] > 0) {
                    Coordination coordination = new Coordination(Integer.parseInt(request.getParams().get("x")), Integer.parseInt(request.getParams().get("y")));
                    if (WorldController.getSelectedTile() != null
                            && WorldController.getSelectedTile().equals(MapController.getTileByCoordinates(coordination))) {
                        WorldController.setSelectedTile(null);
                        return new Response(QueryResponses.RESET_COLOR_ADJUST, new HashMap<>());
                    } else {
                        if (WorldController.getSelectedTile() != null) {
                            return new Response(QueryResponses.RESET_GIVEN_TILE_COLOR, new HashMap<>() {{
                                put("x", String.valueOf(WorldController.getSelectedTile().getX()));
                                put("y", String.valueOf(WorldController.getSelectedTile().getY()));
                            }});
                        }
                        WorldController.setSelectedTile(MapController.getTileByCoordinates(coordination));
                        return new Response(QueryResponses.BLOOM_COLOR_ADJUST, new HashMap<>());
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
                Unit unit;
                if (request.getParams().get("type").equals("combat")) {
                    unit = MapController.getTileByCoordinates(Integer.parseInt(request.getParams().get("x")), Integer.parseInt(request.getParams().get("y"))).getCombatUnit();
                } else {
                    unit = MapController.getTileByCoordinates(Integer.parseInt(request.getParams().get("x")), Integer.parseInt(request.getParams().get("y"))).getNonCombatUnit();
                }
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
            case HEX_IS_TERRITORY -> {
                for (City city : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCities()) {
                    for (Tile tile : city.getTerritory()) {
                        if (MapController.getTileByCoordinates(new Coordination(Integer.parseInt(request.getParams().get("x")), Integer.parseInt(request.getParams().get("y")))).equals(tile) && tile.getCity() == null)
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
                    return new Response(QueryResponses.REVEALED, WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getRevealedTiles()[Integer.parseInt(request.getParams().get("x"))][Integer.parseInt(request.getParams().get("y"))]);
                }
            }
            case NEW_WORLD ->
                    WorldController.newWorld(new Gson().fromJson(request.getParams().get("people"), new TypeToken<List<String>>() {
                    }.getType()), Integer.parseInt(request.getParams().get("width")), Integer.parseInt(request.getParams().get("height")));
            case CIV_GOLD -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    assert currentCivilization != null;
                    put("gold", String.valueOf(currentCivilization.getGold()));
                }});
            }
            case CIV_HAPPINESS -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    assert currentCivilization != null;
                    put("happiness", String.valueOf(currentCivilization.getHappiness()));
                }});
            }
            case CIV_SCIENCE -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    assert currentCivilization != null;
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
                assert currentCivilization != null;
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
                    Unit unit = WorldController.getSelectedCombatUnit() != null ? WorldController.getSelectedCombatUnit() : WorldController.getSelectedNonCombatUnit();
                    UnitController.setUnitDestinationCoordinates(unit, WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY());
                    MoveController.moveUnitToDestination(unit);
                }
            }
            case DELETE_ACTION ->
                    UnitController.delete(WorldController.getSelectedCombatUnit() != null ? WorldController.getSelectedCombatUnit() : WorldController.getSelectedNonCombatUnit());
            case SLEEP_ACTION ->
                    (WorldController.getSelectedCombatUnit() != null ? WorldController.getSelectedCombatUnit() : WorldController.getSelectedNonCombatUnit()).setUnitState(UnitStates.SLEEP);
            case WAKE_ACTION ->
                    (WorldController.getSelectedCombatUnit() != null ? WorldController.getSelectedCombatUnit() : WorldController.getSelectedNonCombatUnit()).setUnitState(UnitStates.WAKE);
            case ALERT_ACTION ->
                    (WorldController.getSelectedCombatUnit() != null ? WorldController.getSelectedCombatUnit() : WorldController.getSelectedNonCombatUnit()).setUnitState(UnitStates.ALERT);
            case FORTIFY_ACTION ->
                    (WorldController.getSelectedCombatUnit() != null ? WorldController.getSelectedCombatUnit() : WorldController.getSelectedNonCombatUnit()).setUnitState(UnitStates.FORTIFY);
            case FORTIFY_TILL_HEALED_ACTION ->
                    (WorldController.getSelectedCombatUnit() != null ? WorldController.getSelectedCombatUnit() : WorldController.getSelectedNonCombatUnit()).setUnitState(UnitStates.FORTIFY_TILL_HEALED);
            case GARRISON_ACTION -> UnitController.garrisonCity(WorldController.getSelectedCombatUnit());
            case PILLAGE_ACTION ->
                    UnitController.pillage(WorldController.getSelectedCombatUnit().getCurrentX(), WorldController.getSelectedCombatUnit().getCurrentY());
            case ATTACK_ACTION -> {
                if (WorldController.getSelectedTile() != null)
                    WarController.combatUnitAttacksTile(WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY(), WorldController.getSelectedCombatUnit());
            }
            case SETUP_RANGED_ACTION ->
                    UnitController.setupRangedUnit(WorldController.getSelectedNonCombatUnit(), WorldController.getSelectedTile().getX(), WorldController.getSelectedTile().getY());
            case GET_AVAILABLE_IMPROVEMENTS_FOR_WORKER -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("improvements", new Gson().toJson(TileController.getAvailableImprovements((Worker) WorldController.getSelectedNonCombatUnit())));
                }});
            }
            case GET_REMOVABLE_FEATURES -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("features", new Gson().toJson(TileController.getRemovableFeatures((Worker) WorldController.getSelectedNonCombatUnit())));
                }});
            }
            case ROAD_BUILD -> UnitController.buildRoad((Worker) WorldController.getSelectedNonCombatUnit());
            case RAILROAD_BUILD -> UnitController.buildRailRoad((Worker) WorldController.getSelectedNonCombatUnit());
            case IMPROVEMENT_BUILD ->
                    UnitController.buildImprovement((Worker) WorldController.getSelectedNonCombatUnit(), new Gson().fromJson(request.getParams().get("improvement"), Improvements.class));
            case ROUTES_REMOVE ->
                    UnitController.removeRouteFromTile((Worker) WorldController.getSelectedNonCombatUnit());
            case JUNGLE_REMOVE ->
                    UnitController.removeJungleFromTile((Worker) WorldController.getSelectedNonCombatUnit());
            case FOREST_REMOVE ->
                    UnitController.removeForestFromTile((Worker) WorldController.getSelectedNonCombatUnit());
            case MARSH_REMOVE ->
                    UnitController.removeMarshFromTile((Worker) WorldController.getSelectedNonCombatUnit());
            case REPAIR_TILE -> UnitController.repairTile((Worker) WorldController.getSelectedNonCombatUnit());
            case FOUND_CITY -> UnitController.foundCity((Settler) WorldController.getSelectedNonCombatUnit());
            case NEXT_TURN -> {
                if (WorldController.nextTurnImpossible() == null) {
                    WorldController.nextTurn();
                    return new Response(QueryResponses.OK, new HashMap<>() {{
                        put("year", String.valueOf(WorldController.getWorld().getYear()));
                    }});
                } else {
                    return new Response(QueryResponses.IGNORE, new HashMap<>());
                }
            }
            case UPDATE_HEX -> {
                return new Response(QueryResponses.OK, MapController.getTileByCoordinates(Integer.parseInt(request.getParams().get("x")), Integer.parseInt(request.getParams().get("y"))));
            }
            case GET_TILE_INFO -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("info", MapController.getTileByCoordinates(Integer.parseInt(request.getParams().get("x")), Integer.parseInt(request.getParams().get("y"))).getInfo());
                }});
            }
            case GET_CITIES_INFO -> {
                ArrayList<String> infos = new ArrayList<>();
                for (City city : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCities()) {
                    infos.add(city.getInfo());
                }
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("info", new Gson().toJson(infos));
                }});
            }
            case GET_CITIES_NAME -> {
                ArrayList<String> names = new ArrayList<>();
                for (City city : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getCities()) {
                    names.add(city.getName());
                }
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("citiesName", new Gson().toJson(names));
                }});
            }
            case GET_MILITARY_INFO -> {
                ArrayList<String> cityCombatInfos = new ArrayList<>();
                ArrayList<String> unitCombatInfos = new ArrayList<>();
                assert currentCivilization != null;
                for (City city : currentCivilization.getCities()) {
                    cityCombatInfos.add(city.getCombatInfo());
                }
                for (Unit unit : currentCivilization.getAllUnits()) {
                    if (unit instanceof CombatUnit) {
                        unitCombatInfos.add(((CombatUnit) unit).getCombatInfo());
                    }
                }
                int totalValueOfCombatUnits = 0, totalCombatUnits = 0;
                for (Unit unit : currentCivilization.getAllUnits()) {
                    if (unit instanceof CombatUnit) {
                        totalValueOfCombatUnits += UnitTypes.valueOf(unit.getName().toUpperCase(Locale.ROOT)).getCost();
                        totalCombatUnits++;
                    }
                }
                int finalTotalValueOfCombatUnits = totalValueOfCombatUnits;
                int finalTotalCombatUnits = totalCombatUnits;
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("cityCombatInfo", new Gson().toJson(cityCombatInfos));
                    put("unitCombatInfo", new Gson().toJson(unitCombatInfos));
                    put("citySize", String.valueOf(currentCivilization.getCities().size()));
                    put("totalValueOfCombatUnits", String.valueOf(finalTotalValueOfCombatUnits));
                    put("totalCombatUnits", String.valueOf(finalTotalCombatUnits));
                }});
            }
            case GET_NOTIFICATIONS -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    assert currentCivilization != null;
                    put("notifications", new Gson().toJson(currentCivilization.getNotifications()));
                }});
            }
            case GET_CIV_INFO -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    assert currentCivilization != null;
                    put("info", currentCivilization.getInfo());
                }});
            }
            case GET_UNITS_INFO -> {
                ArrayList<String> unitsInfo = new ArrayList<>();
                for (Unit unit : WorldController.getWorld().getCivilizationByName(WorldController.getWorld().getCurrentCivilizationName()).getAllUnits()) {
                    unitsInfo.add(unit.getInfo());
                }
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("unitsInfo", new Gson().toJson(unitsInfo));
                }});
            }
            case VALID_CHAT_NAME -> {
                ArrayList<String> usernames = new Gson().fromJson(request.getParams().get("usernames"), new TypeToken<List<String>>() {
                }.getType());
                for (String username : usernames) {
                    if (Objects.requireNonNull(UserController.getUserByUsername(username)).getChats().containsKey(request.getParams().get("chatName"))) {
                        return new Response(QueryResponses.IGNORE, new HashMap<>());
                    }
                }
            }
            case ADD_LISTENER ->
                    Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).setUpdateSocket(this.socket);
            case ADD_MESSAGE -> {
                Chat chat = new Gson().fromJson(request.getParams().get("chat"), Chat.class);
                Message message = new Gson().fromJson(request.getParams().get("message"), Message.class);
                for (String username : chat.getUsernames()) {
                    Objects.requireNonNull(UserController.getUserByUsername(username)).getChats().get(chat.getName()).addMessage(message);
                    if (UserController.getLoggedInUsers().contains(UserController.getUserByUsername(username))) {
                        ServerUpdateController.sendUpdate(username, new Response(QueryResponses.UPDATE_CHAT, new HashMap<>(){{
                            put("user", new Gson().toJson(UserController.getUserByUsername(username)));
                        }}));
                    }
                }
            }
            case GET_USER_AVATAR -> {
                return new Response(QueryResponses.OK, new HashMap<>() {{
                    put("address", Objects.requireNonNull(UserController.getUserByUsername(request.getParams().get("username"))).getAvatarFileAddress());
                }});
            }
            case ADD_CHAT -> {
                ArrayList<String> usernames = new Gson().fromJson(request.getParams().get("usernames"), new TypeToken<List<String>>() {
                }.getType());
                for (String username : usernames) {
                    Objects.requireNonNull(UserController.getUserByUsername(username)).addChats(new Chat(usernames, request.getParams().get("chatName")));
                    if (UserController.getLoggedInUsers().contains(UserController.getUserByUsername(username))) {
                        ServerUpdateController.sendUpdate(username, new Response(QueryResponses.UPDATE_CHAT, new HashMap<>(){{
                            put("user", new Gson().toJson(UserController.getUserByUsername(username)));
                        }}));
                    }
                }
            }
        }
        return new Response(QueryResponses.OK, new HashMap<>());
    }

}
