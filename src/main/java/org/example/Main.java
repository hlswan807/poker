package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("Welcome to Poker! It is Texas Holdem! Good Luck!");
        System.out.println("------How many players do you have?------");
        Poker poker = new Poker(input.nextInt());
        poker.game();


    }

}