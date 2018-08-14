package team.hdt.blockadia.game_engine.common.world;

import team.hdt.blockadia.game_engine.common.world.block.BlockType;
import team.hdt.blockadia.game_engine.common.world.gen.interfaces.IBiome;
import team.hdt.blockadia.game_engine.common.world.storage.RawBlockStorage;
import team.hdt.blockadia.game_engine.common.world.storage.RawIdBlockStorage;

public class Chunk {

    public static final int CHUNK_SIZE = 32;
    public static final int CHUNK_MASK = CHUNK_SIZE - 1;
    public static final int CHUNK_SHIFT = 5;
    public final RawBlockStorage storage;
    private final int chunkX;
    private final int chunkY;
    private final int chunkZ;
    private final ChunkType chunkType;
    private final IBiome BIOME;
    public Chunk(int chunkX, int chunkY, int chunkZ, ChunkType chunkType, IBiome biome) {
        this.chunkX = chunkX;
        this.chunkY = chunkY;
        this.chunkZ = chunkZ;
        this.storage = new RawIdBlockStorage(CHUNK_SIZE, 100, CHUNK_SIZE);
        this.chunkType = chunkType;
        this.BIOME = biome;
    }

    public ChunkType getType() {
        return chunkType;
    }

    public IBiome getBiome() {
        return BIOME;
    }

    public int getChunkX() {
        return this.chunkX;
    }

    public int getChunkY() {
        return this.chunkY;
    }

    public int getChunkZ() {
        return this.chunkZ;
    }

    public void setBlock(int x, int y, int z, BlockType type) {
        this.storage.setBlockUnchecked(x, y, z, type);
    }

    public BlockType getBlock(int x, int y, int z) {
        return this.storage.getBlockUnchecked(x, y, z);
    }

    public enum ChunkType {
        HORIZONTAL, VERTICAL
    }

}