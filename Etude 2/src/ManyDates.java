import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ManyDates {

    static int[] validStyle = new int[6];

    public static void main(String[] args) {
        ManyDates main = new ManyDates();

        ArrayList<String> dates = new ArrayList<>();

        Scanner input = new Scanner(System.in);

        while (input.hasNextLine()){
            dates.add(input.nextLine());
        }

        input.close();

        for(String date: dates) {
            main.checkDates(date);
        }

        int style = getIndexOfLargest(validStyle);

        for(String date: dates) {
            main.printDates(date, style);
        }
    }

    public void checkDates(String date){
        String[] parts = date.split("/");

        if(parts.length != 3){
            return;
        }

        int[] values = new int[3];

        for(int i = 0; i < parts.length; i++){
            if(isNumeric(parts[i])) {
                values[i] = Integer.parseInt(parts[i]);
            }
            else{
                return;
            }
        }

        if(values[0] < 32 && values[0] > 0) {
            if (values[1] < 13 && values[1] > 0) { //DD/MM/YY
                if (isValidYear(values[0], values[1], values[2]) && (parts[2].length() == 2 || parts[2].length() == 4)) {
                    validStyle[0]++;
                }
            }
            if (values[2] < 13 && values[2] > 0) { //DD/YY/MM
                if (isValidYear(values[0], values[2], values[1]) && (parts[1].length() == 2 || parts[1].length() == 4)) {
                    validStyle[1]++;
                }
            }
        }

        if(values[1] < 32 && values[1] > 0) { //MM/DD/YY
            if (values[0] < 13 && values[0] > 0) {
                if (isValidYear(values[1], values[0], values[2]) && (parts[2].length() == 2 || parts[2].length() == 4)) {
                    validStyle[2]++;
                }
            }
            if (values[2] < 13 && values[1] > 0) { //YY/DD/MM
                if (isValidYear(values[1], values[2], values[0]) && (parts[0].length() == 2 || parts[0].length() == 4)) {
                    validStyle[3]++;
                }
            }
        }

        if(values[2] < 32 && values[2] > 0) {
            if (values[0] < 13 && values[0] > 0) { //MM/YY/DD
                if (isValidYear(values[2], values[0], values[1]) && (parts[1].length() == 2 || parts[1].length() == 4)) {
                    validStyle[4]++;
                }
            }
            if (values[1] < 13 && values[1] > 0) { //YY/MM/DD
                if (isValidYear(values[2], values[1], values[0]) && (parts[0].length() == 2 || parts[0].length() == 4)) {
                    validStyle[5]++;
                }
            }
        }
    }

    public void printDates(String date, int style){
        String[] parts = date.split("/");
        int[] values = new int[3];

        String errorMessage = "";

        if(parts.length != 3){
            System.out.println(date + " - Invalid: Not a proper date.");
            return;
        }

        for(int i = 0; i < 3; i++){
            if(isNumeric(parts[i])) {
                if(parts[i].charAt(0) == '0' && parts[i].length() != 2){
                    errorMessage(date, "Invalid: Not a proper date.");
                    return;
                }

                values[i] = Integer.parseInt(parts[i]);

                if(values[i] > 3000 || (values[i] < 1753 && values[i] > 99)){
                    errorMessage(date, "Invalid: Year out of range.");
                    return;
                }
            }
            else{
                System.out.println(date + " - Invalid: Not a proper date.");
                return;
            }
        }

        if(style == 0) {
            errorMessage = validDate(values[0], values[1], values[2], date, 2);
        }
        else if(style == 1){
            errorMessage = validDate(values[0], values[2], values[1], date, 1);
        }
        else if(style == 2){
            errorMessage = validDate(values[1], values[0], values[2], date, 2);
        }
        else if(style == 3){
            errorMessage = validDate(values[1], values[2], values[0], date, 0);
        }
        else if(style == 4){
            errorMessage = validDate(values[2], values[0], values[1], date, 1);
        }
        else if(style == 5){
            errorMessage = validDate(values[2], values[1], values[0], date, 0);
        }

        if(!errorMessage.equals("Valid")){
            errorMessage(date, errorMessage);
        }
    }

    public String validDate(int day, int month, int year, String date, int yearPart){
        String[] parts = date.split("/");

        if(!(parts[yearPart].length() == 2 || parts[yearPart].length() == 4)){
            return "Invalid: Invalid year.";
        }

        String currentMonth = "";

        if(day < 1){
            return "Invalid: Day out of range.";
        }

        if(day == 31 && (month != 1 && month != 3 && month != 5 && month != 7 && month != 9 && month != 11) || day > 31){
            return "Invalid: Day out of range.";
        }

        if(month > 12 || month < 1){
            return "Invalid: Month out of range.";
        }

        if(year > 49 && year < 100){
            year = 1900 + year;
        }
        else if(year < 50){
            year = 2000 + year;
        }

        if(day == 29 && month == 2){
            if (!isLeapYear(year)) {
                return "Invalid: Leap year error.";
            }
        }

        switch(month){
            case 1:
                currentMonth = "Jan";
                break;
            case 2:
                currentMonth = "Feb";
                break;
            case 3:
                currentMonth = "Mar";
                break;
            case 4:
                currentMonth = "Apr";
                break;
            case 5:
                currentMonth = "May";
                break;
            case 6:
                currentMonth = "Jun";
                break;
            case 7:
                currentMonth = "Jul";
                break;
            case 8:
                currentMonth = "Aug";
                break;
            case 9:
                currentMonth = "Sep";
                break;
            case 10:
                currentMonth = "Oct";
                break;
            case 11:
                currentMonth = "Nov";
                break;
            case 12:
                currentMonth = "Dec";
                break;
        }

        System.out.println(String.valueOf(day) + " " + currentMonth + " " + String.valueOf(year));

        return "Valid";
    }

    public boolean isLeapYear(int year){
        if(year > 49 && year < 100) {
            year = 1900 + year;
        }
        else if(year < 50){
            year = 2000 + year;
        }

        if(year % 100 == 0 && year % 400 == 0){
            return true;
        }
        else if (year % 100 == 0){
            return false;
        }
        else if(year % 4 == 0){
            return true;
        }

        return false;
    }

    public boolean isValidYear(int day, int month, int year){
            if(year > 99 && year < 1000){
                return false;
            }

            if(day == 29 && month == 2){
                if(isLeapYear(year)){
                    return true;
                }
                else{
                    return false;
                }
            }
            else if(day == 31){
                if(month == 1 || month == 3 || month == 5 || month == 7 || month == 9 || month == 11){
                    return true;
                }
                else{
                    return false;
                }
            }
            else{
                return true;
            }
    }

    public void errorMessage(String date, String message){
        System.out.println(date + " - " + message);
    }

    public static int getIndexOfLargest( int[] array )
    {
        int largest = 0;
        for ( int i = 1; i < array.length; i++ ) {
            if ( array[i] > array[largest] ) {
                largest = i;
            }
        }
        return largest;
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }

        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
