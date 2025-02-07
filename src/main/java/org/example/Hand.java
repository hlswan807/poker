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

    public Card getCard(int index) {
        return cards.get(index);
    }
    public Card getFirstCard() {
        return cards.getFirst();
    }

    public Hand getFirstTwoCards() {
        Hand hand = new Hand();
        hand.add(cards.getFirst());
        hand.add(cards.get(1));
        return hand;
    }

    public void clear() {
        cards.clear();
    }

    public void addAll(List<Card> cards) {
        this.cards.addAll(cards);
    }


}
