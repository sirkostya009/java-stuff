package org.sirkostya009;

import java.util.*;

public class Main {

    public static List<Integer> filterNegativesOut(List<Integer> integers) {
        return integers.stream().filter(integer -> integer >= 0).toList();
    }

    /**
     *
     * @param strings a list of strings with numerous #hashtags
     * @return a list of top 5 most popular tags sorted in ascending order
     */
    public static List<Map.Entry<String, Integer>> top5Hashtags(List<String> strings) {
        var total = new HashMap<String, Integer>();

        strings.forEach(string -> {
             var parsed = List.of(string.split(" "));

             var tags = new HashSet<String>();
             parsed.forEach(word -> {
                 if (word.startsWith("#")) tags.add(word.toLowerCase());
             });

             tags.forEach(tag -> {
                 total.putIfAbsent(tag, 0);
                 total.put(tag, total.get(tag) + 1);
             });
        });

        return total.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(5)
                .toList();
    }

    public static <T extends Shape> List<T> sortShapes(List<T> shapes) {
        return shapes.stream()
                .sorted()
                .toList();
    }

}
