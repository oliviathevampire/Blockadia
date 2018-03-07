package net.thegaminghuskymc.sandboxgame.game.mod;

import net.thegaminghuskymc.sandboxgame.engine.block.*;
import net.thegaminghuskymc.sandboxgame.engine.managers.BlockManager;
import net.thegaminghuskymc.sandboxgame.engine.managers.ResourceManager;
import net.thegaminghuskymc.sandboxgame.engine.modding.IModResource;
import net.thegaminghuskymc.sandboxgame.engine.modding.Mod;
import net.thegaminghuskymc.sandboxgame.engine.registries.GameData;
import net.thegaminghuskymc.sandboxgame.engine.registry.RegistryNamespacedDefaultedByKey;
import net.thegaminghuskymc.sandboxgame.engine.util.ResourceLocation;

import java.util.LinkedList;

public class Blocks implements IModResource {

    private static final ResourceLocation AIR_ID = new ResourceLocation("air");

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
    public static LinkedList<Block> SNOW_LAYERS = new LinkedList<>();

    public static Block getBlockByID(short blockID) {
        return (BlockManager.instance().getBlockByID(blockID));
    }

    @Override
    public void load(Mod mod, ResourceManager manager) {
        BlockManager blockmanager = manager.getBlockManager();
        this.loadBlocks(blockmanager);
    }

    private static void registerBlock(int id, ResourceLocation textualID, Block block_)
    {
        Block.REGISTRY.register(id, textualID, block_);
    }

    private static void registerBlock(int id, String textualID, Block block_)
    {
        registerBlock(id, new ResourceLocation(textualID), block_);
    }

    private void loadBlocks(BlockManager blockmanager) {
        BlockManager.registerBlock(0, AIR_ID, new BlockAir());
        BlockManager.registerBlock(1, new ResourceLocation(""), new BlockWater(blockmanager.getNextID()));
        BlockManager.registerBlock(2, new ResourceLocation(""), new BlockCobblestone(blockmanager.getNextID()));

        BlockManager.registerBlock(3, new ResourceLocation("glass"), new BlockCobblestone(blockmanager.getNextID()));
        BlockManager.registerBlock(4, new ResourceLocation("sand"), new BlockCobblestone(blockmanager.getNextID()));
        BlockManager.registerBlock(5, new ResourceLocation("furnace"), new BlockCobblestone(blockmanager.getNextID()));
        BlockManager.registerBlock(6, new ResourceLocation("hay_ball"), new BlockCobblestone(blockmanager.getNextID()));
        BlockManager.registerBlock(7, new ResourceLocation("beacon"), new BlockCobblestone(blockmanager.getNextID()));
        BlockManager.registerBlock(8, new ResourceLocation("jukebox"), new BlockCobblestone(blockmanager.getNextID()));
        BlockManager.registerBlock(9, new ResourceLocation("cauldron"), new BlockCobblestone(blockmanager.getNextID()));
        BlockManager.registerBlock(10, new ResourceLocation("dirt"), new BlockDirt(blockmanager.getNextID()));
        BlockManager.registerBlock(11, new ResourceLocation("grass_block"), new BlockGrass(blockmanager.getNextID()));

        for (int i = 0; i < 5; i++) {
            Blocks.PLANTS.add(BlockManager.registerBlock(new BlockPlant(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.PLANKS.add(BlockManager.registerBlock(new BlockPlank(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.LOGS.add(BlockManager.registerBlock(new BlockLog(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.STAIRS.add(BlockManager.registerBlock(new BlockStair(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.SLABS.add(BlockManager.registerBlock(new BlockSlab(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.DOORS.add(BlockManager.registerBlock(new BlockDoor(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.CHESTS.add(BlockManager.registerBlock(new BlockChest(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.LEAVES.add(BlockManager.registerBlock(new BlockLeaves(blockmanager.getNextID())));
        }
        for (int i = 0; i < 7; i++) {
            Blocks.STONES.add(BlockManager.registerBlock(new BlockStone(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.WOOL.add(BlockManager.registerBlock(new BlockWool(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.STAINED_GLASS.add(BlockManager.registerBlock(new BlockStainedGlass(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.TERRACOTTA.add(BlockManager.registerBlock(new BlockTerracotta(blockmanager.getNextID())));
        }
        for (int i = 0; i < 3; i++) {
            Blocks.CAKE.add(BlockManager.registerBlock(new BlockCake(blockmanager.getNextID())));
        }
        for (int i = 0; i < 4; i++) {
            Blocks.CROPS.add(BlockManager.registerBlock(new BlockCrop(blockmanager.getNextID())));
        }
        for (int i = 0; i < 19; i++) {
            Blocks.CORALS.add(BlockManager.registerBlock(new BlockCoral(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.TRAPDOORS.add(BlockManager.registerBlock(new BlockTrapdoor(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.CRAFTING_TABLE.add(BlockManager.registerBlock(new BlockCraftingTable(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.POLES.add(BlockManager.registerBlock(new BlockPole(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.TABLES.add(BlockManager.registerBlock(new BlockTable(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.COFFEE_TABLES.add(BlockManager.registerBlock(new BlockCoffeeTable(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.CHAIRS.add(BlockManager.registerBlock(new BlockChair(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.PRESSUR_PLATES.add(BlockManager.registerBlock(new BlockPressurPlate(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.BUTTONS.add(BlockManager.registerBlock(new BlockButton(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.FENCES.add(BlockManager.registerBlock(new BlockFence(blockmanager.getNextID())));
        }
        for (int i = 0; i < 8; i++) {
            Blocks.FENCE_GATES.add(BlockManager.registerBlock(new BlockFenceGate(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.BED.add(BlockManager.registerBlock(new BlockBed(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.BANNERS.add(BlockManager.registerBlock(new BlockBanner(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.CARPETS.add(BlockManager.registerBlock(new BlockCarpet(blockmanager.getNextID())));
        }
        for (int i = 0; i < 18; i++) {
            Blocks.WOOL_SLABS.add(BlockManager.registerBlock(new BlockSlab(blockmanager.getNextID())));
        }
        for (int i = 0; i < 23; i++) {
            Blocks.ORES.add(BlockManager.registerBlock(new BlockStone(blockmanager.getNextID())));
        }
        for (int i = 0; i < 23; i++) {
            Blocks.STORAGE_BLOCKS.add(BlockManager.registerBlock(new BlockStorage(blockmanager.getNextID())));
        }
        for (int i = 0; i < 2; i++) {
            Blocks.LIGHT.add(BlockManager.registerBlock(new BlockLight(blockmanager.getNextID())));
        }
    }

    @Override
    public void unload(Mod mod, ResourceManager manager) {

    }

}
