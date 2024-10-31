package org.example;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Player {
    private int stack;
    private Hand hand;
    private String name;
    private int currentBet;
    private boolean isFolded = false;
    enum Position {
        UTG, UTGPlus1, UTGPlus2, LG, HJ, CO, BTN, SB, BB,
    }
    private Position position;

    public Player(String name, int startingStack) {
        stack = startingStack;
        this.name = name;

    }

    public void bet(int amount) {
        stack -= amount;
        currentBet += amount;
        System.out.println(" "+name + " bets " + amount + ", Player's current bet=" + currentBet);

    }

    public void fold() {
        isFolded = true;
    }

    public void addCard(Card card) {
        hand.add(card);
        //System.out.println(name + " is dealt " + card.toString()); // for debug
    }

    public void addChips(int chips) {
        stack += chips;
    }

}
