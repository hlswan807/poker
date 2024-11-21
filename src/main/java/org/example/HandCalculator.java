package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class HandCalculator {
    private HandCalculatorMode mode;
    private List<Card> combined = new LinkedList<>();
    private List<Card> bestFiveCards = new LinkedList<>();
    private List<Card> sets = new LinkedList<>();
    private Player[] players;
    private Card highCard;
    private HashMap<Player, Card> highCards = new HashMap<>();


    public HandCalculator(Player[] p) {
        players = p;
    }

    public List<Player> calculateWinner(Board board) {
        Player.HandValue bestRank = Player.HandValue.HIGH_CARD;
        List<Player> potentialWinners = new ArrayList<>();
        for (Player player : players) {
            if (!player.isFolded()) {
                combined.addAll(player.getHand().getCards()); // combine hand and board cards
                combined.addAll(board.getCards());

                sortByValue();
                if (hasRoyalFlush()) {
                    mode = HandCalculatorMode.FIVE_CARD_HANDS;
                    player.setHandValue(Player.HandValue.ROYAL_FLUSH);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.ROYAL_FLUSH;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasStraightFlush()) {
                    mode = HandCalculatorMode.FIVE_CARD_HANDS;
                    player.setHandValue(Player.HandValue.STRAIGHT_FLUSH);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.STRAIGHT_FLUSH;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasFourOfAKind()) {
                    mode = HandCalculatorMode.SETS;
                    player.setHandValue(Player.HandValue.FOUR_OF_A_KIND);
                    player.setFourOfAKindFromList(sets);
                    player.setHighCard(getHighestQuads(sets));
                    bestRank = Player.HandValue.FOUR_OF_A_KIND;
                    System.out.println(player.getName() + " has quad " + player.getHighCard());
                    System.out.println(player.getName() + " and Board: " + combined);
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                } else if (hasFullHouse()) {
                    mode = HandCalculatorMode.FIVE_CARD_HANDS;
                    player.setHandValue(Player.HandValue.FULL_HOUSE);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.FULL_HOUSE;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasFlush()) {
                    mode = HandCalculatorMode.FIVE_CARD_HANDS;
                    player.setHandValue(Player.HandValue.FLUSH);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.FLUSH;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasStraight(combined)) {
                    mode = HandCalculatorMode.FIVE_CARD_HANDS;// we use hasStraight to calculate a straight flush as well, so we have to do some shenanigans
                    player.setHandValue(Player.HandValue.STRAIGHT);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.STRAIGHT;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                    System.out.println(player.getName() + " and Board: " + combined);
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                } else if (hasThreeOfAKind()) {
                    mode = HandCalculatorMode.SETS;
                    player.setThreeOfAKindFromList(sets);

                    player.setHandValue(Player.HandValue.THREE_OF_A_KIND);
                    player.setHighCard(getHighestTrips(sets));

                    bestRank = Player.HandValue.THREE_OF_A_KIND;
                    System.out.println(player.getName() + " has a set of " + player.getHighCard());
                    System.out.println(player.getName() + " and Board: " + combined);
                    System.out.println();
                    System.out.println();
                    System.out.println();
                    System.out.println();
                } else if (hasTwoPair()) {
                    mode = HandCalculatorMode.SETS;
                    player.setHandValue(Player.HandValue.TWO_PAIR);
                    player.setHighCard(getHighestPair(sets));
                    player.setKicker(getKicker(combined));
                    bestRank = Player.HandValue.TWO_PAIR;
                    System.out.println(player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                } else if (hasTwoOfAKind()) {
                    mode = HandCalculatorMode.SETS;

                    player.setHandValue(Player.HandValue.PAIR);
                    player.setHighCard(getHighestPair(sets));
                    player.setKicker(getKicker(combined));
                    player.setPairFromList(sets);
                    bestRank = Player.HandValue.PAIR;
                    System.out.println(player.getName() + " has a pair of " + player.getHighCard() + " with a kicker of " + player.getKicker());
                } else {
                    mode = HandCalculatorMode.HIGH_CARDS;
                    System.out.println("Mode set to HIGH_CARDS");
                    player.setHandValue(Player.HandValue.HIGH_CARD);
                    player.setHighCard(getHighCardInHand(combined));
                    bestRank = Player.HandValue.HIGH_CARD;
                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
                }
            }
            combined.clear();
            sets.clear();
        }
        System.out.println("Calculating who won...");
        Player winner = null;
        switch (mode) {
            case SETS:
                System.out.print("Calculating case SETS");
                int highest = 0;
                int highestSecondPair = 0;
                for (Player player : players) {
                    if (playerRankIsHigherThan(bestRank, player) && !player.isFolded()){
                        //System.out.println(player.getName() + player.getHand());
                        winner = player;
                    } else {
                        if (player.getQuads() != null && player.getQuads().getFirstCard().toInt() > highest) {
                            highest = player.getQuads().getFirstCard().toInt();
                            winner = player;
                        } else if (player.getThreeOfAKind() != null && player.getThreeOfAKind().getFirstCard().toInt() > highest) {
                            highest = player.getThreeOfAKind().getFirstCard().toInt();
                            winner = player;
                        } else if (player.getBestPair() != null && player.getSecondBestPair() != null) {
                            if ((player.getBestPair().getFirstCard().toInt() > highest) || (player.getBestPair().getFirstCard().toInt() == highest && player.getSecondBestPair().getFirstCard().toInt() > highestSecondPair)) {
                                // if the player has two pair, check the first pair, if it is equal check the second pair
                                highest = player.getBestPair().getFirstCard().toInt();
                                highestSecondPair = player.getSecondBestPair().getFirstCard().toInt();
                                winner = player;
                            }
                        } else if (player.getBestPair() != null && player.getBestPair().getFirstCard().toInt() > highest) {
                            highest = player.getBestPair().getFirstCard().toInt();
                            winner = player;
                        }



                    }
                }
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                potentialWinners.add(winner);
                break;
            case FIVE_CARD_HANDS:
                System.out.print("Calculating case FIVE_CARD_HANDS");
                int highestKicker = 2;
                for (Player player : players) {
                    if (playerRankIsHigherThan(bestRank, player) && !player.isFolded()){
                        System.out.println(player.getName() + player.getHand());
                        winner = player;
                    } else {
                        System.out.print(".");
                        if (player.getKicker().toInt() > highestKicker) {
                            highestKicker = player.getKicker().toInt();
                            winner = player;
                        }
                    }
                }
                System.out.println();
                potentialWinners.add(winner);
                break;
            case HIGH_CARDS:
                System.out.print("Calculating case HIGH_CARDS");
                int high_card = 2;
                for (Player player : players) {
                    System.out.print(".");
                    if (player.getHighCard().toInt() > high_card) {
                        high_card = player.getHighCard().toInt();
                        winner = player;
                    }
                }
                System.out.println();
                potentialWinners.add(winner);
                break;
            default:
                break;
        }

        return potentialWinners;
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
                bestFiveCards.add(new Card(suit, Card.FaceValue.Ace));
                bestFiveCards.add(new Card(suit, Card.FaceValue.King));
                bestFiveCards.add(new Card(suit, Card.FaceValue.Queen));
                bestFiveCards.add(new Card(suit, Card.FaceValue.Jack));
                bestFiveCards.add(new Card(suit, Card.FaceValue.Ten));
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
                    .computeIfAbsent(card.getSuit(), _ -> new ArrayList<>())
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
                    .computeIfAbsent(card.getSuit(), _ -> new ArrayList<>()).add(card);
        }

        for (Card.Suit suit : Card.Suit.values()) {
            List<Card> cardsOfSuit = suitToCards.get(suit);
            if (cardsOfSuit != null && cardsOfSuit.size() >= 5) {
                List<Card> sortedCards = sortListByValue(cardsOfSuit);
                bestFiveCards.add(sortedCards.removeFirst());
                bestFiveCards.add(sortedCards.removeFirst());
                bestFiveCards.add(sortedCards.removeFirst());
                bestFiveCards.add(sortedCards.removeFirst());
                bestFiveCards.add(sortedCards.removeFirst());
                System.out.println(bestFiveCards);
                if (bestFiveCards.size() > 5) {
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
                bestFiveCards.add(cards.get(i));
                consecutiveCount++;
                if (consecutiveCount == 5) {
                    highCard = getHighCardInHand(cards);
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
    private Card getHighestTrips(List<Card> hand) {
        return hand.getFirst();
    }
    private Card getHighestQuads(List<Card> hand) {
        return hand.getFirst();
    }
    private Card getHighestPair(List<Card> hand) {
        return hand.getFirst();
    }
    
    private Card getHighCardInHand(List<Card> hand) {

        // Sort by face value in descending order to get the highest card first
        hand.sort((card1, card2) -> card2.getFaceValue().ordinal() - card1.getFaceValue().ordinal());

        return hand.getLast(); // Return the highest card
    }
    private Card getKicker(List<Card> hand) {

        // Sort by face value in descending order to get the highest card first
        hand.sort((card1, card2) -> card2.getFaceValue().ordinal() - card1.getFaceValue().ordinal());
        System.out.println("Getting kicker: " + hand.getFirst());
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
                    .computeIfAbsent(card.getFaceValue(), _ -> new ArrayList<>())
                    .add(card);
        }

        // Check if any face value has the required count
        for (Map.Entry<Card.FaceValue, List<Card>> entry : faceValueToCards.entrySet()) {
            if (entry.getValue().size() == count) {
                // Add the cards with the matching face value to the best_5_cards
                sets.addAll(entry.getValue());
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
