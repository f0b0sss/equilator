package models.calculator.n;

public class Board {

    private Card[] board = new Card[5];

    public Card[] getBoard() {
        return board;
    }

    public void setBoard(Card[] board) {
        this.board = board;
    }

    public String getFlop(){
        return board[0].getCard() + board[1].getCard() + board[2].getCard();
    }

    public String getTurn(){
        return board[3].getCard();
    }

    public String getRiver(){
        return board[4].getCard();
    }
}
