package team.hdt.blockadia.game_engine.common.world.block;

import team.hdt.blockadia.game_engine.common.Identifier;
import team.hdt.blockadia.game_engine.common.registry.DefaultedHashIdRegistry;
import team.hdt.blockadia.game_engine.common.registry.IdRegistry;
import team.hdt.blockadia.game_engine.common.world.block.material.BlockMaterials;

public class BlockTypes {

    public static final BlockType AIR = new BlockBuilder().withTranslationKey("air").withMaterial(BlockMaterials.AIR).build();
    public static final BlockType CAVE_AIR = new BlockBuilder().withTranslationKey("cave_air").withMaterial(BlockMaterials.AIR).build();
    public static final BlockType VOID_AIR = new BlockBuilder().withTranslationKey("void_air").withMaterial(BlockMaterials.AIR).build();
    public static final BlockType ROCK = new BlockBuilder().withTranslationKey("rock").withMaterial(BlockMaterials.ROCK).build();
    public static final BlockType GRASS = new BlockBuilder().withTranslationKey("grass").withMaterial(BlockMaterials.GRASS).build();
    public static final BlockType DIRT = new BlockBuilder().withTranslationKey("dirt").withMaterial(BlockMaterials.GRASS).build();
    public static final BlockType SAND = new BlockBuilder().withTranslationKey("sand").withMaterial(BlockMaterials.SAND).build();
    public static final BlockType OAK_PLANKS = new BlockBuilder().withTranslationKey("oak_planks").withMaterial(BlockMaterials.WOOD).build();
    public static final BlockType OAK_LOG = new BlockBuilder().withTranslationKey("oak_planks").withMaterial(BlockMaterials.WOOD).build();
    public static final BlockType OAK_LEAVES = new BlockBuilder().withTranslationKey("oak_leaves").withMaterial(BlockMaterials.FOLIAGE).build();
    public static final BlockType BEDROCK = new BlockBuilder().withTranslationKey("bedrock").withMaterial(BlockMaterials.METAL).build();

    public static final IdRegistry<BlockType> REGISTRY = new DefaultedHashIdRegistry<BlockType>()
            .withDefaultEntry(AIR);

    public static void register() {
        registerBlockType("air", AIR, 0);
        registerBlockType("cave_air", CAVE_AIR, 1);
        registerBlockType("void_air", VOID_AIR, 2);
        registerBlockType("rock", ROCK, 3);
        registerBlockType("grass", GRASS, 4);
        registerBlockType("dirt", DIRT, 5);
        registerBlockType("sand", SAND, 6);
        registerBlockType("oak_planks", OAK_PLANKS, 7);
        registerBlockType("oak_log", OAK_LOG, 8);
        registerBlockType("oak_leaves", OAK_LEAVES, 9);
        registerBlockType("bedrock", BEDROCK, 10);
    }

    private static void registerBlockType(String registryName, BlockType type, int id) {
        REGISTRY.register(new Identifier(registryName), type, id);
    }

}
