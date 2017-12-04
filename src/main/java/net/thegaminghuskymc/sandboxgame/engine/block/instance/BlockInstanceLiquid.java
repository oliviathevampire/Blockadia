package net.thegaminghuskymc.sandboxgame.engine.block.instance;

import net.thegaminghuskymc.sandboxgame.engine.block.Block;
import net.thegaminghuskymc.sandboxgame.engine.block.BlockLiquid;
import net.thegaminghuskymc.sandboxgame.engine.item.Terrain;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Maths;
import net.thegaminghuskymc.sandboxgame.engine.util.math.Vector3i;
import net.thegaminghuskymc.sandboxgame.game.mod.Blocks;

public class BlockInstanceLiquid extends BlockInstance {

    private static final short MAX_LIQUID_AMOUNT = 16;
    private static final short MIN_LIQUID_AMOUNT = 1;
    public static final float LIQUID_HEIGHT_UNIT = MIN_LIQUID_AMOUNT / (float) MAX_LIQUID_AMOUNT;
    private static final short DEFAULT_WATER_AMOUNT = MAX_LIQUID_AMOUNT;
    private static final Vector3i[] NEIGHBOR = {new Vector3i(1, 0, 0), new Vector3i(-1, 0, 0), new Vector3i(0, 0, 1),
            new Vector3i(0, 0, -1),};

    private short amount;
    private long lastUpdate;
    private Block blockUnder;

    public BlockInstanceLiquid(Terrain terrain, Block block, int index) {
        super(terrain, block, index);
        this.setAmount(DEFAULT_WATER_AMOUNT);
        this.lastUpdate = terrain.getWorld().getTick();
    }

    public short getAmount() {
        return (this.amount);
    }

    private void setAmount(short amount) {
        this.amount = amount;
    }

    @Override
    public void update() {
        int TICK_TO_UPDATE = 4;
        long tick = this.getTerrain().getWorld().getTick();
        if (tick - this.lastUpdate < TICK_TO_UPDATE) {
            return;
        }
        this.lastUpdate = tick;

        if (this.amount < MIN_LIQUID_AMOUNT) {
            this.disperse();
        } else if (this.amount > MIN_LIQUID_AMOUNT) {
            this.flow();
        }
    }

    private void disperse() {
        int index = super.getIndex();
        super.getTerrain().setBlock(Blocks.AIR, index);
    }

    private void flow() {

        Terrain terrain = super.getTerrain();
        int index = super.getIndex();
        int z = terrain.getZFromIndex(index);
        int y = terrain.getYFromIndex(index, z);
        int x = terrain.getXFromIndex(index, y, z);

        int[] under = {x, y - 1, z};
        Terrain terrainUnder = terrain.getRelativeTerrain(under);
        if (terrainUnder != null) {

            int indexUnder = terrainUnder.getIndex(under);
            this.blockUnder = terrainUnder.getBlockAt(indexUnder);

            if (this.blockUnder == Blocks.AIR) {
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
                    BlockInstance liquidUnder = terrainUnder.getBlockInstanceAt(indexUnder);
                    if (liquidUnder != null && liquidUnder instanceof BlockInstanceLiquid) {
                        BlockInstanceLiquid liquid = (BlockInstanceLiquid) liquidUnder;
                        this.transferLiquid(liquid, this.getAmount());
                    }
                }
            }
            if (this.getAmount() > MIN_LIQUID_AMOUNT) {
                short value = (short) Math.max(this.getAmount() / 4, MIN_LIQUID_AMOUNT);

                for (Vector3i d : NEIGHBOR) {
                    int[] bxyz = {x + d.x, y + d.y, z + d.z};

                    Terrain neighborTerrain = terrain.getRelativeTerrain(bxyz);
                    if (neighborTerrain == null) {
                        continue;
                    }

                    int neighborIndex = neighborTerrain.getIndex(bxyz);
                    Block neighborBlock = neighborTerrain.getBlockAt(neighborIndex);

                    if (neighborBlock == Blocks.AIR) {
                        BlockInstance neighborInstance = neighborTerrain.setBlock(super.getBlock(), neighborIndex,
                                bxyz);
                        if (neighborInstance != null && neighborInstance instanceof BlockInstanceLiquid) {
                            BlockInstanceLiquid neighborLiquid = (BlockInstanceLiquid) neighborInstance;
                            neighborLiquid.setAmount((short) 0);
                            neighborLiquid.lastUpdate = this.lastUpdate;
                            this.transferLiquid(neighborLiquid, value);
                        }
                    } else {
                        BlockInstance neighborInstance = neighborTerrain.getBlockInstanceAt(neighborIndex);
                        if (neighborInstance != null && neighborInstance instanceof BlockInstanceLiquid) {
                            BlockInstanceLiquid neighborLiquid = (BlockInstanceLiquid) neighborInstance;
                            if (neighborLiquid.getBlock() == this.getBlock()
                                    && neighborLiquid.getAmount() + value < this.getAmount()) {
                                this.transferLiquid(neighborLiquid, value);
                            }
                        }
                    }
                }
            }
        }
    }

    private void transferLiquid(BlockInstanceLiquid dst, int amount) {
        int toTransfer = Maths.min(amount, this.getAmount());
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
