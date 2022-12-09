package org.sirkostya009.task1;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        var db = new DatabaseManager(Path.of("./database/"));
        var out = new File("./db_stats.xml");

        // the reason we are calling -SingleThreaded two times
        // is because, for some reason, first run (of no matter
        // what method) takes the longest to run on my machine.
        // Although the Executor one usually runs slightly faster.
        db.collectStatsSingleThreaded(out);

        db.collectStatsSingleThreaded(out);

        db.collectFinesWithFutures(out);

        db.collectFinesWithExecutorService(out);
    }

}
