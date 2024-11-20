package org.example;

import java.util.*;
public class Hand {
    List<Card> cards = new LinkedList<>();
    public Hand() {

    }

    public Hand(Card card1, Card card2) {
    }

    public Hand(List<Card> cards) {
        this.cards.addAll(cards);
    }

    public void add(Card c) {
        cards.add(c);
    }
    public String toString() {
        return "Your hand is the " + cards.toString();
    }

    public List<Card> getCards() {
        return cards;
    }
    public Card getFirstCard() {
        return cards.get(0);
    }

    public void clear() {
        cards.clear();
    }

    public void addAll(List<Card> cards) {
        this.cards.addAll(cards);
    }
}
