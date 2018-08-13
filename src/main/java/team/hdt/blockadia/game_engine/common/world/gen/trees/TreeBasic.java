package team.hdt.blockadia.game_engine.common.world.gen.trees;

import team.hdt.blockadia.game_engine.common.world.block.BlockTypes;
import team.hdt.blockadia.game_engine.common.world.gen.interfaces.ITree;

public class TreeBasic implements ITree {
    public float Height;
    public Object model;
    public Object leaf;
    public Object wood;

    @Override
    public float setTreeHeight(float height) {
        return 0;
    }

    @Override
    public void getTreeHeight() {

    }

    @Override
    public void setLeafBlock(Class leaf) {

    }

    @Override
    public void setWoodBlock(Class wood) {

    }

    @Override
    public void getTreeModel() {

    }

    @Override
    public BlockTypes blockCanGrowOn(BlockTypes blockTypes) {
        return null;
    }
}
