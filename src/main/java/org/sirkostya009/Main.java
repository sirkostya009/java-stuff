package org.sirkostya009;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException, URISyntaxException {
        new AwesomeXMLParser().parse(
                Main.class.getResourceAsStream("/persons.xml"),
                // this will create a file in project's root directory
                new FileOutputStream("./people.xml")
        );

        new DatabaseManager(Path.of(Main.class.getResource("/database/").toURI())).collectStats(new File("./db_stats.xml"));
    }

}
