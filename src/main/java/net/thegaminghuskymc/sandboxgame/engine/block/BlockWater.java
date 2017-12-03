package net.thegaminghuskymc.sandboxgame.engine.block;


import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;

public class BlockWater extends BlockLiquid {

	public BlockWater(int blockID) {
		super(blockID);
	}

    @Override
    public String getRegistryName() {
        return "water";
    }

    @Override
    public String getUnlocalizedName() {
        return "Water";
    }

    @Override
	public boolean isOpaque() {
		return (false);
	}

	public boolean hasTransparency() {
		return (true);
	}

	@Override
	public void update(Terrain terrain, int x, int y, int z) {

	}

	@Override
	public void onSet(Terrain terrain, int x, int y, int z) {

	}

}
