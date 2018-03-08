package net.thegaminghuskymc.sandboxgame.engine.managers;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.util.ResourceLocation;

public class BlockManager extends GenericManager<Block> {

    private static BlockManager BLOCK_MANAGER_INSTANCE;

    BlockManager(ResourceManager manager) {
        super(manager);
        BLOCK_MANAGER_INSTANCE = this;
    }

    public static BlockManager instance() {
        return (BLOCK_MANAGER_INSTANCE);
    }

    public static void registerBlock(int id, ResourceLocation textualID, Block block_)
    {

    }

    public Block getBlockByID(int id) {
        return (super.getObjectByID(id));
    }

    @Override
    public void onInitialized() {

    }

    @Override
    public void onLoaded() {

    }

    @Override
    protected void onDeinitialized() {

    }

    @Override
    protected void onUnloaded() {

    }

    @Override
    protected void onObjectRegistered(Block object) {

    }

}
