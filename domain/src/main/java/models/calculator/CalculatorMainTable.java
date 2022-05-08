package models.calculator;

public class CalculatorMainTable {
    private String rangePlayer1;
    private String rangePlayer2;
    private String equityPlayer1;
    private String equityPlayer2;
    private String deal;
    private String Board;

    public String getRangePlayer1() {
        return rangePlayer1;
    }

    public void setRangePlayer1(String rangePlayer1) {
        this.rangePlayer1 = rangePlayer1;
    }

    public String getRangePlayer2() {
        return rangePlayer2;
    }

    public void setRangePlayer2(String rangePlayer2) {
        this.rangePlayer2 = rangePlayer2;
    }

    public String getEquityPlayer1() {
        return equityPlayer1;
    }

    public void setEquityPlayer1(String equityPlayer1) {
        this.equityPlayer1 = equityPlayer1;
    }

    public String getEquityPlayer2() {
        return equityPlayer2;
    }

    public void setEquityPlayer2(String equityPlayer2) {
        this.equityPlayer2 = equityPlayer2;
    }

    public String getBoard() {
        return Board;
    }

    public void setBoard(String board) {
        Board = board;
    }

    public String getDeal() {
        return deal;
    }

    public void setDeal(String deal) {
        this.deal = deal;
    }
}
