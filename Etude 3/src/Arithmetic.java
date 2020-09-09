import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;


public class Arithmetic {
    private static int target;
    private static int[] valuesToUse;

    private static char[] operators;

    private static boolean leftToRight = false;
    private static boolean solutionFound = false;
    private static boolean outOfBounds = false;

    public static void main (String args[]){
        Arithmetic main = new Arithmetic();
        ArrayList<String> lines = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNextLine()) {
            //lines.add(scanner.nextLine());

            solutionFound = false;
            outOfBounds = false;

            String[] numbers = scanner.nextLine().trim().split("\\s+");


            valuesToUse = new int[numbers.length];

            for (int k = 0; k < numbers.length; k++) {
                valuesToUse[k] = Integer.parseInt(numbers[k]);
            }

            operators = new char[valuesToUse.length - 1];

            String[] secondLine = scanner.nextLine().trim().split("\\s+");
            target = Integer.parseInt(secondLine[0]);

            if (secondLine[1].equals("L")) {
                leftToRight = true;
            } else if (secondLine[1].equals("N")) {
                leftToRight = false;
            }

            main.checkCalculations(1, operators, 0, 0);

            if(!solutionFound && !outOfBounds){
                if (leftToRight) {
                    System.out.println("L " + target + " impossible");
                }
                else {
                    System.out.println("N " + target + " impossible");
                }
            }
        }

        scanner.close();

        /*for(int i = 0; i < lines.size(); i++) {
            solutionFound = false;
            outOfBounds = false;

            String[] numbers = lines.get(i).trim().split("\\s+");


            valuesToUse = new int[numbers.length];

            for (int k = 0; k < numbers.length; k++) {
                valuesToUse[k] = Integer.parseInt(numbers[k]);
            }

            operators = new char[valuesToUse.length - 1];

            String[] secondLine = lines.get(++i).trim().split("\\s+");
            target = Integer.parseInt(secondLine[0]);

            if (secondLine[1].equals("L")) {
                leftToRight = true;
            } else if (secondLine[1].equals("N")) {
                leftToRight = false;
            }

            //main.checkInRange();
            main.checkCalculations(1, operators, 0, 0);

            if(!solutionFound && !outOfBounds){
                if (leftToRight) {
                    System.out.println("L " + target + " impossible");
                }
                else {
                    System.out.println("N " + target + " impossible");
                }
            }
        }*/
    }

    public void checkCalculations(int depth, char[] operators, int currentValue, int secondaryValue){
        if(leftToRight) {
            if (currentValue == 0) {
                currentValue = valuesToUse[0];
            }
        }
        else {
            if (secondaryValue == 0) {
                secondaryValue = valuesToUse[0];
            }
        }

        if(depth == valuesToUse.length){
            int value = currentValue + secondaryValue;

            if(value == target && !solutionFound){
                solutionFound = true;
                String output = "";

                for(int i = 1; i <= valuesToUse.length - 1; i++){
                   output = output + String.valueOf(valuesToUse[i - 1] + " " + String.valueOf(operators[i - 1])) + " ";
                }

                output = output + String.valueOf(valuesToUse[valuesToUse.length - 1]);

                if (leftToRight) {
                    System.out.println("L " + target + " " + output);
                }
                else {
                    System.out.println("N " + target + " " + output);
                }
            }

            return;
        }

        depth += 1;

        if(!solutionFound) {
            if (leftToRight) {
                if ((currentValue + valuesToUse[depth - 1]) <= target) {
                    operators[depth - 2] = '+';
                    checkCalculations(depth, operators, currentValue + valuesToUse[depth - 1], 0);
                }

                if ((currentValue * valuesToUse[depth - 1]) <= target) {
                    operators[depth - 2] = '*';
                    checkCalculations(depth, operators, currentValue * valuesToUse[depth - 1], 0);
                }
            } else {
                if (currentValue + secondaryValue <= target) {
                    operators[depth - 2] = '+';
                    checkCalculations(depth, operators, currentValue + secondaryValue, valuesToUse[depth - 1]);
                }

                if (currentValue + secondaryValue * valuesToUse[depth - 1] <= target) {
                    operators[depth - 2] = '*';
                    checkCalculations(depth, operators, currentValue, secondaryValue * valuesToUse[depth - 1]);
                }
            }
        }
    }
}

//Backup for redundant code which was checking the algorithim twice

/*          if(leftToRight) {
                for (int i = 1; i <= valuesToUse.length - 1; i++) {
                    if(value > target){
                        tooLarge = true;
                        break;
                    }

                    if (operators[i - 1] == '+' && value <= target) {
                        value += valuesToUse[i];
                    }
                    else if (operators[i - 1] == '*' && value <= target) {
                        value *= valuesToUse[i];
                    }
                }
            }
            else{
               int currentSum = valuesToUse[0];
               value = 0;
               for(int i = 1; i <= valuesToUse.length - 1; i++){
                   if(value > target || currentSum > target){
                       tooLarge = true;
                       break;
                   }

                   if (operators[i - 1] == '+' && value <= target) {
                       value += currentSum;
                       currentSum = valuesToUse[i];
                   }
                   else if (operators[i - 1] == '*' && value <= target) {
                       currentSum *= valuesToUse[i];
                   }
               }
                value += currentSum;
            }

    public void checkInRange(){
        int minValue = 0, maxValue = 0;

        for (int i = 0; i <= valuesToUse.length - 1; i++){
            if(valuesToUse[0] == 1 && leftToRight && i == 0) {
                minValue = valuesToUse[i] + valuesToUse[++i];
                maxValue = minValue;
            }
            else {
                if (minValue == 0 && maxValue == 0) {
                    minValue = valuesToUse[i];
                    maxValue = valuesToUse[i];
                } else {
                    minValue += valuesToUse[i];
                    maxValue *= valuesToUse[i];
                }
            }
        }

        if(maxValue == 0){
            maxValue = Integer.MAX_VALUE;
        }

        if(valuesToUse[0] == 1 && !leftToRight){
            minValue -= 1;
            maxValue += 1;
        }

        if(target < minValue || target > maxValue){
            if(leftToRight) {
                System.out.println("L " + target + " impossible");
            }
            else {
                System.out.println("N " + target + " impossible");
            }
            outOfBounds = true;
        }
    }*/