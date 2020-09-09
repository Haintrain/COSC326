import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserFilenames {

    public static void main(String[] args) {
        ArrayList<String> listOfFiles = new ArrayList<>();
        ArrayList<Integer[]> storage = new ArrayList<>();

        try (Stream<Path> walk = Files.walk(Paths.get(args[0]))) {

            List<String> result = walk.filter(Files::isRegularFile)
                    .map(x -> x.getFileName().toString()).collect(Collectors.toList());

            listOfFiles = new ArrayList<>(result);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(listOfFiles);

        int jobNumber, jobSite, labDesk;

        int k = 0;

        for(String current: listOfFiles){
            String digits = current.replaceAll("\\D+","");

            jobSite = Integer.parseInt(digits.substring(0,2));
            labDesk = Integer.parseInt(digits.substring(2,4));
            jobNumber = Integer.parseInt(digits.substring(4,6));

            Integer[] tempArray = new Integer[4];

            tempArray[0] = k;
            tempArray[1] = jobSite;
            tempArray[2] = labDesk;
            tempArray[3] = jobNumber;

            storage.add(k, tempArray);
            k++;
        }

        Collections.sort(storage, new JobComparator());
        ArrayList<String> output = new ArrayList<>();

        for(int i = 0; i < listOfFiles.size(); i++) {
            Integer[] temp = storage.get(i);
            int j = temp[0];

            try (Stream<Path> walk = Files.walk(Paths.get(args[0]))) {
                final String fileName = listOfFiles.get(j);

                List<String> result = walk.filter(Files::isRegularFile)
                        .filter(x -> x.toString().contains(fileName) && x.getFileName().toString().equals(fileName))
                        .map(p -> p.toString())
                        .collect(Collectors.toList());

                for(String filePath: result) {
                    File file = new File(filePath);
                    Scanner sc = new Scanner(file);

                    while (sc.hasNextLine()) {
                        output.add(sc.nextLine());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter result = new FileWriter("filename.txt");
            for(String line: output) {
                result.write(line + "\n");

            }
            result.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}

class JobComparator implements Comparator<Integer[]> {

    @Override
    public int compare(Integer[] x, Integer[] y) {
        if (x[1].equals(y[1])) {
            if (x[2].equals(y[2])) {
                return x[3].compareTo(y[3]);
            }
            else {
                return x[2].compareTo(y[2]);
            }
        }
        return x[1].compareTo(y[1]);
    }
}