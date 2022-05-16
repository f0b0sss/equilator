package models.calculator.enums;

public enum Type {

    POCKET_PAIR(6),
    OFFSUIT(12),
    SUIT(4);

    /*
    POCKET_PAIR(Set.of(
            Suit.HEART.getAbbreviation() + Suit.DIAMONDS.getAbbreviation(),
            Suit.SPADE.getAbbreviation() + Suit.HEART.getAbbreviation(),
            Suit.CLUBS.getAbbreviation() + Suit.CLUBS.getAbbreviation(),
            Suit.SPADE.getAbbreviation() + Suit.DIAMONDS.getAbbreviation(),
            Suit.CLUBS.getAbbreviation() + Suit.DIAMONDS.getAbbreviation(),
            Suit.CLUBS.getAbbreviation() + Suit.SPADE.getAbbreviation()
    ), 6),
    OFFSUIT(Set.of(
            Suit.HEART.getAbbreviation() + Suit.DIAMONDS.getAbbreviation(),
            Suit.HEART.getAbbreviation() + Suit.SPADE.getAbbreviation(),
            Suit.HEART.getAbbreviation() + Suit.CLUBS.getAbbreviation(),
            Suit.DIAMONDS.getAbbreviation() + Suit.SPADE.getAbbreviation(),
            Suit.DIAMONDS.getAbbreviation() + Suit.CLUBS.getAbbreviation(),
            Suit.SPADE.getAbbreviation() + Suit.CLUBS.getAbbreviation()
    ), 12),
    SUIT(Set.of(
            Suit.HEART.getAbbreviation() + Suit.DIAMONDS.getAbbreviation(),
            Suit.SPADE.getAbbreviation() + Suit.HEART.getAbbreviation(),
            Suit.CLUBS.getAbbreviation() + Suit.CLUBS.getAbbreviation(),
            Suit.SPADE.getAbbreviation() + Suit.DIAMONDS.getAbbreviation(),
            Suit.CLUBS.getAbbreviation() + Suit.DIAMONDS.getAbbreviation(),
            Suit.CLUBS.getAbbreviation() + Suit.SPADE.getAbbreviation(),
            Suit.DIAMONDS.getAbbreviation() + Suit.HEART.getAbbreviation(),
            Suit.DIAMONDS.getAbbreviation() + Suit.SPADE.getAbbreviation(),
            Suit.DIAMONDS.getAbbreviation() + Suit.CLUBS.getAbbreviation(),
            Suit.HEART.getAbbreviation() + Suit.SPADE.getAbbreviation(),
            Suit.HEART.getAbbreviation() + Suit.CLUBS.getAbbreviation(),
            Suit.SPADE.getAbbreviation() + Suit.CLUBS.getAbbreviation()
    ), 4);

     */

    private final int combs;

    Type(int combs) {
        this.combs = combs;
    }

    public int getCombs() {
        return combs;
    }
}
