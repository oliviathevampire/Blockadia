package team.hdt.blockadia.game_engine.common.world.gen.interfaces;

import team.hdt.blockadia.game_engine.common.world.block.BlockTypes;

public interface ITree {

    public float setTreeHeight(float height);

    public void getTreeHeight();

    public void setLeafBlock(Class leaf);

    public void setWoodBlock(Class wood);

    public void getTreeModel();

    public BlockTypes blockCanGrowOn(BlockTypes blockTypes);
}
