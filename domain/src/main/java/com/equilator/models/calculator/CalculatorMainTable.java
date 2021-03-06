package com.equilator.models.calculator;

public class CalculatorMainTable {
    private String rangePlayer1;
    private String rangePlayer2;
    private String equityPlayer1;
    private String equityPlayer2;
    private String board;

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
        return board;
    }

    public void setBoard(String board) {
        this.board = board;
    }

    @Override
    public String toString() {
        return "CalculatorMainTable{" +
                "rangePlayer1='" + rangePlayer1 + '\'' +
                ", rangePlayer2='" + rangePlayer2 + '\'' +
                ", equityPlayer1='" + equityPlayer1 + '\'' +
                ", equityPlayer2='" + equityPlayer2 + '\'' +
                ", board='" + board + '\'' +
                '}';
    }
}
