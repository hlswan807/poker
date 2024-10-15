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

    public void clear() {
        cards.clear();
    }
}
