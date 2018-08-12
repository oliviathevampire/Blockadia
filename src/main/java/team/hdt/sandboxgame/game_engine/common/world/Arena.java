package team.hdt.sandboxgame.game_engine.common.world;


import team.hdt.sandboxgame.game_engine.common.util.math.vectors.Vectors3f;
import team.hdt.sandboxgame.game_engine.common.world.block.Block;


public class Arena {

    public final int X_SIZE = 10, Y_SIZE = 10, Z_SIZE = 10;
    final int CUBE_LENGTH = 1;
    public Block[][][] blocks;

    public Arena() {

    }

    public void genTwoBlocks() {
        blocks = new Block[1][1][2];
        blocks[0][0][0] = new Block(0, 0, 0, Block.BlockType.DIRT);
        blocks[0][0][1] = new Block(0, 0, 1, Block.BlockType.DIRT);
    }

    public boolean inBounds(int x, int y, int z) {
        return (x >= 0 && x < X_SIZE && y >= 0 && y < Y_SIZE && z >= 0 && z < Z_SIZE);
    }

    public void genArena() {
        blocks = new Block[X_SIZE][Y_SIZE][Z_SIZE];
        Block.BlockType type;
        for (int x = 0; x < X_SIZE; x++)
            for (int y = 0; y < Y_SIZE; y++)
                for (int z = 0; z < Z_SIZE; z++) {
                    if (y == Y_SIZE - 1 && x == 5 && z == 5)
                        type = Block.BlockType.GRASS;
                    else if (y < 5)
                        type = Block.BlockType.STONE;
                    else type = Block.BlockType.AIR;
                    blocks[x][y][z] = new Block(x, y, z, type);
                }
        System.out.println("Done building Arena");
    }

    public void genDemoBlocks() {
        blocks = new Block[6][1][2];
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 2; j++) {
                switch (i) {
                    case 0:
                    case 1:
                        blocks[i][0][j] = new Block(i, 0, j, Block.BlockType.SAND);
                        break;
                    case 2:
                    case 3:
                        blocks[i][0][j] = new Block(i, 0, j, Block.BlockType.DIRT);
                        break;
                    case 4:
                    case 5:
                        blocks[i][0][j] = new Block(i, 0, j, Block.BlockType.WOOD);
                        break;
                }
            }
        }
    }

    public void generate() {
        blocks = new Block[X_SIZE][Y_SIZE][Z_SIZE];
        System.out.println(blocks.length);
        for (int x = 0; x < X_SIZE; x++)
            for (int y = 0; y < Y_SIZE; y++)
                for (int z = 0; z < Z_SIZE; z++) {
                    if (y > 4) {
                        blocks[x][y][z] = new Block(x, y, z, Block.BlockType.AIR);
                    } else if ((x == 2 || x == 3) && z > 2 && y > 0)
                        blocks[x][y][z] = new Block(x, y, z, Block.BlockType.WATER);
                    else
                        blocks[x][y][z] = new Block(x, y, z, Block.BlockType.DIRT);
                }
        for (int x = 0; x < X_SIZE; x++) {
            blocks[x][5][0] = new Block(x, 5, 0, Block.BlockType.DIRT);
            blocks[x][5][Z_SIZE - 1] = new Block(x, 5, 0, Block.BlockType.DIRT);
        }
        blocks[5][5][5] = new Block(5, 5, 5, Block.BlockType.GRASS);
    }

    public void render() {
        for (Block[][] blockX : blocks)
            for (Block[] blockY : blockX)
                for (Block block : blockY)
                    if (block.getType() != Block.BlockType.AIR)
                        block.render();
    }

    public boolean contains(Vectors3f pos) {
        return (pos.x >= 0 && pos.x < X_SIZE &&
                pos.y >= 0 && pos.y < Y_SIZE &&
                pos.z >= 0 && pos.z < Z_SIZE);
    }

}