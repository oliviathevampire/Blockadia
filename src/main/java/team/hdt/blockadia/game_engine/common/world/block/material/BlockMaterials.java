package team.hdt.blockadia.game_engine.common.world.block.material;

public class BlockMaterials {

    public static final Material AIR = new Material(MapColor.AIR);
    public static final Material GRASS = new Material(MapColor.GRASS);
    public static final Material WOOD = (new Material(MapColor.WOOD)).setBurning();
    public static final Material ROCK = (new Material(MapColor.STONE)).setRequiresTool();
    public static final Material METAL = (new Material(MapColor.IRON)).setRequiresTool();
    public static final Material LIQUID = (new LiquidMaterial(MapColor.WATER));
    public static final Material FOLIAGE = (new Material(MapColor.FOLIAGE)).setBurning().setTranslucent();
    public static final Material CLOTH = (new Material(MapColor.CLOTH)).setBurning();
    public static final Material FIRE = (new TransparentMaterial(MapColor.AIR));
    public static final Material SAND = new Material(MapColor.SAND);
    public static final Material DUST = new Material(MapColor.SAND);
    public static final Material GLASS = (new Material(MapColor.AIR)).setTranslucent().setAdventureModeExempt();
    public static final Material ICE = (new Material(MapColor.ICE)).setTranslucent().setAdventureModeExempt();
    public static final Material SNOW = (new MaterialLogic(MapColor.SNOW)).setReplaceable().setTranslucent().setRequiresTool();

    public static class Material {

        /**
         * The color index used to draw the blocks of this material on maps.
         */
        private final MapColor materialMapColor;
        /**
         * Bool defining if the block can burn or not.
         */
        private boolean canBurn;
        /**
         * Determines whether blocks with this material can be "overwritten" by other blocks when placed - eg snow, vines
         * and tall grass.
         */
        private boolean replaceable;
        /**
         * Indicates if the material is translucent
         */
        private boolean isTranslucent;
        /**
         * Determines if the material can be harvested without a tool (or with the wrong tool)
         */
        private boolean requiresNoTool = true;

        private boolean isAdventureModeExempt;

        public Material(MapColor color) {
            this.materialMapColor = color;
        }

        /**
         * Returns if blocks of these materials are liquids.
         */
        public boolean isLiquid() {
            return false;
        }

        /**
         * Returns true if the block is a considered solid. This is true by default.
         */
        public boolean isSolid() {
            return true;
        }

        /**
         * Will prevent grass from growing on dirt underneath and kill any grass below it if it returns true
         */
        public boolean blocksLight() {
            return true;
        }

        /**
         * Returns if this material is considered solid or not
         */
        public boolean blocksMovement() {
            return true;
        }

        /**
         * Marks the material as translucent
         */
        private Material setTranslucent() {
            this.isTranslucent = true;
            return this;
        }

        /**
         * Makes blocks with this material require the correct tool to be harvested.
         */
        protected Material setRequiresTool() {
            this.requiresNoTool = false;
            return this;
        }

        /**
         * Set the canBurn bool to True and return the current object.
         */
        protected Material setBurning() {
            this.canBurn = true;
            return this;
        }

        /**
         * Returns if the block can burn or not.
         */
        public boolean getCanBurn() {
            return this.canBurn;
        }

        /**
         * Sets {@link #replaceable} to true.
         */
        public Material setReplaceable() {
            this.replaceable = true;
            return this;
        }

        /**
         * Returns whether the material can be replaced by other blocks when placed - eg snow, vines and tall grass.
         */
        public boolean isReplaceable() {
            return this.replaceable;
        }

        /**
         * Indicate if the material is opaque
         */
        public boolean isOpaque() {
            return !this.isTranslucent && this.blocksMovement();
        }

        /**
         * Returns true if the material can be harvested without a tool (or with the wrong tool)
         */
        public boolean isToolNotRequired() {
            return this.requiresNoTool;
        }

        protected Material setAdventureModeExempt() {
            this.isAdventureModeExempt = true;
            return this;
        }

        /**
         * Retrieves the color index of the block. This is is the same color used by vanilla maps to represent this block.
         */
        public MapColor getMaterialMapColor() {
            return this.materialMapColor;
        }

    }

}
