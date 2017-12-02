package net.thegaminghuskymc.sandboxgame.game.mod;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.block.BlockDirt;
import net.thegaminghuskymc.sandboxgame.engine.block.BlockGrass;
import net.thegaminghuskymc.sandboxgame.engine.block.BlockStone;
import net.thegaminghuskymc.sandboxgame.engine.managers.BlockManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;

public class Blocks implements IModResource {

    /** load default blocks */
    public static Block DIRT;
    public static Block GRASS;
    public static Block STONE;

    @Override
    public void load(Mod mod, ResourceManager manager) {
        BlockManager blockmanager = manager.getBlockManager();
        this.loadBlocks(blockmanager);
    }

    private void loadBlocks(BlockManager blockmanager) {
        Blocks.DIRT = blockmanager.registerBlock(new BlockDirt(blockmanager.getNextID()));
        Blocks.GRASS = blockmanager.registerBlock(new BlockGrass(blockmanager.getNextID()));
        Blocks.STONE = blockmanager.registerBlock(new BlockStone(blockmanager.getNextID()));
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {

    }

    public static Block getBlockByID(short blockID) {
        return (BlockManager.instance().getBlockByID(blockID));
    }

}
