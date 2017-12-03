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
	public static int[] T_PLANTS = new int[Blocks.PLANTS.length];
    /*public static int[] T_PLANKS = new int[Blocks.PLANKS.length];
    public static int[] T_LOGS_SIDE = new int[Blocks.LOGS.length];
    public static int[] T_LOGS_INSIDE = new int[Blocks.LOGS.length];
    public static int[] T_LEAVES = new int[Blocks.LEAVES.length];
    public static int[] T_STAIRS = new int[Blocks.STAIRS.length];
    public static int[] T_SLABS = new int[Blocks.SLABS.length];
    public static int[] T_DOORS_TOP = new int[Blocks.DOORS.length];
    public static int[] T_DOORS_BOTTOM = new int[Blocks.DOORS.length];
    public static int[] T_CHESTS = new int[Blocks.CHESTS.length];
    public static int[] T_STONES = new int[Blocks.STONES.length];*/

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

		/*T_CHESTS[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_oak"));
        T_CHESTS[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_dark_oak"));
        T_CHESTS[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_birch"));
        T_CHESTS[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_acacia"));
        T_CHESTS[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_spruce"));
        T_CHESTS[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_jungle"));
        T_CHESTS[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_redwood"));
        T_CHESTS[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/chest_palm"));

        T_PLANKS[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_oak"));
        T_PLANKS[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_dark_oak"));
        T_PLANKS[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_birch"));
        T_PLANKS[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_acacia"));
        T_PLANKS[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_spruce"));
        T_PLANKS[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_jungle"));
        T_PLANKS[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_redwood"));
        T_PLANKS[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_palm"));

        T_LOGS_SIDE[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_oak"));
        T_LOGS_SIDE[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_dark_oak"));
        T_LOGS_SIDE[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_birch"));
        T_LOGS_SIDE[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_acacia"));
        T_LOGS_SIDE[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_spruce"));
        T_LOGS_SIDE[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_jungle"));
        T_LOGS_SIDE[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_redwood"));
        T_LOGS_SIDE[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_palm"));

        T_LOGS_INSIDE[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_top_oak"));
        T_LOGS_INSIDE[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_top_dark_oak"));
        T_LOGS_INSIDE[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_top_birch"));
        T_LOGS_INSIDE[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_top_acacia"));
        T_LOGS_INSIDE[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_top_spruce"));
        T_LOGS_INSIDE[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_top_jungle"));
        T_LOGS_INSIDE[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_top_redwood"));
        T_LOGS_INSIDE[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/log_top_palm"));

        T_LEAVES[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_oak"));
        T_LEAVES[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_dark_oak"));
        T_LEAVES[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_birch"));
        T_LEAVES[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_acacia"));
        T_LEAVES[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_spruce"));
        T_LEAVES[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_jungle"));
        T_LEAVES[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_redwood"));
        T_LEAVES[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/leaves_palm"));

        T_STAIRS[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_oak"));
        T_STAIRS[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_dark_oak"));
        T_STAIRS[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_birch"));
        T_STAIRS[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_acacia"));
        T_STAIRS[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_spruce"));
        T_STAIRS[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_jungle"));
        T_STAIRS[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_redwood"));
        T_STAIRS[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_palm"));

        T_DOORS_TOP[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_oak"));
        T_DOORS_TOP[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_dark_oak"));
        T_DOORS_TOP[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_birch"));
        T_DOORS_TOP[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_acacia"));
        T_DOORS_TOP[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_spruce"));
        T_DOORS_TOP[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_jungle"));
        T_DOORS_TOP[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_redwood"));
        T_DOORS_TOP[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_top_palm"));

        T_DOORS_BOTTOM[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_oak"));
        T_DOORS_BOTTOM[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_dark_oak"));
        T_DOORS_BOTTOM[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_birch"));
        T_DOORS_BOTTOM[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_acacia"));
        T_DOORS_BOTTOM[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_spruce"));
        T_DOORS_BOTTOM[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_jungle"));
        T_DOORS_BOTTOM[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_redwood"));
        T_DOORS_BOTTOM[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/door_buttom_palm"));

        T_SLABS[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_oak"));
        T_SLABS[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_dark_oak"));
        T_SLABS[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_birch"));
        T_SLABS[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_acacia"));
        T_SLABS[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_spruce"));
        T_SLABS[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_jungle"));
        T_SLABS[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_redwood"));
        T_SLABS[7] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/planks_palm"));

        T_STONES[0] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone"));
        T_STONES[1] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_diorite"));
        T_STONES[2] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_diorite_smooth"));
        T_STONES[3] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_andesite"));
        T_STONES[4] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_andesite_smooth"));
        T_STONES[5] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_granite"));
        T_STONES[6] = blockTextureManager.registerBlockTexture(R.getResPath("textures/blocks/stone_granite_smooth"));*/

		blockTextureManager.setBlockRenderer(Blocks.DIRT, new BlockRendererCube(ClientBlockRenderers.T_DIRT));

		blockTextureManager.setBlockRenderer(Blocks.GRASS,
				new BlockRendererCube(Face.LEFT, ClientBlockRenderers.T_GRASS_SIDE, Face.RIGHT,
						ClientBlockRenderers.T_GRASS_SIDE, Face.FRONT, ClientBlockRenderers.T_GRASS_SIDE, Face.BACK,
						ClientBlockRenderers.T_GRASS_SIDE, Face.TOP, ClientBlockRenderers.T_GRASS_TOP, Face.BOT,
						ClientBlockRenderers.T_DIRT));

		blockTextureManager.setBlockRenderer(Blocks.LIGHT, new BlockRendererCube(ClientBlockRenderers.T_LIGHT));

		blockTextureManager.setBlockRenderer(Blocks.WATER,
				new BlockRendererLiquid(ClientBlockRenderers.T_LIQUID));

		for (int i = 0; i < Blocks.PLANTS.length; i++) {
			blockTextureManager.setBlockRenderer(Blocks.PLANTS[i],
					new BlockRendererPlant(ClientBlockRenderers.T_PLANTS[i]));
		}
		/*for (int i = 0; i < Blocks.PLANKS.length; i++) {
            blockTextureManager.setBlockRenderer(Blocks.PLANKS[i],
                    new BlockRendererCube(ClientBlockRenderers.T_PLANKS[i]));
		}
		for (int i = 0; i < Blocks.LEAVES.length; i++) {
            blockTextureManager.setBlockRenderer(Blocks.LEAVES[i],
                    new BlockRendererLeaves(ClientBlockRenderers.T_LEAVES[i]));
		}
		for (int i = 0; i < Blocks.SLABS.length; i++) {
            blockTextureManager.setBlockRenderer(Blocks.SLABS[i],
                    new BlockRendererCube(ClientBlockRenderers.T_SLABS[i]));
		}
		for (int i = 0; i < Blocks.STAIRS.length; i++) {
            blockTextureManager.setBlockRenderer(Blocks.STAIRS[i],
                    new BlockRendererCube(ClientBlockRenderers.T_STAIRS[i]));
		}
		for (int i = 0; i < Blocks.STONES.length; i++) {
            blockTextureManager.setBlockRenderer(Blocks.STONES[i],
                    new BlockRendererCube(ClientBlockRenderers.T_STONES[i]));
		}
		for (int i = 0; i < Blocks.LOGS.length; i++) {
			blockTextureManager.setBlockRenderer(Blocks.LOGS[i],
				new BlockRendererCube(Face.LEFT, ClientBlockRenderers.T_LOGS_SIDE[i], Face.RIGHT,
						ClientBlockRenderers.T_LOGS_SIDE[i], Face.FRONT, ClientBlockRenderers.T_LOGS_SIDE[i], Face.BACK,
						ClientBlockRenderers.T_LOGS_SIDE[i], Face.TOP, ClientBlockRenderers.T_LOGS_INSIDE[i], Face.BOT,
						ClientBlockRenderers.T_LOGS_INSIDE[i]));
		}
		for (int i = 0; i < Blocks.CHESTS.length; i++) {
			blockTextureManager.setBlockRenderer(Blocks.CHESTS[i],
                    new BlockRendererCube(ClientBlockRenderers.T_CHESTS[i]));
		}
		for (int i = 0; i < Blocks.DOORS.length; i++) {
			blockTextureManager.setBlockRenderer(Blocks.DOORS[i],
					new BlockRendererCube(ClientBlockRenderers.T_PLANKS[i]));
		}*/
	}

	@Override
	public void unload(Mod mod, ResourceManager manager) {
	}

	public static Block getBlockByID(short blockID) {
		return (BlockManager.instance().getBlockByID(blockID));
	}
}