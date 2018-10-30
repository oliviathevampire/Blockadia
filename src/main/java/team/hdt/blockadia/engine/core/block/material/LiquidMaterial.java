package team.hdt.blockadia.engine.core.block.material;

public class LiquidMaterial extends BlockMaterials.Material {
    public LiquidMaterial(MapColor color) {
        super(color);
        this.setReplaceable();
    }

    /**
     * Returns if block of these materials are liquids.
     */
    public boolean isLiquid() {
        return true;
    }

    /**
     * Returns if this material is considered solid or not
     */
    public boolean blocksMovement() {
        return false;
    }

    /**
     * Returns true if the block is a considered solid. This is true by default.
     */
    public boolean isSolid() {
        return false;
    }
}