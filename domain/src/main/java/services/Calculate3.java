package services;

public class Calculate3 {
    /*
    private final DefaultData defaultData;

    private final Map<String, Integer> playersPoints = new HashMap<>(2);
    private Map<String, Integer> madeHandWithRank = new HashMap<>(7);
    private Map<String, Integer> kickersWithRank = new HashMap<>(7);
    private boolean wasFindCombination = false;
    private int countOfBoards;
    private int sumOfPoints;
    private static boolean isFlush;

    public Calculate3(DefaultData defaultData) {
        this.defaultData = defaultData;
    }

    public void calculate(CalculatorData calculatorData) {

        String cardsPlayer1 = calculatorData.getRangePlayer1();
        String cardsPlayer2 = calculatorData.getRangePlayer2();

        double[] wonTimes = new double[]{0, 0, 0};

        playersPoints.put(cardsPlayer1, 0);
        playersPoints.put(cardsPlayer2, 0);

        switch (calculatorData.getBoard().length()) {
            case (0):
                //      calculateWithoutFlop(defaultData, playersRanges, wonTimes);
                break;
            case (6):
                //      calculateWithoutTurn(calculatorMainTable, defaultData, playersRanges, wonTimes);
                break;
            case (8):
                calculateWithoutRiver(calculatorData, defaultData, wonTimes);
                break;
        }

        calculatorData.setEquityPlayer1(new DecimalFormat("#0.0000").format((wonTimes[0] / countOfBoards) + (wonTimes[2] / countOfBoards / 2)));
        calculatorData.setEquityPlayer2(new DecimalFormat("#0.0000").format((wonTimes[1] / countOfBoards) + (wonTimes[2] / countOfBoards / 2)));
        calculatorData.setDeal(new DecimalFormat("#0.0000").format(wonTimes[2] / countOfBoards));

        defaultData.getCalculatorMainTables().add(0, calculatorData);

        countOfBoards = 0;

    }

    public void calculateWithoutRiver(CalculatorData calculatorData,
                                      DefaultData defaultData,
                                      double[] wonTimes) {

        String hand1 = calculatorData.getRangePlayer1();
        String hand2 = calculatorData.getRangePlayer2();
        String boardInput = calculatorData.getBoard();

        removeOpenCardsFromAllCards(hand1);
        removeOpenCardsFromAllCards(hand2);
        removeOpenCardsFromAllCards(boardInput);

        for (int i = 0; i < defaultData.getCardsDeck().size(); i++) {

            int p1 = evaluate(hand1 + boardInput + defaultData.getCardsDeck().get(i));
            int p2 = evaluate(hand2 + boardInput + defaultData.getCardsDeck().get(i));

            System.out.println(boardInput + defaultData.getCardsDeck().get(i));
            System.out.println(hand1);
            System.out.println(hand2);

            if (p1 < p2) {
                wonTimes[0]++;
            }

            if (p1 > p2) {
                wonTimes[1]++;
            }

            System.out.println(p1);
            System.out.println(p2);

            countOfBoards++;
        }
    }

    public int evaluate(String sevenCards) {

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

    private List<String> generateAllFiveCombination(String cards) {
        List<String> variants = new ArrayList<>();

        for (int i = 0; i + 1 < cards.length(); i++) {
            for (int j = i + 2; j + 1 < cards.length(); j++) {
                for (int k = j + 2; k + 1 < cards.length(); k++) {
                    for (int l = k + 2; l + 1 < cards.length(); l++) {
                        for (int m = l + 2; m + 1 < cards.length(); m++) {
                            variants.add(
                                    String.valueOf(
                                            cards.charAt(i)) + cards.charAt(i + 1) +
                                            cards.charAt(j) + cards.charAt(j + 1) +
                                            cards.charAt(k) + cards.charAt(k + 1) +
                                            cards.charAt(l) + cards.charAt(l + 1) +
                                            cards.charAt(m) + cards.charAt(m + 1)
                            );
                            m += 1;
                        }
                        l += 1;
                    }
                    k += 1;
                }
                j += 1;
            }
            i += 1;
        }

        return variants;
    }


    public void removeOpenCardsFromAllCards(String cards) {
        int i = 0;

        do {
            defaultData.getCardsDeck().remove(cards.substring(i, i + 2));
            i += 2;
        } while (i + 1 < cards.length());
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


     */

}
