package org.example;

import java.util.Scanner;

public class Main {
    static boolean playContinues = true;
    static Scanner input = new Scanner(System.in);
    public static void main(String[] args) {


        System.out.println("Welcome to Poker! It is Texas Holdem! Good Luck!");
        System.out.println("------How many players do you have?------");
        Poker poker = new Poker(input.nextInt());
        input.nextLine();

        while (playContinues) {
            poker.game();
            playAgain();
        }


    }

    private static void playAgain() {
        System.out.println("Want to Play Again? Y / N");
        if (input.nextLine().equalsIgnoreCase("n")) {
            playContinues = false;
        }
    }

}