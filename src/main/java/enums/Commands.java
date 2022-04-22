package enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    // Menu commands
    ENTER_MENU("menu enter (?<menuName>login menu|main menu|game menu|profile menu)"),
    EXIT_MENU("menu exit"),
    SHOW_MENU("menu show-current"),
    // LoginMenu commands
    LOGIN("user login "),
    CREATE_USER("user create "),
    PASSWORD("(\\-\\-password|\\-p) (?<password>\\S+)"),
    USERNAME("(\\-\\-username|\\-u) (?<username>\\S+)"),
    NICKNAME("(\\-\\-nickname|\\-n) (?<nickname>\\S+)"),
    // MainMenu Commands
    LOGOUT("user logout"),
    START_GAME("play game "),
    PLAYER_USERNAME("(\\-\\-player|\\-pl)(?<playerNumber>\\d+) (?<username>\\S+)"),
    // Profile Commands
    PROFILE_CHANGE("profile change "),
    PASSWORD_FLAG("(\\-\\-password|\\-p) "),
    CURRENT("(\\-\\-current|\\-c) (?<currentPassword>\\S+)"),
    NEW("(\\-\\-new|\\-ne) (?<newPassword>\\S+)"),
    // GamePlay commands
    PLAY_GAME(""),
    PLAYER(""),
    UNIT("$//s*unit//s+"),
    UNIT_MOVE_TO(""),
    UNIT_SLEEP(""),
    UNIT_ALERT(""),
    UNIT_FORTIFY(""),
    UNIT_FORTIFY_HEAL(""),
    UNIT_GARRISON(""),
    UNIT_SETUP_RANGED(""),
    UNIT_ATTACK(""),
    UNIT_FOUND_CITY(""),
    UNIT_CANCEL_MISSION(""),
    UNIT_WAKE(""),
    UNIT_DELETE(""),
    UNIT_BUILD(""),
    UNIT_REMOVE(""),
    UNIT_REPAIR(""),
    MAP_SHOW(""),
    MAP_MOVE(""),
    DARD("");

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
        if (input.startsWith(commands.regex))
            return true;
        return false;
    }

    public static Matcher matcherFindsRegex(String input, Commands commands){
        Matcher matcher = Pattern.compile(commands.regex).matcher(input);

        if (matcher.find())
            return matcher;
        return null;
    }

}
