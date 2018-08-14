package team.hdt.blockadia.game_engine.common.world.gen.interfaces;

import team.hdt.blockadia.game_engine.common.world.block.BlockType;
import team.hdt.blockadia.game_engine.common.world.block.BlockTypes;

public interface ITree {

    float getTreeHeight();

    BlockType getLeafBlockType();

    BlockType getLogBlockType();

    BlockType blockCanGrowOn(BlockTypes blockTypes);

}
