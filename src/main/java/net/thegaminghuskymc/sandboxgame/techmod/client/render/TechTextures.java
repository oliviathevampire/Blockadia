package net.thegaminghuskymc.sandboxgame.techmod.client.render;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.managers.BlockManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.game.client.defaultmod.ClientBlockRenderers;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.blocks.BlockRendererCube;
import net.thegaminghuskymc.sandboxgame.game.client.resources.BlockRendererManager;
import net.thegaminghuskymc.sandboxgame.game.client.resources.ResourceManagerClient;
import net.thegaminghuskymc.sandboxgame.techmod.common.blocks.TechBlocks;

public class TechTextures implements IModResource {

    public static int[] T_WIRES = new int[7];

    public static Block getBlockByID(short blockID) {
        return (BlockManager.instance().getBlockByID(blockID));
    }

    @Override
    public void load(Mod mod, ResourceManager manager) {
        BlockRendererManager blockTextureManager = ((ResourceManagerClient) manager).getBlockTextureManager();
        this.loadTextures(blockTextureManager);
    }

    private void loadTextures(BlockRendererManager blockTextureManager) {
        for (int i = 0; i < 7; i++) {
            blockTextureManager.setBlockRenderer(TechBlocks.WIRES.get(i),
                    new BlockRendererCube(ClientBlockRenderers.T_PLANTS[i]));
        }
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {

    }

}
