package org.example;

import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public class Deck {
    private final List<Card> cards = new LinkedList<>();

    public Deck() {
        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.FaceValue faceValue : Card.FaceValue.values()) {
                Card card = new Card(suit, faceValue);
                cards.add(card);


            }
        }
        shuffle();
    }
    public void shuffle() {
        Collections.shuffle(cards);
    } // Collections not valid for AP Exam

    public Card pop() {
        return cards.removeFirst();
    }

    public String toString() {
        StringBuilder output = new StringBuilder();
        for (Card card : cards) {
            output.append(card.toString()).append("\n");
        }
        return output.toString();
    }
}
