package org.sirkostya009.task1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        var db = new DatabaseManager(Path.of("./database/"));
        var out = new File("./db_stats.xml");

        db.collectFinesMultiThreaded(out);

        db.collectStatsSingleThreaded(out);
    }

}
