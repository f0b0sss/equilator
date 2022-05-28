package com.equilator.services;

import com.equilator.DAO.DefaultData;
import com.equilator.exceptions.InvalidInputCards;
import com.equilator.models.calculator.CalculatorMainTable;
import com.equilator.models.calculator.GameInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = {GameInfo.class, CalculatorMainTable.class, Calculate.class, DefaultData.class})
class CalculateTest {

    @Autowired
    CalculatorMainTable calculatorMainTable;
    @Autowired
    Calculate calculate;
    @Autowired
    GameInfo gameInfo;
    @Autowired
    DefaultData defaultData;

    @BeforeEach
    void setUp() {
        calculatorMainTable.setRangePlayer1(null);
        calculatorMainTable.setRangePlayer2(null);
        calculatorMainTable.setBoard(null);
        calculatorMainTable.setEquityPlayer1(null);
        calculatorMainTable.setEquityPlayer2(null);
    }

    @Test
    void calculate_throwInvalidInputCards_whenNoBoardNull() {
        calculatorMainTable.setRangePlayer1("66");
        calculatorMainTable.setRangePlayer2("77");

        assertThrows(InvalidInputCards.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                calculate.calculate(calculatorMainTable);
            }
        });
    }

    @Test
    void calculate_throwInvalidInputCards_whenBoardIsBlank() {
        calculatorMainTable.setBoard(" ");
        calculatorMainTable.setRangePlayer1("66");
        calculatorMainTable.setRangePlayer2("77");

        assertThrows(InvalidInputCards.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                calculate.calculate(calculatorMainTable);
            }
        });
    }

    @Test
    void calculate_throwInvalidInputCards_whenEnteredLessThenThreeCard() {
        calculatorMainTable.setBoard("AsKd");
        calculatorMainTable.setRangePlayer1("66");
        calculatorMainTable.setRangePlayer2("77");

        assertThrows(InvalidInputCards.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                calculate.calculate(calculatorMainTable);
            }
        });
    }

    @Test
    void calculate_throwInvalidInputCards_whenEnteredMoreThenFiveCard() {
        calculatorMainTable.setBoard("AsKd2h3d4h7c");
        calculatorMainTable.setRangePlayer1("66");
        calculatorMainTable.setRangePlayer2("77");

        assertThrows(InvalidInputCards.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                calculate.calculate(calculatorMainTable);
            }
        });
    }

    @Test
    void calculate_throwInvalidInputCards_whenEnteredIncorrectCardFormat() {
        calculatorMainTable.setBoard("AsKd2h3");
        calculatorMainTable.setRangePlayer1("66");
        calculatorMainTable.setRangePlayer2("77");

        assertThrows(InvalidInputCards.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                calculate.calculate(calculatorMainTable);
            }
        });
    }

    @Test
    void calculate_throwInvalidInputCards_whenOneOfPlayerRangeNull() {
        calculatorMainTable.setBoard("AsKd2h3h");
        calculatorMainTable.setRangePlayer1("66");

        assertThrows(InvalidInputCards.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                calculate.calculate(calculatorMainTable);
            }
        });
    }

    @Test
    void calculate_throwInvalidInputCards_whenOneOfPlayerRangeIncorrect() {
        calculatorMainTable.setBoard("AsKd2h3h");
        calculatorMainTable.setRangePlayer1("66");
        calculatorMainTable.setRangePlayer2("777");

        assertThrows(InvalidInputCards.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                calculate.calculate(calculatorMainTable);
            }
        });
    }

    @Test
    void calculate_throwInvalidInputCards_whenTwoBoardCardsEquals() {
        calculatorMainTable.setBoard("AsKd3h3h");
        calculatorMainTable.setRangePlayer1("66");
        calculatorMainTable.setRangePlayer2("77");

        assertThrows(InvalidInputCards.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                calculate.calculate(calculatorMainTable);
            }
        });
    }

    @Test
    void calculate_withFlop_playersHaveEquity_afterCalculated() {
        calculatorMainTable.setBoard("AsKd2h");
        calculatorMainTable.setRangePlayer1("66");
        calculatorMainTable.setRangePlayer2("77");

        assertTrue(calculatorMainTable.getEquityPlayer1() == null);
        assertTrue(calculatorMainTable.getEquityPlayer2() == null);

        calculate.calculate(calculatorMainTable);

        assertTrue(calculatorMainTable.getEquityPlayer1() != null);
        assertTrue(calculatorMainTable.getEquityPlayer2() != null);
    }

    @Test
    void calculate_withTurn_playersHaveEquity_afterCalculated() {
        calculatorMainTable.setBoard("AsKd2h3h");
        calculatorMainTable.setRangePlayer1("66");
        calculatorMainTable.setRangePlayer2("77");

        assertTrue(calculatorMainTable.getEquityPlayer1() == null);
        assertTrue(calculatorMainTable.getEquityPlayer2() == null);

        calculate.calculate(calculatorMainTable);

        assertTrue(calculatorMainTable.getEquityPlayer1() != null);
        assertTrue(calculatorMainTable.getEquityPlayer2() != null);

    }
    @Test
    void calculate_withRiver_playersHaveEquity_afterCalculated() {
        calculatorMainTable.setBoard("AsKd2h3h2d");
        calculatorMainTable.setRangePlayer1("66");
        calculatorMainTable.setRangePlayer2("77");

        assertTrue(calculatorMainTable.getEquityPlayer1() == null);
        assertTrue(calculatorMainTable.getEquityPlayer2() == null);

        calculate.calculate(calculatorMainTable);

        assertTrue(calculatorMainTable.getEquityPlayer1() != null);
        assertTrue(calculatorMainTable.getEquityPlayer2() != null);
    }

    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenHeHaveHighValueOfHighCard() {
        calculatorMainTable.setBoard("AsTd9c3s4h");
        calculatorMainTable.setRangePlayer1("KhQs");
        calculatorMainTable.setRangePlayer2("KhJs");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }

    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenP1HaveBottomPairAndP2HaveHighCard() {
        calculatorMainTable.setBoard("AsTd9c3s4h");
        calculatorMainTable.setRangePlayer1("5h3d");
        calculatorMainTable.setRangePlayer2("KhJs");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }

    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenP1HaveThirdPairAndP2HaveBottomPair() {
        calculatorMainTable.setBoard("AsTd9c3s8h");
        calculatorMainTable.setRangePlayer1("4h4c");
        calculatorMainTable.setRangePlayer2("3c5d");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }

    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenP1HaveTwoPairAndP2HaveLowTwoPairOnFlop() {
        calculatorMainTable.setBoard("AsTd9c5h4h");
        calculatorMainTable.setRangePlayer1("Ad9s");
        calculatorMainTable.setRangePlayer2("Tc9s");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }

    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenP1HaveTripsAndP2HaveTwoPairOnFlop() {
        calculatorMainTable.setBoard("AsTd9c2h3c");
        calculatorMainTable.setRangePlayer1("9h9d");
        calculatorMainTable.setRangePlayer2("Th9s");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }

    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenP1HaveStraightAndP2HaveLowStraight() {
        calculatorMainTable.setBoard("AsTd9cQc8s");
        calculatorMainTable.setRangePlayer1("KhJs");
        calculatorMainTable.setRangePlayer2("Jh9s");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }

    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenP1HaveStraightAndP2HaveLowStraightVariant2() {
        calculatorMainTable.setBoard("2s3d4c5sTh");
        calculatorMainTable.setRangePlayer1("Kh6s");
        calculatorMainTable.setRangePlayer2("Ah2d");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }

    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenP1HaveFlushAndP2HaveLowFlush() {
        calculatorMainTable.setBoard("Ts3s4s5dKd");
        calculatorMainTable.setRangePlayer1("As6s");
        calculatorMainTable.setRangePlayer2("Ks8s");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }

    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenP1HaveFullAndP2HaveFlush() {
        calculatorMainTable.setBoard("Ts4s4h2dQs");
        calculatorMainTable.setRangePlayer1("2h2c");
        calculatorMainTable.setRangePlayer2("AsKs");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }

    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenP1HaveCareAndP2HaveFullHouse() {
        calculatorMainTable.setBoard("AhKd2s2dKh");
        calculatorMainTable.setRangePlayer1("2h2c");
        calculatorMainTable.setRangePlayer2("AsKs");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }
    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenP1HaveStraightFlushAndP2HaveFlush() {
        calculatorMainTable.setBoard("3c4c5c9hTh");
        calculatorMainTable.setRangePlayer1("6c2c");
        calculatorMainTable.setRangePlayer2("AcKc");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }
    @Test
    void calculate_player1HaveHighEquityThenPlayer2_whenP1HaveStraightFlushAndP2HaveLowStraightFlush() {
        calculatorMainTable.setBoard("3c4c5c9hTh");
        calculatorMainTable.setRangePlayer1("6c7c");
        calculatorMainTable.setRangePlayer2("Ac2c");

        calculate.calculate(calculatorMainTable);

        double eqP1 = Double.parseDouble(calculatorMainTable.getEquityPlayer1().replace(",", "."));
        double eqP2 = Double.parseDouble(calculatorMainTable.getEquityPlayer2().replace(",", "."));

        assertTrue(eqP1 > eqP2);
    }
}