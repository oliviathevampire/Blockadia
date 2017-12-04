package net.thegaminghuskymc.sandboxgame.engine.block;

import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

public class BlockAir extends Block {

    public BlockAir() {
        super((short) Blocks.AIR_ID);
    }

    @Override
    public String getUnlocalizedName() {
        return "Air";
    }

    @Override
    public String getRegistryName() {
        return "air";
    }

    @Override
    public boolean isVisible() {
        return (false);
    }

    @Override
    public boolean isOpaque() {
        return (false);
    }

    @Override
    public boolean isCrossable() {
        return (true);
    }

    @Override
    public boolean hasTransparency() {
        return (true);
    }

    @Override
    public void update(Terrain terrain, int x, int y, int z) {
    }

    @Override
    public void onSet(Terrain terrain, int x, int y, int z) {
    }

    @Override
    public void onUnset(Terrain terrain, int x, int y, int z) {
    }

}