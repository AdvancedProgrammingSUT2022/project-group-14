package enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Commands {
    // Menu commands
    ENTER_MENU(""),
    EXIT_MENU(""),
    SHOW_MENU(""),
    // LoginMenu commands
    LOGIN(""),
    CREATE_USER(""),
    PASSWORD("--password//s+(?<password>//S+)"),
    USERNAME(""),
    NICK_NAME(""),
    // MainMenu Commands
    LOGOUT(""),
    // Profile Commands
    PROFILE_CHANGE(""),
    PASSWORD_FLAG(""),
    CURRENT(""),
    NEW(""),
    // GamePlay commands
    PLAY_GAME(""),
    PLAYER(""),
    UNIT("$//s*unit//s+"),
    UNIT_MOVETO(""),
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

}
