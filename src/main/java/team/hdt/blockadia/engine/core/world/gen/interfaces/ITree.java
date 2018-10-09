package team.hdt.blockadia.engine.core.world.gen.interfaces;

import team.hdt.blockadia.engine.core.block.BlockType;
import team.hdt.blockadia.engine.core.registries.BlockRegistry;

public interface ITree {

    float getTreeHeight();

    BlockType getLeafBlockType();

    BlockType getLogBlockType();

    BlockType blockCanGrowOn(BlockRegistry blockTypes);

}
