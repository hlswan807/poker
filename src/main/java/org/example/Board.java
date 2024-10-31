package org.example;

import java.util.LinkedList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter

public class Board {
    List<Card> cards = new LinkedList<>();

    public void add(Card c) {
        cards.add(c);
    }

    public void clear() {
        cards.clear();
    } // for end of the game

    public String toString() {
        return "The board is " + cards.toString();
    }
}
