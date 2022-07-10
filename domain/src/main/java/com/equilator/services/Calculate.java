package com.equilator.services;

import com.equilator.DAO.DefaultData;
import com.equilator.exceptions.InvalidInputCards;
import com.equilator.models.calculator.CalculatorMainTable;
import com.equilator.models.calculator.GameInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Calculate {
    private static Logger logger = LogManager.getLogger(Calculate.class);

    private final DefaultData defaultData;

    @Autowired
    public Calculate(DefaultData defaultData) {
        this.defaultData = defaultData;
    }

    @Autowired
    private GameInfo gameInfo;

    private final String[] cards = {"2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
    private final String[] suit = {"h", "d", "c", "s"};

    private Map<String, Integer> madeHandWithRank = new HashMap<>(7);
    private Map<String, Integer> kickersWithRank = new HashMap<>(7);
    private boolean wasFindCombination = false;
    private int sumOfPoints;

    private List<String> allCards = new LinkedList<>();

    private Map<String, Double> wonTimesByCardP1 = new TreeMap<>();
    private Map<String, Double> wonTimesByCardP2 = new TreeMap<>();
    private Map<String, Double> wonTimesByRangeP1 = new TreeMap<>();
    private Map<String, Double> wonTimesByRangeP2 = new TreeMap<>();
    private Map<String, Integer> timesByCard = new TreeMap<>();

    private List<String> buildAllGroupHands(String groupHand, String board) {
        logger.debug("buildAllGroupHands");
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

    private List<String> buildSuitedGroupHands(String groupHand, String board) {
        logger.debug("buildSuitedGroupHands");
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

    private List<String> buildOffsuitedGroupHands(String groupHand, String board) {
        logger.debug("buildOffsuitedGroupHands");
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

    private List<String> buildPocketPairGroupHands(String groupHand, String board) {
        logger.debug("buildPocketPairGroupHands");
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

    private List<String> buildGroup(String[] rangeArr, String board) {
        logger.debug("buildGroup");
        List<String> hands = new ArrayList<>();

        String regex = "[AKQJT98765432akqjt][hdcs][AKQJT98765432akqjt][hdcs]" +
                "|[AKQJT98765432akqjt]{2}|[AKQJT98765432akqjt]{2}[os]";

        for (int i = 0; i < rangeArr.length; i++) {
            if (!rangeArr[i].matches(regex)) {
                throw new InvalidInputCards("invalid range format - " + rangeArr[i]);
            }
        }
        for (int i = 0; i < rangeArr.length; i++) {
            if (rangeArr[i].length() == 4) {
                if (rangeArr[i].charAt(0) == rangeArr[i].charAt(2) &&
                        rangeArr[i].charAt(1) == rangeArr[i].charAt(3)) {
                    throw new InvalidInputCards("invalid range format - " + rangeArr[i]);
                }
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

    private List<String> buildRange(String range, String board) {
        logger.debug("Build range");
        List<String> groups = new ArrayList<>();

        if (range == null){
            throw new InvalidInputCards("Select range");
        }

        String[] rangeArr = range.split(",");

        groups.addAll(buildGroup(rangeArr, board));

        return groups.stream().distinct().collect(Collectors.toList());
    }

    private void buildWonTimesByCardMap() {
        logger.debug("buildWonTimesByCardMap");
        for (int i = 0; i < suit.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                wonTimesByCardP1.put(cards[j] + suit[i], 0.0);
                wonTimesByCardP2.put(cards[j] + suit[i], 0.0);
                timesByCard.put(cards[j] + suit[i], 0);
            }
        }
    }

    private void deleteFromRangeSameCardWithBoard(List<String> range, String board,
                                                  Map<String, Double> wonTimesByRange) {
        logger.debug("deleteFromRangeSameCardWithBoard");
        for (int i = 0; i < range.size(); ) {
            if (board.contains(range.get(i).substring(0, 2)) || board.contains(range.get(i).substring(2, 4))) {
                range.remove(range.get(i));
            } else {
                wonTimesByRange.put(range.get(i), 0.0);
                i++;
            }
        }
    }

    public void calculate(CalculatorMainTable calculatorMainTable) {
        String board = calculatorMainTable.getBoard();

        validateBoard(board);

        List<String> range1 = buildRange(calculatorMainTable.getRangePlayer1(), board);
        List<String> range2 = buildRange(calculatorMainTable.getRangePlayer2(), board);

        deleteFromRangeSameCardWithBoard(range1, board, wonTimesByRangeP1);
        deleteFromRangeSameCardWithBoard(range2, board, wonTimesByRangeP2);

        buildWonTimesByCardMap();

        gameInfo.getEquityByRangeP1().clear();
        gameInfo.getEquityByRangeP2().clear();
        gameInfo.getEquityByCardP1().clear();
        gameInfo.getEquityByCardP2().clear();

        switch (calculatorMainTable.getBoard().length()) {
            case (6):
                logger.debug("select calculate with flop cards");
                calculateWithoutTurn(defaultData, range1, range2, board);
                break;
            case (8):
                logger.debug("select calculate with turn cards");
                calculateWithoutRiver(defaultData, range1, range2, board);
                break;
            case (10):
                logger.debug("select calculate with river cards");
                calculateWithRiver(range1, range2, board);
                break;
        }

        double equityP1 = wonTimesByRangeP1.values().stream().mapToDouble(Double::doubleValue).sum() / range1.size();
        double equityP2 = wonTimesByRangeP2.values().stream().mapToDouble(Double::doubleValue).sum() / range2.size();

        double deal = 1 - equityP2 - equityP1;

        calculatorMainTable.setEquityPlayer1(new DecimalFormat("#0.0000").format(equityP1 + deal / 2));
        calculatorMainTable.setEquityPlayer2(new DecimalFormat("#0.0000").format(equityP2 + deal / 2));

        defaultData.getCalculatorMainTables().add(0, calculatorMainTable);

        allCards.clear();
        wonTimesByCardP1.clear();
        wonTimesByCardP2.clear();
        wonTimesByRangeP1.clear();
        wonTimesByRangeP2.clear();
        timesByCard.clear();


        /*

        Thread startCalcThread = new Thread(() -> {
            System.out.println("start 1");
            switch (calculatorMainTable.getBoard().length()) {
                case (6):
                    logger.debug("select calculate with flop cards");
                    calculateWithoutTurn(defaultData, range1, range2, board);
                    break;
                case (8):
                    logger.debug("select calculate with turn cards");
                    calculateWithoutRiver(defaultData, range1, range2, board);
                    break;
                case (10):
                    logger.debug("select calculate with river cards");
                    calculateWithRiver(range1, range2, board);
                    break;
            }
            System.out.println("end 1");
        });

        Thread showResultThread = new Thread(() -> {
            System.out.println("start 2");
            while (startCalcThread.isAlive()){
                double equityP1 = wonTimesByRangeP1.values().stream().mapToDouble(Double::doubleValue).sum() / range1.size();
                double equityP2 = wonTimesByRangeP2.values().stream().mapToDouble(Double::doubleValue).sum() / range2.size();

                double deal = 1 - equityP2 - equityP1;

                calculatorMainTable.setEquityPlayer1(new DecimalFormat("#0.0000").format(equityP1 + deal / 2));
                calculatorMainTable.setEquityPlayer2(new DecimalFormat("#0.0000").format(equityP2 + deal / 2));

                defaultData.getCalculatorMainTables().add(0, calculatorMainTable);
                System.out.println("end 2");
            }
        });

        try {
            startCalcThread.start();
            showResultThread.start();
            startCalcThread.join();
            showResultThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (startCalcThread.isInterrupted()){
            allCards.clear();
            wonTimesByCardP1.clear();
            wonTimesByCardP2.clear();
            wonTimesByRangeP1.clear();
            wonTimesByRangeP2.clear();
            timesByCard.clear();
        }

         */
    }

    private void validateBoard(String board) {
        logger.debug("Validate board");
        List<String> list = new ArrayList<>();

        if (board == null || board.isEmpty() || board.isBlank()) {
            throw new InvalidInputCards("You must enter board");
        }
        if (board.length() > 10 || board.length() < 6 || board.length() % 2 != 0) {
                 throw new InvalidInputCards("Invalid board format, enter 3 - 5 cards");
        }
        String regex = "[AKQJT98765432akqjt][hdcs]";

        for (int i = 0; i < board.length(); i += 2) {

            if (!board.substring(i, i + 2).matches(regex)) {
                throw new InvalidInputCards("invalid card in board - " + board.substring(i, i + 2));
            }
            list.add(board.substring(i, i + 2));
        }

        if (list.stream().distinct().collect(Collectors.toList()).size() * 2 != board.length()){
            throw new InvalidInputCards("You entered two same cards");
        }
    }

    private void calculateWithoutTurn(DefaultData defaultData,
                                     List<String> range1, List<String> range2, String board) {

        allCards.addAll(defaultData.getCardsDeck());

        removeOpenCardsFromAllCards(board, allCards);

        logger.debug("start calculation with flop");

        for (int j = 0; j < range1.size(); j++) {
            for (int k = 0; k < range2.size(); k++) {

                for (int i = 0; i < allCards.size(); i++) {
                    for (int i2 = 0; i2 < allCards.size(); i2++) {

                        if (!allCards.get(i).equals(allCards.get(i2))) {
                            if (!range1.get(j).contains(allCards.get(i)) &&
                                    !range2.get(k).contains(allCards.get(i)) &&
                                    !range1.get(j).contains(allCards.get(i2)) &&
                                    !range2.get(k).contains(allCards.get(i2))
                            ) {
                                int p1 = evaluate(
                                        board + allCards.get(i) + allCards.get(i2) + range1.get(j),
                                        range1.get(j));

                                int p2 = evaluate(
                                        board + allCards.get(i) + allCards.get(i2) + range2.get(k),
                                        range2.get(k));

                                if (p1 > p2) {
                                    double n = wonTimesByCardP1.get(allCards.get(i));
                                    n += 1;
                                    wonTimesByCardP1.put(allCards.get(i), n);

                                    n = wonTimesByRangeP1.get(range1.get(j));
                                    n += 1;
                                    wonTimesByRangeP1.put(range1.get(j), n);
                                }
                                if (p1 < p2) {
                                    double n = wonTimesByCardP2.get(allCards.get(i));
                                    n += 1;
                                    wonTimesByCardP2.put(allCards.get(i), n);

                                    n = wonTimesByRangeP2.get(range2.get(k));
                                    n += 1;
                                    wonTimesByRangeP2.put(range2.get(k), n);
                                }

                                int t = timesByCard.get(allCards.get(i));
                                t += 1;
                                timesByCard.put(allCards.get(i), t);
                            }
                        }
                    }
                }
            }
        }
        buildEquityByCard();
        buildEquityByRange(wonTimesByRangeP1, range1, range2.size(), 45 * 44, gameInfo.getEquityByRangeP1());
        buildEquityByRange(wonTimesByRangeP2, range2, range1.size(), 45 * 44, gameInfo.getEquityByRangeP2());
    }

    private void calculateWithoutRiver(DefaultData defaultData,
                                      List<String> range1, List<String> range2, String board) {
        allCards.addAll(defaultData.getCardsDeck());

        removeOpenCardsFromAllCards(board, allCards);

        logger.debug("start calculation with turn");

        for (int j = 0; j < range1.size(); j++) {
            for (int k = 0; k < range2.size(); k++) {
                for (int i = 0; i < allCards.size(); i++) {
                    if (!range1.get(j).contains(allCards.get(i)) &&
                            !range2.get(k).contains(allCards.get(i))
                    ) {
                        int p1 = evaluate(
                                board + allCards.get(i) + range1.get(j),
                                range1.get(j));

                        int p2 = evaluate(
                                board + allCards.get(i) + range2.get(k),
                                range2.get(k));

                        if (p1 > p2) {

                            double n = wonTimesByCardP1.get(allCards.get(i));
                            n += 1;
                            wonTimesByCardP1.put(allCards.get(i), n);

                            n = wonTimesByRangeP1.get(range1.get(j));
                            n += 1;
                            wonTimesByRangeP1.put(range1.get(j), n);
                        }
                        if (p1 < p2) {
                            double n = wonTimesByCardP2.get(allCards.get(i));
                            n += 1;
                            wonTimesByCardP2.put(allCards.get(i), n);

                            n = wonTimesByRangeP2.get(range2.get(k));
                            n += 1;
                            wonTimesByRangeP2.put(range2.get(k), n);
                        }

                        int t = timesByCard.get(allCards.get(i));
                        t += 1;
                        timesByCard.put(allCards.get(i), t);
                    }
                }
            }
        }
        buildEquityByCard();
        buildEquityByRange(wonTimesByRangeP1, range1, range2.size(), 44, gameInfo.getEquityByRangeP1());
        buildEquityByRange(wonTimesByRangeP2, range2, range1.size(), 44, gameInfo.getEquityByRangeP2());
    }

    private void calculateWithRiver(List<String> range1, List<String> range2, String board) {
        logger.debug("start calculation with river");

        for (int j = 0; j < range1.size(); j++) {
            for (int k = 0; k < range2.size(); k++) {

                int p1 = evaluate(
                        board + range1.get(j),
                        range1.get(j));

                int p2 = evaluate(
                        board + range2.get(k),
                        range2.get(k));

                if (p1 > p2) {
                    double n = wonTimesByRangeP1.get(range1.get(j));
                    n += 1;
                    wonTimesByRangeP1.put(range1.get(j), n);
                }
                if (p1 < p2) {
                    double n = wonTimesByRangeP2.get(range2.get(k));
                    n += 1;
                    wonTimesByRangeP2.put(range2.get(k), n);
                }
            }
        }
        buildEquityByRange(wonTimesByRangeP1, range1, range2.size(), 1, gameInfo.getEquityByRangeP1());
        buildEquityByRange(wonTimesByRangeP2, range2, range1.size(), 1, gameInfo.getEquityByRangeP2());
    }


    private void buildEquityByCard() {
        for (int i = 0; i < allCards.size(); i++) {
            double eq1 = wonTimesByCardP1.get(allCards.get(i)) / timesByCard.get(allCards.get(i));
            double eq2 = wonTimesByCardP2.get(allCards.get(i)) / timesByCard.get(allCards.get(i));
            double deal = 1 - eq1 - eq2;

            gameInfo.getEquityByCardP1().put(allCards.get(i), String.format("%.2f", eq1 + deal / 2));
            gameInfo.getEquityByCardP2().put(allCards.get(i), String.format("%.2f", eq2 + deal / 2));
        }
    }

    private void buildEquityByRange(Map<String, Double> wonTimesByRange, List<String> range,
                                    int rangeSizeVS, int numberOfBoards, Map<String, String> equityByRange) {
        for (int i = 0; i < range.size(); i++) {
            double eq = wonTimesByRange.get(range.get(i)) / (numberOfBoards * rangeSizeVS);
            wonTimesByRange.put(range.get(i), eq);
            equityByRange.put(range.get(i), String.format("%.2f", eq));
        }
    }

    private int evaluate(String board, String playerCards) {
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

    private void evaluateCare(char[] openedCards, char[] copyOpenedCards,
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

    private void evaluateFullHouse(char[] openedCards, char[] copyOpenedCards,
                                  String allKnowCardsAsString, String playerCards) {
        kickersWithRank.clear();
        madeHandWithRank.clear();

        if (!firstFullHouseVariant(openedCards, copyOpenedCards, allKnowCardsAsString, playerCards)) {
            secondFullHouseVariant(playerCards);
        }
    }

    private boolean firstFullHouseVariant(char[] openedCards, char[] copyOpenedCards,
                                          String allKnowCardsAsString, String playerCards) {
        char[] threeCards = new char[2];
        int countOfThreeCard = 0;
        char[] twoCards = new char[3];
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
        }

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

            for (int k = 0; k + 1 < copyOpenedCards.length; k++) {
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
            for (int k = 0; k + 1 < copyOpenedCards.length; k++) {
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

    private void evaluateStraightFlushOrFlush(char[] openedCards, char[] copyOpenedCards,
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

    private void evaluateStraight(char[] copyOpenedCards, String playerCards) {
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
                    straight.add(cardWithRank.entrySet()
                            .stream()
                            .filter(p -> p.getValue() == 13)
                            .map(Map.Entry::getKey)
                            .findAny().orElse(null));
                } else {
                    straight.add(cardWithRank.entrySet()
                            .stream()
                            .filter(p -> p.getValue() == card)
                            .map(Map.Entry::getKey)
                            .findFirst().orElse(null));
                }
                if (straight.size() == 4 && straight.get(3).charAt(0) != ('A')) {
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

    private void evaluateSet(char[] openedCards, char[] copyOpenedCards,
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

    private void evaluateTwoPAirOrPAir(char[] openedCards, char[] copyOpenedCards,
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

    private void evaluateHighCard(char[] copyOpenedCards, String playerCards) {
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


    private void removeOpenCardsFromAllCards(String cards, List<String> list) {
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
        int key = 100;

        if (combName.equals("straight") || combName.equals("straight flush") && hand.get(0).charAt(0) == '5'){
            for (int i = 0; i < hand.size(); i++) {
                if (i == hand.size() - 1){
                    sumOfPoints += 0;
                }else {
                    sumOfPoints += key * defaultData.getCardsWithRank().get(String.valueOf(hand.get(i).charAt(0)));
                    key /= 2;
                }
            }
        } else {
            for (int i = 0; i < hand.size(); i++) {
                sumOfPoints += key * defaultData.getCardsWithRank().get(String.valueOf(hand.get(i).charAt(0)));
                key /= 2;
            }
        }
        sumOfPoints += defaultData.getCombinationWithPoints().get(combName);
    }

    private void showResult(String combination, List<String> hand, String player) {

   //     System.out.println(combination + " = " + hand);
        finalCombinationPoints(hand, combination, player);
    }


}
