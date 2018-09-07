package team.hdt.blockadia.game_engine.core.util;

public class ChunkWorker implements Runnable {
    private final Chunk chunk;

    public ChunkWorker(final Chunk chunk) {
        this.chunk = chunk;
    }

    @Override
    public void run() {
        chunk.rebuild();
    }
}