package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class HandCalculator {
    private List<Card> combined = new LinkedList<>();
    private List<Card> best_5_cards = new LinkedList<>();
    private Player[] players;
    private Player.HandValue bestRank = Player.HandValue.HIGH_CARD;
    private Player player;




    public HandCalculator(Player[] p) {
        players = p;
    }

    public List<Player> calculateWinner(Board board) {
        List<Player> potentialWinners = new ArrayList<>();
        for (Player player : players) {
            if (!player.isFolded()) {
                combined.addAll(player.getHand().getCards()); // combine hand and board cards
                combined.addAll(board.getCards());
                System.out.println("Player and Board: " + combined);
                sortByValue();
                if (hasRoyalFlush()) {
                    player.setHandValue(Player.HandValue.ROYAL_FLUSH);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.ROYAL_FLUSH;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasStraightFlush()) {
                    player.setHandValue(Player.HandValue.STRAIGHT_FLUSH);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.STRAIGHT_FLUSH;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasFourOfAKind()) {
                    player.setHandValue(Player.HandValue.FOUR_OF_A_KIND);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.FOUR_OF_A_KIND;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasFullHouse()) {
                    player.setHandValue(Player.HandValue.FULL_HOUSE);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.FULL_HOUSE;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasFlush()) {
                    player.setHandValue(Player.HandValue.FLUSH);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.FLUSH;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasStraight(combined)) { // we use hasStraight to calculate a straight flush as well, so we have to do some shenanigans
                    player.setHandValue(Player.HandValue.STRAIGHT);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.STRAIGHT;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasThreeOfAKind()) {
                    player.setHandValue(Player.HandValue.THREE_OF_A_KIND);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.THREE_OF_A_KIND;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasTwoPair()) {
                    player.setHandValue(Player.HandValue.TWO_PAIR);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.TWO_PAIR;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasTwoOfAKind()) {
                    player.setHandValue(Player.HandValue.PAIR);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.PAIR;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else {
                    player.setHandValue(Player.HandValue.HIGH_CARD);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.HIGH_CARD;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                }
            }
            combined.clear();
        }
        System.out.println("Calculating who won...");

        for (Player player : players) {
            if (playerRankIsHigherThan(bestRank, player) && !player.isFolded()){
                System.out.println(player.getName() + player.getHand());
                potentialWinners.add(player);
            } else {
                System.out.println("Players hands are the same, calculating high card.");

            }
        }

        return potentialWinners;
    }

    private Player.HandValue evaluateHand(Hand hand, Board board) {
        return Player.HandValue.HIGH_CARD;
    }

    private boolean playerRankIsHigherThan(Player.HandValue bestRank, Player player) {
        if (bestRank == Player.HandValue.HIGH_CARD && player.getHandValueAsInt() > 0) {
            return true;
        } else if (bestRank == Player.HandValue.PAIR && player.getHandValueAsInt() > 1) {
            return true;
        } else if (bestRank == Player.HandValue.TWO_PAIR && player.getHandValueAsInt() > 2) {
            return true;
        } else if (bestRank == Player.HandValue.THREE_OF_A_KIND && player.getHandValueAsInt() > 3) {
            return true;
        } else if (bestRank == Player.HandValue.STRAIGHT && player.getHandValueAsInt() > 4) {
            return true;
        } else if (bestRank == Player.HandValue.FLUSH && player.getHandValueAsInt() > 5) {
            return true;
        } else if (bestRank == Player.HandValue.FULL_HOUSE && player.getHandValueAsInt() > 6) {
            return true;
        } else if (bestRank == Player.HandValue.FOUR_OF_A_KIND && player.getHandValueAsInt() > 7) {
            return true;
        } else if (bestRank == Player.HandValue.STRAIGHT_FLUSH && player.getHandValueAsInt() > 8) {
            return true;
        } else if (bestRank == Player.HandValue.ROYAL_FLUSH && player.getHandValueAsInt() > 9) {
            return true;
        } else {
            return false;
        }
    }

    private void sortByValue() {
        combined.sort(Comparator.comparingInt(card -> card.getFaceValue().ordinal())); // sorts the cards by face value, lowest to highest
    }

    private List<Card> sortListByValue(List<Card> cards) {
        cards.sort(Comparator.comparingInt(card -> card.getFaceValue().ordinal()));
        return cards;
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
                // add the best 5 cards in the hand for calculating high card
                best_5_cards.add(new Card(suit, Card.FaceValue.Ace));
                best_5_cards.add(new Card(suit, Card.FaceValue.King));
                best_5_cards.add(new Card(suit, Card.FaceValue.Queen));
                best_5_cards.add(new Card(suit, Card.FaceValue.Jack));
                best_5_cards.add(new Card(suit, Card.FaceValue.Ten));
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
        Map<Card.Suit, List<Card>> suitToCards = new HashMap<>();
        for (Card card : combined) {
            suitToCards
                    .computeIfAbsent(card.getSuit(), k -> new ArrayList<>()).add(card);
        }

        for (Card.Suit suit : Card.Suit.values()) {
            List<Card> cardsOfSuit = suitToCards.get(suit);
            if (cardsOfSuit != null && cardsOfSuit.size() >= 5) {
                List<Card> sortedCards = sortListByValue(cardsOfSuit);
                best_5_cards.add(sortedCards.removeFirst());
                best_5_cards.add(sortedCards.removeFirst());
                best_5_cards.add(sortedCards.removeFirst());
                best_5_cards.add(sortedCards.removeFirst());
                best_5_cards.add(sortedCards.removeFirst());
                System.out.println(best_5_cards);
                if (best_5_cards.size() > 5) {
                    System.out.println("best_5 cards is larger than 5, something went very wrong");
                }
                return true;
            }
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
                best_5_cards.add(cards.get(i));
                consecutiveCount++;
                if (consecutiveCount == 5) {
                    System.out.println(best_5_cards);
                    return true;
                }
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
        Map<Card.FaceValue, List<Card>> faceValueToCards = new HashMap<>();

        // Populate the map with face values and their corresponding cards
        for (Card card : combined) {
            faceValueToCards
                    .computeIfAbsent(card.getFaceValue(), k -> new ArrayList<>())
                    .add(card);
        }

        // Check if any face value has the required count
        for (Map.Entry<Card.FaceValue, List<Card>> entry : faceValueToCards.entrySet()) {
            if (entry.getValue().size() == count) {
                // Add the cards with the matching face value to the best_5_cards
                best_5_cards.addAll(entry.getValue());
                return true;
            }
        }
        return false;
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
