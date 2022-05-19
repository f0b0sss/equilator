package models.calculator.n;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class Hand {

    private Card card1;
    private Card card2;
    private double handEquity;
    private double turnEquity;
    private double wonTimes;
    private Map<String, Double> equityHandOnTurnCard;
    private Map<String, Double> equityHandOnRiverCard;

    public Map<String, Double> getEquityHandOnTurnCard() {
        return equityHandOnTurnCard;
    }

    public void setEquityHandOnTurnCard(Map<String, Double> equityHandOnTurnCard) {
        this.equityHandOnTurnCard = equityHandOnTurnCard;
    }

    public Map<String, Double> getEquityHandOnRiverCard() {
        return equityHandOnRiverCard;
    }

    public void setEquityHandOnRiverCard(Map<String, Double> equityHandOnRiverCard) {
        this.equityHandOnRiverCard = equityHandOnRiverCard;
    }

    public double getWonTimes() {
        return wonTimes;
    }

    public void setWonTimes(double wonTimes) {
        this.wonTimes = wonTimes;
    }

    public Card getCard1() {
        return card1;
    }

    public void setCard1(Card card1) {
        this.card1 = card1;
    }

    public Card getCard2() {
        return card2;
    }

    public void setCard2(Card card2) {
        this.card2 = card2;
    }

    public double getHandEquity() {
        return handEquity;
    }

    public void setHandEquity(double handEquity) {
        this.handEquity = handEquity;
    }

    public String getHand() {
        return card1.getCard() + card2.getCard();
    }

    public double getTurnEquity() {
        return turnEquity;
    }

    public void setTurnEquity(double turnEquity) {
        this.turnEquity = turnEquity;
    }


    public Hand() {
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hand hand = (Hand) o;
        return Double.compare(hand.handEquity, handEquity) == 0 && Double.compare(hand.turnEquity, turnEquity) == 0 && wonTimes == hand.wonTimes && Objects.equals(card1, hand.card1) && Objects.equals(card2, hand.card2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(card1, card2, handEquity, turnEquity, wonTimes);
    }

    @Override
    public String toString() {
        return card1.getCard() +
                card2.getCard();
    }

    public Hand buildHand(String cards) {
        Card card1 = new Card();
        Card card2 = new Card();

        card1.setValue(String.valueOf(cards.charAt(0)));
        card1.setSuit(String.valueOf(cards.charAt(1)));

        card2.setValue(String.valueOf(cards.charAt(2)));
        card2.setSuit(String.valueOf(cards.charAt(3)));

        Map<String, Double> map1 = new LinkedHashMap<>();
        Map<String, Double> map2 = new LinkedHashMap<>();

        Hand hand = new Hand();
        hand.setCard1(card1);
        hand.setCard2(card2);
        hand.setHandEquity(0.0);
        hand.setTurnEquity(0.0);
        hand.setEquityHandOnTurnCard(map1);
        hand.setEquityHandOnRiverCard(map1);

        return hand;
    }
}
