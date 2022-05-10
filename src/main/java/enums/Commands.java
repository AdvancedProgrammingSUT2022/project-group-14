package enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    // Menu commands
    ENTER_MENU("menu enter (?<menuName>login menu|main menu|game menu|profile menu)"),
    EXIT_MENU("menu exit"),
    SHOW_MENU("menu show-current"),
    // LoginMenu commands
    LOGIN("login user "),
    CREATE_USER("create user "),
    PASSWORD("(\\-\\-password|\\-p) (?<password>\\S+)"),
    USERNAME("(\\-\\-username|\\-u) (?<username>\\S+)"),
    NICKNAME("(\\-\\-nickname|\\-n) (?<nickname>\\S+)"),
    // MainMenu Commands
    LOGOUT("logout user"),
    START_GAME("play game "),
    PLAYER_USERNAME("(\\-\\-player|\\-pl)(?<playerNumber>\\d+) (?<username>\\S+)"),
    // Profile Commands
    PROFILE_CHANGE("change profile "),
    PASSWORD_FLAG("(\\-\\-password|\\-p) "),
    CURRENT("(\\-\\-current|\\-c) (?<currentPassword>\\S+)"),
    NEW("(\\-\\-new|\\-ne) (?<newPassword>\\S+)"),
    // GamePlay commands
    SELECT_UNIT("select unit (?<militaryStatus>combat|nonCombat) (?<x>\\d+) (?<y>\\d+)"),
    SELECT_CITY_BY_NAME("select city (?<name>\\S+)"),
    SELECT_CITY_BY_POSITION("select city (?<x>\\d+) (?<y>\\d+)"),
    SHOW_INFO("show info of (?<field>research|units|cities|diplomacy|victory|demographics|notifications|military|economic|diplomatic|deals)"),
    UNIT_MOVE_TO("unit move to (?<x>\\d+) (?<y>\\d+)"),
    UNIT_SLEEP("unit sleep"),
    UNIT_ALERT("unit alert"),
    UNIT_FORTIFY("unit fortify"),
    UNIT_FORTIFY_HEAL("unit fortify heal"),
    UNIT_GARRISON("unit garrison"),
    UNIT_SETUP_RANGED("uit setup ranged"),
    UNIT_ATTACK("unit attack (?<x>\\d+) (?<y>\\d+)"),
    UNIT_FOUND_CITY("unit found city"),
    UNIT_CANCEL_MISSION("unit cancel mission"),
    UNIT_WAKE("unit wake"),
    UNIT_DELETE("unit delete"),
    UNIT_BUILD("unit build (?<progress>road|railroad|farm|mine|tradingPost|lumberMill|pasture|camp|plantation|quarry)"),
    UNIT_REMOVE("unit remove (?<foundation>jungle|forest|marsh|route)"),
    UNIT_REPAIR("unit repair"),
    MAP_SHOW_BY_POSITION("show map (?<x>\\d+) (?<y>\\d+)"),
    MAP_SHOW_BY_NAME("show map (?<name>\\S+)"),
    MAP_MOVE("move map (?<direction>right|left|up|down) (?<movementAmount>\\d+)"),
    NEXT_TURN("next turn"),
    END_GAME("end game"),
    LOCK_CITIZEN("lock citizen (?<id>\\d+) to (?<x>\\d+) (?<y>\\d+)"),
    UNLOCK_CITIZEN("unlock citizen (?<id>\\d+)"),
    CITY_PRODUCE("city produce (?<type>unit|building) (?<productionName>\\S+) with (?<payment>gold|production)"),
    START_RESEARCH("start research (?<technology>\\S+)"),
    CANCEL_CURRENT_RESEARCH("cancel current research"),
    BUY_TILE("buy tile (?<x>\\d+) (<?y>\\d+)"),
    UPGRADE_UNIT("upgrade unit to (?<unitName>\\S+)"),
    SHOW_CITY_BANNER("show city banner"),
    SHOW_UNEMPLOYED_CITIZENS("show unemployed citizens"),
    SHOW_EMPLOYED_CITIZENS("show employed citizens"),
    //cheat codes
    INCREASE_GOODS("increase -(?<goods>gold|food|production|happiness) (?<amount>\\d+)"),
    INCREASE_MP("increase -movementPoints (?<amount>\\d+)"),
    INCREASE_TURN("increase -turn (?<amount>\\d+)"),
    BUY_TILE_FREE("buy tile for free (?<x>\\d+) (?<y>\\d+)"),
    INCREASE_RANGE("increase range of units by (?<amount>\\d+)"),
    GET_ALL_TECHS("get all technologies"),
    SEE_WHOLE_MAP("see whole map");



    private final String regex;

    private Commands(String regex) {
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Commands commands) {
        Pattern pattern = Pattern.compile(commands.regex);
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches())
            return matcher;
        return null;
    }

    public static boolean startsWith(String input, Commands commands){
        return input.startsWith(commands.regex);
    }

    public static Matcher matcherFindsRegex(String input, Commands commands){
        Matcher matcher = Pattern.compile(commands.regex).matcher(input);

        if (matcher.find())
            return matcher;
        return null;
    }

}
