package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

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

    private static boolean isStraightFlush() {
        return hasFlush() && hasStraight();
    }
    /*
        hasFlush runs through each suit and counts the number of each suit. If any are over 5, that is a flush
    */
    private static boolean hasFlush() {
        for (Card.Suit suit : Card.Suit.values()) {
            long suitCount = combined.stream().filter(card -> card.getSuit() == suit).count();
            if (suitCount >= 5) return true;
        }
        return false;
    }

    private static boolean hasStraight() {
        return false;
    }
    private static boolean hasFourOfAKind() {
        return hasSameValue(4);
    }
    private static boolean hasFullHouse() {
        return hasTwoOfAKind() && hasThreeOfAKind();
    }
    private static boolean hasThreeOfAKind() {
        return hasSameValue(3);
    }
    private static boolean hasTwoOfAKind() {
        return hasSameValue(2);
    }
    /*
        hasSameValue function gets the number of cards it wants to look for then checks if the set of those cards are in the hand
        example:
        count = 2
        checks if there are a pair of any two cards in the hand.
     */
    private static boolean hasSameValue(int count) {
        Map<Card.FaceValue, Integer> faceCount = new HashMap<>();

        for (Card card : combined) {
            faceCount.put(card.getFaceValue(), faceCount.getOrDefault(card.getFaceValue(), 0) + 1);
        }
        return faceCount.containsValue(count);
    }
    private static int countPairs() {
        Map<Card.FaceValue, Integer> faceCount = new HashMap<>(); //maps the face value as the key and the integer as the value

        for (Card card : combined) {
            faceCount.put(card.getFaceValue(), faceCount.getOrDefault(card.getFaceValue(), 0) + 1);
            //if the face value is in the map it increments the count by one saying there is a pair, if not, 0
        }

        int pairs = 0;
        for (int count : faceCount.values()) { // count the number of pairs based on the number of values in the hash map
            if (count == 2) pairs++;
        }
        return pairs;
    }

}
