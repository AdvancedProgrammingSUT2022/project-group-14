package models;

import enums.Colors;

public class Cell {
    private Colors color;
    private char ch;

    public Colors getColor() {
        return this.color;
    }

    public void setColor(Colors color) {
        this.color = color;
    }

    public char getCh() {
        return this.ch;
    }

    public void setCharacter(char ch) {
        this.ch = ch;
    }

}
