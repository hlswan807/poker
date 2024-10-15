package org.example;

import java.util.Scanner;


public class Poker
{
    int bigBlindAmount = 5;
    int smallBlindAmount = bigBlindAmount/2;
    Scanner input = new Scanner(System.in);
    Deck deck = new Deck();
    Hand hand = new Hand();
    Hand hand2 = new Hand();
    Pot pot = new Pot();
    Player player1 = new Player();
    Player player2 = new Player();
    public void game() {
        setup();
        blinds();
        dealHand();
        dealHand2();

    }

    private void blinds() {
        if (player1.getPosition() == Player.Position.BB) {
            player1.bet(bigBlindAmount);
            player2.bet(smallBlindAmount);
        }
    }

    private void setup() {
        int startingStack = 300;
        player1.setStack(startingStack);
        player2.setStack(startingStack);
        player1.setPosition(Player.Position.BB);
        player2.setPosition(Player.Position.SB);
    }

    public void deal() {
        hand.add(deck.pop());
    }

    public void dealHand() {
        System.out.println("Player 1, your hand is about to be shown! Everyone else look away! Player, when you are ready to see your hand, press enter.");
        pressEnterToContinue();
        hand.add(deck.pop());
        hand.add(deck.pop());
        player1.setHand(hand);
        System.out.println(player1.getHand().toString());
        System.out.println("Your starting stack is " + player1.getStack());
        System.out.println("Player 1, now hide your hand and continue.");
        pressEnterToContinue();
        hide();
    }

    public void dealHand2() {
        System.out.println("Player 2, your hand is about to be shown! Everyone else look away! Player, when you are ready to see your hand, press enter.");
        pressEnterToContinue();
        hand2.add(deck.pop());
        hand2.add(deck.pop());
        player2.setHand(hand2);
        System.out.println(player2.getHand().toString());
        System.out.println("Your starting stack is " + player2.getStack());
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
