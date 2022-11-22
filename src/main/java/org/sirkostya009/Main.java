package org.sirkostya009;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Objects;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        var parser = new AwesomeXMLParser();

        parser.parse(
                parser.getClass().getResourceAsStream("/persons.xml"),
                // this will create a file inside target/ directory, and, for some reason, also in root directory of the project
                new FileOutputStream(new File(Objects.requireNonNull(parser.getClass().getResource("/people.xml")).toURI()))
        );
    }

}
