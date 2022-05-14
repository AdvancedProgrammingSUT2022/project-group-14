package enums;

public enum Colors {
    RESET("reset", "\u001b[0m", ""),
    OCEAN_BLUE("ocean_blue", "\u001b[48;2;15;0;255m", "\u001B[48;2;99;119;255m"),
    BLACK("black", "\u001b[48;2;0;0;0m", "\u001B[48;2;186;186;186m"),
    BROWN("brown", "\u001b[48;2;181;76;7m", "\u001B[48;2;181;150;117m"),
    GREY("grey", "\u001b[48;2;120;120;120m", "\u001B[48;2;186;186;186m"),
    RED("red", "\u001b[48;2;255;0;0m", "\u001b[48;2;255;130;130m"),
    SOFT_GREEN("soft_green", "\u001b[48;2;20;255;0m", "\u001B[48;2;145;255;123m"),
    DARK_GREEN("dark_green", "\u001b[48;2;7;130;0m", "\u001B[48;2;90;130;79m"),
    YELLOW("yellow", "\u001b[48;2;255;251;0m", "\u001b[48;2;255;251;119m"),

    PURPLE("purple", "\u001b[48;2;196;6;255m", "\u001b[48;2;213;119;255m"),
    PINK("pink", "\u001b[48;2;255;0;208m", "\u001b[48;2;255;120;236m"),
    CYAN("cyan", "\u001b[48;2;0;255;238m", "\u001b[48;2;146;255;245m"),
    WHITE("white", "\u001b[48;2;255;255;255m", "\u001b[48;2;255;255;255m");

    private final String name;
    private final String ansiEscapeCode;
    private final String lightAnsiEscapeCode;

    Colors(String name, String ansiEscapeCode, String lightAnsiEscapeCode) {
        this.name = name;
        this.ansiEscapeCode = ansiEscapeCode;
        this.lightAnsiEscapeCode = lightAnsiEscapeCode;
    }


    public String getAnsiEscapeCode() {
        return this.ansiEscapeCode;
    }

    public String getName() {
        return name;
    }

    public String getLightAnsiEscapeCode() {
        return lightAnsiEscapeCode;
    }
}
