import java.util.ArrayList;
import java.util.Scanner;

/*Input is via 1 line with 2 numbers separated by a space.
  First value is n and second value is k
 */

public class CountingItUp {

    public static void main(String[] args) {
	    CountingItUp main = new CountingItUp();
        ArrayList<String> lines = new ArrayList<>();

	    long n = -1, k = -1;

        Scanner scanner = new Scanner(System.in);

        while(scanner.hasNextLine()) {
            lines.add(scanner.nextLine());
        }

        scanner.close();

        for(String line: lines) {
            String[] split = line.trim().split("\\s+");

            long[] numbers = new long[split.length];

            for (int i = 0; i < split.length; i++) {
                numbers[i] = Long.parseLong(split[i]);
            }

            main.calculateFactorial(numbers[0], numbers[1]);
        }
    }

    public void calculateFactorial(long n, long k){
        long value = 1;

        long current = -1;
        ArrayList<Long> factors = new ArrayList<>();

        if(k < (n - k)){
            k = n - k;
        }

        for(long l = 1; l - 1 < n - k; l++){
            factors.add(l);
        }

        for(long i = n; i > k; i--){
            current = i;

            for(int m = factors.size() - 1; m >= 0; m--) {
                long factor = factors.get(m);

                if(current % factor == 0) {
                    current = current / factor;
                    factors.remove(m);
                }

            }

            value *= current;

            for(int m = factors.size() - 1; m >= 0; m--) {
                long factor = factors.get(m);

                if(value % factor == 0) {
                    value = value / factor;
                    factors.remove(m);
                }
            }

        }
        System.out.println((String.valueOf(value)));
    }
}
