package org.example;

import java.util.List;
import java.util.Scanner;


public class Poker {
    final int smallBlindAmount = 5;
    final int bigBlindAmount = smallBlindAmount*2;
    final int lowBettingLimit = smallBlindAmount*2;
    final int highBettingLimit = lowBettingLimit*2;
    private static final int startingStack = 1000;
    int nextPlayerNum = 0;
    int dealer = 0;
    // amountToCall is the official name for this in poker
    // The amountToCall-currentPlayerBetAmount is called the remainingAmountToCall
    int amountToCall;
    int lastRaiser;
    Scanner input = new Scanner(System.in);
    Deck deck = new Deck();
    Pot pot = new Pot();
    Board board = new Board();
    Player[] players;
    HandCalculator handCalculator;

    Hand fullHouseHand = new Hand(new Card(Card.Suit.Clubs, Card.FaceValue.Ten), new Card(Card.Suit.Diamonds, Card.FaceValue.Ten)); // fh
    Hand flushHand = new Hand(new Card(Card.Suit.Hearts, Card.FaceValue.Two), new Card(Card.Suit.Hearts, Card.FaceValue.Seven)); // f
    Hand straightHand = new Hand(new Card(Card.Suit.Spades, Card.FaceValue.Nine), new Card(Card.Suit.Spades, Card.FaceValue.Eight));// s
    Hand fourOfAKindHand = new Hand(new Card(Card.Suit.Spades, Card.FaceValue.Nine), new Card(Card.Suit.Spades, Card.FaceValue.Eight));// s
    Hand threeOfAKindHand = new Hand(new Card(Card.Suit.Spades, Card.FaceValue.Nine), new Card(Card.Suit.Spades, Card.FaceValue.Eight));// s
    Hand twoPairHand = new Hand(new Card(Card.Suit.Spades, Card.FaceValue.Nine), new Card(Card.Suit.Spades, Card.FaceValue.Eight));// s
    Hand pairHand = new Hand(new Card(Card.Suit.Spades, Card.FaceValue.Nine), new Card(Card.Suit.Spades, Card.FaceValue.Eight));// s
    Hand highCardHand = new Hand(new Card(Card.Suit.Spades, Card.FaceValue.Nine), new Card(Card.Suit.Spades, Card.FaceValue.Eight));// s




    public Poker(int numPlayers) {
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            players[i] = new Player("p"+i, startingStack);
        }
        handCalculator = new HandCalculator(players);
    }
    public void debug_game() {

        System.out.println("What kind of hands do you want to be dealt? 5 - Five Card Hands(RF, SF, FH, Flush, Straight) S - sets and two pair( 2P, 3 of a kind, 4K) or P - Pair and High Card");
        dealSpecificHand(input.nextLine());

        List<Player> winningPlayers = handCalculator.calculateWinner(board);
        System.out.println(winningPlayers.getFirst().getName() + " is the winner!");
    }
    public void dealSpecificHand(String hand) {
        nextPlayerNum = dealer;
        deck.pop(); // burn card
        for (int i = 0; i < players.length; i++) {
            incNextPlayerNumber();
            players[nextPlayerNum].setHand(new Hand());
        }
        if (hand.equalsIgnoreCase("5")) {
            players[0].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Ace));
            players[0].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.King));// royal flush setup
            players[1].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Nine));
            players[1].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Eight));// sf
            players[2].addCard(deck.popSpecificCard(Card.Suit.Clubs, Card.FaceValue.Ten));
            players[2].addCard(deck.popSpecificCard(Card.Suit.Diamonds, Card.FaceValue.Ten));// fh
            players[3].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Two));
            players[3].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Seven));// f
            players[4].addCard(deck.popSpecificCard(Card.Suit.Spades, Card.FaceValue.Nine));
            players[4].addCard(deck.popSpecificCard(Card.Suit.Spades, Card.FaceValue.Eight));// s
            board.add(new Card(Card.Suit.Hearts, Card.FaceValue.Queen));
            board.add(new Card(Card.Suit.Hearts, Card.FaceValue.Jack));
            board.add(new Card(Card.Suit.Hearts, Card.FaceValue.Ten));
            board.add(new Card(Card.Suit.Clubs, Card.FaceValue.Three));
            board.add(new Card(Card.Suit.Spades, Card.FaceValue.Three));
            for (Player player : players) {
                System.out.println(player.getHand());
            }
            System.out.println(board);

        } else if (hand.equalsIgnoreCase("s")) {
            players[0].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Ace));
            players[0].addCard(deck.popSpecificCard(Card.Suit.Spades, Card.FaceValue.Ace));// 4k
            players[1].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Three));
            players[1].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Eight));// 3k
            players[2].addCard(deck.popSpecificCard(Card.Suit.Clubs, Card.FaceValue.King));
            players[2].addCard(deck.popSpecificCard(Card.Suit.Diamonds, Card.FaceValue.Jack));// 2p

            board.add(new Card(Card.Suit.Clubs, Card.FaceValue.Ace));
            board.add(new Card(Card.Suit.Diamonds, Card.FaceValue.Ace));
            board.add(new Card(Card.Suit.Hearts, Card.FaceValue.Ten));
            board.add(new Card(Card.Suit.Clubs, Card.FaceValue.Three));
            board.add(new Card(Card.Suit.Spades, Card.FaceValue.Three));
            for (Player player : players) {
                System.out.println(player.getHand());
            }
            System.out.println(board);
        } else if (hand.equalsIgnoreCase("p")) {
            players[0].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.King));
            players[0].addCard(deck.popSpecificCard(Card.Suit.Spades, Card.FaceValue.King));
            players[1].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Jack));
            players[1].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Eight));// pairs
            players[2].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Two));
            players[2].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Ace));// hc
            board.add(new Card(Card.Suit.Clubs, Card.FaceValue.Jack));
            board.add(new Card(Card.Suit.Diamonds, Card.FaceValue.Queen));
            board.add(new Card(Card.Suit.Hearts, Card.FaceValue.Ten));
            board.add(new Card(Card.Suit.Clubs, Card.FaceValue.Four));
            board.add(new Card(Card.Suit.Spades, Card.FaceValue.Three));
            for (Player player : players) {
                System.out.println(player.getHand());
            }
            System.out.println(board);
        } else if (hand.equalsIgnoreCase("h")) {
            players[0].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.King));
            players[0].addCard(deck.popSpecificCard(Card.Suit.Spades, Card.FaceValue.Eight));
            players[1].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Jack));
            players[1].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Eight));// pairs
            players[2].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Two));
            players[2].addCard(deck.popSpecificCard(Card.Suit.Hearts, Card.FaceValue.Ace));// hc
            board.add(new Card(Card.Suit.Clubs, Card.FaceValue.Seven));
            board.add(new Card(Card.Suit.Diamonds, Card.FaceValue.Queen));
            board.add(new Card(Card.Suit.Hearts, Card.FaceValue.Ten));
            board.add(new Card(Card.Suit.Clubs, Card.FaceValue.Four));
            board.add(new Card(Card.Suit.Spades, Card.FaceValue.Three));
            for (Player player : players) {
                System.out.println(player.getHand());
            }
            System.out.println(board);
        }




    }

    public void game() {
        betBlinds();
        dealHand();
        Player winningPlayer = preFlopAction();
        doesGameContinue(winningPlayer);
        dealFlop();
        winningPlayer = action();
        doesGameContinue(winningPlayer);
        dealTurn();
        winningPlayer = action();
        doesGameContinue(winningPlayer);
        dealRiver();
        winningPlayer = action();
        doesGameContinue(winningPlayer);
        List<Player> winningPlayers = handCalculator.calculateWinner(board);
        System.out.println(winningPlayers.getFirst().getName());

    }



    private void doesGameContinue(Player winningPlayer) {
        if (winningPlayer != null) {
            endHand(winningPlayer);
        }
    }


    private void betBlinds() {
        incNextPlayerNumber();
        System.out.println(nextPlayerName() + " is the SB and puts in " + smallBlindAmount);
        bet(smallBlindAmount);
        incNextPlayerNumber();
        System.out.println(nextPlayerName() + " is the BB and puts in " + bigBlindAmount);
        bet(bigBlindAmount);
        amountToCall = bigBlindAmount;
    }
    public void dealHand() {
        System.out.println("Press enter to deal.");
        pressEnterToContinue();
        nextPlayerNum = dealer;
        deck.pop(); // burn card
        for (int i = 0; i < players.length; i++) {
            incNextPlayerNumber();
            players[nextPlayerNum].setHand(new Hand());
        }
        for(int j = 0; j < 2; j++) {
            for (int i = 0; i < players.length; i++) {
                incNextPlayerNumber();
                players[nextPlayerNum].addCard(deck.pop());
            }
        }
        System.out.println("Dealing Complete! Player " + 3 + ", you are first to act. Your cards will be revealed and you can take the first action.");
        pressEnterToContinue();
    }



    private Player preFlopAction() {
        Player winningPlayer = null;
        nextPlayerNum = dealer;
        incNextPlayerNumber(); //sb
        incNextPlayerNumber(); // bb
        incNextPlayerNumber(); //utg
        lastRaiser = nextPlayerNum;
        for (int i = 0; i < 100; i++) {
            getNextPlayerAction();
            //hide();
            incNextPlayerNumber();

            winningPlayer = getWinner();
            if(winningPlayer != null) {
                System.out.println(winningPlayer.getName() + " is the winner!");
                break;
            }
            if (lastRaiser == nextPlayerNum) {break;}
        }
        return winningPlayer;
    }

    private void dealFlop() {
        deck.pop(); // burn card
        board.add(deck.pop());
        board.add(deck.pop());
        board.add(deck.pop());
        printBoard();
    }

    private Player action() {
        Player winningPlayer = null;
        nextPlayerNum = dealer+1;
        lastRaiser = nextPlayerNum;
        for (int i = 0; i < 100; i++) {
            getNextPlayerAction();

            incNextPlayerNumber();

            winningPlayer = getWinner();
            if(winningPlayer != null) {
                System.out.println(winningPlayer.getName() + " is the winner!");
                break;
            }
            if (lastRaiser == nextPlayerNum) {break;}
        }
        return winningPlayer;
    }
    private void dealTurn() {
        deck.pop(); // burn card
        board.add(deck.pop());
        printBoard();
    }
    private void dealRiver() {
        deck.pop(); // burn card
        board.add(deck.pop());
        printBoard();

    }


    private void endHand(Player winningPlayer) {
        winningPlayer.addChips(pot.getChips());
        System.out.println(winningPlayer.getName() + " now has " + winningPlayer.getStack() + " chips.");
        pot.clearChips();
    }

    private void getNextPlayerAction() {
        if (players[nextPlayerNum].isFolded()) {
            System.out.println("\n"+nextPlayerName() + " is folded.");
            return;
        }
        printBoard();
        System.out.println("\nAction to -> " + nextPlayerName() + " " + players[nextPlayerNum].getHand() +
                "\n Pot=" + pot.getChips() +
                "\n amount to call=" + amountToCall + ", Player's current bet="+players[nextPlayerNum].getCurrentBet()+", remaining amount to call="+ getRemainingAmountToCall() );
        if (amountToCall == players[nextPlayerNum].getCurrentBet()) { // if currentPotBet = player currentBet, then actions are ch or f
            System.out.print(" What would you like to do? You can C, Check, or R, Raise.");
            String action = input.nextLine();
            if (action.equalsIgnoreCase("R")) {
                nextPlayerRaises();
            } else if (action.equalsIgnoreCase("C")) {
                System.out.println(nextPlayerName() + " checks");
            }
        } else { // if currentPotBet < player currentBet, then you need to put in more money.

            System.out.print(" What would you like to do? You can F, Fold, C, Call(" + getRemainingAmountToCall() + " to call), or R, Raise.");
            String action = input.nextLine();
            if (action.equalsIgnoreCase("F")) {
                nextPlayerFolds();
            } else if (action.equalsIgnoreCase("C")) {
                nextPlayerCalls();
            } else if (action.equalsIgnoreCase("R")) {
                nextPlayerRaises();
            }
        }

        //System.out.println(" amount to call=" + amountToCall);
    }


    private void nextPlayerFolds() {
        System.out.println(nextPlayerName() + " folds!");
        players[nextPlayerNum].fold();
    }

    private Player getWinner() {
        Player remainingPlayer = null;
        for(Player player : players) {
            if (!player.isFolded()) {
                if (remainingPlayer == null) {
                    remainingPlayer = player;
                } else {   //remainingPlayer is not null, at least two players still remaining, no remainingPlayer
                  return null;
                }

            }
        }
        return remainingPlayer;
    }

    private void nextPlayerRaises() {
        System.out.println(" How much do you want to raise by?");
        int betAmount = input.nextInt();
        input.nextLine();
        System.out.println(nextPlayerName() + " raises "+ betAmount+
                ", plus the remaining amount to call="+ getRemainingAmountToCall()+", total bet="+(getRemainingAmountToCall()+betAmount));
        lastRaiser = nextPlayerNum;
        bet(getRemainingAmountToCall() + betAmount);
        amountToCall += betAmount;

    }
    // GMS
    // added this
    // remainingAmountToCall is the official name for this in poker. It is the per player amount.
    // The amountToCall is the highest amount of chips has put in one round (we had called it current pot bet)
    // remainingAmountToCall=amountToCall-currentPlayerBetAmount
    public int getRemainingAmountToCall() {
        return amountToCall -players[nextPlayerNum].getCurrentBet();
    }
    private void nextPlayerCalls() {
        System.out.println(nextPlayerName() + " calls, amount to call=" + amountToCall +
                ", remaining amount to call="+ getRemainingAmountToCall());
        bet(getRemainingAmountToCall());

    }


    private void incNextPlayerNumber() {
        nextPlayerNum++;
        if (nextPlayerNum >= players.length) {
            nextPlayerNum = 0;
        }
    }


    public void hide() {
        for (int i = 0; i < 50; ++i) System.out.println();
        //System.out.println("Console cleared!");
    }

    public void pressEnterToContinue() {
        System.out.println("------Press Enter to Continue------");
        input.nextLine();
    }

    private void bet(int amount) {
        players[nextPlayerNum].bet(amount);
        pot.add(amount);
    }
    
    private String nextPlayerName() {
        return players[nextPlayerNum].getName();
    }

    private void printBoard() {
        System.out.println(board.toString());
    }

}
