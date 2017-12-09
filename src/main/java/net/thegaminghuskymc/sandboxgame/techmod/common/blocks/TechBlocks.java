package net.thegaminghuskymc.sandboxgame.techmod.common.blocks;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.managers.BlockManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;

import java.util.LinkedList;

public class TechBlocks implements IModResource {

    public static LinkedList<Block> WIRES = new LinkedList<>();

    @Override
    public void load(Mod mod, ResourceManager manager) {
        BlockManager blockmanager = manager.getBlockManager();
        this.loadBlocks(blockmanager);
    }

    private void loadBlocks(BlockManager blockmanager) {
        for(int i = 0; i < 6; i++) {
            TechBlocks.WIRES.add(new BlockWire(blockmanager.getNextID()));
        }
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {

    }

}
