package org.example;

import java.util.LinkedList;
import java.util.List;

public class Board {
    List<Card> cards = new LinkedList<>();

    public void add(Card c) {
        cards.add(c);
    }

    public void clear() {
        cards.clear();
    }

    // Checks if a given list of cards (e.g., player's hand + board) can form a royal flush
    public boolean canContainRoyalFlush(List<Card> hand) {
        // Combine hand and board cards
        List<Card> combined = new LinkedList<>(cards);
        combined.addAll(hand);

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

    public String toString() {
        return "The board is " + cards.toString();
    }
}
