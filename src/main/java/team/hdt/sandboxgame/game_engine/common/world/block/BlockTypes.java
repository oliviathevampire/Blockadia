package team.hdt.sandboxgame.game_engine.common.world.block;

import team.hdt.sandboxgame.game_engine.common.Identifier;
import team.hdt.sandboxgame.game_engine.common.registry.DefaultedHashIdRegistry;
import team.hdt.sandboxgame.game_engine.common.registry.IdRegistry;
import team.hdt.sandboxgame.game_engine.common.world.block.material.BlockMaterials;

public class BlockTypes {

    public static final BlockType AIR = new BlockBuilder().withTranslationKey("air").withMaterial(BlockMaterials.AIR).build();
    public static final BlockType CAVE_AIR = new BlockBuilder().withTranslationKey("cave_air").withMaterial(BlockMaterials.AIR).build();
    public static final BlockType VOID_AIR = new BlockBuilder().withTranslationKey("void_air").withMaterial(BlockMaterials.AIR).build();
    public static final BlockType ROCK = new BlockBuilder().withTranslationKey("rock").withMaterial(BlockMaterials.ROCK).build();
    public static final BlockType SAND = new BlockBuilder().withTranslationKey("sand").withMaterial(BlockMaterials.SAND).build();
    public static final BlockType WOOD = new BlockBuilder().withTranslationKey("wood").withMaterial(BlockMaterials.WOOD).build();
    public static final BlockType PLANKS = new BlockBuilder().withTranslationKey("planks").withMaterial(BlockMaterials.WOOD).build();

    public static final IdRegistry<BlockType> REGISTRY = new DefaultedHashIdRegistry<BlockType>()
            .withDefaultEntry(AIR);

    public static void register() {
        registerBlockType("air", AIR, 0);
        registerBlockType("cave_air", CAVE_AIR, 1);
        registerBlockType("void_air", VOID_AIR, 2);
        registerBlockType("rock", ROCK, 3);
        registerBlockType("sand", SAND, 4);
        registerBlockType("wood", WOOD, 5);
        registerBlockType("planks", PLANKS, 6);
    }

    private static void registerBlockType(String registryName, BlockType type, int id) {
        REGISTRY.register(new Identifier(registryName), type, id);
    }

}
