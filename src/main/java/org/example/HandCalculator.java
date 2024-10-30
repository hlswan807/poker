package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;


public class HandCalculator {

    public static Player calculateWinner(Player[] players, Board board) {
        for (Player player : players) {
            List<Card> hand = player.getHand().getCards();
            if (board.canContainRoyalFlush(hand)) {
                return player; // Return the player with a royal flush
            }
        }
        return null; // No player has a royal flush
    }
}
