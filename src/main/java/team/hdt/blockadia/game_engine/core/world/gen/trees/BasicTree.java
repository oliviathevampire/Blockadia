package team.hdt.blockadia.game_engine.core.world.gen.trees;

import team.hdt.blockadia.game_engine.core.block.BlockType;
import team.hdt.blockadia.game_engine.core.registries.BlockRegistry;
import team.hdt.blockadia.game_engine.core.world.gen.interfaces.ITree;

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
    public BlockType blockCanGrowOn(BlockRegistry blockTypes) {
        return BlockRegistry.GRASS;
    }

}