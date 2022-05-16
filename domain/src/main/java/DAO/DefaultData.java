package DAO;


import models.calculator.CalculatorMainTable;
import models.calculator.Card;

import java.util.*;


public class DefaultData {
    private final String[] cards = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
    private final String[] suit = {"h", "d", "c", "s"};
    private final Map<String, Integer> cardsWithRank = new HashMap<>();
    private final Map<String, Integer> combinationWithPoints = new HashMap<>();
    private final List<String> cardsDeck = new LinkedList<>();
    private final Map<String, String> suitNumber = new HashMap<>();
    private final Map<String, String> primeNumber = new HashMap<>();
    private final List<Card> cardList = new LinkedList<>();

    Map<String, List<String>> allHands = new LinkedHashMap<>();

    public Map<String, List<String>> getAllHands() {
        return allHands;
    }

    public List<Card> getCardList() {
        return cardList;
    }

    public Map<String, String> getSuitNumber() {
        return suitNumber;
    }

    public Map<String, String> getPrimeNumber() {
        return primeNumber;
    }

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

        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                allHands.put(cards[i] + cards[j], new LinkedList<>());
            }
        }

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

        suitNumber.put("s", "0001");
        suitNumber.put("h", "0010");
        suitNumber.put("d", "0100");
        suitNumber.put("c", "1000");

        primeNumber.put("2", "000010");
        primeNumber.put("3", "000011");
        primeNumber.put("4", "000101");
        primeNumber.put("5", "000111");
        primeNumber.put("6", "001011");
        primeNumber.put("7", "001101");
        primeNumber.put("8", "010001");
        primeNumber.put("9", "010011");
        primeNumber.put("t", "010111");
        primeNumber.put("j", "011101");
        primeNumber.put("q", "011111");
        primeNumber.put("k", "100101");
        primeNumber.put("a", "101001");


        for (int i = 0; i < suit.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                cardList.add(new Card(cards[j], suit[i]));
            }
        }
    }


}
