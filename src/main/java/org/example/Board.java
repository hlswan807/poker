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

    public String toString() {
        return "The board is the " + cards.toString();
    }

}
