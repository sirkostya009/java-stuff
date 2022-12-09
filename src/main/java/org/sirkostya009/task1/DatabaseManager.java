package org.sirkostya009.task1;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

public class DatabaseManager {

    private final File[] allFiles;

    public DatabaseManager(Path pathToDatabase) {
        this.allFiles = pathToDatabase.toFile().listFiles();
    }

    private final ObjectMapper mapper = new XmlMapper();

    {
        mapper.enable(INDENT_OUTPUT);
    }

    private void parseFile(File file, Map<String, AtomicReference<Double>> violations) throws IOException {
        try (var parser = new JsonFactory().createParser(file)) {
            var previousType = "";

            while (parser.nextToken() != JsonToken.END_ARRAY) {
                if ("type".equals(parser.getCurrentName())) {
                    parser.nextToken();
                    previousType = parser.getValueAsString();
                    violations.putIfAbsent(previousType, new AtomicReference<>(.0));
                }
                if ("fine_amount".equals(parser.getCurrentName())) {
                    parser.nextToken();
                    violations.get(previousType).getAndAccumulate(parser.getDoubleValue(), Double::sum);
                }
            }
        }
    }

    private Map<String, Double> parseFile(File file)  {
        var violations = new HashMap<String, Double>();

        try (var parser = new JsonFactory().createParser(file)) {
            iterateParser(violations, parser);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return violations;
    }

    private void iterateParser(Map<String, Double> violations, JsonParser parser) throws IOException {
        var previousType = "";

        while (parser.nextToken() != JsonToken.END_ARRAY) {
            if ("type".equals(parser.getCurrentName())) {
                parser.nextToken();
                previousType = parser.getValueAsString();
                violations.putIfAbsent(previousType, .0);
            }
            if ("fine_amount".equals(parser.getCurrentName())) {
                parser.nextToken();
                violations.put(previousType, violations.get(previousType) + parser.getDoubleValue());
            }
        }
    }

    private FinesWrapper wrap(Map<String, Double> map) {
        return new FinesWrapper(map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(entry -> new Fine(entry.getKey(), entry.getValue())).toList());
    }

    public void collectFinesWithFutures(File out) throws ExecutionException, InterruptedException, IOException {
        var future = CompletableFuture.completedFuture(new HashMap<String, Double>());

        System.out.print("Futures run: ");
        var now = Instant.now();

        for (var file : allFiles)
            future = future.thenCombineAsync(
                    CompletableFuture.supplyAsync(() -> parseFile(file)), (hashMap, result) -> {
                        result.forEach((string, aDouble) -> {
                            hashMap.putIfAbsent(string, .0);
                            hashMap.put(string, hashMap.get(string) + result.get(string));
                        });
                        return hashMap;
                    });

        future.get();
        var alsoNow = Instant.now();
        System.out.println(alsoNow.toEpochMilli() - now.toEpochMilli());

        mapper.writeValue(out, wrap(future.get()));
    }

    public void collectFinesWithExecutorService(File out) throws IOException, InterruptedException {
        var executor = Executors.newFixedThreadPool(8);
        var violations = new ConcurrentHashMap<String, AtomicReference<Double>>();

        var callables = Stream.of(allFiles)
                .map(file -> (Callable<Boolean>) () -> {
                    try {
                        parseFile(file, violations);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }).toList();

        System.out.print("Executor run: ");
        var now = Instant.now();

        executor.invokeAll(callables);

        var alsoNow = Instant.now();
        System.out.println(alsoNow.toEpochMilli() - now.toEpochMilli());

        mapper.writeValue(
                out,
                new FinesWrapper(violations.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue((ref1, ref2) -> Double.compare(ref2.get(), ref1.get())))
                        .map(entry -> new Fine(entry.getKey(), entry.getValue().get())).toList())
        );

        executor.shutdown();
    }

    public void collectStatsSingleThreaded(File out) throws IOException {
        var violations = new HashMap<String, Double>();

        System.out.print("Single-threaded run: ");
        var now = Instant.now();

        for (var file : allFiles)
            try (var parser = new JsonFactory().createParser(file)) {
                iterateParser(violations, parser);
            }

        var alsoNow = Instant.now();
        System.out.println(alsoNow.toEpochMilli() - now.toEpochMilli());

        mapper.writeValue(out, wrap(violations));
    }

}
