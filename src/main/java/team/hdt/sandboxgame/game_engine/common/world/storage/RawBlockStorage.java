package team.hdt.sandboxgame.game_engine.common.world.storage;

import team.hdt.sandboxgame.game_engine.common.world.block.BlockType;

public interface RawBlockStorage {
    void setBlockUnchecked(int x, int y, int z, BlockType type);

    BlockType getBlockUnchecked(int x, int y, int z);

    int getSizeX();

    int getSizeY();

    int getSizeZ();
}
