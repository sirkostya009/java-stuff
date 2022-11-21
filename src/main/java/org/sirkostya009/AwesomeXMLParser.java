package org.sirkostya009;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AwesomeXMLParser {

    private final static String ELIMINATE = "surname";
    private final static String APPEND = "name";

    private final ArrayList<String> lines = new ArrayList<>();

    private String extractSurname(String line) {
        var stringAfterSurname = line.split(ELIMINATE)[1]; // [1] is the rest of the string

        return stringAfterSurname.split("\"")[1]; // [1] is the surname
    }

    private String modifyLine(String line, String surname) {
        // will remove surname=""
        if (line.contains(ELIMINATE) && line.contains(surname)) {
            var parts = line.split(ELIMINATE);
            var firstPart = parts[0];
            var secondPart = parts[1].split(surname)[1];

            // substring(1) will remove an unnecessary =
            line = firstPart + secondPart.substring(1).trim();
        }

        // will append surname to name
        if (line.contains(APPEND)) {
            var parts = line.split(APPEND);
            var firstPart = parts[0];
            var secondPart = new ArrayList<>(List.of(parts[1].split("\"")));

            firstPart += APPEND;

            // pops equals and surname strings
            if (secondPart.size() > 1)
                firstPart += secondPart.remove(0) + '"';
            if (secondPart.size() == 1)
                firstPart += (secondPart.remove(0) + ' ' + surname + '"');

            line = firstPart + String.join("\"", secondPart);
        }

        return line;
    }

    public void parse(InputStream from, OutputStream to) throws IOException {
        try(var scanner = new Scanner(from);
            var out = new BufferedOutputStream(to)) {
            while (scanner.hasNext()) {
                var line = scanner.nextLine();

                lines.add(line);

                if (line.contains(ELIMINATE)) {
                    var surname = extractSurname(line);

                    for (var i = lines.size() - 1; i >= 0; --i)
                        lines.set(i, modifyLine(lines.get(i), surname));
                }

                if (line.contains(">")) {
                    // if line contains a closing tag, it means we're onto end of the object/file
                    // dump everything from array to the output buffer
                    lines.forEach(s -> {
                        try {
                            out.write((s + '\n').getBytes());
                        } catch (IOException ignored) {
                        }
                    });

                    lines.clear();
                    out.flush();
                }
            }
        }
    }

}