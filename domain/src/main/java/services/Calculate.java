package services;

import DAO.DefaultData;
import exceptions.InvalidInputCards;
import models.calculator.CalculatorMainTable;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Calculate {

    private final DefaultData defaultData;

    private final String[] cards = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
    private final String[] suit = {"h", "d", "c", "s"};

    private final Map<String, Integer> playersPoints = new HashMap<>(2);
    private Map<String, Integer> madeHandWithRank = new HashMap<>(7);
    private Map<String, Integer> kickersWithRank = new HashMap<>(7);
    private boolean wasFindCombination = false;
    private int countOfBoards;
    private int sumOfPoints;
    private List<String> allCards = new LinkedList<>();


    private Map<String, Double> mapRange1 = new LinkedHashMap<>();
    private Map<String, Double> mapRange2 = new LinkedHashMap<>();

    @Autowired
    public Calculate(DefaultData defaultData) {
        this.defaultData = defaultData;
    }


    public List<String> buildAllGroupHands(String groupHand, String board) {
        List<String> hands = new ArrayList<>();

        for (int b = 0; b + 1 < board.length(); b++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[j];
                    if (board.charAt(b) + board.charAt(b + 1) != hand.charAt(0) + hand.charAt(1) &&
                            board.charAt(b) + board.charAt(b + 1) != hand.charAt(2) + hand.charAt(3)) {
                        hands.add(hand);
                    }
                }
            }
        }

        return hands;
    }

    public List<String> buildSuitedGroupHands(String groupHand, String board) {
        List<String> hands = new ArrayList<>();

        for (int b = 0; b + 1 < board.length(); b++) {
            for (int i = 0; i < 4; i++) {
                String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[i];
                if (board.charAt(b) + board.charAt(b + 1) != hand.charAt(0) + hand.charAt(1) &&
                        board.charAt(b) + board.charAt(b + 1) != hand.charAt(2) + hand.charAt(3)) {
                    hands.add(hand);
                }
            }
        }

        return hands;
    }

    public List<String> buildOffsuitedGroupHands(String groupHand, String board) {
        List<String> hands = new ArrayList<>();

        for (int b = 0; b + 1 < board.length(); b++) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (suit[i] != suit[j]) {
                        String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[j];
                        if (board.charAt(b) + board.charAt(b + 1) != hand.charAt(0) + hand.charAt(1) &&
                                board.charAt(b) + board.charAt(b + 1) != hand.charAt(2) + hand.charAt(3)) {
                            hands.add(hand);
                        }
                    }
                }
            }
        }

        return hands;
    }

    public List<String> buildPocketPairGroupHands(String groupHand, String board) {
        List<String> hands = new ArrayList<>();

        for (int b = 0; b + 1 < board.length(); b++) {
            for (int i = 0; i < 4; i++) {
                for (int j = i + 1; j < 4; j++) {
                    String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[j];
                    if (board.charAt(b) + board.charAt(b + 1) != hand.charAt(0) + hand.charAt(1) &&
                            board.charAt(b) + board.charAt(b + 1) != hand.charAt(2) + hand.charAt(3)) {
                        hands.add(hand);
                    }
                }
            }
        }


        return hands;
    }

    public List<String> buildGroup(String[] rangeArr, String board) {
        List<String> hands = new ArrayList<>();

        String regex = "[AKQJT98765432akqjt][hdcs][AKQJT98765432akqjt][hdcs]" +
                "|[AKQJT98765432akqjt]{2}|[AKQJT98765432akqjt]{2}[os]";

        for (int i = 0; i < rangeArr.length; i++) {
            if (!rangeArr[i].matches(regex)){
                throw new InvalidInputCards("invalid format - " + rangeArr[i]);
            }
        }

        for (int i = 0; i < rangeArr.length; i++) {
            if (rangeArr[i].length() == 4) {
                hands.add(rangeArr[i]);
            }
            if (rangeArr[i].length() == 2 && rangeArr[i].charAt(0) == rangeArr[i].charAt(1)) {
                hands.addAll(buildPocketPairGroupHands(rangeArr[i], board));
            }
            if (rangeArr[i].length() == 2 && rangeArr[i].charAt(0) != rangeArr[i].charAt(1)) {
                hands.addAll(buildAllGroupHands(rangeArr[i], board));
            }
            if (rangeArr[i].length() == 3 && rangeArr[i].charAt(2) == 'o' &&
                    rangeArr[i].charAt(0) != rangeArr[i].charAt(1)) {
                hands.addAll(buildOffsuitedGroupHands(rangeArr[i], board));
            }
            if (rangeArr[i].length() == 3 && rangeArr[i].charAt(2) == 's' &&
                    rangeArr[i].charAt(0) != rangeArr[i].charAt(1)) {
                hands.addAll(buildSuitedGroupHands(rangeArr[i], board));
            }
        }

        return hands;
    }

    public List<String> buildRange(String input, String board) {
        List<String> groups = new ArrayList<>();

        String[] rangeArr = input.split(",");

        groups.addAll(buildGroup(rangeArr, board));

        return groups.stream().distinct().collect(Collectors.toList());
    }

    public void calculate(CalculatorMainTable calculatorMainTable) {
        String board = calculatorMainTable.getBoard();

        validateBoard(board);

        List<String> range1 = buildRange(calculatorMainTable.getRangePlayer1(), board);
        List<String> range2 = buildRange(calculatorMainTable.getRangePlayer2(), board);

        for (int i = 0; i < range1.size(); i++) {
            mapRange1.put(range1.get(i), 0.0);
        }
        for (int i = 0; i < range2.size(); i++) {
            mapRange2.put(range2.get(i), 0.0);
        }

        switch (calculatorMainTable.getBoard().length()) {
            case (6):
                calculateWithoutTurn(defaultData, range1, range2, board);
                break;
            case (8):
                calculateWithoutRiver(defaultData, range1, range2, board);
                break;
        }

        double sum1 = mapRange1.values().stream().mapToDouble(Double::doubleValue).sum();
        double sum2 = mapRange2.values().stream().mapToDouble(Double::doubleValue).sum();
        int count = range1.size() * range2.size();

        double equityP1 = sum1 / (countOfBoards * count);
        double equityP2 = sum2 / (countOfBoards * count);
        double deal = 1 - ((sum1 + sum2) / (countOfBoards * count));

        calculatorMainTable.setEquityPlayer1(new DecimalFormat("#0.0000").format(equityP1 + deal / 2));
        calculatorMainTable.setEquityPlayer2(new DecimalFormat("#0.0000").format(equityP2 + deal / 2));

        defaultData.getCalculatorMainTables().add(0, calculatorMainTable);

        countOfBoards = 0;
        mapRange1.clear();
        mapRange2.clear();
        allCards.clear();
    }

    private void validateBoard(String board) {
        if(board.length() > 8 || board.length() < 6 || board.length() == 7){
            throw new InvalidInputCards("invalid board - " + board);
        }
        String regex = "[AKQJT98765432akqjt][hdcs]";

        for (int i = 0; i + 2 < board.length(); i += 2) {
            if (!board.substring(i, i + 2).matches(regex)){
                throw new InvalidInputCards("invalid card in board - " + board.substring(i, i + 2));
            }
        }
    }

    public void calculateWithoutRiver(DefaultData defaultData,
                                      List<String> range1, List<String> range2, String board) {
        removeOpenCardsFromAllCards(board, defaultData.getCardsDeck());

        allCards.addAll(defaultData.getCardsDeck());

        for (int i = 0; i < 44; i++) {

            for (int j = 0; j < range1.size(); j++) {
                removeOpenCardsFromAllCards(range1.get(j), allCards);

                for (int k = 0; k < range2.size(); k++) {
                    removeOpenCardsFromAllCards(range2.get(k), allCards);

                    sumOfPoints = 0;
                    int p1 = evaluate(
                            board + allCards.get(i) + range1.get(j),
                            range1.get(j));

                    sumOfPoints = 0;
                    int p2 = evaluate(
                            board + allCards.get(i) + range2.get(k),
                            range2.get(k));

                    if (p1 > p2) {
                        double n = mapRange1.get(range1.get(j));
                        n += 1;
                        mapRange1.put(range1.get(j), n);
                    }
                    if (p1 < p2) {
                        double n = mapRange2.get(range2.get(k));
                        n += 1;
                        mapRange2.put(range2.get(k), n);
                    }
                    allCards.add(range2.get(k).substring(0, 2));
                    allCards.add(range2.get(k).substring(2, 4));
                }
                allCards.add(range1.get(j).substring(0, 2));
                allCards.add(range1.get(j).substring(2, 4));
            }
            countOfBoards++;
        }
    }


    public void calculateWithoutTurn(DefaultData defaultData,
                                     List<String> range1, List<String> range2, String board) {

        removeOpenCardsFromAllCards(board, defaultData.getCardsDeck());

        allCards.addAll(defaultData.getCardsDeck());

        for (int i = 0; i < 45; i++) {
            for (int i2 = i + 1; i2 < 45; i2++) {

                for (int j = 0; j < range1.size(); j++) {
                    removeOpenCardsFromAllCards(range1.get(j), allCards);

                    for (int k = 0; k < range2.size(); k++) {
                        removeOpenCardsFromAllCards(range2.get(k), allCards);

                        sumOfPoints = 0;
                        int p1 = evaluate(
                                board + allCards.get(i) + allCards.get(i2) + range1.get(j),
                                range1.get(j));

                        sumOfPoints = 0;
                        int p2 = evaluate(
                                board + allCards.get(i) + allCards.get(i2) + range2.get(k),
                                range2.get(k));

                        if (p1 > p2) {
                            double n = mapRange1.get(range1.get(j));
                            n += 1;
                            mapRange1.put(range1.get(j), n);
                        }
                        if (p1 < p2) {
                            double n = mapRange2.get(range2.get(k));
                            n += 1;
                            mapRange2.put(range2.get(k), n);
                        }
                        allCards.add(range2.get(k).substring(0, 2));
                        allCards.add(range2.get(k).substring(2, 4));
                    }
                    allCards.add(range1.get(j).substring(0, 2));
                    allCards.add(range1.get(j).substring(2, 4));
                }
                countOfBoards++;
            }
        }
    }

    public int evaluate(String board, String playerCards) {
        char[] openedCards = board.toCharArray();
        char[] copyOpenedCards = board.toCharArray();
        Arrays.sort(openedCards);

        String allKnowCardsAsString = String.valueOf(openedCards);
        allKnowCardsAsString = allKnowCardsAsString.substring(0, 7);

        evaluateCare(openedCards, copyOpenedCards, allKnowCardsAsString, playerCards);

        if (wasFindCombination == false) {
            evaluateFullHouse(openedCards, copyOpenedCards, allKnowCardsAsString, playerCards);
        }

        if (wasFindCombination == false) {
            evaluateStraightFlushOrFlush(openedCards, copyOpenedCards, allKnowCardsAsString, playerCards);
        }

        if (wasFindCombination == false) {
            evaluateStraight(copyOpenedCards, playerCards);
        }

        if (wasFindCombination == false) {
            evaluateSet(openedCards, copyOpenedCards, allKnowCardsAsString, playerCards);
        }

        if (wasFindCombination == false) {
            evaluateTwoPAirOrPAir(openedCards, copyOpenedCards, allKnowCardsAsString, playerCards);
        }

        if (wasFindCombination == false) {
            evaluateHighCard(copyOpenedCards, playerCards);
        }

        wasFindCombination = false;
        return sumOfPoints;
    }

    public void evaluateCare(char[] openedCards, char[] copyOpenedCards,
                             String allKnowCardsAsString, String playerCards) {
        for (int i = 0; i < openedCards.length; ) {
            if (allKnowCardsAsString.lastIndexOf(openedCards[i]) -
                    allKnowCardsAsString.indexOf(openedCards[i]) + 1 == 4) {

                kickersWithRank.clear();
                madeHandWithRank.clear();

                for (int j = 0; j < copyOpenedCards.length; j++) {
                    if (copyOpenedCards[j] == openedCards[i]) {

                        madeHandWithRank.put(
                                String.valueOf(copyOpenedCards[j]) +
                                        String.valueOf(copyOpenedCards[j + 1]),
                                defaultData.getCardsWithRank().get(String.valueOf(copyOpenedCards[j])));
                    } else {

                        kickersWithRank.put(
                                String.valueOf(copyOpenedCards[j]) +
                                        String.valueOf(copyOpenedCards[j + 1]),
                                defaultData.getCardsWithRank().get(String.valueOf(copyOpenedCards[j]))
                        );
                    }
                    j++;
                }

                kickersWithRank = sortReversedMap(kickersWithRank);

                madeHandWithRank.put(
                        kickersWithRank.keySet().toArray()[0].toString(),
                        (Integer) kickersWithRank.values().toArray()[0]
                );

                List<String> fourOfKind = getFinalMadeHand(madeHandWithRank);

                wasFindCombination = true;
                showResult("four of kind", fourOfKind, playerCards);
            }
            i += (allKnowCardsAsString.lastIndexOf(openedCards[i]) - allKnowCardsAsString.indexOf(openedCards[i]) + 1);
        }
    }

    public void evaluateFullHouse(char[] openedCards, char[] copyOpenedCards,
                                  String allKnowCardsAsString, String playerCards) {
        kickersWithRank.clear();
        madeHandWithRank.clear();

        if (firstFullHouseVariant(openedCards, copyOpenedCards, allKnowCardsAsString, playerCards) == false) {
            secondFullHouseVariant(playerCards);
        }
    }

    private boolean firstFullHouseVariant(char[] openedCards, char[] copyOpenedCards,
                                          String allKnowCardsAsString, String playerCards) {
        char[] threeCards = new char[6];
        int countOfThreeCard = 0;
        char[] twoCards = new char[6];
        int countOfTwoCard = 0;
        boolean finded = true;

        for (int i = 0; i < openedCards.length; ) {
            if (allKnowCardsAsString.lastIndexOf(openedCards[i]) -
                    allKnowCardsAsString.indexOf(openedCards[i]) + 1 == 2 ||
                    allKnowCardsAsString.lastIndexOf(openedCards[i]) -
                            allKnowCardsAsString.indexOf(openedCards[i]) + 1 == 3) {

                if (allKnowCardsAsString.lastIndexOf(openedCards[i]) -
                        allKnowCardsAsString.indexOf(openedCards[i]) + 1 == 3) {

                    threeCards[countOfThreeCard] = openedCards[i];
                    countOfThreeCard++;
                }
                if (allKnowCardsAsString.lastIndexOf(openedCards[i]) -
                        allKnowCardsAsString.indexOf(openedCards[i]) + 1 == 2) {

                    twoCards[countOfTwoCard] = openedCards[i];
                    countOfTwoCard++;
                }
            }
            i += (allKnowCardsAsString.lastIndexOf(openedCards[i]) - allKnowCardsAsString.indexOf(openedCards[i]) + 1);

            if (countOfThreeCard == 2) {

                for (int k = 0; k < 2; k++) {
                    for (int j = 0; j < copyOpenedCards.length; j++) {
                        if (copyOpenedCards[j] == threeCards[k]) {
                            madeHandWithRank.put(
                                    String.valueOf(copyOpenedCards[j]) +
                                            String.valueOf(copyOpenedCards[j + 1]),
                                    defaultData.getCardsWithRank().get(String.valueOf(copyOpenedCards[j]))
                            );
                        }
                        j++;
                    }
                }

                madeHandWithRank = sortReversedMap(madeHandWithRank);

                List<String> fullHouse = getFinalMadeHand(madeHandWithRank);
                fullHouse = fullHouse.stream().limit(5).collect(Collectors.toList());

                wasFindCombination = true;

                showResult("full house", fullHouse, playerCards);
            }

            if (countOfThreeCard == 1 && countOfTwoCard > 0) {
                finded = false;

                for (int k = 0; k < copyOpenedCards.length; k++) {
                    for (int j = 0; j < threeCards.length; j++) {
                        if (copyOpenedCards[k] == threeCards[j]) {
                            madeHandWithRank.put(
                                    String.valueOf(copyOpenedCards[k]) +
                                            String.valueOf(copyOpenedCards[k + 1]),
                                    defaultData.getCardsWithRank().get(String.valueOf(copyOpenedCards[k]))
                            );
                        }
                    }
                }
                for (int k = 0; k < copyOpenedCards.length; k++) {
                    for (int j = 0; j < twoCards.length; j++) {
                        if (copyOpenedCards[k] == twoCards[j]) {
                            kickersWithRank.put(
                                    String.valueOf(copyOpenedCards[k]) +
                                            String.valueOf(copyOpenedCards[k + 1]),
                                    defaultData.getCardsWithRank().get(String.valueOf(copyOpenedCards[k]))
                            );
                        }
                    }
                }

            }
        }

        return finded;
    }

    private void secondFullHouseVariant(String playerCards) {
        kickersWithRank = sortReversedMap(kickersWithRank);

        madeHandWithRank.put(
                kickersWithRank.keySet().toArray()[0].toString(),
                (Integer) kickersWithRank.values().toArray()[0]
        );
        madeHandWithRank.put(
                kickersWithRank.keySet().toArray()[1].toString(),
                (Integer) kickersWithRank.values().toArray()[1]
        );

        List<String> fullHouse = getFinalMadeHand(madeHandWithRank);

        wasFindCombination = true;

        showResult("full house", fullHouse, playerCards);
    }

    public void evaluateStraightFlushOrFlush(char[] openedCards, char[] copyOpenedCards,
                                             String allKnowCardsAsString, String playerCards) {
        kickersWithRank.clear();
        madeHandWithRank.clear();

        allKnowCardsAsString = String.valueOf(openedCards);
        allKnowCardsAsString = allKnowCardsAsString.substring(7, 14);

        getFiveSuited(defaultData.getCardsWithRank(), madeHandWithRank, openedCards, copyOpenedCards,
                allKnowCardsAsString);

        if (madeHandWithRank.size() > 4) {

            List<String> straightFlush = findStraight(madeHandWithRank);

            if (straightFlush.size() >= 5) {

                wasFindCombination = true;
                showResult("straight flush", straightFlush, playerCards);
            } else {
                madeHandWithRank = sortReversedMap(madeHandWithRank);

                List<String> flush = getFinalMadeHand(madeHandWithRank);
                flush = flush.stream().limit(5).collect(Collectors.toList());

                wasFindCombination = true;
                showResult("flush", flush, playerCards);
            }
        }
    }

    private void getFiveSuited(Map<String, Integer> cardMap, Map<String, Integer> madeHandWithRank, char[] allKnowCards, char[] copyAllKnowCards, String allKnowCardsAsString) {
        for (int i = 0; i < allKnowCards.length; ) {
            if (allKnowCardsAsString.lastIndexOf(allKnowCards[i]) - allKnowCardsAsString.indexOf(allKnowCards[i]) + 1 > 4) {
                for (int j = 0; j < copyAllKnowCards.length; j++) {
                    if (copyAllKnowCards[j] == allKnowCards[i]) {
                        madeHandWithRank.put(
                                String.valueOf(copyAllKnowCards[j - 1]) +
                                        String.valueOf(copyAllKnowCards[j]),
                                cardMap.get(String.valueOf(copyAllKnowCards[j - 1])));
                    }
                }
            }

            i += (allKnowCardsAsString.lastIndexOf(allKnowCards[i]) - allKnowCardsAsString.indexOf(allKnowCards[i]) + 1);
        }
    }

    public void evaluateStraight(char[] copyOpenedCards, String playerCards) {
        madeHandWithRank.clear();
        int i = 0;

        do {
            madeHandWithRank.put(
                    String.valueOf(copyOpenedCards[i]) +
                            String.valueOf(copyOpenedCards[i + 1]),
                    defaultData.getCardsWithRank().get(String.valueOf(copyOpenedCards[i])));
            i += 2;
        } while (i < copyOpenedCards.length - 1);

        List<String> straight = findStraight(madeHandWithRank);

        if (straight.size() >= 5) {
            wasFindCombination = true;
            showResult("straight", straight, playerCards);
        }
    }

    private List<String> findStraight(Map<String, Integer> cardWithRank) {
        List<String> straight = new LinkedList<>();

        List<Integer> straightInDigits = cardWithRank
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(p -> p.getValue())
                .collect(Collectors.toList());

        if (straightInDigits.contains(13)) {
            straightInDigits.add(straightInDigits.size(), 0);
        }

        straightInDigits = straightInDigits.stream().distinct().collect(Collectors.toList());

        int i = 0;
        do {
            if (straightInDigits.get(i) - straightInDigits.get(i + 1) == 1) {
                int card = straightInDigits.get(i);

                if (straightInDigits.get(i + 1) == 0) {
                    straight.add(cardWithRank.entrySet()
                            .stream()
                            .filter(p -> p.getValue() == card)
                            .map(Map.Entry::getKey)
                            .findAny().orElse(null));

                } else {
                    straight.add(cardWithRank.entrySet()
                            .stream()
                            .filter(p -> p.getValue() == card)
                            .map(Map.Entry::getKey)
                            .findFirst().orElse(null));
                }
                if (straight.size() == 4) {
                    int card2 = straightInDigits.get(i + 1);
                    straight.add(cardWithRank.entrySet()
                            .stream()
                            .filter(p -> p.getValue() == card2)
                            .map(Map.Entry::getKey)
                            .findFirst().orElse(null));
                }
            } else {
                if (straight.size() < 5) {
                    straight.clear();
                }
            }
            i++;
        } while (i < straightInDigits.size() - 1);

        if (straight.size() >= 5) {
            return straight.subList(0, 5);
        } else {
            return straight;
        }
    }

    public void evaluateSet(char[] openedCards, char[] copyOpenedCards,
                            String allKnowCardsAsString, String playerCards) {
        allKnowCardsAsString = allKnowCardsAsString.substring(0, 7);

        boolean findedSet = false;

        madeHandWithRank.clear();
        kickersWithRank.clear();

        for (int i = 0; i < openedCards.length; ) {
            if (allKnowCardsAsString.lastIndexOf(openedCards[i]) - allKnowCardsAsString.indexOf(openedCards[i]) + 1 == 3) {
                findedSet = true;
                for (int j = 0; j < copyOpenedCards.length; j++) {
                    if (copyOpenedCards[j] == openedCards[i]) {
                        madeHandWithRank.put(
                                String.valueOf(copyOpenedCards[j]) +
                                        String.valueOf(copyOpenedCards[j + 1]),
                                defaultData.getCardsWithRank().get(String.valueOf(copyOpenedCards[j])));
                    } else {
                        kickersWithRank.put(
                                String.valueOf(copyOpenedCards[j]) +
                                        String.valueOf(copyOpenedCards[j + 1]),
                                defaultData.getCardsWithRank().get(String.valueOf(copyOpenedCards[j])));
                    }
                    j++;
                }
            }

            i += (allKnowCardsAsString.lastIndexOf(openedCards[i]) - allKnowCardsAsString.indexOf(openedCards[i]) + 1);
        }

        if (findedSet == true) {
            kickersWithRank = sortReversedMap(kickersWithRank);

            madeHandWithRank.put(
                    kickersWithRank.keySet().toArray()[0].toString(),
                    (Integer) kickersWithRank.values().toArray()[0]
            );
            madeHandWithRank.put(
                    kickersWithRank.keySet().toArray()[1].toString(),
                    (Integer) kickersWithRank.values().toArray()[1]
            );

            List<String> set = getFinalMadeHand(madeHandWithRank);

            wasFindCombination = true;
            showResult("set", set, playerCards);
        }
    }

    public void evaluateTwoPAirOrPAir(char[] openedCards, char[] copyOpenedCards,
                                      String allKnowCardsAsString, String playerCards) {
        allKnowCardsAsString = allKnowCardsAsString.substring(0, 7);

        madeHandWithRank.clear();
        kickersWithRank.clear();

        searchPairCards(openedCards, copyOpenedCards, allKnowCardsAsString);

        if (madeHandWithRank.size() == 6) {
            List<String> twoPair = getFinalMadeHand(madeHandWithRank);

            twoPair.remove(5);
            twoPair.remove(4);

            filterTwoPairsAndAddKicker(kickersWithRank, twoPair);

            wasFindCombination = true;
            showResult("two pair", twoPair, playerCards);
        } else if (madeHandWithRank.size() == 4) {
            List<String> twoPair = getFinalMadeHand(madeHandWithRank);

            filterTwoPairsAndAddKicker(kickersWithRank, twoPair);

            wasFindCombination = true;
            showResult("two pair", twoPair, playerCards);
        } else if (madeHandWithRank.size() == 2) {
            madeOnePairFinalHand(playerCards);
        }
    }

    private static void filterTwoPairsAndAddKicker(Map<String, Integer> kickersWithRank, List<String> twoPairs) {
        for (int i = 0; i < twoPairs.size(); i++) {
            if (kickersWithRank.containsKey(twoPairs.get(i))) {
                kickersWithRank.remove(twoPairs.get(i));
            }
        }

        twoPairs.add(kickersWithRank.keySet().toArray()[0].toString());
    }

    private void searchPairCards(char[] openedCards, char[] copyOpenedCards, String allKnowCardsAsString) {
        for (int i = 0; i < openedCards.length; ) {
            if (allKnowCardsAsString.lastIndexOf(openedCards[i]) - allKnowCardsAsString.indexOf(openedCards[i]) + 1 == 2) {
                for (int j = 0; j < copyOpenedCards.length; j++) {
                    if (copyOpenedCards[j] == openedCards[i]) {
                        madeHandWithRank.put(
                                String.valueOf(copyOpenedCards[j]) +
                                        String.valueOf(copyOpenedCards[j + 1]),
                                defaultData.getCardsWithRank().get(String.valueOf(copyOpenedCards[j])));
                    } else {
                        kickersWithRank.put(
                                String.valueOf(copyOpenedCards[j]) +
                                        String.valueOf(copyOpenedCards[j + 1]),
                                defaultData.getCardsWithRank().get(String.valueOf(copyOpenedCards[j])));
                    }
                    j++;
                }
            }

            i += (allKnowCardsAsString.lastIndexOf(openedCards[i]) - allKnowCardsAsString.indexOf(openedCards[i]) + 1);
        }

        madeHandWithRank = sortReversedMap(madeHandWithRank);
        kickersWithRank = sortReversedMap(kickersWithRank);
    }

    private void madeOnePairFinalHand(String playerCards) {
        madeHandWithRank.put(
                kickersWithRank.keySet().toArray()[0].toString(),
                (Integer) kickersWithRank.values().toArray()[0]
        );
        madeHandWithRank.put(
                kickersWithRank.keySet().toArray()[1].toString(),
                (Integer) kickersWithRank.values().toArray()[1]
        );
        madeHandWithRank.put(
                kickersWithRank.keySet().toArray()[2].toString(),
                (Integer) kickersWithRank.values().toArray()[2]
        );

        List<String> onePair = getFinalMadeHand(madeHandWithRank);

        wasFindCombination = true;
        showResult("one pair", onePair, playerCards);
    }

    public void evaluateHighCard(char[] copyOpenedCards, String playerCards) {
        kickersWithRank.clear();
        madeHandWithRank.clear();

        int i = 0;

        do {
            madeHandWithRank.put(
                    String.valueOf(copyOpenedCards[i]) +
                            String.valueOf(copyOpenedCards[i + 1]),
                    defaultData.getCardsWithRank().get(String.valueOf(copyOpenedCards[i])));

            i += 2;
        } while (i < copyOpenedCards.length - 1);

        madeHandWithRank = sortReversedMap(madeHandWithRank);

        List<String> highCard = getFinalMadeHand(madeHandWithRank);
        highCard = highCard.stream().limit(5).collect(Collectors.toList());

        showResult("high card", highCard, playerCards);
    }


    public void removeOpenCardsFromAllCards(String cards, List<String> list) {
        int i = 0;

        do {
            list.remove(cards.substring(i, i + 2));
            i += 2;
        } while (i + 1 < cards.length());

    }

    private static LinkedHashMap<String, Integer> sortReversedMap(Map<String, Integer> finalResultWithPoints) {
        return finalResultWithPoints.entrySet()
                .parallelStream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> a,
                        LinkedHashMap::new));
    }

    private static List<String> getFinalMadeHand(Map<String, Integer> finalResultWithPoints) {
        return finalResultWithPoints
                .entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .map(p -> p.getKey())
                .collect(Collectors.toList());
    }

    private void finalCombinationPoints(List<String> hand, String combName, String player) {
        sumOfPoints = 0;

        for (int i = 0; i < hand.size(); i++) {
            sumOfPoints += defaultData.getCardsWithRank().get(String.valueOf(hand.get(i).charAt(0)));
        }

        sumOfPoints += defaultData.getCombinationWithPoints().get(combName);

        //   playersPoints.put(player, sumOfPoints);
    }

    private void showResult(String combination, List<String> hand, String player) {

        //    System.out.println(combination + " = " + hand);
        finalCombinationPoints(hand, combination, player);
    }


}
