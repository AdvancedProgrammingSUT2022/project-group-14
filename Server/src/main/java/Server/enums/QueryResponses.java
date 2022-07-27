package Server.enums;

public enum QueryResponses {
    OK,
    USER_NOT_EXIST,
    PASSWORD_INCORRECT,
    USERNAME_EXIST,
    NICKNAME_EXIST,
    IGNORE,
    RESET_COLOR_ADJUST,
    BLOOM_COLOR_ADJUST,
    RESET_GIVEN_TILE_COLOR,
    FOG,
    REVEALED,
    COMBAT_UNIT_SELECTED,
    NONCOMBAT_UNIT_SELECTED,
    UNIT_NOT_SELECTED,
    SET_TECH_VISIBLE,
    SET_TECH_INVISIBLE,
    UPDATE_GIVEN_TILE,
    CHANGE_HEX_INFO_TEXT,
    UPDATE_ALL_HEXES,
    CHANGE_SCENE,
    UPDATE_CHAT,
    UPDATE_INVITATIONS,
    //
    YOU_NOT_ENOUGH_GOLD, YOU_LACK_STRATEGIC_RESOURCE, YOU_LACK_LUXURY_RESOURCE, OTHER_CIVILIZATION_NOT_ENOUGH_GOLD, OTHER_CIVILIZATION_LACK_STRATEGIC_RESOURCE, OTHER_CIVILIZATION_LACK_LUXURY_RESOURCE,
    CHOOSE_CITY_OPTIONS,
    CHOOSE_WAR_OPTIONS,
    INVALID_TOKEN,
    //city controller
    CANT_BUY_THIS_TILE,
    ALREADY_HAVE_TILE,
    NOT_ENOUGH_GOLD,
    NO_CITIZEN_EXISTS,
    SELECT_TILE_IN_CITY,
    UPDATE_DISCUSS_CHAT,
    //
    TECHNOLOGY_WAS_STUDIED,
    TECHNOLOGY_IS_BEING_STUDIED,
    TECHNOLOGY_HAS_NOT_BEEN_STUDIED,
}
