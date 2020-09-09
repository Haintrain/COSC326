import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Pattern;

public class PokerHands {

    String[] faces = {"1", "13", "12", "11", "10", "9", "8", "7", "6", "5", "4", "3", "2"};
    String[] pictureFaces = {"A", "K", "Q", "J", "T"};
    String[] suits = {"S", "H", "D", "C"};

    static ArrayList<String> hands = new ArrayList<>();

    public static void main(String[] args) {
        PokerHands main = new PokerHands();

        Scanner input = new Scanner(System.in);

        while (input.hasNextLine()){
            hands.add(input.nextLine());
        }

        input.close();

        for(String hand: hands){
            main.checkValidHand(hand);
        }
    }

    public void checkValidHand(String handOriginal) {
        String hand = handOriginal.toUpperCase();

        hand = hand.replace("T", "X");

        hand = hand.replace("13", "K");
        hand = hand.replace("12", "Q");
        hand = hand.replace("11", "J");
        hand = hand.replace("10", "T");
        hand = hand.replace("1", "A");

        String[] cards;
        int[][] value = new int[5][3];

        Pattern facePattern = Pattern.compile("^[0-9AKQJT]+$");
        Pattern suitPattern = Pattern.compile("^[SHDC]+$");

        if (hand.contains("-")) {
            cards = hand.split("-");
        }
        else if (hand.contains(" ")) {
            cards = hand.split(" ");
        }
        else if (hand.contains("/")) {
            cards = hand.split("/");
        }
        else{
           invalidMessage(handOriginal);
           return;
        }

        if (cards.length != 5) {
            invalidMessage(handOriginal);
            return;
        }

        for (int i = 0; i < cards.length; i++){
            for (int j = 0; j < cards.length; j++){
                if(i != j && cards[i].equals(cards[j])){
                    invalidMessage(handOriginal);
                    return;
                }
            }
        }

        for (int i = 0; i < cards.length; i++) {
            value[i][0] = i;
            String face, suit;

            if (cards[i].length() == 2){
                face = cards[i].substring(0, 1);
                suit = cards[i].substring(1, 2);
            }
            else{
                invalidMessage(handOriginal);
                return;
            }

            if (facePattern.matcher(face).find()) {
                value[i][2] = Arrays.asList(faces).indexOf(face);

                if (value[i][2] == -1) {
                    value[i][2] = Arrays.asList(pictureFaces).indexOf(face);
                }
            }
            else {
                invalidMessage(handOriginal);
                return;
            }

            if (suitPattern.matcher(suit).find()) {
                value[i][1] = Arrays.asList(suits).indexOf(suit);
            }
            else {
                invalidMessage(handOriginal);
                return;
            }
        }

        Arrays.sort(value, new Comparator<int[]>() {

            @Override
            public int compare(final int[] entry1,
                               final int[] entry2) {

                if (entry1[2] < entry2[2])
                    return 1;
                else if(entry1[2] == entry2[2]){
                    if(entry1[1] < entry2[1]){
                        return 1;
                    }
                    else{
                        return -1;
                    }
                }
                else
                    return -1;
            }
        });

        String output= cards[value[0][0]];

        for(int i = 1; i < value.length; i++){
            output += " " + cards[value[i][0]];
        }

        output = output.replace("T", "10");

        System.out.println(output);

    }

    public void invalidMessage(String hand){
        System.out.println("Invalid: " + hand);
    }
}
