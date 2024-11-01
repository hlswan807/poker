package org.example;

import java.util.*;
public class Hand {
    List<Card> cards = new LinkedList<>();

    public void add(Card c) {
        cards.add(c);
    }
    public String toString() {
        return "Your hand is the " + cards.toString();
    }
    public String toWinningString() {
        return " won with the hand: " + cards.toString();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void clear() {
        cards.clear();
    }
}
