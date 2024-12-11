package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
public class HandCalculator {
    private HandCalculatorMode mode = HandCalculatorMode.HIGH_CARDS;
    private List<Card> combined = new LinkedList<>();
    private List<Card> bestFiveCards = new LinkedList<>();
    private List<Card> sets = new LinkedList<>();
    private Player[] players;
    private Card highCard;
    private HashMap<Player, Card> highCards = new HashMap<>();
    private List<Player> bestPlayers = new ArrayList<>();
    private int bestRank = 0;


    public HandCalculator(Player[] p) {
        players = p;
    }

    public List<Player> calculateWinner(Board board) {
        List<Player> potentialWinners = new ArrayList<>();
        List<Player> winners = new ArrayList<>();

        // assign each player a hand value
        // check for highest hand value
        // highest hand value wins
        // if two hand values are the same then
        // check what type of hand values are the same then do something specific for those two hand values.
        // ex, two players with pairs would have the pairs compared.
        for (Player player : players) {
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            if (!player.isFolded()) {
                combined.addAll(player.getHand().getCards());
                combined.addAll(board.getCards());
                System.out.println("Added hand and board cards to combined list \n" + combined);
                System.out.println("|H--------A---------N---------D|F--------------L--------------O------------P|T-----U-----R-----N|R---I---V---E---R|");
                sortByValue();
                System.out.println("Sorted hand and board cards \n" + combined);
                System.out.println("---------------------------------------------------");
            } else {
                continue;
            }
            if (hasRoyalFlush()) {
                player.setHandValue(Player.HandValue.ROYAL_FLUSH);
                winners.add(player); //todo redo potential winners to something else
                //this player outright wins
                combined.clear();
                sets.clear();
                bestFiveCards.clear();
                break;
            } else if (hasStraightFlush()) {
                System.out.println("Player " + player.getName() + " has a straight flush");
                System.out.println("--------------------------------------------------- \n");
                System.out.println("Setting player hand value to STRAIGHT_FLUSH and setting handValueAsInt to 9");
                System.out.println("---------------------------------------------------");
                player.setHandValueAsInt(9);
                player.setHandValue(Player.HandValue.STRAIGHT_FLUSH);
                getBestRank(player);
            } else if (hasFourOfAKind()) {
                System.out.println("Player " + player.getName() + " has a four of a kind");
                System.out.println("--------------------------------------------------- \n");
                System.out.println("Setting player hand value to FOUR_OF_A_KIND and setting handValueAsInt to 8");
                player.setHandValueAsInt(8);
                player.setHandValue(Player.HandValue.FOUR_OF_A_KIND);
                getBestRank(player);
            } else if (hasFullHouse()) {
                System.out.println("Player " + player.getName() + " has a full house");
                System.out.println("--------------------------------------------------- \n");
                System.out.println("Setting player hand value to FULL_HOUSE and setting handValueAsInt to 7");
                player.setHandValueAsInt(7);
                player.setHandValue(Player.HandValue.FULL_HOUSE);
                getBestRank(player);
            } else if (hasFlush()) {
                System.out.println("Player " + player.getName() + " has a flush");
                System.out.println("--------------------------------------------------- \n");
                System.out.println("Setting player hand value to FLUSH and setting handValueAsInt to 6");
                player.setHandValueAsInt(6);
                player.setHandValue(Player.HandValue.FLUSH);
                getBestRank(player);
            } else if (hasStraight(combined)) {
                System.out.println("Player " + player.getName() + " has a straight");
                System.out.println("--------------------------------------------------- \n");
                System.out.println("Setting player hand value to STRAIGHT and setting handValueAsInt to 5");
                player.setHandValueAsInt(5);
                player.setHandValue(Player.HandValue.STRAIGHT);
                getBestRank(player);
            } else if (hasThreeOfAKind()) {
                System.out.println("Player " + player.getName() + " has a three of a kind");
                System.out.println("--------------------------------------------------- \n");
                System.out.println("Setting player hand value to THREE_OF_A_KIND and setting handValueAsInt to 4");
                player.setHandValueAsInt(4);
                player.setHandValue(Player.HandValue.THREE_OF_A_KIND);
                getBestRank(player);
            } else if (hasTwoPair()) {
                System.out.println("Player " + player.getName() + " has two pair");
                System.out.println("--------------------------------------------------- \n");
                System.out.println("Setting player hand value to TWO_PAIR and setting handValueAsInt to 3");
                player.setHandValueAsInt(3);
                player.setHandValue(Player.HandValue.TWO_PAIR);
                getBestRank(player);
            } else if (hasTwoOfAKind()) {
                System.out.println("Player " + player.getName() + " has a two of a kind");
                System.out.println("--------------------------------------------------- \n");
                System.out.println("Setting player hand value to PAIR and setting handValueAsInt to 2");
                player.setHandValueAsInt(2);
                player.setHandValue(Player.HandValue.PAIR);
                getBestRank(player);
            } else {
                System.out.println("Player " + player.getName() + " has a high card");
                System.out.println("--------------------------------------------------- \n");
                System.out.println("Setting player hand value to HIGH_CARD and setting handValueAsInt to 1");
                player.setHandValueAsInt(1);
                player.setHandValue(Player.HandValue.HIGH_CARD);
                getBestRank(player);
            }
            System.out.println("Clearing combined and sets");
            combined.clear();
            sets.clear();
            bestFiveCards.clear();
        }



        Player.HandValue bestR = Player.HandValue.HIGH_CARD;

//        for (Player player : players) {
//            if (!player.isFolded()) {
//
//                combined.addAll(player.getHand().getCards()); // combine hand and board cards
//                combined.addAll(board.getCards());
//                System.out.println(combined);
//                System.out.println(player.getHand());
//                System.out.println(board.getCards());
//                sortByValue();
//                if (hasRoyalFlush()) {
//                    mode = HandCalculatorMode.FIVE_CARD_HANDS;
//                    player.setHandValue(Player.HandValue.ROYAL_FLUSH);
//                    player.setHighCard(getHighCardInHand(combined));
//
//                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
//                } else if (hasStraightFlush()) {
//                    mode = HandCalculatorMode.FIVE_CARD_HANDS;
//                    player.setHandValue(Player.HandValue.STRAIGHT_FLUSH);
//                    player.setHighCard(getHighCardInHand(combined));
//
//                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
//                } else if (hasFourOfAKind() && mode != HandCalculatorMode.FIVE_CARD_HANDS) {
//                    mode = HandCalculatorMode.SETS;
//                    player.setHandValue(Player.HandValue.FOUR_OF_A_KIND);
//                    player.setFourOfAKindFromList(sets);
//                    player.setHighCard(getHighestQuads(sets));
//
//                    System.out.println(player.getName() + " has quad " + player.getHighCard());
//                    System.out.println(player.getName() + " and Board: " + combined);
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
//                } else if (hasFullHouse()) {
//                    mode = HandCalculatorMode.FIVE_CARD_HANDS;
//                    player.setHandValue(Player.HandValue.FULL_HOUSE);
//                    player.setHighCard(getHighCardInHand(combined));
//
//                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
//                } else if (hasFlush() && !hasStraightFlush()) {
//                    mode = HandCalculatorMode.FIVE_CARD_HANDS;
//                    player.setHandValue(Player.HandValue.FLUSH);
//                    player.setHighCard(getHighCardInHand(combined));
//
//                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
//                } else if (hasStraight(combined) && !hasStraightFlush()) {
//                    mode = HandCalculatorMode.FIVE_CARD_HANDS;// we use hasStraight to calculate a straight flush as well, so we have to do some shenanigans
//                    player.setHandValue(Player.HandValue.STRAIGHT);
//                    player.setHighCard(getHighCardInHand(combined));
//
//                    System.out.println("Player " + player.getName() + " has a " + player.getHandValue() + ", with a high card of " + player.getHighCard());
//                    System.out.println(player.getName() + " and Board: " + combined);
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
//                } else if (hasThreeOfAKind() && mode != HandCalculatorMode.FIVE_CARD_HANDS) {
//                    mode = HandCalculatorMode.SETS;
//                    player.setThreeOfAKindFromList(sets);
//
//                    player.setHandValue(Player.HandValue.THREE_OF_A_KIND);
//                    player.setHighCard(getHighestTrips(sets));
//
//
//                    System.out.println(player.getName() + " has a set of " + player.getHighCard());
//                    System.out.println(player.getName() + " and Board: " + combined);
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
//                } else if (hasTwoPair()) {
//                    mode = HandCalculatorMode.SETS;
//                    player.setHandValue(Player.HandValue.TWO_PAIR);
//                    player.setHighCard(getHighestPair(sets));
//                    player.setBestPair(getHighestPairAsHand(sets));
//
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
//                    System.out.println();
//                    System.out.println("Has two pair");
//                    System.out.println(player.getName() + " has " + player.getHandValue() + ", with the highest pair being " + player.getHighCard());
//                } else if (hasTwoOfAKind() && !hasTwoPair() && mode != HandCalculatorMode.FIVE_CARD_HANDS) {
//                    mode = HandCalculatorMode.SETS;
//                    player.setHandValue(Player.HandValue.PAIR);
//                    player.setHighCard(getHighestPair(sets));
//                    player.setKicker(getKicker(combined, sets));
//                    player.setPairFromList(sets);
//
//                    System.out.println(player.getName() + " has a pair of " + player.getHighCard() + " with a kicker of " + player.getKicker());
//                } else {
//                    player.setHandValue(Player.HandValue.HIGH_CARD);
//                    player.setHighCard(getHighCardInHand(combined));
//
//                    System.out.println(player.getName() + " has a high card of " + player.getHighCard());
//                }
//            }
//            combined.clear();
//            sets.clear();
//            bestFiveCards.clear();
//        }

        System.out.println("Calculating who won...");
        for (Player player : players) {
            System.out.println("Calculating.. Player " + player.getName() + " has " + player.getHandValue());
            System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
            if (player.getHandValueAsInt() == bestRank) {
                potentialWinners.add(player);
            }
        }

        if (potentialWinners.size() == 1) {
            System.out.println("Found one winner! " + potentialWinners.getFirst().getName() + " has the highest hand value of " + potentialWinners.getFirst().getHandValue());
            return potentialWinners;
        } else if (potentialWinners.size() > 1) {
            System.out.println(potentialWinners.size() + " players are tied in hand value, calculating who won");
            if (potentialWinners.size() == 2) {
                System.out.println("Found two potential winners! " + potentialWinners.getFirst().getName() + " and " + potentialWinners.get(1).getName() + " have the same hand value of " + bestRank + "(" + potentialWinners.getFirst().getHandValue() + ")");
                if (potentialWinners.getFirst().getHandValueAsInt() == 1) {

                }
            }
        }

        Player winner = null;
        switch (mode) {
            case SETS:
                System.out.println("Calculating case SETS");
                int highest = 0;
                int highestSecondPair = 0;
                for (Player player : players) {
                    System.out.println("Calculating.. Player " + player.getName() + " has " + player.getHandValue() + " " + player.getHand());
                    if (playerRankIsHigherThan(bestR, player) && !player.isFolded()){
                        bestR = player.getHandValue();
                        System.out.println(bestR + " " + player.getHand());
                        winner = player;
                    } else if (!player.isFolded() && playerRankIsEqualTo(bestR, player)) {
                        System.out.println(bestR + " " + player.getHand());
                        if (player.getQuads() != null && player.getQuads().getFirstCard().toInt() > highest) {
                            highest = player.getQuads().getFirstCard().toInt();
                            winner = player;
                        } else if (player.getThreeOfAKind() != null && player.getThreeOfAKind().getFirstCard().toInt() > highest) {
                            highest = player.getThreeOfAKind().getFirstCard().toInt();
                            winner = player;
                        } else if (player.getBestPair() != null && player.getSecondBestPair() != null && (player.getBestPair().getFirstCard().toInt() > highest)) {
                            if (player.getBestPair().getFirstCard().toInt() == highest && player.getSecondBestPair().getFirstCard().toInt() > highestSecondPair) {
                                // if the player has two pair, check the first pair, if it is equal check the second pair
                                highest = player.getBestPair().getFirstCard().toInt();
                                highestSecondPair = player.getSecondBestPair().getFirstCard().toInt();
                                winner = player;
                            } else {
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

                winners.add(winner);
                break;
            case FULL_HOUSES:
                System.out.println("Calculating case FULL_HOUSES");
                List<Card> highestSet;
                List<Card> highestPair;
                for (Player player : players) {
                    System.out.println("Calculating.. Player " + player.getName() + " has " + player.getHandValue() + " " + player.getHand());
                    if (playerRankIsHigherThan(bestR, player) && !player.isFolded()){
                        bestR = player.getHandValue();
                        System.out.println(bestR + " " + player.getHand());
                        winner = player;
                    }
                }
                System.out.println();
                System.out.println();
                System.out.println();
                winners.add(winner);
                break;
            case FIVE_CARD_HANDS:
                System.out.println("Calculating case FIVE_CARD_HANDS");
                System.out.println();
                System.out.println();
                System.out.println();
                System.out.println();
                int highestKicker = 2;
                for (Player player : players) {
                    if (playerRankIsHigherThan(bestR, player) && !player.isFolded()){
                        bestR = player.getHandValue();
                        System.out.println(player.getName() + " " + player.getHandValue());
                        winner = player;
                    } else {
                        if (player.getKicker() != null && player.getKicker().toInt() > highestKicker) {
                            highestKicker = player.getKicker().toInt();
                            winner = player;
                            System.out.println(player.getName() + player.getHand());
                        }
                    }
                }
                System.out.println();
                winners.add(winner);
                break;
            case HIGH_CARDS:
                System.out.print("Calculating case HIGH_CARDS");
                int highCard = 2;
                int secondHighCard = 2;
                for (Player player : players) {
                    System.out.print(".");
                    if (player.getHighCard().toInt() > highCard) {
                        highCard = player.getHighCard().toInt();
                        winner = player;
                        secondHighCard = player.getSecondBestHighCard().toInt();
                    } else if (player.getHighCard().toInt() == highCard && player.getSecondBestHighCard().toInt() > secondHighCard) {
                        secondHighCard = player.getSecondBestHighCard().toInt();
                        winner = player;
                    } else if (player.getHighCard().toInt() == highCard && player.getSecondBestHighCard().toInt() == secondHighCard) {
                        System.out.println("Top two high cards are the same, have not implemented past this. figure out who won on ur own.");
                    }
                }
                System.out.println();
                winners.add(winner);
                break;
            default:
                break;
        }

        return winners;
    }

    private void getBestRank(Player player) {
        if (bestRank < player.getHandValueAsInt()) {
            System.out.println("bestRank " + bestRank + " is less than player.getHandValueAsInt(), setting bestRank to " + player.getHandValueAsInt());
            System.out.println("---------------------------------------------------");
            bestPlayers.add(player);
            bestRank = player.getHandValueAsInt();
        } else if (bestRank == player.getHandValueAsInt()) {
            System.out.println("bestRank " + bestRank + " is equal to player.getHandValueAsInt() " + player.getHandValueAsInt());
            System.out.println("---------------------------------------------------");

        }
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
        } else return bestRank == Player.HandValue.ROYAL_FLUSH && player.getHandValueAsInt() > 9;
    }
    private boolean playerRankIsEqualTo(Player.HandValue bestRank, Player player) {
        if (bestRank == Player.HandValue.HIGH_CARD && player.getHandValueAsInt() == 0) {
            return true;
        } else if (bestRank == Player.HandValue.PAIR && player.getHandValueAsInt() == 1) {
            return true;
        } else if (bestRank == Player.HandValue.TWO_PAIR && player.getHandValueAsInt() == 2) {
            return true;
        } else if (bestRank == Player.HandValue.THREE_OF_A_KIND && player.getHandValueAsInt() == 3) {
            return true;
        } else if (bestRank == Player.HandValue.STRAIGHT && player.getHandValueAsInt() == 4) {
            return true;
        } else if (bestRank == Player.HandValue.FLUSH && player.getHandValueAsInt() == 5) {
            return true;
        } else if (bestRank == Player.HandValue.FULL_HOUSE && player.getHandValueAsInt() == 6) {
            return true;
        } else if (bestRank == Player.HandValue.FOUR_OF_A_KIND && player.getHandValueAsInt() == 7) {
            return true;
        } else if (bestRank == Player.HandValue.STRAIGHT_FLUSH && player.getHandValueAsInt() == 8) {
            return true;
        } else return bestRank == Player.HandValue.ROYAL_FLUSH && player.getHandValueAsInt() == 9;
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
    private Hand getHighestPairAsHand(List<Card> hand) {
        Hand hand1 = new Hand(hand);
        return (hand1.getFirstTwoCards());
    }
    
    private Card getHighCardInHand(List<Card> hand) {

        // Sort by face value in descending order to get the highest card first
        hand.sort((card1, card2) -> card2.getFaceValue().ordinal() - card1.getFaceValue().ordinal());

        return hand.getFirst(); // Return the highest card
    }
    private Card getKicker(List<Card> hand, List<Card> excluded) {
        boolean isExcluded = false;
        excluded.removeLast(); // remove extras from sets
        excluded.removeLast();
        System.out.println("Excluding: " + excluded);
        // Sort by face value in descending order to get the highest card first
        hand.sort((card1, card2) -> card2.getFaceValue().ordinal() - card1.getFaceValue().ordinal());
        //System.out.println("Getting kicker: " + hand.getFirst());
        for (Card card : excluded) {
            if (hand.getFirst() == card) {
                isExcluded = true;
            }
        }
        if (isExcluded) {
            return hand.get(2); // Return the highest card if it is not excluded.
        } else {
            return hand.getFirst();
        }
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
                // Add the cards with the matching face value to sets
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
