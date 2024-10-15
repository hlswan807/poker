package org.example;

public class Card {

    enum Suit {
        Hearts,
        Clubs,
        Spades,
        Diamonds
    }
    //TODO talk to papa about this!
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

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit s) {
        suit = s;
    }

    public FaceValue getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(FaceValue v) {
        faceValue = v;
    }

    public String toString() {
        return faceValue + " of " + suit;
    }

}
