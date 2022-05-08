package services;

public class FindCombination {
    /*
    private final DefaultData defaultData;
    private int sumOfPoints;
    private int countOfBoards;
    boolean wasFindCombination = false;
    private Map<String, Integer> playersPoints = new HashMap<>(2);
    private Map<String, Integer> madeHandWithRank = new LinkedHashMap<>(7);
    private Map<String, Integer> kickersWithRank = new LinkedHashMap<>(7);

    @Autowired
    public FindCombination(DefaultData defaultData) {
        this.defaultData = defaultData;
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

    public void removeOpenCardsFromAllCards(String cards) {
        int i = 0;

        do {
            defaultData.getCardsDeck().remove(cards.substring(i, i + 2));
            i += 2;
        } while (i + 1 < cards.length());

    }

    public void calculateWithoutFlop(DefaultData defaultData,String[] players,
                                     double[] wonTimes){
        for (int i = 0; i < defaultData.getCardsDeck().size(); i++) {
            for (int j = i + 1; j < defaultData.getCardsDeck().size(); j++) {
                for (int k = j + 1; k < defaultData.getCardsDeck().size(); k++) {
                    for (int l = k + 1; l < defaultData.getCardsDeck().size(); l++) {
                        for (int m = l + 1; m < defaultData.getCardsDeck().size(); m++) {

                            for (int n = 0; n < players.length; n++) {
                                String board =
                                        defaultData.getCardsDeck().get(i) +
                                                defaultData.getCardsDeck().get(j) +
                                                defaultData.getCardsDeck().get(k) +
                                                defaultData.getCardsDeck().get(l) +
                                                defaultData.getCardsDeck().get(m);

                                evaluate(board + players[n], players[n]);
                            }

                            incrementWonTimes(players, wonTimes);
                            countOfBoards++;
                        }
                    }
                }
            }
        }
    }

    public void calculateWithoutTurn(CalculatorMainTable calculatorMainTable,
                                     DefaultData defaultData,String[] players,
                                     double[] wonTimes){
        for (int i = 0; i < defaultData.getCardsDeck().size(); i++) {
            if (i + 1 != defaultData.getCardsDeck().size()){
                for (int j = i + 1; j < defaultData.getCardsDeck().size() ; j++) {

                    for (int n = 0; n < players.length; n++) {
                        String board =
                                defaultData.getCardsDeck().get(i) +
                                        defaultData.getCardsDeck().get(j);

                        removeOpenCardsFromAllCards(calculatorMainTable.getBoard());

                        evaluate(calculatorMainTable.getBoard() + board + players[n], players[n]);
                    }

                    incrementWonTimes(players, wonTimes);
                    countOfBoards++;
                }
            }
        }
    }

    public void calculateWithoutRiver(CalculatorMainTable calculatorMainTable,
                                     DefaultData defaultData,String[] players,
                                     double[] wonTimes){
        for (int i = 0; i < defaultData.getCardsDeck().size(); i++) {

            for (int n = 0; n < players.length; n++) {

                removeOpenCardsFromAllCards(calculatorMainTable.getBoard());

                evaluate(calculatorMainTable.getBoard() + defaultData.getCardsDeck().get(i) + players[n], players[n]);
            }

            incrementWonTimes(players, wonTimes);
            countOfBoards++;
        }
    }

    private void incrementWonTimes(String[] players, double[] wonTimes) {
        if (playersPoints.get(players[0]) > playersPoints.get(players[1])) {
            wonTimes[0]++;
        }
        if (playersPoints.get(players[0]) < playersPoints.get(players[1])) {
            wonTimes[1]++;
        }
    }


    public void calculate(CalculatorMainTable calculatorMainTable) {
        String cardsPlayer1 = calculatorMainTable.getRangePlayer1();
        String cardsPlayer2 = calculatorMainTable.getRangePlayer2();

        String[] players = new String[]{cardsPlayer1, cardsPlayer2};
        double[] wonTimes = new double[]{0, 0};

        playersPoints.put(cardsPlayer1, 0);
        playersPoints.put(cardsPlayer2, 0);

        removeOpenCardsFromAllCards(cardsPlayer1);
        removeOpenCardsFromAllCards(cardsPlayer2);

        switch (calculatorMainTable.getBoard().length()) {
            case (0):
                calculateWithoutFlop(defaultData, players, wonTimes);
                break;
            case (6):
                calculateWithoutTurn(calculatorMainTable, defaultData, players, wonTimes);
                break;
            case (8):
                calculateWithoutRiver(calculatorMainTable, defaultData, players, wonTimes);
                break;
        }

        calculatorMainTable.setEquityPlayer1(new DecimalFormat("#0.0000").format(wonTimes[0] / countOfBoards));
        calculatorMainTable.setEquityPlayer2(new DecimalFormat("#0.0000").format(wonTimes[1] / countOfBoards));

        defaultData.getCalculatorMainTables().add(0, calculatorMainTable);
    }

    public void evaluateCare(char[] openedCards, char[] copyOpenedCards,
                             String allKnowCardsAsString, String playerCards){
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
                             String allKnowCardsAsString, String playerCards){
        kickersWithRank.clear();
        madeHandWithRank.clear();

        if (firstFullHouseVariant(openedCards, copyOpenedCards, allKnowCardsAsString, playerCards) == false) {
            secondFullHouseVariant(playerCards);
        }
    }

    private boolean firstFullHouseVariant(char[] openedCards, char[] copyOpenedCards,
                                       String allKnowCardsAsString, String playerCards){
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

    private void secondFullHouseVariant(String playerCards){
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
                                  String allKnowCardsAsString, String playerCards){
        kickersWithRank.clear();
        madeHandWithRank.clear();

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

    public void evaluateStraight(char[] copyOpenedCards, String playerCards){
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

    public void evaluateSet(char[] openedCards, char[] copyOpenedCards,
                            String allKnowCardsAsString, String playerCards){
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
                            String allKnowCardsAsString, String playerCards){
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

    private void searchPairCards(char[] openedCards, char[] copyOpenedCards, String allKnowCardsAsString){
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

    private void madeOnePairFinalHand(String playerCards){
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

    public void evaluateHighCard(char[] copyOpenedCards, String playerCards){
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

        wasFindCombination = true;
        showResult("high card", highCard, playerCards);
    }

     */







}
