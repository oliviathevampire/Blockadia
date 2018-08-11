package team.hdt.sandboxgame.game_engine.util;

import team.hdt.sandboxgame.game_engine.world.Block;
import team.hdt.sandboxgame.game_engine.world.BlockVBO;

public class GLShapes {

	private GLShapes() {

	}

	public static void drawCube(Block.BlockType type, float x, float y, float z) {
		BlockVBO.getInstance().render(type, x, y, z);
	}

}