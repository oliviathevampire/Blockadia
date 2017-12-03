package net.thegaminghuskymc.sandboxgame.engine.block.instance;


import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;

/** a class which represent an unique block instance in the world */
public abstract class BlockInstance {

	private final Terrain terrain;
	private final Block block;
	private final int index;

	/**
	 * this constructor should be call with given argument inside function:
	 * Block.createBlockInstance(terrain, x, y, z)
	 */
	public BlockInstance(Terrain terrain, Block block, int index) {
		this.terrain = terrain;
		this.block = block;
		this.index = index;
	}

	/** update function, called every ticks */
	public abstract void update();

	/** called when this instance is set */
	public abstract void onSet();

	/** called when this instance is unset */
	public abstract void onUnset();

	/** get the terrain where this block instance is */
	public Terrain getTerrain() {
		return (this.terrain);
	}

	/** return the block type of this instance */
	public Block getBlock() {
		return (this.block);
	}

	/** get the position of this block instance relative to the terrain */
	public final int getIndex() {
		return (this.index);
	}
}
