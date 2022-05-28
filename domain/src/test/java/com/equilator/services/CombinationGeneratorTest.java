package com.equilator.services;

import com.equilator.models.calculator.GameInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {GameInfo.class, CombinationGenerator.class})
class CombinationGeneratorTest {

    @Autowired
    GameInfo gameInfo;
    @Autowired
    CombinationGenerator combinationGenerator;

    @Test
    void generate_getSixCombs_whenTwoCardsEquel() {
        String hand = "22";

        combinationGenerator.generate(hand);

        assertEquals(6, gameInfo.getCombsByCard().get("2"));
        assertEquals(6, gameInfo.getCombsByCard().get("pocket pair"));
    }

    @Test
    void generate_getFourSuitedCombs_whenCardsSuited() {
        String hand = "AKs";

        combinationGenerator.generate(hand);

        assertEquals(4, gameInfo.getCombsByCard().get("A"));
        assertEquals(4, gameInfo.getCombsByCard().get("K"));
        assertEquals(4, gameInfo.getCombsByCard().get("suited"));
    }

    @Test
    void generate_getTwelveOffsuitedCombs_whenCardsOffsuited() {
        String hand = "AKo";

        combinationGenerator.generate(hand);

        assertEquals(12, gameInfo.getCombsByCard().get("A"));
        assertEquals(12, gameInfo.getCombsByCard().get("K"));
        assertEquals(12, gameInfo.getCombsByCard().get("offsuited"));
    }

    @Test
    void generate_SummCombs_whenCardInRAngeWithSameValue() {
        String hand = "AKo,KQs,A2s,22,KTo";

        combinationGenerator.generate(hand);

        assertEquals(24, gameInfo.getCombsByCard().get("offsuited"));
        assertEquals(8, gameInfo.getCombsByCard().get("suited"));
        assertEquals(6, gameInfo.getCombsByCard().get("pocket pair"));
        assertEquals(16, gameInfo.getCombsByCard().get("A"));
        assertEquals(28, gameInfo.getCombsByCard().get("K"));
        assertEquals(12, gameInfo.getCombsByCard().get("T"));
        assertEquals(10, gameInfo.getCombsByCard().get("2"));
    }

}