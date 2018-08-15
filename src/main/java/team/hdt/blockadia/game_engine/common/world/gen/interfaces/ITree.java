package team.hdt.blockadia.game_engine.common.world.gen.interfaces;

import team.hdt.blockadia.game_engine.common.world.block.BlockType;
import team.hdt.blockadia.game_engine.common.registry.BlockRegistry;

public interface ITree {

    float getTreeHeight();

    BlockType getLeafBlockType();

    BlockType getLogBlockType();

    BlockType blockCanGrowOn(BlockRegistry blockRegistry);

}
