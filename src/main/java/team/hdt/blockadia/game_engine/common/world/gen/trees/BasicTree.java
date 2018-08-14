package team.hdt.blockadia.game_engine.common.world.gen.trees;

import team.hdt.blockadia.game_engine.common.world.block.BlockType;
import team.hdt.blockadia.game_engine.common.world.block.BlockTypes;
import team.hdt.blockadia.game_engine.common.world.gen.interfaces.ITree;

public class BasicTree implements ITree {

    private BlockType log, leaves;

    public BasicTree(BlockType log, BlockType leaves) {
        this.log = log;
        this.leaves = leaves;
    }

    @Override
    public float getTreeHeight() {
        return 2.0f;
    }

    @Override
    public BlockType getLeafBlockType() {
        return leaves;
    }

    @Override
    public BlockType getLogBlockType() {
        return log;
    }

    @Override
    public BlockType blockCanGrowOn(BlockTypes blockTypes) {
        return BlockTypes.GRASS;
    }

}