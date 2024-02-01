import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

/*Sample Input 0
    STDIN               Function
    -----               --------
    9 5                 expenditure[] size n =9, d = 5
    2 3 4 2 3 6 8 4 5   expenditure = [2, 3, 4, 2, 3, 6, 8, 4, 5]
    Sample Output 0
    2
/*

/*
Sample Input 1
5 4
1 2 3 4 4
Sample Output 1
0
 */
public class BankFraud {


    public static int activityNotifications(List<Integer> exp, int d) {
        int result = 0;
        int[] days = new int[d];
        for (int i = 0; i < exp.size(); i++) {

            // fill array and skip until we reach valid trailing days
            if (i < d) {
                days[i] = exp.get(i);
                continue;
            }

            // sort
            int[] sorted = Arrays.copyOf(days, d);
            Arrays.sort(sorted);

            // find median
            double median;
            if (sorted.length % 2 == 1) {
                median = sorted[sorted.length / 2];
            } else {
                median = (sorted[(sorted.length / 2)] + sorted[((sorted.length / 2) - 1)]) / 2.0;
            }

            System.out.println("Median: " + median + " - Spent: " + exp.get(i));
            // increment result if sus
            if ((median * 2) <= exp.get(i)) {
                result++;
                System.out.println("találat");
            }



            // modify days for next iter
            int[] copy = new int[d];
            System.arraycopy(days, 1, copy, 0, d - 1);

            try {
                copy[d - 1] = exp.get(i);
            } catch (Exception ig) {

            }
            days = copy;
        }

        return result;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("./fraud.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./fraud1.txt"));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);

        int d = Integer.parseInt(firstMultipleInput[1]);

        List<Integer> expenditure = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Integer::parseInt)
                .collect(toList());

        int result = BankFraud.activityNotifications(expenditure, d);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
