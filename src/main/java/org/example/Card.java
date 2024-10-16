package org.example;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Card {

    public enum Suit {
        Hearts,
        Clubs,
        Spades,
        Diamonds
    }
    public enum FaceValue {
        Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten,
        Jack, Queen, King, Ace
    }

    private FaceValue faceValue;
    private Suit suit;


    public Card(Suit s, FaceValue v) {
        suit = s;
        faceValue = v;
    }

    public String toString() {
        return faceValue + " of " + suit;
    }

}
