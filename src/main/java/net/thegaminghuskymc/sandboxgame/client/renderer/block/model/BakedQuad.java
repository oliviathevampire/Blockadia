package net.thegaminghuskymc.sandboxgame.client.renderer.block.model;

import net.thegaminghuskymc.sandboxgame.client.model.pipeline.IVertexConsumer;
import net.thegaminghuskymc.sandboxgame.client.model.pipeline.IVertexProducer;
import net.thegaminghuskymc.sandboxgame.client.model.pipeline.LightUtil;
import net.thegaminghuskymc.sandboxgame.client.renderer.texture.TextureAtlasSprite;
import net.thegaminghuskymc.sandboxgame.client.renderer.vertex.DefaultVertexFormats;
import net.thegaminghuskymc.sandboxgame.client.renderer.vertex.VertexFormat;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class BakedQuad implements IVertexProducer {
    /**
     * Joined 4 vertex records, each stores packed data according to the VertexFormat of the quad. Vanilla minecraft
     * uses DefaultVertexFormats.BLOCK, Forge uses (usually) ITEM, use BakedQuad.getFormat() to get the correct format.
     */
    protected final int[] vertexData;
    protected final int tintIndex;
    protected final EnumFacing face;
    protected final TextureAtlasSprite sprite;
    protected final VertexFormat format;
    protected final boolean applyDiffuseLighting;

    /**
     * @deprecated Use constructor with the format argument.
     */
    @Deprecated
    public BakedQuad(int[] vertexDataIn, int tintIndexIn, EnumFacing faceIn, TextureAtlasSprite spriteIn) {
        this(vertexDataIn, tintIndexIn, faceIn, spriteIn, true, DefaultVertexFormats.ITEM);
    }

    public BakedQuad(int[] vertexDataIn, int tintIndexIn, EnumFacing faceIn, TextureAtlasSprite spriteIn, boolean applyDiffuseLighting, VertexFormat format) {
        this.format = format;
        this.applyDiffuseLighting = applyDiffuseLighting;
        this.vertexData = vertexDataIn;
        this.tintIndex = tintIndexIn;
        this.face = faceIn;
        this.sprite = spriteIn;
    }

    public TextureAtlasSprite getSprite() {
        return this.sprite;
    }

    public int[] getVertexData() {
        return this.vertexData;
    }

    public boolean hasTintIndex() {
        return this.tintIndex != -1;
    }

    public int getTintIndex() {
        return this.tintIndex;
    }

    public EnumFacing getFace() {
        return this.face;
    }

    @Override
    public void pipe(IVertexConsumer consumer) {
        LightUtil.putBakedQuad(consumer, this);
    }

    public VertexFormat getFormat() {
        return format;
    }

    public boolean shouldApplyDiffuseLighting() {
        return applyDiffuseLighting;
    }
}