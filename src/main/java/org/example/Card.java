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

    public int toInt() {
        return faceValue.ordinal() + 2;
    }

    @Override
    public String toString() {
        return faceValue + " of " + suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Card card = (Card) obj;
        return faceValue == card.faceValue && suit == card.suit;
    }

    @Override
    public int hashCode() {
        int result = faceValue != null ? faceValue.hashCode() : 0;
        result = 31 * result + (suit != null ? suit.hashCode() : 0);
        return result;
    }
}
