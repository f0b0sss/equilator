package com.equilator.models.calculator;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameInfo {

    private Map<String, String> equityByCardP1 = new LinkedHashMap<>(52);
    private Map<String, String> equityByCardP2  = new LinkedHashMap<>(52);

    private Map<String, String> equityByRangeP1 = new LinkedHashMap<>();
    private Map<String, String> equityByRangeP2  = new LinkedHashMap<>();

    private Map<String, Integer> combsByCard  = new LinkedHashMap<>(16);




    public Map<String, Integer> getCombsByCard() {
        return combsByCard;
    }

    public Map<String, String> getEquityByCardP1() {
        return equityByCardP1;
    }

    public Map<String, String> getEquityByCardP2() {
        return equityByCardP2;
    }

    public Map<String, String> getEquityByRangeP1() {
        return equityByRangeP1;
    }

    public Map<String, String> getEquityByRangeP2() {
        return equityByRangeP2;
    }

    public void setEquityByCardP1(Map<String, String> equityByCardP1) {
        this.equityByCardP1 = equityByCardP1;
    }

    public void setEquityByCardP2(Map<String, String> equityByCardP2) {
        this.equityByCardP2 = equityByCardP2;
    }

    public void setEquityByRangeP1(Map<String, String> equityByRangeP1) {
        this.equityByRangeP1 = equityByRangeP1;
    }

    public void setEquityByRangeP2(Map<String, String> equityByRangeP2) {
        this.equityByRangeP2 = equityByRangeP2;
    }

    public void setCombsByCard(Map<String, Integer> combsByCard) {
        this.combsByCard = combsByCard;
    }
}
