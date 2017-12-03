package net.thegaminghuskymc.sandboxgame.engine.block;

import net.thegaminghuskymc.sandboxgame.engine.block.instance.BlockInstance;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;

public class BlockCobblestone extends Block {

    public BlockCobblestone(int ID) {
        super(ID);
    }

    @Override
    public String getRegistryName() {
        return "cobblestone";
    }

    @Override
    public String getUnlocalizedName() {
        return "Cobblestone";
    }

    @Override
    public boolean isVisible() {
        return true;
    }

    @Override
    public void onSet(Terrain terrain, int x, int y, int z) {

    }

    @Override
    public void onUnset(Terrain terrain, int x, int y, int z) {

    }

    @Override
    public boolean isOpaque() {
        return false;
    }

    @Override
    public void update(Terrain terrain, int x, int y, int z) {

    }

    @Override
    public boolean hasTransparency() {
        return false;
    }

    @Override
    public BlockInstance createBlockInstance(Terrain terrain, int index) {
        return new BlockInstance(terrain, this, index) {

            @Override
            public void update() {

            }

            @Override
            public void onSet() {

            }

            @Override
            public void onUnset() {

            }

        };
    }

}
