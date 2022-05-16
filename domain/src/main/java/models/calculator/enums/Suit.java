package models.calculator.enums;

public enum Suit {
    HEART("h"),
    DIAMONDS("d"),
    CLUBS("c"),
    SPADE("s");

    private final String abbreviation;

    Suit(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
