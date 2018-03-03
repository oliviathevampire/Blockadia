package net.thegaminghuskymc.sandboxgame.client.renderer.block.model;

import net.thegaminghuskymc.sandboxgame.block.state.IBlockState;
import net.thegaminghuskymc.sandboxgame.client.renderer.texture.TextureAtlasSprite;
import net.thegaminghuskymc.sandboxgame.util.math.Matrix4f;
import net.thegaminghuskymc.sandboxgame.util.EnumFacing;
import net.thegaminghuskymc.sgf.fml.client.ForgeHooksClient;
import net.thegaminghuskymc.sgf.fml.relauncher.Side;
import net.thegaminghuskymc.sgf.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

@SideOnly(Side.CLIENT)
public interface IBakedModel {
    List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand);

    boolean isAmbientOcclusion();

    boolean isGui3d();

    boolean isBuiltInRenderer();

    TextureAtlasSprite getParticleTexture();

    @Deprecated
    default ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    ItemOverrideList getOverrides();

    /*
     * Returns the pair of the model for the given perspective, and the matrix
     * that should be applied to the GL state before rendering it (matrix may be null).
     */
    default org.apache.commons.lang3.tuple.Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        return ForgeHooksClient.handlePerspective(this, cameraTransformType);
    }
}