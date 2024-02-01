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

class Cache {

    /**
     * An entity to hold bin range details. A bin range is a pair of 12 digit numbers that
     * mark the boundaries of the range which is maped to other bin range properties such
     * as a card type. The range boundaries are inclusive.
     */
    static final class BinRange {
        final String start;
        final String end;
        final String cardType;

        BinRange(String start, String end, String cardType) {
            this.start = start;
            this.end = end;
            this.cardType = cardType;
        }
    }

    /*
    *
    *
    * 4111111111111111
400000000000,499999999999,visa
500000000000,599999999999,mc
 visa
    * */

    interface CardTypeCache {
        /**
         * @param cardNumber 12 to 23 digit card number.
         * @return the card type for this cardNumber or null if the card number does not
         * fall into any valid bin ranges.
         */
        String get(String cardNumber);
    }

    static class EfficientCardTypeCache implements CardTypeCache {

        private final NavigableMap<String, BinRange> cache = new TreeMap<>();

        public EfficientCardTypeCache(List<BinRange> binRanges) {
            for (BinRange binRange : binRanges) {
                cache.put(binRange.start, binRange);
            }
        }

        @Override
        public String get(String cardNumber) {
            Map.Entry<String, BinRange> startRangeEntry = cache.floorEntry(cardNumber);
            if (startRangeEntry != null) {
                String binRelevantPart = cardNumber.substring(0, startRangeEntry.getValue().start.length());
                if (Long.parseLong(binRelevantPart) <= Long.parseLong(startRangeEntry.getValue().end)) {
                    String cardType = startRangeEntry.getValue().cardType;
                    return cardType;
                }
            }

            return null;
        }
    }

    /**
     * @param binRanges the list of card bin ranges to build a cache from.
     * @return an implementation of CardTypeCache.
     */
    public static CardTypeCache buildCache(List<BinRange> binRanges) {
        return new EfficientCardTypeCache(binRanges);
    }

}

public class CardTypeLookupCache {
    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("./cache.txt"));

        List<Cache.BinRange> binRanges = new ArrayList<>();

        String cardNumber = bufferedReader.readLine();

        String binRange = bufferedReader.readLine();
        while (binRange != null) {
            String[] bin = binRange.split(",");

            String start = bin[0];
            String end = bin[1];
            String cardType = bin[2];
            binRanges.add(new Cache.BinRange(start, end, cardType));

            binRange = bufferedReader.readLine();
        }

        Cache.CardTypeCache cache = Cache.buildCache(binRanges);
        System.out.println(cache.get(cardNumber));

        bufferedReader.close();
    }
}