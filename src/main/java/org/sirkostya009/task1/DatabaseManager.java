package org.sirkostya009.task1;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

@RequiredArgsConstructor
public class DatabaseManager {

    private final Path databaseDirectory;

    private void parseFile(File file, Map<String, AtomicReference<Double>> violations) throws IOException {
        try (var parser = new JsonFactory().createParser(file)) {
            var lastType = "";

            while (parser.nextToken() != JsonToken.END_ARRAY) {
                if ("type".equals(parser.getCurrentName())) {
                    parser.nextToken();
                    lastType = parser.getValueAsString();
                    violations.putIfAbsent(lastType, new AtomicReference<>(.0));
                }
                if ("fine_amount".equals(parser.getCurrentName())) {
                    parser.nextToken();
                    violations.get(lastType).getAndAccumulate(parser.getDoubleValue(), Double::sum);
                }
            }
        }
    }

    public void collectFinesMultiThreaded(File out) throws IOException, InterruptedException {
        var executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        var violations = new ConcurrentHashMap<String, AtomicReference<Double>>();

        var callables = Stream.of(databaseDirectory.toFile().listFiles())
                .map(file -> (Callable<Boolean>) () -> {
                    try {
                        parseFile(file, violations);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    return null;
                }).toList();

        System.out.print("Multi-threaded run: ");
        var now = Instant.now();

        executor.invokeAll(callables);

        var alsoNow = Instant.now();
        System.out.println(alsoNow.toEpochMilli() - now.toEpochMilli());

        var mapper = new XmlMapper();
        mapper.enable(INDENT_OUTPUT);

        mapper.writeValue(
                out,
                new FinesWrapper(violations.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue((ref1, ref2) -> Double.compare(ref2.get(), ref1.get())))
                        .map(Fine::new).toList())
        );

        executor.shutdown();
    }

    public void collectStatsSingleThreaded(File out) throws IOException {
        var violations = new HashMap<String, Double>();

        System.out.print("Single-threaded run: ");
        var now = Instant.now();

        for (var file : databaseDirectory.toFile().listFiles())
            try (var parser = new JsonFactory().createParser(file)) {
                var lastType = "";

                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    if ("type".equals(parser.getCurrentName())) {
                        parser.nextToken();
                        lastType = parser.getValueAsString();
                        violations.putIfAbsent(lastType, .0);
                    }
                    if ("fine_amount".equals(parser.getCurrentName())) {
                        parser.nextToken();
                        violations.put(lastType, violations.get(lastType) + parser.getDoubleValue());
                    }
                }
            }

        var alsoNow = Instant.now();
        System.out.println(alsoNow.toEpochMilli() - now.toEpochMilli());

        var mapper = new XmlMapper();
        mapper.enable(INDENT_OUTPUT);

        mapper.writeValue(
                out,
                new FinesWrapper(violations.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .map(entry -> new Fine(entry.getKey(), entry.getValue())).toList())
        );
    }

}
