import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EmailAddresses {

    public static void main(String[] args) {

        boolean validEmail = true;

        ArrayList<String> emailAddresses = new ArrayList<>();

        Scanner input = new Scanner(System.in);

        while (input.hasNextLine()){
            String line = input.nextLine();
            if(line.trim().length() > 0) {
                emailAddresses.add(line);
            }
        }

        input.close();
        Pattern pattern = Pattern.compile("^[@_.\\-a-z0-9]+$");

        for(String emailAddress: emailAddresses) {

            EmailAddresses main = new EmailAddresses();
            emailAddress = main.shortenAddress(emailAddress);

            if (emailAddress != null) {
                String[] front1 = emailAddress.split("[-._]");

                for(String split: front1){
                    if(split.length() == 0){
                        validEmail = false;
                    }
                }

                String front = emailAddress.substring(0, emailAddress.indexOf("@"));

                if (pattern.matcher(front).find()) {
                    if(validEmail) {
                        System.out.println(emailAddress);
                    }
                    else{
                        main.printError(emailAddress, "Invalid characters");
                    }
                }
                else {
                    main.printError(emailAddress, "Invalid characters");
                }
            }

            validEmail = true;
        }
    }

    public String shortenAddress(String originalAddress) {
        String shortAddress = originalAddress.toLowerCase();
        int dotIndex = -1;
        int atIndex = -1;

        if (!shortAddress.contains("@")) {
            if (shortAddress.contains("_at_")) {
                atIndex = originalAddress.lastIndexOf("_at_");

                String front = shortAddress.substring(0, atIndex);
                String back = shortAddress.substring(atIndex + 4);

                shortAddress = front + "@" + back;
            } else {
                printError(originalAddress, "missing @ symbol");
                return null;
            }
        }

        shortAddress = shortAddress.replaceAll("_dot_", ".");

        atIndex = shortAddress.lastIndexOf("@");
        String domain = shortAddress.substring(atIndex + 1, shortAddress.length());

        if(domain.contains(".")){

        }
        else{
            printError(originalAddress, "Invalid extension");
            return null;
        }

        Pattern pattern = Pattern.compile("^[.0-9]+$");

        if (domain.charAt(0) == '[' && domain.charAt(domain.length() - 1) == ']') {
            domain = domain.substring(1, domain.length() - 1);

            if(domain.split("\\.").length == 4 && pattern.matcher(domain).find()){
                for(String part: domain.split("\\.")) {
                    if(part.length() == 0) {
                        printError(originalAddress, "Invalid domain");
                        return null;
                    }
                    else if(Integer.parseInt(part) > 256){
                        printError(originalAddress, "Invalid domain: byte out of range");
                        return null;
                    }
                }
                return shortAddress;
            }
            else{
                printError(originalAddress, "Invalid characters");
                return null;
            }
        }

        Pattern patternDomain = Pattern.compile("^[.a-z0-9]+$");

        if(!patternDomain.matcher(domain).find()){
            printError(originalAddress, "Invalid characters");
            return null;
        }

        String[] splitDomain = domain.split("\\.");

        if (splitDomain[splitDomain.length - 1].length() == 3) {
            if (splitDomain[splitDomain.length - 1].equals("com")) {
                return shortAddress;
            } else {
                printError(originalAddress, "Invalid extension");
                return null;
            }
        }


        String extension = splitDomain[splitDomain.length - 2] + "." + splitDomain[splitDomain.length - 1];
        extension = extension.replace(" ", "");

        int extensionLength = extension.length();

        if (extensionLength == 6) {
            if (extension.equals("com.au")) {
                return shortAddress;
            } else {
                printError(originalAddress, "Invalid extension");
                return null;
            }
        }
        else if (extensionLength == 5) {
            if (extension.equals("co.ca") || extension.equals("co.uk") || extension.equals("co.us") || extension.equals("co.nz")) {
                return shortAddress;
            } else {
                printError(originalAddress, "Invalid extension");
                return null;
            }
        }
        else {
            printError(originalAddress, "Invalid extension");
            return null;
        }
    }

    public void printError(String emailAddress, String errorMessage){
        System.out.println(emailAddress + " <- " + errorMessage);
    }
}
