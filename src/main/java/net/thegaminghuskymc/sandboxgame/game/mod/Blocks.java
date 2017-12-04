package net.thegaminghuskymc.sandboxgame.game.mod;

import net.thegaminghuskymc.sandboxgame.engine.block.*;
import net.thegaminghuskymc.sandboxgame.engine.managers.BlockManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;

import java.util.LinkedList;

public class Blocks implements IModResource {

    public static final int AIR_ID = 0;

    /**
     * load default blocks
     */
    public static Block AIR;
    public static Block DIRT;
    public static Block GRASS;
    public static Block LIGHT;
    public static Block WATER;
    public static Block COBBLESTONE;
    public static Block GLASS;
    public static Block SAND;
    public static Block FURNACE;
    public static Block HAY_BALE;
    public static Block BEACON;
    public static Block JUKEBOX;
    public static Block CAULDRON;

    public static LinkedList<Block> PLANTS = new LinkedList<>();
    public static LinkedList<Block> PLANKS = new LinkedList<>();
    public static LinkedList<Block> LOGS = new LinkedList<>();
    public static LinkedList<Block> STAIRS = new LinkedList<>();
    public static LinkedList<Block> SLABS = new LinkedList<>();
    public static LinkedList<Block> DOORS = new LinkedList<>();
    public static LinkedList<Block> CHESTS = new LinkedList<>();
    public static LinkedList<Block> LEAVES = new LinkedList<>();
    public static LinkedList<Block> STONES = new LinkedList<>();

    public static Block getBlockByID(short blockID) {
        return (BlockManager.instance().getBlockByID(blockID));
    }

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

        for (int i = 0; i < 8; i++) {
            Blocks.PLANTS.add(blockmanager.registerBlock(new BlockPlank(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.PLANKS.add(blockmanager.registerBlock(new BlockPlank(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.LOGS.add(blockmanager.registerBlock(new BlockLog(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.STAIRS.add(blockmanager.registerBlock(new BlockStair(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.SLABS.add(blockmanager.registerBlock(new BlockSlab(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.DOORS.add(blockmanager.registerBlock(new BlockDoor(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.CHESTS.add(blockmanager.registerBlock(new BlockChest(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.LEAVES.add(blockmanager.registerBlock(new BlockLeaves(blockmanager.getNextID())));
        }
        for (int i = 0; i < 7; i++) {
            Blocks.STONES.add(blockmanager.registerBlock(new BlockStone(blockmanager.getNextID())));
        }
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {
    }

}
