package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;
@Getter @Setter
public class Player {
    private int stack;
    private Hand hand;
    enum Position {
        BB, SB
    }
    private Position position;

    public void bet(int amount) {
        stack -= amount;
    }


}
