package net.thegaminghuskymc.sandboxgame.engine.block;

import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;

public abstract class BlockCube extends Block {

    public BlockCube(int blockID) {
        super(blockID);
    }

    @Override
    public void update(Terrain terrain, int x, int y, int z) {
    }

    public void onSet(Terrain terrain, int x, int y, int z) {
    }

    public void onUnset(Terrain terrain, int x, int y, int z) {
    }

    public boolean influenceAO() {
        return (true);
    }

    public boolean isCube() {
        return (true);
    }

}