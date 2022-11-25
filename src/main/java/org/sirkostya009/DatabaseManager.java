package org.sirkostya009;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.*;

import static com.fasterxml.jackson.databind.SerializationFeature.INDENT_OUTPUT;

@RequiredArgsConstructor
public class DatabaseManager {

    private final URI uri;
    private final Map<String, List<Double>> violations = new HashMap<>();

    public void collectStats(File out) throws IOException {
        for (var file : Path.of(uri).toFile().listFiles())
            try (var parser = new JsonFactory().createParser(file)) {
                String lastType = null;

                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    if ("type".equals(parser.getCurrentName())) {
                        parser.nextToken();
                        lastType = parser.getValueAsString();
                        violations.putIfAbsent(lastType, new ArrayList<>());
                    }
                    if ("fine_amount".equals(parser.getCurrentName())) {
                        parser.nextToken();
                        violations.get(lastType).add(Double.valueOf(parser.getValueAsString()));
                    }
                }
            }

        violations.values().forEach(list -> list.sort(Comparator.reverseOrder()));

        var mapper = new XmlMapper();

        mapper.enable(INDENT_OUTPUT);

        mapper.writeValue(
                out,
                new Database(
                        violations.entrySet().stream()
                                .map(entry -> new Statistic(entry.getKey(), entry.getValue())).toList()
                )
        );
    }

}
