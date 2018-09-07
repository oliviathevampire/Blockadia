package team.hdt.blockadia.game_engine.core.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChunksWorkersExecutorService {
    public final ExecutorService es;

    public ChunksWorkersExecutorService() {
        this.es = Executors.newFixedThreadPool(1);
    }

    public void add_worker(ChunkWorker worker) {
        es.execute(worker);
    }
}