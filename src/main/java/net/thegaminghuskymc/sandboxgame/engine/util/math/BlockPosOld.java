package net.thegaminghuskymc.sandboxgame.engine.util.math;

public class BlockPosOld {
	
	public int x, y, z;
	
	public BlockPosOld(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public BlockPosOld() {
		this(0,0,0);
	}
	
	public BlockPosOld(Vector v) {
		this((int) Math.round(v.x), (int) Math.round(v.y), (int) Math.round(v.z));
	}
	
	public BlockPosOld(BlockPosOld b) {
		this(b.x, b.y, b.z);
	}

	@Override
	public String toString() {
		return "[" +
                x +
                ", " +
                y +
                ", " +
                z +
                "]";
	}

}
