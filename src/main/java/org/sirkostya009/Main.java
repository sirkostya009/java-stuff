package org.sirkostya009;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.time.Instant;

public class Main {

    public static void main(String[] args) throws IOException {
        var now = Instant.now();
        new DatabaseManager(Path.of("./database/"))
                .collectFinesMultithreaded(new File("./db_stats.xml"));
        var alsoNow = Instant.now();

        System.out.println(alsoNow.toEpochMilli() - now.toEpochMilli());
    }

}
