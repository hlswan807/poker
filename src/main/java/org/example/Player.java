package org.example;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.processing.Generated;
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
    // GMS
    // Call was kind of confusing because to raise, you need to bet to catch up to the current pot amount then bet over that
    // plus there was information leakage of Poker.currentPotBet into Player
    // Instead I added a method getCallAmount to poker
//    public void call(int currentPotBet) {
//        bet(currentPotBet-currentBet);
//        System.out.println(name + " calls " + currentBet);
//    }

    public void fold() {
        isFolded = true;
    }

    public void add(Card card) {
        hand.add(card);
        //System.out.println(name + " is dealt " + card.toString());
    }

}
