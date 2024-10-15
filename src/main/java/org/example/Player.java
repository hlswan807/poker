package org.example;

import lombok.*;

import javax.annotation.processing.Generated;

public class Player {
    @Getter @Setter private int stack;
    @Setter @Getter private Hand hand;
    private enum Position {
        BB, SB
    }
    private Position position;



}
