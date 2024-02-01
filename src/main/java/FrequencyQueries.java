import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class FrequencyQueries {

    static Map<Integer, Set<Integer>> freq_map = new HashMap<>();
    static Map<Integer, Integer> value_map = new HashMap<>();

    static List<Integer> result = new ArrayList<>();

    /*      1, x: Insert x in your data structure.
            2, y: Delete one occurence of y from your data structure, if present.
            3, z: Check if any integer is present whose frequency is exactly . If yes, print 1 else 0.*/
    static List<Integer> freqQuery(List<List<Integer>> queries) {
        for (List<Integer> query : queries) {
            Integer value = query.get(1);
            switch (query.get(0)) {
                case 1:
                    add(value);
                    break;
                case 2:
                    delete(value);
                    break;
                case 3:
                    check_freq(value);
                    break;
                default:
                    throw new IllegalStateException("the first number of the query must be either 1,2 or 3");
            }
        }

        return result;
    }

    private static void add(Integer value) {
        Integer frequency = value_map.get(value);
        if (frequency == null) {
            frequency = 0;
        }

        int freq = frequency;
        int newFreq = freq + 1;

        value_map.put(value, newFreq);

        Set<Integer> values = freq_map.get(freq);
        if (values != null) {
            values.remove(value);
            freq_map.put(freq, values);
        }

        values = freq_map.get(newFreq);
        if (values == null) {
            values = new HashSet<>();
        }
        values.add(value);
        freq_map.put(newFreq, values);
    }

    private static void delete(Integer value) {
        Integer frequency = value_map.get(value);
        if (frequency == null) {
            frequency = 0;
        }

        int freq = frequency;
        int newFreq = freq - 1;

        // don't decrement if we are at 0 freq already
        if (freq >= 1) {
            value_map.put(value, newFreq);
        }

        Set<Integer> values = freq_map.get(freq);
        if (values != null) {
            values.remove(value);
            freq_map.put(freq, values);
        }

        values = freq_map.get(newFreq);
        if (values == null) {
            values = new HashSet<>();
        }
        values.add(value);
        freq_map.put(newFreq, values);
    }

    private static void check_freq(Integer value) {
        int ret = 0;
        Set<Integer> values = freq_map.get(value);
        if (values != null && !values.isEmpty()) {
            ret = 1;
        }
        result.add(ret);
        System.out.println(ret);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("./freq_input.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./freq_output.txt"));

        int q = Integer.parseInt(bufferedReader.readLine().trim());

        List<List<Integer>> queries = new ArrayList<>();

        IntStream.range(0, q).forEach(i -> {
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

        List<Integer> ans = freqQuery(queries);

        bufferedWriter.write(
                ans.stream()
                        .map(Object::toString)
                        .collect(joining("\n"))
                        + "\n"
        );

        bufferedReader.close();
        bufferedWriter.close();
    }
}
