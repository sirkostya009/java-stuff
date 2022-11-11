package org.sirkostya009;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static List<Integer> filterNegativesOut(List<Integer> integers) {
        return integers.stream().filter(integer -> integer >= 0).toList();
    }

    /**
     * @param strings a list of strings with numerous #hashtags
     * @return a list of top 5 most popular tags sorted in ascending order
     */
    public static Map<String, Integer> top5Hashtags(List<String> strings) {
        var result = new HashMap<String, Integer>();

        strings.forEach(string -> {
             var parsed = List.of(string.split(" "));

             var tags = new HashSet<String>();
             parsed.forEach(word -> {
                 if (word.startsWith("#")) tags.add(word.toLowerCase());
             });

             tags.forEach(tag -> {
                 result.putIfAbsent(tag, 0);
                 result.put(tag, result.get(tag) + 1);
             });
        });

        return result
                .entrySet()
                .stream()
                .sorted((c1, c2) -> Integer.compare(c2.getValue(), c1.getValue()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public static <T extends Shape> List<T> sortShapes(List<T> shapes) {
        return shapes.stream()
                .sorted()
                .toList();
    }

}
