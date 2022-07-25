package Client.enums;

public enum QueryRequests {
    //LOGIN PAGE
    LOGIN_USER,
    CREATE_USER,
    GET_USER_BY_USERNAME,
    GET_USER_BY_NICKNAME,
    ADD_USER,
    SET_LOGGED_IN_USER,
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
    GET_LOGGED_IN_USER,
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
    GET_INFO_POPUP,
    GET_UNIT_GROUP,
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
    //game page
    DECLARE_WAR

}
