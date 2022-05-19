package services;

import models.calculator.n.Group;
import models.calculator.n.Hand;
import models.calculator.n.Range;
import models.calculator.enums.Type;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewCalculate {
    public static String[] suit = {"h", "d", "c", "s"};
    public static boolean isFlush;

    static int numberOfStringFromFile(String str, int range) {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader("G:\\JAVA\\GeekHub\\CourseWork\\equilator\\domain\\src\\main\\resources\\searchFile.txt"));
            String line = null;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                i++;
                if (isFlush == false && i == 323) {
                    i = 1114;
                } else {
                    if (line.equals(str)) {
                        return i;
                    }
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

    public static List<Hand> buildAllGroupHands(String groupHand) {
        List<Hand> hands = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[j];
                hands.add(new Hand().buildHand(hand));
            }
        }

        return hands;
    }

    public static List<Hand> buildSuitedGroupHands(String groupHand) {
        List<Hand> hands = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[i];
            hands.add(new Hand().buildHand(hand));
        }

        return hands;
    }

    public static List<Hand> buildOffsuitedGroupHands(String groupHand) {
        List<Hand> hands = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (suit[i] != suit[j]) {
                    String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[j];
                    hands.add(new Hand().buildHand(hand));
                }
            }
        }

        return hands;
    }

    public static List<Hand> buildPocketPairGroupHands(String groupHand) {
        List<Hand> hands = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = i + 1; j < 4; j++) {
                String hand = groupHand.charAt(0) + suit[i] + groupHand.charAt(1) + suit[j];
                hands.add(new Hand().buildHand(hand));
            }
        }

        return hands;
    }

    public static Hand buildHand(String groupHand) {
        return new Hand().buildHand(groupHand);
    }

    public static Group buildGroup(String groupHand) {
        Group group = new Group();
        List<Hand> hands = new ArrayList<>();

        if (groupHand.length() == 4) {
            hands.add(buildHand(groupHand));
            if (groupHand.charAt(0) == groupHand.charAt(2)) {
                group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(2));
                group.setType(Type.POCKET_PAIR);
            } else if (groupHand.charAt(1) == groupHand.charAt(3)) {
                group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(2) + "s");
                group.setType(Type.SUIT);
            } else {
                group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(2) + "o");
                group.setType(Type.OFFSUIT);
            }
        }
        if (groupHand.length() == 2 && groupHand.charAt(0) == groupHand.charAt(1)) {
            hands.addAll(buildPocketPairGroupHands(groupHand));
            group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(1));
            group.setType(Type.POCKET_PAIR);
        }
        if (groupHand.length() == 2 && groupHand.charAt(0) != groupHand.charAt(1)) {
            hands.addAll(buildSuitedGroupHands(groupHand));
            group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(2) + "s");
            group.setType(Type.SUIT);

            hands.addAll(buildOffsuitedGroupHands(groupHand));
            group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(2) + "o");
            group.setType(Type.OFFSUIT);

          //  hands.addAll(buildAllGroupHands(groupHand));
        //    group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(1));
        }
        if (groupHand.length() == 3 && groupHand.charAt(2) == 'o' && groupHand.charAt(0) != groupHand.charAt(1)) {
            hands.addAll(buildOffsuitedGroupHands(groupHand));
            group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(1) + "o");
            group.setType(Type.OFFSUIT);
        }
        if (groupHand.length() == 3 && groupHand.charAt(2) == 's' && groupHand.charAt(0) != groupHand.charAt(1)) {
            hands.addAll(buildSuitedGroupHands(groupHand));
            group.setGroupName(String.valueOf(groupHand.charAt(0)) + groupHand.charAt(1) + "s");
        }

        group.setGroupHands(hands);

        return group;
    }

    public static Range buildRange(String input) {
        Range range = new Range();
        List<Group> groups = new ArrayList<>();

        String[] rangeArr = input.split(",");

        for (int i = 0; i < rangeArr.length; i++) {
            Group group = buildGroup(rangeArr[i]);
            groups.add(group);
        }

        range.setRangeGroups(groups);

        return range;
    }


    public static void main(String[] args) {
        String[] cards = {"A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2"};

        Map<String, String> suit = new HashMap<>();
        suit.put("s", "0001");
        suit.put("h", "0010");
        suit.put("d", "0100");
        suit.put("c", "1000");

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

        int[] cardsArr = new int[5];
        int[] flushArr = new int[5];
        String combNumber = null;
        String flushNumber = null;
        int range = 20220;

        String range1 = "QsKs,22";
        String range2 = "Kd7s";
        String board = "AsJsTs";

        Range rangeP1 = buildRange(range1);
        Range rangeP2 = buildRange(range2);



        String hand = board + range1;

        System.out.println(hand);

        int k = 0;
        hand = hand.toLowerCase();
        for (int i = 0; i < hand.length(); i++) {
            if (i + 1 < hand.length()) {
                String c1 = String.valueOf(hand.charAt(i));
                String s1 = String.valueOf(hand.charAt(i + 1));
                combNumber = prime.get(c1);
                flushNumber = suit.get(s1);
                cardsArr[k] = Integer.parseInt(combNumber, 2);
                flushArr[k] = Integer.parseInt(flushNumber, 2);
                k++;
                i++;
            }
        }

        int flushResult = flushArr[0] & flushArr[1] & flushArr[2] & flushArr[3] & flushArr[4];
        //  System.out.println(Integer.toBinaryString(resultComb));
        //    System.out.println("is flush - " + Integer.toBinaryString(isFlushResult));

        if (flushResult == 1) {
            isFlush = true;
        }

        int resultComb = cardsArr[0] * cardsArr[1] * cardsArr[2] * cardsArr[3] * cardsArr[4];
        //   System.out.println(resultComb);
        System.out.println(numberOfStringFromFile(String.valueOf(resultComb), range));




    }
}
