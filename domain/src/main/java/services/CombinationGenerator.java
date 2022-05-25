package services;

import models.calculator.GameInfo;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashMap;
import java.util.Map;

public class CombinationGenerator {
    private Map<String, Integer> setCombsTableTemplate() {
        Map<String, Integer> map = new LinkedHashMap<>();

        map.put("suited", 0);
        map.put("offsuited", 0);
        map.put("pocket pair", 0);
        map.put("A", 0);
        map.put("K", 0);
        map.put("Q", 0);
        map.put("J", 0);
        map.put("T", 0);
        map.put("9", 0);
        map.put("8", 0);
        map.put("7", 0);
        map.put("6", 0);
        map.put("5", 0);
        map.put("4", 0);
        map.put("3", 0);
        map.put("2", 0);

        return map;
    }

    private void setValues(String card, int combs) {
        int c = gameInfo.getCombsByCard().get(card);
        c += combs;
        gameInfo.getCombsByCard().put(card, c);
    }

    @Autowired
    private GameInfo gameInfo;

    public void generate(String range) {

        gameInfo.getCombsByCard().clear();
        gameInfo.getCombsByCard().putAll(setCombsTableTemplate());

        String[] splitRange = range.split(",");

        for (int i = 0; i < splitRange.length; i++) {
            if (splitRange[i].length() == 2) {
                setValues(String.valueOf(splitRange[i].charAt(0)), 6);

                setValues("pocket pair", 6);
            } else if (splitRange[i].charAt(2) == 's') {
                setValues(String.valueOf(splitRange[i].charAt(0)), 4);

                setValues(String.valueOf(splitRange[i].charAt(1)), 4);

                setValues("suited", 4);
            } else if (splitRange[i].charAt(2) == 'o') {
                setValues(String.valueOf(splitRange[i].charAt(0)), 12);

                setValues(String.valueOf(splitRange[i].charAt(1)), 12);

                setValues("offsuited", 12);
            }
        }
    }
}
