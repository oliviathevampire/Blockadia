package team.hdt.sandboxgame.game_engine.common.world;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.world.block.BlockType;

import static java.lang.Math.*;

public class Physics {

    public Physics() {

    }

    /**
     * @param entity
     * @param dx
     * @param dz
     * @param camEntity
     * @return true if CAN move
     */
    public static boolean moveWithCollisions(Entity entity, float dx, float dz, Entity camEntity) {
        if (camEntity == null)
            camEntity = entity;
        Arena arena = entity.arena;
        if (entity.x < 0 || entity.y < 0 || entity.z < 0 || entity.x >= arena.X_SIZE || entity.y >= arena.Y_SIZE || entity.z >= arena.Z_SIZE)
            return false;
        float xAm = -(dx * (float) sin(toRadians(camEntity.yaw - 90)) + dz * (float) sin(toRadians(camEntity.yaw)));
        float zAm = dx * (float) cos(toRadians(camEntity.yaw - 90)) + dz * (float) cos(toRadians(camEntity.yaw));
        BlockType testBlock = arena.blocks[(int) (entity.x + xAm)][(int) entity.y][(int) (entity.z + zAm)];
        // TODO
//        if (testBlock.isWalkThroughable() ||
//                (int) testBlock.x == (int) entity.x && (int) testBlock.y == (int) entity.y - 1 && (int) testBlock.z == (int) entity.z) {
//            entity.x += xAm;
//            entity.z += zAm;
//        } else {
//            if ((int) (entity.x + xAm) < (int) entity.x)
//                entity.x = (int) entity.x;
//            else if ((int) (entity.x + xAm) > (int) entity.x)
//                entity.x = (int) entity.x + .99f;
//            if ((int) (entity.z + zAm) < (int) entity.z)
//                entity.z = (int) entity.z;
//            else if ((int) (entity.z + zAm) > (int) entity.z)
//                entity.z = (int) entity.z + .99f;
//            return true;
//        }
        return false;
    }

    public static void moveEntityMomentum(Entity entity, Entity player) {
        Vectors3f momentum = entity.momentum;
        float accel = entity.accel;
        moveWithCollisions(entity, entity.speed * momentum.x, entity.speed * -momentum.z, null);
        entity.speed -= .005f + accel;
        entity.accel += .002;
        if (entity.speed < 0)
            entity.speed = 0;
    }

    /**
     * @param entity The entity to add gravity effects to
     * @return true if is falling
     */
    public static boolean gravity(Entity entity) {
        float fallSpeed = entity.fallSpeed + entity.momentum.y * entity.speed;
        Arena arena = entity.arena;
        BlockType blockUnder = arena.blocks[(int) entity.x][(int) entity.y - 1][(int) entity.z];
        // TODO
//        if (!blockUnder.isWalkThroughable()) {
//            if (fallSpeed < 0) {
//                entity.y -= fallSpeed;
//                return true;
//            }
//            if ((int) blockUnder.x == (int) entity.x && (int) blockUnder.y == (int) entity.y - 1 && (int) blockUnder.z == (int) entity.z) {
//                if (entity.y - fallSpeed < blockUnder.y + 1 || entity.y == blockUnder.y + 1) {    // If it will be put underground or it is on the ground
//                    entity.y = blockUnder.y + 1;                                                // Then put it on the ground
//                    return false;
//                }
//            } else {
//                System.out.println("else " + fallSpeed);
//            }
//        }
        entity.y -= fallSpeed;
        return true;
    }
}
