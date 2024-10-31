package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class HandCalculator {
    private List<Card> combined = new LinkedList<>();
    private Player[] players;


    public HandCalculator(Player[] p) {
        players = p;
    }

    public Player calculateWinner(Board board) {
        for (Player player : players) {
            combined.addAll(player.getHand().getCards()); // combine hand and board cards
            combined.addAll(board.getCards());
            System.out.println("Player and Board: " + combined);
            sortByValue();
            if (hasRoyalFlush()) {
                player.setHandValue(Player.HandValue.ROYAL_FLUSH);
                player.setHighCard(getHighCardInHand(combined));
                System.out.println("Player " + player.getName() + " has a Royal Flush");
            } else if (hasStraightFlush()) {
                player.setHandValue(Player.HandValue.STRAIGHT_FLUSH);
                player.setHighCard(getHighCardInHand(combined));
                System.out.println("Player " + player.getName() + " has a Straight Flush");
            } else if (hasFourOfAKind()) {
                player.setHandValue(Player.HandValue.FOUR_OF_A_KIND);
                player.setHighCard(getHighCardInHand(combined));
                System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
            } else if (hasFullHouse()) {
                player.setHandValue(Player.HandValue.FULL_HOUSE);
                player.setHighCard(getHighCardInHand(combined));
                System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
            } else if (hasFlush()) {
                player.setHandValue(Player.HandValue.FLUSH);
                player.setHighCard(getHighCardInHand(combined));
                System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
            } else if (hasStraight(combined)) { // we use hasStraight to calculate a straight flush as well, so we have to do some shenanigans
                player.setHandValue(Player.HandValue.STRAIGHT);
                player.setHighCard(getHighCardInHand(combined));
                System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
            } else if (hasThreeOfAKind()) {
                player.setHandValue(Player.HandValue.THREE_OF_A_KIND);
                player.setHighCard(getHighCardInHand(combined));
                System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
            } else if (hasTwoPair()) {
                player.setHandValue(Player.HandValue.TWO_PAIR);
                player.setHighCard(getHighCardInHand(combined));
                System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
            }else if (hasTwoOfAKind()) {
                player.setHandValue(Player.HandValue.PAIR);
                player.setHighCard(getHighCardInHand(combined));
                System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
            } else {
                player.setHandValue(Player.HandValue.HIGH_CARD);
                player.setHighCard(combined.getLast());
                System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
            }
            if (combined.getLast() == getHighCardInHand(combined)) {
                System.out.println("You should replace getHighCardInHand");
            }
            combined.clear();
        }
        return null; //No player won?? should never reach here
    }

    private void sortByValue() {
        combined.sort(Comparator.comparingInt(card -> card.getFaceValue().ordinal())); // sorts the cards by face value, lowest to highest
    }

    private boolean hasRoyalFlush() {

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

    private boolean hasStraightFlush() {
        // Group cards by suit
        Map<Card.Suit, List<Card>> suitToCards = new HashMap<>();
        for (Card card : combined) {
            suitToCards
                    .computeIfAbsent(card.getSuit(), k -> new ArrayList<>())
                    .add(card);
        }

        // Check for a straight in each suit
        for (List<Card> suitedCards : suitToCards.values()) {
            // Sort suited cards by face value
            suitedCards.sort(Comparator.comparingInt(card -> card.getFaceValue().ordinal()));

            // Check for a straight within the suited cards
            return hasStraight(suitedCards);
        }
        return false; // No straight flush found
    }

    /*
        hasFlush runs through each suit and counts the number of each suit. If any are over 5, that is a flush
    */
    private boolean hasFlush() {
        for (Card.Suit suit : Card.Suit.values()) {
            long suitCount = combined.stream().filter(card -> card.getSuit() == suit).count();
            if (suitCount >= 5) return true;
        }
        return false;
    }
    /*
        hasStraight gets the ordered hand and starts a count with the value of the cards
     */

    private boolean hasStraight(List<Card> cards) {
        int consecutiveCount = 1;
        for (int i = 1; i < cards.size(); i++) {
            int prevOrdinal = cards.get(i - 1).getFaceValue().ordinal();
            int currOrdinal = cards.get(i).getFaceValue().ordinal();

            if (currOrdinal == prevOrdinal + 1) {
                consecutiveCount++;
                if (consecutiveCount == 5) return true;
            } else if (currOrdinal != prevOrdinal) {
                consecutiveCount = 1;
            }
        }
        return false;
    }
    private boolean hasFourOfAKind() {
        return hasSameValue(4);
    }
    private boolean hasFullHouse() {
        return hasTwoOfAKind() && hasThreeOfAKind();
    }
    private boolean hasThreeOfAKind() {
        return hasSameValue(3);
    }
    private boolean hasTwoPair() {
        return countPairs() == 2;
    }
    private boolean hasTwoOfAKind() {
        return hasSameValue(2);
    }
    private Card getHighCardInHand(List<Card> hand) {

        // Sort by face value in descending order to get the highest card first
        hand.sort((card1, card2) -> card2.getFaceValue().ordinal() - card1.getFaceValue().ordinal());

        return hand.getFirst(); // Return the highest card
    }


    /*
        hasSameValue function gets the number of cards it wants to look for then checks if the set of those cards are in the hand
        example:
        count = 2
        checks if there are a pair of any two cards in the hand.
     */
    private boolean hasSameValue(int count) {
        Map<Card.FaceValue, Integer> faceCount = new HashMap<>();

        for (Card card : combined) {
            faceCount.put(card.getFaceValue(), faceCount.getOrDefault(card.getFaceValue(), 0) + 1);
        }
        return faceCount.containsValue(count);
    }
    private int countPairs() {
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
