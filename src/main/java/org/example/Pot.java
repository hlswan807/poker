package org.example;

public class Pot {
    private int chips;

    public Pot() {
        chips = 0;
    }

    public int getChips() {
        return chips;
    }

    public void setChips(int c) {
        chips = c;
    }

    public void clearChips() {
        chips = 0;
    }
}
