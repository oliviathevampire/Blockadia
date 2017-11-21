package net.thegaminghuskymc.sandboxgame.engine.idk;

public class Air extends Block {

	public Air(Vector3 a, float dimension) {
		super(a, dimension, null);
	}
	
	@Override
	public boolean visible() {
		return false;
	}
}
