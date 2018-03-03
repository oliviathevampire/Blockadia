package net.thegaminghuskymc.sandboxgame.world.gen;

import com.google.common.collect.Lists;
import net.thegaminghuskymc.sandboxgame.block.Block;
import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.init.Blocks;
import net.thegaminghuskymc.sandboxgame.world.World;
import net.thegaminghuskymc.sandboxgame.world.biome.Biome;
import net.thegaminghuskymc.sandboxgame.world.chunk.Chunk;
import net.thegaminghuskymc.sandboxgame.world.chunk.ChunkPrimer;
import net.thegaminghuskymc.sandboxgame.util.math.BlockPos;
import net.thegaminghuskymc.sandboxgame.util.math.MathHelper;

import javax.annotation.Nullable;
import java.util.List;

public class ChunkGeneratorDebug implements IChunkGenerator {
    protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
    protected static final IBlockState BARRIER = Blocks.BARRIER.getDefaultState();
    /**
     * A list of all valid block states.
     */
    private static final List<IBlockState> ALL_VALID_STATES = Lists.<IBlockState>newArrayList();
    private static final int GRID_WIDTH;
    private static final int GRID_HEIGHT;

    static {
        for (Block block : Block.REGISTRY) {
            ALL_VALID_STATES.addAll(block.getBlockState().getValidStates());
        }

        GRID_WIDTH = MathHelper.ceil(MathHelper.sqrt((float) ALL_VALID_STATES.size()));
        GRID_HEIGHT = MathHelper.ceil((float) ALL_VALID_STATES.size() / (float) GRID_WIDTH);
    }

    private final World world;

    public ChunkGeneratorDebug(World worldIn) {
        this.world = worldIn;
    }

    public static IBlockState getBlockStateFor(int p_177461_0_, int p_177461_1_) {
        IBlockState iblockstate = AIR;

        if (p_177461_0_ > 0 && p_177461_1_ > 0 && p_177461_0_ % 2 != 0 && p_177461_1_ % 2 != 0) {
            p_177461_0_ = p_177461_0_ / 2;
            p_177461_1_ = p_177461_1_ / 2;

            if (p_177461_0_ <= GRID_WIDTH && p_177461_1_ <= GRID_HEIGHT) {
                int i = MathHelper.abs(p_177461_0_ * GRID_WIDTH + p_177461_1_);

                if (i < ALL_VALID_STATES.size()) {
                    iblockstate = ALL_VALID_STATES.get(i);
                }
            }
        }

        return iblockstate;
    }

    /**
     * Generates the chunk at the specified position, from scratch
     */
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer chunkprimer = new ChunkPrimer();

        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                int k = x * 16 + i;
                int l = z * 16 + j;
                chunkprimer.setBlockState(i, 60, j, BARRIER);
                IBlockState iblockstate = getBlockStateFor(k, l);

                if (iblockstate != null) {
                    chunkprimer.setBlockState(i, 70, j, iblockstate);
                }
            }
        }

        Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
        chunk.generateSkylightMap();
        Biome[] abiome = this.world.getBiomeProvider().getBiomes((Biome[]) null, x * 16, z * 16, 16, 16);
        byte[] abyte = chunk.getBiomeArray();

        for (int i1 = 0; i1 < abyte.length; ++i1) {
            abyte[i1] = (byte) Biome.getIdForBiome(abiome[i1]);
        }

        chunk.generateSkylightMap();
        return chunk;
    }

    /**
     * Generate initial structures in this chunk, e.g. mineshafts, temples, lakes, and dungeons
     *
     * @param x Chunk x coordinate
     * @param z Chunk z coordinate
     */
    public void populate(int x, int z) {
    }

    /**
     * Called to generate additional structures after initial worldgen, used by ocean monuments
     */
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        Biome biome = this.world.getBiome(pos);
        return biome.getSpawnableList(creatureType);
    }

    @Nullable
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }

    /**
     * Recreates data about structures intersecting given chunk (used for example by getPossibleCreatures), without
     * placing any blocks. When called for the first time before any chunk is generated - also initializes the internal
     * state needed by getPossibleCreatures.
     */
    public void recreateStructures(Chunk chunkIn, int x, int z) {
    }
}