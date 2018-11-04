package team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.texture;

import team.hdt.blockadia.old_engine_code_1.core_rewrite.util.Identifier;

public class ModelTexture {

	private Identifier texture;
	private int numberOfRows;
	private boolean hasTransparency;
	private boolean useFakeLighting;

	public ModelTexture(Identifier texture) {
		this.texture = texture;
		this.numberOfRows = 1;
		this.hasTransparency = false;
		this.useFakeLighting = false;
	}

	/**
	 * @return The resource location for the texture.
	 */
	public Identifier getTexture() {
		return texture;
	}

	/**
	 * @return The number of rows the texture has.
	 */
	public int getNumberOfRows() {
		return numberOfRows;
	}

	/**
	 * @return Whether or not the texture has transparency.
	 */
	public boolean hasTransparency() {
		return hasTransparency;
	}

	/**
	 * @return Whether or not the texture uses fake lighting.
	 */
	public boolean usesFakeLighting() {
		return useFakeLighting;
	}

	/**
	 * Sets the number of rows the model texture has.
	 * 
	 * @param numberOfRows
	 * 				The number of rows this model texture will have.
	 * 
	 * @return The model texture.
	 */
	public ModelTexture setNumberOfRows(int numberOfRows) {
		if(numberOfRows < 1)
			numberOfRows = 1;
		this.numberOfRows = numberOfRows;
		return this;
	}

	/**
	 * Sets whether or not this model texture has transparency.
	 * 
	 * @param transparent
	 * 				Sets whether or not the model texture has transparency.
	 * 
	 * @return The model texture.
	 */
	public ModelTexture setTransparent(boolean transparent) {
		this.hasTransparency = transparent;
		return this;
	}

	/**
	 * Sets whether or not this model texture uses fake lighting.
	 * 
	 * @param useFakeLighting
	 * 				Sets whether or not the model texture will use fake lighting.
	 * 
	 * @return The model texture.
	 */
	public ModelTexture setUseFakeLighting(boolean useFakeLighting) {
		this.useFakeLighting = useFakeLighting;
		return this;
	}
}