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
        HIGH_CARD, PAIR, TWO_PAIR, THREE_OF_A_KIND, STRAIGHT, FLUSH, FULL_HOUSE, FOUR_OF_A_KIND, STRAIGHT_FLUSH, ROYAL_FLUSH
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
        switch (handValue1) {
            case HIGH_CARD:
                handValueAsInt = 1;
                break;
            case PAIR:
                handValueAsInt = 2;
                break;
            case TWO_PAIR:
                handValueAsInt = 3;
                break;
            case THREE_OF_A_KIND:
                handValueAsInt = 4;
                break;
            case STRAIGHT:
                handValueAsInt = 5;
                break;
            case FLUSH:
                handValueAsInt = 6;
                break;
            case FULL_HOUSE:
                handValueAsInt = 7;
                break;
            case FOUR_OF_A_KIND:
                handValueAsInt = 8;
                break;
            case STRAIGHT_FLUSH:
                handValueAsInt = 9;
                break;
            case ROYAL_FLUSH:
                handValueAsInt = 10;
                break;
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
        cards.sort((card1, card2) -> card2.getFaceValue().ordinal() - card1.getFaceValue().ordinal());

        highCard = cards.getFirst();
        secondBestHighCard = cards.get(1);
    }


    public void bet(int amount) {
        stack -= amount;
        currentBet += amount;
        System.out.println(" " + name + " bets " + amount + ", Player's current bet=" + currentBet);

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


