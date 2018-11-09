package team.hdt.blockadia.old_engine_code_1.core.world.gen.interfaces;

import team.hdt.blockadia.engine_rewrite.client.blocks.BlockType;
import team.hdt.blockadia.old_engine_code_1.core.registries.BlockRegistry;

public interface ITree {

    float getTreeHeight();

    BlockType getLeafBlockType();

    BlockType getLogBlockType();

    BlockType blockCanGrowOn(BlockRegistry blockTypes);

}
