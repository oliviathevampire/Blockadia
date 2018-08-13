package team.hdt.sandboxgame.game_engine.common.world.misc;

public class GridGenerator {
	
	public static int[] generateGridIndexBuffer(int vertexCount, boolean evenTile) {
		int testBit = (vertexCount%2==0) && !evenTile ? 1 : 0;
		int indexCount = (vertexCount - 1) * (vertexCount - 1) * 6;
		int[] indices = new int[indexCount];
		int pointer = 0;
		for (int col = 0; col < vertexCount - 1; col++) {
			for (int row = 0; row < vertexCount - 1; row++) {
				int topLeft = (row * vertexCount) + col;
				int topRight = topLeft + 1;
				int bottomLeft = ((row + 1) * vertexCount) + col;
				int bottomRight = bottomLeft + 1;
				if (row % 2 == 0) {
					pointer = storeQuad1(indices, pointer, topLeft, topRight, bottomLeft, bottomRight, col % 2 == testBit);
				} else {
					pointer = storeQuad2(indices, pointer, topLeft, topRight, bottomLeft, bottomRight, col % 2 == testBit);
				}
			}
		}
		return indices;
	}

	private static int storeQuad1(int[] indices, int pointer, int topLeft, int topRight, int bottomLeft,
			int bottomRight, boolean mixed) {
		indices[pointer++] = topLeft;
		indices[pointer++] = bottomLeft;
		indices[pointer++] = mixed ? topRight : bottomRight;
		indices[pointer++] = bottomRight;
		indices[pointer++] = topRight;
		indices[pointer++] = mixed ? bottomLeft : topLeft;
		return pointer;
	}

	private static int storeQuad2(int[] indices, int pointer, int topLeft, int topRight, int bottomLeft,
			int bottomRight, boolean mixed) {
		indices[pointer++] = topRight;
		indices[pointer++] = topLeft;
		indices[pointer++] = mixed ? bottomRight : bottomLeft;
		indices[pointer++] = bottomLeft;
		indices[pointer++] = bottomRight;
		indices[pointer++] = mixed ? topLeft : topRight;
		return pointer;
	}

}
