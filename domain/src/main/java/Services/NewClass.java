package Services;

public class NewClass {

    public NewClass() {

    }


/*


    public String getCard(String input){

        if (input.charAt(2) == 'o'){
            String cards = input.indent(0) + "h" + input.indexOf(1) + "d";
            return cards;
        }

        if (input.charAt(2) == 's'){
            String cards = input.indent(0) + "h" + input.indexOf(1) + "h";
            return cards;
        }

        if (input.length() == 4){
            return input;
        }


        return input;

    }


    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        NewClass newClass = new NewClass();

        double wonTimesPlayer1 = 0;
        double wonTimesPlayer2 = 0;

        String p1 = "AsKd";
        String p2 = "3s3d";


        newClass.getEquityByPLayer().put(p1, 0.0);
        newClass.getEquityByPLayer().put(p2, 0.0);

        String[] playersCards = new String[]{p1, p2};

        newClass.getPlayersPoints().put(p1, 0);
        newClass.getPlayersPoints().put(p2, 0);

        newClass.removeOpenCardsFromAllCards(p1);
        newClass.removeOpenCardsFromAllCards(p2);

        String board1 = "7s5h2c";
        newClass.removeOpenCardsFromAllCards(board1);


        int count = 0;


        for (int i = 0; i < newClass.allCards.size(); i++) {
            for (int j = i + 1; j < newClass.allCards.size(); j++) {

                String board = board1 + newClass.allCards.get(i) + newClass.allCards.get(j);

                for (int n = 0; n < playersCards.length; n++) {
                    newClass.generate(board + playersCards[n], playersCards[n]);
                }

                if (newClass.getPlayersPoints().get(p1) > newClass.getPlayersPoints().get(p2)) {
                    wonTimesPlayer1++;
                }
                if (newClass.getPlayersPoints().get(p1) < newClass.getPlayersPoints().get(p2)) {
                    wonTimesPlayer2++;
                }


                count++;

            }
        }


/*

        for (int i = 0; i < newClass.allCards.size(); i++) {
            for (int j = i + 1; j < newClass.allCards.size(); j++) {
                for (int k = j + 1; k < newClass.allCards.size(); k++) {
                    for (int l = k + 1; l < newClass.allCards.size(); l++) {
                        for (int m = l + 1; m < newClass.allCards.size(); m++) {

                            String board = newClass.allCards.get(i) +
                                    newClass.allCards.get(j) +
                                    newClass.allCards.get(k) +
                                    newClass.allCards.get(l) +
                                    newClass.allCards.get(m);

                         //   newClass.removeOpenCardsFromAllCards(board);

                            for (int n = 0; n < playersCards.length; n++) {
                                newClass.generate(board + playersCards[n], playersCards[n]);
                            }

                            if (newClass.getPlayersPoints().get(p1) > newClass.getPlayersPoints().get(p2)) {
                                pp1++;
                            }
                            if (newClass.getPlayersPoints().get(p1) < newClass.getPlayersPoints().get(p2)) {
                                pp2++;
                            }

                            count++;
                        }
                    }
                }
            }
        }





        System.out.println("winP1 - " + new DecimalFormat("#0.0000").format(wonTimesPlayer1 / count));
        System.out.println("winP2 - " + new DecimalFormat("#0.0000").format(wonTimesPlayer2 / count));

        System.out.println(System.currentTimeMillis() - time);


    }

   */


}
