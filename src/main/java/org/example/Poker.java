package org.example;

import java.util.Scanner;


public class Poker
{
    Scanner input = new Scanner(System.in);
    Deck deck = new Deck();
    Hand hand = new Hand();
    Hand hand2 = new Hand();
    Pot pot = new Pot();
    public void game() {
        dealHand();
        dealHand2();
    }

    public void deal() {
        hand.add(deck.pop());
    }

    public void dealHand() {
        System.out.println("Player 1, your hand is about to be shown! Everyone else look away! Player, when you are ready to see your hand, press enter.");
        pressEnterToContinue();
        hand.add(deck.pop());
        hand.add(deck.pop());
        System.out.println(hand.toString());
        System.out.println("Player 1, now hide your hand and continue.");
        pressEnterToContinue();
        hide();
    }

    public void dealHand2() {
        System.out.println("Player 2, your hand is about to be shown! Everyone else look away! Player, when you are ready to see your hand, press enter.");
        pressEnterToContinue();
        hand2.add(deck.pop());
        hand2.add(deck.pop());
        System.out.println(hand2.toString());
        System.out.println("Player 2, now hide your hand and continue.");
        pressEnterToContinue();
        hide();
    }

    public void hide() {
        for (int i = 0; i < 50; ++i) System.out.println();
        System.out.println("Console cleared!");
    }

    public void pressEnterToContinue() {
        System.out.println("------Press Enter to Continue------");
        input.nextLine();
    }

}
