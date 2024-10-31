package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

@Setter
@Getter
public class HandCalculator {
    private static List<Card> combined = new LinkedList<>();

    public static Player calculateWinner(Player[] players, Board board) {
        for (Player player : players) {
            combined.addAll(player.getHand().getCards()); // combine hand and board cards
            combined.addAll(board.getCards());
            sortByValue();
            if (isRoyalFlush()) {
                return player;
            }
        }
        return null; //No player won?? should never reach here
    }

    private static void sortByValue() {
        combined.sort(Comparator.comparingInt(card -> card.getFaceValue().ordinal())); // sorts the cards by face value, lowest to highest
    }

    private static boolean isRoyalFlush() {

        // Check for each suit if it contains the royal flush
        for (Card.Suit suit : Card.Suit.values()) {
            boolean hasAce = combined.contains(new Card(suit, Card.FaceValue.Ace));
            boolean hasKing = combined.contains(new Card(suit, Card.FaceValue.King));
            boolean hasQueen = combined.contains(new Card(suit, Card.FaceValue.Queen));
            boolean hasJack = combined.contains(new Card(suit, Card.FaceValue.Jack));
            boolean hasTen = combined.contains(new Card(suit, Card.FaceValue.Ten));

            if (hasAce && hasKing && hasQueen && hasJack && hasTen) {
                return true; // Found a royal flush
            }
        }
        return false; // No royal flush found
    }

    private static Player.HandValue isStraightFlush() {
        if (hasFlush() == Player.HandValue.FLUSH && hasStraight() == Player.HandValue.STRAIGHT) {
            return Player.HandValue.STRAIGHT_FLUSH;
        }
        return null;
    }



    private static Player.HandValue hasFlush() {
        return null;
    }

    private static Player.HandValue hasStraight() {
        return null;
    }
}
