package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

public class HandCalculator {


    public Player calculateWinner(Player[] players) {
        List<Hand> hands = new LinkedList<>();
        for (Player player : players) {
            hands.add(player.getHand());
        }
    }

}
