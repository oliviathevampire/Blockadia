package team.hdt.sandboxgame.game_engine.common.world.gen.factory;

import team.hdt.sandboxgame.game_engine.common.util.interfaces.Nullable;
import team.hdt.sandboxgame.game_engine.common.world.Chunk;
import team.hdt.sandboxgame.game_engine.common.world.ChunkPos;
import team.hdt.sandboxgame.game_engine.common.world.World;
import team.hdt.sandboxgame.game_engine.common.world.gen.interfaces.IBiome;

import java.util.function.BiConsumer;

public class WorldFactory {

    private static WorldFactory INSTANCE = new WorldFactory();
    /**
     * world will be x amount of chunks into every direction on creation
     */
    public static final int START_SIZE = 100;

//TODO: gen x lines, make it select 2 biometypes, from x-1 chunk and y-1 chunk, and grab random one

    protected enum GEN_MODE{
        POSxPOSz, NEGxPOSz, POSxNEGz, NEGxNEGz
    }

    public static World generate(){
        return INSTANCE.generateWorld();
    }

    public World generateWorld(){
        World world = new World();
        WorldGenComponent worldGenComponent1 = new WorldGenComponent(GEN_MODE.POSxPOSz, true, null);
        WorldGenComponent worldGenComponent2 = new WorldGenComponent(GEN_MODE.NEGxPOSz, true, null);
        WorldGenComponent worldGenComponent3 = new WorldGenComponent(GEN_MODE.POSxNEGz, true, null);
        WorldGenComponent worldGenComponent4 = new WorldGenComponent(GEN_MODE.NEGxNEGz, true, null);
        WorldGenComponent[] worldGenComponents = {
                worldGenComponent1, worldGenComponent2, worldGenComponent3, worldGenComponent4
        };
        for(WorldGenComponent component : worldGenComponents){
            component.onChunkGen((pos, chunk) -> world.CHUNKS.put(pos, chunk));
            component.run();
        }
        try {
            wait(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return world;
    }

    private class WorldGenComponent implements Runnable{

        private GEN_MODE mode;
        private IBiome type;
        private boolean first;

        protected WorldGenComponent(GEN_MODE mode, boolean first, @Nullable IBiome type){
            this.mode = mode;
            this.type = type;
            this.first = first;
        }

        private BiConsumer<ChunkPos, Chunk> consumer;

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            ChunkFactory factory = new ChunkFactory();
            factory.initFactory(first ? null : type, first);
            switch (mode){
                case POSxPOSz:
                    for(long x = 0; x < START_SIZE; x++){
                        for(long z = 0; z < START_SIZE; z++){
                            consumer.accept(new ChunkPos(x, z), factory.getChunk());
                            factory.getNeighbourFactory();
                        }
                    }
                    break;
                case NEGxPOSz:
                    for(long x = 0; (x > START_SIZE * -1); x--){
                        for(long z = 0; z < START_SIZE; z++){
                            consumer.accept(new ChunkPos(x, z), factory.getChunk());
                            factory.getNeighbourFactory();
                        }
                    }
                    break;
                case POSxNEGz:
                    for(long x = 0; x < START_SIZE; x++){
                        for(long z = 0; (z > START_SIZE * -1); z--){
                            consumer.accept(new ChunkPos(x, z), factory.getChunk());
                            factory.getNeighbourFactory();
                        }
                    }
                    break;
                case NEGxNEGz:
                    for(long x = 0; (x > START_SIZE * -1); x--){
                        for(long z = 0; (z > START_SIZE * -1); z--){
                            consumer.accept(new ChunkPos(x, z), factory.getChunk());
                            factory.getNeighbourFactory();
                        }
                    }
                    break;
            }
        }

        public void onChunkGen(BiConsumer<ChunkPos, Chunk> consumer){
            this.consumer = consumer;
        }
    }
}
