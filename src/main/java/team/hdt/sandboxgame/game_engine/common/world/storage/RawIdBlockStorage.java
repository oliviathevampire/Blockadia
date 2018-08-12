package team.hdt.sandboxgame.game_engine.common.world.storage;

import team.hdt.sandboxgame.game_engine.common.world.block.BlockTypes;
import team.hdt.sandboxgame.game_engine.common.world.block.BlockType;

public class RawIdBlockStorage implements RawBlockStorage {
    private final byte[] data;
    private final int sizeX;
    private final int sizeY;
    private final int sizeZ;

    public RawIdBlockStorage(int sizeX, int sizeY, int sizeZ) {
        this.data = new byte[sizeX * sizeY * sizeZ];
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
    }

    @Override
    public void setBlockUnchecked(int x, int y, int z, BlockType type) {
        int id = BlockTypes.REGISTRY.getId(type.getIdentifier()) & 0xFF;
        this.data[this.getIndexUnchecked(x, y, z)] = (byte) id;
    }

    @Override
    public BlockType getBlockUnchecked(int x, int y, int z) {
        byte id = this.data[this.getIndexUnchecked(x, y, z)];
        return BlockTypes.REGISTRY.get(id & 0xFF);
    }

    @Override
    public int getSizeX() {
        return this.sizeX;
    }

    @Override
    public int getSizeY() {
        return this.sizeY;
    }

    @Override
    public int getSizeZ() {
        return this.sizeZ;
    }

    private int getIndexUnchecked(int x, int y, int z) {
        return x + (z + y * this.sizeZ) * this.sizeX;
    }
}
