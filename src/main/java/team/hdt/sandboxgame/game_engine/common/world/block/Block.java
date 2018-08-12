package team.hdt.sandboxgame.game_engine.common.world.block;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.world.Entity;

public class Block extends Entity {

    private BlockType type;
    private boolean isActive;
    private BlockVBO vbo;

    public Block(int x, int y, int z, BlockType type) {
        super(x, y, z);
        this.type = type;
        this.vbo = BlockVBO.getInstance();
    }

    public BlockType getType() {
        return type;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isWalkThroughable() {
        switch (type) {
            case WATER:
            case AIR:
                return true;
            default:
                return false;
        }
    }

    public int getID() {
        return type.getID();
    }

    @Override
    public void update() {

    }

    @Override
    public void render() {
        this.vbo.render(type, x, y, z);
    }

    public boolean contains(Vectors3f loc) {
        return (loc.x > x && loc.x < x + 1 &&
                loc.y > y && loc.y < y + 1 &&
                loc.z > z && loc.z < z + 1);
    }

    public void move(float dx, float dy, float dz) {
        this.x += dx;
        this.y += dy;
        this.z += dz;
    }

    public enum BlockType {
        AIR(0), GRASS(1), DIRT(2), WATER(3), STONE(4), WOOD(5), SAND(6), OUTLINE(7), FIRE(8);

        private int blockID;

        BlockType(int i) {
            blockID = i;
        }

        public int getID() {
            return blockID;
        }
    }

}