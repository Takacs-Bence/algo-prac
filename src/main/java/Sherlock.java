import java.io.*;
import java.util.*;
import java.util.stream.*;

class Result {

    public static int sherlockAndAnagrams(String s) {
        Map<String, Integer> substringCountMap = new HashMap<>();

        for (int i = 0; i < s.length(); i++) {
            for (int j = 0; j < s.length() - i; j++) {
                String tmp = s.substring(i, i + j + 1);
                System.out.println(tmp);
                String substring = sortAlphabetically(tmp);
                substringCountMap.merge(substring, 1, Integer::sum);
            }
        }

        return countAnagrams(substringCountMap);
    }

    private static String sortAlphabetically(String toSort) {
        char[] charArray = toSort.toCharArray();
        Arrays.sort(charArray);
        return new String(charArray);
    }

    private static int countAnagrams(Map<String, Integer> substringCountMap) {
        Collection<Integer> values = substringCountMap.values();
        int res = values.stream()
                .filter(i -> i > 1)
                .map(n -> (n * (n - 1)) / 2)
                .reduce(0, Integer::sum);
        System.out.println(res);
        return res;
    }

}

public class Sherlock {
    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new FileReader("./test.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("./output.txt"));

        String trim = bufferedReader.readLine().trim();
        int q = Integer.parseInt(trim);

        IntStream.range(0, q).forEach(qItr -> {
            try {
                String s = bufferedReader.readLine();

                int result = Result.sherlockAndAnagrams(s);

                bufferedWriter.write(String.valueOf(result));
                bufferedWriter.newLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        bufferedReader.close();
        bufferedWriter.close();
    }
}
