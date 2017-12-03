package net.thegaminghuskymc.sandboxgame.engine.block.instance;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.block.BlockLiquid;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Maths;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

/**
 * the block instance class of a liquid
 * 
 * basically, it contains data for a water voxel
 * 
 * if the voxel is full of water, then it is replace with a static water block,
 * and this instance is destroyed (this will allow to create huge water spaces
 * without memory issues)
 *
 * this instance should only exists if the block is a 'flowing' water voxel
 */
public class BlockInstanceLiquid extends BlockInstance {

	/**
	 * amount of water for this block instance. ( [MIN_LIQUID_AMOUNT,
	 * MAX_LIQUID_AMOUNT] )
	 */
	private short amount;
	private long lastUpdate;

	private Block blockUnder;

	/** minimum amount of water before dispersion */
	public static int TICK_TO_UPDATE = 4;
	public static final short MAX_LIQUID_AMOUNT = 16;
	public static final short MIN_LIQUID_AMOUNT = 1;
	public static final float LIQUID_HEIGHT_UNIT = MIN_LIQUID_AMOUNT / (float) MAX_LIQUID_AMOUNT;
	private static final short DEFAULT_WATER_AMOUNT = MAX_LIQUID_AMOUNT;

	public BlockInstanceLiquid(Terrain terrain, Block block, int index) {
		super(terrain, block, index);
		this.setAmount(DEFAULT_WATER_AMOUNT);
		this.lastUpdate = terrain.getWorld().getTick();
	}

	/**
	 * set the amount of liquid for this instance.
	 *
	 * @param amount
	 */
	public void setAmount(short amount) {
		this.amount = amount;
	}

	public short getAmount() {
		return (this.amount);
	}

	@Override
	public void update() {

		long tick = this.getTerrain().getWorld().getTick();
		if (tick - this.lastUpdate < TICK_TO_UPDATE) {
			return;
		}
		this.lastUpdate = tick;

		if (this.amount < MIN_LIQUID_AMOUNT) {
			this.disperse();
		} else if (this.amount >= MIN_LIQUID_AMOUNT) {
			this.flow();
		}
	}

	private void disperse() {
		int index = super.getIndex();
		super.getTerrain().setBlock(Blocks.AIR, index);
	}

	/** flow the water around */
	private void flow() {

		Terrain terrain = super.getTerrain();
		int index = super.getIndex();
		int z = terrain.getZFromIndex(index);
		int y = terrain.getYFromIndex(index, z);
		int x = terrain.getXFromIndex(index, y, z);

		int[] under = { x, y - 1, z };
		Terrain terrainUnder = terrain.getRelativeTerrain(under);
		if (terrainUnder != null) {

			int indexUnder = terrainUnder.getIndex(under);
			this.blockUnder = terrainUnder.getBlockAt(indexUnder);

			// if the block under is air
			if (this.blockUnder == Blocks.AIR) {
				// fall
				BlockInstanceLiquid liquidUnder = (BlockInstanceLiquid) terrainUnder.setBlock(super.getBlock(),
						indexUnder, under);
				if (liquidUnder != null) {
					terrain.setBlock(Blocks.AIR, index, x, y, z);
					liquidUnder.lastUpdate = this.lastUpdate;
					liquidUnder.setAmount(this.getAmount());
					this.setAmount((short) 0);
				}
			} else {
				if (this.blockUnder instanceof BlockLiquid) {
					// if there is a liquid under
					// transfer liquid to it
					BlockInstance liquidUnder = terrainUnder.getBlockInstanceAt(indexUnder);
					if (liquidUnder != null && liquidUnder instanceof BlockInstanceLiquid) {

						// get the liquid under
						BlockInstanceLiquid liquid = (BlockInstanceLiquid) liquidUnder;

						// get the maximum amount to transfer
						this.transferLiquid(liquid, this.getAmount());
					}
				}
			}

			// if there is still water remaining in this block
			if (this.getAmount() > MIN_LIQUID_AMOUNT) {
				short value = (short) Math.max(this.getAmount() / 4, MIN_LIQUID_AMOUNT);

				for (Vector3i d : NEIGHBOR) {
					// flow it around

					int[] bxyz = { x + d.x, y + d.y, z + d.z };

					Terrain neighborTerrain = terrain.getRelativeTerrain(bxyz);
					if (neighborTerrain == null) {
						continue;
					}

					int neighborIndex = neighborTerrain.getIndex(bxyz);
					Block neighborBlock = neighborTerrain.getBlockAt(neighborIndex);

					// if the neighbor is air
					if (neighborBlock == Blocks.AIR) {

						// then set it to liquid and transfer some liquid to
						// it
						BlockInstance neighborInstance = neighborTerrain.setBlock(super.getBlock(), neighborIndex,
								bxyz);
						if (neighborInstance != null && neighborInstance instanceof BlockInstanceLiquid) {
							BlockInstanceLiquid neighborLiquid = (BlockInstanceLiquid) neighborInstance;
							neighborLiquid.setAmount((short) 0);
							neighborLiquid.lastUpdate = this.lastUpdate;

							// note we only transfer the min liquid amount
							// here
							// to
							// create the flowing effect
							this.transferLiquid(neighborLiquid, value);
						}
					} else {

						// if the neighbor isnt air
						// get the block
						BlockInstance neighborInstance = neighborTerrain.getBlockInstanceAt(neighborIndex);
						// if it does have an instance and it is a liquid
						// instance
						if (neighborInstance != null && neighborInstance instanceof BlockInstanceLiquid) {
							BlockInstanceLiquid neighborLiquid = (BlockInstanceLiquid) neighborInstance;
							// if it is the same liquid type and it has less
							// liquid
							if (neighborLiquid.getBlock() == this.getBlock()
									&& neighborLiquid.getAmount() + value < this.getAmount()) {
								// transfer liquid to it
								this.transferLiquid(neighborLiquid, value);
							}
						}
					}
				}
			}
		}
	}

	private static final Vector3i[] NEIGHBOR = { new Vector3i(1, 0, 0), new Vector3i(-1, 0, 0), new Vector3i(0, 0, 1),
			new Vector3i(0, 0, -1), };

	/**
	 * try to transfer water to the given block instance
	 * 
	 * @return the amount of liquid transfered
	 */
	public void transferLiquid(BlockInstanceLiquid dst, int amount) {

		// get the greatest amount we can transfer
		int toTransfer = Maths.min(amount, this.getAmount());

		// get the greatest amount the block under can hold
		int transfered = Maths.min(toTransfer, MAX_LIQUID_AMOUNT - dst.getAmount());

		if (transfered > 0) {
			dst.setAmount((short) (dst.getAmount() + transfered));
			this.setAmount((short) (this.getAmount() - transfered));
		}
	}

	@Override
	public void onSet() {
	}

	@Override
	public void onUnset() {
	}

	public Block getBlockUnder() {
		return (this.blockUnder);
	}
}
