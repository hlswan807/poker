package org.example;

import java.util.Scanner;


public class Poker {
    final int smallBlindAmount = 2;
    final int bigBlindAmount = smallBlindAmount*2;
    private static final int startingStack = 300;
    int nextPlayer = 0;
    int dealer = 0;
    // GMS
    // amountToCall is the official name for this in poker
    // The amountToCall-currentPlayerBetAmount is called the remainingAmountToCall
    int amountToCall;
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
        if (players[nextPlayer].isFolded()) {
            System.out.println("\n"+players[nextPlayer].getName() + " is folded.");
            return; // GMS - just short-circuit here and return if folded
        }
        // GMS added this to show where everything is at
        System.out.println("\nAction to -> " + players[nextPlayer].getName() +
                "\n amount to call=" + amountToCall + ", Player's current bet="+players[nextPlayer].getCurrentBet()+", remaining amount to call="+ getRemainingAmountToCall() );
        if (amountToCall == players[nextPlayer].getCurrentBet()) { // if currentPotBet = player currentBet, then actions are ch or f
            System.out.print(" What would you like to do? You can C, Check, or R, Raise.");
            String action = input.nextLine();
            if (action.equalsIgnoreCase("R")) {
                nextPlayerRaises();
            } else if (action.equalsIgnoreCase("C")) {
                System.out.println("You check");
            }
        } else { // if currentPotBet < player currentBet, then you need to put in more money.

            System.out.print(" What would you like to do? You can F, Fold, C, Call, or R, Raise.");
            String action = input.nextLine();
            if (action.equalsIgnoreCase("F")) {
                nextPlayerFolds();
            } else if (action.equalsIgnoreCase("C")) {
                nextPlayerCalls();
            } else if (action.equalsIgnoreCase("R")) {
                nextPlayerRaises();
            }
        }
        // GMS added this to show where everything is at
        System.out.println(" amount to call=" + amountToCall);
    }


    private void nextPlayerFolds() {
        System.out.println(players[nextPlayer].getName() + " folds!");
        players[nextPlayer].fold();
    }

    private void nextPlayerRaises() {
        // Original code
//        System.out.println(players[nextPlayer].getName() + " raises!");
//        lastRaiser = nextPlayer;
//        players[nextPlayer].call(currentPotBet);
//        players[nextPlayer].bet(5);
//        currentPotBet += 5; // FIXED HERE. It was: currentPotBet =+ 5 The plus is on the wrong side of the =

        // added this. Instead of calling and raising, do the raising all as one
        int betAmount = 5;
        System.out.println(players[nextPlayer].getName() + " raises "+ betAmount+
                ", plus the remaining amount to call="+ getRemainingAmountToCall()+", total bet="+(getRemainingAmountToCall()+betAmount));
        lastRaiser = nextPlayer;
        players[nextPlayer].bet(getRemainingAmountToCall()+betAmount);
        amountToCall += betAmount;

    }
    // GMS
    // added this
    // remainingAmountToCall is the official name for this in poker. It is the per player amount.
    // The amountToCall is the highest amount of chips has put in one round (we had called it current pot bet)
    // remainingAmountToCall=amountToCall-currentPlayerBetAmount
    public int getRemainingAmountToCall() {
        return amountToCall -players[nextPlayer].getCurrentBet();
    }
    private void nextPlayerCalls() {
        System.out.println(players[nextPlayer].getName() + " calls, amount to call=" + amountToCall +
                ", remaining amount to call="+ getRemainingAmountToCall());
        players[nextPlayer].bet(getRemainingAmountToCall());
    }

    private void betBlinds() {
        incNextPlayerNumber();
        players[nextPlayer].bet(smallBlindAmount);
        incNextPlayerNumber();
        players[nextPlayer].bet(bigBlindAmount);
        amountToCall = bigBlindAmount;
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
