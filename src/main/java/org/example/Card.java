package org.example;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Card {

    enum Suit {
        Hearts,
        Clubs,
        Spades,
        Diamonds
    }
    //TODO talk to papa about faceValue being exposed in line 12 of Deck.java
    enum FaceValue {
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
