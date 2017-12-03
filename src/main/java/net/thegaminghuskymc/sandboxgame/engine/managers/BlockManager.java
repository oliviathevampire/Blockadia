package net.thegaminghuskymc.sandboxgame.engine.managers;

import net.thegaminghuskymc.sandboxgame.engine.Logger;
import net.thegaminghuskymc.sandboxgame.engine.block.Block;

public class BlockManager extends GenericManager<Block> {

	private static BlockManager BLOCK_MANAGER_INSTANCE;

	public BlockManager(ResourceManager manager) {
		super(manager);
		BLOCK_MANAGER_INSTANCE = this;
	}

	public static BlockManager instance() {
		return (BLOCK_MANAGER_INSTANCE);
	}

	public Block registerBlock(Block block) {
		Logger.get().log(Logger.Level.FINE, "Registering a block: " + block.toString());
		super.registerObject(block);
		return (block);
	}

	public Block getBlockByID(int id) {
		return (super.getObjectByID(id));
	}

	public Block getBlockByName(String name) {
		return super.getObjectByName(name);
	}

	public int getBlockCount() {
		return (super.getObjectCount());
	}

	@Override
	public void onInitialized() {

	}

	@Override
	public void onLoaded() {

	}

	@Override
	protected void onDeinitialized() {

	}

	@Override
	protected void onUnloaded() {

	}

	@Override
	protected void onObjectRegistered(Block object) {

	}

}
