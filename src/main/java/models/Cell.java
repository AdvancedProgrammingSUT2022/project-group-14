package models;

import enums.Colors;

public class Cell {
    private Colors color;
    private char ch;

    private boolean revealed = false;

    public Colors getColor() {
        return this.color;
    }

    public String getAnsiEscapeCode() {
        if (revealed)
            return this.color.getLightAnsiEscapeCode();
        else
            return this.color.getAnsiEscapeCode();
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public char getCharacter() {
        return this.ch;
    }

    public void setCharacter(char ch) {
        this.ch = ch;
    }

    public void setRevealed(boolean revealed) {
        this.revealed = revealed;
    }

}
