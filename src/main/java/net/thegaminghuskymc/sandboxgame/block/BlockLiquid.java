package net.thegaminghuskymc.sandboxgame.block;


import net.thegaminghuskymc.sandboxgame.block.instance.BlockInstance;
import net.thegaminghuskymc.sandboxgame.block.instance.BlockInstanceLiquid;

public abstract class BlockLiquid extends BlockAlt {

    public BlockLiquid(int blockID) {
        super(blockID);
    }

    @Override
    public abstract String getRegistryName();

    @Override
    public abstract String getUnlocalizedName();

    @Override
    public boolean isVisible() {
        return (true);
    }

    @Override
    public boolean isOpaque() {
        return (false);
    }

    @Override
    public BlockInstance createBlockInstance(Terrain terrain, int index) {
        return (new BlockInstanceLiquid(terrain, this, index));
    }

    @Override
    public boolean isCrossable() {
        return (true);
    }

    @Override
    public void update(Terrain terrain, int x, int y, int z) {
    }

    public void onSet(Terrain terrain, int x, int y, int z) {
    }

    public void onUnset(Terrain terrain, int x, int y, int z) {
    }

    @Override
    public boolean bypassRaycast() {
        return (true);
    }

}