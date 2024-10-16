package org.example;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Pot {
    private int chips;

    public void clearChips() {
        chips = 0;
    }

    public void add(int chips) {
        this.chips += chips;
    }
}
