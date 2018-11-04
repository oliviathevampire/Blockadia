package team.hdt.blockadia.old_engine_code_1.core.world.gen.trees;

import team.hdt.blockadia.engine_rewrite.client.blocks.BlockType;
import team.hdt.blockadia.old_engine_code_1.core.init.Blocks;
import team.hdt.blockadia.old_engine_code_1.core.registries.BlockRegistry;
import team.hdt.blockadia.old_engine_code_1.core.world.gen.interfaces.ITree;

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
        return Blocks.GRASS;
    }

}