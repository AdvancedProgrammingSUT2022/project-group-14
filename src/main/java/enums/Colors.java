package enums;

public enum Colors {
    RESET("\u001B[0m"),
    OCEAN_BLUE("\u001B[48;2;15;0;255m"),
    BLACK("\u001B[48;2;0;0;0m"),
    BROWN("\u001B[48;2;181;76;7m"),
    GREY("\u001B[48;2;120;120;120m"),
    RED("\u001B[48;2;255;0;0m"),
    SOFT_GREEN("\u001B[48;2;20;255;0m"),
    DARK_Green("\u001B[48;2;7;130;0m"),
    YELLOW("\u001B[48;2;255;251;0m"),
    PURPLE("\u001B[48;2;191;9;255m"),
    PINK("\u001B[48;2;255;0;208m"),
    CYAN("\u001B[48;2;0;255;238m"),
    WHITE("\u001B[48;2;255;255;255m");

    private final String ansiEscapeCode;

    Colors(String ansiEscapeCode) {
        this.ansiEscapeCode = ansiEscapeCode;
    }

    public Colors getColorByName(String input) {
        for (var color : Colors.values())
            if (color.getAnsiEscapeCode().equals(input))
                return color;
        return null;
    }

    public Colors getLightColor(Colors color) {
        return getColorByName("light " + color.getAnsiEscapeCode());
    }

    public String getAnsiEscapeCode() {
        return this.ansiEscapeCode;
    }
}
