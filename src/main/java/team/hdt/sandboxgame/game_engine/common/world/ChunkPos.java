package team.hdt.sandboxgame.game_engine.common.world;

public class ChunkPos {

    private long posX,posZ;

    public ChunkPos(long x, long z){
        this.posX = x;
        this.posZ = z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ChunkPos){
            return (((ChunkPos) obj).posX == this.posX) && (((ChunkPos) obj).posZ == this.posZ);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 3 * 2 * (int)(posX+posZ);
    }

    public long getPosX() {
        return posX;
    }

    public long getPosZ() {
        return posZ;
    }
}
