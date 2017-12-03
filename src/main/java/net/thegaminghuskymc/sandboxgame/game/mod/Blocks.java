package net.thegaminghuskymc.sandboxgame.game.mod;

import net.thegaminghuskymc.sandboxgame.engine.block.*;
import net.thegaminghuskymc.sandboxgame.engine.managers.BlockManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;

public class Blocks implements IModResource {

    public static final int AIR_ID = 0;

    /** load default blocks */
    public static Block AIR;
    public static Block DIRT;
    public static Block GRASS;
    public static Block LIGHT;
    public static Block WATER;
    public static Block LAVA;
    public static Block COBBLESTONE;
    public static Block GLASS;
    public static Block SAND;
    public static Block FUENACE;
    public static Block HAY_BALE;
    public static Block BEACON;
    public static Block JUKEBOX;
    public static Block CAULDRON;

    public static Block PLANKS_OAK;
    public static Block PLANKS_DARK_OAK;
    public static Block PLANKS_BIRCH;
    public static Block PLANKS_ACACIA;
    public static Block PLANKS_SPRUCE;
    public static Block PLANKS_JUNGLE;
    public static Block PLANKS_REDWOOD;
    public static Block PLANKS_PALM;

    /*public static Block PLANKS_OAK;
    public static Block PLANKS_DARK_OAK;
    public static Block PLANKS_BIRCH;
    public static Block PLANKS_ACACIA;
    public static Block PLANKS_SPRUCE;
    public static Block PLANKS_JUNGLE;
    public static Block PLANKS_REDWOOD;
    public static Block PLANKS_PALM;

    public static Block PLANKS_OAK;
    public static Block PLANKS_DARK_OAK;
    public static Block PLANKS_BIRCH;
    public static Block PLANKS_ACACIA;
    public static Block PLANKS_SPRUCE;
    public static Block PLANKS_JUNGLE;
    public static Block PLANKS_REDWOOD;
    public static Block PLANKS_PALM;

    public static Block PLANKS_OAK;
    public static Block PLANKS_DARK_OAK;
    public static Block PLANKS_BIRCH;
    public static Block PLANKS_ACACIA;
    public static Block PLANKS_SPRUCE;
    public static Block PLANKS_JUNGLE;
    public static Block PLANKS_REDWOOD;
    public static Block PLANKS_PALM;

    public static Block PLANKS_OAK;
    public static Block PLANKS_DARK_OAK;
    public static Block PLANKS_BIRCH;
    public static Block PLANKS_ACACIA;
    public static Block PLANKS_SPRUCE;
    public static Block PLANKS_JUNGLE;
    public static Block PLANKS_REDWOOD;
    public static Block PLANKS_PALM;

    public static Block PLANKS_OAK;
    public static Block PLANKS_DARK_OAK;
    public static Block PLANKS_BIRCH;
    public static Block PLANKS_ACACIA;
    public static Block PLANKS_SPRUCE;
    public static Block PLANKS_JUNGLE;
    public static Block PLANKS_REDWOOD;
    public static Block PLANKS_PALM;

    public static Block PLANKS_OAK;
    public static Block PLANKS_DARK_OAK;
    public static Block PLANKS_BIRCH;
    public static Block PLANKS_ACACIA;
    public static Block PLANKS_SPRUCE;
    public static Block PLANKS_JUNGLE;
    public static Block PLANKS_REDWOOD;
    public static Block PLANKS_PALM;

    public static Block PLANKS_OAK;
    public static Block PLANKS_DARK_OAK;
    public static Block PLANKS_BIRCH;
    public static Block PLANKS_ACACIA;
    public static Block PLANKS_SPRUCE;
    public static Block PLANKS_JUNGLE;
    public static Block PLANKS_REDWOOD;
    public static Block PLANKS_PALM;*/

    public static Block[] PLANTS = new Block[5];

    @Override
    public void load(Mod mod, ResourceManager manager) {
        BlockManager blockmanager = manager.getBlockManager();
        this.loadBlocks(blockmanager);
    }

    private void loadBlocks(BlockManager blockmanager) {
        Blocks.AIR = blockmanager.registerBlock(new BlockAir());
        Blocks.DIRT = blockmanager.registerBlock(new BlockDirt(blockmanager.getNextID()));
        Blocks.GRASS = blockmanager.registerBlock(new BlockGrass(blockmanager.getNextID()));
        Blocks.LIGHT = blockmanager.registerBlock(new BlockLight(blockmanager.getNextID()));
        Blocks.WATER = blockmanager.registerBlock(new BlockWater(blockmanager.getNextID()));
        Blocks.LAVA = blockmanager.registerBlock(new BlockLava(blockmanager.getNextID()));

        for (int i = 0; i < Blocks.PLANTS.length; i++) {
            Blocks.PLANTS[i] = blockmanager.registerBlock(new BlockPlant(blockmanager.getNextID()));
        }
        /*for (int i = 0; i < Blocks.PLANKS.length; i++) {
            Blocks.PLANKS[i] = blockmanager.registerBlock(new BlockPlank(blockmanager.getNextID()));
        }
        for (int i = 0; i < Blocks.LOGS.length; i++) {
            Blocks.LOGS[i] = blockmanager.registerBlock(new BlockLog(blockmanager.getNextID()));
        }
        for (int i = 0; i < Blocks.STAIRS.length; i++) {
            Blocks.STAIRS[i] = blockmanager.registerBlock(new BlockStair(blockmanager.getNextID()));
        }
        for (int i = 0; i < Blocks.SLABS.length; i++) {
            Blocks.SLABS[i] = blockmanager.registerBlock(new BlockSlab(blockmanager.getNextID()));
        }
        for (int i = 0; i < Blocks.DOORS.length; i++) {
            Blocks.DOORS[i] = blockmanager.registerBlock(new BlockDoor(blockmanager.getNextID()));
        }
        for (int i = 0; i < Blocks.CHESTS.length; i++) {
            Blocks.CHESTS[i] = blockmanager.registerBlock(new BlockChest(blockmanager.getNextID()));
        }
        for (int i = 0; i < Blocks.LEAVES.length; i++) {
            Blocks.LEAVES[i] = blockmanager.registerBlock(new BlockLeaves(blockmanager.getNextID()));
        }
        for (int i = 0; i < Blocks.STONES.length; i++) {
            Blocks.STONES[i] = blockmanager.registerBlock(new BlockStone(blockmanager.getNextID()));
        }*/
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {
    }

    public static Block getBlockByID(short blockID) {
        return (BlockManager.instance().getBlockByID(blockID));
    }

}
