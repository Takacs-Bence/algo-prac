import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class Triplet {

    static long countTriplets(List<Long> arr, long r) {
        long result = 0;

        Map<Long, Long> map = new HashMap<>();
        for (int i = arr.size() -1; i >= 0; i--) {
            long c = arr.get(i);

            if (c != 1 && c % r != 0) {
                continue;
            }

            long cr = c * r;
            long crr = cr * r;

            Long one = map.get(cr);
            Long sec = map.get(crr);

            if (one != null && sec != null) {
                result = result + (one * sec);
            }

            if (map.containsKey(c)) {
                long count = map.get(c);
                map.put(c, ++count);
            } else {
                map.put(c, 1L);
            }
        }

        return result;
    }

    static long countTripletss(List<Long> arr, long r) {
        Map<Long, Long> rightMap = getOccurenceMap(arr);
        Map<Long, Long> leftMap = new HashMap<>();
        long result = 0;

        for (long val : arr) {
            long countLeft = 0;
            long countRight = 0;
            long lhs = 0;
            long rhs = val * r;
            if (val % r == 0) {
                lhs = val / r;
            }
            Long occurence = rightMap.get(val);
            rightMap.put(val, occurence - 1L);

            if (rightMap.containsKey(rhs)) {
                countRight = rightMap.get(rhs);
            }
            if (leftMap.containsKey(lhs)) {
                countLeft = leftMap.get(lhs);
            }
            result += countLeft * countRight;
            insertIntoMap(leftMap, val);
        }
        return result;
    }

    private static Map<Long, Long> getOccurenceMap(List<Long> test) {
        Map<Long, Long> occurenceMap = new HashMap<>();
        for (long val : test) {
            insertIntoMap(occurenceMap, val);
        }
        return occurenceMap;
    }

    private static void insertIntoMap(Map<Long, Long> occurenceMap, Long val) {
        if (!occurenceMap.containsKey(val)) {
            occurenceMap.put(val, 1L);
        } else {
            Long occurence = occurenceMap.get(val);
            occurenceMap.put(val, occurence + 1L);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("./test1.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./output1.txt"));

        String[] nr = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(nr[0]);

        long r = Long.parseLong(nr[1]);

        List<Long> arr = Stream.of(bufferedReader.readLine().replaceAll("\\s+$", "").split(" "))
                .map(Long::parseLong)
                .collect(toList());

        long ans = countTriplets(arr, r);

        bufferedWriter.write(String.valueOf(ans));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}
