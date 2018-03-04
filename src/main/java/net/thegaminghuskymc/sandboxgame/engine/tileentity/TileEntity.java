package net.thegaminghuskymc.sandboxgame.engine.tileentity;

import javax.annotation.Nullable;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.engine.util.Mirror;
import net.thegaminghuskymc.sandboxgame.engine.util.Rotation;
import net.thegaminghuskymc.sandboxgame.engine.util.text.ITextComponent;
import net.thegaminghuskymc.sandboxgame.engine.world.BlockPos;
import net.thegaminghuskymc.sandboxgame.engine.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TileEntity
{
    private static final Logger LOGGER = LogManager.getLogger();
    /** the instance of the world the tile entity is in. */
    protected World world;
    protected BlockPos pos = BlockPos.ORIGIN;
    protected boolean tileEntityInvalid;
    private int blockMetadata = -1;
    /** the Block type that this TileEntity is contained within */
    protected Block blockType;

    /**
     * Returns the worldObj for this tileEntity.
     */
    public World getWorld()
    {
        return this.world;
    }

    /**
     * Sets the worldObj for this tileEntity.
     */
    public void setWorld(World worldIn)
    {
        this.world = worldIn;
    }

    /**
     * Returns true if the worldObj isn't null.
     */
    public boolean hasWorld()
    {
        return this.world != null;
    }

    protected void setWorldCreate(World worldIn)
    {
    }

    /**
     * Returns the square of the distance between this entity and the passed in coordinates.
     */
    public double getDistanceSq(double x, double y, double z)
    {
        double d0 = (double)this.pos.getX() + 0.5D - x;
        double d1 = (double)this.pos.getY() + 0.5D - y;
        double d2 = (double)this.pos.getZ() + 0.5D - z;
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double getMaxRenderDistanceSquared()
    {
        return 4096.0D;
    }

    public BlockPos getPos()
    {
        return this.pos;
    }

    public boolean isInvalid()
    {
        return this.tileEntityInvalid;
    }

    /**
     * invalidates a tile entity
     */
    public void invalidate()
    {
        this.tileEntityInvalid = true;
    }

    /**
     * validates a tile entity
     */
    public void validate()
    {
        this.tileEntityInvalid = false;
    }

    public boolean receiveClientEvent(int id, int type)
    {
        return false;
    }

    public void updateContainingBlockInfo()
    {
        this.blockType = null;
        this.blockMetadata = -1;
    }

    public void setPos(BlockPos posIn)
    {
        this.pos = posIn.toImmutable();
    }

    public boolean onlyOpsCanSetNbt()
    {
        return false;
    }

    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    @Nullable
    public ITextComponent getDisplayName()
    {
        return null;
    }

    public void rotate(Rotation rotationIn)
    {
    }

    public void mirror(Mirror mirrorIn)
    {
    }

    /**
     * Called when the chunk this TileEntity is on is Unloaded.
     */
    public void onChunkUnload()
    {
    }

    private boolean isVanilla = getClass().getName().startsWith("net.minecraft.");
    /**
     * Called from Chunk.setBlockIDWithMetadata and Chunk.fillChunk, determines if this tile entity should be re-created when the ID, or Metadata changes.
     * Use with caution as this will leave straggler TileEntities, or create conflicts with other TileEntities if not used properly.
     *
     * @param world Current world
     * @param pos Tile's world position
     * @param oldState The old ID of the block
     * @return true forcing the invalidation of the existing TE, false not to invalidate the existing TE
     */
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate)
    {
        return isVanilla ? (oldState.getBlock() != newSate.getBlock()) : oldState != newSate;
    }

    public boolean shouldRenderInPass(int pass)
    {
        return pass == 0;
    }

    /**
     * Override instead of adding {@code if (firstTick)} stuff in update.
     */
    public void onLoad()
    {
        // NOOP
    }

    /**
     * If the TileEntitySpecialRenderer associated with this TileEntity can be batched in with another renderers, and won't access the GL state.
     * If TileEntity returns true, then TESR should have the same functionality as (and probably extend) the FastTESR class.
     */
    public boolean hasFastRenderer()
    {
        return false;
    }

}