package net.thegaminghuskymc.sandboxgame.engine.block;

import net.thegaminghuskymc.sandboxgame.engine.block.instance.BlockInstance;
import net.thegaminghuskymc.sandboxgame.engine.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;

public abstract class Block {

    private final short id;

    public Block(int blockID) {
        this.id = (short) blockID;
    }

    public String toString() {
        return ("Block: " + this.getRegistryName());
    }

    public abstract String getUnlocalizedName();

    public abstract String getRegistryName();

    public abstract boolean isVisible();

    public abstract boolean isOpaque();

    public final boolean isTransparent() {
        return (!this.isOpaque() || this.hasTransparency());
    }

    public abstract boolean hasTransparency();

    public abstract void update(Terrain terrain, int x, int y, int z);

    public final short getID() {
        return (this.id);
    }

    public BlockInstance createBlockInstance(Terrain terrain, int index) {
        return (null);
    }

    public abstract void onSet(Terrain terrain, int x, int y, int z);

    public abstract void onUnset(Terrain terrain, int x, int y, int z);

    public boolean isCrossable() {
        return (false);
    }

    public boolean bypassRaycast() {
        return (false);
    }

}