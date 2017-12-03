package net.thegaminghuskymc.sandboxgame.engine.block;

public class BlockSlab extends BlockCube {

	public BlockSlab(int blockID) {
		super(blockID);
	}

	@Override
	public String getUnlocalizedName() {
		return null;
	}

	@Override
	public String getRegistryName() {
		return null;
	}

	@Override
	public boolean isOpaque() {
		return (false);
	}

	@Override
	public boolean hasTransparency() {
		return (true);
	}

	@Override
	public boolean isVisible() {
		return (true);
	}

}