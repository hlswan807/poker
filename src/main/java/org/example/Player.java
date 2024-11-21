package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
public class Player {
    @Setter
    private Hand bestPair;
    @Setter
    private Hand secondBestPair;
    @Setter
    private Hand threeOfAKind;
    @Setter
    private Hand quads;
    private HandValue handValue;
    @Setter
    private int stack;
    @Setter
    private Hand hand;
    @Setter
    private String name;
    @Setter
    private int currentBet;
    @Setter
    private boolean isFolded = false;
    @Setter
    private int handValueAsInt = 0;




    @Getter
    enum Position {
        UTG, UTGPlus1, UTGPlus2, LG, HJ, CO, BTN, SB, BB,
    }
    @Getter
    public enum HandValue {
        ROYAL_FLUSH, STRAIGHT_FLUSH, FOUR_OF_A_KIND, FULL_HOUSE, FLUSH, STRAIGHT, THREE_OF_A_KIND, TWO_PAIR, PAIR, HIGH_CARD
    }
    @Setter
    private Card highCard;
    @Setter
    private Card secondBestHighCard;
    @Setter
    private Card kicker;
    @Setter
    private Position position;

    public Player(String name, int startingStack) {
        stack = startingStack;
        this.name = name;

    }
    public void setHandValue(HandValue handValue1) {
        handValue = handValue1;
        if (handValue1 == HandValue.HIGH_CARD) {
            setHandValueAsInt(0);
        } else if (handValue1 == HandValue.PAIR) {
            setHandValueAsInt(1);
        } else if (handValue1 == HandValue.TWO_PAIR) {
            setHandValueAsInt(2);
        } else if (handValue1 == HandValue.THREE_OF_A_KIND) {
            setHandValueAsInt(3);
        } else if (handValue1 == HandValue.STRAIGHT) {
            setHandValueAsInt(4);
        } else if (handValue1 == HandValue.FLUSH) {
            setHandValueAsInt(5);
        } else if (handValue1 == HandValue.FULL_HOUSE) {
            setHandValueAsInt(6);
        } else if (handValue1 == HandValue.FOUR_OF_A_KIND) {
            setHandValueAsInt(7);
        } else if (handValue1 == HandValue.STRAIGHT_FLUSH) {
            setHandValueAsInt(8);
        } else if (handValue1 == HandValue.ROYAL_FLUSH) {
            setHandValueAsInt(9);
        }
    }

    public void setThreeOfAKindFromList(List<Card> cards) {
        threeOfAKind = new Hand(cards);
    }

    public void setFourOfAKindFromList(List<Card> cards) {
        quads = new Hand(cards);
    }

    public void setPairFromList(List<Card> cards) {
        bestPair = new Hand(cards);
    }

    public void setHighCards(List<Card> cards) {

    }



    public void bet(int amount) {
        stack -= amount;
        currentBet += amount;
        System.out.println(" "+name + " bets " + amount + ", Player's current bet=" + currentBet);

    }

    public void fold() {
        isFolded = true;
    }

    public void addCard(Card card) {
        hand.add(card);
        //System.out.println(name + " is dealt " + card.toString()); // for debug
    }

    public void addHand(Hand hand) {
        this.hand.addAll(hand.getCards());
    }

    public void addChips(int chips) {
        stack += chips;
    }

}
