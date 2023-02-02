package org.sirkostya009;

import org.sirkostya009.shapes.Shape;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    /**
     * @param integers a list of integers, null-insecure
     * @return a sorted list without negative integers
     */
    public static int[] filterNegativesOut(int[] integers) {
        return Arrays.stream(integers).filter(value -> value >= 0).sorted().toArray();
    }

    /**
     * @param strings a list of strings with numerous #hashtags
     * @return a Map of 5 most popular tags
     */
    public static Map<String, Integer> top5Hashtags(List<String> strings) {
        return strings.stream()
                .flatMap(string -> Arrays.stream(string.split(" ")).distinct())
                .filter(word -> word.startsWith("#"))
                .reduce(new HashMap<String, Integer>(), (map, hashTag) -> {
                    map.putIfAbsent(hashTag, 0);
                    map.put(hashTag, map.get(hashTag) + 1);
                    return map;
                }, (map, ignored) -> map)
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    /**
     * @param shapes a list of shapes
     * @return sorted list of shapes
     */
    public static List<Shape> sortShapes(Collection<Shape> shapes) {
        return shapes.stream().sorted().toList();
    }

}
