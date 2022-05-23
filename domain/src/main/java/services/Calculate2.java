package services;

public class Calculate2 {

    /*
    private final DefaultData defaultData;

    private final Map<String, Integer> playersPoints = new HashMap<>(2);
    private Map<String, Integer> madeHandWithRank = new HashMap<>(7);
    private Map<String, Integer> kickersWithRank = new HashMap<>(7);
    private boolean wasFindCombination = false;
    private int countOfBoards;
    private int sumOfPoints;
    private static boolean isFlush;

    private final String[] suit = {"h", "d", "c", "s"};

    @Autowired
    public Calculate2(DefaultData defaultData) {
        this.defaultData = defaultData;
    }


    public List<Hand> buildAllGroupHands(String groupHand) {
        List<Hand> hands = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[j];
                hands.add(new Hand().buildHand(hand));
            }
        }

        return hands;
    }

    public List<Hand> buildSuitedGroupHands(String groupHand) {
        List<Hand> hands = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[i];
            hands.add(new Hand().buildHand(hand));
        }

        return hands;
    }

    public List<Hand> buildOffsuitedGroupHands(String groupHand) {
        List<Hand> hands = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (suit[i] != suit[j]) {
                    String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[j];
                    hands.add(new Hand().buildHand(hand));
                }
            }
        }

        return hands;
    }

    public List<Hand> buildPocketPairGroupHands(String groupHand) {
        List<Hand> hands = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[j];
                hands.add(new Hand().buildHand(hand));
            }
        }

        return hands;
    }

    public Hand buildHand(String groupHand) {
        return new Hand().buildHand(groupHand);
    }

    public Group buildGroup(String groupHand) {
        Group group = new Group();
        List<Hand> hands = new ArrayList<>();

        if (groupHand.length() == 4) {
            hands.add(buildHand(groupHand));
            group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(2));
        }
        if (groupHand.length() == 2 && groupHand.charAt(0) == groupHand.charAt(1)) {
            hands.addAll(buildPocketPairGroupHands(groupHand));
            group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(1));
        }
        if (groupHand.length() == 2 && groupHand.charAt(0) != groupHand.charAt(1)) {
            hands.addAll(buildAllGroupHands(groupHand));
            group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(1));
        }
        if (groupHand.length() == 3 && groupHand.charAt(2) == 'o' && groupHand.charAt(0) != groupHand.charAt(1)) {
            hands.addAll(buildOffsuitedGroupHands(groupHand));
            group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(1));
        }
        if (groupHand.length() == 3 && groupHand.charAt(2) == 's' && groupHand.charAt(0) != groupHand.charAt(1)) {
            hands.addAll(buildSuitedGroupHands(groupHand));
            group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(1));
        }

        group.setGroupHands(hands);

        return group;
    }

    public Range buildRange(String input) {
        Range range = new Range();
        List<Group> groups = new ArrayList<>();

        String[] rangeArr = input.split(",");

        for (int i = 0; i < rangeArr.length; i++) {
            Group group = buildGroup(rangeArr[i]);
            groups.add(group);
        }

        range.setRangeGroups(groups);

        return range;
    }

    public void calculate(CalculatorData calculatorData) {

        String cardsPlayer1 = calculatorData.getPlayer1().getRange();
        String cardsPlayer2 = calculatorData.getPlayer2().getRange();

        Range rangeP1 = buildRange(cardsPlayer1);
        Range rangeP2 = buildRange(cardsPlayer2);

        Range[] playersRanges = new Range[]{rangeP1, rangeP2};
        double[] wonTimes = new double[]{0, 0};

        playersPoints.put(cardsPlayer1, 0);
        playersPoints.put(cardsPlayer2, 0);

        switch (calculatorData.getBoard().length()) {
            case (0):
                //      calculateWithoutFlop(defaultData, playersRanges, wonTimes);
                break;
            case (6):
                calculateWithoutTurn(calculatorData, defaultData, playersRanges, wonTimes);
                break;
            case (8):
                calculateWithoutRiver(calculatorData, defaultData, playersRanges, wonTimes);
                break;
        }

        calculatorData.setEquityPlayer1(new DecimalFormat("#0.0000").format((wonTimes[0] / countOfBoards) + (wonTimes[2] / countOfBoards / 2)));
        calculatorData.setEquityPlayer2(new DecimalFormat("#0.0000").format((wonTimes[1] / countOfBoards) + (wonTimes[2] / countOfBoards / 2)));
        calculatorData.setDeal(new DecimalFormat("#0.0000").format(wonTimes[2] / countOfBoards));

        defaultData.getCalculatorMainTables().add(0, calculatorData);

        countOfBoards = 0;

    }

    public void calculateWithoutTurn(CalculatorData calculatorData,
                                     DefaultData defaultData, Range[] playersRanges,
                                     double[] wonTimes) {
        List<Card> allCards = new LinkedList<>();
        allCards.addAll(defaultData.getCardList());

        Card c1 = new Card(String.valueOf(
                calculatorData.getBoard().charAt(0)),
                String.valueOf(calculatorData.getBoard().charAt(1)));
        Card c2 = new Card(String.valueOf(
                calculatorData.getBoard().charAt(2)),
                String.valueOf(calculatorData.getBoard().charAt(3)));
        Card c3 = new Card(String.valueOf(
                calculatorData.getBoard().charAt(4)),
                String.valueOf(calculatorData.getBoard().charAt(5)));

        allCards.remove(c1);
        allCards.remove(c2);
        allCards.remove(c3);

        for (int j = 0; j < playersRanges[0].getRangeGroups().size(); j++) {
            for (int k = 0; k < playersRanges[0].getRangeGroups().get(j).getGroupHands().size(); k++) {

                for (int l = 0; l < playersRanges[1].getRangeGroups().size(); l++) {
                    for (int m = 0; m < playersRanges[1].getRangeGroups().get(m).getGroupHands().size(); m++) {

                        Hand hand1 = playersRanges[0].getRangeGroups().get(j).getGroupHands().get(k);
                        Hand hand2 = playersRanges[1].getRangeGroups().get(m).getGroupHands().get(m);

                        allCards.remove(hand1.getCard1());
                        allCards.remove(hand1.getCard2());
                        allCards.remove(hand2.getCard1());
                        allCards.remove(hand2.getCard2());

                        for (int i = 0; i < allCards.size(); i++) {

                            int countWonByCardP1 = 0;
                            int countWonByCardP2 = 0;

                            for (int i2 = i + 1; i2 < allCards.size(); i2++) {

                                Card[] sevenCardP1 = new Card[]{
                                        c1, c2, c3,
                                        hand1.getCard1(), hand1.getCard2(),
                                        allCards.get(i), allCards.get(i2)
                                };

                                Card[] sevenCardP2 = new Card[]{
                                        c1, c2, c3,
                                        hand2.getCard1(), hand2.getCard2(),
                                        allCards.get(i), allCards.get(i2)
                                };

                                int p1 = evaluate(sevenCardP1);
                                int p2 = evaluate(sevenCardP2);

                                if (p1 < p2) {
                                    //      hand1.setWonTimes(hand1.getWonTimes() + 1);
                                    countWonByCardP1++;
                                    wonTimes[0]++;
                                }

                                if (p1 > p2) {
                                    //         hand2.setWonTimes(hand1.getWonTimes() + 1);
                                    countWonByCardP2++;
                                    wonTimes[1]++;
                                }

                                countOfBoards++;
                            }

                            hand1.getEquityHandOnTurnCard().put(defaultData.getCardsDeck().get(i),
                                    (double) (countWonByCardP1 / defaultData.getCardsDeck().size()));
                            hand2.getEquityHandOnTurnCard().put(defaultData.getCardsDeck().get(i),
                                    (double) (countWonByCardP2 / defaultData.getCardsDeck().size()));
                        }
                    }
                }
            }
        }
    }


    public void calculateWithoutRiver(CalculatorData calculatorData,
                                      DefaultData defaultData, Range[] playersRanges,
                                      double[] wonTimes) {
        List<Card> allCards = new LinkedList<>();
        allCards.addAll(defaultData.getCardList());

        Card c1 = new Card(String.valueOf(
                calculatorData.getBoard().charAt(0)),
                String.valueOf(calculatorData.getBoard().charAt(1)));
        Card c2 = new Card(String.valueOf(
                calculatorData.getBoard().charAt(2)),
                String.valueOf(calculatorData.getBoard().charAt(3)));
        Card c3 = new Card(String.valueOf(
                calculatorData.getBoard().charAt(4)),
                String.valueOf(calculatorData.getBoard().charAt(5)));
        Card c4 = new Card(String.valueOf(
                calculatorData.getBoard().charAt(6)),
                String.valueOf(calculatorData.getBoard().charAt(7)));

        allCards.remove(c1);
        allCards.remove(c2);
        allCards.remove(c3);
        allCards.remove(c4);

        for (int j = 0; j < playersRanges[0].getRangeGroups().size(); j++) {
            for (int k = 0; k < playersRanges[0].getRangeGroups().get(j).getGroupHands().size(); k++) {

                for (int l = 0; l < playersRanges[1].getRangeGroups().size(); l++) {
                    for (int m = 0; m < playersRanges[1].getRangeGroups().get(m).getGroupHands().size(); m++) {

                        Hand hand1 = playersRanges[0].getRangeGroups().get(j).getGroupHands().get(k);
                        Hand hand2 = playersRanges[1].getRangeGroups().get(m).getGroupHands().get(m);

                        allCards.remove(hand1.getCard1());
                        allCards.remove(hand1.getCard2());
                        allCards.remove(hand2.getCard1());
                        allCards.remove(hand2.getCard2());

                        for (int i = 0; i < allCards.size(); i++) {

                            int countWonByCardP1 = 0;
                            int countWonByCardP2 = 0;

                            Card[] sevenCardP1 = new Card[]{
                                    c1, c2, c3, c4,
                                    hand1.getCard1(), hand1.getCard2(),
                                    allCards.get(i)
                            };

                            Card[] sevenCardP2 = new Card[]{
                                    c1, c2, c3, c4,
                                    hand2.getCard1(), hand2.getCard2(),
                                    allCards.get(i)
                            };

                            int p1 = evaluate(sevenCardP1);
                            int p2 = evaluate(sevenCardP2);

                            if (p1 < p2) {
                                //      hand1.setWonTimes(hand1.getWonTimes() + 1);
                                countWonByCardP1++;
                                wonTimes[0]++;
                            }

                            if (p1 > p2) {
                                //         hand2.setWonTimes(hand1.getWonTimes() + 1);
                                countWonByCardP2++;
                                wonTimes[1]++;
                            }

                            hand1.getEquityHandOnTurnCard().put(defaultData.getCardsDeck().get(i),
                                    (double) (countWonByCardP1 / defaultData.getCardsDeck().size()));
                            hand2.getEquityHandOnTurnCard().put(defaultData.getCardsDeck().get(i),
                                    (double) (countWonByCardP2 / defaultData.getCardsDeck().size()));
                        }
                        countOfBoards++;
                    }
                }
            }
        }
    }

    public int evaluate(Card[] sevenCards) {

        List<String> allVariants = generateAllFiveCombination(sevenCards);

        int result = 20220;

        for (int j = 0; j < allVariants.size(); j++) {
            int[] cardsArr = new int[5];
            int[] flushArr = new int[5];
            String combNumber = null;
            String flushNumber = null;

            int k = 0;
            String hand = allVariants.get(j).toLowerCase();
            for (int i = 0; i < hand.length(); i++) {
                if (i + 1 < hand.length()) {
                    String c1 = String.valueOf(hand.charAt(i));
                    String s1 = String.valueOf(hand.charAt(i + 1));
                    combNumber = defaultData.getPrimeNumber().get(c1);
                    flushNumber = defaultData.getSuitNumber().get(s1);
                    cardsArr[k] = Integer.parseInt(combNumber, 2);
                    flushArr[k] = Integer.parseInt(flushNumber, 2);
                    k++;
                    i++;
                }
            }

            int flushResult = flushArr[0] & flushArr[1] & flushArr[2] & flushArr[3] & flushArr[4];

            if (flushResult == 1) {
                isFlush = true;
            }

            int resultComb = cardsArr[0] * cardsArr[1] * cardsArr[2] * cardsArr[3] * cardsArr[4];

            int line = numberOfStringFromFile(String.valueOf(resultComb));

            if (line < result) {
                result = line;
            }
        }
        return result;
    }

    private List<String> generateAllFiveCombination(Card[] sevenCards) {
        List<String> variants = new ArrayList<>();

        for (int i = 0; i < sevenCards.length; i++) {
            for (int j = i + 1; j < sevenCards.length; j++) {
                for (int k = j + 1; k < sevenCards.length; k++) {
                    for (int l = k + 1; l < sevenCards.length; l++) {
                        for (int m = l + 1; m < sevenCards.length; m++) {
                            variants.add(
                                    sevenCards[i].getCard() +
                                            sevenCards[j].getCard() +
                                            sevenCards[k].getCard() +
                                            sevenCards[l].getCard() +
                                            sevenCards[m].getCard()
                            );
                        }
                    }
                }
            }
        }

        return variants;
    }


    static int numberOfStringFromFile(String str) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("G:\\JAVA\\GeekHub\\CourseWork\\equilator\\domain\\src\\main\\resources\\searchFile.txt"));
            String line = null;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                if (isFlush == false && i == 323) {
                    i = 1114;
                } else {
                    if (line.equals(str)) {
                        isFlush = false;
                        return i;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }










        /*




    }







    public void evaluate(String board, String playerCards) {
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
            i += (allKnowCardsAsString.lastIndexOf(openedCards[i]) - allKnowCardsAsString.indexOf(openedCards[i]) + 1);
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

    private static void getFiveSuited(Map<String, Integer> cardMap, Map<String, Integer> madeHandWithRank, char[] allKnowCards, char[] copyAllKnowCards, String allKnowCardsAsString) {
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

    private static List<String> findStraight(Map<String, Integer> cardWithRank) {
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

        playersPoints.put(player, sumOfPoints);
    }

    private void showResult(String combination, List<String> hand, String player) {

        //   System.out.println(combination + " = " + hand);
        finalCombinationPoints(hand, combination, player);
    }

         */



}
