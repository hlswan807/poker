package org.example;

import java.util.Scanner;


public class Poker {
    final int smallBlindAmount = 2;
    final int bigBlindAmount = smallBlindAmount*2;
    private static final int startingStack = 300;
    int nextPlayer = 0;
    int dealer = 0;
    int currentPotBet;
    int lastRaiser;
    Scanner input = new Scanner(System.in);
    Deck deck = new Deck();
    Hand hand = new Hand();
    Hand hand2 = new Hand();
    Pot pot = new Pot();
    Player[] players;


    public Poker(int numPlayers) {
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player(i+"", startingStack);
        }
    }


    public void game() {

        betBlinds();
        dealHand();
        preFlopAction();

    }

    private void preFlopAction() {
        boolean actionContinues = true;

        nextPlayer = dealer;
        incNextPlayerNumber(); //sb
        incNextPlayerNumber(); // bb
        incNextPlayerNumber(); //utg
        lastRaiser = nextPlayer;
        for (int i = 0; i < 100; i++) {
            getNextPlayerAction();
            incNextPlayerNumber();
            if (lastRaiser == nextPlayer) {break;}
        }

    }


    private void getNextPlayerAction() {

        // if currentPotBet = player currentBet, then actions are ch or f
        if (currentPotBet == players[nextPlayer].getCurrentBet()) {
            System.out.println("What would you like to do? You can C, Check, or R, Raise.");
            String action = input.nextLine();
            if (action.equalsIgnoreCase("R")) {
                nextPlayerRaises();
            } else if (action.equalsIgnoreCase("C")) {
                System.out.println("You check");
            }
        } else { // if currentPotBet < player currentBet, then you need to put in more money.

            System.out.println("What would you like to do? You can F, Fold, C, Call, or R, Raise.");
            String action = input.nextLine();
            if (action.equalsIgnoreCase("F")) {
                System.out.println("You fold!");
            } else if (action.equalsIgnoreCase("C")) {
                System.out.println("You call");
            } else if (action.equalsIgnoreCase("R")) {
                nextPlayerRaises();
            }
        }
    }

    private void nextPlayerRaises() {
        System.out.println(players[nextPlayer].getName() + " raises!");
        lastRaiser = nextPlayer;
    }

    private void betBlinds() {
        incNextPlayerNumber();
        players[nextPlayer].bet(smallBlindAmount);
        incNextPlayerNumber();
        players[nextPlayer].bet(bigBlindAmount);
        currentPotBet = bigBlindAmount;
    }
    private void incNextPlayerNumber() {
        nextPlayer++;
        if (nextPlayer >= players.length) {
            nextPlayer = 0;
        }
    }

    public void dealHand() {
        System.out.println("Your hand is about to be shown! Everyone else look away! Player, when you are ready to see your hand, press enter.");
        pressEnterToContinue();
        nextPlayer = dealer;
        for (int i = 0; i < players.length; i++) {
            incNextPlayerNumber();
            players[nextPlayer].setHand(new Hand());
        }
        for(int j = 0; j < 2; j++) {
            for (int i = 0; i < players.length; i++) {
                incNextPlayerNumber();
                players[nextPlayer].add(deck.pop());
            }
        }
        pressEnterToContinue();
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
