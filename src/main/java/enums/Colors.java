package enums;

public enum Colors {
    RESET("\u001B[0m"),
    BLACK("\u001B[40m"),
    RED("\u001B[41m"),
    GREEN("\u001B[42m"),
    YELLOW("\u001B[43m"),
    PURPLE("\u001B[44m"),
    PINK("\u001B[45m"),
    CYAN("\u001B[46m"),
    WHITE("\u001B[47m"),

    BBLACK("\u001B[100m"),
    BRED("\u001B[101m"),
    BGREEN("\u001B[102m"),
    BYELLOW("\u001B[103m"),
    BBLUE("\u001B[104m"),
    BPURPLE("\u001B[105m"),
    BCYAN("\u001B[106m"),
    BWHITE("\u001B[107m");

    private String ansiEscapeCode;

    Colors(String ansiEscapeCode) {
        this.ansiEscapeCode = ansiEscapeCode;
    }

    public String getAnsiEscapeCode() {
        return this.ansiEscapeCode;
    }
}
