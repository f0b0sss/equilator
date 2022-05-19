package models.calculator;

import java.util.LinkedHashMap;
import java.util.Map;

public class GameInfo {

    private final Map<String, String> equityByCardP1 = new LinkedHashMap<>(52);
    private final Map<String, String> equityByCardP2  = new LinkedHashMap<>(52);

    public Map<String, String> getEquityByCardP1() {
        return equityByCardP1;
    }

    public Map<String, String> getEquityByCardP2() {
        return equityByCardP2;
    }
}
