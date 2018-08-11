package team.hdt.sandboxgame.game_engine.client.rendering;

import team.hdt.sandboxgame.game_engine.common.world.block.Block;
import team.hdt.sandboxgame.game_engine.common.world.block.BlockVBO;

public class GLShapes {

    private GLShapes() {

    }

    public static void drawCube(Block.BlockType type, float x, float y, float z) {
        BlockVBO.getInstance().render(type, x, y, z);
    }

}