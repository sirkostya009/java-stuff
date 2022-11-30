package org.sirkostya009;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

@RequiredArgsConstructor
public class DatabaseManager {

    private final Path databaseDirectory;
    private final Map<String, List<Double>> violations = new HashMap<>();

    public void collectStats(File out) throws IOException {
        for (var file : databaseDirectory.toFile().listFiles())
            try (var parser = new JsonFactory().createParser(file)) {
                var lastType = "";

                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    if ("type".equals(parser.getCurrentName())) {
                        parser.nextToken();
                        lastType = parser.getValueAsString();
                        violations.putIfAbsent(lastType, new ArrayList<>());
                    }
                    if ("fine_amount".equals(parser.getCurrentName())) {
                        parser.nextToken();
                        violations.get(lastType).add(parser.getDoubleValue());
                    }
                }
            }

        var result = new HashMap<String, Double>(violations.size());
        violations.forEach((name, fines) -> result.put(name, fines.stream().reduce(Double::sum).orElseThrow()));

        var mapper = new XmlMapper();
        mapper.enable(INDENT_OUTPUT);

        mapper.writeValue(
                out,
                new FinesWrapper(result.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).map(Fine::new).toList())
        );

        violations.clear();
    }

}
