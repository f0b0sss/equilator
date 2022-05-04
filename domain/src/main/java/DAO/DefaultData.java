package DAO;


import models.CalculatorMainTable;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class DefaultData {
    private final String[] cards = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
    private final String[] suit = {"h", "d", "c", "s"};
    private final Map<String, Integer> cardsWithRank = new HashMap<>();
    private final Map<String, Integer> combinationWithPoints = new HashMap<>();
    private final List<String> cardsDeck = new LinkedList<>();

    private final List<CalculatorMainTable> calculatorMainTables = new LinkedList<>();

    public List<CalculatorMainTable> getCalculatorMainTables() {
        return calculatorMainTables;
    }

    public Map<String, Integer> getCardsWithRank() {
        return cardsWithRank;
    }

    public Map<String, Integer> getCombinationWithPoints() {
        return combinationWithPoints;
    }

    public List<String> getCardsDeck() {
        return cardsDeck;
    }

    {
        combinationWithPoints.put("high card", 100);
        combinationWithPoints.put("one pair", 200);
        combinationWithPoints.put("two pair", 300);
        combinationWithPoints.put("set", 400);
        combinationWithPoints.put("straight", 500);
        combinationWithPoints.put("flush", 600);
        combinationWithPoints.put("full house", 700);
        combinationWithPoints.put("four of kind", 800);
        combinationWithPoints.put("straight flush", 900);
        combinationWithPoints.put("royal flush", 1000);

        cardsWithRank.put("2", 1);
        cardsWithRank.put("3", 2);
        cardsWithRank.put("4", 3);
        cardsWithRank.put("5", 4);
        cardsWithRank.put("6", 5);
        cardsWithRank.put("7", 6);
        cardsWithRank.put("8", 7);
        cardsWithRank.put("9", 8);
        cardsWithRank.put("T", 9);
        cardsWithRank.put("J", 10);
        cardsWithRank.put("Q", 11);
        cardsWithRank.put("K", 12);
        cardsWithRank.put("A", 13);

        for (int i = 0; i < suit.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                cardsDeck.add(cards[j] + suit[i]);
            }
        }
    }


}
