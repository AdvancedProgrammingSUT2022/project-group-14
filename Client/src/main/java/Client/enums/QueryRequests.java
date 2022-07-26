package Client.enums;

public enum QueryRequests {
    //LOGIN PAGE
    LOGIN_USER,
    LOGOUT_USER,
    CREATE_USER,
    GET_LOGGED_IN_USERS,
    //PROFILE PAGE
    CHANGE_NICKNAME,
    CHANGE_PASSWORD,
    CHANGE_AVATAR,
    //city panel page
    GET_SELECTED_CITY,
    CAN_PRODUCE_UNIT,
    CAN_PRODUCE_BUILDING,
    GET_MAP_HEIGHT,
    GET_MAP_WIDTH,
    GET_TILE_GOLD_BY_COORDINATES,
    GET_TILE_FOOD_BY_COORDINATES,
    GET_TILE_PRODUCTION_BY_COORDINATES,
    BUY_TILE,
    GET_CURRENT_CIVILIZATION_NAME,
    PRODUCE_UNIT,
    PRODUCE_BUILDING,
    UNLOCK_CITIZEN_FROM_TILE,
    LOCK_CITIZEN_TO_TILE,
    //END GAME PAGE
    SET_DATE_OF_LAST_WIN_OF_USER,
    CHANGE_SCORE_OF_USER,
    //GAME COMMAND VALIDATION
    NEXT_TURN,
    GET_CURRENT_CIVILIZATION,
    GET_TILE_BY_COORDINATES,
    SELECTED_TILE_IS_VALID,
    SET_CHEAT_COORDINATION,
    //GAME PAGE
    GET_AVAILABLE_TECHNOLOGIES,
    GET_SELECTED_COMBAT_UNIT,
    GET_SELECTED_NONCOMBAT_UNIT,
    GET_SELECTED_TILE,
    GET_ACTION_IMAGE,
    PILLAGE,//DOESN'T NEED ANY INPUT. IT WORKS WITH THE SELECTED COMBAT UNIT
    COMBAT_UNIT_ATTACKS_TILE,//THIS ONE NEEDS AN INPUT BECAUSE IN WORLD_CONTROLLER NEXT TURN THIS FUNCTION IS CALLED AND IT USES DIFFERENT INPUTS
    SET_UP_RANGED_UNIT,//I THINK IT DOESN'T NEED INPUTS
    GET_AVAILABLE_IMPROVEMENTS_FOR_WORKER,
    BUILD_ROAD,
    BUILD_RAILROAD,
    BUILD_IMPROVEMENTS,
    GET_REMOVABLE_FEATURES,
    REMOVE_ROUTE_FROM_TILE,
    REMOVE_MARSH_FROM_TILE,
    REMOVE_JUNGLE_FROM_TILE,
    REMOVE_FOREST_FROM_TILE,
    REPAIR_TILE,
    FOUND_CITY,
    NEXT_TURN_IMPOSSIBLE,
    GET_YEAR,
    CONQUER_CITY,
    DESTROY_CITY,
    //INFO PANEL
    SET_SELECTED_CITY,
    //SCOREBOARD
    SORT_USERS,
    GET_USERS,
    //START GAME PAGE
    WORLD_IS_NULL,
    NEW_WORLD,
    //hex
    SET_SELECTED_COMBAT_UNIT,
    SET_SELECTED_NONCOMBAT_UNIT,
    //TECHNOLOGIES
    SET_CURRENT_TECHNOLOGY,
    //TILE
    RESOURCE_IS_AVAILABLE_TO_BE_USED,
    SET_LUXURY_RESOURCE,
    GET_MAP_RANGES,
    SET_STRATEGIC_RESOURCE,
    CHEAT_COMMAND,
    HEX_MOUSE_CLICKED,
    CITY_HEX_MOUSE_CLICKED,
    UNIT_HEX_MOUSE_CLICKED,
    HEX_VISION_UPDATE,
    HEX_IS_TERRITORY,
    CIV_GOLD,
    CIV_HAPPINESS,
    CIV_SCIENCE,
    UNIT_PANEL_UPDATE,
    TECH_PANEL_UPDATE,
    MOVE_ACTION,
    DELETE_ACTION,
    SLEEP_ACTION,
    WAKE_ACTION,
    ALERT_ACTION,
    FORTIFY_ACTION,
    FORTIFY_TILL_HEALED_ACTION,
    GARRISON_ACTION,
    PILLAGE_ACTION,
    ATTACK_ACTION,
    SETUP_RANGED_ACTION,
    ROAD_BUILD,
    RAILROAD_BUILD,
    IMPROVEMENT_BUILD,
    ROUTES_REMOVE,
    JUNGLE_REMOVE,
    FOREST_REMOVE,
    MARSH_REMOVE,
    UPDATE_HEX,
    GET_TILE_INFO,
    GET_CITIES_INFO,
    GET_CITIES_NAME,
    GET_UNITS_INFO,
    GET_MILITARY_INFO,
    GET_NOTIFICATIONS,
    GET_CIV_INFO,
    VALID_CHAT_NAME,
    ADD_MESSAGE,
    GET_USER_AVATAR,
    ADD_CHAT,
    ADD_LISTENER,
    //game page
    DECLARE_WAR,
    SEND_INVITATION,
    ACCEPT_INVITATION,
    DECLINE_INVITATION,
    LEAVE_LOBBY,
    //trade panel
    GET_ALL_CIVILIZATIONS_NAMES,
    ADD_TRADE,
    GET_CIVILIZATION_TRADES,
    ACCEPT_TRADE,

}
