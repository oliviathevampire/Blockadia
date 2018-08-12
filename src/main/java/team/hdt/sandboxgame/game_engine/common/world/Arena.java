package team.hdt.sandboxgame.game_engine.common.world;

import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.world.block.BlockType;
import team.hdt.sandboxgame.game_engine.common.world.player.Projectile;

import java.util.ArrayList;

public class Arena {

    public final int X_SIZE = 10, Y_SIZE = 10, Z_SIZE = 10;
    final int CUBE_LENGTH = 1;
    public BlockType[][][] blocks;
    private ArrayList<Projectile> projectiles;

    public Arena() {

    }

    public void addProjectile(Projectile proj) {
        if (projectiles == null)
            projectiles = new ArrayList<Projectile>();
        projectiles.add(proj);
    }

    public void genTwoBlocks() {
        blocks = new BlockType[1][1][2];
        // TODO
//        blocks[0][0][0] = new BlockType(0, 0, 0, BlockType.BlockType.DIRT);
//        blocks[0][0][1] = new BlockType(0, 0, 1, BlockType.BlockType.DIRT);
    }

    public boolean inBounds(int x, int y, int z) {
        return (x >= 0 && x < X_SIZE && y >= 0 && y < Y_SIZE && z >= 0 && z < Z_SIZE);
    }

    public void genArena() {
        blocks = new BlockType[X_SIZE][Y_SIZE][Z_SIZE];
//        BlockType.BlockType type;
//        for (int x = 0; x < X_SIZE; x++)
//            for (int y = 0; y < Y_SIZE; y++)
//                for (int z = 0; z < Z_SIZE; z++) {
//                    if (y == Y_SIZE - 1 && x == 5 && z == 5)
//                        type = BlockType.BlockType.GRASS;
//                    else if (y < 5)
//                        type = BlockType.BlockType.STONE;
//                    else type = BlockType.BlockType.AIR;
//                    blocks[x][y][z] = new BlockType(x, y, z, type);
//                }
        System.out.println("Done building Arena");
    }

    public void genDemoBlocks() {
        blocks = new BlockType[6][1][2];
//        for (int i = 0; i < 6; i++) {
//            for (int j = 0; j < 2; j++) {
//                switch (i) {
//                    case 0:
//                    case 1:
//                        blocks[i][0][j] = new BlockType(i, 0, j, BlockType.BlockType.SAND);
//                        break;
//                    case 2:
//                    case 3:
//                        blocks[i][0][j] = new BlockType(i, 0, j, BlockType.BlockType.DIRT);
//                        break;
//                    case 4:
//                    case 5:
//                        blocks[i][0][j] = new BlockType(i, 0, j, BlockType.BlockType.WOOD);
//                        break;
//                }
//            }
//        }
    }

    public void generate() {
        blocks = new BlockType[X_SIZE][Y_SIZE][Z_SIZE];
        System.out.println(blocks.length);
//        for (int x = 0; x < X_SIZE; x++)
//            for (int y = 0; y < Y_SIZE; y++)
//                for (int z = 0; z < Z_SIZE; z++) {
//                    if (y > 4) {
//                        blocks[x][y][z] = new BlockType(x, y, z, BlockType.BlockType.AIR);
//                    } else if ((x == 2 || x == 3) && z > 2 && y > 0)
//                        blocks[x][y][z] = new BlockType(x, y, z, BlockType.BlockType.WATER);
//                    else
//                        blocks[x][y][z] = new BlockType(x, y, z, BlockType.BlockType.DIRT);
//                }
//        for (int x = 0; x < X_SIZE; x++) {
//            blocks[x][5][0] = new BlockType(x, 5, 0, BlockType.BlockType.DIRT);
//            blocks[x][5][Z_SIZE - 1] = new BlockType(x, 5, 0, BlockType.BlockType.DIRT);
//        }
//        blocks[5][5][5] = new BlockType(5, 5, 5, BlockType.BlockType.GRASS);
    }

    public void update() {
        if (projectiles != null)
            for (Projectile projectile : projectiles)
                projectile.update();
    }

    public void render() {
//        for (BlockType[][] blockX : blocks)
//            for (BlockType[] blockY : blockX)
//                for (BlockType block : blockY)
//                    if (block.getType() != BlockType.BlockType.AIR)
//                        block.render();
        if (projectiles != null)
            for (Projectile projectile : projectiles)
                projectile.render();
    }

    public boolean contains(Vectors3f pos) {
        return (pos.x >= 0 && pos.x < X_SIZE &&
                pos.y >= 0 && pos.y < Y_SIZE &&
                pos.z >= 0 && pos.z < Z_SIZE);
    }

}
