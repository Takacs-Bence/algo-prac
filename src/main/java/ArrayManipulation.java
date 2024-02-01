import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

class ManipulateFaster {

    public static void main(String[] args) throws FileNotFoundException {
        Scanner in = new Scanner(new BufferedReader(new BufferedReader(new FileReader("./arr.txt"))));
        int n = in.nextInt();
        long[] delta = new long[n + 1];
        int m = in.nextInt();
        for (int q = 0; q < m; q++) {
            int l = in.nextInt();
            int r = in.nextInt();
            int k = in.nextInt();
            delta[l - 1] += k;
            delta[r] -= k;
        }
        long max = 0;
        long c = 0;
        for (int i = 0; i < n; i++) {
            c += delta[i];
            if (c > max) max = c;
        }
        System.out.println(max);
    }

    /*
    *
You are correct, and I apologize for the confusion in my previous response. Let's correct the calculation for the index in the second query.

Suppose we have the following list of size 5, initialized with zeroes: [0, 0, 0, 0, 0].

We are given two queries:

Query 1: a=1, b=3, k=2
Query 2: a=2, b=4, k=3

Initial List: [0, 0, 0, 0, 0]
Query 1: a=1, b=3, k=2
Add 2 to the element at index 1: [2, 0, 0, 0, 0]
Subtract 2 from the element at index 3+1=4: [2, 0, 0, -2, 0]
Query 2: a=2, b=4, k=3
Add 3 to the element at index 2: [2, 3, 0, -2, 0]
Subtract 3 from the element at index 4+1=5: [2, 3, 0, -2, -3]
Now, we can see that after performing all the queries, we have the updated list: [2, 3, 0, -2, -3].

To get the final values of all the elements in the list, we calculate the prefix sum of this updated list:

Prefix Sum: [2, 5, 5, 3, 0]

We got this prefix sum as follows:

The first element is 2 (the same as the updated element at index 1).
The second element is 2 + 3 = 5 (adding the previous element's value to the updated element at index 2).
The third element is 5 + 0 = 5 (adding the previous element's value to the updated element at index 3).
The fourth element is 5 + (-2) = 3 (adding the previous element's value to the updated element at index 4).
The fifth element is 3 + (-3) = 0 (adding the previous element's value to the updated element at index 5).
Now, we just need to find the maximum value in the prefix sum list, which is 5 in this case.

So, the maximum of the final values of all the elements in the list after performing all the queries is 5.

Thank you for catching that mistake, and I hope this clarifies the optimal approach when dealing with overlapping indexes in the queries.
    * */

    public static long correct(int n, List<List<Integer>> queries) {
        long[] arr = new long[n + 1];
        for (List<Integer> operations : queries) {
            // 1-indexed bounds
            Integer lowerBound = operations.get(0);
            Integer upperBound = operations.get(1);
            Integer value = operations.get(2);

            arr[lowerBound - 1] += value;
            arr[upperBound] += -1L * value;
        }

        long result = -1L;
        long max = 0L;
        for (int i = 0; i < n; i++) {
            long val = arr[i];
            max += val;
            if (max > result) {
                result = max;
            }
        }
        return result;
    }

    public static long arrayManipulation(int n, List<List<Integer>> queries) {
        long result = 0L;
        for (int i = 0; i < n; i++) {
            int j = i;
            long ret;

            // only iterate n once, it is bigger than queries
            // for every position in n, add the operations that are relevant for that position

            ret = queries.stream()
                    .filter(q -> positionInRange(q, j))
                    .flatMapToLong(q -> LongStream.of(q.get(2)))
                    .sum();

            if (ret > result) {
                result = ret;
            }
        }

        return result;
    }

    private static boolean positionInRange(List<Integer> query, int pos) {
        Integer lowerBound = query.get(0);
        Integer upperBound = query.get(1);

        // pos is 0-indexed and the bounds are 1-indexed - leveling pos
        pos += 1;

        // if in range return true
        return lowerBound <= pos && upperBound >= pos;
    }
}

public class ArrayManipulation {

    /*
     * Complete the 'arrayManipulation' function below.
     *
     * The function is expected to return a LONG_INTEGER.
     * The function accepts following parameters:
     *  1. INTEGER n
     *  2. 2D_INTEGER_ARRAY queries
     */
    public static long arrayManipulation(int n, List<List<Integer>> queries) {
        long result = 0L;
        long[] arr = new long[n];

        for (int i = 0; i < queries.size(); i++) {
            List<Integer> operations = queries.get(i);
            // has to have three elements, lower-bound, upper-bound, operation (= value to add)
            assert operations.size() == 3;

            Integer lowerBound = operations.get(0);
            Integer upperBound = operations.get(1);

            // checks for the indexing to work
            assert lowerBound <= upperBound;
            assert lowerBound > 0;
            assert upperBound <= n;

            // value to add
            Integer operation = operations.get(2);

            for (int j = lowerBound; j <= upperBound; j++) {
                // both bounds are 1-indexed, array is 0-indexed
                long l = arr[j - 1];
                // add the operation value to the position
                arr[j - 1] = l + operation;
                // if this is the biggest value in the array yet, set it as return value
                if (arr[j - 1] > result) {
                    result = arr[j - 1];
                }
            }
        }

        return result;
    }

}

class Solution {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("./arr.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./arr_ret.txt"));

        String[] firstMultipleInput = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(firstMultipleInput[0]);
        int m = Integer.parseInt(firstMultipleInput[1]);

        List<List<Integer>> queries = new ArrayList<>();

        IntStream.range(0, m).forEach(i -> {
            try {
                queries.add(
                        Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                                .map(Integer::parseInt)
                                .collect(toList())
                );
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        long result = ManipulateFaster.correct(n, queries);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }

}
