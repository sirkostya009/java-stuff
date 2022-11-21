package org.sirkostya009;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws IOException {
        var parser = new AwesomeXMLParser();

        parser.parse(
                parser.getClass().getResourceAsStream("/persons.xml"),
                // this will create a file inside target/ directory, and, for some reason, also in root directory of the project
                new FileOutputStream(Objects.requireNonNull(parser.getClass().getResourceAsStream("/people.xml")).toString())
        );
    }

}
