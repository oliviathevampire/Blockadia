package team.hdt.blockadia.game_engine.common.world.item;

public enum EnumVisualColor {

    WHITE(0, "white", 16383998),
    ORANGE(1, "orange", 16351261),
    MAGENTA(2, "magenta", 13061821),
    LIGHT_BLUE(3, "light_blue", 3847130),
    YELLOW(4, "yellow", 16701501),
    LIME(5, "lime", 8439583),
    PINK(6, "pink", 15961002),
    GRAY(7, "gray", 4673362),
    SILVER(8, "silver", 10329495),
    CYAN(9, "cyan", 1481884),
    PURPLE(10, "purple", 8991416),
    BLUE(11, "blue", 3949738),
    BROWN(12, "brown", 8606770),
    GREEN(13, "green", 6192150),
    RED(14, "red", 11546150),
    BLACK(15, "black", 1908001);

    private static final EnumVisualColor[] META_LOOKUP = new EnumVisualColor[values().length];

    static {
        for (EnumVisualColor enumdyecolor : values()) {
            META_LOOKUP[enumdyecolor.getMetadata()] = enumdyecolor;
        }
    }

    private final int meta;
    private final String name;
    /**
     * An int containing the corresponding RGB color for this dye color.
     */
    private final int colorValue;
    /**
     * An array containing 3 floats ranging from 0.0 to 1.0: the red, green, and blue components of the corresponding
     * color.
     */
    private final float[] colorComponentValues;

    EnumVisualColor(int metaIn, String nameIn, int colorValueIn) {
        this.meta = metaIn;
        this.name = nameIn;
        this.colorValue = colorValueIn;
        int i = (colorValueIn & 16711680) >> 16;
        int j = (colorValueIn & 65280) >> 8;
        int k = (colorValueIn & 255);
        this.colorComponentValues = new float[]{(float) i / 255.0F, (float) j / 255.0F, (float) k / 255.0F};
    }

    public static EnumVisualColor byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
        }

        return META_LOOKUP[meta];
    }

    public int getMetadata() {
        return this.meta;
    }

    public String getColorName() {
        return this.name;
    }

    /**
     * Gets the RGB color corresponding to this dye color.
     */
    public int getColorValue() {
        return this.colorValue;
    }

    /**
     * Gets an array containing 3 floats ranging from 0.0 to 1.0: the red, green, and blue components of the
     * corresponding color.
     */
    public float[] getColorComponentValues() {
        return this.colorComponentValues;
    }

    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }
}