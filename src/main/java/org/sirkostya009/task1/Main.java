package org.sirkostya009.task1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        var db = new DatabaseManager(Path.of("./database/"));
        var out = new File("./db_stats.xml");

        db.collectFinesWithFutures(out);

        db.collectFinesWithExecutorService(out);

        db.collectStatsSingleThreaded(out);
    }

}
