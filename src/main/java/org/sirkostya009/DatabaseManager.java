package org.sirkostya009;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

@RequiredArgsConstructor
public class DatabaseManager {

    private final Path databaseDirectory;
    private final Map<String, AtomicReference<Double>> violations = new ConcurrentHashMap<>();

    private void parseFile(File file) throws IOException {
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

    public void collectFinesMultithreaded(File out) throws IOException {
        var executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (var file : databaseDirectory.toFile().listFiles())
            CompletableFuture.supplyAsync(() -> {
                try {
                    parseFile(file);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                return true;
            }, executor);

        var mapper = new XmlMapper();
        mapper.enable(INDENT_OUTPUT);

        mapper.writeValue(
                out,
                new FinesWrapper(violations.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue((ref1, ref2) -> Double.compare(ref2.get(), ref1.get())))
                        .map(Fine::new).toList())
        );

        violations.clear();
        executor.shutdown();
    }

}
