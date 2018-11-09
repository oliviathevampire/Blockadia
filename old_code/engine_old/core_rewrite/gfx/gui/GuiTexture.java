package team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.gui;

import org.joml.Matrix4f;
import org.joml.Vector4f;
import team.hdt.blockadia.old_engine_code_1.core_rewrite.gfx.texture.ITexture;

import javax.annotation.Nullable;

/**
 * <em><b>Copyright (c) 2018 The Zerra Team.</b></em>
 * 
 * <br>
 * </br>
 * 
 * A texture that is used to represent a single quad in a gui.
 * 
 * @author Ocelot5836
 */
public class GuiTexture {

	public static final int TEXTURE = 0;
	public static final int COLOR = 1;

	private int type;

	private ITexture texture;
	private Vector4f textureOffsets;
	private float textureSize;

	private Vector4f color;

	private Matrix4f transformation;

	protected GuiTexture(ITexture texture, Vector4f textureOffsets, float textureSize, Matrix4f transformation) {
		this.type = TEXTURE;
		this.texture = texture;
		this.textureOffsets = textureOffsets;
		this.textureSize = textureSize;
		this.transformation = transformation;
	}

	protected GuiTexture(Vector4f color, Matrix4f transformation) {
		this.type = COLOR;
		this.color = color;
		this.transformation = transformation;
	}

	/**
	 * @return The type of rendering that will occur
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return The actual texture of this quad. {@link ITexture} is used so you can render an image that is created in opengl if you wish
	 */
	@Nullable
	public ITexture getTexture() {
		return texture;
	}

	/**
	 * @return The offsets used in the shaders when rendering the quad
	 */
	@Nullable
	public Vector4f getTextureOffsets() {
		return textureOffsets;
	}

	/**
	 * @return The size of the texture
	 */
	@Nullable
	public float getTextureSize() {
		return textureSize;
	}

	/**
	 * @return The color of the quad
	 */
	@Nullable
	public Vector4f getColor() {
		return color;
	}

	/**
	 * @return The transformation matrix for this quad
	 */
	public Matrix4f getTransformation() {
		return transformation;
	}
}