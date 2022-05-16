import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainApp {

    static int numberOfStringFromFile(String str) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("G:\\JAVA\\GeekHub\\CourseWork\\equilator\\number.txt"));
            String S = null;
            int i = 0;
            while ((S = reader.readLine()) != null) {
                i++;
                if (S.equals(str)) {
                    return i;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return -1;
    }

    private static final int DISTINCT_VALUES = 7462;

    private static final int PRIME_DEUCE = 2;
    private static final int PRIME_THREE = 3;
    private static final int PRIME_FOUR = 5;
    private static final int PRIME_FIVE = 7;
    private static final int PRIME_SIX = 11;
    private static final int PRIME_SEVEN = 13;
    private static final int PRIME_EIGHT = 17;
    private static final int PRIME_NINE = 19;
    private static final int PRIME_TEN = 23;
    private static final int PRIME_JACK = 29;
    private static final int PRIME_QUEEN = 31;
    private static final int PRIME_KING = 37;
    private static final int PRIME_ACE = 41;

    protected static final int[] CARD_RANKS = {PRIME_DEUCE,
            PRIME_THREE, PRIME_FOUR, PRIME_FIVE, PRIME_SIX, PRIME_SEVEN,
            PRIME_EIGHT, PRIME_NINE, PRIME_TEN, PRIME_JACK, PRIME_QUEEN,
            PRIME_KING, PRIME_ACE};


    public static void main(String[] args) throws IOException {
        Map<String, String> bit = new HashMap<>();
        bit.put("2", "0000000000001");
        bit.put("3", "0000000000010");
        bit.put("4", "0000000000100");
        bit.put("5", "0000000001000");
        bit.put("6", "0000000010000");
        bit.put("7", "0000000100000");
        bit.put("8", "0000001000000");
        bit.put("9", "0000010000000");
        bit.put("t", "0000100000000");
        bit.put("j", "0001000000000");
        bit.put("q", "0010000000000");
        bit.put("k", "0100000000000");
        bit.put("a", "1000000000000");


        //cdhs = suit of card (bit turned on based on suit of card)
        Map<String, String> suit = new HashMap<>();
        suit.put("s", "0001");
        suit.put("h", "0010");
        suit.put("d", "0100");
        suit.put("c", "1000");


        //r = rank of card (deuce=0,trey=1,four=2,five=3,...,ace=12)
        Map<String, String> rank = new HashMap<>();
        rank.put("2", "0000");
        rank.put("3", "0001");
        rank.put("4", "0010");
        rank.put("5", "0011");
        rank.put("6", "0100");
        rank.put("7", "0101");
        rank.put("8", "0110");
        rank.put("9", "0111");
        rank.put("t", "1000");
        rank.put("j", "1001");
        rank.put("q", "1010");
        rank.put("k", "1011");
        rank.put("a", "1100");

        //p = prime number of rank (deuce=2,trey=3,four=5,...,ace=41)
        Map<String, String> prime = new HashMap<>();
        prime.put("2", "000010");
        prime.put("3", "000011");
        prime.put("4", "000101");
        prime.put("5", "000111");
        prime.put("6", "001011");
        prime.put("7", "001101");
        prime.put("8", "010001");
        prime.put("9", "010011");
        prime.put("t", "010111");
        prime.put("j", "011101");
        prime.put("q", "011111");
        prime.put("k", "100101");
        prime.put("a", "101001");

        List<String> variants = new LinkedList<>();
        String cards = "AsJdQhJsTd9c8h";

        for (int i = 0; i + 1 < cards.length(); i++) {
            for (int j = i + 2; j + 1 < cards.length(); j++) {
                for (int k = j + 2; k + 1 < cards.length(); k++) {
                    for (int l = k + 2; l + 1 < cards.length(); l++) {
                        for (int m = l + 2; m + 1 < cards.length(); m++) {

                            variants.add(
                                    String.valueOf(
                                            cards.charAt(i)) + cards.charAt(i + 1) +
                                            cards.charAt(j) + cards.charAt(j + 1) +
                                            cards.charAt(k) + cards.charAt(k + 1) +
                                            cards.charAt(l) + cards.charAt(l + 1) +
                                            cards.charAt(m) + cards.charAt(m + 1)


                            );
                            m += 1;
                        }
                        l += 1;
                    }
                    k += 1;
                }
                j += 1;
            }
            i += 1;
        }





        /*


        File file = new File("Ranges.txt");
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }




        int[] comb = new int[5];
        int[] isFlush = new int[5];
        //     int[] rankPlusPrime = new int[5];


        String number = null;


        List<String> cardsDeck = new LinkedList<>();

        String[] cards = {"A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] suit1 = {"h", "d", "c", "s"};


        String hand = "Ackc4ctc2c";

        int k = 0;
        hand = hand.toLowerCase();
        for (int j1 = 0; j1 < hand.length(); j1++) {
            if (j1 + 1 < hand.length()) {
                String c1 = String.valueOf(hand.charAt(j1));
                number = prime.get(c1);
                comb[k] = Integer.parseInt(number, 2);
                k++;
                j1++;
            }
        }

        int resultComb = comb[0] * comb[1] * comb[2] * comb[3] * comb[4];
     //   System.out.println(resultComb);

        System.out.println(numberOfStringFromFile(String.valueOf(resultComb)));

         */







/*
        for (int i = 0; i <= suit1.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                cardsDeck.add(cards[j] + suit1[i]);
            }
        }




        for (int i = 0; i < cards.length; i++) {
            for (int j = 0; j < cards.length; j++) {
                for (int n = 0; n < cards.length; n++) {
                    for (int l = 0; l < cards.length; l++) {


                        if (cards[i] != cards[n] && cards[i] != cards[j] && cards[i] != cards[l]&&
                                cards[j] != cards[n] && cards[j] != cards[l] && cards[n] != cards[l]) {
                            String hand = cards[i] + " " +
                                    cards[i] + " " +
                                    cards[j] + " " +
                                    cards[n] + " " +
                                    cards[l] + " ";
                            System.out.println(hand);

                            int k = 0;
                            hand = hand.toLowerCase();
                            for (int j1 = 0; j1 < hand.length(); j1++) {
                                if (j1 + 1 < hand.length()) {
                                    String c1 = String.valueOf(hand.charAt(j1));
                                    number = prime.get(c1);
                                    comb[k] = Integer.parseInt(number, 2);
                                    k++;
                                    j1++;
                                }
                            }

                            int resultComb = comb[0] * comb[1] * comb[2] * comb[3] * comb[4];
                            //      System.out.println(resultComb);


                            writer.write(hand + " = " + resultComb + "\n");


                        }
                    }
                }
            }

        }
        writer.close();
    }

 */


        //      List<Integer> comb = new ArrayList<>();
/*
        String isFlushNumber = null;
        //    String rankPlusPrimeString = null;


        //  String hand = "Ackc4ctc2c"; //419794751, 419823423, 6405
        //    String hand = "2c2h2d2sAs"; //268565547, 4097
        String hand = number;
        hand = hand.toLowerCase();
        for (int i = 0; i < hand.length(); i++) {
            if (i + 1 < hand.length()) {
                String c1 = String.valueOf(hand.charAt(i));
                //      String s1 = String.valueOf(hand.charAt(i + 1));
                //    number = "000" + bit.get(c1) + suit.get(s1) + rank.get(c1) + "00" + prime.get(c1);
                number = bit.get(c1);
                //            isFlushNumber = suit.get(s1);
                //     rankPlusPrimeString = prime.get(c1);
                //      System.out.println(number);
                comb[k] = Integer.parseInt(number, 2);
                isFlush[k] = Integer.parseInt(isFlushNumber, 2);
                //        rankPlusPrime[k] = Integer.parseInt(rankPlusPrimeString, 2);
                k++;
                i++;
            }
        }



        for (int i = 0; i < isFlush.length; i++) {
            System.out.println(rankPlusPrime[i]);
        }




    //    int multiply = rankPlusPrime[0] * rankPlusPrime[1] * rankPlusPrime[2] * rankPlusPrime[3] * rankPlusPrime[4];
    //    System.out.println(0x1E80);
    //   System.out.println(multiply);
    int resultComb = (comb[0] | comb[1] | comb[2] | comb[3] | comb[4]);
        System.out.println(resultComb);
 */


/*

        int isFlushResult = isFlush[0] & isFlush[1] & isFlush[2] & isFlush[3] & isFlush[4];
        System.out.println(Integer.toBinaryString(resultComb));
        System.out.println("is flush - " + Integer.toBinaryString(isFlushResult));

 */

        //  String finalNumber = (Integer.toBinaryString(resultComb)).substring(0, 13);
        //   System.out.println(finalNumber);

        //      int decimal = Integer.parseInt(finalNumber.substring(0, 13), 2);
        //      System.out.println(decimal);


        //    System.out.println(Integer.toBinaryString(2));

        //   number = number.substring(0, 15);
        //    System.out.println(number);


        //    System.out.println(number);
        //   System.out.println(Integer.parseInt(number, 2));

        int flushes = 7937;





/*
        int[] value = new int[32];
        //     12345678 91234567 89123456 78912345
        //    xxxAKQJT 98765432 CDHSrrrr xxPPPPPP
        //    00001000 00000000 01001011 00100101 Бубновый король

        List<Integer> deck = new ArrayList<>();

        byte data = 0b101;
        System.out.println(data);

        System.out.println(0x8000);

        int i, j, n = 0, suit = 0x8000;

        for (i = 0; i < 4; i++, suit >>= 1)
            for (j = 0; j < 13; j++, n++)
                deck.add(n, CARD_RANKS[j] | (j << 8) | suit | (1 << (16 + j)));

        //     System.out.println(deck);

 */


        //b = bit turned on depending on rank of card
        /*
        int two = 0b0000000000001;
        int three = 0b1000000000000;
        int four = 0b0100000000000;
        int five = 0b0010000000000;
        int six = 0b0001000000000;
        int seven = 0b0000100000000;
        int eight = 0b0000010000000;
        int nine = 0b0000001000000;
        int ten = 0b0000000100000;
        int jack = 0b0000000010000;
        int queen = 0b0000000001000;
        int king = 0b0000000000100;
        int ace = 0b0000000000010;
         */

        //cdhs = suit of card (bit turned on based on suit of card)
        /*
        int s = 0b0001;
        int h = 0b0010;
        int d = 0b0100;
        int c = 0b1000;

         */

        //r = rank of card (deuce=0,trey=1,four=2,five=3,...,ace=12)
        /*
        int twoRank   = 0b0;
        int threeRank = 0b1;
        int fourRank  = 0b10;
        int fiveRank  = 0b11;
        int sixRank   = 0b100;
        int sevenRank = 0b101;
        int eightRank = 0b110;
        int nineRank  = 0b111;
        int tenRank   = 0b1000;
        int jackRank  = 0b1001;
        int queenRank = 0b1010;
        int kingRank  = 0b1011;
        int aceRank   = 0b1100;

         */

        //p = prime number of rank (deuce=2,trey=3,four=5,...,ace=41)
        /*
        int  twoPrime   = 0b10    ;
        int  threePrime = 0b11    ;
        int  fourPrime  = 0b101   ;
        int  fivePrime  = 0b111   ;
        int  sixPrime   = 0b1011  ;
        int  sevenPrime = 0b1101  ;
        int  eightPrime = 0b10001 ;
        int  ninePrime  = 0b10011 ;
        int  tenPrime   = 0b10111 ;
        int  jackPrime  = 0b11101 ;
        int  queenPrime = 0b11111 ;
        int  kingPrime  = 0b100101;
        int  acePrime   = 0b101001;

         */

        /*
        System.out.println(0x00082707);
        System.out.println(Integer.toBinaryString(2));
        System.out.println(Integer.parseInt("1000000000000", 2));


 */


        //   char[] letters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g'};

        int count = 0;
/*
        for (int i = 0; i < letters.length; i++) {
            for (int j = i + 1; j < letters.length; j++) {
                for (int k = j + 1; k < letters.length; k++) {
                    for (int l = k + 1; l < letters.length; l++) {
                        for (int m = l + 1; m < letters.length; m++) {
                            System.out.println(letters[i] +
                                            letters[j] +
                                            letters[k] +
                                            letters[l] +
                                            letters[m]
                            );
                            count++;
                        }
                    }
                }
            }
        }
  */


    }
}


