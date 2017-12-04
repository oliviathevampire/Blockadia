package net.thegaminghuskymc.sandboxgame.game.client.defaultmod;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.faces.Face;
import net.thegaminghuskymc.sandboxgame.engine.managers.BlockManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.resourcepacks.R;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.blocks.BlockRendererCube;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.blocks.BlockRendererLeaves;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.blocks.BlockRendererLiquid;
import net.thegaminghuskymc.sandboxgame.game.client.renderer.blocks.BlockRendererPlant;
import net.thegaminghuskymc.sandboxgame.game.client.resources.BlockRendererManager;
import net.thegaminghuskymc.sandboxgame.game.client.resources.ResourceManagerClient;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

public class ClientBlockRenderers implements IModResource {
    public static final int AIR_ID = 0;

    // texture id
    public static int T_EMPTY;
    public static int T_DIRT;
    public static int T_GRASS_TOP;
    public static int T_GRASS_SIDE;
    public static int T_LIQUID;
    public static int T_LIGHT;
    public static int[] T_PLANTS = new int[5];
    public static int[] T_PLANKS = new int[8];
    public static int[] T_LOGS_SIDE = new int[8];
    public static int[] T_LOGS_INSIDE = new int[8];
    public static int[] T_LEAVES = new int[8];
    public static int[] T_STAIRS = new int[8];
    public static int[] T_SLABS = new int[8];
    public static int[] T_DOORS_TOP = new int[8];
    public static int[] T_DOORS_BOTTOM = new int[8];
    public static int[] T_CHESTS = new int[8];
    public static int[] T_STONES = new int[7];

    public static Block getBlockByID(short blockID) {
        return (BlockManager.instance().getBlockByID(blockID));
    }

    @Override
    public void load(Mod mod, ResourceManager manager) {
        BlockRendererManager blockTextureManager = ((ResourceManagerClient) manager).getBlockTextureManager();
        this.loadTextures(blockTextureManager);
    }

    private void loadTextures(BlockRendererManager blockTextureManager) {
        T_EMPTY = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/empty.png"));
        T_DIRT = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/dirt.png"));
        T_GRASS_TOP = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/grass_top.png"));
        T_GRASS_SIDE = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/grass_side.png"));
        T_LIQUID = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/liquid.png"));
        T_LIGHT = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/light.png"));

        T_PLANTS[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/flower1.png"));
        T_PLANTS[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/flower2.png"));
        T_PLANTS[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/flower3.png"));
        T_PLANTS[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/herb.png"));
        T_PLANTS[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/herb2.png"));

        T_CHESTS[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_oak.png"));
        T_CHESTS[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_dark_oak.png"));
        T_CHESTS[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_birch.png"));
        T_CHESTS[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_acacia.png"));
        T_CHESTS[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_spruce.png"));
        T_CHESTS[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_jungle.png"));
        T_CHESTS[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_redwood.png"));
        T_CHESTS[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_palm.png"));

        T_PLANKS[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_oak.png"));
        T_PLANKS[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_dark_oak.png"));
        T_PLANKS[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_birch.png"));
        T_PLANKS[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_acacia.png"));
        T_PLANKS[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_spruce.png"));
        T_PLANKS[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_jungle.png"));
        T_PLANKS[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_redwood.png"));
        T_PLANKS[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_palm.png"));

        T_LOGS_SIDE[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_oak.png"));
        T_LOGS_SIDE[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_dark_oak.png"));
        T_LOGS_SIDE[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_birch.png"));
        T_LOGS_SIDE[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_acacia.png"));
        T_LOGS_SIDE[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_spruce.png"));
        T_LOGS_SIDE[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_jungle.png"));
        T_LOGS_SIDE[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_redwood.png"));
        T_LOGS_SIDE[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_palm.png"));

        T_LOGS_INSIDE[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_oak_top.png"));
        T_LOGS_INSIDE[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_dark_oak_top.png"));
        T_LOGS_INSIDE[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_birch_top.png"));
        T_LOGS_INSIDE[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_acacia_top.png"));
        T_LOGS_INSIDE[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_spruce_top.png"));
        T_LOGS_INSIDE[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_jungle_top.png"));
        T_LOGS_INSIDE[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_redwood_top.png"));
        T_LOGS_INSIDE[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_palm_top.png"));

        T_LEAVES[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_oak.png"));
        T_LEAVES[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_dark_oak.png"));
        T_LEAVES[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_birch.png"));
        T_LEAVES[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_acacia.png"));
        T_LEAVES[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_spruce.png"));
        T_LEAVES[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_jungle.png"));
        T_LEAVES[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_redwood.png"));
        T_LEAVES[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_palm.png"));

        T_STAIRS[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_oak.png"));
        T_STAIRS[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_dark_oak.png"));
        T_STAIRS[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_birch.png"));
        T_STAIRS[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_acacia.png"));
        T_STAIRS[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_spruce.png"));
        T_STAIRS[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_jungle.png"));
        T_STAIRS[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_redwood.png"));
        T_STAIRS[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_palm.png"));

        T_DOORS_TOP[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_oak.png"));
        T_DOORS_TOP[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_dark_oak.png"));
        T_DOORS_TOP[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_birch.png"));
        T_DOORS_TOP[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_acacia.png"));
        T_DOORS_TOP[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_spruce.png"));
        T_DOORS_TOP[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_jungle.png"));
        T_DOORS_TOP[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_redwood.png"));
        T_DOORS_TOP[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_palm.png"));

        T_DOORS_BOTTOM[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_oak.png"));
        T_DOORS_BOTTOM[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_dark_oak.png"));
        T_DOORS_BOTTOM[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_birch.png"));
        T_DOORS_BOTTOM[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_acacia.png"));
        T_DOORS_BOTTOM[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_spruce.png"));
        T_DOORS_BOTTOM[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_jungle.png"));
        T_DOORS_BOTTOM[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_redwood.png"));
        T_DOORS_BOTTOM[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_palm.png"));

        T_SLABS[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_oak.png"));
        T_SLABS[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_dark_oak.png"));
        T_SLABS[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_birch.png"));
        T_SLABS[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_acacia.png"));
        T_SLABS[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_spruce.png"));
        T_SLABS[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_jungle.png"));
        T_SLABS[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_redwood.png"));
        T_SLABS[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_palm.png"));

        T_STONES[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone.png"));
        T_STONES[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_diorite.png"));
        T_STONES[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_diorite_smooth.png"));
        T_STONES[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_andesite.png"));
        T_STONES[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_andesite_smooth.png"));
        T_STONES[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_granite.png"));
        T_STONES[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_granite_smooth.png"));

        blockTextureManager.setBlockRenderer(Blocks.DIRT, new BlockRendererCube(ClientBlockRenderers.T_DIRT));

        blockTextureManager.setBlockRenderer(Blocks.GRASS,
                new BlockRendererCube(Face.LEFT, ClientBlockRenderers.T_GRASS_SIDE, Face.RIGHT,
                        ClientBlockRenderers.T_GRASS_SIDE, Face.FRONT, ClientBlockRenderers.T_GRASS_SIDE, Face.BACK,
                        ClientBlockRenderers.T_GRASS_SIDE, Face.TOP, ClientBlockRenderers.T_GRASS_TOP, Face.BOT,
                        ClientBlockRenderers.T_DIRT));

        blockTextureManager.setBlockRenderer(Blocks.LIGHT, new BlockRendererCube(ClientBlockRenderers.T_LIGHT));

        blockTextureManager.setBlockRenderer(Blocks.WATER,
                new BlockRendererLiquid(ClientBlockRenderers.T_LIQUID));

        for (int i = 0; i < 5; i++) {
            blockTextureManager.setBlockRenderer(Blocks.PLANTS.get(i),
                    new BlockRendererPlant(ClientBlockRenderers.T_PLANTS[i]));
        }
        for (int i = 0; i < 8; i++) {
            blockTextureManager.setBlockRenderer(Blocks.PLANKS.get(i),
                    new BlockRendererCube(ClientBlockRenderers.T_PLANKS[i]));
        }
        for (int i = 0; i < 8; i++) {
            blockTextureManager.setBlockRenderer(Blocks.LEAVES.get(i),
                    new BlockRendererLeaves(ClientBlockRenderers.T_LEAVES[i]));
        }
        for (int i = 0; i < 8; i++) {
            blockTextureManager.setBlockRenderer(Blocks.SLABS.get(i),
                    new BlockRendererCube(ClientBlockRenderers.T_SLABS[i]));
        }
        for (int i = 0; i < 8; i++) {
            blockTextureManager.setBlockRenderer(Blocks.STAIRS.get(i),
                    new BlockRendererCube(ClientBlockRenderers.T_STAIRS[i]));
        }
        for (int i = 0; i < 7; i++) {
            blockTextureManager.setBlockRenderer(Blocks.STONES.get(i),
                    new BlockRendererCube(ClientBlockRenderers.T_STONES[i]));
        }
        for (int i = 0; i < 8; i++) {
            blockTextureManager.setBlockRenderer(Blocks.LOGS.get(i),
                    new BlockRendererCube(Face.LEFT, ClientBlockRenderers.T_LOGS_SIDE[i], Face.RIGHT,
                            ClientBlockRenderers.T_LOGS_SIDE[i], Face.FRONT, ClientBlockRenderers.T_LOGS_SIDE[i], Face.BACK,
                            ClientBlockRenderers.T_LOGS_SIDE[i], Face.TOP, ClientBlockRenderers.T_LOGS_INSIDE[i], Face.BOT,
                            ClientBlockRenderers.T_LOGS_INSIDE[i]));
        }
        for (int i = 0; i < 8; i++) {
            blockTextureManager.setBlockRenderer(Blocks.CHESTS.get(i),
                    new BlockRendererCube(ClientBlockRenderers.T_CHESTS[i]));
        }
        for (int i = 0; i < 8; i++) {
            blockTextureManager.setBlockRenderer(Blocks.DOORS.get(i),
                    new BlockRendererCube(ClientBlockRenderers.T_PLANKS[i]));
        }
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {
    }
}