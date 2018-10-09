package team.hdt.blockadia.engine.core.init;

import team.hdt.blockadia.engine.client.util.Identifier;
import team.hdt.blockadia.engine.core.block.BlockBuilder;
import team.hdt.blockadia.engine.core.block.BlockType;
import team.hdt.blockadia.engine.core.block.material.BlockMaterials;
import team.hdt.blockadia.engine.core.block.material.MapColor;
import team.hdt.blockadia.engine.core.registries.BlockRegistry;

/*
 * This class is made by HuskyTheArtist
 * the 18.08.2018 at 13.59
 */
public class Blocks {

    public static final BlockType AIR = new BlockBuilder().withTranslationKey("air").withMaterial(BlockMaterials.AIR).withMapColor(MapColor.AIR).build();
    public static final BlockType CAVE_AIR = new BlockBuilder().withTranslationKey("cave_air").withMaterial(BlockMaterials.AIR).withMapColor(MapColor.AIR).build();
    public static final BlockType VOID_AIR = new BlockBuilder().withTranslationKey("void_air").withMaterial(BlockMaterials.AIR).withMapColor(MapColor.AIR).build();
    public static final BlockType ROCK = new BlockBuilder().withTranslationKey("rock").withMaterial(BlockMaterials.ROCK).withMapColor(MapColor.STONE).build();
    public static final BlockType GRASS = new BlockBuilder().withTranslationKey("grass").withMaterial(BlockMaterials.GRASS).withMapColor(MapColor.GRASS).build();
    public static final BlockType DIRT = new BlockBuilder().withTranslationKey("dirt").withMaterial(BlockMaterials.GRASS).withMapColor(MapColor.DIRT).build();
    public static final BlockType SAND = new BlockBuilder().withTranslationKey("sand").withMaterial(BlockMaterials.SAND).withMapColor(MapColor.SAND).build();
    public static final BlockType OAK_PLANKS = new BlockBuilder().withTranslationKey("oak_planks").withMaterial(BlockMaterials.WOOD).withMapColor(MapColor.WOOD).build();
    public static final BlockType OAK_LOG = new BlockBuilder().withTranslationKey("oak_planks").withMaterial(BlockMaterials.WOOD).withMapColor(MapColor.WOOD).build();
    public static final BlockType OAK_LEAVES = new BlockBuilder().withTranslationKey("oak_leaves").withMaterial(BlockMaterials.FOLIAGE).withMapColor(MapColor.FOLIAGE).build();
    public static final BlockType BEDROCK = new BlockBuilder().withTranslationKey("bedrock").withMaterial(BlockMaterials.METAL).withMapColor(MapColor.GRAY).build();

    public static void register() {
        registerBlockType("air", AIR);
        registerBlockType("cave_air", CAVE_AIR);
        registerBlockType("void_air", VOID_AIR);
        registerBlockType("rock", ROCK);
        registerBlockType("grass", GRASS);
        registerBlockType("dirt", DIRT);
        registerBlockType("sand", SAND);
        registerBlockType("oak_planks", OAK_PLANKS);
        registerBlockType("oak_log", OAK_LOG);
        registerBlockType("oak_leaves", OAK_LEAVES);
        registerBlockType("bedrock", BEDROCK);
    }

    private static void registerBlockType(String name, BlockType blockType) {
        BlockRegistry.registries.register(new Identifier(name), blockType);
    }

}