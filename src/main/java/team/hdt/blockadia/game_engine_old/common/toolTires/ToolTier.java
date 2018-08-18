package team.hdt.blockadia.game_engine_old.common.toolTires;

public enum ToolTier {
	ZERO(0, 1f),
	ONE(1, 1.5f),
	TWO(2, 2f),
	THREE(3, 2.25f),
	FOUR(4, 2.5f),
	FIVE(5, 2.7f),
	SIX(6, 2.9f),
	SEVEN(7, 3f),
	EIGHT(8, 3.1f),
	NINE(9, 3.15f),
	TEN(10, 3.2f),
	CUSTOM(-1, 1f);
	
	private final float multiplier;
	private final int tier;
	
	private ToolTier(int tier, float multiplier) {
		this.multiplier = multiplier;
		this.tier = tier;
	}
	
	/**
	 * Returns if this ToolTier is a custom one
	 * @return <b>true</b> if this ToolTier is a custom tier, <b>false</b> otherwise
	 */
	public final boolean isCustomTier() {
		return this.tier == -1;
	}
	
	/**
	 * Gets the multiplier this ToolTier provides
	 * @return the ToolTier multiplier
	 */
	public final float getMultiplier() {
		return this.multiplier;
	}
	
	/**
	 * Gets the tier this ToolTier is
	 * @return the tier of this ToolTier
	 */
	public final int getTier() {
		return this.tier;
	}
	
	/**
	 * Checks to see if the supplied ToolTier is sufficient enough for the base ToolTier
	 * @param base the ToolTier to check against
	 * @param supplied the ToolTier used to check the base
	 * @return <b>true</b> if supplied >= base
	 */
	public static boolean isSufficient(ToolTier base, ToolTier supplied) {
		return (supplied.tier >= base.tier);
	}
	
	/**
	 * Checks to see if the supplied CustomToolTier is sufficient enough for the base ToolTier
	 * @param base the ToolTier to check against
	 * @param supplied the CustomToolTier used to check the base
	 * @return <b>true</b> if supplied >= base
	 */
	public static boolean isSufficient(ToolTier base, ICustomToolTier supplied) {
		return (supplied.getTier() >= base.tier);
	}
	
	/**
	 * Checks to see if the supplied ToolTier is sufficient enough for the base CustomToolTier
	 * @param base the CustomToolTier to check against
	 * @param supplied the ToolTier used to check the base
	 * @return <b>true</b> if supplied >= base
	 */
	public static boolean isSufficient(ICustomToolTier base, ToolTier supplied) {
		return (supplied.tier >= base.getTier());
	}
	
	/**
	 * Checks to see if the supplied CustomToolTier is sufficient enough for the base CustomToolTier
	 * @param base the ToolTier to check against
	 * @param check the ToolTier used to check the base
	 * @return <b>true</b> if supplied >= base
	 */
	public static boolean isSufficient(ICustomToolTier base, ICustomToolTier check) {
		return (check.getTier() >= base.getTier());
	}
}