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
    public static LinkedList<Block> WOOL = new LinkedList<>();
    public static LinkedList<Block> STAINED_GLASS = new LinkedList<>();
    public static LinkedList<Block> TERRACOTTA = new LinkedList<>();
    public static LinkedList<Block> CAKE = new LinkedList<>();
    public static LinkedList<Block> CROPS = new LinkedList<>();
    public static LinkedList<Block> CORALS = new LinkedList<>();
    public static LinkedList<Block> TRAPDOORS = new LinkedList<>();
    public static LinkedList<Block> CRAFTING_TABLE = new LinkedList<>();
    public static LinkedList<Block> POLES = new LinkedList<>();
    public static LinkedList<Block> TABLES = new LinkedList<>();
    public static LinkedList<Block> COFFEE_TABLES = new LinkedList<>();
    public static LinkedList<Block> CHAIRS = new LinkedList<>();
    public static LinkedList<Block> PRESSUR_PLATES = new LinkedList<>();
    public static LinkedList<Block> BUTTONS = new LinkedList<>();
    public static LinkedList<Block> FENCES = new LinkedList<>();
    public static LinkedList<Block> FENCE_GATES = new LinkedList<>();
    public static LinkedList<Block> BED = new LinkedList<>();
    public static LinkedList<Block> BANNERS = new LinkedList<>();
    public static LinkedList<Block> CARPETS = new LinkedList<>();
    public static LinkedList<Block> WOOL_SLABS = new LinkedList<>();
    public static LinkedList<Block> ORES = new LinkedList<>();
    public static LinkedList<Block> STORAGE_BLOCKS = new LinkedList<>();
    public static LinkedList<Block> DIRT = new LinkedList<>();
    public static LinkedList<Block> GRASS = new LinkedList<>();
    public static LinkedList<Block> LIGHT = new LinkedList<>();

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
        Blocks.WATER = blockmanager.registerBlock(new BlockWater(blockmanager.getNextID()));
        Blocks.COBBLESTONE = blockmanager.registerBlock(new BlockCobblestone(blockmanager.getNextID()));

        Blocks.GLASS = blockmanager.registerBlock(new BlockCobblestone(blockmanager.getNextID()));
        Blocks.SAND = blockmanager.registerBlock(new BlockCobblestone(blockmanager.getNextID()));
        Blocks.FURNACE = blockmanager.registerBlock(new BlockCobblestone(blockmanager.getNextID()));
        Blocks.HAY_BALE = blockmanager.registerBlock(new BlockCobblestone(blockmanager.getNextID()));
        Blocks.BEACON = blockmanager.registerBlock(new BlockCobblestone(blockmanager.getNextID()));
        Blocks.JUKEBOX = blockmanager.registerBlock(new BlockCobblestone(blockmanager.getNextID()));
        Blocks.CAULDRON = blockmanager.registerBlock(new BlockCobblestone(blockmanager.getNextID()));

        for (int i = 0; i < 5; i++) {
            Blocks.PLANTS.add(blockmanager.registerBlock(new BlockPlant(blockmanager.getNextID())));
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
        for (int i = 0; i < 18; i++) {
            Blocks.WOOL.add(blockmanager.registerBlock(new BlockWool(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.STAINED_GLASS.add(blockmanager.registerBlock(new BlockStainedGlass(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.TERRACOTTA.add(blockmanager.registerBlock(new BlockTerracotta(blockmanager.getNextID())));
        }
        for (int i = 0; i < 3; i++) {
            Blocks.CAKE.add(blockmanager.registerBlock(new BlockCake(blockmanager.getNextID())));
        }
        for (int i = 0; i < 4; i++) {
            Blocks.CROPS.add(blockmanager.registerBlock(new BlockCrop(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.CORALS.add(blockmanager.registerBlock(new BlockCoral(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.TRAPDOORS.add(blockmanager.registerBlock(new BlockTrapdoor(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.CRAFTING_TABLE.add(blockmanager.registerBlock(new BlockCraftingTable(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.POLES.add(blockmanager.registerBlock(new BlockPole(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.TABLES.add(blockmanager.registerBlock(new BlockTable(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.COFFEE_TABLES.add(blockmanager.registerBlock(new BlockCoffeeTable(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.CHAIRS.add(blockmanager.registerBlock(new BlockChair(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.PRESSUR_PLATES.add(blockmanager.registerBlock(new BlockPressurPlate(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.BUTTONS.add(blockmanager.registerBlock(new BlockButton(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.FENCES.add(blockmanager.registerBlock(new BlockFence(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.FENCE_GATES.add(blockmanager.registerBlock(new BlockFenceGate(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.BED.add(blockmanager.registerBlock(new BlockBed(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.BANNERS.add(blockmanager.registerBlock(new BlockBanner(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.CARPETS.add(blockmanager.registerBlock(new BlockCarpet(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.WOOL_SLABS.add(blockmanager.registerBlock(new BlockSlab(blockmanager.getNextID())));
        }
        for (int i = 0; i < 23; i++) {
            Blocks.ORES.add(blockmanager.registerBlock(new BlockStone(blockmanager.getNextID())));
        }
        for (int i = 0; i < 23; i++) {
            Blocks.STORAGE_BLOCKS.add(blockmanager.registerBlock(new BlockStorage(blockmanager.getNextID())));
        }
        for (int i = 0; i < 2; i++) {
            Blocks.DIRT.add(blockmanager.registerBlock(new BlockDirt(blockmanager.getNextID())));
        }
        for (int i = 0; i < 5; i++) {
            Blocks.GRASS.add(blockmanager.registerBlock(new BlockGrass(blockmanager.getNextID())));
        }
        for (int i = 0; i < 2; i++) {
            Blocks.LIGHT.add(blockmanager.registerBlock(new BlockLight(blockmanager.getNextID())));
        }
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {
    }

}
