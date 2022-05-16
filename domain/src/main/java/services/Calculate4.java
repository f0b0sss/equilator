package services;

import codes.derive.foldem.Hand;
import codes.derive.foldem.tool.EquityCalculationBuilder;

import java.util.Map;

import static codes.derive.foldem.Poker.*;
import static codes.derive.foldem.Poker.format;

public class Calculate4 {

    public static void main(String[] args) {
        Hand QQ = hand("QsQh");
        Hand AKo = hand("AsKd");

        Map<Hand, EquityCalculationBuilder.Equity> equities = equity(QQ, AKo);

        System.out.println(QQ + ": " + format(equities.get(QQ)));
        System.out.println(AKo + ": " + format(equities.get(AKo)));

    }

}
