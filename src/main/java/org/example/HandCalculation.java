package org.example;

import java.util.Comparator;
import java.util.List;

public class HandCalculation {
    public static boolean playingTheBoard(Player player, List<Card> board) {
        List<Card> sortedBoard = sortListByValue(board);
        for (Card card : player.getHand().getCards()) {
            if (card.toInt() < sortedBoard.getFirst().toInt() || card.toInt() < sortedBoard.getFirst().toInt()) {

            }
        }
        return true;
    }

    public static List<Card> sortListByValue(List<Card> cards) {
        cards.sort(Comparator.comparingInt(card -> card.getFaceValue().ordinal()));
        return cards;
    }
}
