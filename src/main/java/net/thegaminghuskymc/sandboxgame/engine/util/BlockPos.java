package net.thegaminghuskymc.sandboxgame.engine.util;

public class BlockPos {
	
	public int x, y, z;
	
	public BlockPos(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BlockPos() {
		this(0,0,0);
	}
	
	public BlockPos(Vector v) {
		this((int) Math.round(v.x), (int) Math.round(v.y), (int) Math.round(v.z));
	}
	
	public BlockPos(BlockPos b) {
		this(b.x, b.y, b.z);
	}

	@Override
	public String toString() {
		return new StringBuilder()
				.append("[")
				.append(x)
				.append(", ")
				.append(y)
				.append(", ")
				.append(z)
				.append("]")
				.toString();
	}

}
