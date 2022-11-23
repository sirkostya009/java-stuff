package org.sirkostya009;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.simpleframework.xml.ElementMap;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public class DatabaseManager {

    private final URI uri;

    @ElementMap(entry = "type", value = "fines", inline = true, attribute = true)
    private final Map<String, List<Double>> violations = new HashMap<>();

    private Statistic[] toStatistics() {
        var result = new Statistic[violations.size()];
        var i = new AtomicInteger(0);

        violations.forEach((type, fines) -> result[i.getAndIncrement()] = new Statistic(type, fines));

        return result;
    }

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

        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        // we don't use stream api toArray() or toList() only
        // because we want root element to be named Statistics
        mapper.writeValue(out, toStatistics());
    }

}
