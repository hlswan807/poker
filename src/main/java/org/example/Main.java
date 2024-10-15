package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        Deck deck = new Deck();
        Pot pot = new Pot();
        Poker poker = new Poker();
        System.out.println("Welcome to Poker! You are playing against three NPCs in a game of Texas Holdem! Good Luck!");
        System.out.println("------Press Enter to Continue------");
        input.nextLine();
        poker.game();


    }

}