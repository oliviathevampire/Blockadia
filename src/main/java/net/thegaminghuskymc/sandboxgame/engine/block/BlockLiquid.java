package net.thegaminghuskymc.sandboxgame.engine.block;


import net.thegaminghuskymc.sandboxgame.engine.block.instance.BlockInstance;
import net.thegaminghuskymc.sandboxgame.engine.block.instance.BlockInstanceLiquid;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;

public abstract class BlockLiquid extends Block {

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